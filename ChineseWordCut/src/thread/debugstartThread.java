package thread;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import factory.Factory;

import tools.Dir;



public class debugstartThread {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stubInputStream in = new BufferedInputStream(new FileInputStream(path));
		String inputdir=null;
		String outputdir=null;
		String movedir=null;
		String threadnum=null;
		String fieldsize=null;
		String threadbean=null;
		if (args.length == 0) 
		{
			System.out.println("no configue file");
		}
		else
		{
			InputStream in=null;
			ResourceBundle rb=null;
			try {
				in = new BufferedInputStream(new FileInputStream(args[0]));
				if(in==null)
				{
					in=debugstartThread.class.getResourceAsStream("/"+"config.properties");
				}
				
			}
			 catch (IOException e) {
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
			inputdir = rb.getString("inputdir");
			outputdir = rb.getString("outputdir");
			threadnum=rb.getString("threadnum");
			movedir=rb.getString("movedir");
			fieldsize=rb.getString("fieldsize");
			threadbean=rb.getString("threadbean");
			System.out.println("out"+outputdir+" "+"input"+" "+inputdir+" "+threadnum);
		}

		DicTion.movedir=new Dir(movedir);//初始化reading之后移动的目录
		DicTion.movedirstr=movedir;//设置要移动的文件路径
		DicTion.outdir=outputdir;
		DicTion.fieldsize=Integer.parseInt(fieldsize);//输出的列数
		
		GetDirFileBlock read=new GetDirFileBlock(inputdir);
		ExecutorService readExecutors=Executors.newFixedThreadPool(1);
		readExecutors.submit(read);
		int writethreadnum=Integer.parseInt(threadnum);
		ExecutorService ServiceExecutors=Executors.newFixedThreadPool(writethreadnum);
		
		for(int i=0;i<writethreadnum;i++)
		{
			
			Thread onethread=(Thread)Factory.createObject(threadbean);
			ServiceExecutors.submit(onethread);
			//onethread.start();
		}

	}

}
