package EZ;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import Sword.SwordMain;


public class AiMain {
	static File file = new File("/Users/zhujing/Desktop/log.text");
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		
		InformationReceive IR=new InformationReceive();
		IR.inReceive();		
		GameIniInformation.SetGameIniInformation(IR.getInformation());
		
/*		while(true){
			TurnInformationReceive TIR=new TurnInformationReceive();
			TIR.tuReceive();
			TurnInformation.SetTurnInformation(TIR.getTurnInformation(),TIR.getBattleField());
			System.out.println("# Turn "+TurnInformation.turnNum);
			System.out.println(GameIniInformation.samuraiID+" 0");
		}
*/
		if(GameIniInformation.samuraiID==0||GameIniInformation.samuraiID==3){
			for(int i=1;i<GameIniInformation.totalRounds;i++){
				Spear spear=new Spear();
				spear.spearAiRun();
			}
			
			
		}else if(GameIniInformation.samuraiID==1||GameIniInformation.samuraiID==4){
			
			for(int i=1;i<GameIniInformation.totalRounds;i++){
				TurnInformationReceive TIR=new TurnInformationReceive();
				TIR.tuReceive();
				TurnInformation.SetTurnInformation(TIR.getTurnInformation(),TIR.getBattleField());			
				SwordMain swordMain=new SwordMain();
			}
			
		}else if(GameIniInformation.samuraiID==2||GameIniInformation.samuraiID==5){
				FileWriter fw=new FileWriter(file);
				BufferedWriter bw=new BufferedWriter(fw);
				TurnInformation.initi();
			for(int i=1;i<GameIniInformation.totalRounds;i++){
			
				TurnInformationReceive TIR=new TurnInformationReceive();
				TIR.tuReceive();
				TurnInformation.SetTurnInformation(TIR.getTurnInformation(),TIR.getBattleField());	
				
				Battleax battleax=new Battleax();
				battleax.battleaxAiRun();
				
				System.out.println("# Turn "+TurnInformation.turnNum);
				System.out.println(battleax.actions);
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
				bw.write("你的操作是:"+battleax.actions);
				bw.newLine();
			}
			bw.close();
			fw.close();
			
	
		}
		
		else{
			for(int i=1;i<GameIniInformation.totalRounds;i++){
				System.out.println(GameIniInformation.samuraiID);
			}
		}
	}
	


}
