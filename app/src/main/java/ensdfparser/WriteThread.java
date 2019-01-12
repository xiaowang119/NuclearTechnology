package ensdfparser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Vector;


public class WriteThread{

	public static final String INSERT_DECAY = "insert into decaymessage values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
	public static final String CLASSNAME= "org.sqlite.JDBC";
	private String sqlString = "jdbc:sqlite://";
	private Connection conn;
	private Statement statement;
	
	public void inputAll(Vector<SqliteDecay>decays)
	{
		
		try {
			int i = 1;
			Class.forName(CLASSNAME);
			conn = DriverManager.getConnection(sqlString);
			statement = conn.createStatement();
			conn.setAutoCommit(false);
			for(SqliteDecay decay:decays)
			{
				String sql  = "insert into decaymessage (ParentMassNumber,ParentName,DaughterMassNumber,DaughterName,HalfLife,DecayType,DecayIntensity,GEnergy,GIntensity,BEnergy,"
						+ "BIntensity,AEnergy,AIntensity,PostiveBEnergy,PostiveBIntensity) values ("
						+"'"+ decay.getParentMass() +"'"+ "," + "'"+decay.getParentName() +"'"+ "," + "'" + decay.getDaughterMass()+"'"+ "," + "'"
						+ decay.getDaughterName() + "'"+ "," + "'" + decay.getHalfLife() + "'"+ "," + "'" + decay.getDecayType()+"'"+ "," + "'"
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
	public WriteThread(String dbPath) {
		// TODO Auto-generated constructor stub
		String str1 = dbPath.substring(0,1).toLowerCase();
		String str2 = dbPath.substring(1);
		dbPath = str1 + str2;
		sqlString = sqlString + dbPath.replace('\\', '/');
		
	}


}
