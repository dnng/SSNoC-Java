package edu.cmu.sv.ws.ssnoc.test;

import static com.eclipsesource.restfuse.Assert.assertNotFound;
import static com.eclipsesource.restfuse.Assert.assertOk;
import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Rule;
import org.junit.runner.RunWith;

import com.eclipsesource.restfuse.Destination;
import com.eclipsesource.restfuse.HttpJUnitRunner;
import com.eclipsesource.restfuse.Method;
import com.eclipsesource.restfuse.Response;
import com.eclipsesource.restfuse.annotation.Context;
import com.eclipsesource.restfuse.annotation.HttpTest;

@RunWith(HttpJUnitRunner.class)
public class UserGroupsServiceIT {

	///usergroups/unconnected/timeWindowInMinutes
	@Rule
	public Destination destination = new Destination(this, "http://localhost:4321/ssnoc/usergroups/unconnected");

	@Context
	public Response response;

	@HttpTest( method = Method.GET, path = "/1" )
	public void checkRestfuseOnlineStatus() {
		assertOk(response);
	}  


	@HttpTest(method = Method.GET, path = "/1")
	public void testUserGroupsFound() {
		assertOk(response);
	}

	@HttpTest(method = Method.GET, path = "/a")
	public void testInvalidParam() {
		assertNotFound(response);			
	}
	
	@HttpTest(method = Method.GET, path = "/1")
	public void testuserGroupsListsUsers() {
		String message = response.getBody();
		assertTrue(message, message.equals("[{\"userNames\":[\"SSNAdmin\"]}]"));
	}
	
}


