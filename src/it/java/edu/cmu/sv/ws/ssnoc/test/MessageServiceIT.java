package edu.cmu.sv.ws.ssnoc.test;

import static com.eclipsesource.restfuse.Assert.assertCreated;
import static com.eclipsesource.restfuse.Assert.assertOk;
import static com.eclipsesource.restfuse.Assert.assertBadRequest;

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
public class MessageServiceIT {

    @Rule
    public Destination destination = new Destination(this, "http://localhost:4321/ssnoc");

    @Context
    public Response response;

    @HttpTest(method = Method.POST, path = "/message/SSNAdmin", type = MediaType.APPLICATION_JSON, content = "{\"content\":\"This is a test wall message\",\"postedAt\":\"Nov 11, 2014 10:10:15 AM\"}")
    public void testPostingAMessage() {
        assertCreated(response);
        String msg = response.getBody();
        Assert.assertTrue(msg.contains("This is a test wall message"));
    }

    @HttpTest(method = Method.POST, path = "/message/SSNAdmin/user1", type = MediaType.APPLICATION_JSON, content = "{\"content\":\"This is a test chat message\",\"postedAt\":\"Nov 11, 2014 10:10:15 AM\"}")
    public void testChatAMessage() {
        assertCreated(response);
        String msg = response.getBody();
        Assert.assertTrue(msg.contains("This is a test chat message"));
    }

    @HttpTest(method = Method.POST, path = "/message/SSNAdmin/user999", type = MediaType.APPLICATION_JSON, content = "{\"content\":\"\",\"postedAt\":\"12345\"}")
    public void testChattingWithNonExistantUsers() {
        assertBadRequest(response);
    }

    @HttpTest(method = Method.POST, path = "/message/user999", type = MediaType.APPLICATION_JSON, content = "{\"content\":\"This is a test wall message\",\"postedAt\":\"12345\"}")
    public void testPostingMessageAsANonExistantUser() {
    	assertBadRequest(response);
    }
  ///get//  
    @HttpTest( method = Method.GET, path = "/messages/wall", type = MediaType.APPLICATION_JSON, content = "{\"content\":\"This is a test wall message\",\"postedAt\":\"Nov 11, 2014 10:10:15 AM\"}")
    public void checkWallMessage() {
    assertOk(response);
    String msg = response.getBody();
    Assert.assertTrue(msg.contains("This is a test wall message"));
    }
    
    @HttpTest( method = Method.GET, path = "/messages/SSNAdmin/user1", type = MediaType.APPLICATION_JSON, content = "{\"content\":\"This is a test chat message\",\"postedAt\":\"Nov 11, 2014 10:10:15 AM\"}" )
    public void checkChatMessage() {
    assertOk(response);
    String msg = response.getBody();
    Assert.assertTrue(msg.contains("This is a test chat message"));
    }
    @HttpTest( method = Method.GET, path = "/message/1", type = MediaType.APPLICATION_JSON, content = "{\"content\":\"This is a test wall message\",\"postedAt\":\"Nov 11, 2014 10:10:15 AM\"}")
    public void checkMessageID() {
    assertOk(response);
    String msg = response.getBody();
    Assert.assertTrue(msg.contains("This is a test wall message"));
    }
    @HttpTest( method = Method.GET, path = "/users/SSNAdmin/chatbuddies", type = MediaType.APPLICATION_JSON, content = "{\"author_name\":\"SSNAdmin\",\"target_name\":\"user1\",\"content\":\"This is a test wall message\",\"postedAt\":\"Nov 11, 2014 10:10:15 AM\"}")
    public void checkChatBuddies() {
    assertOk(response);
    String buddies = response.getBody();
    Assert.assertTrue(buddies.contains("user1"));
    }
    
}