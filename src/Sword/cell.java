package Sword;

import EZ.TurnInformation;
//此类用来保存棋盘中的每个格子的坐标以及值。
public class cell {
	public int row;
	public int col;
	public int value = -1;
	
	public cell(){
		
		if(row >=0 &&row<=14 && col>=0 && col<=14){
		value = TurnInformation.battleField[col][row];
		}
		
	}

}
