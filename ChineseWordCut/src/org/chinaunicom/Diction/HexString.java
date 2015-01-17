package org.chinaunicom.Diction;

import java.util.List;
import java.util.Locale;




public class HexString {
	public static String byteToHex(byte src)
	{
		String  hex=null;
		StringBuilder stringBuilder = new StringBuilder("");  
		int v = src & 0xFF;  
		String hv = Integer.toHexString(v);  
		if (hv.length() < 2) 
		{  
		      stringBuilder.append(0);  
		 }  
		     stringBuilder.append(hv);  
		   hex=stringBuilder.toString();
		   // System.out.println(hexString);
		    return hex;  
	}
	public static  String bytesToHexString(byte[] src){  
	    StringBuilder stringBuilder = new StringBuilder("");  
	    if (src == null || src.length <= 0) {  
	        return null;  
	    }  
	    for (int i = 0; i < src.length; i++) {  
	        int v = src[i] & 0xFF;  
	        String hv = Integer.toHexString(v);  
	        if (hv.length() < 2) {  
	            stringBuilder.append(0);  
	        }  
	        stringBuilder.append(hv);  
	    }  
	    String hexString=stringBuilder.toString();
	    return hexString;  
	}
	// byte={0x12,0x23}  转为 String "1223"
	public static  String bytesToHexString(byte[] src,int start,int end){  
	    StringBuilder stringBuilder = new StringBuilder("");  
	    int length=src.length;
	    if (src == null || src.length <= 0||(end>=length)) {  
	        return null;  
	    }  
	    for (int i = start; i <= end; i++) {  
	        int v = src[i] & 0xFF;  
	        String hv = Integer.toHexString(v);  
	        if (hv.length() < 2) {  
	            stringBuilder.append(0);  
	        }  
	        stringBuilder.append(hv);  
	    }  
	    String hexString=stringBuilder.toString();
	    return hexString;  
	}
	//byte 对英的 ascii 3gnet
	public static String ExplainStartEndToString(byte[]src,int start,int end)
	{
		StringBuilder stringBuilder = new StringBuilder(""); 
		int length=src.length;
	    if (src == null || length <= 0||(end>=length)) {  
	        return null;  
	    } 
	    
	    for (int i = start; i <=end; i++) {  
	    	 int v = src[i] & 0xFF;  
		     char hv = (char)(v); 
	        stringBuilder.append(hv);  
	    }  
	    String hexString=stringBuilder.toString();
	  
	    return hexString;  
	} 
	//解析为IP 地址
	public static String IPString(byte[]src,int start,int end)
	{
		StringBuilder stringBuilder = new StringBuilder(""); 
		int ipstart=end-3;
		int value=0;
		String str=null;
		for(int i=ipstart;i<=end;i++)
		{
			value=src[i];
			if(value<0) value+=256;
			str=String.format("%d", value);
			stringBuilder.append(str);
			stringBuilder.append(".");
		}
		int len=stringBuilder.length()-1;
		stringBuilder.deleteCharAt(len);
		String hexString=stringBuilder.toString();
		return hexString;
	}
	//计算某位置的 int值 计算 tlv 的 Lengh
	public static int ComputeTagLengh(byte[]src,int start)
	{
		int taglen=0;
		taglen=(int)src[start];
		return taglen;
	}
	public static int ComputeTwoTagLengh(byte[]src,int start,int end)
	{
		int taglen=0;
		taglen=bytesToInterger(src,start,end);
		//taglen=(int)src[start];
		return taglen;
	}
	public static int ComputeThreeTagLengh(byte[]src,int start,int end)
	{
		int taglen=0;
		taglen=bytesToInterger(src,start,end);
		//taglen=(int)src[start];
		return taglen;
	}
	//计算一个字节to 整形
	public static int byteToInteger(byte src)
	{
		int taglen=0;
		taglen=(int)src;
		return taglen;
	}
	private static char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
	//字节序高低颠倒
	public static String byteToOctetString(byte [] bytes,int start,int end)
	{
	    StringBuffer sb=new StringBuffer();
	    
		int len=end-start+1;
		if(end>=bytes.length) return null; 
		//char[] chars=null;
//		try{
//		 chars= new char[len * 2];
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
		int k=0;
	     for (int i = start; i <= end; i++)
	     {
	    	 byte c=0;
	    	 try{ 
	    	  c=(byte) bytes[i];
	         }
	         catch(Exception e)
	         {
	        	 e.printStackTrace();
	         }
	           //int b=c;
	           byte high=(byte)((c>>4)&0x0f);
	           byte low=(byte)(c&0xf);
	           int h=high;
	           int l=low;
	           k=i-start;
	           try
	           {
	        	  // chars[k * 2] = hexDigits[low];
		           sb.append(hexDigits[low]);
	        	   if(high!=0x0f) 
		           {
		        	  // chars[k * 2+1] = hexDigits[high];
		        	   sb.append(hexDigits[high]);
		           }
		           else 
		           {
		        	   //chars[k * 2+1]=' ';
		           }
	           }
	           catch(Exception e)
	           {
	        	   e.printStackTrace();
	           }
	           
	           //chars[k * 2] = hexDigits[low];
	           //sb.append(hexDigits[low]);
	      }
	    
	        
	     	//return new String(chars);
	     return sb.toString();
	   } 
	// String 转化为  byte[]
	public static byte[] hexStr2Bytes(String src){
    	/*对输入值进行规范化整理*/
    	src = src.trim().replace(" ", "").toUpperCase(Locale.US);
    	//处理值初始化
    	int m=0,n=0;
        int iLen=src.length()/2; //计算长度
        byte[] ret = new byte[iLen]; //分配存储空间
        
        for (int i = 0; i < iLen; i++){
            m=i*2+1;
            n=m+1;
            ret[i] = (byte)(Integer.decode("0x"+ src.substring(i*2, m) + src.substring(m,n)) & 0xFF);
        }
        return ret;
    }
	

