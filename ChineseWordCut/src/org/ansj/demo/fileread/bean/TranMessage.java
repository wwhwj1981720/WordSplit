package org.ansj.demo.fileread.bean;


import org.ansj.demo.fileread.bean.Message;

public class TranMessage {
	public void toTranMessage(String line,Message meg) {
		
		this.line = line;
		this.meg=meg;
		String[] str =null;
		if(line!=null)
		{
			str=line.split("\\|");
			if (str.length == 6) {
				this.meg.setId(str[0]);
				this.meg.setCid(str[1]);
				this.meg.setTime(str[2]);
				this.meg.setPum(str[3]);
				this.meg.setDum(str[4]);
				this.meg.setContent(str[5]);
				//System.out.println(str[5]);
			}	
		}
		

	}
public void toTranSms(String line,SMS sms) {
		
		this.line = line;
		this.sms=sms;
		String[] str =null;
		if(line!=null)
		{
			str=line.split("\\|");
			if (str.length == 6) {
				this.sms.setOc(str[0]);
				this.sms.setDc(str[1]);
				this.sms.setCdate(str[2]);
				this.sms.setPnum(str[3]);
				this.sms.setDnum(str[4]);
				this.sms.setContent(str[5]);
				//System.out.println(str[5]);
			}	
		}
		

	}
	Message meg;
	SMS sms;
	public SMS getSms() {
		return sms;
	}
	public void setSms(SMS sms) {
		this.sms = sms;
	}
	public Message getMeg() {
		return meg;
	}
	public void setMeg(Message meg) {
		this.meg = meg;
	}
	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	String  line;

	

}
