package factory;
//import lex.LexInterface;


/**
 * @author Administrator
 *
 */
public class Factory {

	
	public static Object createObject(String classname) 
	{
		String classpr="thread.";
		Object ob=null;
		if((classname!=null)&&(!classname.equals("")))
		{
			
			Class cl;
			try {
				cl = Class.forName(classpr+classname);
				ob=cl.newInstance();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ob;
	} 
	public static Object createThreadObject(String classname) 
	{
		String classpr="thread.";
		Object ob=null;
		if((classname!=null)&&(!classname.equals("")))
		{
			
			Class cl;
			try {
				cl = Class.forName(classpr+classname+"Thread");
				ob=cl.newInstance();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ob;
	} 
	//后缀开头大写后面小写
	public static String changFirstChar(String classname)
	{
		String first="";
		if((classname!=null)&&(!classname.equals("")))
		{
			first=classname.substring(0, 1);
			String other=classname.substring(1);
			first=first.toUpperCase();
			other=other.toLowerCase();
			first+=other;
		}
		return first;
		
	}
	public static Object createLexObject(String classname) 
	{
		String classpr="lex.";
		
		Object ob=null;
		if((classname!=null)&&(!classname.equals("")))
		{
			
			Class cl;
			classname=changFirstChar(classname);
			try {
				cl = Class.forName(classpr+classname+"Anayase");
				ob=cl.newInstance();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					cl = Class.forName(classpr+"Html"+"Anayase");
					ob=cl.newInstance();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//e.printStackTrace();
			}
		}
		return ob;
	} 
	/** 动态创建 函数识别
	 * 没有自己的识别类时，使用java的作为通用的
	 * @param args
	 * 
	 */
	public static Object createFuntionObject(String classname) 
	{
		String classpr="function.";
		
		Object ob=null;
		if((classname!=null)&&(!classname.equals("")))
		{
			
			Class cl;
			classname=changFirstChar(classname);
			try {
				cl = Class.forName(classpr+classname+"FunctionJudge");
				ob=cl.newInstance();
			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				try {
					cl = Class.forName(classpr+"Java"+"FunctionJudge");
					ob=cl.newInstance();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				//e.printStackTrace();
			}
		}
		return ob;
	} 
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String pre="";
		pre=changFirstChar(pre);
//		LexInterface lex=null;
//		lex=(LexInterface)Factory.createLexObject(pre+"Anayase");
		//lex.getWordlist();
	}

}
