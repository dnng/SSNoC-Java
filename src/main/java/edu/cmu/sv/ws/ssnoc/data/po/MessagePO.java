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
     private String imagePath;
     private String videoPath;

     public long getMessageId() {
          return messageId;
     }

     public void setMessageId(long messageId) {
          this.messageId = messageId;
     }

     public long getAuthorId() {
          return authorId;
     }

     public void setAuthorId(long authorId) {
          this.authorId = authorId;
     }

     public String getAuthorName() {
          return authorName;
     }


     public void setAuthorName(String authorName) {
          this.authorName = authorName;
     }

     public long getTargetId() {
          return targetId;
     }

     public void setTargetId(long targetId) {
          this.targetId = targetId;
     }

     public String getTargetName() {
          return targetName;
     }

     public void setTargetName(String targetName) {
          this.targetName = targetName;
     }

     public String getContent() {
          return content;
     }

     public void setContent(String content) {
          this.content = content;
     }

     public long getLocationId() {
          return locationId;
     }
     public void setLocationId(long locationId) {
          this.locationId = locationId;
     }

     public String getLocation() {
          return location;
     }

     public void setLocation(String location) {
          this.location = location;
     }

     public String getMessageType() {
          return messageType;
     }

     public void setMessageType(String messageType) {
          this.messageType = messageType;
     }

     public Timestamp getCreatedAt() {
          return createdAt;
     }

     public void setCreatedAt(Timestamp createdAt) {
          this.createdAt = createdAt;
     }

     public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    @Override
     public String toString() {
          return new Gson().toJson(this);
     }

}