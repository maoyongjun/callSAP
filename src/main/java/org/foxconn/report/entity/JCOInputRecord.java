package org.foxconn.report.entity;

import java.util.ArrayList;
import java.util.List;

public abstract class JCOInputRecord {
	public String getCalssName(){
		return "";
	}
	public List<String> getFiledList(){
		return new ArrayList<String>();
	}

}
