package org.chinaunicom.tree;

import org.chinaunicom.match.MatchWord;
import org.chinaunicom.thread.DicTion;




public class DefineForest {
	Node root;
	
	public Node getRoot() {
		return root;
	}
	public void setRoot(Node root) {
		this.root = root;
	}
//	public DefineForest()
//	{
//		T r=new T;
//		root=new Node(r);
//	}
	public DefineForest()
	{
		char r='#';
		root=new Node(r);
	}
	public void insertTag(char [] tag)
	{
		Node nodefind=root;
		for(char b:tag)
		{
			
			Node node=nodefind.get(b);
			if(node!=null)//找到
			{
				nodefind=node;//继续找
			}
			else//没找到
			{
				Node newnode=new Node(b);
				nodefind.add(newnode);//新建节点
				nodefind=newnode;//注意后续在新建节点上操作
				
			}
		}
		if(nodefind.branch!=null)
		{
			nodefind.setState(3);//插入结束之后状态为3
		}
		else nodefind.setState(4);//插入结束之后状态为4 是叶子结点
	}
	//way 代表匹配tag的处理方式
	public void insertTag(char [] tag,int way,int tlv,int pos)
	{
		Node nodefind=root;
		for(char b:tag)
		{
			
			Node node=nodefind.get(b);
			if(node!=null)//找到
			{
				nodefind=node;//继续找
			}
			else//没找到
			{
				Node newnode=new Node(b);
				nodefind.add(newnode);//新建节点
				nodefind=newnode;//注意后续在新建节点上操作
				
			}
		}
		nodefind.setState(3);//插入结束之后状态为3
		nodefind.setWay(way);
		nodefind.setTlv(tlv);
		nodefind.setPos(pos);
	}
	
	public static void main(String args[])
	{
		DefineForest dic=new DefineForest();
		//DefineForest alldic=new DefineForest();
		char []tag0={'北','京'};
		char []tag1={'北','京','欢'};
		char []tag2={'北','京','欢','迎','你'};
		char []tag5={'欢','迎','你'};
		char []tag3={'北','京','爱','你'};
		char []tag4={'爱','你','们'};
		dic.insertTag(tag0);
		dic.insertTag(tag1);
		dic.insertTag(tag2);
		dic.insertTag(tag3);
		dic.insertTag(tag4);
		dic.insertTag(tag5);
		//DicTion.fieldsize=10;
		//alldic=DicTion.coredic;
		MatchWord test=new MatchWord();
		String word="北京欢迎你，北京爱你们";
		test.setInput(word);
		//char []b=.toCharArray();
		
		test.match(dic);
		System.out.println("end");
		//dic.match(tag1);
	}

}
