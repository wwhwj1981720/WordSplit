package org.ansj.demo.testspeed;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class CFile {
	File f=null;
	static int i=0;
	public CFile(String name) {
		super();
		this.f = new File(name);
		if(f.exists()) 
		{
			//f.delete();
			//i++;
			//f.renameTo(f.getName());
		}
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public CFile() {
		super();
	}

	public void makeFile(String fname) throws IOException
	{
		if(f==null)
		f=new File(fname);
		f.createNewFile();
		
	}
	public void writeOneRow(String str) throws IOException
	{
		FileWriter fw=new FileWriter(f,true);
		BufferedWriter bf=new BufferedWriter(fw);
		bf.write(str);
		bf.close();
	}
	public void writeRow(String str) throws IOException
	{
		FileWriter fw=new FileWriter(f,true);
		BufferedWriter bf=new BufferedWriter(fw);
		bf.write(str+""+"\r\n");
		bf.close();
	}
	public void writeCountRow(long i,String str) throws IOException
	{
		FileWriter fw=new FileWriter(f,true);
		BufferedWriter bf=new BufferedWriter(fw);
		bf.write(str+","+i+","+"\r\n");
		bf.close();
	}
	public void writeCountFile(long i,String str,String b) throws IOException
	{
		FileWriter fw=new FileWriter(f,true);
		BufferedWriter bf=new BufferedWriter(fw);
		bf.write(str+","+i+","+b+","+"\r\n");
		bf.close();
	}
	public void writeHead(String str) throws IOException
	{
		FileWriter fw=new FileWriter(f,true);
		BufferedWriter bf=new BufferedWriter(fw);
		bf.write(str+","+"\r\n");
		bf.close();
	}
	public void writeList(List<String> list)
	{
		for(String s:list)
		{
			try {
				writeRow(s);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void initCsv() throws IOException
	{
		FileWriter fw=new FileWriter(f,true);
		BufferedWriter bf=new BufferedWriter(fw);
		bf.write("Total Lines"+","+"Valid Lines"+","+"Patts Lines"+","+"File Hash"+","+"Key Hash"+","+"FunHash"+","+"Filename"+","+"Path"+"\r\n");
		bf.close();
	}
	public void initFunCsv() 
	{
		FileWriter fw=null;
		try {
			fw = new FileWriter(f,true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedWriter bf=new BufferedWriter(fw);
		try {
			bf.write("Filename"+","+"Start"+","+"End"+","+"FunMd5"+","+"Path"+"\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void initCsvTitle(String title) 
	{
		FileWriter fw=null;
		try {
			fw = new FileWriter(f,true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedWriter bf=new BufferedWriter(fw);
		try {
			bf.write(title+"\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			bf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void writeMap( Map<String,List<String>> map)
	{
		long c=0;
		try {
			writeHead("path,row");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(map!=null) 
		{
			for ( String key : map.keySet()) 
			{
				List<String> lf=map.get(key);
				for(String s:lf)
				{
					try {
						c++;
						writeCountRow(c,s);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
		
	
	
	public void writeCsvTotal(long all,long val) throws IOException
	{
		FileWriter fw=new FileWriter(f,true);
		BufferedWriter bf=new BufferedWriter(fw);
		bf.write(all+","+val+","+""+","+""+","+""+","+""+","+""+","+""+"\r\n");
		bf.close();
	}
}
