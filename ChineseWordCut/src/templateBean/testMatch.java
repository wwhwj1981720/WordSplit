package templateBean;


import org.chinaunicom.graph.Graph;
import org.chinaunicom.tree.DefineForest;

import thread.DicTion;

public class testMatch {
	DefineForest alldic=new DefineForest();
	//DefineForest alldic=new DefineForest();
	MatchWord test=new MatchWord();
	public void start()
	{
		alldic=DicTion.coredic;
		
		String input="我爱北京城天安门";
		String strinput="这是中国人的事情，说有人都要同意 ";
		test.setInput(input);
		test.match(alldic);
		test.setInput(strinput);
		test.match(alldic);
		Graph gp = new Graph();
		gp.setInput(input);
		//file.FileOutput(fout);
		System.out.println("end");
		
	}

}
