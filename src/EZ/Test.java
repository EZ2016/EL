package EZ;

public class Test {
	public static void main(String[] args) {
		TurnInformationReceive TIR=new TurnInformationReceive();
		TIR.tuReceive();
		TurnInformation TI=new TurnInformation(TIR.getTurnInformation(),TIR.getBattleField());
		System.out.println(TI.myRecoverRound);
	}
}
