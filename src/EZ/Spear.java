package EZ;

import Spear.SpearAi;

public class Spear {
	private SpearAi spearAi;
	
	public Spear(){
		this.spearAi=new SpearAi();//spear
	}
	
	public void spearAiRun(){	
        if(spearAi.recover!=0){//Spear先判断自己是否死亡，再去判断战场状态
				System.out.println("0");
		}
        else {
			spearAi.analyseEnemy();
			spearAi.analyseMe();
			System.out.println(spearAi.order);//输出指令到控制台，manager自己读取命令
		}
	}

	
}
