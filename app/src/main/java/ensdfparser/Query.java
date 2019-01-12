package ensdfparser;

import java.util.Vector;

public class Query {

	private Vector<StructDecay> datasourceDecays;   //需要查找的数据源
	
	public Query(Vector<StructDecay>decays)
	{
		datasourceDecays = decays;
	}
	/*
	 * 函数功能：  通过核素查询，核素的符号和核素的质量数
	 */
	public Vector<StructDecay> findByNuclide(int queryType, String str)
	{
		Vector<StructDecay> resultDecays = new Vector<StructDecay>();
		switch(queryType)
		{
			case QueryTypeByNuclide.NAME_PARENT:
				resultDecays = findByParentName(str);
				break;
			case QueryTypeByNuclide.MASS_PARENT:
				resultDecays = findByParentMass(str);
				break;
			case QueryTypeByNuclide.NAME_SON:
				resultDecays = findBySonName(str);
				break;
			case QueryTypeByNuclide.MASS_SON:
				resultDecays = findBySonMass(str);
		}
		return resultDecays;
	}
	/*
	 * 函数功能：  衰变产物查询
	 */
	public Vector<StructDecay> findByEnergy(int energyType, String energy, String energyRange, String intensity, String intensityRange)
	{
		Vector<StructDecay> resultDecays = new Vector<StructDecay>();
		switch(energyType)
		{
			case QueryTypeByEnergy.ALPHA:
				resultDecays = findByAlphaEnergy(energy, energyRange, intensity, intensityRange);
				break;
			case QueryTypeByEnergy.BETA:
				resultDecays = findByBetaEnergy(energy, energyRange, intensity, intensityRange);
				break;
			case QueryTypeByEnergy.POSTIVEBETA:
				resultDecays = findByPostiveBetaEnergy(energy, energyRange, intensity, intensityRange);
				break;
			case QueryTypeByEnergy.GAMMA:
				resultDecays = findByGammaEnergy(energy, energyRange, intensity, intensityRange);
		}
		return resultDecays;
	}
	/*
	 * 函数功能：   通过母核的元素符号查询
	 */
	private Vector<StructDecay>findByParentName(String nuclidename)
	{
		Vector<StructDecay> resultdecays = new Vector<StructDecay>();
		for(int i = 0;i<datasourceDecays.size();i++)
		{
			if(datasourceDecays.elementAt(i).getParentName().equals(nuclidename))
				resultdecays.add(datasourceDecays.elementAt(i));
		}
		return resultdecays;
	}
	/*
	 * 函数功能：   通过母核的质量数查询
	 */
	private Vector<StructDecay>findByParentMass(String nuclideMass)
	{
		Vector<StructDecay> resultdecays = new Vector<StructDecay>();
		for(int i = 0;i<datasourceDecays.size();i++)
		{
			if(datasourceDecays.elementAt(i).getParentMass().equals(nuclideMass))
				resultdecays.add(datasourceDecays.elementAt(i));
		}
		return resultdecays;
	}
	/*
	 * 函数功能：   通过子核的元素符号查询
	 */
	private Vector<StructDecay>findBySonName(String nuclidename)
	{
		Vector<StructDecay> resultdecays = new Vector<StructDecay>();
		for(int i = 0;i<datasourceDecays.size();i++)
		{
			StructDecay strcutDecay = new StructDecay();   //保存查询到的该条核素信息
			strcutDecay.setParentMass(datasourceDecays.elementAt(i).getParentMass());
			strcutDecay.setParentName(datasourceDecays.elementAt(i).getParentName());
			strcutDecay.setParentAtomNumber(datasourceDecays.elementAt(i).getParentAtomNumber());
			strcutDecay.setParentAtomMass(datasourceDecays.elementAt(i).getParentAtomMass());
			strcutDecay.setParentChineseName(datasourceDecays.elementAt(i).getParentChineseName());
			//示例化子核的相关信息
			Vector<SonNuclide> son = new Vector<SonNuclide>();
			for(int j = 0;j<datasourceDecays.elementAt(i).getSoNuclides().size();j++)
			{
				//根据子核的名字判断是否查询到
				if(datasourceDecays.elementAt(i).getSoNuclides().elementAt(j).getSonName().equals(nuclidename))
				{
					son.add(datasourceDecays.elementAt(i).getSoNuclides().elementAt(j));

				}
			}
			//如果子核的数组的大小为零，那么就是代表没有查询到，所有不用保存
			if(son.size()!=0)
			{
				strcutDecay.setSoNuclides(son);
				resultdecays.add(strcutDecay);
			}
		}
		return resultdecays;
	}
	// 2017年11月11日 shinecore
	// 添加了根据母核符号和质量数查询
	public Vector<StructDecay> findByNuclide(int queryType, String name, String mass )
	{
		Vector<StructDecay> resultdecays = new Vector<StructDecay>();
		for(int i = 0;i<datasourceDecays.size();i++)
		{
			if(datasourceDecays.elementAt(i).getParentName().equals(name) &&
					datasourceDecays.elementAt(i).getParentMass().equals(mass) )
				resultdecays.add(datasourceDecays.elementAt(i));
		}
		return resultdecays;
	}
	/*
	 * 函数功能：   通过母核的质量数查询
	 */
	private Vector<StructDecay>findBySonMass(String nuclideMass)
	{
		Vector<StructDecay> resultdecays = new Vector<StructDecay>();  //保存查询到的核素信息
		for(int i = 0;i<datasourceDecays.size();i++)
		{
			StructDecay strcutDecay = new StructDecay();  //查询到的核素
			
			strcutDecay.setParentMass(datasourceDecays.elementAt(i).getParentMass());
			strcutDecay.setParentName(datasourceDecays.elementAt(i).getParentName());
			strcutDecay.setParentAtomNumber(datasourceDecays.elementAt(i).getParentAtomNumber());
			strcutDecay.setParentAtomMass(datasourceDecays.elementAt(i).getParentAtomMass());
			strcutDecay.setParentChineseName(datasourceDecays.elementAt(i).getParentChineseName());
			//实例化一个子核的数组，用来保存查询到的子核
			Vector<SonNuclide> son = new Vector<SonNuclide>();
			for(int j = 0;j<datasourceDecays.elementAt(i).getSoNuclides().size();j++)
			{
				//根据子核的质量数来查询
				if(datasourceDecays.elementAt(i).getSoNuclides().elementAt(j).getSonMass().equals(nuclideMass))
				{
					son.add(datasourceDecays.elementAt(i).getSoNuclides().elementAt(j));

				}
			}
			//如果查询到的子核大小为零那么久不需要保存
			if(son.size()!=0)
			{
				strcutDecay.setSoNuclides(son);
				resultdecays.add(strcutDecay);
			}
		}
		return resultdecays;
	}
	/*
	*函数功能：  通过伽马射线的能量范围和发射几率范围查找
	*
	*/
	private Vector<StructDecay> findByGammaEnergy(String energy, String energyrange, String intensity, String intensityrange)
	{
		Vector<StructDecay> resultDecay = new Vector<StructDecay>();
		//确定查询的范围
		double leftenergy = Double.parseDouble(energy) - Double.parseDouble(energyrange);
		double rightenergy = Double.parseDouble(energy) + Double.parseDouble(energyrange);
		double leftintensity = Double.parseDouble(intensity) - Double.parseDouble(intensityrange);
		double rightintesity = Double.parseDouble(intensity) + Double.parseDouble(intensityrange);
		//开始查询
		for(int i = 0;i<datasourceDecays.size();i++)
		{
			StructDecay strcutDecay = new StructDecay();
			strcutDecay.setParentMass(datasourceDecays.elementAt(i).getParentMass());
			strcutDecay.setParentName(datasourceDecays.elementAt(i).getParentName());
			strcutDecay.setParentAtomNumber(datasourceDecays.elementAt(i).getParentAtomNumber());
			strcutDecay.setParentAtomMass(datasourceDecays.elementAt(i).getParentAtomMass());
			strcutDecay.setParentChineseName(datasourceDecays.elementAt(i).getParentChineseName());
			Vector<SonNuclide> son = new Vector<SonNuclide>();
			//遍历某个核素的伽马射线的能量和发射几率，来进行比对
			for(int j = 0;j<datasourceDecays.elementAt(i).getSoNuclides().size();j++)
			{
				for(int k = 0;k<datasourceDecays.elementAt(i).getSoNuclides().elementAt(j).getGammaEnergyVector().size();k++)
				{
					//将伽马射线的能量和对应的发射几率转换成为double类型以便来进行比较
					if(isAllNumber(datasourceDecays.elementAt(i).getSoNuclides().elementAt(j).getGammaEnergyVector().elementAt(k))&&
							isAllNumber(datasourceDecays.elementAt(i).getSoNuclides().elementAt(j).getGammaIntensityVector().elementAt(k)))
					{
						double energyDouble = Double.parseDouble(datasourceDecays.elementAt(i).getSoNuclides().elementAt(j).getGammaEnergyVector().elementAt(k));
						double intensityDouble = Double.parseDouble(datasourceDecays.elementAt(i).getSoNuclides().elementAt(j).getGammaIntensityVector().elementAt(k));
						if(leftenergy<energyDouble&&rightenergy>energyDouble&&leftintensity<intensityDouble&&rightintesity>intensityDouble)
						{
							son.add(datasourceDecays.elementAt(i).getSoNuclides().elementAt(j));
		
						}
					}
				}
			}
			if(son.size()!=0)
			{
				strcutDecay.setSoNuclides(son);
				resultDecay.add(strcutDecay);
			}
		}
		return resultDecay;
	}
	/*
	*函数功能： 通过β射线的能量和发射几率进行查询
	*
	*/
	private Vector<StructDecay> findByBetaEnergy(String energy, String energyrange, String intensity, String intensityrange)
	{
		Vector<StructDecay> resultDecay = new Vector<StructDecay>();
		//确定查询的范围
		double leftenergy = Double.parseDouble(energy) - Double.parseDouble(energyrange);
		double rightenergy = Double.parseDouble(energy) + Double.parseDouble(energyrange);
		double leftintensity = Double.parseDouble(intensity) - Double.parseDouble(intensityrange);
		double rightintesity = Double.parseDouble(intensity) + Double.parseDouble(intensityrange);
		//开始查询
		for(int i = 0;i<datasourceDecays.size();i++)
		{
			StructDecay strcutDecay = new StructDecay();
			strcutDecay.setParentMass(datasourceDecays.elementAt(i).getParentMass());
			strcutDecay.setParentName(datasourceDecays.elementAt(i).getParentName());
			strcutDecay.setParentAtomNumber(datasourceDecays.elementAt(i).getParentAtomNumber());
			strcutDecay.setParentAtomMass(datasourceDecays.elementAt(i).getParentAtomMass());
			strcutDecay.setParentChineseName(datasourceDecays.elementAt(i).getParentChineseName());
			Vector<SonNuclide> son = new Vector<SonNuclide>();
			for(int j = 0;j<datasourceDecays.elementAt(i).getSoNuclides().size();j++)
			{
				for(int k = 0;k<datasourceDecays.elementAt(i).getSoNuclides().elementAt(j).getBetaEnergyVector().size();k++)
				{
					//将β射线的能量和对应发射几率转化成为double类型以便比较
					if(isAllNumber(datasourceDecays.elementAt(i).getSoNuclides().elementAt(j).getBetaEnergyVector().elementAt(k))&&
							isAllNumber(datasourceDecays.elementAt(i).getSoNuclides().elementAt(j).getBetaIntenistyVector().elementAt(k)))
					{
						double energyDouble = Double.parseDouble(datasourceDecays.elementAt(i).getSoNuclides().elementAt(j).getBetaEnergyVector().elementAt(k));
						double intensityDouble = Double.parseDouble(datasourceDecays.elementAt(i).getSoNuclides().elementAt(j).getBetaIntenistyVector().elementAt(k));
						if(leftenergy<energyDouble&&rightenergy>energyDouble&&leftintensity<intensityDouble&&rightintesity>intensityDouble)
						{
							son.add(datasourceDecays.elementAt(i).getSoNuclides().elementAt(j));
		
						}
					}
				}
			}
			//如果子核的信息为零，则代表该条信息不是需要的核素信息
			if(son.size()!=0)
			{
				strcutDecay.setSoNuclides(son);
				resultDecay.add(strcutDecay);
			}
		}
		return resultDecay;
	}
	/*
	*函数功能：  通过对β+射线的能量和发射几率进行查询
	*
	*/
	private Vector<StructDecay> findByPostiveBetaEnergy(String energy, String energyrange, String intensity, String intensityrange)
	{
		Vector<StructDecay> resultDecay = new Vector<StructDecay>();
		//确定查询的范围
		double leftenergy = Double.parseDouble(energy) - Double.parseDouble(energyrange);
		double rightenergy = Double.parseDouble(energy) + Double.parseDouble(energyrange);
		double leftintensity = Double.parseDouble(intensity) - Double.parseDouble(intensityrange);
		double rightintesity = Double.parseDouble(intensity) + Double.parseDouble(intensityrange);
		//开始查询
		for(int i = 0;i<datasourceDecays.size();i++)
		{
			StructDecay strcutDecay = new StructDecay();
			strcutDecay.setParentMass(datasourceDecays.elementAt(i).getParentMass());
			strcutDecay.setParentName(datasourceDecays.elementAt(i).getParentName());
			strcutDecay.setParentAtomNumber(datasourceDecays.elementAt(i).getParentAtomNumber());
			strcutDecay.setParentAtomMass(datasourceDecays.elementAt(i).getParentAtomMass());
			strcutDecay.setParentChineseName(datasourceDecays.elementAt(i).getParentChineseName());
			Vector<SonNuclide> son = new Vector<SonNuclide>();
			//查询每个子核中贝塔+射线的能量和发射几率
			for(int j = 0;j<datasourceDecays.elementAt(i).getSoNuclides().size();j++)
			{
				for(int k = 0;k<datasourceDecays.elementAt(i).getSoNuclides().elementAt(j).getPostiveBetaEnergyVector().size();k++)
				{
					if(isAllNumber(datasourceDecays.elementAt(i).getSoNuclides().elementAt(j).getPostiveBetaEnergyVector().elementAt(k))&&
							isAllNumber(datasourceDecays.elementAt(i).getSoNuclides().elementAt(j).getPostiveBetaIntensityVector().elementAt(k)))
					{
						//β+射线的能量和发射几率转换成为double类型
						double energyDouble = Double.parseDouble(datasourceDecays.elementAt(i).getSoNuclides().elementAt(j).getPostiveBetaEnergyVector().elementAt(k));
						double intensityDouble = Double.parseDouble(datasourceDecays.elementAt(i).getSoNuclides().elementAt(j).getPostiveBetaIntensityVector().elementAt(k));
						if(leftenergy<energyDouble&&rightenergy>energyDouble&&leftintensity<intensityDouble&&rightintesity>intensityDouble)
						{
							son.add(datasourceDecays.elementAt(i).getSoNuclides().elementAt(j));
		
						}
					}
				}
			}
			//如果子核的数量为零，那么代表未查询到
			if(son.size()!=0)
			{
				strcutDecay.setSoNuclides(son);
				resultDecay.add(strcutDecay);
			}
		}
		return resultDecay;
	}
	/*
	*函数功能：通过α射线的能量和发射几率进行查询
	*
	*/
	private Vector<StructDecay> findByAlphaEnergy(String energy, String energyrange, String intensity, String intensityrange)
	{
		Vector<StructDecay> resultDecay = new Vector<StructDecay>();
		//确定需要查询的范围
		double leftenergy = Double.parseDouble(energy) - Double.parseDouble(energyrange);
		double rightenergy = Double.parseDouble(energy) + Double.parseDouble(energyrange);
		double leftintensity = Double.parseDouble(intensity) - Double.parseDouble(intensityrange);
		double rightintesity = Double.parseDouble(intensity) + Double.parseDouble(intensityrange);
		//开始查询
		for(int i = 0;i<datasourceDecays.size();i++)
		{
			StructDecay strcutDecay = new StructDecay();
			strcutDecay.setParentMass(datasourceDecays.elementAt(i).getParentMass());
			strcutDecay.setParentName(datasourceDecays.elementAt(i).getParentName());
			strcutDecay.setParentAtomNumber(datasourceDecays.elementAt(i).getParentAtomNumber());
			strcutDecay.setParentAtomMass(datasourceDecays.elementAt(i).getParentAtomMass());
			strcutDecay.setParentChineseName(datasourceDecays.elementAt(i).getParentChineseName());
			Vector<SonNuclide> son = new Vector<SonNuclide>();
			for(int j = 0;j<datasourceDecays.elementAt(i).getSoNuclides().size();j++)
			{
				for(int k = 0;k<datasourceDecays.elementAt(i).getSoNuclides().elementAt(j).getAlphaEnergyVector().size();k++)
				{
					if(isAllNumber(datasourceDecays.elementAt(i).getSoNuclides().elementAt(j).getAlphaEnergyVector().elementAt(k))&&
							isAllNumber(datasourceDecays.elementAt(i).getSoNuclides().elementAt(j).getAlphaIntensityVector().elementAt(k)))
					{
						//将α射线的能量和发射几率转换成为double类型
						double energyDouble = Double.parseDouble(datasourceDecays.elementAt(i).getSoNuclides().elementAt(j).getAlphaEnergyVector().elementAt(k));
						double intensityDouble = Double.parseDouble(datasourceDecays.elementAt(i).getSoNuclides().elementAt(j).getAlphaIntensityVector().elementAt(k));
						if(leftenergy<energyDouble&&rightenergy>energyDouble&&leftintensity<intensityDouble&&rightintesity>intensityDouble)
						{
							son.add(datasourceDecays.elementAt(i).getSoNuclides().elementAt(j));
		
						}
					}
				}
			}
			//如果子核的数量为零，则代表为查询到
			if(son.size()!=0)
			{
				strcutDecay.setSoNuclides(son);
				resultDecay.add(strcutDecay);
			}
		}
		return resultDecay;
	}
	private boolean isAllNumber(String str)
	{
		if(str.isEmpty()) return false;
		for(int i = 0;i<str.length();i++)
		{
			if(!Character.isDigit(str.charAt(i)))
			{
				if(str.charAt(i)!='.')
				{
					return false;
				}
			}
		}
		return true;
	}
}
