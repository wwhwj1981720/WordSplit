package org.chinaunicom.match;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.zip.GZIPInputStream;

import org.chinaunicom.graph.CostD;
import org.chinaunicom.graph.Graph;
import org.chinaunicom.graph.GraphInterface;
import org.chinaunicom.graph.GraphMore;
import org.chinaunicom.graph.Graphic;
import org.chinaunicom.thread.DicTion;
import org.chinaunicom.tree.DefineForest;
import org.chinaunicom.tree.Node;





/**
 * @param <T>
 * @param args
 *  param input 输入的中文String 
 *  
 *  param b     要进行分词的中文词组
 *  param filesize 中文词语的大小
 *  param  hexString 文件对应的16进制String
 */
public class MatchWord {
	
	public String input=null;
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
		this.b=input.toCharArray();
		filesize=input.length();
		seq=this.filesize+1;
	}
	public char b[]=null;
	protected int filesize;
	int seq=0;//记录末端点 end 的编号
	public char [] getB() {
		return b;
	}
	public void setB(char[] b) {
		this.b = b;
	}
	/*
	 * 
	 * //按照顺序匹配将每个词都进行匹配
	 * 
	 * */
	public void match(DefineForest forest)
	{
		if(b==null) return;
		
		int len=filesize;
		int i=0;
		int p=0;//移动指针
		int state=0;
		int way=0;
		int starttag=-1;//记录tag标签的初始位置
		int yetpos=0;//以及匹配到的位置
		boolean isstart=false;
		//int continuetag=-1;
		int arrnum=0;
		Node branch=null;//得到可以匹配的枝
		Node branchnext=null;
		Node root=forest.getRoot();
		branch=root;//开始从根节点匹配
		//StringBuffer tagsb=new StringBuffer();
		//按照顺序匹配将每个词都进行匹配
		for(;yetpos<len;)
		{
			i=yetpos;
			//  单个词正向向前最大匹配
			while(i<len)
			{
				p=i;
				branchnext=branch.get(b[p]);
				if(branchnext!=null)//有后续位置
				{
					if(!isstart) 
					{
						isstart=true;//说名是第一次匹配到
						starttag=p;
					}
					state=branchnext.getState();
					
					switch (state)
					{
						case 1:
						{
							branch=branchnext;
							//starttag=p;
							break;
						}
						case 2:
						{
							branch=branchnext;
							//starttag=p;
							break;
						}
						case 3://tag匹配成功
						{
							way=branchnext.getExplainWay();
							if(starttag==-1) //说明这个位置是一次匹配上来的
							{
								starttag=p;
							}
							arrnum=branchnext.getPos();//该字段在一行中的位置
							i=dealTLV(starttag,p,way,arrnum,state);
							//yetpos=i;
							//branch=root;
							//starttag=-1;
							branch=branchnext;//继续往4去匹配，只是部分匹配
							break;
						}
						case 4://tag匹配成功
						{
							way=branchnext.getExplainWay();
							if(starttag==-1) //说明这个位置是一次匹配上来的
							{
								starttag=p;
							}
							arrnum=branchnext.getPos();//该字段在一行中的位置
							i=dealTLV(starttag,p,way,arrnum,state);
							
							branch=root;
							starttag=-1;
							isstart=false;//最大长度匹配结束，恢复初始状态
							//yetpos++;
							break;
						}
						case 0://匹配失败
						{
							branch=branchnext;
							starttag=-1;
							isstart=false;
							
							break;
						}
					}
					
					
					
				}
				else//没有匹配上
				{
					starttag=-1;
					branch=root;
					//yetpos++;
					isstart=false;
					break;
					//i=yetpos;//已经匹配过得位置
				}
				if(state==4) break;
				i++;//没有匹配上直接后跳一个未知
				
			}
			yetpos++;//匹配的字符下移
		}
	}
	
	public int dealTLV(int starttag,int pos,int way,int arrnum,int state)//处理完 tlv 返回 最后的位置
	{
		
		String tag=input.substring(starttag,pos+1);
		
		System.out.println(tag);
		if(state==3) return pos;
		if (state==4) return starttag;
		//wordlist.add(word);
		return pos;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fname="D:\\BSSdecode\\zjfee\\scdr\\test\\141022155953.r1sf04.b07134408.dat.gz";
		//String fname="D:\\data\\dat\\shex0.dat";
		MatchWordAddGraph test=new MatchWordAddGraph();
		DefineForest dic=new DefineForest();
		
		char []tag0={'我'};
		char []tag1={'爱'};
		char []tag2={'北'};
		char []tag3={'京'};
		char []tag4={'城'};
		char []tag5={'北','京'};
		char []tag6={'北','京','城'};
		
		dic.insertTag(tag0);
		dic.insertTag(tag1);
		dic.insertTag(tag2);
		dic.insertTag(tag3);
		dic.insertTag(tag4);
		dic.insertTag(tag5);
		dic.insertTag(tag6);
		DefineForest alldic=new DefineForest();
		
		//DefineForest alldic=new DefineForest();
		alldic=DicTion.coredic;
		
		//String input="我爱北京城";
		//String input="这是中国人的事情,说有人都要同意 ";
		//
		String input="李浩瀚爱北京城";
		test.setInput(input);
		GraphInterface gp=new Graphic();
		gp.setInput(input);
		gp.initV(100);
		test.match(alldic,gp);
		
		//gp.compute(test.seq+1);
		gp.compute();
		gp.showPath(input.length()+1,0);
		System.out.println("end");
		String  input1="我爱北京天安门，天安门上彩旗飘";
		test.setInput(input1);
		gp.setInput(input1);
		gp.initV(100);
		test.match(alldic,gp);
		
		//gp.compute(test.seq+1);
		gp.compute();
		gp.showPath(input1.length()+1,0);
		System.out.println("end");
		//gp.showPath(input.length()+1,0);
		//byte []tag1={0x00,0x1f,0x4e,0x55,0x01,0x44,0x1f,0x56,0x00,0x55};
		
		

	}

}
