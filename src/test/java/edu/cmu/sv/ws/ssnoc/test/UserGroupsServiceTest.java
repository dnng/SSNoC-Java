package edu.cmu.sv.ws.ssnoc.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.sv.ws.ssnoc.data.SQL;
import edu.cmu.sv.ws.ssnoc.data.dao.MessageDAOImpl;
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
		testUsers = null;
		UserService userService = new UserService();

		for (int i = 1; i <= 5; i++) {
			User tempUser = new User();
			tempUser.setUserName("user" + i);
			tempUser.setPassword("password" + i);
			userService.addUser(tempUser);
		}
	}

	@After
	public void cleanup() {
		testUsers = null;
		
		// clear all previous messages
		String msgCleanUPQuery = SQL.CLEAN_UP_MSGS;
		
		MessageDAOImpl msgCleaning = new MessageDAOImpl();
		msgCleaning.cleanUpAllMessages();
		
	}
	// Test Case4
	// No body done any chat
	@Test
	public void Test04_nochat() {
		Message msg = new Message();
		Timestamp postedAt = new Timestamp(System.currentTimeMillis() - 1 * 60 * 60 * 1000);
		msg.setPostedAt(postedAt);
		msg.setContent("chat msg1");
		MessageService msgService = new MessageService();
		UserGroupsService tempgroupservice = new UserGroupsService();
		List<UserGroup> testResult = tempgroupservice.loadUnconnectedClusters(2);
		System.out.println("----------------Test 4--------------------");
		System.out.println(testResult.toString());
	}

	@Test
	public void Test01_Chatbuddies() {
		Message msg = new Message();
		Timestamp postedAt = new Timestamp(
				System.currentTimeMillis() - 30 * 60 * 1000);
		msg.setPostedAt(postedAt);
		msg.setContent("chat msg1");
		MessageService msgService = new MessageService();

		Response response = msgService.postPrivateChatMessage("user1", "user2", msg);
		response = msgService.postPrivateChatMessage("user1", "user3", msg);
		response = msgService.postPrivateChatMessage("user2", "user4", msg);

		UserGroupsService tempgroupservice = new UserGroupsService();
		List<UserGroup> testResult = tempgroupservice
				.loadUnconnectedClusters(2);
		System.out.println("------------------Test 1------------------");
		System.out.println(testResult.toString());
	}

	// Test Case2
	// create a list of 10 users
	// Check list of users for last 1 hr
	// users chated before that should be in non buddies
	@Test
	public void Test02_Chattiming() {
		Message msg = new Message();
		Timestamp postedAt3hrs = new Timestamp(System.currentTimeMillis() - 3 * 60 * 60 * 1000);
		Timestamp postedAt1hrs = new Timestamp(System.currentTimeMillis() - 1 * 60 * 60 * 1000);
		
		msg.setContent("chat msg1");
		MessageService msgService = new MessageService();
		
		msg.setPostedAt(postedAt3hrs);
		Response response = msgService.postPrivateChatMessage("user1", "user2",msg);
		msg.setPostedAt(postedAt3hrs);
		response = msgService.postPrivateChatMessage("user1", "user3", msg);
		msg.setPostedAt(postedAt1hrs);
		response = msgService.postPrivateChatMessage("user1", "user4", msg);
		msg.setPostedAt(postedAt1hrs);
		response = msgService.postPrivateChatMessage("user1", "user5", msg);

		UserGroupsService tempgroupservice = new UserGroupsService();
		List<UserGroup> testResult = tempgroupservice.loadUnconnectedClusters(2);
		System.out.println("----------------Test 2--------------------");
		System.out.println(testResult.toString());
	}
	// Test Case3
		// boundry value test all 5 will come if all body had chat
	@Test
	public void Test03_Chatboundryvalue() {
		Message msg = new Message();
	//	Timestamp postedAt3hrs = new Timestamp(System.currentTimeMillis() - 3 * 60 * 60 * 1000);
		Timestamp postedAt = new Timestamp(System.currentTimeMillis() - 1 * 60 * 60 * 1000);
		msg.setPostedAt(postedAt);
		msg.setContent("chat msg1");
		MessageService msgService = new MessageService();
		Response response = msgService.postPrivateChatMessage("user1", "user2",msg);
		response = msgService.postPrivateChatMessage("user1", "user3", msg);
		response = msgService.postPrivateChatMessage("user1", "user4", msg);
		response = msgService.postPrivateChatMessage("user1", "user5", msg);
		response = msgService.postPrivateChatMessage("user2", "user3",msg);
		response = msgService.postPrivateChatMessage("user2", "user4", msg);
		response = msgService.postPrivateChatMessage("user2", "user5", msg);
		response = msgService.postPrivateChatMessage("user3", "user4", msg);
		response = msgService.postPrivateChatMessage("user3", "user5", msg);
		response = msgService.postPrivateChatMessage("user4", "user5", msg);
		UserGroupsService tempgroupservice = new UserGroupsService();
		List<UserGroup> testResult = tempgroupservice.loadUnconnectedClusters(2);
		System.out.println("----------------Test 3--------------------");
		System.out.println(testResult.toString());
	}

}
