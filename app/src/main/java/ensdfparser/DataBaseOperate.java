package ensdfparser;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class DataBaseOperate{

	public static final String CREATE_TABLE_DECAY =
			"CREATE	TABLE if not exists decaymessage("
			+ "ParentMassNumber text,"
			+ "ParentName text,"
			+"ParentAtomNumber text,"
			+"ParentAtomMass text,"
			+"ParentChineseName text,"
			+ "DaughterMassNumber text,"
			+ "DaughterName text,"
			+ "DaughterAtomNumber text,"
			+"DaughterAtomMass text,"
			+"DaughterChineseName text,"
			+ "HalfLife text,"
			+ "DecayType text,"
			+ "DecayIntensity text,"
			+ "GEnergy text,"
			+ "GIntensity text,"
			+ "BEnergy text,"
			+ "BIntensity text,"
			+ "AEnergy text,"
			+ "AIntensity text,"
			+ "PostiveBEnergy text,"
			+ "PostiveBIntensity text"
			+");";
	public static final  String QUERY_PARENT = "select * from decaymessage where ParentMassNumber=? and ParentName=?;";  //查询sql语言
	public static final String DELETE_SAMEPARENTNUCLIDE = "delete from decaymessage where ParentMassNumber=? and ParentName=?;"; //删除SQL语言
	public static final String INSERT_DECAY = "insert into decaymessage values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
	public static final String CLASSNAME= "org.sqlite.JDBC";
	private String databasepathString;    //数据库路径
	private String sqlString;
	private Vector<SqliteDecay> saveDecays;
	private Vector<Decay> singleDecays;
	private Connection conn;
	private Statement statement;
	private boolean load;
	//对数据库是否存在进行判断
	public DataBaseOperate(String dbpathString, Vector<Decay> decays, boolean isLoad)
	{
		databasepathString = dbpathString;
		File dbFile = new File(databasepathString);
		try {
			if(!dbFile.exists())
				dbFile.createNewFile();
			else{
				if(isLoad){
					dbFile.delete();
					dbFile.createNewFile();
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		saveDecays = new Vector<SqliteDecay>();
		singleDecays = decays;
		String str1 = databasepathString.substring(0,1).toLowerCase();
		String str2 = databasepathString.substring(1);
		databasepathString = str1 + str2;
		//sqlString = "jdbc:sqlite://" + databasepathString.replace('\\', '/');
		sqlString = databasepathString.replace('\\', '/');
		load = isLoad;
	}
	/*
	 * 函数功能: 创建一个表
	 */
	private boolean createTable()
	{
		boolean success = false;
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection(sqlString); //创建一个数据库连接
			Statement statement = conn.createStatement();
			int i = statement.executeUpdate(CREATE_TABLE_DECAY);
			if(!conn.isClosed())
				conn.close();
			success = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			success = false;
		}
		return success;
	}
	/*
	 * 函数功能 :   向缓存之中写入数据
	 *
	 */
	private void ConvertToSave()
	{
		getExcelMessage();             //将Excel中的数据存入内存之中
		for(int i = 0;i<singleDecays.size();i++)
		{
			Decay decay = singleDecays.elementAt(i);
			SqliteDecay sDecay = saveToClass(decay);
			SqliteDecay haveSaveDecay = ContainSameParent(sDecay.getParentMass(), sDecay.getParentName());
			//如果同一母核，有多个衰变子核，则采用“|”分割开
			if(haveSaveDecay.getParentName()!=null)
			{
				sDecay.setDaughterMass(haveSaveDecay.getDaughterMass() + "|" + sDecay.getDaughterMass());
				sDecay.setDaughterName(haveSaveDecay.getDaughterName() + "|" + sDecay.getDaughterName());
				sDecay.setDaughterAtomNumber(haveSaveDecay.getDaughterAtomNumber()+"|"+sDecay.getDaughterAtomNumber());
				sDecay.setDaughterAtomMass(haveSaveDecay.getDaughterAtomMass()+"|"+sDecay.getDaughterAtomMass());
				sDecay.setDaughterChineseName(haveSaveDecay.getDaughterChineseName()+"|"+sDecay.getDaughterChineseName());
				sDecay.setHalfLife(haveSaveDecay.getHalfLife()+"|"+sDecay.getHalfLife());
				sDecay.setDecayIntensity(haveSaveDecay.getDecayIntensity() + "|" + sDecay.getDecayIntensity());
				sDecay.setDecayType(haveSaveDecay.getDecayType() + "|" + sDecay.getDecayType());
				sDecay.setGammaEnergy(haveSaveDecay.getGammaEnergy() + "|" + sDecay.getGammaEnergy());
				sDecay.setGammaIntensity(haveSaveDecay.getGammaIntensity() + "|" + sDecay.getGammaIntensity());
				sDecay.setBetaEnergy(haveSaveDecay.getBetaEnergy() + "|" + sDecay.getBetaEnergy());
				sDecay.setBetaIntensity(haveSaveDecay.getBetaIntensity() + "|" + sDecay.getBetaIntensity());
				sDecay.setAlphaEnergy(haveSaveDecay.getAlphaEnergy() + "|" + sDecay.getAlphaEnergy());
				sDecay.setAlphaIntensity(haveSaveDecay.getAlphaIntensity() + "|" + sDecay.getAlphaIntensity());
				sDecay.setPostiveBetaEnergy(haveSaveDecay.getPostiveBetaEnergy() + "|" + sDecay.getPostiveBetaEnergy());
				sDecay.setPostiveBetaIntensity(haveSaveDecay.getPostiveBetaIntensity() + "|" + sDecay.getPostiveBetaIntensity());
			}
			saveDecays.add(sDecay);
		}
	}
	//判断已经存储在内存之中的衰变信息之中是否包含母核，如果包含，则返回该信息，并且从内存之中删除
	private SqliteDecay ContainSameParent(String parentmass, String parentnuclide)
	{
		SqliteDecay sDecay = new SqliteDecay();
		for(SqliteDecay decay :saveDecays)
		{
			//母核的标识为名字和质量数
			if(decay.getParentMass().equals(parentmass)&&decay.getParentName().equals(parentnuclide))
			{
				sDecay = decay;
				saveDecays.removeElement(decay);
				break;
			}
		}
		return sDecay;
	}
	public void inputAll()
	{
		if(load)
		{
			if(createTable())
				System.out.println("已经创建了表");
			ConvertToSave();
			try {
				int i = 1;
				Class.forName(CLASSNAME);
				conn = DriverManager.getConnection(sqlString);
				statement = conn.createStatement();
				conn.setAutoCommit(false);
				for(SqliteDecay decay:saveDecays)
				{
					String sql  = "insert into decaymessage (ParentMassNumber,ParentName,ParentAtomNumber,ParentAtomMass,ParentChineseName,"
							+ "DaughterMassNumber,DaughterName,DaughterAtomNumber,DaughterAtomMass,DaughterChineseName,"
							+ "HalfLife,DecayType,DecayIntensity,GEnergy,GIntensity,BEnergy,"
							+ "BIntensity,AEnergy,AIntensity,PostiveBEnergy,PostiveBIntensity) values ("
							+"'"+ decay.getParentMass() +"'"+ "," + "'"+decay.getParentName() +"'"+ "," +"'"+decay.getParentAtomNumber()+"'"+","
							+"'"+decay.getParentAtomMass()+"'"+","+"'"+decay.getParentChineseName()+"'"+","+ "'" + decay.getDaughterMass()+"'"+ "," + "'"
							+ decay.getDaughterName() + "'"+ "," +"'"+decay.getDaughterAtomNumber()+"'"+","
							+"'"+decay.getDaughterAtomMass()+"'"+","+"'"+decay.getDaughterChineseName()+"'"+","+ "'" + decay.getHalfLife() + "'"+ "," + "'" + decay.getDecayType()+"'"+ "," + "'"
							+ decay.getDecayIntensity() + "'"+ "," + "'" + decay.getGammaEnergy()+"'"+ "," + "'"+decay.getGammaIntensity()+"'"+ "," + "'"
							+decay.getBetaEnergy()+"'"+ "," + "'"+decay.getBetaIntensity()+"'"+ "," + "'"+decay.getAlphaEnergy()+"'"+ "," + "'"+decay.getAlphaIntensity()+"'"+ "," + "'"
							+decay.getPostiveBetaEnergy()+"'"+ "," + "'"+decay.getPostiveBetaIntensity()+"');";
					statement.addBatch(sql);
					System.out.println("已经写入" + i + "个衰变信息");
					i++;
					if(i%30==0)
					{
						statement.executeBatch();
						conn.commit();
					}
				}
				statement.executeBatch();
				conn.commit();
				statement.close();
				conn.close();
				System.out.println("已完成");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public Vector<StructDecay> getAll()
	{
		Vector<StructDecay> decays = new Vector<StructDecay>();
		try {
			SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(sqlString, null);
			Cursor cursor = sqLiteDatabase.rawQuery("select * from decaymessage", null);
			Log.d("test", "getAll()  Cursor:列数： " + cursor.getColumnCount());
			int a = 0;
			//从数据库第一行开始遍历
			if (cursor.moveToFirst()) {
				//对每一种核素的具体信息进行详尽地整理
				do {
					String parentMass = cursor.getString(cursor.getColumnIndex("ParentMassNumber"));
					String parentName = cursor.getString(cursor.getColumnIndex("ParentName"));
					String parentAtomNumber= cursor.getString(cursor.getColumnIndex("ParentAtomNumber"));
					String parentAtomMass = cursor.getString(cursor.getColumnIndex("ParentAtomMass"));
					String parentChineseName = cursor.getString(cursor.getColumnIndex("ParentChineseName"));
					String []sonMass = split(cursor.getString(cursor.getColumnIndex("DaughterMassNumber")),'|');
					String []sonName = split(cursor.getString(cursor.getColumnIndex("DaughterName")),'|');
					String [] sonAtomNumberStrings = split(cursor.getString(cursor.getColumnIndex("DaughterAtomNumber")), '|');
					String [] sonAtomMass = split(cursor.getString(cursor.getColumnIndex("DaughterAtomMass")), '|');
					String [] sonChineseName = split(cursor.getString(cursor.getColumnIndex("DaughterChineseName")), '|');
					String []halfLife = split(cursor.getString(cursor.getColumnIndex("HalfLife")),'|');
					String []decayType = split(cursor.getString(cursor.getColumnIndex("DecayType")),'|');
					String []decayIntensity = split(cursor.getString(cursor.getColumnIndex("DecayIntensity")),'|');
					String []gammaEnergy = split(cursor.getString(cursor.getColumnIndex("GEnergy")),'|');
					String []gammaIntensity = split(cursor.getString(cursor.getColumnIndex("GIntensity")),'|');
					String []betaEnergy = split(cursor.getString(cursor.getColumnIndex("BEnergy")),'|');
					String []betaIntensity = split(cursor.getString(cursor.getColumnIndex("BIntensity")),'|');
					String []postiveBetaEnergy = split(cursor.getString(cursor.getColumnIndex("PostiveBEnergy")),'|');
					String []postiveBetaIntensity = split(cursor.getString(cursor.getColumnIndex("PostiveBIntensity")),'|');
					String []alphaEnergy = split(cursor.getString(cursor.getColumnIndex("AEnergy")),'|');
					String[] alphaIntensity = split(cursor.getString(cursor.getColumnIndex("AIntensity")), '|');
					String group = cursor.getString(cursor.getColumnIndex("duxing"));
					String level1 = cursor.getString(cursor.getColumnIndex("Level1"));
					String level2 = cursor.getString(cursor.getColumnIndex("Level2"));
					String level3 = cursor.getString(cursor.getColumnIndex("Level3"));
					String level4 = cursor.getString(cursor.getColumnIndex("Level4"));
					String level5 = cursor.getString(cursor.getColumnIndex("Level5"));

					//将每个核素打包成一个衰变结构体
					StructDecay structdecay = new StructDecay();
					structdecay.setParentMass(parentMass);
					structdecay.setParentName(parentName);
					structdecay.setParentAtomNumber(parentAtomNumber);
					structdecay.setParentAtomMass(parentAtomMass);
					structdecay.setParentChineseName(parentChineseName);
					structdecay.setGroup(group);
					structdecay.setLevel1(level1);
					structdecay.setLevel2(level2);
					structdecay.setLevel3(level3);
					structdecay.setLevel4(level4);
					structdecay.setLevel5(level5);

					a++;
					//将衰变得到的子体核素打包成衰变结构体内的一个向量
					Vector<SonNuclide> sons = new Vector<SonNuclide>();
					for(int i = 0;i<sonName.length;i++)
					{
						SonNuclide son = new SonNuclide();
						son.setSonName(sonName[i]);
						son.setSonMass(sonMass[i]);
						son.setSonAtomNumber(sonAtomNumberStrings[i]);
						son.setSonAtomMass(sonAtomMass[i]);
						son.setSonChineseName(sonChineseName[i]);
						son.setHalfLife(halfLife[i]);
						son.setDecayIntensity(decayIntensity[i]);
						son.setDecayTypeString(decayType[i]);

						//β衰变的能量和强度
						String[] benergy = split(betaEnergy[i],';');
						String[] bIntensity = split(betaIntensity[i],';');
						Vector<String> betaeVector = new Vector<String>();
						Vector<String> betaIVector = new Vector<String>();
						for(int k =0;k<benergy.length;k++)
						{
							if (!benergy[k].equals("")) {
								betaeVector.add(benergy[k]);
								betaIVector.add(bIntensity[k]);
							}
						}
						son.setBetaEnergyVector(betaeVector);
						son.setBetaIntenistyVector(betaIVector);

						//α衰变的能量和强度
						String[] aenergy = split(alphaEnergy[i],';');
						String[] aIntensity = split(alphaIntensity[i],';');
						Vector<String> alphaeVector = new Vector<String>();
						Vector<String> alphaIVector = new Vector<String>();
						for(int k =0;k<aenergy.length;k++)
						{
							if (!aenergy[k].equals("")) {
								alphaeVector.add(aenergy[k]);
								alphaIVector.add(aIntensity[k]);
							}

						}
						son.setAlphaEnergyVector(alphaeVector);
						son.setAlphaIntensityVector(alphaIVector);

						//β正衰变的能量和强度
						String[] pbenergy = split(postiveBetaEnergy[i],';');
						String[] pbIntensity = split(postiveBetaIntensity[i],';');
						Vector<String> pbeVector = new Vector<String>();
						Vector<String> pbIVector = new Vector<String>();
						for(int k =0;k<pbenergy.length;k++)
						{
							if (!pbenergy[k].equals("")) {
								pbeVector.add(pbenergy[k]);
								pbIVector.add(pbIntensity[k]);
							}
						}
						son.setPostiveBetaEnergyVector(pbeVector);
						son.setPostiveBetaIntensityVector(pbIVector);

						//γ衰变的能量和强度
						String[] genergy =split(gammaEnergy[i],';');
						String[] gIntensity = split(gammaIntensity[i],';');
						Vector<String> geVector = new Vector<String>();
						Vector<String> gIVector = new Vector<String>();
						for(int k =0;k<genergy.length;k++)
						{
							if (!genergy[k].equals("")) {
								geVector.add(genergy[k]);
								gIVector.add(gIntensity[k]);
							}
						}
						son.setGammaEnergyVector(geVector);
						son.setGammaIntensityVector(gIVector);
						sons.add(son);
					}
					structdecay.setSoNuclides(sons);
					//将各个衰变体打包成一个完整的衰变数据结构
					decays.add(structdecay);
				} while (cursor.moveToNext());
			}
			sqLiteDatabase.close();

//			Class.forName(CLASSNAME);
//			conn = DriverManager.getConnection(sqlString);
//			statement = conn.createStatement();
//			String sqlSelect = "SELECT *FROM decaymessage";
//			ResultSet rs =  statement.executeQuery(sqlSelect);
//			int a = 0;
//			while(rs.next())
//			{
//				String parentMass = rs.getString("ParentMassNumber");
//				String parentName = rs.getString("ParentName");
//				String parentAtomNumber= rs.getString("ParentAtomNumber");
//				String parentAtomMass = rs.getString("ParentAtomMass");
//				String parentChineseName = rs.getString("ParentChineseName");
//				String []sonMass = split(rs.getString("DaughterMassNumber"),'|');
//				String []sonName = split(rs.getString("DaughterName"),'|');
//				String [] sonAtomNumberStrings = split(rs.getString("DaughterAtomNumber"), '|');
//				String [] sonAtomMass = split(rs.getString("DaughterAtomMass"), '|');
//				String [] sonChineseName = split(rs.getString("DaughterChineseName"), '|');
//				String []halfLife = split(rs.getString("HalfLife"),'|');
//				String []decayType = split(rs.getString("DecayType"),'|');
//				String []decayIntensity = split(rs.getString("DecayIntensity"),'|');
//				String []gammaEnergy = split(rs.getString("GEnergy"),'|');
//				String []gammaIntensity = split(rs.getString("GIntensity"),'|');
//				String []betaEnergy = split(rs.getString("BEnergy"),'|');
//				String []betaIntensity = split(rs.getString("BIntensity"),'|');
//				String []postiveBetaEnergy = split(rs.getString("PostiveBEnergy"),'|');
//				String []postiveBetaIntensity = split(rs.getString("PostiveBIntensity"),'|');
//				String []alphaEnergy = split(rs.getString("AEnergy"),'|');
//				String []alphaIntensity = split(rs.getString("AIntensity"),'|');
//				StructDecay structdecay = new  StructDecay();
//				structdecay.setParentMass(parentMass);
//				structdecay.setParentName(parentName);
//				structdecay.setParentAtomNumber(parentAtomNumber);
//				structdecay.setParentAtomMass(parentAtomMass);
//				structdecay.setParentChineseName(parentChineseName);
//				a++;
//				Vector<SonNuclide> sons = new Vector<SonNuclide>();
//				for(int i = 0;i<sonName.length;i++)
//				{
//					SonNuclide son = new SonNuclide();
//					son.setSonName(sonName[i]);
//					son.setSonMass(sonMass[i]);
//					son.setSonAtomNumber(sonAtomNumberStrings[i]);
//					son.setSonAtomMass(sonAtomMass[i]);
//					son.setSonChineseName(sonChineseName[i]);
//					son.setHalfLife(halfLife[i]);
//					son.setDecayIntensity(decayIntensity[i]);
//					son.setDecayTypeString(decayType[i]);
//
//					String[] benergy = split(betaEnergy[i],';');
//					String[] bIntensity = split(betaIntensity[i],';');
//					Vector<String> betaeVector = new Vector<String>();
//					Vector<String> betaIVector = new Vector<String>();
//					for(int k =0;k<benergy.length;k++)
//					{
//						betaeVector.add(benergy[k]);
//						betaIVector.add(bIntensity[k]);
//					}
//					son.setBetaEnergyVector(betaeVector);
//					son.setBetaIntenistyVector(betaIVector);
//
//					String[] aenergy = split(alphaEnergy[i],';');
//					String[] aIntensity = split(alphaIntensity[i],';');
//					Vector<String> alphaeVector = new Vector<String>();
//					Vector<String> alphaIVector = new Vector<String>();
//					for(int k =0;k<aenergy.length;k++)
//					{
//						alphaeVector.add(aenergy[k]);
//						alphaIVector.add(aIntensity[k]);
//					}
//					son.setAlphaEnergyVector(alphaeVector);
//					son.setAlphaIntensityVector(alphaIVector);
//
//					String[] pbenergy = split(postiveBetaEnergy[i],';');
//					String[] pbIntensity = split(postiveBetaIntensity[i],';');
//					Vector<String> pbeVector = new Vector<String>();
//					Vector<String> pbIVector = new Vector<String>();
//					for(int k =0;k<pbenergy.length;k++)
//					{
//						pbeVector.add(pbenergy[k]);
//						pbIVector.add(pbIntensity[k]);
//					}
//					son.setPostiveBetaEnergyVector(pbeVector);
//					son.setPostiveBetaIntensityVector(pbIVector);
//
//					String[] genergy =split(gammaEnergy[i],';');
//					String[] gIntensity = split(gammaIntensity[i],';');
//					Vector<String> geVector = new Vector<String>();
//					Vector<String> gIVector = new Vector<String>();
//					for(int k =0;k<genergy.length;k++)
//					{
//						geVector.add(genergy[k]);
//						gIVector.add(gIntensity[k]);
//					}
//					son.setGammaEnergyVector(geVector);
//					son.setGammaIntensityVector(gIVector);
//					sons.add(son);
//				}
//				structdecay.setSoNuclides(sons);
//				decays.add(structdecay);
//			}
			conn.setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decays;
	}
	//自定义字符串分离函数
	private String[] split(String str,char c)
	{
		Vector<String> strs = new Vector<String>();
		String temp = "";
		if(str.isEmpty())
			strs.add(str);
		else
		{
			//boolean iscontain = false;
			int i = 0;
			for(;i<str.length();i++)
			{
				if(str.charAt(i)==c)
				{
					strs.add(temp);
					//iscontain = true;
					temp="";
				}else {
					temp = temp + str.charAt(i);
				}
			}
			if(i==str.length()) strs.add(temp);

		}
		//由于字串的数目不定，所以不能一开始就用字符数组；改而选择用可变长的向量
		String[] strings = new String[strs.size()];
		for(int k = 0;k<strs.size();k++)
		{
			strings[k] = strs.elementAt(k);
		}
		return strings;
	}
	/*
	 * 函数功能: 判断数据库之中是否已经存在该母核，若存在则返回信息，并且在数据库之中删除
	 */
 	private SqliteDecay getEqualParent(String parentMass, String parentName)
	{
		SqliteDecay sDecay = new SqliteDecay();
		try {
			//下面代码主要是查询母核
			Class.forName("org.sqlite.JDBC"); //相当于 new org.sqlite.JDBC();初始化了一个Driver对象
            //在初始化时会自动注册到DriverManager上，所以可以通过DriverManager获得数据库连接
			Connection conn = DriverManager.getConnection(sqlString); //创建一个数据库连接
			PreparedStatement statement = conn.prepareStatement(QUERY_PARENT);
			statement.setString(1, parentMass);
			statement.setString(2, parentName);
			ResultSet rSet = statement.executeQuery();
			//下面代码是将查询到数据存储到sqliteDecay类之中
			while(rSet.next())
			{
				sDecay.setParentMass(rSet.getString(1));
				sDecay.setParentName(rSet.getString(2));
				sDecay.setDaughterMass(rSet.getString(3));
				sDecay.setDaughterName(rSet.getString(4));
				sDecay.setHalfLife(rSet.getString(5));
				sDecay.setDecayType(rSet.getString(6));
				sDecay.setDecayIntensity(rSet.getString(7));
				sDecay.setGammaEnergy(rSet.getString(8));
				sDecay.setGammaIntensity(rSet.getString(9));
				sDecay.setBetaEnergy(rSet.getString(10));
				sDecay.setBetaIntensity(rSet.getString(11));
				sDecay.setAlphaEnergy(rSet.getString(12));
				sDecay.setAlphaIntensity(rSet.getString(13));
				sDecay.setPostiveBetaEnergy(rSet.getString(14));
				sDecay.setPostiveBetaIntensity(rSet.getString(15));
			}
			//然后将该条信息从数据库之中删除
			PreparedStatement deletestatement = conn.prepareStatement(DELETE_SAMEPARENTNUCLIDE);
			deletestatement.setString(1, parentMass);
			deletestatement.setString(2, parentName);
			deletestatement.executeUpdate();
			statement.close();
			deletestatement.close();
			if(!conn.isClosed())
				conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sDecay;
	}
	/*
	 * 函数功能:   将数据存储到SqliteDecay类之中
	 */
	public SqliteDecay saveToClass(Decay decay)
	{

	    //Parentnuclide是以类似于“H-3”的形式命名，所以可以通过一定的方法分别获取名字和质量数
		String parentMass = getMass(decay.getParentnuclide());                    //母核质量数
		String parentNameString = getName(decay.getParentnuclide());		        //母核名字
		Vector<String> othermessageStrings = getOtherMessage(parentNameString);

		String daughterString = "";
		if(decay.getDaughternuclide().size()>0)
			daughterString = decay.getDaughternuclide().elementAt(0);			   	   //子核
		String daughterMass = getMass(daughterString);								   //子核质量数
		String daughterNameString = getName(daughterString);       //子核名字
		Vector<String> sonMessageStrings = getOtherMessage(daughterNameString);
		String halflifeString = decay.getHalflife();								   //半衰期
		String decaytypeString = "";
		if(decay.getDecaytype().size()>0)
			decaytypeString = decay.getDecaytype().elementAt(0);					   //衰变方式
		String decayintensityString = "";
		if(decay.getDecayIntensityVector().size()>0)
			decayintensityString = decay.getDecayIntensityVector().elementAt(0);    //衰变分支比

		//γ射线的能量和发射几率
		String genergyString = "";
		String gintensity ="";
		if(decay.getGenergyVector().size()>0)
		{
			genergyString = decay.getGenergyVector().elementAt(0);
			gintensity = decay.getGintensityVector().elementAt(0);
			//之所以不一开始就直接循环，是为了方便添加分号
			for(int j = 1;j<decay.getGenergyVector().size();j++)
			{
				genergyString = genergyString + ";" + decay.getGenergyVector().elementAt(j);
				gintensity = gintensity + ";" + decay.getGintensityVector().elementAt(j);
			}
		}

		//β射线的能量和发射几率
		String benergyString ="";
		String bintensityString = "";
		if(decay.getbEnergyVector().size()>0)
		{
			bintensityString = decay.getbIntensityVector().elementAt(0);
			benergyString = decay.getbEnergyVector().elementAt(0);
			for(int j = 1;j<decay.getbEnergyVector().size();j++)
			{
				benergyString = benergyString + ";" + decay.getbEnergyVector().elementAt(j);
				bintensityString = bintensityString + ";" + decay.getbIntensityVector().elementAt(j);
			}
		}

		//β+射线的能量和发射几率
		String postivebenergyString = "";
		String postivebintensityString = "";
		if(decay.getPostiveBEnergyVector().size()>0)
		{
			postivebenergyString =  decay.getPostiveBEnergyVector().elementAt(0);
			postivebintensityString =  decay.getPostiveBIntensityVector().elementAt(0);
			for(int j=1;j<decay.getPostiveBEnergyVector().size();j++)
			{
				postivebenergyString = postivebenergyString + ";" + decay.getPostiveBEnergyVector().elementAt(j);
				postivebintensityString = postivebintensityString + ";" + decay.getPostiveBIntensityVector().elementAt(j);
			}
		}

		//α射线的能量和发射几率
		String aenergyString = "";
		String aintensityString = "";
		if(decay.getAenergyVector().size()>0)
		{
			aintensityString = decay.getAintensityVector().elementAt(0);
			aenergyString = decay.getAenergyVector().elementAt(0);
			for(int j=1;j<decay.getAenergyVector().size();j++)
			{
				aenergyString = aenergyString + ";" + decay.getAenergyVector().elementAt(j);
				aintensityString = aintensityString + ";" + decay.getAintensityVector().elementAt(j);
			}
		}
		SqliteDecay sDecay = new SqliteDecay();
		sDecay.setParentMass(parentMass);
		sDecay.setParentName(parentNameString);
		sDecay.setParentAtomNumber(othermessageStrings.elementAt(0));
		sDecay.setParentAtomMass(othermessageStrings.elementAt(1));
		sDecay.setParentChineseName(othermessageStrings.elementAt(2));
		sDecay.setDaughterMass(daughterMass);
		sDecay.setDaughterName(daughterNameString);
		sDecay.setDaughterAtomNumber(sonMessageStrings.elementAt(0));
		sDecay.setDaughterAtomMass(sonMessageStrings.elementAt(1));
		sDecay.setDaughterChineseName(sonMessageStrings.elementAt(2));
		sDecay.setHalfLife(halflifeString);
		sDecay.setDecayIntensity(decayintensityString);
		sDecay.setDecayType(decaytypeString);
		sDecay.setGammaEnergy(genergyString);
		sDecay.setGammaIntensity(gintensity);
		sDecay.setBetaEnergy(benergyString);
		sDecay.setBetaIntensity(bintensityString);
		sDecay.setPostiveBetaEnergy(postivebenergyString);
		sDecay.setPostiveBetaIntensity(postivebintensityString);
		sDecay.setAlphaEnergy(aenergyString);
		sDecay.setAlphaIntensity(aintensityString);
		return sDecay;
	}

	private Vector<Vector<String>> elementVector;
	private Vector<String> getOtherMessage(String parentName)
	{
		Vector<String> othermessage = new Vector<String>();
		Vector<String> parentNameVector = elementVector.elementAt(1);
		int index = 0;
		for(int i=0;i<parentNameVector.size();i++){
			if(parentNameVector.elementAt(i).equals(parentName))
			{
				index = i;
				break;
			}
		}
		othermessage.add(0,elementVector.elementAt(0).elementAt(index));
		othermessage.add(1,elementVector.elementAt(2).elementAt(index));
		othermessage.add(2,elementVector.elementAt(3).elementAt(index));
		return othermessage;
	}
	/*
	 * 函数功能：获取元素周期表相关信息
	 */
	private void getExcelMessage(){
		elementVector = new Vector<Vector<String>>();
		String elementPathString = System.getProperty("user.dir")+"\\element.xls";
		try {
			InputStream stream = new FileInputStream(elementPathString);
			Workbook workbook = Workbook.getWorkbook(stream);
			Sheet sheet = workbook.getSheet(0);
			Cell[] atomNumberCells = sheet.getColumn(0);
			Cell[] atomNameCells = sheet.getColumn(1);
			Cell[] atomMassCells = sheet.getColumn(4);
			Cell[] atomChineseNameCells = sheet.getColumn(2);
			Vector<String>numberStrings = new Vector<String>();
			Vector<String>nameStrings = new Vector<String>();
			Vector<String>massStrings = new Vector<String>();
			Vector<String>chineseNameStrings = new Vector<String>();
			for(int i=0;i<atomChineseNameCells.length;i++){
				numberStrings.add(atomNumberCells[i].getContents());
				nameStrings.add(atomNameCells[i].getContents());
				massStrings.add(atomMassCells[i].getContents());
				chineseNameStrings.add(atomChineseNameCells[i].getContents());
			}
			elementVector.add(0,numberStrings);
			elementVector.add(1,nameStrings);
			elementVector.add(2,massStrings);
			elementVector.add(3,chineseNameStrings);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*
	 * 函数功能: 获取母核与子核的质量数
	 */
	private String getMass(String str)
	{
		String temp = "";
		for(int i = 0;i<str.length();i++){
			if(str.charAt(i)>=48&&str.charAt(i)<=57)
			{
				if(temp.length()==3)
					break;
				else {
					temp = temp+str.charAt(i);
				}
			}
		}
		return temp;
	}
	private String getName(String str)
	{
		String temp = "";
		for(int i = 0;i<str.length();i++)
		{
			if(!Character.isDigit(str.charAt(i)))
			{
				temp = temp + str.charAt(i);
			}
		}
		return temp;
	}
}
