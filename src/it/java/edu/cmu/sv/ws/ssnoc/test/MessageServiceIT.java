package edu.cmu.sv.ws.ssnoc.test;

import static com.eclipsesource.restfuse.Assert.assertBadRequest;
import static com.eclipsesource.restfuse.Assert.assertCreated;
import static com.eclipsesource.restfuse.Assert.assertOk;

import java.sql.Timestamp;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.runner.RunWith;

import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.HttpJUnitRunner;
import com.eclipsesource.restfuse.MediaType;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.HttpTest;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.util.ConnectionPoolFactory;
import edu.cmu.sv.ws.ssnoc.data.util.DBUtils;
import edu.cmu.sv.ws.ssnoc.data.util.IConnectionPool;
import edu.cmu.sv.ws.ssnoc.dto.Message;
import edu.cmu.sv.ws.ssnoc.dto.User;
import edu.cmu.sv.ws.ssnoc.rest.MessageService;
import edu.cmu.sv.ws.ssnoc.rest.UserService;

@RunWith(HttpJUnitRunner.class)
public class MessageServiceIT {

	@Rule
	public Destination destination = new Destination(this,
			"http://localhost:4321/ssnoc");

	@Context
	public Response response;
	
	@BeforeClass
	public static void initialSetUp() throws Exception {
		
		try {
			IConnectionPool cp = ConnectionPoolFactory.getInstance()
					.getH2ConnectionPool();
			cp.switchConnectionToTest();
			
			DBUtils.reinitializeDatabase();
			
			UserService userService = new UserService();

			User testuser = new User();
			testuser.setUserName("testuser");
			testuser.setPassword("testuser");

			userService.addUser(testuser);
			System.out.println(">>>>>>>Created testuser<<<<<<");
			
			Message msg = new Message();
			MessageService msgService = new MessageService();

			msg.setAuthor("SSNAdmin");
			Timestamp postedAt = new Timestamp(1234);
			msg.setPostedAt(postedAt);
			msg.setContent("testSendPrivateMessageToAnotherUser");

			msgService.postPrivateChatMessage("SSNAdmin", "testuser", msg);
			System.out.println(">>>>>>>Sent private message from SSNAdmin to testuser<<<<<<");

		} catch (Exception e) {
			Log.error(e);
		} finally {
			Log.exit();
		}
	}
	
	@AfterClass
	public static void finalTearDown() throws Exception {
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

	@HttpTest(method = Method.POST, path = "/message/SSNAdmin", type = MediaType.APPLICATION_JSON, content = "{\"content\":\"This is a test wall message\",\"postedAt\":\"Nov 11, 2014 10:10:15 AM\"}")
	public void testPostingAMessage() {
		assertCreated(response);
		String msg = response.getBody();
		Assert.assertTrue(msg.contains("This is a test wall message"));
	}

//	@HttpTest(method = Method.POST, path = "/message/SSNAdmin/testuser", type = MediaType.APPLICATION_JSON, content = "{\"content\":\"This is a test chat message\",\"postedAt\":\"Nov 11, 2014 10:10:15 AM\"}")
//	public void testChatMessage() {
//		assertCreated(response);
//		String msg = response.getBody();
//		Assert.assertTrue(msg.contains("This is a test chat message"));
//	}

	@HttpTest(method = Method.POST, path = "/message/SSNAdmin/invaliduser", type = MediaType.APPLICATION_JSON, content = "{\"content\":\"\",\"postedAt\":\"Nov 11, 2014 10:10:15 AM\"}")
	public void testChattingWithNonExistantUsers() {
		assertBadRequest(response);
	}

	@HttpTest(method = Method.POST, path = "/message/invaliduser", type = MediaType.APPLICATION_JSON, content = "{\"content\":\"This is a test wall message\",\"postedAt\":\"Nov 11, 2014 10:10:15 AM\"}")
	public void testPostingMessageAsANonExistantUser() {
		assertBadRequest(response);
	}

	// /get//
	@HttpTest(method = Method.GET, path = "/messages/wall")
	public void checkWallMessage() {
		assertOk(response);
		String msg = response.getBody();
		Assert.assertTrue(msg.contains("This is a test wall message"));
	}

//	@HttpTest(method = Method.GET, path = "/messages/SSNAdmin/testuser")
//	public void checkChatMessage() {
//		assertOk(response);
//		String msg = response.getBody();
//		Assert.assertTrue(msg.contains("This is a test chat message"));
//	}

	@HttpTest(method = Method.GET, path = "/message/1")
	public void checkMessageID() {
		assertOk(response);
	}

//	@HttpTest(method = Method.GET, path = "/users/SSNAdmin/chatbuddies")
//	public void checkChatBuddies() {
//		assertOk(response);
//		String buddies = response.getBody();
//		Assert.assertTrue(buddies.contains("testuser"));
//	}

}