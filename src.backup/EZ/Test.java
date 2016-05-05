package EZ;

public class Test {
	public static void main(String[] args) {
		TurnInformationReceive TIR=new TurnInformationReceive();
		TIR.tuReceive();
		TurnInformation TI=new TurnInformation(TIR.getTurnInformation(),TIR.getBattleField());
		System.out.println(TI.myRecoverRound);
		System.out.println(TI.nowAllSamurai.get(0).col);
		System.out.println(TI.nowAllSamurai.get(0).row);
		System.out.println(TI.nowAllSamurai.get(0).state);
		System.out.println(TI.nowAllSamurai.get(1).col);
		System.out.println(TI.nowAllSamurai.get(1).row);
		System.out.println(TI.nowAllSamurai.get(1).state);
		System.out.println(TI.nowAllSamurai.get(2).col);
		System.out.println(TI.nowAllSamurai.get(2).row);
		System.out.println(TI.nowAllSamurai.get(2).state);
		System.out.println(TI.nowAllSamurai.get(3).col);
		System.out.println(TI.nowAllSamurai.get(3).row);
		System.out.println(TI.nowAllSamurai.get(3).state);
		System.out.println(TI.nowAllSamurai.get(4).col);
		System.out.println(TI.nowAllSamurai.get(4).row);
		System.out.println(TI.nowAllSamurai.get(4).state);
		System.out.println(TI.nowAllSamurai.get(5).col);
		System.out.println(TI.nowAllSamurai.get(5).row);
		System.out.println(TI.nowAllSamurai.get(5).state);
		
	}
}
