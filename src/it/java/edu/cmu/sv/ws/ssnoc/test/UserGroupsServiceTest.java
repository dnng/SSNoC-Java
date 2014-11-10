package edu.cmu.sv.ws.ssnoc.test;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.cmu.sv.ws.ssnoc.common.exceptions.CheckedException;
import edu.cmu.sv.ws.ssnoc.common.exceptions.ServiceException;
import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.dao.MessageDAOImpl;
import edu.cmu.sv.ws.ssnoc.data.util.ConnectionPoolFactory;
import edu.cmu.sv.ws.ssnoc.data.util.IConnectionPool;
import edu.cmu.sv.ws.ssnoc.dto.Message;
import edu.cmu.sv.ws.ssnoc.dto.User;
import edu.cmu.sv.ws.ssnoc.dto.UserGroup;
import edu.cmu.sv.ws.ssnoc.rest.MessageService;
import edu.cmu.sv.ws.ssnoc.rest.UserGroupsService;
import edu.cmu.sv.ws.ssnoc.rest.UserService;

public class UserGroupsServiceTest {

    ArrayList<User> testUsers = new ArrayList<User>();

    protected static void handleException(Exception e) {
        Log.error(e);
        if (e instanceof CheckedException) {
            throw (CheckedException) e;
        } else {
            throw new ServiceException(e);
        }
    }

    @BeforeClass
    public static void setUpTest() throws Exception {
        // testUsers = null;
        UserService userService = new UserService();
        try {
            IConnectionPool cp = ConnectionPoolFactory.getInstance().getH2ConnectionPool();
            cp.switchConnectionToLive();

        } catch (Exception e) {
            handleException(e);
        } finally {
            Log.exit();
        }

        for (int i = 1; i <= 4; i++) {
            User tempUser = new User();
            tempUser.setUserName("user" + i);
            tempUser.setPassword("password" + i);
            userService.addUser(tempUser);
        }
    }

    @Before
    public void setUp() throws Exception {
        MessageDAOImpl msgCleaning = new MessageDAOImpl();
        msgCleaning.cleanUpAllMessages();

    }

    @After
    public void cleanup() {
        try {
            IConnectionPool cp = ConnectionPoolFactory.getInstance().getH2ConnectionPool();
            cp.switchConnectionToLive();

        } catch (Exception e) {
            handleException(e);
        } finally {
            Log.exit();
        }
    }

    private boolean isPresent(UserGroup ug, List<UserGroup> ugl) {
        boolean contains = false;
        for (UserGroup tempGroup : ugl) {
            if (ug.equals(tempGroup)) {
                contains = true;
                break;
            } else
                contains = false;
        }
        return contains;
    }

    // Test Case 1
    //
    @Test
    public void test01Chatbuddies() {
        Message msg = new Message();
        Timestamp postedAt = new Timestamp(System.currentTimeMillis());
        msg.setPostedAt(postedAt);
        msg.setContent("chat msg1");
        MessageService msgService = new MessageService();

        Response response = msgService.postPrivateChatMessage("user1", "user2", msg);
        response = msgService.postPrivateChatMessage("user1", "user3", msg);
        response = msgService.postPrivateChatMessage("user2", "user4", msg);

        UserGroupsService tempgroupservice = new UserGroupsService();
        List<UserGroup> testResult = tempgroupservice.loadUnconnectedClusters(2);

        // System.out.println("------------------Test 1------------------");
        // System.out.println(testResult.toString());
        // --------------------Asserts------------------------------
        // [{"userNames":["user1","user4","SSNAdmin"]},
        // {"userNames":["user2","user3","SSNAdmin"]},
        // {"userNames":["user4","user3","SSNAdmin"]}]

        assertEquals(3, testResult.size());

        UserGroup expectGroup = new UserGroup();
        expectGroup.setUserNames(new ArrayList<String>(Arrays.asList("user1", "user4", "SSNAdmin")));
        assertTrue(isPresent(expectGroup, testResult));

        expectGroup.setUserNames(new ArrayList<String>(Arrays.asList("user2", "user3", "SSNAdmin")));
        assertTrue(isPresent(expectGroup, testResult));

        expectGroup.setUserNames(new ArrayList<String>(Arrays.asList("user4", "user3", "SSNAdmin")));
        assertTrue(isPresent(expectGroup, testResult));
    }

