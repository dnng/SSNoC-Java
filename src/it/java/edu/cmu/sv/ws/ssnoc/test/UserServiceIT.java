package edu.cmu.sv.ws.ssnoc.test;

import static com.eclipsesource.restfuse.Assert.assertBadRequest;
import static com.eclipsesource.restfuse.Assert.assertOk;
import static org.junit.Assert.assertTrue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.HttpJUnitRunner;
import com.eclipsesource.restfuse.MediaType;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.HttpTest;

import edu.cmu.sv.ws.ssnoc.dto.User;
import edu.cmu.sv.ws.ssnoc.rest.UserService;

@RunWith(HttpJUnitRunner.class)
public class UserServiceIT {

	User user1 = new User();
	User user2 = new User();

	@Before
	public void setUp() throws Exception {
		UserService userService = new UserService();

		user1.setUserName("panda");
		user1.setPassword("1234");
		user1.setPrivilegeLevel("Administrator");
		user1.setAccountStatus("active");

		user2.setUserName("dragon");
		user2.setPassword("1234");
		user1.setPrivilegeLevel("Administrator");
		user1.setAccountStatus("active");

		userService.addUser(user1);
		userService.addUser(user2);
	}

	@Rule
	public Destination destination = new Destination(this,
			"http://localhost:1234/ssnoc/users");

	@Context
	public Response response;

	@HttpTest(method = Method.GET, path = "/")
	public void testUsersFound() {
		assertOk(response);
	}

	@HttpTest(method = Method.POST, path = "/login", type = MediaType.APPLICATION_JSON,
			content = "{\"userName\":\"Surya\",\"password\":\"Kiran\"}")
	public void testInvalidLogin() {
		assertBadRequest(response);
		String messg = response.getBody();
		Assert.assertEquals("Invalid username: Surya", messg);
	}

	@Test
	public void testUpdateUserInformation() {
		User newUser1Name = new User();
		newUser1Name.setUserName("dinosaur");
		UserService usrSrv = new UserService();
		javax.ws.rs.core.Response resp = usrSrv.updateUserRecord(user1, newUser1Name);
		String userNameAfterUpdate = user1.getUserName();
		assertTrue(userNameAfterUpdate, resp instanceof javax.ws.rs.core.Response);
	}
}
