package org.chinaunicom.match;


import java.util.List;

import org.chinaunicom.graph.GraphInterface;
import org.chinaunicom.graph.Graphic;
import org.chinaunicom.thread.DicTion;
import org.chinaunicom.tree.DefineForest;


public class WordSplit   {

	/**
	 * @param 封装之后的 中文分词类
	 */
	MatchWordAddGraph test=new MatchWordAddGraph();
	GraphInterface gp=new Graphic();
	public void parseString(String input)
	{
		
		test.setInput(input);
		gp.setInput(input);
		gp.initV(100);
		test.match(DicTion.coredic,gp);
		gp.compute();
		gp.showPath(input.length()+1,0);
		//System.out.println("end");
	}
	public  List<String> parse(String input)
	{
		
		List resultlist=null;
		test.setInput(input);
		gp.setInput(input);
		gp.initV(100);
		test.match(DicTion.coredic,gp);
		gp.compute();
		resultlist=gp.pathList(input.length()+1,0);
		//System.out.println("end");
		return resultlist;
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		WordSplit word=new WordSplit();
		word.parseString("赵锡臣是王伟华的领导");
//		String s=null;
//		for(String str:wordlist)
//		{
//			System.out.println(str+"");
//			//s+=str;
//		}
//		//word.parse("中国人民解放军是最可爱的人");

	}

}
