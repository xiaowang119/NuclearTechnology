/*
 * 
 * 类的功能：    解析ENSDF数据库的信息
 * 作者:    王海东
 */

package ensdfparser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;

public class EnsdfParse {

	//ensdf--->评价核结构数据库
	private String ensdfDirectoryString;             //ENSDF数据库的文件路径
	private Vector<String> ensdfpathStrings;         //ensdf数据信息
	private Vector<Decay> decays;					 	 //衰变信息类
	private int indexof;                              //在读取能量的时候记录解析位置

	//在构造函数之中，实现数据的获取
	public EnsdfParse(String ensdfpath){

		decays = new Vector<Decay>();
		ensdfpathStrings = new Vector<String>();

		ensdfDirectoryString = ensdfpath;
		try {
			File ensdfFile = new File(ensdfpath);
			if(ensdfFile.isDirectory())
			{
				String[] ensdfNameStrings = ensdfFile.list();     //获取ENSDF文件下的所有数据文件

				//判断ENSDF文件下的文件是否全部为文件，并且将文件的路径存储起来
				for(int i=0;i<ensdfNameStrings.length;i++)
				{
					//之所以用双斜杠是考虑到了转义字符
					File isFile = new File(ensdfpath + "\\" + ensdfNameStrings[i]);
					if(isFile.isFile())
						ensdfpathStrings.add(ensdfpath + "\\" + ensdfNameStrings[i]);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
	}


	public Vector<Decay> getDecays() {
		return decays;
	}

	public void readFile()
	{
		//循环读取各个ENSDF文件中的信息
		int a = 0;                       		    //文件路径的游标
		while(true)
		{
			String [] ensdfmessageStrings = getEnsdfMessage(ensdfpathStrings.elementAt(a));        //获取某个文件的ENSDF中的全部数据
			parseFile(ensdfmessageStrings);
			//如果游标到达数组的末尾，则跳出循环
			System.out.println("已读"+Integer.toString(a+1)+"个文件");
			a++;
			if(a>=ensdfpathStrings.size())
				break;
		}
	}

	//解析ENSDF数据库
	private void parseFile(String[] message)
	{
		int index = 0;        //记录信息已经读取到的位置
		for(int i = 1;i<message.length;i++)
		{
			String[] tempStrings = splitBlank(message[i]);
			//下面的代码的目的某一段信息是否是衰变信息
			if(tempStrings.length>3)
			{
				if(message[i-1].trim().isEmpty())
				{
					if(tempStrings[3].contains("DECAY")){
						try
						{
						Decay decay  = new Decay();
						//设置子核
						Vector<String> daughterStrings = new Vector<String>();
						daughterStrings.add(ConvertNuclideToStandard(tempStrings[0]));
						decay.setDaughternuclide(daughterStrings);
						//设置母核
						decay.setParentnuclide(ConvertNuclideToStandard(tempStrings[1]));
						//设置衰变方式
						Vector<String> decaytypeStrings = new Vector<String>();
						decaytypeStrings.add(tempStrings[2]);
						decay.setDecaytype(decaytypeStrings);
						//设置半衰期
						String[] halfStrings = getHalfLife(message, i,tempStrings[1]);
						//i =Integer.parseInt(halfStrings[1]);
						decay.setHalflife(halfStrings[0]);
						//设置衰变几率
						String[] intenstiStrings = getDecayIntensity(message, i, tempStrings[0]);
						if(!intenstiStrings[1].isEmpty())
							i = Integer.parseInt(intenstiStrings[1]);   //将返回的位置转化为整型
						Vector<String> intensityStrings = new Vector<String>();
						intensityStrings.add(intenstiStrings[0]);    //返回的发射几率存储到矢量
						decay.setDecayIntensityVector(intensityStrings);
						//获取阿尔法射线的能量和强度
						Vector<Vector<String>> atemp = new Vector<Vector<String>>();
						atemp = getAenenergy(message, i);
						decay.setAenergyVector(atemp.elementAt(0));
						decay.setAintensityVector(atemp.elementAt(1));
						//获取γ射线能量和强度
						Vector<Vector<String>> gtemp = new Vector<Vector<String>>();
						gtemp = getGamma(message, i);
						decay.setGenergyVector(gtemp.elementAt(0));
						decay.setGintensityVector(gtemp.elementAt(1));
						if(decayType(tempStrings[2])==2)
						{
							//获取β射线的能量和强度
							Vector<Vector<String>> btemp = new Vector<Vector<String>>();
							btemp = getBeta(message, i);
							decay.setbEnergyVector(btemp.elementAt(0));
							decay.setbIntensityVector(btemp.elementAt(1));
						}else if(decayType(tempStrings[2])==1){
							//获取β+射线的能量和强度
							Vector<Vector<String>> ectemp = new Vector<Vector<String>>();
							ectemp = getElectronCapture(message, i);
							decay.setPostiveBEnergyVector(ectemp.elementAt(0));
							decay.setPostiveBIntensityVector(ectemp.elementAt(1));
						}else {
							//获取β射线的能量和强度
							Vector<Vector<String>> btemp = new Vector<Vector<String>>();
							btemp = getBeta(message, i);
							decay.setbEnergyVector(btemp.elementAt(0));
							decay.setbIntensityVector(btemp.elementAt(1));
							//获取β+射线的能量和强度
							Vector<Vector<String>> ectemp = new Vector<Vector<String>>();
							ectemp = getElectronCapture(message, i);
							decay.setPostiveBEnergyVector(ectemp.elementAt(0));
							decay.setPostiveBIntensityVector(ectemp.elementAt(1));
						}
						decays.add(decay);      //将某一条衰变链加入衰变信息表
						}catch(Exception e)
						{
							System.out.println(e.getMessage());
						}
					}
				}
			}
		}
	}
	/*
	 * 函数功能:      获取衰变的类别
	 * 返回值    :      1代表β+，2代表β-
	 */
	private int decayType(String str)
	{
		int type = 0;
		switch (str) {
		case "B-":	
			type = 2;
			break;
		case "B+":
			type = 1;
			break;
		case "EC":
			type = 1;
			break;
		case "B-A":
			type = 2;
			break;
		case "B-N":
			type = 2;
			break;
		case "B-2N":
			type = 2;
			break;
		case "B+P":
			type = 1;
			break;
		default:
			type = 0;
			break;
		}
		return type;
	}
	/*
	 * 函数功能:  获取某一条衰变的衰变几率
	 * 参数:     message:某一ENSDF文件的信息  i: 为已经解析到的位置
	 * 返回值:    返回值是一个字符串数组，数组的第一个保存的是衰变几率，第二个保存的是解析位置
	 */
	private String[] getDecayIntensity(String[] message,int i,String duaghterString)
	{
		String[] intensityStrings = new String[2];
		//实现判断某一行是否为空的目的是为了判断某一衰变信息的末尾位置
		intensityStrings[0] ="";//保存衰变几率
		intensityStrings[1] = Integer.toString(i);               //保存解析位置
		for(;!message[i].trim().isEmpty();i++)
		{
			//normalization record 记录中标志位1-5列保存得是子核，6-8保存的是“  N”
			if(message[i].substring(0,5).trim().equals(duaghterString)&&
					message[i].substring(5,8).equals("  N")){
				intensityStrings[0] = message[i].substring(31,39).trim();//保存衰变几率
				intensityStrings[1] = Integer.toString(i);               //保存解析位置
				break;
			}
		}
		return intensityStrings;
	}
	
	/*
	 * 函数功能:     实现从文件中读取α射线能量和强度
	 * 返回值    :     矢量组中第一个保存能量，第二个值保存发射几率
	 */
	
	private Vector<Vector<String>> getAenenergy(String[]message,int i)
	{
		Vector<Vector<String>>aiVectors = new Vector<Vector<String>>();
		Vector<String> enerStrings = new Vector<String>();         //保存α射线能量
		Vector< String> intensityStrings = new Vector<String>();   //保存α射线强度
		//下面的循环目的是获取α射线的能量和强度
		for(;!message[i].trim().isEmpty();i++)
		{
			//α射线记录必须跟随level记录
			if(isAlpha(message[i])&&isLevel(message[i-1]))
			{
				enerStrings.add(message[i].substring(9,19).trim());
				intensityStrings.add(message[i].substring(21,29).trim());
			}
		}
		aiVectors.add(enerStrings);
		aiVectors.add(intensityStrings);
		return aiVectors;
	}
	/*
	 * 函数功能:   获取伽马射线的能量和强度
	 * 返回值    :   矢量组中第一个保存能量，第二个值保存发射几率
	 */
	private Vector<Vector<String>> getGamma(String[]message,int i)
	{
		Vector<Vector<String>>giVectors = new Vector<Vector<String>>();
		Vector<String> enerStrings = new Vector<String>();         //保存γ射线能量
		Vector< String> intensityStrings = new Vector<String>();   //保存γ射线强度
		//获取能量和强度
		for(;!message[i].trim().isEmpty();i++)
		{
			if(isGamma(message[i]))
			{
				enerStrings.add(message[i].substring(9,19).trim());
				intensityStrings.add(message[i].substring(21,29).trim());
			}
		}
		giVectors.add(enerStrings);
		giVectors.add(intensityStrings);
		indexof = i;
		return giVectors;
	}
	/*
	 * 函数功能:   获取β-射线的能量和强度
	 * 返回值    :   矢量组中第一个保存能量，第二个值保存发射几率
	 */
	private Vector<Vector<String>> getBeta(String[]message,int i)
	{
		Vector<Vector<String>>giVectors = new Vector<Vector<String>>();
		Vector<String> enerStrings = new Vector<String>();         //保存β-射线能量
		Vector< String> intensityStrings = new Vector<String>();   //保存β-射线强度
		//获取能量和强度
		for(;!message[i].trim().isEmpty();i++)
		{
			if(isBeta(message[i]))
			{
				//获取β射线的能量
				if(message[i+1].startsWith(message[i].substring(0,5)+"S B EAV="))
				{
					intensityStrings.add(message[i].substring(21,29).trim());
					String energyString = getBetaEnergy(message[i+1]);
					enerStrings.add(energyString);
				}
			}
		}
		//将能量和强度加入到二位矢量组之中
		giVectors.add(enerStrings);
		giVectors.add(intensityStrings);
		indexof = i;     //记录某一衰变信息的末尾位置
		return giVectors;
	}
	
	/*
	 * 函数功能:     获取β射线的平均能量
	 * 返回值    ：              β射线平均能量
	 */
	private String getBetaEnergy(String str)
	{
		String averageString = "";
		String nameString = str.substring(0,5)+"S B EAV=";    //β平均射线的标志
		//将标志去除，然后字符串进行分割
		str =  str.replaceAll(nameString, "");
		String[] energyStrings=splitBlank(str);
		averageString = energyStrings[0];
		return averageString;
	}
	
	/*
	 * 函数功能:   获取β+射线的能量和强度
	 * 返回值    :   矢量组中第一个保存能量，第二个值保存发射几率
	 */
	private Vector<Vector<String>> getElectronCapture(String[]message,int i)
	{
		Vector<Vector<String>>eciVectors = new Vector<Vector<String>>();
		Vector<String> enerStrings = new Vector<String>();         //保存β+射线能量
		Vector< String> intensityStrings = new Vector<String>();   //保存β+射线强度
		//获取能量和强度
		for(;!message[i].trim().isEmpty();i++)
		{
			if(isElectronCapture(message[i]))
			{
				//获取β射线的能量
				if(message[i+1].startsWith(message[i].substring(0,5)+"S E EAV="))
				{
					intensityStrings.add(message[i].substring(21,29).trim());
					String energyString = getPostiveBetaEnergy(message[i+1]);
					enerStrings.add(energyString);
				}
			}
		}
		//将能量和强度加入到二位矢量组之中
		eciVectors.add(enerStrings);
		eciVectors.add(intensityStrings);
		indexof = i;     //记录某一衰变信息的末尾位置
		return eciVectors;
	}
	private String getPostiveBetaEnergy(String str)
	{
		String averageString = "";
		String nameString = str.substring(0,5)+"S E EAV=";    //β平均射线的标志
		//将标志去除，然后字符串进行分割
		str =  str.replaceAll(nameString, "");
		String[] energyStrings=splitBlank(str);
		averageString = energyStrings[0];
		return averageString;
	}
	
	/*
	 * 函数功能:    判断ENSDF文件的某一行是否是EC Record
	 * 返回值 :     返回值为true，代表是EC Record 
	 */
	private boolean isElectronCapture(String string) {
		boolean reslut = false;
		//level record的标志是7-9列是“ E ”
		if(string.substring(6,9).equals(" E "))
			reslut = true;
		return reslut;	
	}

	/*
	 * 函数功能:    判断ENSDF文件的某一行是否是Beta Record
	 * 返回值 :     返回值为true，代表是Beta Record 
	 */
	private boolean isBeta(String str)
	{
		boolean reslut = false;
		//level record的标志是7-9列是“ B ”
		if(str.substring(6,9).equals(" B "))
			reslut = true;
		return reslut;	
	}
	/*
	 * 函数功能:    判断ENSDF文件的某一行是否是Gamma Record
	 * 返回值 :     返回值为true，代表是Gamma Record 
	 */
	private boolean isGamma(String str)
	{
		boolean reslut = false;
		//level record的标志是7-9列是“ G ”
		if(str.startsWith(str.substring(0,5)+"  G "))
			reslut = true;
		return reslut;	
	}
	/*
	 * 函数功能:    判断ENSDF文件的某一行是否是Alpha Record
	 * 返回值 :     返回值为true，代表是Alpha Record 
	 */
	private boolean isAlpha(String message)
	{
		boolean reslut = false;
		//level record的标志是7-9列是“ A ”
		if(message.substring(6,9).equals(" A "))
			reslut = true;
		return reslut;	
	}
	/*
	 * 函数功能:    判断ENSDF文件的某一行是否是level record
	 * 返回值 :     返回值为true，代表是level record
	 */
	private boolean isLevel(String message)
	{
		boolean reslut = false;
		//level record的标志是7-9列是“ L ”
		if(message.substring(6,9).equals(" L ")||message.substring(6,9).equals("CL "))
			reslut = true;
		return reslut;
	}
	/*
	 * 函数功能:   获取半衰期
	 * 返回值    :   字符串数组为二元字符数组，字符数组的第一个是半衰期，第二个代表当前的游标
	 */
	private String[] getHalfLife(String[] message,int i,String parentnucilde)
	{
		String[] halfLifeString = new String[2];
		//String daughtrString = message[i].substring(0,4);   //获取子核的标志
		//获取母核的半衰期
		for(;!message[i].trim().isEmpty();i++)
		{
			if(message[i].substring(0, 5).trim().equals(parentnucilde)&&
					message[i].substring(5, 8).equals("  P")){
				halfLifeString[0] = message[i].substring(39,48).trim();
				halfLifeString[1] = Integer.toString(i);
				break;
			}
		}
		return halfLifeString;
	}
	/*
	 * 函数功能:   实现将核素的形式转变为如：46Cl 的形式
	 * 这个函数的实现没怎么看懂
	 */
	private String ConvertNuclideToStandard(String str)
	{
		String quality = getText(str);
		int length = quality.length();
		String daughterNameString;
		//判断元素符号的字母个数，个数为1时，则不变，个数为2时，将第二个字母转化为小写
		if(str.substring(length).length()>1){
			daughterNameString = str.substring(length,length+1)+str.substring(length+1).toLowerCase();	
		}else {
			daughterNameString = str.substring(length);
		}
		return quality+ daughterNameString;
	}
	/*
	 * 
	 * 函数功能：     获取字符串中的数字
	 * 参数：             需要提出数字的字符串
	 */
	private String getText(String str){
		String temp = "";
		for(int i = 0;i<str.length();i++){
			if(str.charAt(i)>=48&&str.charAt(i)<=57)
				temp = temp+str.charAt(i);
		}
		return temp;
	} 
	/*
	 * 函数功能：  实现用空格分割字符串，空格不限
	 * 参数       ：   需要分割的字符串
	 */
	private String[] splitBlank(String str)
	{
		String[] temp = str.split(" ");
		
		Vector<String> messageStrings = new Vector<String>();    //暂时存储分割之后的字符串
		
		for(int i = 0;i<temp.length;i++){
			if(!temp[i].isEmpty())
				messageStrings.add(temp[i]);
		}
		//下面的代码是将缓存之中的数据保存到字符串数组之中
		String[] message = new String[messageStrings.size()];
		for(int i = 0;i<messageStrings.size();i++){
			message[i] = messageStrings.elementAt(i); 
		}
		return message;
	}	
	/*
	 * 函数功能:   获取某个ENSDF文件中的全部信息，并且全部存储在字符串数组之中
	 * 参数      :   某个文件的路径
	 */
	private String[] getEnsdfMessage(String filePath)
	{
		//使用可变长度的字符串存储相关信息
		Vector<String> messageStrings = new Vector<String>();
		try {
			//文件读取的相关定义
			FileInputStream fileInputStream =new FileInputStream(new File(filePath));
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			
			//通过循环获取文件中的所有信息
			String str;
			try {
				while((str = bufferedReader.readLine())!=null)
				{
					messageStrings.add(str);    //向Vector之中添加数据
				}
				fileInputStream.close();
				inputStreamReader.close();
				bufferedReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//将可变字符串数组转换成为一个字符串数组
		String []messageString = new String[messageStrings.size()];
		for(int i = 0;i < messageString.length;i++)
		{
			messageString[i] = messageStrings.elementAt(i);
		}
		
		return messageString;
	}
}