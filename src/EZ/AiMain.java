package EZ;


public class AiMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		InformationReceive IR=new InformationReceive();
		IR.inReceive();		
		GameIniInformation GII=new GameIniInformation();
		if(GII.samuraiID==0||GII.samuraiID==3){
			for(int i=1;i<GII.totalRounds;i++){
				SpearAi spear=new SpearAi();//spear
                if(spear.recover!=0){//Spear先判断自己是否死亡，再去判断战场状态
						System.out.print(0+"\n");
						break;
				}else {
					spear.analyseEnemy();
					spear.analyseEnemyCourt();
					}
				System.out.print(spear.order);//输出指令到控制台，manager自己读取命令	
			}
			
			
		}else if(GII.samuraiID==1||GII.samuraiID==4){
			
			for(int i=1;i<GII.totalRounds;i++){
				Sword sword=new Sword();
//				sword.
				
			}
			
		}else if(GII.samuraiID==2||GII.samuraiID==5){
			
			for(int i=1;i<GII.totalRounds;i++){
				Battleax battleax=new Battleax();
//				battleax.
				
			}
			
		}	

	}

}
