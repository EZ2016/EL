package Battleax;
import EZ.GameIniInformation;
import EZ.Home;
import EZ.Samurai;
import EZ.TurnInformation;
/*斧头武士的评分系统
 * v1.0
 * by 俊毅
 */
//注意：分数为0表示这个action不成立
public class GradingActions{
	
	private int score=0;
	private static final int samuraiID=GameIniInformation.samuraiID;       //斧头武士的评分系统
	private static final int weapon=GameIniInformation.weapon;
	private int myTeam=GameIniInformation.teamID;
	private int kill=0;
	
	public int getScore(int[] actions) {
		int myRow=TurnInformation.nowAllSamurai.get(weapon).row;
		int myCol=TurnInformation.nowAllSamurai.get(weapon).col;
		int myState=TurnInformation.nowAllSamurai.get(weapon).state;
		int battleField[][]=new int[TurnInformation.battleField.length][TurnInformation.battleField[0].length];
		int turnNum=TurnInformation.turnNum;
		int myRecoverRound=TurnInformation.myRecoverRound;
		for(int i=0;i<TurnInformation.battleField.length;i++){
			for(int j=0;j<TurnInformation.battleField[0].length;j++){
				battleField[i][j]=TurnInformation.battleField[i][j];
			}
		}
		score=0;
		
		for(int action:actions){
			if(action==0){
				continue;
			}
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
			
			else{
				return 0;
			}
			
		}
		
		score=score+kill*10000;                            //暂定杀人加10000分
		for(int[] i:battleField){
			for(int j:i){
				if(j>=myTeam*3 && j<=myTeam*3+2){
					score=score+500;                      //暂定每有一块地加500分
				}
			}
		}
		for(int i=Math.max(myRow-1, 0);i<=Math.min(myRow+1, 14);i++){
			for(int j=Math.max(myCol-1, 0);j<=Math.min(myCol+1, 14);j++){
				if(battleField[i][j]<myTeam*3 || battleField[i][j]>myTeam*3+2){
					score=score+400;                   //暂定周围格子中每有一块不是自己的地盘就加400分
				}
			}
		}
		if(myState==1){
			score=score+100;                             //暂定回合结束时隐身加100分
		}
		return score;
	}
	
	public boolean isKill(int i,int j) {                 //若(i,j)有敌人且能被杀死，则杀敌数+1且返回true，否则false
		for(Samurai enemy:TurnInformation.nowAllSamurai){
			if(enemy.team!=myTeam && enemy.row==i && enemy.col==j && enemy.state==0){ //恢复中的敌人（enemy.state==-1）当成没看见
				kill++;
				return true;
			}
		}
		return false;
	}
	
	public boolean existEnemy (int row,int col) {       //若(i,j)有敌人，则返回true，否则false
		for(Samurai enemy:TurnInformation.nowAllSamurai){
			if(enemy.team!=myTeam && enemy.row==row && enemy.col==col ){
				return true;
			}
		}
		return false;
	}

}
