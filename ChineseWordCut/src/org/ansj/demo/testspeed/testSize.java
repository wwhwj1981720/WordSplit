package org.ansj.demo.testspeed;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class testSize extends testSpeed {

	/**
	 * @param args
	 * @return 
	 * @throws UnsupportedEncodingException 
	 */
	
	public static void reset(String repath)
	{
		testSpeed.smspath=repath;
	}
	public testSize(long total) {
		super(total);
		// TODO Auto-generated constructor stub
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String str="中华人民共和国";
		
		byte a[]=str.getBytes("gbk");
		System.out.println(java.nio.charset.Charset.defaultCharset().toString()+a.length);
//		testSpeed test=new testSpeed(1000);
//		test.readSms(smspath);
		testSize test=new testSize(2);
		test.readSms("d:/SMSCDR/SMSCDR_201309010.txt");
	}

}
