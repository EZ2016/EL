package Spear;

import EZ.GameIniInformation;
import EZ.Home;
import EZ.Samurai;
import EZ.TurnInformation;

//关于武士矛的命令分析
//返回的String order是完整的命令
//ZHU YINGSHAN
public class SpearAi {
	
    int teamId;
	int enspearID,enswordID,enbattleaxID;
	Samurai me;//对象我
	Samurai enspear;//敌方矛
	Samurai enbattleax;//敌方斧
	Samurai ensword;//敌方剑
	public int type;
	public String order="";//最终输出的命令
	int col;//列
	int row;//行
	public int energy=7;//行动力为7
	public int recover;//如果武士没有受伤，恢复周期的数值就是0，这个武士可以执行自己的行动。 
	public int state;/* 对于敌方来说，无法判断他们是隐⾝身了还是在己方视野之 外，这两种情况下状态都会表示为1*/
    int[][] battlefield;
    
	public SpearAi(int teamID) {//构造方法
		// TODO Auto-generated constructor stub  
		teamId=GameIniInformation.teamID;//0 or 1
		me=TurnInformation.nowAllSamurai.get(GameIniInformation.weapon);//输入武士中的第一个
	    enspearID=3;
		enswordID=enspearID+1;
		enbattleaxID=enswordID+1;
        enspear=TurnInformation.nowAllSamurai.get(3);
        ensword=TurnInformation.nowAllSamurai.get(4);
        enbattleax=TurnInformation.nowAllSamurai.get(5);
        col=me.col;
        row=me.row;
        recover=TurnInformation.myRecoverRound;
        state=me.state;
        battlefield=TurnInformation.battleField;
     }
	
	public int  onClothes() {//判断类型1234下右上左
		int a=GameIniInformation.home.get(teamId).colOfHome;
		int b=GameIniInformation.home.get(teamId).rowOfHome;
		if(GameIniInformation.home.get(teamId*3).colOfHome==0)return 4;//根据家的位置判断类型
		if (GameIniInformation.home.get(teamId*3).colOfHome==14)return 2;
		if(GameIniInformation.home.get(teamId*3).rowOfHome==0)return 3;
		if (GameIniInformation.home.get(teamId*3).rowOfHome==14)return 1;
		return 0;
	}
	
	public void  action() {//返回所有行动的字符串
		String str=cankill();
		if (str.length()==0) {
			occupy();//不能杀人，占领
		}
        else {//能杀人
		order=order+str;
		energy-=4;
		}
	}
	
	private void showOrHide() {
		// TODO Auto-generated method stub
		if (state==1) {//隐身变成不隐身
			order=order+10+" ";
			energy-=1;
			state=0;
		}else {
			order=order+9+" ";
			energy-=1;//不隐身变成隐身
			state=1;
		}
	}
	
	boolean inField(int i,int j){//i,j是以武士为原点的坐标
		int grade=0;//满足两种条件的时候返回true
		if (i>=0&&col+i<15) {
			grade++;
		}
		if (i<0&&col+i>=0) {
			grade++;
		}
		if (j>=0&&row-j>=0) {
			grade++;
		}
		if (j<0&&row-j<15) {
			grade++;
		}
		if (grade>1) {
			return true;
		}
		return false;
		
	}
	
	 //判断是否能隐身&&me.row!=GameIniInformation.home.get(teamId*3).rowOfHome&&me.col!=GameIniInformation.home.get(teamId*3).colOfHome
    public boolean canHide(){
        if (battlefield[me.row][me.col]>=(teamId*3)&&battlefield[me.row][me.col]<=(teamId*3+2)) {
	         return false;
         }
            return true;
  }
	
