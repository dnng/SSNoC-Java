package edu.cmu.sv.ws.ssnoc.test;

import static org.junit.Assert.*;

import java.sql.Time;
import java.sql.Timestamp;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.sv.ws.ssnoc.dto.Message;
import edu.cmu.sv.ws.ssnoc.dto.User;
import edu.cmu.sv.ws.ssnoc.rest.MessageService;
import edu.cmu.sv.ws.ssnoc.rest.UserGroupsService;
import edu.cmu.sv.ws.ssnoc.rest.UserService;

public class SocialNetworkAnalysisTest {

	@Before
	public void tearDown() throws Exception {
	}

	@Test
	public void test01_checkUserCount() {
		// Add multiple users
		for (int i = 0; i < 5; i++) {
			User user = new User();
			user.setUserName("user" + i);
			user.setPassword("1234");

			UserService userService = new UserService();
			userService.addUser(user);
		}
		
		// assertEquals(5, userClusters.size());
	}
	

	@Test
	public void test02_checkCloseUser(){
		Time time = new Time(0);

		// send messages from user1 to user3 and user5
		// send messages from user5 to user3

		// check user1 is close to user2 and user4
		// Check user3 is close to user2 and user4 as well
		// Check user5 is close to user2 and user4 as well
		// Check user2 is close to user4

		// check user1 is NOT close to user3 and user5
		// check user5 is NOT close to user3

		// Response response = UserGroupsService.add("foo", msg);
		// assertEquals(1, userClusters.size());
		//
		// MessageService msgService = new MessageService();
		// Response response = msgService.addWallMessage("foo", msg);
		// assertEquals(((Message)response.getEntity()).getContent(),
		// "test test lloyd owes a me a beer");
		System.out.println(time.toString());
	}

}
