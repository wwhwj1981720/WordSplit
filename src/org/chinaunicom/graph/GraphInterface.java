package org.chinaunicom.graph;

import java.util.List;

public interface GraphInterface {
	public void initV(int max);//初始化 多段图的单个字符段
	public void setInput(String input);//设置输入的字符串
	public void compute();//动态规划
	public void showPath(int n,int end);//得到路径
	public void addTerm(CostD costterm,int seq,int i);//添加 一个分词的内容
	public List<String> pathList(int n,int end);
}