    public void occupy() {//能量为7
		switch (type) {
		case 1:
			String s1=type1();
			order=order+s1;
			break;
		case 2:
			String s2=type2();
			order=order+s2;
			break;
		case 3:
			String s3=type3();
			order=order+s3;
			break;
		case 4:
			String s4=type4();
			order=order+s4;
			break;
		default:
			break;
		}
	}
	private String type1() {//在下边界的时候
		String occuString="";
		int score;
		int bestdirection=0;
		int maxscore=0;
		if (inField(0, 1)&&battlefield[row-1][col]==8||battlefield[row-1][col]==enbattleaxID||battlefield[row-1][col]==enswordID||battlefield[row-1][col]==enspearID) {
			if (state==1) {
				showOrHide();
			}
			occuString="3 ";//north
		  }else {
			for (int i = 1; i < 3; i+=2) {//4231左右上下
				score=0;
				for (int j = 1; j < 5; j++) {
					switch (i) {
					case 4:	if (inField(-j,0)) {//向左占领
						if (battlefield[row][col-j]==enbattleaxID||battlefield[row][col-j]==enspearID||battlefield[row][col-j]==enswordID||battlefield[row][col-j]==8) {
							score++;
						}
					}
						break;
					case 2:if (inField(j,0)) {//向右占领
						if (battlefield[row][col+j]==enbattleaxID||battlefield[row][col+j]==enspearID||battlefield[row][col+j]==enswordID||battlefield[row][col+j]==8) {
							score++;
						}
					}
						break;
					
					}
				}
				
				if(score>maxscore){
					maxscore=score;
					bestdirection=i;
				}
			}
		  if (bestdirection==0) {//两边都被占领的情况
			row++;
			energy-=2;
			occuString="7 "+type1();
			return occuString;
		  }else {
			  if (state==1) {
				showOrHide();
			}
			occuString=bestdirection+" ";
		    return occuString;
		   }
	    }
	return occuString;
	}

	private String type3() {//在上边界的时候
		String occuString="";
		int score;
		int bestdirection=0;
		int maxscore=0;
		if (inField(0, -1)&&battlefield[row+1][col]==8||battlefield[row+1][col]==enbattleaxID||battlefield[row+1][col]==enswordID||battlefield[row+1][col]==enspearID) {//下一步是未占领的
			if (state==1) {
				showOrHide();
			}
			occuString="1 ";//south
		  }else {//下一步是占领的,占领旁边的
			for (int i = 2; i < 5; i+=2) {//1234下右上左
				score=0;
				for (int j = 1; j < 5; j++) {
					switch (i) {
					case 2:	if (inField(j,0)) {
						if (battlefield[row][col+j]==enbattleaxID||battlefield[row][col+j]==enspearID||battlefield[row][col+j]==enswordID||battlefield[row][col+j]==8) {
							score++;
						}
					}
						break;
					case 4:if (inField(-j,0)) {
						if (battlefield[row][col-j]==enbattleaxID||battlefield[row][col-j]==enspearID||battlefield[row][col-j]==enswordID||battlefield[row][col-j]==8) {
							score++;
						}
					}
						break;
					}
				}
				
				if(score>maxscore){
					maxscore=score;
					bestdirection=i;
				}
			}
		if (bestdirection==0) {//两边都被占领的情况
			row++;
			energy-=2;
			occuString="5 "+type3();
			return occuString;
		}else {
			if (state==1) {
				showOrHide();
			}
			occuString=bestdirection+" ";
		    return occuString;
		}
		  }
		return occuString;
	}

	
	private String type2() {//in the east bord
		String occuString="";
		int score;
		int bestdirection=0;
		int maxscore=0;
		if (inField(-1, 0)&&battlefield[row][col-1]==8||battlefield[row][col-1]==enbattleaxID||battlefield[row][col-1]==enswordID||battlefield[row][col-1]==enspearID) {
			if (state==1) {
				showOrHide();
			}
			occuString="4 ";
		  }else {
			for (int i = 1; i < 5; i+=2) {//1234左右上下
				score=0;
				for (int j = 1; j < 5; j++) {
					switch (i) {
					case 3:if (inField(0,j)) {//north occupy
						if (battlefield[row-j][col]==enbattleaxID||battlefield[row-j][col]==enspearID||battlefield[row-j][col]==enswordID||battlefield[row-j][col]==8) {
							score++;
						}
					}
						break;
					case 1:if (inField(0,-j)) {//south occupy
						if (battlefield[row+j][col]==enbattleaxID||battlefield[row+j][col]==enspearID||battlefield[row+j][col]==enswordID||battlefield[row+j][col]==8) {
							score++;		
						}
					}
						break;
					
					}
				}
				
				if(score>maxscore){
					maxscore=score;
					bestdirection=i;
				}
			}	
		if (bestdirection==0) {//两边都被占领的情况
			col--;
			energy-=2;
			occuString="8 "+type2();//move west
			return occuString;
		}else {
			if (state==1) {
				showOrHide();
			}
			occuString=bestdirection+" ";
			energy-=4;
		return occuString;
		}
		  }
		return occuString;
	}

