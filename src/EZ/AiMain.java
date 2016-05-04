package EZ;

import Sword.SwordMain;

public class AiMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		InformationReceive IR=new InformationReceive();
		IR.inReceive();		
		GameIniInformation GII=new GameIniInformation(IR.getInformation());
		
		if(GII.samuraiID==0||GII.samuraiID==3){
			for(int i=1;i<GII.totalRounds;i++){
				Spear spear=new Spear();
				spear.spearAiRun();
			}
			
			
		}else if(GII.samuraiID==1||GII.samuraiID==4){
			
			for(int i=1;i<GII.totalRounds;i++){
				TurnInformationReceive TIR=new TurnInformationReceive();
				TIR.tuReceive();
				TurnInformation TI=new TurnInformation(TIR.getTurnInformation(),TIR.getBattleField());			
				SwordMain swordMain=new SwordMain();
//				
				
			}
			
		}else if(GII.samuraiID==2||GII.samuraiID==5){
			
			for(int i=1;i<GII.totalRounds;i++){
				TurnInformationReceive TIR=new TurnInformationReceive();
				TIR.tuReceive();
				TurnInformation TI=new TurnInformation(TIR.getTurnInformation(),TIR.getBattleField());					
				Battleax battleax=new Battleax();
				battleax.battleaxAiRun();
				
			}
			
		}	

	}

}
