package tools;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.PropertyResourceBundle;
import java.util.Random;
import java.util.ResourceBundle;

import thread.debugstartThread;




//
/**
 * @author Administrator
 * 将文件从一个 地方拷贝到另一个地方的 Java代码
 * @param filelist 需要拷贝的文件列表
 *
 */
public class FileCP {
	//String path;
	//String md;
	List<String> filelist;
	public void readFile(String path) throws Exception
	{
		File f=new File(path);
		byte b[]=new byte[100];
		File fnew=new File("e:\\test\\"+f.getName());
		RandomAccessFile raf=new RandomAccessFile(f, "r");
		//OutputStreamWriter writer=new FileWriter(fnew);
		RandomAccessFile fw=new RandomAccessFile(fnew,"rw");
		while(raf.read(b)!=-1)
		{
			
			long len=0;
			len=fw.length();
			fw.seek(len);
			fw.write(b);
			for(int i=0;i<100;i++)
			{
				b[i]=0;
			}
			
			
		}
		fw.close();
	}
	//用二进制的读写
	public void readAndWrite(String readpath,String writepath) throws Exception
	{
		File f=new File(readpath);
		byte b[]=new byte[100];
		File fnew=new File(writepath+f.getName());
		FileOutputStream fw=new FileOutputStream(fnew, true);
		//OutputStreamWriter writer=new FileWriter(fnew);
		FileInputStream raf=new FileInputStream(f);
		String content="";
		int c=0;
		while((c=raf.read(b))!=-1)
		{
			
			fw.write(b, 0, c);
			for(int i=0;i<100;i++)
			{
				b[i]=0;
			}
			
		}
		//fw.writeBytes(content);
		fw.close();
	}
	public void travelList(String writepath) throws Exception
	{
		for(String s:filelist)
		{
			
			if(!s.equals(""))
			{
				//this.readFile(s);
				this.readAndWrite(s,writepath);
			}
		}
	}
	public void readMD(String file) throws Exception
	{
		//File filelist=new File(md);
		InputStreamReader in=new InputStreamReader(FileCP.class.getResourceAsStream("/"+file));
		//Reader rd=new Reader(in);
		BufferedReader bf=new BufferedReader(in);
		String line=null;
		while((line=bf.readLine())!=null)
		{
			filelist.add(line.trim());
		}
	}
	//访问一个目录下的所有文件
	public void visitMD(String dir) throws Exception
	{
		//File filelist=new File(md);
		String []filearr=null;
		File Dir=new File(dir);
		if(Dir.isDirectory())
		{
			filearr=Dir.list();
		}
		for(String file:filearr)
		{
			this.filelist.add(dir+file);
		}
		
		
	}
	public FileCP() {
		super();
	
		this.filelist = new ArrayList<String>();
	}
	public static void main(String args[]) throws Exception
	{
		String renamedir=null;
		InputStream in=null;
		ResourceBundle rb=null;
		String inputdir=null;
		try {
			in = new BufferedInputStream(new FileInputStream(args[0]));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			in=debugstartThread.class.getResourceAsStream("/config.properties");
		} 
		try {
			rb = new PropertyResourceBundle(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		renamedir=rb.getString("renamedir");
		inputdir = rb.getString("inputdir");
		FileCP fcp=new FileCP();
//		String path="path.txt";
//		fcp.readMD(path);
		fcp.visitMD(renamedir);
		fcp.travelList(inputdir);
	}

}
