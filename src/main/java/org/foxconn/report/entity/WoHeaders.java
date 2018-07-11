package org.foxconn.report.entity;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("ZSSF08B")
public class WoHeaders {
	//@XStreamImplicit//加上这个标签外面可以不用<LIST>
	private WoHeader item;

	public WoHeader getItem() {
		return item;
	}

	public void setItem(WoHeader item) {
		this.item = item;
	}

	
}
