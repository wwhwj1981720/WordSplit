package thread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.chinaunicom.tree.DefineForest;




import tools.Dir;

public class DicTion {
	
	public static String scdrhead="recordType,imsi,imei,sgsnip,msNetworkCapability,routingArea,lac,cellid,chargingID,ggsnip,apn,pdpType,pdpip,uplink,downlink,linkdate,updownlinktime,recordOpeningTime,duration,causeForRecClosing,recSequenceNumList,nodeID,localSequenceNumberList,apnSelectionMode,accessPointName,msisdn,chargingCharacteristics,rATType,chChSelectionMode,dynamicAddressFlag,sgsnPLMNIdentifier,consolidationResult,filename";
	public static String sgwhead="recordType,imsi,imei,sgwip,servingNodeAddress,routingArea,lac,cellid,chargingID,servedPDPPDNAddress,apn,pdpPDNType,pdpip,uplink,downlink,linkdate,updownlinktime,recordOpeningTime,duration,causeForRecClosing,recSequenceNumList,nodeID,localSequenceNumberList,apnSelectionMode,accessPointName,msisdn,chargingCharacteristics,rATType,chChSelectionMode,dynamicAddressFlag,sgsnPLMNIdentifier,servedIMEISV,mSTimeZone,userLocationInformation,cAMELChargingInformation,servingNodeType,pGWAddressUsed,pGWPLMNIdentifier,startTime,stopTime,pDNConnectionChargingID,filename";
	public static String pgwhead="recordType,imsi,imei,sgwip,servingNodeAddress,routingArea,lac,cellid,chargingID,servedPDPPDNAddress,apn,pdpPDNType,pdpip,uplink,downlink,linkdate,updownlinktime,recordOpeningTime,duration,causeForRecClosing,recSequenceNumList,nodeID,localSequenceNumberList,apnSelectionMode,accessPointName,msisdn,chargingCharacteristics,rATType,chChSelectionMode,dynamicAddressFlag,sgsnPLMNIdentifier,servedIMEISV,mSTimeZone,userLocationInformation,cAMELChargingInformation,servingNodeType,pGWAddressUsed,pGWPLMNIdentifier,startTime,stopTime,pDNConnectionChargingID,filename";
	public static String egcdrhead="recordType,imsi,imei,sgwip,servingNodeAddress,routingArea,lac,cellid,chargingID,servedPDPPDNAddress,apn,pdpPDNType,pdpip,uplink,downlink,linkdate,updownlinktime,recordOpeningTime,duration,causeForRecClosing,recSequenceNumList,nodeID,localSequenceNumberList,apnSelectionMode,accessPointName,msisdn,chargingCharacteristics,rATType,chChSelectionMode,dynamicAddressFlag,sgsnPLMNIdentifier,servedIMEISV,mSTimeZone,userLocationInformation,filename";
	public static String suffix=".gz"; 
	public static int fieldsize=0;
	public static int scdrfieldsize=32;
	public static int zxscdrfieldsize=32;
	public static int sgwrfieldsize=44;
	public static String movedirstr="";//需要移动到的目录；
	public static String outdir=null;//解码生成的decode文件的目录；
	public static boolean isstop=false;
	public static Dir movedir=null;
	public static DefineForest coredic=new DefineForest();
	public static Map<String,Integer> bigramEntryMap=new HashMap<String,Integer>();
	
	
	static
	{
		initCoreDic(coredic);
		initBigramTables();
	}
	public static void initCoreDic(DefineForest dic)
	{
		readTag("core",dic);
	}
	public static void readTagDistinctTLVTV(String name,DefineForest dic)
	{
		InputStream in;
		BufferedReader bf;
		in=DicTion.class.getResourceAsStream("/"+name+".txt");
		bf=new BufferedReader(new InputStreamReader(in));
		String line=null;
		char []tagb=null;
		int way=0;
		int tlv=0;
		int pos=0;
		try {
			for(;(line=bf.readLine())!=null;)
			{
				String []tag=line.split("\\s+");
				tagb=tag[0].toCharArray();
				way=Integer.parseInt(tag[1]);
				tlv=Integer.parseInt(tag[2]);
				pos=Integer.parseInt(tag[3]);
				dic.insertTag(tagb,way,tlv,pos);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void readTag(String name,DefineForest dic)
	{
		InputStream in;
		BufferedReader bf;
		in=DicTion.class.getResourceAsStream("/"+name+".dic");
		bf=new BufferedReader(new InputStreamReader(in));
		String line=null;
		char []tagb=null;
		int way=0;
		int pos=0;
		int count=0;
		try {
			for(;(line=bf.readLine())!=null;)
			{
				count++;
				if(count==1) continue;
				String []tag=line.split("\\s+");
				tagb=tag[1].toCharArray();
				way=Integer.parseInt(tag[0]);
				try {
					pos=Integer.parseInt(tag[3]);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				dic.insertTag(tagb,way,0,pos);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void initBigramTables() {
		BufferedReader reader = null;
		InputStream in;
		String name="bigramdict.dic";
		in=DicTion.class.getResourceAsStream("/"+name);
		try {
			reader = new BufferedReader(new InputStreamReader(in,"utf-8"));
			String temp = null;
			String[] strs = null;
			int freq = 0;
			StringBuffer fromto=new StringBuffer();
			while ((temp = reader.readLine()) != null) {
				if (temp!=null||temp.equals("")) {
					continue;
				}
				strs = temp.split("\t");
				freq = Integer.parseInt(strs[1]);
				strs = strs[0].split("@");
				//fromto=strs[0]+"|"+strs[1];
				fromto.append(strs[0]);
				fromto.append("|");
				fromto.append(strs[1]);
				bigramEntryMap.put(fromto.toString(), freq) ;
				fromto.delete(0, fromto.length());

			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
}
