package tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.channels.FileChannel;


/**
 * @param args
 * param fin 输入的文件路径
 * param fout 输出的文件路径
 * 修改一个文件等编码
 */

public class ChangeEncoding {

	String fin;
	String fout;
	public void read()
	{
		BufferedReader reader=null;
		File f = new File(fout);
		BufferedWriter pageFc=null;
		try {
			pageFc = new BufferedWriter(new OutputStreamWriter( new FileOutputStream(f,true),"gb2312"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			reader= new BufferedReader(new InputStreamReader(  
			        new FileInputStream(fin), "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line=null;
		try {
			for(;(line=reader.readLine())!=null;)
			{
				
				//System.out.println(page.getText());
				
				pageFc.write(line+"\r\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			pageFc.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public ChangeEncoding(String fin, String fout) {
		super();
		this.fin = fin;
		this.fout = fout;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ChangeEncoding ch=new ChangeEncoding("D:\\zd\\test.txt","D:\\zd\\gbk.csv");
		ch.read();
		System.out.println("end");

	}

}
