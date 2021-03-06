package EZ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


/*
 * Receive turn information of game
 *游戏回合信息获取， 根据提示一行一行读
 */
public class TurnInformationReceive {		
		public static int []turnInformation=new int[20];
		public static int [][]battleField=new int[GameIniInformation.widthOfBf][GameIniInformation.heightOfBf];
		
	public static void tuReceive() {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		try {
			String tip1=br.readLine();
			String tip2=br.readLine();
			if(tip1.equals("# Turn information")&&tip2.equals("# <turn>")){
				turnInformation[0]=Integer.parseInt(br.readLine());		
			}
			
			String tip3=br.readLine();
			if(tip3.equals("# <cure period>")){
				turnInformation[1]=Integer.parseInt(br.readLine());	
			}
			
			String tip4=br.readLine();
			if(tip4.equals("# Samuiai states")){
				for(int i=0;i<6;i++){
					String[]a=br.readLine().split(" ");
					turnInformation[2+3*i]=Integer.parseInt(a[0]);
					turnInformation[3+3*i]=Integer.parseInt(a[1]);
					turnInformation[4+3*i]=Integer.parseInt(a[2]);				
				}
			}
			
			String tip5=br.readLine();
			if(tip5.equals("# Battle field states")){
				for(int i=0;i<GameIniInformation.heightOfBf;i++){
					String []b=br.readLine().split(" ");
					for(int j=0;j<GameIniInformation.widthOfBf;j++){
						battleField[i][j]=Integer.parseInt(b[j]);						
					}
				}					
			}
   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
