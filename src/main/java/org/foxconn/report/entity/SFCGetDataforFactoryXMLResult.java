package org.foxconn.report.entity;

import java.util.ArrayList;
import java.util.List;

public class SFCGetDataforFactoryXMLResult extends JCOInputRecord{
	String	SERIAL_NUMBER;
	String	TYPE;
	String	VALUE1;
	String	VALUE2;
	public String getSERIAL_NUMBER() {
		return SERIAL_NUMBER;
	}
	public void setSERIAL_NUMBER(String sERIAL_NUMBER) {
		SERIAL_NUMBER = sERIAL_NUMBER;
	}
	public String getTYPE() {
		return TYPE;
	}
	public void setTYPE(String tYPE) {
		TYPE = tYPE;
	}
	public String getVALUE1() {
		return VALUE1;
	}
	public void setVALUE1(String vALUE1) {
		VALUE1 = vALUE1;
	}
	public String getVALUE2() {
		return VALUE2;
	}
	public void setVALUE2(String vALUE2) {
		VALUE2 = vALUE2;
	}
	@Override
	public String getCalssName() {
		return "";
	}
	public List<String> getFiledList(){
		List<String> list = new ArrayList<String>();	
		list.add("SERIAL_NUMBER");
		list.add("TYPE");
		list.add("VALUE1");
		list.add("VALUE2");
		return list;
	}
	
}
