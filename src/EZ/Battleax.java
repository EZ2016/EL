package EZ;

import Battleax.BattleaxAi;

public class Battleax {
	private BattleaxAi battleaxAi;
	String actions="";//AiMain在新建一个Battleax对象后可以从actions中取得最终行动
	public Battleax(){
		this.battleaxAi=new BattleaxAi();
	}
	public void battleaxAiRun(){
		if(TurnInformation.myRecoverRound!=0){  //先判断自己是否死亡
			System.out.println("0");
		}
		else{
			battleaxAi.run();
			this.actions=battleaxAi.actions.substring(0,battleaxAi.actions.length()-2);
			System.out.println(this.actions);
		}
	}
}
