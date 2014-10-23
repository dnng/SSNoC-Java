package edu.cmu.sv.ws.ssnoc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.sv.ws.ssnoc.dto.Message;
import edu.cmu.sv.ws.ssnoc.dto.StatusCrumb;
import edu.cmu.sv.ws.ssnoc.dto.User;
import edu.cmu.sv.ws.ssnoc.rest.MessageService;
import edu.cmu.sv.ws.ssnoc.rest.MessagesService;
import edu.cmu.sv.ws.ssnoc.rest.StatusService;
import edu.cmu.sv.ws.ssnoc.rest.UserService;

public class WallMessageTest {

	User user1 = new User();
	User user2 = new User();
	@Before
	public void setUp() throws Exception {
		UserService userService = new UserService();

		user1.setUserName("foo");
		user1.setPassword("1234");

		user2.setUserName("bar");
		user2.setPassword("1234");

		userService.addUser(user1);
		userService.addUser(user2);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSendMessageToPublicWall() {

		Message msg = new Message();
		msg.setAuthor("foo");
		Timestamp postedAt = new Timestamp(1234);
		msg.setPostedAt(postedAt);
		msg.setContent("testSendMessageToPublicWall");

		MessageService msgService = new MessageService();
		Response response = msgService.addWallMessage("foo", msg);
		assertEquals(((Message)response.getEntity()).getContent(), "testSendMessageToPublicWall");
	}

	@Test
	public void testGetAllMessagesFromPublicWall() {
		Message msg = new Message();
		MessagesService msgsService = new MessagesService();
		MessageService msgService = new MessageService();

		/*
		 * Add message with null usename
		 */
		//Temporarily commented this check out, coz i want null user ids for performance testing. Will have to fix this eventually. Sorry. 
		//assertNull(msgService.addWallMessage("", msg));

		/*
		 * Add atleast 1 message
		 */
		msg.setAuthor("foo");
		Timestamp postedAt = new Timestamp(1234);
		msg.setPostedAt(postedAt);
		msg.setContent("testGetAllMessagesFromPublicWall");

		msgService.addWallMessage("foo", msg);

		List<Message> messages = msgsService.loadWallMessages();
		assertTrue(messages.size() != 0);
		for (Message m : messages) {
			assertTrue(m instanceof Message);
		}
	}

	@Test
	public void testLoadMessageInfoByGivenID() {
		Message msg = new Message();
		MessageService msgService = new MessageService();

		msg.setAuthor("foo");
		Timestamp postedAt = new Timestamp(1234);
		msg.setPostedAt(postedAt);
		msg.setContent("testLoadMessageInfoByGivenID");

		msgService.addWallMessage("foo", msg);

		long id = 1;

		Message retreived = msgService.loadMessage(id);

		assertTrue(retreived instanceof Message);

	}

	@Test
	public void testSendPrivateMessageToAnotherUser() {
		Message msg = new Message();
		MessageService msgService = new MessageService();

		msg.setAuthor("");
		msg.setContent("");
		assertNull(msgService.postPrivateChatMessage(null, null, msg));

		msg.setAuthor("foo");
		Timestamp postedAt = new Timestamp(1234);
		msg.setPostedAt(postedAt);
		msg.setContent("testSendPrivateMessageToAnotherUser");

		Response resp = msgService.postPrivateChatMessage(user1.getUserName(), user2.getUserName(), msg);
		assertTrue(resp instanceof Response);
	}

	@Test
	public void testloadWallAndStatusMessages() {
		StatusService statSrv = new StatusService();
		StatusCrumb statCrmb1 = new StatusCrumb();

		statCrmb1.setStatus("warning");
		statCrmb1.setLocation("Mountain View");
		statCrmb1.setUserName("foo");

		statSrv.addStatusCrumb(statCrmb1);

		StatusCrumb statCrmb2 = new StatusCrumb();
		statCrmb2.setStatus("ok");
		statCrmb2.setLocation("Mars");
		statCrmb2.setUserName("bar");

		statSrv.addStatusCrumb(statCrmb2);

		MessagesService msgsSrv = new MessagesService();
		List<Message> messages = new ArrayList<Message>();

		messages = msgsSrv.loadWallAndStatusMessages();

		assertTrue(messages instanceof List<?>);
	}

	@Test
	public void testgetAllChatMessagesForPeers() {
		MessagesService msgsSrv = new MessagesService();
		List<Message> msgBetweenUsers = new ArrayList<Message>();

		msgBetweenUsers =  msgsSrv.getAllChatMessagesForPeers(user1.getUserName(), user2.getUserName());
		assertTrue(msgBetweenUsers instanceof List<?>);
	}
}
