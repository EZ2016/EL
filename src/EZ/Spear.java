package EZ;

import Spear.SpearAi;

public class Spear {
	private SpearAi spearAi;
	public Spear(){
		int teamID=GameIniInformation.teamID;
		this.spearAi=new SpearAi(teamID);// new a spear base on teamID
	}
	
	public void spearAiRun(){	
        if(spearAi.recover!=0){//Spear先判断自己是否死亡，再去判断战场状态
				System.out.println("0");
		}
        else {
			spearAi.type=spearAi.onClothes();//analyse his type1234
			String action=spearAi.actions();
		    spearAi.order=spearAi.order+action;
			if (spearAi.energy>=1&&spearAi.state==0&&spearAi.canHide()) {
				spearAi.order=spearAi.order+"9 ";
				spearAi.energy-=1;
			}
			System.out.println(spearAi.order+"0");//输出指令到控制台，manager自己读取命令
		}
	}

	
}
