package Logistics.DTO;

import java.sql.Date;


public class MessageDTO {
	public static enum Type{
		news("新闻信息"),announcement("公告通知"),logisticsNews("行业动态"),knowledge("知识管理"),culture("文化天地"),actionNews("业务动态");
		public final String name;
		private Type(String name){
			this.name=name;
		}
		@Override
		public String toString() {
			return name;
		}
		
	}
	private Integer messageID;
	private String header;
	private String body;
	private Date datePosted;
	private String type;
	private String attachment;
	private String originName;
	private Type msgType;
	
	public Type getMsgType() {
		return msgType;
	}
	public void setMsgType(Type msgType) {
		this.msgType = msgType;
	}
	public Integer getMessageID() {
		return messageID;
	}
	public void setMessageID(Integer messageID) {
		this.messageID = messageID;
	}
	public String getHeader() {
		return header;
	}
	public void setHeader(String header) {
		this.header = header;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Date getDatePosted() {
		return datePosted;
	}
	public void setDatePosted(Date datePosted) {
		this.datePosted = datePosted;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public String getOriginName() {
		return originName;
	}
	public void setOriginName(String originName) {
		this.originName = originName;
	}
	

}
