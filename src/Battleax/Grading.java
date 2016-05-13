package Battleax;
import java.util.ArrayList;

import EZ.GameIniInformation;
import EZ.Home;
import EZ.Samurai;
import EZ.TurnInformation;
/*斧头武士的评分系统
 * v1.0
 * v2.0: 调整了代码风格，不直接调用GameIniInformation类和TurnInformation类的数据
 * by 俊毅
 */
//注意：分数为0表示这个action不成立
public class Grading {
	
	private BattleaxAi battleaxAi;
	private BattleaxAi battleaxAi_backup;
	
	public Grading(BattleaxAi battleaxAi){
		battleaxAi_backup=battleaxAi;
		this.battleaxAi=cloneBattleaxAi(battleaxAi);
		this.battleaxAi.setBattleField(battleaxAi.getBattleField());
		this.battleaxAi.setMe(battleaxAi.getMe());
	}
	
	public BattleaxAi cloneBattleaxAi(BattleaxAi battleaxAi) {
		BattleaxAi newBattleaxAi=new BattleaxAi();
		try{
			newBattleaxAi=(BattleaxAi)battleaxAi.clone();
		}
		catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return newBattleaxAi;
	}
	
	public int[] maxScore(ArrayList<int[]> allActions) {                //将最高分步骤以int数组返回
		ArrayList<StepAndScore> steps=new ArrayList<StepAndScore>();
		for(int[] action:allActions){
			steps.add(new StepAndScore(action,getScore(action)));
		}
		
		StepAndScore maxStep=steps.get(0);
		for(StepAndScore step:steps){
			if(step.score>maxStep.score){
				maxStep=step;
			}
			else if (step.score==maxStep.score) {//如果分数相同，随机取一个
				int i=(int)(Math.random()*2);
				maxStep= i==0?maxStep:step;
			}
		}
		return maxStep.step;
	}
	
