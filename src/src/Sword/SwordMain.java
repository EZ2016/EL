package Sword;

public class SwordMain {
	public static int energy = 7;
	public String act="";
	NecessaryAct nAct =new NecessaryAct();
	public SwordMain(){
		if(nAct.MustKill()==0){
				if(!nAct.Escape()){
					nAct.ShouldOccupy();
					
				}
			}
		act = nAct.action+"0";
		System.out.println(act);
		}
			
	}
	


