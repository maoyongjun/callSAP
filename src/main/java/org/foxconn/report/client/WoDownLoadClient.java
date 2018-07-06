package org.foxconn.report.client;

import org.foxconn.report.entity.MmprodmasterModel;

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
	public static void downloadWO() throws JCoException {

		JCoDestination destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
		JCoFunction function = destination.getRepository().getFunction("ZRFC_GET_PRO_HEADER1");

		if (function == null)
			throw new RuntimeException("ZRFC_GET_PRO_HEADER1 not found in SAP.");

		try {
			JCoParameterList jCoParameterList = function.getImportParameterList();
//			|--------|------|--------|------|----|------------|
//			| PARAMETERS 'INPUT'
//			|--------|------|--------|------|----|------------|
//			|AEDAT   |AEZEIT|ERDAT   |ERFZEI|PLAN|PO_NO       |
//			|--------|------|--------|------|----|------------|
//			|01234567|890123|45678901|234567|8901|234567890123|
//			|--------|------|--------|------|----|------------|
//			|00000000|000000|00000000|000000|    |*           |
//			|--------|------|--------|------|----|------------|

			jCoParameterList.setValue("PO_NO", "000060038254");
			jCoParameterList.setValue("PLANT", "GHUB");
			function.execute(destination);

		} catch (AbapException e) {
			System.out.println(e.toString());
			return;
		}
		JCoParameterList jCoParameterList = function.getTableParameterList();
		JCoTable table = jCoParameterList.getTable("PO");
//		|------------|----|----|------------------|--|----------|--------|---|----------------|----|--------|----------|-----------------------------------|--------|------------|---------|----------------------------------------|---|----------------------------------------|--------|------|------|------|----------|----------------|--------|--------------|------|----|------------------------------------------------------------------------------------------------------------------------------------|------|-----------------------------------|------|----------|---|------------|----------------------|---|----------------|------------|--------|-|--|-|--------|----------------|
//		| TABLE 'ZSSF08B'
//		|------------|----|----|------------------|--|----------|--------|---|----------------|----|--------|----------|-----------------------------------|--------|------------|---------|----------------------------------------|---|----------------------------------------|--------|------|------|------|----------|----------------|--------|--------------|------|----|------------------------------------------------------------------------------------------------------------------------------------|------|-----------------------------------|------|----------|---|------------|----------------------|---|----------------|------------|--------|-|--|-|--------|----------------|
//		|AUFNR       |WERK|AUAR|MATNR             |RE|KDAUF     |GSTRS   |DIS|GAMNG           |VERI|ARBPL   |KUNNR     |KDMAT                              |AEDAT   |AENAM       |MATKL    |MAKTX                                   |GME|STATUS                                  |ERDAT   |ERFZEI|AEZEIT|GSUZS |RSNUM     |KBEASOLL        |TASKGROU|CY_SEQNR      |ZAPLFL|ZVOR|ZTDLINE                                                                                                                             |KDPOS |BSTKD                              |POSEX_|TKNUM     |APR|MAUFNR      |OBJNR                 |FEV|WEMNG           |ERNAM       |IDAT2   |P|ST|S|VDATU   |VGW03           |
//		|------------|----|----|------------------|--|----------|--------|---|----------------|----|--------|----------|-----------------------------------|--------|------------|---------|----------------------------------------|---|----------------------------------------|--------|------|------|------|----------|----------------|--------|--------------|------|----|------------------------------------------------------------------------------------------------------------------------------------|------|-----------------------------------|------|----------|---|------------|----------------------|---|----------------|------------|--------|-|--|-|--------|----------------|
//		|012345678901|2345|6789|012345678901234567|89|0123456789|01234567|890|   1   2   3   4|5678|90123456|7890123456|78901234567890123456789012345678901|23456789|012345678901|234567890|1234567890123456789012345678901234567890|123|4567890123456789012345678901234567890123|45678901|234567|890123|456789|0123456789|   0   1   2   3|45678901|23456789012345|678901|2345|678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567|890123|45678901234567890123456789012345678|901234|5678901234|567|890123456789|0123456789012345678901|234|   5   6   7   8|901234567890|12345678|9|01|2|34567890|   1   2   3   4|
//		|------------|----|----|------------------|--|----------|--------|---|----------------|----|--------|----------|-----------------------------------|--------|------------|---------|----------------------------------------|---|----------------------------------------|--------|------|------|------|----------|----------------|--------|--------------|------|----|------------------------------------------------------------------------------------------------------------------------------------|------|-----------------------------------|------|----------|---|------------|----------------------|---|----------------|------------|--------|-|--|-|--------|----------------|
//
//		|------------|----|----|------------------|--|----------|--------|---|----------------|----|--------|----------|-----------------------------------|--------|------------|---------|----------------------------------------|---|----------------------------------------|--------|------|------|------|----------|----------------|--------|--------------|------|----|------------------------------------------------------------------------------------------------------------------------------------|------|-----------------------------------|------|----------|---|------------|----------------------|---|----------------|------------|--------|-|--|-|--------|----------------|

		System.out.println(table.toString());
		System.out.println(table.toXML());

		for (int i = 0; i < table.getNumRows(); i++) {
			table.setRow(i);
			System.out.println(table.getString("AUFNR"));
			System.out.println(table.getString("WERK"));
			System.out.println(table.getString("AUAR"));
			System.out.println(table.getString("MATNR"));
			System.out.println(table.getString("RE"));
			System.out.println(table.getString("KDAUF"));
			System.out.println(table.getString("GSTRS"));
			System.out.println(table.getString("DIS"));
			System.out.println(table.getString("GAMNG"));
			System.out.println(table.getString("VERI"));
			System.out.println(table.getString("ARBPL"));
			System.out.println(table.getString("KUNNR"));
			System.out.println(table.getString("KDMAT"));
		}

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
//				|--------|------|--------|------|----|------------|----|
//				| PARAMETERS 'INPUT'
//				|--------|------|--------|------|----|------------|----|
//				|AEDAT   |AEZEIT|ERDAT   |ERFZEI|PLAN|PO_NO       |UIDX|
//				|--------|------|--------|------|----|------------|----|
//				|01234567|890123|45678901|234567|8901|234567890123|4567|
//				|--------|------|--------|------|----|------------|----|
//				|00000000|000000|00000000|000000|    |            |ZF1 |
//				|--------|------|--------|------|----|------------|----|


				jCoParameterList.setValue("PO_NO", "000140174261");
				jCoParameterList.setValue("PLANT", "GHUC");
				function.execute(destination);

			} catch (AbapException e) {
				System.out.println(e.toString());
				return;
			}
			JCoParameterList jCoParameterList = function.getTableParameterList();
			JCoTable table = jCoParameterList.getTable("POD");

