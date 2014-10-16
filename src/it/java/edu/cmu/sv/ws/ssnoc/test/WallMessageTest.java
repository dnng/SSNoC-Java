package edu.cmu.sv.ws.ssnoc.test;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;

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
		List<Message> messages = (List<Message>) msgsService.loadWallMessages();
		for (Message m : messages) {
			assertTrue(m instanceof Message);
		}
	}
}
