package org.ansj.demo.fileread.bean;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;


import org.ansj.demo.testspeed.CFile;
import org.ansj.dic.DicReader;






public class DicTree {
	CheseDate Base[];
	int Check[];//get pre
	int size;
	int Status[];
	public DicTree(int s)
	{
		size=s;
		Base=new CheseDate[size];
		Check=new int [size];
		Status=new int[size];
	}
	//读入词典，初始化字典树
	public void initDicTree(BufferedReader br) throws IOException
	{
		String temp=null;
		while((temp=br.readLine())!=null)
		{
			String []parm=temp.split("\t");
			int pos=Integer.valueOf(parm[0]);
			String content=parm[1];
			int jump=Integer.valueOf(parm[2]);
			int pre=Integer.valueOf(parm[3]);
			int status=Integer.valueOf(parm[4]);
			//Base[pos]=new CheseDate(jump,0);
			Base[pos]=new CheseDate(jump,0,content);
			Check[pos]=pre;
			
		}
	}
	////匹配全词和前缀，前缀计数
	public boolean insertWord(String word)
	{
		char []array=word.toCharArray();
		if(array!=null)
		{
			int len=array.length;
			CheseDate now=null;
			int jump=0;
			int pre=0;
			int circleNum=0;
			for(int i=0; i<len; i++)
			{
				pre=array[i]+pre;
				now=Base[pre];
				circleNum++;
				if(now!=null) 
				{
					now.count++;
					//jump+=now.jump;
					pre=now.jump;
					if(Status[jump]==3) break;
				}
				
			}
			if(circleNum==len) return true;
			else return false;
		}
		return false;
	}
	//匹配全词才计数，前缀不计数
	public boolean findEntireWord(String word)
	{
		char []array=word.toCharArray();
		if(array!=null)
		{
			int len=array.length;
			CheseDate now=null;
			int jump=0;
			int pre=0;
			int circleNum=0;
			for(int i=0; i<len; i++)
			{
				pre=array[i]+pre;
				now=Base[pre];
				circleNum++;
				if(now!=null) 
				{
					
					//jump+=now.jump;
					pre=now.jump;
					if(Status[jump]==3) break;
				}
				else return false;
				
			}
			if(circleNum==len) 
			{
				now.count++;//整词匹配后才计数
				return true;
			}
			else return false;
		}
		return false;
	}
	//匹配节点
	public CheseDate findWord(String word)
	{
		char []array=word.toCharArray();
		if(array!=null)
		{
			int len=array.length;
			CheseDate now=null;
			int jump=0;
			int pre=0;
			int circleNum=0;
			for(int i=0; i<len; i++)
			{
				pre=array[i]+pre;
				now=Base[pre];
				circleNum++;
				if(now!=null) 
				{
					
					pre=now.jump;
					if(Status[jump]==3) break;
				}
				
			}
			if(circleNum==len) return now;
			//else return null;
		}
		return null;
	}
	public void readTxt(BufferedReader br) throws IOException
	{
		String temp=null;
		while((temp=br.readLine())!=null)
		{
			String []parm=temp.split("\\|");
			for(String word:parm)
			{
				findEntireWord(word);
			}
			
			
		}
	}
	//遍历当前字典树统计数，将所有词频输出到一个文件	
	public void traveTree(CFile newWordFile) throws Throwable
		{
			
			for(int i=1499999; i>=0; i--){
				String s=null;
				if(Base[i]!=null)
				{
					if (!ConsV.yetset.contains(i)) preFatherTree(newWordFile,i);
				}
				//newWordFile.inputRow(+"\t"+row);
			}
		}
		public boolean preFatherTree(CFile newWordFile,int i) throws Throwable
		{
			if(i<0)return false;
			CheseDate  node=Base[i];
			ConsV.yetset.add(i);
			newWordFile.writeOneRow(node.content+"\t"+node.count+"\t"+i+"\r\n");
			//CheseDate father=null;
			if(node.jump==i)return false;
			//father=[node.jump]+;
			preFatherTree(newWordFile,Check[i]);
			return true;
			
		}
		public void seqtraveTree(CFile newWordFile) throws Throwable
		{
			
			for(int i=0; i<1000000; i++){
				String s=null;
				if(Base[i]!=null)
				{
					CheseDate node=Base[i];
					newWordFile.writeOneRow(node.content+"\t"+node.count+"\r\n");
				}
				//newWordFile.inputRow(+"\t"+row);
			}
		}
	
	
	public static void main(String []args) throws IOException
	{
		CFile newWordFile = new CFile();
		newWordFile.makeFile(ConsV.pathword_frequt);
		DicTree tree=new DicTree(ConsV.dicTreeBaseSize);
		tree.initDicTree(DicReader.getReader("arrays.dic"));
		//tree.insertWord("中华人民共和国");
		//tree.insertWord("中华人民共和国");
		tree.readTxt( new BufferedReader(
				new InputStreamReader(new FileInputStream(ConsV.pathSMS_WOR), "utf-8")));
		try {
			tree.traveTree(newWordFile);
			//tree.seqtraveTree(newWordFile);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(tree.findWord("联通").content+" "+tree.findWord("联通").count);
	}

}
