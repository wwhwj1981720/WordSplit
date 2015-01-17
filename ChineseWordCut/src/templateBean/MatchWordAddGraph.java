package templateBean;


import org.chinaunicom.graph.CostD;
import org.chinaunicom.graph.Graph;
import org.chinaunicom.graph.GraphInterface;
import org.chinaunicom.tree.DefineForest;
import org.chinaunicom.tree.Node;

public class MatchWordAddGraph extends MatchWord {
	
	public void match(DefineForest forest,GraphInterface gp)
	{
		if(b==null) return;
		
		int len=filesize;
		int i=0;
		int p=0;//移动指针
		int state=0;
		int way=0;
		int starttag=-1;//记录tag标签的初始位置
		int yetpos=0;//以及匹配到的位置
		boolean isstart=false;
		int arrnum=0;
		Node branch=null;//得到可以匹配的枝
		Node branchnext=null;
		Node root=forest.getRoot();
		branch=root;//开始从根节点匹配
		//StringBuffer tagsb=new StringBuffer();
		//按照顺序匹配将每个词都进行匹配
		for(;yetpos<len;)
		{
			i=yetpos;
			//  单个词正向向前最大匹配
			while(i<len)
			{
				p=i;
				branchnext=branch.get(b[p]);
				if(branchnext!=null)//有后续位置
				{
					if(!isstart) 
					{
						isstart=true;//说名是第一次匹配到
						starttag=p;
					}
					state=branchnext.getState();
					
					switch (state)
					{
						case 1:
						{
							branch=branchnext;
							//starttag=p;
							break;
						}
						case 2:
						{
							branch=branchnext;
							//starttag=p;
							break;
						}
						case 3://tag匹配成功
						{
							way=branchnext.getExplainWay();
							if(starttag==-1) //说明这个位置是一次匹配上来的
							{
								starttag=p;
							}
							arrnum=branchnext.getPos();//该字段在一行中的位置
							i=dealTLV(starttag,p,way,arrnum,state,gp);
							//yetpos=i;
							//branch=root;
							//starttag=-1;
							branch=branchnext;//继续往4去匹配，只是部分匹配
							break;
						}
						case 4://tag匹配成功
						{
							way=branchnext.getExplainWay();
							if(starttag==-1) //说明这个位置是一次匹配上来的
							{
								starttag=p;
							}
							arrnum=branchnext.getPos();//该字段在一行中的位置
							i=dealTLV(starttag,p,way,arrnum,state,gp);
							
							branch=root;
							starttag=-1;
							isstart=false;//最大长度匹配结束，恢复初始状态
							//yetpos++;
							break;
						}
						case 0://匹配失败
						{
							branch=branchnext;
							starttag=-1;
							isstart=false;
							
							break;
						}
					}
					
					
					
				}
				else//没有匹配上
				{
					starttag=-1;
					branch=root;
					//yetpos++;
					isstart=false;
					break;
					//i=yetpos;//已经匹配过得位置
				}
				if(state==4) break;
				i++;//没有匹配上直接后跳一个未知
				
			}
			{
				yetpos++;//匹配的字符下移
				starttag=-1;
				branch=root;
				//yetpos++;
				isstart=false;
			}
		}
		//最后一个图的结点
		
	}
	
	public int dealTLV(int starttag,int pos,int way,int arrnum,int state,GraphInterface gp)//处理完 tlv 返回 最后的位置
	{
	
			
			String tag=input.substring(starttag,pos+1);
			//System.out.println(tag);
			if(tag.length()>1)
			{
				seq++;
			    gp.addTerm(new CostD(tag,seq,starttag,pos+1),seq,starttag+1);
			
			}
			if(state==3) return pos;
			if (state==4) return starttag;
		
		//wordlist.add(word);
		return pos;
	}

}
