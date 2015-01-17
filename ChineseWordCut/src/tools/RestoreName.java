package tools;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import thread.debugstartThread;



public class RestoreName {

	/**
	 * @param args
	 * @throws IOException
	 */

	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		
		String renamedir=null;
		InputStream in=null;
		ResourceBundle rb=null;
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
		System.out.println(renamedir);
		File sourceFile = new File(renamedir);
		File fileDir = sourceFile;
		if (fileDir.isDirectory()) {
			File[] textFiles = fileDir.listFiles();
			for (File f : textFiles) {
				String fname = f.getName();
				if(fname.indexOf("reading")==-1) continue;
				String name = fname.substring(0, fname.indexOf("reading"));
				
				File targetFile1 = new File(renamedir + name);
				System.out.println("source file is exist? "
						+ sourceFile.exists() + ", source file => "
						+ sourceFile);
				System.out.println(targetFile1 + " is exist? "
						+ targetFile1.exists());
				System.out.println("rename to " + targetFile1 + " => "
						+ f.renameTo(targetFile1));
				System.out.println("source file is exist? "
						+ sourceFile.exists() + ", source file => "
						+ sourceFile);
			}

		}
	}

}
