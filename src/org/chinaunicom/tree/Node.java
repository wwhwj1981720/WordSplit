package org.chinaunicom.tree;

public class Node {
	char data;
	Node []branch;
	int state=0;
	int way=0;
	int tlv=0;
	int pos=0;
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public Node(char b)
	{
		this.data=b;
		branch=null;
	}
	public int getTlv() {
		return tlv;
	}
	public void setTlv(int tlv) {
		this.tlv = tlv;
	}
	/*
	 * 
	 *  查询某个结点的子节点中是否存在一个data的结点
	 * 
	 * @param node 需要添加的结点
	 * 
	 * 
	 * */
	public Node get(char b)
	{
		//boolean flag=false;
		if(branch==null) return null;
		for(Node node:branch)
		{
			if(node.data==b) 
			{
				return node;
			} 
		}
		return null;
	}
	public int getExplainWay()
	{
		return way;
		
	}
	public void setWay(int way)
	{
		this.way=way;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	/*
	 * 
	 * 给 当前结点添加一个儿子结点 node
	 * 需要遍历当前的所有儿子结点，往数组最后添加一个结点
	 * @param node 需要添加的结点
	 * state成词状态
	 * 2表示能成词还可以继续
	 * 3表示可以成词
	 * 
	 * */
	
	
	public void  add(Node node)
	{
		
		int size=0;
		if(branch!=null)
		{	
			size=branch.length;
		}
		Node []newbranch=new Node[size+1];
		int i=0;
		if(branch!=null)
		{
			for(Node oldnode:branch)
			{
				newbranch[i]=oldnode;
				i++;
			}
		}	
		newbranch[i]=node;
		this.branch=null;//将原来的内存释放
		if(state==0)
		{
			this.state=2;//后面插入一个节点证明这个节点状态时可以继续
		}
		if(state==4)
		{
			this.state=3;//后面插入一个节点证明这个节点状态时可以继续
		}
		this.branch=newbranch;
		
	}
	}