//			ZSSF09
//			AUFNR                           ,AUFNR                           ,C,12,0,12,0,0,0,0,Order Number
//			POSNR                           ,APOSN                           ,C,4,12,4,12,0,0,0,BOM item number
//			WERKS                           ,WERKS_D                         ,C,4,16,4,16,0,0,0,Plant
//			MATNR                           ,MATNR                           ,C,18,20,18,20,0,0,0,Material number
//			BDMNG                           ,NOMNG                           ,P,4,38,7,38,0,0,3,Required quantity
//			ENMNG                           ,ENMNG                           ,P,4,42,7,45,0,0,3,Quantity withdrawn
//			KDMAT                           ,KDMAT                           ,C,35,46,35,52,0,0,0,Material belonging to the customer
//			MEINS                           ,MEINS                           ,C,3,81,3,87,0,0,0,Base unit of measure
//			MATKL                           ,MATKL                           ,C,9,84,9,90,0,0,0,Material group
//			ALPGR                           ,CS_ALPGR                        ,C,2,93,2,99,0,0,0,Alternative item: group
//			MAKTX                           ,MAKTX                           ,C,40,95,40,101,0,0,0,Material description
//			DUMPS                           ,DUMPS                           ,C,1,135,1,141,0,0,0,Phantom item indicator
//			SOBSL                           ,SOBSL                           ,C,2,136,2,142,0,0,0,Special procurement type
//			REVLV                           ,REVLV                           ,C,2,138,2,144,0,0,0,Revision level
//			SHKZG                           ,SHKZG                           ,C,1,140,1,146,0,0,0,Debit/credit indicator
//			LGORT                           ,LGORT_D                         ,C,4,141,4,147,0,0,0,Storage location
//			QUANTITY                        ,                                ,P,3,145,6,151,0,0,3,Basic quantity
//			BAUGR                           ,BAUGR                           ,C,18,148,18,157,0,0,0,Material number of higher-level assembly
//			MENGE                           ,NOMNG                           ,P,4,166,7,175,0,0,3,Required quantity
//			CHARG                           ,CHARG_D                         ,C,10,170,10,182,0,0,0,Batch number
//			MOD_NO                          ,BAUGR                           ,C,18,180,18,192,0,0,0,Material number of higher-level assembly
//			POSTP                           ,POSTP                           ,C,1,198,1,210,0,0,0,Item category (bill of material)
//			SORTF                           ,SORTP                           ,C,10,199,10,211,0,0,0,Sort string
//			LIFNR                           ,LIFNR                           ,C,10,209,10,221,0,0,0,Account number of vendor or creditor
//			POTX1                           ,POTX1                           ,C,40,219,40,231,0,0,0,BOM item text (line 1)
//			POTX2                           ,POTX2                           ,C,40,259,40,271,0,0,0,BOM item text (line 2)
//			Record length: 311,0

			System.out.println(table.toString());
			System.out.println(table.toXML());

			for (int i = 0; i < table.getNumRows(); i++) {
				table.setRow(i);
				System.out.println(table.getString("AUFNR"));
			}

		}
}
