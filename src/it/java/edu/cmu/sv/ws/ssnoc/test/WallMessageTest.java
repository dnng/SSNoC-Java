package edu.cmu.sv.ws.ssnoc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Timestamp;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.sv.ws.ssnoc.dto.Message;
import edu.cmu.sv.ws.ssnoc.dto.User;
import edu.cmu.sv.ws.ssnoc.rest.MessageService;
import edu.cmu.sv.ws.ssnoc.rest.MessagesService;
import edu.cmu.sv.ws.ssnoc.rest.UserService;

public class WallMessageTest {

	@Before
	public void setUp() throws Exception {
		User user = new User();
		user.setUserName("foo");
		user.setPassword("1234");
		
		UserService userService = new UserService();
		userService.addUser(user);
		
		//Message msg = new Message();
		
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
		msg.setContent("test test lloyd owes a me a beer");
		
		MessageService msgService = new MessageService();
		Response response = msgService.addWallMessage("foo", msg);
		assertEquals(((Message)response.getEntity()).getContent(), "test test lloyd owes a me a beer");
	}

	@Test
	public void testGetAllMessagesFromPublicWall() {
		Message msg = new Message();
		MessagesService msgsService = new MessagesService();
		MessageService msgService = new MessageService();
		
		//Add atleast 1 message
		msg.setAuthor("foo");
		Timestamp postedAt = new Timestamp(1234);
		msg.setPostedAt(postedAt);
		msg.setContent("test test lloyd owes a me a beer");
		
		Response response = msgService.addWallMessage("foo", msg);
		
		List<Message> messages = (List<Message>) msgsService.loadWallMessages();
		assertTrue(messages.size() != 0);
		for (Message m : messages) {
			assertTrue(m instanceof Message);
		}
	}
}
