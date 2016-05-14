package EZ;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import Spear.SpearAi;

public class Spear {
	private SpearAi spearAi;
	FileWriter fw;
	BufferedWriter bw;

	
	public Spear() throws IOException{

		int teamID=GameIniInformation.teamID;
		this.spearAi=new SpearAi(teamID);//spear
		fw=new FileWriter("Spearlog.txt");
		bw=new BufferedWriter(fw);
	}
	
	public void spearAiRun() throws IOException{	
		
        if(spearAi.recover!=0){//Spear先判断自己是否死亡，再去判断战场状态
				System.out.println("0");
		}
        else {
			spearAi.analyseEnemy();
			spearAi.analyseMe();
			if (spearAi.energy>1&&spearAi.state==0&&spearAi.canHide()) {
				spearAi.order=spearAi.order+"9 ";
				spearAi.energy-=1;
			}
			System.out.println(spearAi.order+"0");//输出指令到控制台，manager自己读取命令
		}
        
        bw.write("manager的操作:");
		bw.newLine();
		bw.write("回合数:"+TurnInformation.turnNum+" ");
		bw.newLine();
		bw.write("恢复期:"+TurnInformation.myRecoverRound+" ");
		bw.newLine();
		bw.write(TurnInformation.nowAllSamurai.get(0).row+" ");
		bw.write(TurnInformation.nowAllSamurai.get(0).col+" ");
		bw.write(TurnInformation.nowAllSamurai.get(0).state+" ");
		bw.newLine();
		bw.write(TurnInformation.nowAllSamurai.get(1).row+" ");
		bw.write(TurnInformation.nowAllSamurai.get(1).col+" ");
		bw.write(TurnInformation.nowAllSamurai.get(1).state+" ");
		bw.newLine();
		bw.write(TurnInformation.nowAllSamurai.get(2).row+" ");
		bw.write(TurnInformation.nowAllSamurai.get(2).col+" ");
		bw.write(TurnInformation.nowAllSamurai.get(2).state+" ");
		bw.newLine();
		bw.write(TurnInformation.nowAllSamurai.get(3).row+" ");
		bw.write(TurnInformation.nowAllSamurai.get(3).col+" ");
		bw.write(TurnInformation.nowAllSamurai.get(3).state+" ");
		bw.newLine();
		bw.write(TurnInformation.nowAllSamurai.get(4).row+" ");
		bw.write(TurnInformation.nowAllSamurai.get(4).col+" ");
		bw.write(TurnInformation.nowAllSamurai.get(4).state+" ");
		bw.newLine();
		bw.write(TurnInformation.nowAllSamurai.get(5).row+" ");
		bw.write(TurnInformation.nowAllSamurai.get(5).col+" ");
		bw.write(TurnInformation.nowAllSamurai.get(5).state+" ");
		bw.newLine();
		bw.write("战场:");
		bw.newLine();
		for(int m=0;m<GameIniInformation.heightOfBf;m++){
			for(int n=0;n<GameIniInformation.widthOfBf;n++){
				bw.write(TurnInformation.battleField[m][n]+" ");
			}
			bw.newLine();
		}
		bw.write("你的操作是:"+spearAi.order+"0");
		bw.newLine();
	}



	
}
