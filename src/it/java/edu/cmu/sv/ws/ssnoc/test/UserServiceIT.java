package edu.cmu.sv.ws.ssnoc.test;

import static com.eclipsesource.restfuse.Assert.assertBadRequest;
import static com.eclipsesource.restfuse.Assert.assertOk;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.runner.RunWith;

import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.HttpJUnitRunner;
import com.eclipsesource.restfuse.MediaType;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.HttpTest;

@RunWith(HttpJUnitRunner.class)
public class UserServiceIT {
	@Rule
	public Destination destination = new Destination(this,
			"http://localhost:4321/ssnoc");

	@Context
	public Response response;

	@HttpTest(method = Method.GET, path = "/users/")
	public void testUsersFound() {
		assertOk(response);
	}

	@HttpTest(method = Method.POST, path = "/user/SSNAdmin/authenticate", type = MediaType.APPLICATION_JSON, 
			content = "{\"userName\":\"SSNAdmin\",\"password\":\"admin\"}")
	public void testValidLogin() {
		assertOk(response);
	}
	
	@HttpTest(method = Method.POST, path = "/user/barney/authenticate", type = MediaType.APPLICATION_JSON, 
			content = "{\"userName\":\"barney\",\"password\":\"barney\"}")
	public void testInvalidLogin() {
		assertBadRequest(response);
		String messg = response.getBody();
		Assert.assertEquals("Invalid username: barney", messg);
	}
}