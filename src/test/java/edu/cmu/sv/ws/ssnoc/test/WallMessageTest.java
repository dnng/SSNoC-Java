package edu.cmu.sv.ws.ssnoc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.util.ConnectionPoolFactory;
import edu.cmu.sv.ws.ssnoc.data.util.DBUtils;
import edu.cmu.sv.ws.ssnoc.data.util.IConnectionPool;
import edu.cmu.sv.ws.ssnoc.dto.Message;
import edu.cmu.sv.ws.ssnoc.dto.StatusCrumb;
import edu.cmu.sv.ws.ssnoc.dto.User;
import edu.cmu.sv.ws.ssnoc.rest.MessageService;
import edu.cmu.sv.ws.ssnoc.rest.MessagesService;
import edu.cmu.sv.ws.ssnoc.rest.StatusService;
import edu.cmu.sv.ws.ssnoc.rest.UserService;

public class WallMessageTest {

	static User user1 = new User();
	static User user2 = new User();
	
	@BeforeClass
	public static void setUp() throws Exception {
		
		try {
			IConnectionPool cp = ConnectionPoolFactory.getInstance()
					.getH2ConnectionPool();
			cp.switchConnectionToTest();
			
			DBUtils.reinitializeDatabase();
			
			UserService userService = new UserService();

			user1.setUserName("foo");
			user1.setPassword("1234");

			user2.setUserName("bar");
			user2.setPassword("1234");

			userService.addUser(user1);
			userService.addUser(user2);

		} catch (Exception e) {
			Log.error(e);
		} finally {
			Log.exit();
		}

	}

	@AfterClass
	public static void tearDown() throws Exception {
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
	public void testGetAllVisibleMessagesFromPublicWall() {
		Message msg = new Message();
		MessagesService msgsService = new MessagesService();
		MessageService msgService = new MessageService();

		/*
		 * Add atleast 1 message
		 */
		msg.setAuthor("foo");
		Timestamp postedAt = new Timestamp(1234);
		msg.setPostedAt(postedAt);
		msg.setContent("testGetAllMessagesFromPublicWall");

		msgService.addWallMessage("foo", msg);

		List<Message> visibleMessages = new ArrayList<Message>();
		visibleMessages = msgsService.loadVisibleWallMessages();
		assertTrue(visibleMessages.size() != 0);
		for (Message m : visibleMessages) {
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

		int id = 1;

		Message retreived = msgService.loadMessage(id);

		assertTrue(retreived instanceof Message);

	}

	@Test
	public void testSendPrivateMessageToAnotherUser() {
		Message msg = new Message();
		MessageService msgService = new MessageService();

		msg.setAuthor("");
		msg.setContent("");
		assertTrue(msgService.postPrivateChatMessage(null, null, msg).toString().contains("400"));

		msg.setAuthor("SSNAdmin");
		Timestamp postedAt = new Timestamp(1234);
		msg.setPostedAt(postedAt);
		msg.setContent("testSendPrivateMessageToAnotherUser");

		Response resp = msgService.postPrivateChatMessage(user1.getUserName(), user2.getUserName(), msg);
		assertTrue(resp instanceof Response);
	}

	@Test
	public void testLoadWallAndStatusMessages() {
		StatusService statSrv = new StatusService();
		StatusCrumb statCrmb1 = new StatusCrumb();

		statCrmb1.setStatus("warning");
		statCrmb1.setLocation("Mountain View");
		statCrmb1.setUserName("foo");

		statSrv.addStatusCrumb(statCrmb1.getUserName(), statCrmb1);

		StatusCrumb statCrmb2 = new StatusCrumb();
		statCrmb2.setStatus("ok");
		statCrmb2.setLocation("Mars");
		statCrmb2.setUserName("bar");

		statSrv.addStatusCrumb(statCrmb2.getUserName(), statCrmb2);

		MessagesService msgsSrv = new MessagesService();
		List<Message> messages = new ArrayList<Message>();

		messages = msgsSrv.loadAllMessages();

		assertTrue(messages instanceof List<?>);
	}

	@Test
	public void testGetAllChatMessagesForPeers() {
		MessagesService msgsSrv = new MessagesService();
		List<Message> msgBetweenUsers = new ArrayList<Message>();

		msgBetweenUsers =  msgsSrv.getAllChatMessagesForPeers(user1.getUserName(), user2.getUserName());
		assertTrue(msgBetweenUsers instanceof List<?>);

		List<Message> visibleMsgBetweenUsers = new ArrayList<Message>();
		visibleMsgBetweenUsers = msgsSrv.getAllVisibleChatMessagesForPeers(user1.getUserName(), user2.getUserName());
		assertTrue(visibleMsgBetweenUsers instanceof List<?>);
	}

	@Test
	public void testLoadingAnnouncementMessages() {
		MessageService msgSrv = new MessageService();
		MessagesService msgsSrv = new MessagesService();
		List<Message> announcementMessages = new ArrayList<Message>();

		Message msg = new Message();
		msg.setAuthor("");
		msg.setContent("");
		msg.setAuthor("bar");
		Timestamp postedAt = new Timestamp(4321);
		msg.setPostedAt(postedAt);
		msg.setContent("testLoadingAnnouncementMessages");

		/*
		 * Add at least one announcement
		 */
		msgSrv.addAnnouncementMessage(msg);

		announcementMessages = msgsSrv.loadAnnouncementMessages();
		assertTrue(announcementMessages instanceof List<?>);
	}

	@Test
	public void testLoadingVisibleAnnouncementMessages() {

		MessagesService msgsSrv = new MessagesService();
		List<Message> visibleannouncementMessages = new ArrayList<Message>();

		/*
		 * Add at least one announcement
		 */
		Message msg = new Message();
		msg.setAuthor("");
		msg.setContent("");
		msg.setAuthor("foo");
		Timestamp postedAt = new Timestamp(4321);
		msg.setPostedAt(postedAt);
		msg.setContent("testLoadingVisibleAnnouncementMessages");


		visibleannouncementMessages = msgsSrv.loadVisibleAnnouncementMessages();
		assertTrue(visibleannouncementMessages instanceof List<?>);
	}
}
