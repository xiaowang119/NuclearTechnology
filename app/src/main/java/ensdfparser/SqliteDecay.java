package ensdfparser;
/*
 * 类功能: 实现衰变信息在存储到数据库时使用
 */
public class SqliteDecay {
	private String parentMass;				//母核质量数
	private String parentName;				//母核名称
	private String parentAtomNumber;			//原子序数
	private String parentAtomMass;			//原子相对质量
	private String parentChineseName;		//中文名称

	private String daughterMass;
	private String daughterName;
	private String daughterAtomNumber;
	private String daughterAtomMass;
	private String daughterChineseName;

	private String decayType;					//衰变类型
	private String halfLife;					//半衰期
	private String decayIntensity;			//衰变强度
	private String gammaEnergy;				//伽马衰变能量
	private String gammaIntensity;			//伽马衰变强度
	private String betaEnergy;				//β衰变能量
	private String betaIntensity;			//β衰变强度
	private String postiveBetaEnergy;		//β正衰变能量
	private String postiveBetaIntensity;	//β正衰变强度
	private String alphaEnergy;				//α衰变能量
	private String alphaIntensity;			//α衰变强度
	
	public String getParentAtomNumber() {
		return parentAtomNumber;
	}
	public void setParentAtomNumber(String parentAtomNumber) {
		this.parentAtomNumber = parentAtomNumber;
	}
	public String getParentAtomMass() {
		return parentAtomMass;
	}
	public void setParentAtomMass(String parentAtomMass) {
		this.parentAtomMass = parentAtomMass;
	}
	public String getParentChineseName() {
		return parentChineseName;
	}
	public void setParentChineseName(String parentChineseName) {
		this.parentChineseName = parentChineseName;
	}
	public String getDaughterAtomNumber() {
		return daughterAtomNumber;
	}
	public void setDaughterAtomNumber(String daughterAtomNumber) {
		this.daughterAtomNumber = daughterAtomNumber;
	}
	public String getDaughterAtomMass() {
		return daughterAtomMass;
	}
	public void setDaughterAtomMass(String daughterAtomMass) {
		this.daughterAtomMass = daughterAtomMass;
	}
	public String getDaughterChineseName() {
		return daughterChineseName;
	}
	public void setDaughterChineseName(String daughterChineseName) {
		this.daughterChineseName = daughterChineseName;
	}
	public String getParentMass() {
		return parentMass;
	}
	public void setParentMass(String parentMass) {
		this.parentMass = parentMass;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getDaughterMass() {
		return daughterMass;
	}
	public void setDaughterMass(String daughterMass) {
		this.daughterMass = daughterMass;
	}
	public String getDaughterName() {
		return daughterName;
	}
	public void setDaughterName(String daughterName) {
		this.daughterName = daughterName;
	}
	public String getDecayType() {
		return decayType;
	}
	public void setDecayType(String decayType) {
		this.decayType = decayType;
	}
	public String getHalfLife() {
		return halfLife;
	}
	public void setHalfLife(String halfLife) {
		this.halfLife = halfLife;
	}
	public String getDecayIntensity() {
		return decayIntensity;
	}
	public void setDecayIntensity(String decayIntensity) {
		this.decayIntensity = decayIntensity;
	}
	public String getGammaEnergy() {
		return gammaEnergy;
	}
	public void setGammaEnergy(String gammaEnergy) {
		this.gammaEnergy = gammaEnergy;
	}
	public String getGammaIntensity() {
		return gammaIntensity;
	}
	public void setGammaIntensity(String gammaIntensity) {
		this.gammaIntensity = gammaIntensity;
	}
	public String getBetaEnergy() {
		return betaEnergy;
	}
	public void setBetaEnergy(String betaEnergy) {
		this.betaEnergy = betaEnergy;
	}
	public String getBetaIntensity() {
		return betaIntensity;
	}
	public void setBetaIntensity(String betaIntensity) {
		this.betaIntensity = betaIntensity;
	}
	public String getPostiveBetaEnergy() {
		return postiveBetaEnergy;
	}
	public void setPostiveBetaEnergy(String postiveBetaEnergy) {
		this.postiveBetaEnergy = postiveBetaEnergy;
	}
	public String getPostiveBetaIntensity() {
		return postiveBetaIntensity;
	}
	public void setPostiveBetaIntensity(String postiveBetaIntensity) {
		this.postiveBetaIntensity = postiveBetaIntensity;
	}
	public String getAlphaEnergy() {
		return alphaEnergy;
	}
	public void setAlphaEnergy(String alphaEnergy) {
		this.alphaEnergy = alphaEnergy;
	}
	public String getAlphaIntensity() {
		return alphaIntensity;
	}
	public void setAlphaIntensity(String alphaIntensity) {
		this.alphaIntensity = alphaIntensity;
	}
}