    // Test Case2
    // create a list of 10 users
    // Check list of users for last 1 hr
    // users chated before that should be in non buddies
    @Test
    public void test02Chattiming() {

        Timestamp postedAt3hrs = new Timestamp(System.currentTimeMillis() - 3 * 60 * 60 * 1000);
        Timestamp postedAt1hrs = new Timestamp(System.currentTimeMillis() - 1 * 60 * 60 * 1000);
        Timestamp postedAtNow = new Timestamp(System.currentTimeMillis());

        Message msg = new Message();
        msg.setContent("chat msg1");
        MessageService msgService = new MessageService();

        msg.setPostedAt(postedAt3hrs);
        System.out.println("------------------------------\n" + msg.getPostedAt()
                + "\n------------------------------\n");
        Response response = msgService.postPrivateChatMessage("user1", "user2", msg);
        msg.setPostedAt(postedAt3hrs);
        response = msgService.postPrivateChatMessage("user1", "user3", msg);

        msg.setPostedAt(postedAt1hrs);
        response = msgService.postPrivateChatMessage("user1", "user4", msg);

        msg.setPostedAt(postedAtNow);
        response = msgService.postPrivateChatMessage("user1", "SSNAdmin", msg);

        Message msg1 = new Message();
        msg1.setContent("chat msg1");
        // MessageService msgService = new MessageService();
        response = msgService.postPrivateChatMessage("user1", "user4", msg);

        UserGroupsService tempgroupservice = new UserGroupsService();
        List<UserGroup> testResult = tempgroupservice.loadUnconnectedClusters(2);
        // System.out.println("----------------Test 2--------------------");
        // System.out.println(testResult.toString());
        // -------------Assert----------------------
        // [{"userNames":["user2","user4","user3","SSNAdmin"]},
        // {"userNames":["user2","user1","user3"]}]

        assertEquals(2, testResult.size());
        UserGroup expectGroup = new UserGroup();
        expectGroup.setUserNames(new ArrayList<String>(Arrays.asList("user2", "user4", "user3", "SSNAdmin")));
        assertTrue(isPresent(expectGroup, testResult));

        expectGroup.setUserNames(new ArrayList<String>(Arrays.asList("user2", "user1", "user3")));
        assertTrue(isPresent(expectGroup, testResult));
    }

    // // Test Case3
    // // boundry value test all 5 will come if all body had chat
    @Test
    public void test03Chatboundryvalue() {
        Message msg = new Message();
        // Timestamp postedAt3hrs = new Timestamp(System.currentTimeMillis() - 3
        // * 60 * 60 * 1000);
        Timestamp postedAt = new Timestamp(System.currentTimeMillis());
        msg.setPostedAt(postedAt);
        msg.setContent("chat msg1");
        MessageService msgService = new MessageService();
        Response response = msgService.postPrivateChatMessage("user1", "user2", msg);
        response = msgService.postPrivateChatMessage("user1", "user3", msg);
        response = msgService.postPrivateChatMessage("user1", "user4", msg);
        response = msgService.postPrivateChatMessage("user1", "SSNAdmin", msg);
        response = msgService.postPrivateChatMessage("user2", "user3", msg);
        response = msgService.postPrivateChatMessage("user2", "user4", msg);
        response = msgService.postPrivateChatMessage("user2", "SSNAdmin", msg);
        response = msgService.postPrivateChatMessage("user3", "user4", msg);
        response = msgService.postPrivateChatMessage("user3", "SSNAdmin", msg);
        response = msgService.postPrivateChatMessage("user4", "SSNAdmin", msg);
        UserGroupsService tempgroupservice = new UserGroupsService();
        List<UserGroup> testResult = tempgroupservice.loadUnconnectedClusters(2);
        // System.out.println("----------------Test 3--------------------");
        // System.out.println(testResult.toString());
        // -------------Assert----------------------
        // [{"userNames":["SSNAdmin"]}, {"userNames":["user1"]},
        // {"userNames":["user2"]}, {"userNames":["user3"]},
        // {"userNames":["user4"]}]
        assertEquals(5, testResult.size());
        UserGroup expectGroup = new UserGroup();
        expectGroup.setUserNames(new ArrayList<String>(Arrays.asList("SSNAdmin")));
        assertTrue(isPresent(expectGroup, testResult));
        expectGroup.setUserNames(new ArrayList<String>(Arrays.asList("user2")));
        assertTrue(isPresent(expectGroup, testResult));
        expectGroup.setUserNames(new ArrayList<String>(Arrays.asList("user1")));
        assertTrue(isPresent(expectGroup, testResult));
        expectGroup.setUserNames(new ArrayList<String>(Arrays.asList("user4")));
        assertTrue(isPresent(expectGroup, testResult));
        expectGroup.setUserNames(new ArrayList<String>(Arrays.asList("user3")));
        assertTrue(isPresent(expectGroup, testResult));
    }

    // // Test Case4
    // // No body done any chat
    @Test
    public void test04nochat() {
        Message msg = new Message();
        Timestamp postedAt = new Timestamp(System.currentTimeMillis());
        msg.setPostedAt(postedAt);
        msg.setContent("chat msg1");
        MessageService msgService = new MessageService();
        UserGroupsService tempgroupservice = new UserGroupsService();
        List<UserGroup> testResult = tempgroupservice.loadUnconnectedClusters(2);
        // System.out.println("----------------Test 4--------------------");
        // System.out.println(testResult.toString());
        // ------Assert---------------------
        assertEquals(1, testResult.size());
        UserGroup expectGroup = new UserGroup();
        expectGroup.setUserNames(new ArrayList<String>(Arrays.asList("user1", "user2", "user4", "user3", "SSNAdmin")));
        assertTrue(isPresent(expectGroup, testResult));
        // // [{"userNames":["user2","user1","user4","user3","SSNAdmin"]}]
    }

}
