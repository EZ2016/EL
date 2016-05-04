package Sword;

public class SwordMain {
	public static int energy = 7;
	NecessaryAct nAct =new NecessaryAct();
	public SwordMain(){
		if(nAct.MustKill()==0){
				if(!nAct.Escape()){
					nAct.ShouldOccupy();
					System.out.println(nAct.action);
				}
			}
			
		}
			
	}
	


