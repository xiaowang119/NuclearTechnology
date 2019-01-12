package ensdfparser;

import java.util.Vector;

/*
 * 类：定义一个衰变类，包含其相关信息
 * 
 */
public class Decay {
	private int id;
	private String parentnuclide;   		 	 	 //母核
	private String halflife;                 	 	 //半衰期
	private Vector<String> decayIntensityVector;	 //衰变几率
	private Vector<String> radiationconstVector;	 //γ常数
	private Vector<String> decaytype;        		 //衰变方式
	private Vector<String> daughternuclide;  		 //子核
	private Vector<String> genergyVector;    	 	 //γ射线能量
	private Vector<String> gintensityVector; 	 	 //γ射线发射几率
	private Vector<String> aenergyVector;    	 	 //α射线能量
	private Vector<String> aintensityVector; 	 	 //α射线发射几率
	private Vector<String> postiveBEnergyVector; 	 //正电子能量
	private Vector<String> postiveBIntensityVector;  //正电子发射几率
	private Vector<String> bEnergyVector;            //β射线的平均能量
	private Vector<String> bIntensityVector;		 //β射线的发射几率
	private Vector<String> levelbadVector;           //
	
	//构造函数
	public Decay(){
		decayIntensityVector = new Vector<String>();
		radiationconstVector = new Vector<String>();
		decaytype  = new Vector<String>();
		daughternuclide = new Vector<String>();
		genergyVector = new Vector<String>();
		gintensityVector = new Vector<String>();
		aenergyVector = new Vector<String>();
		aintensityVector = new Vector<String>();
		postiveBEnergyVector = new Vector<String>();
		postiveBIntensityVector = new Vector<String>();
		bEnergyVector = new Vector<String>();
		bIntensityVector = new Vector<String>();
		levelbadVector = new Vector<String>();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getParentnuclide() {
		return parentnuclide;
	}
	public void setParentnuclide(String parentnuclide) {
		this.parentnuclide = parentnuclide;
	}
	public String getHalflife() {
		return halflife;
	}
	public void setHalflife(String halflife) {
		this.halflife = halflife;
	}
	public Vector<String> getDecayIntensityVector() {
		return decayIntensityVector;
	}
	public void setDecayIntensityVector(Vector<String> decayIntensityVector) {
		this.decayIntensityVector = decayIntensityVector;
	}
	public Vector<String> getRadiationconstVector() {
		return radiationconstVector;
	}
	public void setRadiationconstVector(Vector<String> radiationconstVector) {
		this.radiationconstVector = radiationconstVector;
	}
	public Vector<String> getDecaytype() {
		return decaytype;
	}
	public void setDecaytype(Vector<String> decaytype) {
		this.decaytype = decaytype;
	}
	public Vector<String> getDaughternuclide() {
		return daughternuclide;
	}
	public void setDaughternuclide(Vector<String> daughternuclide) {
		this.daughternuclide = daughternuclide;
	}
	public Vector<String> getGenergyVector() {
		return genergyVector;
	}
	public void setGenergyVector(Vector<String> genergyVector) {
		this.genergyVector = genergyVector;
	}
	public Vector<String> getGintensityVector() {
		return gintensityVector;
	}
	public void setGintensityVector(Vector<String> gintensityVector) {
		this.gintensityVector = gintensityVector;
	}
	public Vector<String> getAenergyVector() {
		return aenergyVector;
	}
	public void setAenergyVector(Vector<String> aenergyVector) {
		this.aenergyVector = aenergyVector;
	}
	public Vector<String> getAintensityVector() {
		return aintensityVector;
	}
	public void setAintensityVector(Vector<String> aintensityVector) {
		this.aintensityVector = aintensityVector;
	}
	public Vector<String> getPostiveBEnergyVector() {
		return postiveBEnergyVector;
	}
	public void setPostiveBEnergyVector(Vector<String> postiveBEnergyVector) {
		this.postiveBEnergyVector = postiveBEnergyVector;
	}
	public Vector<String> getPostiveBIntensityVector() {
		return postiveBIntensityVector;
	}
	public void setPostiveBIntensityVector(Vector<String> postiveBIntensityVector) {
		this.postiveBIntensityVector = postiveBIntensityVector;
	}
	public Vector<String> getbEnergyVector() {
		return bEnergyVector;
	}
	public void setbEnergyVector(Vector<String> bEnergyVector) {
		this.bEnergyVector = bEnergyVector;
	}
	public Vector<String> getbIntensityVector() {
		return bIntensityVector;
	}
	public void setbIntensityVector(Vector<String> bIntensityVector) {
		this.bIntensityVector = bIntensityVector;
	}
	public Vector<String> getLevelbadVector() {
		return levelbadVector;
	}
	public void setLevelbadVector(Vector<String> levelbadVector) {
		this.levelbadVector = levelbadVector;
	}
}