	public int getScore(int[] actions) {
		int kill=0;
		int score=0;
		
		for(int action:actions){
			if(action==0){
				continue;
			}
			else if(action>=1 && action<=4){
				if(battleaxAi.attact(action)){
					for(int[]i:newFieldAfterOccupy(action)){
						kill=kill+isKill(i[0], i[1]);
					}
				}
				else{
					return 0;
				}
			}
			else if (action>=5 && action<=8) {
				if(battleaxAi.move(action)){
					
				}
				else{
					return 0;
				}
			}
			else if (action==9) {
				if(battleaxAi.hide()){
					
				}
				else{
					return 0;
				}
			}
			else if (action==10) {
				if(battleaxAi.show()){
					
				}
				else{
					return 0;
				}
			}
/*			
			if(action==1){       //南占领
				if(myState==1){
					return 0;
				}
				for(int i=Math.max(myRow-1, 0);i<=Math.min(myRow+1, 14);i++){      //棋盘范围外的无法占领
					for(int j=Math.max(myCol-1, 0);j<=Math.min(myCol+1, 14);j++){
						if((i==myRow)&&(j==myCol)){
							continue;
						}
						if((i==myRow-1)&&(j==myCol)){
							continue;
						}
						for(Home home:GameIniInformation.home){              //如果这个格子不是大本营，那么可以改变所有者
							if(!(home.rowOfHome==i && home.colOfHome==j)){
								battleField[i][j]=samuraiID;
							}
						}
						isKill(i,j);                                         //看看有没有敌人在这一格被剁死
					}
				}
			}

			else if(action==2){       //东占领
				if(myState==1){
					return 0;
				}
				for(int i=Math.max(myRow-1, 0);i<=Math.min(myRow+1, 14);i++){
					for(int j=Math.max(myCol-1, 0);j<=Math.min(myCol+1, 14);j++){
						if((i==myRow)&&(j==myCol)){
							continue;
						}
						if((i==myRow)&&(j==myCol-1)){
							continue;
						}
						for(Home home:GameIniInformation.home){              //如果这个格子不是大本营，那么可以改变所有者
							if(!(home.rowOfHome==i && home.colOfHome==j)){
								battleField[i][j]=samuraiID;
							}
						}
						isKill(i,j);
					}
				}
			}
			
			else if(action==3){      //北占领
				if(myState==1){
					return 0;
				}
				for(int i=Math.max(myRow-1, 0);i<=Math.min(myRow+1, 14);i++){
					for(int j=Math.max(myCol-1, 0);j<=Math.min(myCol+1, 14);j++){
						if((i==myRow)&&(j==myCol)){
							continue;
						}
						if((i==myRow+1)&&(j==myCol)){
							continue;
						}
						for(Home home:GameIniInformation.home){              //如果这个格子不是大本营，那么可以改变所有者
							if(!(home.rowOfHome==i && home.colOfHome==j)){
								battleField[i][j]=samuraiID;
							}
						}
						isKill(i,j);
					}
				}
			}
			
			else if(action==4){    //西占领
				if(myState==1){
					return 0;
				}
				for(int i=Math.max(myRow-1, 0);i<=Math.min(myRow+1, 14);i++){
					for(int j=Math.max(myCol-1, 0);j<=Math.min(myCol+1, 14);j++){
						if((i==myRow)&&(j==myCol)){
							continue;
						}
						if((i==myRow)&&(j==myCol+1)){
							continue;
						}
						for(Home home:GameIniInformation.home){              //如果这个格子不是大本营，那么可以改变所有者
							if(!(home.rowOfHome==i && home.colOfHome==j)){
								battleField[i][j]=samuraiID;
							}
						}
						isKill(i,j);
					}
				}
			}
			
			else if(action==5){   //南移动
				if(myRow==14 || existEnemy(myRow+1, myCol)){    //不能移动到棋盘外，或者敌人所在的格子
					return 0;
				}
				myRow++;
				if(myState==1&&battleField[myRow][myCol]>2){   //不能在隐身时移动到非己方的格子
					return 0;
				}
			}
			
			else if(action==6){   //东移动
				if(myCol==0 || existEnemy(myRow, myCol-1)){
					return 0;
				}
				myCol--;
				if(myState==1&&battleField[myRow][myCol]>2){
					return 0;
				}
			}
			
			else if(action==7){  //北移动
				if(myRow==0 || existEnemy(myRow-1, myCol)){
					return 0;
				}
				myRow--;
				if(myState==1&&battleField[myRow][myCol]>2){
					return 0;
				}
			}
			
			else if(action==8){   //西移动
				if(myCol==14 || existEnemy(myRow, myCol+1)){
					return 0;
				}
				myCol++;
				if(myState==1&&battleField[myRow][myCol]>2){
					return 0;
				}
			}
			
			else if(action==9){   //隐身
				if(battleField[myRow][myCol]>2){   //不能在非己方的格子隐身
					return 0;
				}
				myState=1;
			}
			
			else if(action==10){  //现身
				myState=0;
			}
*/			
			else{
				return 0;
			}
			
		}
		
		score=score+kill*10000;                            //暂定杀人加10000分
		for(int[] i:battleaxAi.getBattleField()){
			for(int j:i){
				if(j>=battleaxAi.getMe().team*3 && j<=battleaxAi.getMe().team*3+2){
					score=score+500;                      //暂定每有一块地加500分
				}
			}
		}
		for(int i=Math.max(battleaxAi.getMyRow()-1, 0);i<=Math.min(battleaxAi.getMyRow()+1, 14);i++){
			for(int j=Math.max(battleaxAi.getMyCol()-1, 0);j<=Math.min(battleaxAi.getMyCol()+1, 14);j++){
				if(battleaxAi.getBattleField()[i][j]<battleaxAi.getMe().team*3 
						|| battleaxAi.getBattleField()[i][j]>battleaxAi.getMe().team*3+2){
					score=score+400;                   //暂定周围格子中每有一块不是自己的地盘就加400分
				}
			}
		}
		if(battleaxAi.getState()==1){
			score=score+100;                             //暂定回合结束时隐身加100分
		}
		this.battleaxAi=cloneBattleaxAi(battleaxAi_backup);
		this.battleaxAi.setBattleField(battleaxAi.getBattleField());
		this.battleaxAi.setMe(battleaxAi.getMe());
		return score;
	}
	
	public ArrayList<int[]> newFieldAfterOccupy(int direction) {
		if(direction==1){
			return newFieldAfterOccupy("southward");
		}
		else if (direction==2) {
			return newFieldAfterOccupy("eastward");
		}
		else if (direction==3) {
			return newFieldAfterOccupy("northward");
		}
		else if (direction==4) {
			return newFieldAfterOccupy("westward");
		}
		return null;
	}
	
