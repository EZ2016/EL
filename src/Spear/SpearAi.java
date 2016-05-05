package EZ;
//关于武士矛的命令分析
//返回的String order是完整的命令
//ZHU YINGSHAN
public class SpearAi {
	
	TurnInformationReceive tir=new TurnInformationReceive();
	TurnInformation ti=new TurnInformation();//获取战场信息
	
	Samurai me=ti.nowAllSamurai.get(0);//对象我
	Samurai enspear=ti.nowAllSamurai.get(3);//敌方矛
	Samurai enbattleax=ti.nowAllSamurai.get(5);//敌方斧
	Samurai ensword=ti.nowAllSamurai.get(4);//敌方剑
	
    public String order=null;//最终输出的命令
	int col=me.col;//列
	int row=me.row;//行
	int energy=7;//行动力为7
	public int recover=ti.myRecoverRound;//如果武士没有受伤，恢复周期的数值就是0，这个武士可以执行自己的行动。 
	int state=me.state;/* 对于敌方来说，无法判断他们是隐⾝身了还是在己方视野之 外，这两种情况下状态都会表示为1*/


	public void analyseEnemy() {//分析敌人
		String killString=null;//用killstring表示杀人命令的集合
		killString=kill(enspear, killString);
		if (!killString.equals(null)) {//先杀矛
			if (state==1) {//假如隐身的话先现身再杀矛
				showOrHide();
			}
			order=order+killString+" ";
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
	        //判断是否能隐身
         boolean canHide(){
	         if (ti.battleField[me.row][me.col]>2) {
		         return false;
	          }
	             return true;
       }
         //改变现在的状态，隐身或是现形。
	private void showOrHide() {
		// TODO Auto-generated method stub
		if (state==1) {//隐身变成不隐身
			order=order+10+" ";
			energy-=1;
		}else {
			order=order+9+" ";
			energy-=1;//不隐身变成隐身
		}
	}

	void analyseEnemyCourt() {//分析在一步攻击以外其他地方的敌方武士
		// TODO Auto-generated method stub

		String killString=null;
		killString=killCourt(enspear, killString);
		if (state==1) {//现形
				showOrHide();
		}
		if (!killString.equals(null)) {
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
	
	 String kill(Samurai samurai,String killString){
	    if (samurai.state==1) {//当武士的状态未知时，返回原字符串
		return killString;
	}
	    if (samurai.row==row&&(samurai.col-col)<5&&samurai.col-col>0) {//1234左右上下，一步杀死
		killString=killString+2+" ";
	}   if (samurai.row==row&&(samurai.col-col)<0&&samurai.col-col>-5) {
		killString=killString+1+" ";
	}   if (samurai.col==col&&samurai.row-row<0&&samurai.col-col>-5) {
		killString=killString+3+" ";
	}if (samurai.col==col&&samurai.row-row<5&&samurai.col-col>0) {
		killString=killString+4+" ";
	}
		return killString;
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
		if (order.length()==0) {//行动力为7
			occupy();
		}
	}
//简单的占领指令
	private void occupy() {
		// TODO Auto-generated method stub
		if (state==1) {
			showOrHide();
		}
		order=(1+(int)(Math.random()*4))+"";//随机占领
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
				return	(Math.abs(samurai.col-j)+Math.abs(samurai.row-i))<1?true:false;		}
		}
		return false;
	}	
}