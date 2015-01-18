package org.chinaunicom.graph;

import java.util.Vector;

import org.chinaunicom.thread.DicTion;


/**
 * 存在  vi 与 vj  j<i-1 的 多段图
 * 
 * 
 * ***/
public class GraphMore extends Graph {
	
	
	public GraphMore(int c) {
		super(c);
		// TODO Auto-generated constructor stub
	}
	public GraphMore() {
		super();
		// TODO Auto-generated constructor stub
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
	 *    v[i] 与 前面和他有边的点   vj  (i>j) 层 之间  的 有  边的情况 
	 * */
	public void vexDistance(Vector<Integer> vi,int deep)//每一个段内的 端点遍历
	{
		int count=vi.size();
		//遍历当前段内的端点，计算端点到0节点的 cost
		for(int i=0;i<count;i++) //vi 层  有  几个端点
		{
			int p=vi.get(i);//得到当前段内的 node id 端点编号
			//cost(i+1,p)=cost(i,)+c[i][p];
			//前面的一层是 v[i-1]
			for(int j=0;j<p;j++)  // 与v[i] 层有几个边联系的点 一定是小于当前端点号的
			{
				if(c[j][p]!=0)
				{
					int f=j;//说明f点 编号到 p有边的关系
				
						if(cost[p]==null) cost[p]=new CostD(p);
						if(cost[f]==null) cost[f]=new CostD(f);
						if(f!=0) 
						{
							double  mid=cost[f].d+c[f][p];
							if(cost[p].d==0) 
							{
								cost[p].d=mid;
								cost[p].f=f;
							}
							else if(cost[p].d>mid) 
							{
								cost[p].d=mid;
								cost[p].f=f;
							}
						}
						else 
						{
							cost[p].d=c[0][p];
							cost[p].f=0;//记录路径
						}
					
				}
			}
		}
	}
	/*
	 *    v[i] 表示  i 层有 哪些端点 
	 *    i-1 表示 i前面的一层
	 *    v[i] 与 前面和他有边的点   vj  (i>j) 层 之间  的 有  边的情况 
	 *    vi 表示当前是 vi 层
	 *    deep表示是  vi 所在的段号
	 * */
	public void wordDistance(Vector<Integer> vi,int deep,int all)//每一个段内的 端点遍历
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
			for(int j=0;j<all;j++)  // 与v[i] 层有几个边联系的点
			{
				if(cost[j].name.equals("end")) continue; 
				int offset=deep-cost[j].offset;//就是这里被修改了 表示能够访问的点
				if(offset==1)
				{
					int f=j;//说明f点 编号到 p有边的关系
				
						if(cost[p]==null) cost[p]=new CostD(p);
						if(cost[f]==null) cost[f]=new CostD(f);
						if(f!=0) 
						{
							fromto.append(cost[f].name);
							fromto.append("|");
							fromto.append(cost[p].name);
							if(cost[p].name.equals("end"))
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
							double  mid=cost[f].d+edge;
							if(cost[p].d==0) 
							{
								cost[p].d=mid;
								cost[p].f=f;
							}
							else if(cost[p].d>mid) 
							{
								cost[p].d=mid;
								cost[p].f=f;
							}
							edge=0;
						}
						else 
						{
							//默认  c[0][p]=1.0
							//cost[p].d=c[0][p];
							cost[p].d=1.0;
							cost[p].f=0;//记录路径
						}
					
				}
			}
		}
	}
	
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
				int offset=deep-cost[j].offset;//就是这里被修改了 表示能够访问的点
				if(offset==1)
				{
					int f=j;//说明f点 编号到 p有边的关系
				
//						if(cost[p]==null) cost[p]=new CostD(p);
//						if(cost[f]==null) cost[f]=new CostD(f);
						if(f!=0) 
						{
							fromto.append(cost[f].name);
							fromto.append("|");
							fromto.append(cost[p].name);
							if(cost[p].name.equals("end"))
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
							double  mid=cost[f].d+edge;
							if(cost[p].d==0) 
							{
								cost[p].d=mid;
								cost[p].f=f;
							}
							else if(cost[p].d>mid) 
							{
								cost[p].d=mid;
								cost[p].f=f;
							}
							edge=0;
						}
						else 
						{
							//默认  c[0][p]=1.0
							//cost[p].d=c[0][p];
							cost[p].d=1.0;
							cost[p].f=0;//记录路径
						}
					
				}
			}
			}
		}
	}
	

}
