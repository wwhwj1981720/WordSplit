package org.chinaunicom.tools;

import java.io.File;

public class Dir {
	String path;
	File dir;

	public Dir(String path) {
		super();
		this.path = path;
		dir=new File(this.path);
		if(dir.exists()){ System.out.println(path+"already exit"); } //dir.delete();
		else dir.mkdir();
		
	}
	public String getPath()
	{
		return dir.getParent();
	}


}
