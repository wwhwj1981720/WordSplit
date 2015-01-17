package thread;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;



/**
 * 类 GetDirFile 从目录下面读取文件列表，并将文件名加入队列 path 读入的源文件路径， specialword 要查的特殊词汇
 * 
 * @throws Throwable
 */
public class GetDirFileBlock extends Thread {
	String path;
	public static BlockingQueue<File> queque;
	static int max=100;
	public GetDirFileBlock(String path) {
		super();
		this.path = path;
		queque = new LinkedBlockingQueue(max) ;
	}
	

	public void getQueque() {
		File fileDir = new File(path);
		if (fileDir.isDirectory()) {
			File[] textFiles = fileDir.listFiles();
			for (File f : textFiles) {
				if (f.isFile() && f.getName().endsWith(DicTion.suffix)) {
					if (!queque.contains(f))
					{	
						try {
							queque.put(f);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("reading thread id is "+this.getId()+"fname+"+f.getName());
					}
				}
			}
		}

		
	}

//	public void setQueque(Queue queque) {
//		this.queque = queque;
//	}

	

	public void run() {

		while (true) 
		{
			
			System.out.println("thread id is "+this.getId()+"reading file");
			getQueque();
//			try 
//			{
//				//this.sleep(100);
//			} 
			
		}
			
		
	}

	public static void main(String args[]) {
		long before = System.currentTimeMillis();
		String path = "E:\\SMSCDR_20130901\\SMSCDR_20130901\\";
		GetDirFileBlock dir = new GetDirFileBlock(path);
		dir.start();
		
	}

}