	public ArrayList<int[]> newFieldAfterOccupy(String direction){
		ArrayList<int[]> field=new ArrayList<int[]>();
		if(direction.equals("southward")){
			for(int row=Math.max(battleaxAi.getMyRow()-1, 0);row<=Math.min(battleaxAi.getMyRow()+1, 14);row++){//棋盘范围外的无法占领
				for(int col=Math.max(battleaxAi.getMyCol()-1, 0);col<=Math.min(battleaxAi.getMyCol()+1, 14);col++){
					if((row==battleaxAi.getMyRow())&&(col==battleaxAi.getMyCol())){
						continue;
					}
					if((row==battleaxAi.getMyRow()-1)&&(col==battleaxAi.getMyCol())){
						continue;
					}
					for(Home home:GameIniInformation.home){              //如果这个格子不是大本营，那么可以改变所有者
						if(home.rowOfHome==row && home.colOfHome==col){
							continue;
						}
					}
					field.add(new int[]{row,col});
				}
			}
		}
		else if(direction.equals("eastward")){
			for(int row=Math.max(battleaxAi.getMyRow()-1, 0);row<=Math.min(battleaxAi.getMyRow()+1, 14);row++){      //棋盘范围外的无法占领
				for(int col=Math.max(battleaxAi.getMyCol()-1, 0);col<=Math.min(battleaxAi.getMyCol()+1, 14);col++){
					if((row==battleaxAi.getMyRow())&&(col==battleaxAi.getMyCol())){
						continue;
					}
					if((row==battleaxAi.getMyRow())&&(col==battleaxAi.getMyCol()-1)){
						continue;
					}
					for(Home home:GameIniInformation.home){              //如果这个格子不是大本营，那么可以改变所有者
						if(!(home.rowOfHome==row && home.colOfHome==col)){
							field.add(new int[]{row,col});
						}
					}
				}
			}
		}
		else if(direction.equals("northward")){
			for(int row=Math.max(battleaxAi.getMyRow()-1, 0);row<=Math.min(battleaxAi.getMyRow()+1, 14);row++){      //棋盘范围外的无法占领
				for(int col=Math.max(battleaxAi.getMyCol()-1, 0);col<=Math.min(battleaxAi.getMyCol()+1, 14);col++){
					if((row==battleaxAi.getMyRow())&&(col==battleaxAi.getMyCol())){
						continue;
					}
					if((row==battleaxAi.getMyRow()+1)&&(col==battleaxAi.getMyCol())){
						continue;
					}
					for(Home home:GameIniInformation.home){              //如果这个格子不是大本营，那么可以改变所有者
						if(!(home.rowOfHome==row && home.colOfHome==col)){
							field.add(new int[]{row,col});
						}
					}
				}
			}
		}
		else if(direction.equals("westward")){
			for(int row=Math.max(battleaxAi.getMyRow()-1, 0);row<=Math.min(battleaxAi.getMyRow()+1, 14);row++){      //棋盘范围外的无法占领
				for(int col=Math.max(battleaxAi.getMyCol()-1, 0);col<=Math.min(battleaxAi.getMyCol()+1, 14);col++){
					if((row==battleaxAi.getMyRow())&&(col==battleaxAi.getMyCol())){
						continue;
					}
					if((row==battleaxAi.getMyRow())&&(col==battleaxAi.getMyCol()+1)){
						continue;
					}
					for(Home home:GameIniInformation.home){              //如果这个格子不是大本营，那么可以改变所有者
						if(!(home.rowOfHome==row && home.colOfHome==col)){
							field.add(new int[]{row,col});
						}
					}
				}
			}
		}
		return field;
	}
	
	public int isKill(int i,int j) {                 //若(i,j)有敌人且能被杀死，则杀敌数+1且返回true，否则false
		for(Samurai enemy:battleaxAi.getAllSamurai()){
			if(enemy.team!=battleaxAi.getMe().team && enemy.row==i && enemy.col==j && enemy.state==0){ //恢复中的敌人（enemy.state==-1）当成没看见
				return 1;
			}
		}
		return 0;
	}
/*	
	public boolean existEnemy (int row,int col) {       //若(i,j)有敌人，则返回true，否则false
		for(Samurai enemy:TurnInformation.nowAllSamurai){
			if(enemy.team!=myTeam && enemy.row==row && enemy.col==col ){
				return true;
			}
		}
		return false;
	}
*/
}
