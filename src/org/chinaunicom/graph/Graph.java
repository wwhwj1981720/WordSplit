package org.chinaunicom.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * 
 * 标准多段图
 * 存在  vi 与 vi-1 j<i-1 的 多段图
 * 
 * 
 * ***/
public class Graph implements GraphInterface{

String input=null;
static int  N=10;// N是 多段图的端点数 总共矩阵的行数

double [][]c={
		{0,1,0,0,0,0,0,0,0},
		{0,0,1,0,0,0,0,0,0}, 
		{0,0,0,3,2,1,0,0,0},
		{0,0,0,0,0,0,3,0,0},
		{0,0,0,0,0,0,0,2,0},
		{0,0,0,0,0,0,0,0,1},
		{0,0,0,0,0,0,0,1,0},
		{0,0,0,0,0,0,0,0,1},
		{0,0,0,0,0,0,0,0,0},
	};//图的边连接权值矩阵
/*double [][]c={
		{0,1,0,0,0,0,0},
		{0,0,1,0,0,0,0}, 
		{0,0,0,2,1,0,0},
		{0,0,0,0,0,1,0},
		{0,0,0,0,0,0,1},
		{0,0,0,0,0,0,1},
		{0,0,0,0,0,0,0},
	};//图的边连接权值矩阵

*/
//int [][]v={{0},{1},{2},{3,4},{5},{6}};//初始化 多段图的段数
//int [][]v=new int[N][];
  List<Vector<Integer>> v=new ArrayList<Vector<Integer>>();
CostD []cost=new CostD[N];//到达每个端的成本
    Map<String,Integer> m=new HashMap<String,Integer>();
   /*
    * 根据输入的中文 得到 基本段图
    * 例如：
    * 我爱北京城
    *   基本段图是;
    *   begin->我->爱-》 北-》京-》城-》end
    * 
    * */
    public void initV(int max)
    {
    	v.clear();
    	int len=input.length()+1;//
    	cost=new CostD[max];//到达每个端的成本
		CostD begin=new CostD("begin",0,0,0);
		cost[0]=begin;
		//v[i][0]=seq;
		Vector a=new Vector();
		a.add(0);
		v.add(0, a);//构造  多段图
    	for(int i=1;i<len;i++)
    	{
    		CostD costi=new CostD(input.substring(i-1,i),i,i,i);
    		cost[i]=costi;
    		Vector ai=new Vector();
    		ai.add(i);
    		v.add(ai);
    	}
    	CostD costend=new CostD ("end",len,5,6);
		cost[len]=costend;
		Vector ai=new Vector();
		ai.add(len);
		v.add(ai);
		//gp.addTerm(costend, 8, 6);
//    	Vector a1=new Vector();
//    	a1.add(1);
//    	v.add(a1);
//    	Vector a2=new Vector();
//    	a2.add(2);
//    	v.add(a2);
//    	Vector a3=new Vector();
//    	a3.add(3);
//    	a3.add(4);
//    	v.add(a3);
//    	Vector a4=new Vector();
//    	a4.add(5);
//    	v.add(a4);
//    	Vector a5=new Vector();
//    	a5.add(6);
//    	v.add(a5);
//    	for(int i=0;i<6)
//    	{
//    		
//    	}
    	
    }
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	public Graph() {
	
	}
	public Graph(int c) {
		//N=c+2;
		//this.N=c;
		
		
	}
	public void compute()
	{
		int vlen=v.size();//多段图分为几个段
		for(int i=1;i<vlen;i++)
		{
			vexDistance(v.get(i),v.get(i-1),i);//先计算 v1 to vo 的 cost(1,i)
		}
		
	}
	/*
	 *    v[i] 表示  i 层有 哪些端点 
	 *    i-1 表示 i前面的一层
	 *    v[i] 与 v[i-1]   vi  vi-1 层 之间  的 有  边的情况 
	 * */
	public void vexDistance(Vector<Integer> vi,Vector<Integer> v,int deep)//每一个段内的 端点遍历
	{
		int count=vi.size();
		//遍历当前段内的端点，计算端点到0节点的 cost
		for(int i=0;i<count;i++) //vi 层  有  几个端点
		{
			int p=vi.get(i);//得到当前段内的 node id 端点编号
			//cost(i+1,p)=cost(i,)+c[i][p];
			//前面的一层是 v[i-1]
			for(int j=0;j<v.size();j++)  // v[i-1] 层有几个端点
			{
				int f=v.get(j);
				if(c[f][p]!=0) //f点 与 p 点  edge 
				{
					if(cost[p]==null) cost[p]=new CostD(p);
					if(cost[f]==null) cost[f]=new CostD(f);
					if(f!=0) 
					{
						double  mid=cost[f].d+c[f][p];
						if(cost[p].d==0) cost[p].d=mid;
						else if(cost[p].d>mid) cost[p].d=mid;
					}
					else cost[p].d=c[0][p];
				}
			}
		}
	}
	
	/*
	 * v 是 段号
	 * node 是 端点序号  
	 * /
	 */

	
	public void showDistance()
	{
		for(int j=1;j<N;j++)
		{
			System.out.println(cost[j].i+" "+cost[j].d);
			
		}
	}
	//n是  终点编号 ，end 是   开始点编号
	public void showPath(int n,int end)
	{
		StringBuffer sb=new StringBuffer();
		if(n<=end) return;
		System.out.println("path "+n);
		int start=cost[n].f;
		for(;start!=end;start=cost[start].f)
		{
			System.out.println("path "+start);
			sb.insert(0, cost[start].name);
			sb.insert(0, "|");
			
		}
		System.out.println("path "+end);
		System.out.println(sb.toString());
	}
	/*往图中添加中文词汇，
	***
	*    seq 是    图中的端点号
	*    i   是   端点处于 v 段
	*    costterm 是 成本
	*    
	*
	**/
	public void addTerm(CostD costterm,int seq,int i)
	{
		
			cost[seq]=costterm;
			//v[i][0]=seq;
			Vector vget=(Vector)v.get(i);
			vget.add(seq);
					
		
	}
	
		

public static void main(String[] args) {
	Graph gp=new GraphMore();
	gp.initV(100);
	gp.compute();
	gp.showDistance();
	gp.showPath(6,2);
}
//
@Override
public List<String> pathList(int n, int end) {
	// TODO Auto-generated method stub
	return null;
}
}
