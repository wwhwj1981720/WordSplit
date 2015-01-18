package org.chinaunicom.demo.testspeed;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.chinaunicom.demo.fileread.bean.SMS;
import org.chinaunicom.demo.fileread.bean.SpeedBean;
import org.chinaunicom.demo.fileread.bean.TranMessage;
import org.chinaunicom.graph.GraphInterface;
import org.chinaunicom.graph.Graphic;
import org.chinaunicom.match.MatchWordAddGraph;
import org.chinaunicom.thread.DicTion;
import org.chinaunicom.tree.DefineForest;




public class testSpeed {

	/**
	 * @param args
	 */
	long total;
	long totalcount;
	StringBuffer content = new StringBuffer();
	int row = 0;
	List<String> list = new ArrayList();
	SpeedBean bean = new SpeedBean();
	CFile cf;
	String encoding;
	//static String path = "/storm/testSpeed.csv";
	//static String smspath = "/storm/1.txt";
	
	MatchWordAddGraph test=new MatchWordAddGraph();
	//DefineForest dic=new DefineForest();
	DefineForest alldic=new DefineForest();
	GraphInterface gp=new Graphic();

	public testSpeed(long total,String path) {
		super();
		this.total = total;
		this.totalcount = 0;
		this.bean = bean;
		cf = new CFile(path);
		encoding = "utf-8";
		cf.initCsvTitle("second,total,totalcount,v");
	}

	public testSpeed(long total, CFile cf) {
		super();
		this.total = total;
		this.totalcount = 0;
		this.bean = bean;
		this.cf = cf;
		encoding = "utf-8";
		// cf=new CFile(path);

	}

	// 读入total条数据到 list 中
	public void readSms(String f) throws IOException, IOException {

		SMS sms = new SMS();
		TranMessage tran = new TranMessage();

		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(f), encoding));
		int size = 0;
		for (String line = ""; (line = bufferedReader.readLine()) != null;) {
			row++;
			tran.toTranSms(line, sms);
			// content.append(sms.getContent());
			String singleconent = sms.getContent();
			// size=singleconent.getBytes(encoding).length;
			size = singleconent.length();
			totalcount += size;
			list.add(singleconent);
			size = 0;
			if (row > total)
				break;
		}

	}

	/**
	 * @throws IOException
	 */
	public void countSpeed() throws IOException {
		
		//long startTime = 0L;
		int b = 0;
		long startTime = System.currentTimeMillis();
		for (String input : list) {
			
			test.setInput(input);
			gp.setInput(input);
			gp.initV(100);
			test.match(DicTion.coredic,gp);
			gp.compute();
			gp.showPath(input.length()+1,0);
			//System.out.println("end");

			
			
		}
		long endTime = System.currentTimeMillis();
		double seconds = (endTime - startTime) / 1000F;
		double v = totalcount / seconds;
		bean.setTotal(total);
		bean.setTotalcount(totalcount);
		bean.setSecond(seconds);
		bean.setV(v);
		System.out.println(seconds + "	" + total + "	" + totalcount + "	" + v);
		cf.writeRow(bean.getSecond() + "," + bean.getTotal() + ","
				+ bean.getTotalcount() + "," + bean.getV());
		
		// System.out.println(endTime-startTime+" "+seconds+"	");
	}

	

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String path = "D:/SMSCDR/myownsmstestSpeed.csv";
		String smspath = "D:\\SMSCDR\\SMSCDR_20130901\\SMSCDR_201309010.txt";
		System.out.println("start");
		CFile cf = new CFile(path);
		cf.initCsvTitle("second,total,totalcount,v");
		long a[] = { 400, 400000, 200, 200, 1000, 1000, 2000, 3000, 10000,
				20000, 100000, 1000000, 2000000, 3000000, 4000000, 5000000,
				6000000 };

		for (long i : a) {
			testSpeed test = new testSpeed(i, cf);
			test.readSms(smspath);
			test.countSpeed();
		}
		System.out.println("end");

	}

	public testSpeed() {
		super();
	}

}