	private String type4() {//在左边界
		String occuString="";
		int score;
		int bestdirection=0;
		int maxscore=0;
	    if (inField(1, 0) && battlefield[row][col + 1] == 8
					|| battlefield[row][col + 1] == enbattleaxID
					|| battlefield[row][col + 1] == enswordID
					|| battlefield[row][col + 1] == enspearID) {
				if (state==1) {
					showOrHide();
					energy-=1;
				}
				occuString = "2 ";
				energy -= 4;
			} else {
				for (int i = 1; i < 5; i += 2) {
					score = 0;//每一回合的分数清零
					for (int j = 1; j < 5; j++) {
						switch (i) {
						case 1:
							if (inField(0, -j)) {//South occupy
								if (battlefield[row + j][col] == enbattleaxID
										|| battlefield[row + j][col] == enspearID
										|| battlefield[row + j][col] == enswordID
										|| battlefield[row + j][col] == 8) {
									score++;
								}
							}
							break;
						case 3:
							if (inField(0, j)) {//north occupy
								if (battlefield[row - j][col] == enbattleaxID
										|| battlefield[row - j][col] == enspearID
										|| battlefield[row - j][col] == enswordID
										|| battlefield[row - j][col] == 8) {
									score++;
								}
							}
							break;

						}
					}

					if (score > maxscore) {
						maxscore = score;
						bestdirection = i;
					}
				}

			
			if (bestdirection == 0) {//north and south can not be occupied,east move
				col++;
				energy -= 2;
				occuString = "6 " + type4();//only east move
				return occuString;
			} else {
				if (state==1) {
					showOrHide();
					energy-=1;}
				occuString = bestdirection + " ";
				energy -= 4;
				return occuString;
			}
		}
	    System.out.println(occuString);
	    return occuString;
	}

