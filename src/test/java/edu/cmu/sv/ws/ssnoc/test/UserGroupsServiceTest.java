package edu.cmu.sv.ws.ssnoc.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.dao.MessageDAOImpl;
import edu.cmu.sv.ws.ssnoc.data.util.ConnectionPoolFactory;
import edu.cmu.sv.ws.ssnoc.data.util.DBUtils;
import edu.cmu.sv.ws.ssnoc.data.util.IConnectionPool;
import edu.cmu.sv.ws.ssnoc.dto.Message;
import edu.cmu.sv.ws.ssnoc.dto.User;
import edu.cmu.sv.ws.ssnoc.dto.UserGroup;
import edu.cmu.sv.ws.ssnoc.rest.MessageService;
import edu.cmu.sv.ws.ssnoc.rest.UserGroupsService;
import edu.cmu.sv.ws.ssnoc.rest.UserService;

public class UserGroupsServiceTest {

	ArrayList<User> testUsers = new ArrayList<User>();

	@Before
	public void setUp() throws Exception {
		
		try {
			IConnectionPool cp = ConnectionPoolFactory.getInstance()
					.getH2ConnectionPool();
			cp.switchConnectionToTest();
			
			DBUtils.reinitializeDatabase();
			
			UserService userService = new UserService();

			for (int i = 1; i <= 5; i++) {
				User tempUser = new User();
				tempUser.setUserName("user" + i);
				tempUser.setPassword("password" + i);
				userService.addUser(tempUser);
				testUsers.add(tempUser);
			}

		} catch (Exception e) {
			Log.error(e);
		} finally {
			Log.exit();
		}
	}

	@After
	public void cleanup() {
		testUsers = null;


		MessageDAOImpl msgCleaning = new MessageDAOImpl();
		msgCleaning.cleanUpAllMessages();
		
		try {
			IConnectionPool cp = ConnectionPoolFactory.getInstance()
					.getH2ConnectionPool();
			cp.switchConnectionToLive(); 

		} catch (Exception e) {
			Log.error(e);
		} finally {
			Log.exit();
		}

	}


	@Test
	public void testNoChatBetweenUsers() {
		UserGroupsService tempgroupservice = new UserGroupsService();
		List<UserGroup> testResult = tempgroupservice.loadUnconnectedClusters(2);
		/*
		 * On the setup, we created 5 users (user1, user2, user3, user4, user5). So,
		 * the object returned by loadUnconnectedClusters(), should contain _at
		 * least_ these 5 users
		 */
		assertTrue(testResult.get(0).getUserNames().contains(testUsers.get(0).getUserName()));
		assertTrue(testResult.get(0).getUserNames().contains(testUsers.get(1).getUserName()));
		assertTrue(testResult.get(0).getUserNames().contains(testUsers.get(2).getUserName()));
		assertTrue(testResult.get(0).getUserNames().contains(testUsers.get(3).getUserName()));
	}

	@Test
	public void testChatBetweenUsers() {
		Message msg = new Message();
		Timestamp postedAt = new Timestamp(System.currentTimeMillis() - 30 * 60 * 1000);
		msg.setPostedAt(postedAt);
		msg.setContent("chat msg1");
		MessageService msgService = new MessageService();

		Response response = msgService.postPrivateChatMessage("user1", "user2", msg);
		response = msgService.postPrivateChatMessage("user1", "user3", msg);
		response = msgService.postPrivateChatMessage("user2", "user4", msg);
		assertTrue(response instanceof Response);

		/*
		 * Here, we can see that user5 did not communicate with nobody, then,
		 * no matter the number of clusters, user5 will appear in _all_ of them!
		 * So, in reality, we only need to test one case.
		 */
		UserGroupsService tempgroupservice = new UserGroupsService();
		List<UserGroup> testResult = tempgroupservice.loadUnconnectedClusters(2);
		assertTrue(testResult.get(0).getUserNames().contains(testUsers.get(4).getUserName()));
	}

	@Test
	public void testOneUserChatsWithAllOtherUsers() {
		Message msg = new Message();
		Timestamp postedAt3hrs = new Timestamp(System.currentTimeMillis() - 3 * 60 * 60 * 1000);
		Timestamp postedAt1hrs = new Timestamp(System.currentTimeMillis() - 1 * 60 * 60 * 1000);

		msg.setContent("chat msg1");
		MessageService msgService = new MessageService();

		msg.setPostedAt(postedAt3hrs);
		Response response = msgService.postPrivateChatMessage("user1", "user2", msg);

		msg.setPostedAt(postedAt3hrs);
		response = msgService.postPrivateChatMessage("user1", "user3", msg);

		msg.setPostedAt(postedAt1hrs);
		response = msgService.postPrivateChatMessage("user1", "user4", msg);

		msg.setPostedAt(postedAt1hrs);
		response = msgService.postPrivateChatMessage("user1", "user5", msg);

		assertTrue(response instanceof Response);

		UserGroupsService tempgroupservice = new UserGroupsService();
		List<UserGroup> testResult = tempgroupservice.loadUnconnectedClusters(2);
		/*
		 * In this test, we know that at least one of the users talked to everybody.
		 * A simple test is to verify that no cluster should have all users in it.
		 */
		assertFalse(testResult.get(0).getUserNames().containsAll(testUsers));
	}

	@Test
	public void testAllUsersTalkToAllUsers() {
		Message msg = new Message();
		Timestamp postedAt = new Timestamp(System.currentTimeMillis() - 1 * 60 * 60 * 1000);
		msg.setPostedAt(postedAt);
		msg.setContent("chat msg1");
		MessageService msgService = new MessageService();
		Response response = msgService.postPrivateChatMessage("user1", "user2", msg);
		response = msgService.postPrivateChatMessage("user1", "user3", msg);
		response = msgService.postPrivateChatMessage("user1", "user4", msg);
		response = msgService.postPrivateChatMessage("user1", "user5", msg);
		response = msgService.postPrivateChatMessage("user2", "user3", msg);
		response = msgService.postPrivateChatMessage("user2", "user4", msg);
		response = msgService.postPrivateChatMessage("user2", "user5", msg);
		response = msgService.postPrivateChatMessage("user3", "user4", msg);
		response = msgService.postPrivateChatMessage("user3", "user5", msg);
		response = msgService.postPrivateChatMessage("user4", "user5", msg);
		UserGroupsService tempgroupservice = new UserGroupsService();
		List<UserGroup> testResult = tempgroupservice.loadUnconnectedClusters(2);
		/*
		 * In this test, we know that all created users talked to each other.
		 * So, no cluster should have more than the single user in it (and other
		 * possible users like  SSNAdmin). To test that behavior, we remove users
		 * from the testUsers object until there are only two of them left. After
		 * that, we check if a cluster has more than a single isolated user from
		 * the testUsers set.
		 */
		testUsers.remove(4);
		testUsers.remove(3);
		testUsers.remove(2);
		assertFalse(testResult.get(0).getUserNames().containsAll(testUsers));

	}

}
