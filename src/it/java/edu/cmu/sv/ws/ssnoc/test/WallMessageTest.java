package edu.cmu.sv.ws.ssnoc.test;

import static org.junit.Assert.*;

import java.sql.Timestamp;

import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.cmu.sv.ws.ssnoc.dto.Message;
import edu.cmu.sv.ws.ssnoc.dto.User;
import edu.cmu.sv.ws.ssnoc.rest.MessageService;
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
	public void test() {
		Message msg = new Message();
		msg.setAuthor("foo");
		Timestamp postedAt = new Timestamp(1234);
		msg.setPostedAt(postedAt);
		msg.setContent("test test lloyd owes a me a beer");
		
		MessageService msgService = new MessageService();
		Response response = msgService.addWallMessage("foo", msg);
		assertEquals(((Message)response.getEntity()).getContent(), "test test lloyd owes a me a beer");
	}

}
