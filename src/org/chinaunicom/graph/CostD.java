package org.chinaunicom.graph;

public class CostD {
	int i;
	double d=0;
	int f;//记录路径
	String name;
	int offset;
	int start;
	public CostD() {
		super();
	}
	public CostD(int i) {
		super();
		this.i = i;
	}
	/*
	 *  @param  name 词条
	 *  @param  seq  端点号
	 *  @param  starttag
	 *  @param  pos  词条偏移
 	 * 
	 * */
	public CostD(String name,int seq,int starttag ,int pos ) {
		super();
		this.i = i;
		this.name = name;
		this.offset=pos;
	}
	

}
