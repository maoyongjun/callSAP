package org.foxconn.report.client;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.dom4j.DocumentException;
import org.foxconn.callSAP.util.XmlUtil;
import org.foxconn.report.entity.WoDetail;
import org.foxconn.report.entity.WoDetails;
import org.foxconn.report.entity.WoHeaders;

import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;

public class WoDownLoadClient extends SAPBaseClient {
	
	// bResult = ObjSapFunction.ZRFC_GET_PRO_HEADER1(varException, _
	// PLANT:=strPlant, _
	// PO_NO:=pParam1, _
	// PO:=objData)
	
	public static Map<String,Object> downloadWO(String plant,String wo){
		
		JCoDestination destination = null;
		JCoFunction function = null;
		try {
			destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
			function = destination.getRepository().getFunction("ZRFC_GET_PRO_HEADER8");
		} catch (JCoException e1) {
			e1.printStackTrace();
		}

		if (function == null)
			throw new RuntimeException("ZRFC_GET_PRO_HEADER8 not found in SAP.");

		try {
			JCoParameterList jCoParameterList = function.getImportParameterList();
			jCoParameterList.setValue("PO_NO", wo);
			jCoParameterList.setValue("PLANT", plant);
			function.execute(destination);

		} catch (AbapException e) {
			System.out.println(e.toString());
		} catch (JCoException e) {
			e.printStackTrace();
		}
		JCoParameterList jCoParameterList = function.getTableParameterList();
		JCoTable table = jCoParameterList.getTable("PO");

		System.out.println(table.toString());
		System.out.println(table.toXML());
		String xmlStr =table.toXML();
		//方案一:xml转化为实体，然后由实体再遵化为map
//		WoHeaders bean = XmlUtil.toBean(xmlStr, WoHeaders.class);//xml转化为实体
//		System.out.println(bean.getItem());
//		Map<String,String> map =null;
//		try {
//			map  = BeanUtils.describe(bean.getItem());//实体转化为map
//		} catch (Exception e){
//			e.printStackTrace();
//		}
//		System.out.println(map);
		Map<String, Object> result= new HashMap<String, Object>();
		//方案二，直接转化为实体
		try {
			result=XmlUtil.dom2Map(xmlStr);
			System.out.println(result);//xml转化为map
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static void downloadWO() throws JCoException {
		 downloadWO("GHUE","000650072756");
	}
	
//	 bResult = ObjSapFunction.ZRFC_GET_PRO_DETAIL(varException2, _
//             PLANT:=strPlant, _
//             PO_NO:=pParam1, _
//             POD:=objData2)

		public static void downloadWODetail() throws JCoException {

			JCoDestination destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
			JCoFunction function = destination.getRepository().getFunction("ZRFC_GET_PRO_DETAIL");

			if (function == null)
				throw new RuntimeException("ZRFC_GET_PRO_DETAIL not found in SAP.");

			try {
				JCoParameterList jCoParameterList = function.getImportParameterList();
				jCoParameterList.setValue("PO_NO", "000650072756");
				jCoParameterList.setValue("PLANT", "GHUE");
				function.execute(destination);

			} catch (AbapException e) {
				System.out.println(e.toString());
				return;
			}
			JCoParameterList jCoParameterList = function.getTableParameterList();
			JCoTable table = jCoParameterList.getTable("POD");

			System.out.println(table.toString());
			System.out.println(table.toXML());
			String xmlStr =table.toXML();
			WoDetails bean = XmlUtil.toBean(xmlStr, WoDetails.class);
			System.out.println(bean);
			
			for(WoDetail d :bean.getList()){
				try {
//					System.out.println( BeanUtils.describe(d));
					System.out.println(XmlUtil.dom2Map(xmlStr));//xml转化为map 集合不能直接转化为map key值有相同的
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		}
}
