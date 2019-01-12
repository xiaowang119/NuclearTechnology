package ensdfparser;

import java.util.Vector;

public class SonNuclide {
	private String sonName;
	private String sonMass;
	private String sonAtomNumber;
	private String sonAtomMass;
	private String sonChineseName;
	private String halfLife;
	private String decayIntensity;
	private String decayType;
	private Vector<String> gammaEnergyVector;
	private Vector<String> gammaIntensityVector;
	private Vector<String> betaEnergyVector;
	private Vector<String> betaIntenistyVector;
	private Vector<String> postiveBetaEnergyVector;
	private Vector<String> postiveBetaIntensityVector;
	private Vector<String> alphaEnergyVector;
	private Vector<String> alphaIntensityVector;


	public String getSonName() {
		return sonName;
	}
	public void setSonName(String sonName) {
		this.sonName = sonName;
	}
	public String getSonMass() {
		return sonMass;
	}
	public void setSonMass(String sonMass) {
		this.sonMass = sonMass;
	}
	public String getDecayIntensity() {
		return decayIntensity;
	}
	public void setDecayIntensity(String decayIntensity) {
		this.decayIntensity = decayIntensity;
	}
	public String getDecayTypeString() {
		return decayType;
	}
	public void setDecayTypeString(String decayTypeString) {
		this.decayType = decayTypeString;
	}
	public Vector<String> getGammaEnergyVector() {
		return gammaEnergyVector;
	}
	public void setGammaEnergyVector(Vector<String> gammaEnergyVector) {
		this.gammaEnergyVector = gammaEnergyVector;
	}
	public Vector<String> getGammaIntensityVector() {
		return gammaIntensityVector;
	}
	public void setGammaIntensityVector(Vector<String> gammaIntensityVector) {
		this.gammaIntensityVector = gammaIntensityVector;
	}
	public Vector<String> getBetaEnergyVector() {
		return betaEnergyVector;
	}
	public void setBetaEnergyVector(Vector<String> betaEnergyVector) {
		this.betaEnergyVector = betaEnergyVector;
	}
	public Vector<String> getBetaIntenistyVector() {
		return betaIntenistyVector;
	}
	public void setBetaIntenistyVector(Vector<String> betaIntenistyVector) {
		this.betaIntenistyVector = betaIntenistyVector;
	}
	public Vector<String> getPostiveBetaEnergyVector() {
		return postiveBetaEnergyVector;
	}
	public void setPostiveBetaEnergyVector(Vector<String> postiveBetaEnergyVector) {
		this.postiveBetaEnergyVector = postiveBetaEnergyVector;
	}
	public Vector<String> getPostiveBetaIntensityVector() {
		return postiveBetaIntensityVector;
	}
	public void setPostiveBetaIntensityVector(
			Vector<String> postiveBetaIntensityVector) {
		this.postiveBetaIntensityVector = postiveBetaIntensityVector;
	}
	public Vector<String> getAlphaEnergyVector() {
		return alphaEnergyVector;
	}
	public void setAlphaEnergyVector(Vector<String> alphaEnergyVector) {
		this.alphaEnergyVector = alphaEnergyVector;
	}
	public Vector<String> getAlphaIntensityVector() {
		return alphaIntensityVector;
	}
	public void setAlphaIntensityVector(Vector<String> alphaIntensityVector) {
		this.alphaIntensityVector = alphaIntensityVector;
	}

	public String getSonAtomNumber() {
		return sonAtomNumber;
	}
	public void setSonAtomNumber(String sonAtomNumber) {
		this.sonAtomNumber = sonAtomNumber;
	}
	public String getSonAtomMass() {
		return sonAtomMass;
	}
	public void setSonAtomMass(String sonAtomMass) {
		this.sonAtomMass = sonAtomMass;
	}
	public String getSonChineseName() {
		return sonChineseName;
	}
	public void setSonChineseName(String sonChineseName) {
		this.sonChineseName = sonChineseName;
	}
	public String getHalfLife() {
		return halfLife;
	}
	public void setHalfLife(String halfLife) {
		this.halfLife = halfLife;
	}

}
