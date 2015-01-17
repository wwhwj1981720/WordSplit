package org.chinaunicom.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.chinaunicom.thread.DicTion;


/**
 * 
 * 标准多段图
 * 存在  vi 与 vi-1 j<i-1 的 多段图
 * 
 * 
 * ***/
public class Graphic implements GraphInterface {

String input=null;
static int  N=10;// N是 多段图的端点数 总共矩阵的行数
List<Vector<Integer>> v=new ArrayList<Vector<Integer>>();
  Vector<CostD> cost=new Vector <CostD>();//到达每个端的成本
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
    	cost.clear();
    	int len=input.length()+1;//
    	//cost=new CostD[max];//到达每个端的成本
		CostD begin=new CostD("begin",0,0,0);
		cost.add(0, begin);
		Vector a=new Vector();
		a.add(0);
		v.add(0, a);//构造  多段图
    	for(int i=1;i<len;i++)
    	{
    		CostD costi=new CostD(input.substring(i-1,i),i,i,i);
    		cost.add(i,costi);
    		Vector ai=new Vector();
    		ai.add(i);
    		v.add(ai);
    	}
    	CostD costend=new CostD ("end",len,5,6);
		cost.add(len,costend);
		Vector ai=new Vector();
		ai.add(len);
		v.add(ai);    	
    }
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	public Graphic() {
	
	}
	public Graphic(int c) {		
	}
	public void compute()
	{
		int vlen=v.size();//多段图分为几个段
		for(int i=1;i<vlen;i++)
		{
			wordDistanceEnd(v.get(i),i);//先计算 v1 to vo 的 cost(1,i)
		}
		
	}
	/*
	 *    v[i] 表示  i 层有 哪些端点 
	 *    i-1 表示 i前面的一层
	 *    v[i] 与 v[i-1]   vi  vi-1 层 之间  的 有  边的情况 
	 * */
	public void wordDistanceEnd(Vector<Integer> vi,int deep)//每一个段内的 端点遍历
	{
		StringBuffer fromto=new StringBuffer();
		double edge=0;
		int count=vi.size();
		//遍历当前段内的端点，计算端点到0节点的 cost
		for(int i=0;i<count;i++) //vi 层  有  几个端点
		{
			int p=vi.get(i);//得到当前段内的 node id 端点编号
			//cost(i+1,p)=cost(i,)+c[i][p];
			//前面的一层是 v[i-1]
			for(int k=0;k<deep;k++)  // 与v[i] 层有几个边联系的点
			{
				Vector<Integer> vt=v.get(k);//遍历 deep 之前的层 
				for(int m=0;m<vt.size();m++) 
				{
					int j=vt.get(m);
				int offset=deep-cost.get(j).offset;//就是这里被修改了 表示能够访问的点
				if(offset==1)
				{
					int f=j;//说明f点 编号到 p有边的关系
				
//						if(cost[p]==null) cost[p]=new CostD(p);
//						if(cost[f]==null) cost[f]=new CostD(f);
						if(f!=0) 
						{
							fromto.append(cost.get(f).name);
							fromto.append("|");
							fromto.append(cost.get(p).name);
							if(cost.get(p).name.equals("end"))
							{
								edge=1.0;
							}
							else
							{	
								try {
									Integer score=DicTion.bigramEntryMap.get(fromto.toString());
									if(score==null) edge=9999;
									fromto.delete(0, fromto.length());
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							//double  mid=cost[f].d+c[f][p];//c[f][p] 需要修改为  f与p 之间的  词频 以后要自己训练
							double  mid=cost.get(f).d+edge;
							if(cost.get(p).d==0) 
							{
								cost.get(p).d=mid;
								cost.get(p).f=f;
							}
							else if(cost.get(p).d>mid) 
							{
								cost.get(p).d=mid;
								cost.get(p).f=f;
							}
							edge=0;
						}
						else 
						{
							//默认  c[0][p]=1.0
							//cost[p].d=c[0][p];
							cost.get(p).d=1.0;
							cost.get(p).f=0;//记录路径
						}
					
				}
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
			System.out.println(cost.get(j).i+" "+cost.get(j).d);
			
		}
	}
	//n是  终点编号 ，end 是   开始点编号
	public void showPath(int n,int end)
	{
		StringBuffer sb=new StringBuffer();
		if(n<=end) return;
		//System.out.println("path "+n);
		int start=cost.get(n).f;
		for(;start!=end;start=cost.get(start).f)
		{
			//System.out.println("path "+start);
			sb.insert(0, cost.get(start).name);
			sb.insert(0, "|");
			
		}
		//System.out.println("path "+end);
		//System.out.println(sb.toString());
	}
	public List<String> pathList(int n,int end)
	{
		List<String> sb=new ArrayList<String>();
		if(n<=end) return null;
		//System.out.println("path "+n);
		int start=cost.get(n).f;
		for(;start!=end;start=cost.get(start).f)
		{
			//System.out.println("path "+start);
			sb.add(0, cost.get(start).name);
			//sb.insert(0, "|");
			
		}
		return sb;
		//System.out.println("path "+end);
		//System.out.println(sb.toString());
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
		
			cost.add(seq,costterm);
			//v[i][0]=seq;
			Vector vget=(Vector)v.get(i);
			vget.add(seq);
					
		
	}
	
		

public static void main(String[] args) {
	int len=100;
	Graphic gp=new Graphic();
	gp.initV(100);
	gp.compute();
	gp.showDistance();
	gp.showPath(6,2);
}
//
}
