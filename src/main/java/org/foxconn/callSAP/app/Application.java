package org.foxconn.callSAP.app;

import org.foxconn.report.client.WoDownLoadClient;

import com.sap.conn.jco.JCoException;

public class Application {
	public static void main(String[] args) throws JCoException {
//		StepByStepClient.step3SimpleCall();
//		StepByStepClient.step5DownMMprodmastercalls();
//		WoDownLoadClient.downloadWO();
		WoDownLoadClient.downloadWODetail();
		
	}
}
