package edu.cmu.sv.ws.ssnoc.data.po;

import java.sql.Timestamp;

import com.google.gson.Gson;

/**
 * This is the persistence class to save all chat message information in the system.
 * This contains information like the user's id, his location, his wall message
 * and the creation time information entered by the user when posting a chat message. <br/>
 * Information is saved in SSN_CHAT_MESSAGES table.
 * 
 */
public class MessagePO {
     private long messageId;
     private long authorId;
     private String authorName;
     private long targetId;
     private String targetName;
     private long locationId;
     private String location;
     private String content;
     private String messageType;
     private Timestamp createdAt;

     public long getMessageId() {
          return messageId;
     }

     public void setMessageId(long messageId) {
          messageId = messageId;
     }

     public long getauthorId() {
          return authorId;
     }

     public void setauthorId(long authorId) {
          this.authorId = authorId;
     }

     public String getauthorName() {
          return authorName;
     }


     public void setauthorName(String string) {
          authorName = string;
     }

     public long gettargetId() {
          return targetId;
     }

     public void settargetId(long targetId) {
          this.targetId = targetId;
     }

     public String gettargetName() {
          return targetName;
     }

     public void settargetName(String targetName) {
          targetName = targetName;
     }

     public String getContent() {
          return content;
     }

     public void setContent(String content) {
          this.content = content;
     }

     public long getlocationId() {
          return locationId;
     }
     public void setlocationId(long locationId) {
          this.locationId = locationId;
     }

     public String getLocation() {
          return location;
     }

     public void setLocation(String location) {
          this.location = location;
     }

     public String getmessageType() {
          return messageType;
     }

     public void setmessageType(String messageType) {
          this.messageType = messageType;
     }

     public Timestamp getCreatedAt() {
          return createdAt;
     }

     public void setCreatedAt(Timestamp createdAt) {
          this.createdAt = createdAt;
     }

     @Override
     public String toString() {
          return new Gson().toJson(this);
     }

}