	public static  String bytesToIntergerString(byte[] src,int start,int end){  
	     
	    int length=src.length;
	    if (src == null || src.length <= 0||(end>=length)) {  
	        return null;  
	    }
	    int result=0;
	    for (int i = start; i <= end; i++) {  
	        int v = src[i] & 0xFF;  
	        result=(result<<8);
	        result+=v;
	    }  
	    String hexString=String.format("%d",result);
	    return hexString;  
	}
	//将字节数组转为对应的整形    例如 duration 0x2131  转化为对应的整数
	public static  int bytesToInterger(byte[] src,int start,int end){  
	     
	    int length=src.length;
	    if (src == null || src.length <= 0||(end>=length)) {  
	        return 0;  
	    }
	    int result=0;
	    for (int i = start; i <= end; i++) {  
	        int v = src[i] & 0xFF;  
	        result=(result<<8);
	        result+=v;
	    }  
	   
	    return result;  
	}
	//tlv 套接 tlv 模式
	
	public static void main(String args[])
	{
		byte a=-76;
		String b=byteToHex(a);
		System.out.println(b);
	}
//	public List <TrafficDataVolumes> bytesToSquenceString(byte [] src,int start,int end)
//	{
//		StringBuilder stringBuilder = new StringBuilder(""); 
//		int length=src.length;
//	    if (src == null || length <= 0||(end>=length)) {  
//	        return null;  
//	    } 
//	    
//	    for (int i = start; i <=end; i++) {  
//	    	 int v = src[i] & 0xFF;  
//		     char hv = (char)(v); 
//	        stringBuilder.append(hv);  
//	    }  
//	    String hexString=stringBuilder.toString();
//	  
//	    return hexString;  
//	}
//	public byteTotrafficDataVolume(byte src,int start;int end)
//	{
//		
//	}
}
