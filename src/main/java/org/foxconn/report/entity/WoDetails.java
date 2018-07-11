package org.foxconn.report.entity;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("ZSSF09")
public class WoDetails {
	@XStreamImplicit
	private List<WoDetail> list;

	public List<WoDetail> getList() {
		return list;
	}

	public void setList(List<WoDetail> list) {
		this.list = list;
	}

	
}
