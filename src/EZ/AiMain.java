package EZ;

import Sword.SwordMain;

public class AiMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		InformationReceive IR=new InformationReceive();
		IR.inReceive();		
		GameIniInformation.SetGameIniInformation(IR.getInformation());
		
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
//				
				
			}
			
		}else if(GameIniInformation.samuraiID==2||GameIniInformation.samuraiID==5){
			
			for(int i=1;i<GameIniInformation.totalRounds;i++){
				TurnInformationReceive TIR=new TurnInformationReceive();
				TIR.tuReceive();
				TurnInformation.SetTurnInformation(TIR.getTurnInformation(),TIR.getBattleField());					
				Battleax battleax=new Battleax();
				battleax.battleaxAiRun();
				
			}
			
		}	

	}

}