	private String  cankill() {//返回能杀死的字符串
		String killString="";//用killstring表示杀人命令的集合
		killString=kill(enspear, killString);
		if (!killString.equals("")) {//先杀矛
			if (state==1) {//假如隐身的话先现身再杀矛
				showOrHide();
				state=0;}
			return killString;
		}else {//如果不能杀矛，在判断其他的武士
			killString=kill(ensword, killString);//分析斧子和剑
			killString=kill(enbattleax, killString);
			switch (killString.length()) {
			case 0://此时string中没有值，移动一格后去占领
				   break;
			case 1:return killString;
			case 2:killString=""+killString.charAt((int )(0+Math.random()*2));//此处是随机(数据类型)(最小值+Math.random()*(最大值-最小值+1)),随机杀死。
			       return killString;
			default:
				   break;
				}
			}
		return killString;
	}
 
	
	
	
	String kill(Samurai samurai,String killString){//把杀一个武士的字符串加在killstring的后面
		int a=samurai.row;
		int b=samurai.col;
		int c=samurai.col-col;
		
	    if (samurai.state==1) {//当武士的状态未知时，返回原字符串
		return killString;}
	    if (samurai.row==row&&(samurai.col-col)<5&&samurai.col-col>0) {//4231左右上下，一步杀死
		   killString=killString+2+" ";}
	    if (samurai.row==row&&(samurai.col-col)<0&&samurai.col-col>-5) {
		   killString=killString+4+" ";}
	    if (samurai.col==col&&samurai.row-row<0&&samurai.row-row>-5) {
		   killString=killString+3+" ";}
	    if (samurai.col==col&&samurai.row-row<5&&samurai.row-row>0) {
		   killString=killString+1+" ";	}
		return killString;

	}
}
/*
	public void analyseEnemy() {//分析敌人
		
		
		String killString="";//用killstring表示杀人命令的集合
		killString=kill(enspear, killString);
		if (!killString.equals("")) {//先杀矛
			if (state==1) {//假如隐身的话先现身再杀矛
				showOrHide();
				state=0;
			}
			order=order+killString;
			energy-=4;
		  }else {//如果不能杀矛，在判断其他的武士
			killString=kill(ensword, killString);//分析斧子和剑
			killString=kill(enbattleax, killString);
			switch (killString.length()) {
			case 0://此时string中没有值，移动一格后去占领
				analyseEnemyCourt();
				break;
			case 1:order=order+killString;
			energy-=4;
				break;
			case 2:order=order+killString.charAt((int )(0+Math.random()*2));//此处是随机(数据类型)(最小值+Math.random()*(最大值-最小值+1)),随机杀死。
			    energy-=4;
			    break;
			default:
				break;
		}
			}
	}
	
	
	       
         
         
         //改变现在的状态，隐身或是现形。
	

	void analyseEnemyCourt() {//分析在一步攻击以外其他地方的敌方武士
		// TODO Auto-generated method stub

		String killString="";
		killString=killCourt(enspear, killString);
		if (state==1) {//现形
				showOrHide();
				state=0;
		}
		if (!killString.equals("")) {
			order=order+killCourt(enspear, killString);//先分析矛
		}else {
			killString=killCourt(ensword,killString);
		killString=killCourt(enbattleax,killString);
		switch (killString.length()) {
		case 0://怎么移动也不能杀死，主函数中交给下一步；
			break;
		case 1:order=order+killString;
		energy-=4;
			break;
		case 2:order=order+killString.charAt((int )(0+Math.random()*2));//此处是随机(数据类型)(最小值+Math.random()*(最大值-最小值+1)),随机杀死。
		energy-=4;
		break;
		case 3:order=order+killString.charAt((int )(0+Math.random()*3));
		energy-=4;
			break;
		default:
			break;
		       }
		}
	}
	
	
	
String killCourt(Samurai samurai, String killString) {	//走一步杀死 TODO Auto-generated method stub
		 if (samurai.state==1) {//当武士的状态未知时，不操作
			
			}else if ((Math.abs(samurai.row-row)+Math.abs(samurai.col-col))>5||((Math.abs(samurai.row-row)>2)&&Math.abs(samurai.col-col)>2)) {
				//在攻击范围之外，不操作
			}else {
				int i=samurai.row-row;
				int j=samurai.col-col;
				energy-=4;//行动力减去4
				if (i==-1) {
					if (j<0) killString=killString+"7 1 ";
					if(j>0) killString=killString+"7 2 ";
				}
				else if (i==1) {
					if(j<0) killString=killString+"8 1 ";
					if(j>0)killString=killString+"8 2 ";
				}else if(j==-1){
					if(i<0) killString=killString+"5 3 ";
					if(i>0) killString=killString+"5 4 ";
				}else if (j==1) {
					if(i>0)killString=killString+"6 4 ";
					if(i<0)killString=killString+"6 3 ";
						}
				}
				return killString;
			}
//必躲：分析自己的位置
	public void analyseMe() {
		// TODO Auto-generated method stub	
		if (energy==0) {
			return;//行动力为0，等死
		}
		if (energy==1) {
			if (state==0) {
				if (canHide()) {
					showOrHide();//如果能隐身就隐身,躲不掉怎么办？!!!
					state=0;
					return;
				}
			}
		}
		boolean[] a=new boolean[3];
		a[0]=within(enspear,row,col);//敌方矛
		a[1]=within(ensword,row,col);//敌方剑
		a[2]=within(enbattleax,row,col);//敌方斧子
		if (a[1]||a[2]||a[0]) {
			String string=MoveAction(a);	//string为必躲的命令
			order=order.concat(string);
		}
		if (energy==3) {
			move();
			return;
		}
		if (energy<3) {
			showOrHide();
			state=0;
			return;
		}
		if (energy>4) {//行动力为7
			easyOccupy();
		}
	}
	
       //简单的占领指令
	private void easyOccupy() {
		// TODO Auto-generated method stub
		if (state==1) {
			showOrHide();
			state=0;
		}
		int bestdirection=1;
		int maxscore=0;
		int [] score={0,0,0,0};
		for (int i = 1; i < 5; i++) {//1234左右上下
			for (int j = 1; j < 5; j++) {
				switch (i) {
				case 1:	if (inField(-j,0)) {
					if (battlefield[row][col-j]==enbattleaxID||battlefield[row][col-j]==enspearID||battlefield[row][col-j]==enswordID||battlefield[row][col-j]==8) {
						score[i-1]++;
					}
				}
					break;
				case 2:if (inField(j,0)) {
					if (battlefield[row][col+j]==enbattleaxID||battlefield[row][col+j]==enspearID||battlefield[row][col+j]==enswordID||battlefield[row][col+j]==8) {
						score[i-1]++;
					}
				}
					break;
				case 3:if (inField(0,j)) {
					if (battlefield[row-j][col]==enbattleaxID||battlefield[row-j][col]==enspearID||battlefield[row-j][col]==enswordID||battlefield[row-j][col]==8) {
						score[i-1]++;
					}
				}
					break;
				case 4:if (inField(0,-j)) {
					if (battlefield[row+j][col]==enbattleaxID||battlefield[row+j][col]==enspearID||battlefield[row+j][col]==enswordID||battlefield[row+j][col]==8) {
						score[i-1]++;
						
					}
				}
					break;
				
				}
			}
			if(score[i-1]>maxscore){
				maxscore=score[i-1];
				bestdirection=i;
			}
		}
		loop:
			for (int i = 0; i < score.length; i++) {
			if (score[i]>1||energy<=6) {
				order=order+bestdirection+" ";//随机占领
				energy-=4;
				break loop;
			}
			if (i==3&&energy>5) {
				order=order+bestdirection+" ";//随机占领
				if (teamId==0) {
					order=order+"2 6 ";energy-=6;col++;
				}else {
					order=order+"1 5 ";energy-=6;row--;
				}
				
			}
		}
		//移动后占领
	}
	
	private void move() {
		// TODO Auto-generated method stub
		int bestdirection=1;
		int maxscore=0;
		int [] score={0,0,0,0};
		
		
		for (int i = 5; i < 9; i++) {//1234左右上下
			for (int j = 1; j < 5; j++) {
				switch (i) {
				case 5:	
					if (inField(-j,0)&&inField(-1, 0)) {
					if (battlefield[row][col-j]==enbattleaxID||battlefield[row][col-j]==enspearID||battlefield[row][col-j]==enswordID||battlefield[row][col-j]==8) {
						score[0]++;
					}
				}
					break;
				case 6:if (inField(j,0)&&inField(1, 0)) {
					if (battlefield[row][col-j]==enbattleaxID||battlefield[row][col-j]==enspearID||battlefield[row][col-j]==enswordID||battlefield[row][col-j]==8) {
						score[1]++;
					}
				}
					break;
				case 7:if (inField(0,j)&&inField(0, 1)) {
					if (battlefield[row][col-j]==enbattleaxID||battlefield[row][col-j]==enspearID||battlefield[row][col-j]==enswordID||battlefield[row][col-j]==8) {
						score[2]++;
					}
				}
					break;
				case 8:if (inField(0,-j)&&inField(0, -1)) {
					if (battlefield[row][col-j]==enbattleaxID||battlefield[row][col-j]==enspearID||battlefield[row][col-j]==enswordID||battlefield[row][col-j]==8) {
						score[3]++;		
					}
				}
					break;
				}
			}
			if(score[i-5]>maxscore){
				maxscore=score[i-5];
				bestdirection=i;
			
			}
		}
		energy-=2;
		order=order+bestdirection+" ";
		
	}


	private String MoveAction(boolean[] a) {//24种移动方式
		// TODO Auto-generated method stub
		String string=new String();
		loop1:for (int i = -3; i < 4; i++) {
			for (int j = -3; j < 4; j++) {
				if (Math.abs(i+j)<4) {
					if (!(within(enbattleax, row+i, col+j)&&within(enspear, row+i, col+j)&&within(ensword, row+i, col+j))) {//不在他们的攻击范围	
						if (Math.abs(j)+Math.abs(i)<(energy/2)) {//这里还可以改进,隐身能不能走到别人上面
							string=moveOrder(i,j);//i,j是以矛为原点的坐标
						}
						break loop1;
					}
				}
			}
		}
		return string;
	}

	private String moveOrder(int i, int j) {//移动命令
		// TODO Auto-generated method stub
		String moveOrder=new String();
		switch (i) {
		case -3:
			moveOrder=moveOrder+"3 3 3 ";
			break;
		case -2:
			moveOrder=moveOrder+"3 3 ";
			break;
		case -1:moveOrder=moveOrder+"3 ";
			break;
		case 1:moveOrder=moveOrder+"4 ";
		break;
		case 2:
			moveOrder=moveOrder+"4 4 ";
		case 3:
			moveOrder=moveOrder+"4 4 4 ";
			break;
		default:
			break;
		}
		switch (j) {
		case -3:
			moveOrder=moveOrder+"1 1 1 ";
			break;
		case -2:
			moveOrder=moveOrder+"1 1 ";
			break;
		case -1:moveOrder=moveOrder+"1 ";
			break;
		case 1:moveOrder=moveOrder+"2 ";
		break;
		case 2:
			moveOrder=moveOrder+"2 2 ";
		case 3:
			moveOrder=moveOrder+"2 2 2 ";
			break;
		default:
			break;
		}
		return moveOrder;
	}
	
//是否在该武士的攻击范围以内,ij代表我的坐标
	private boolean within(Samurai samurai,int i,int j) {
		if (samurai==enspear) {
			if (samurai.col==j||samurai.row==i) 
				return (Math.abs(samurai.col-j)+Math.abs(samurai.row-i))<4?true:false;//在范围内返回true
		}else {
			if (samurai==ensword) {
				return (Math.abs(samurai.col-j)+Math.abs(samurai.row-i))<2?true:false;
			}else {
				return	(Math.abs(samurai.col-j)+Math.abs(samurai.row-i))<1?true:false;		
				}
		}
		return false;
	}	
}*/