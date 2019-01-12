package ensdfparser;

import java.util.Vector;


public class StructDecay {

	private String parentName;
	private String parentMass;
	private String parentAtomNumber;
	private String parentAtomMass;
	private String parentChineseName;
	private Vector<SonNuclide> soNuclides;

	private String group;
	private String level1,level2,level3,level4, level5;

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getLevel1() {
		return level1;
	}

	public void setLevel1(String level1) {
		this.level1 = level1;
	}

	public String getLevel2() {
		return level2;
	}

	public void setLevel2(String level2) {
		this.level2 = level2;
	}

	public String getLevel3() {
		return level3;
	}

	public void setLevel3(String level3) {
		this.level3 = level3;
	}

	public String getLevel4() {
		return level4;
	}

	public void setLevel4(String level4) {
		this.level4 = level4;
	}

	public String getLevel5() {
		return level5;
	}

	public void setLevel5(String level5) {
		this.level5 = level5;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public String getParentMass() {
		return parentMass;
	}
	public void setParentMass(String parentMass) {
		this.parentMass = parentMass;
	}

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
	public Vector<SonNuclide> getSoNuclides() {
		return soNuclides;
	}
	public void setSoNuclides(Vector<SonNuclide> soNuclides) {
		this.soNuclides = soNuclides;
	}

}
