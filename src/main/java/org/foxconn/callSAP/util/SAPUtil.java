package org.foxconn.callSAP.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.foxconn.report.entity.JCOInputRecord;
import org.foxconn.report.entity.SFCGetDataforFactoryXMLResult;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFieldIterator;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class SAPUtil {
	static String ABAP_AS = "ABAP_AS_WITHOUT_POOL";
    static String ABAP_AS_POOLED = "ABAP_AS_WITH_POOL";
    static String ABAP_MS = "ABAP_MS_WITHOUT_POOL";
    static
    {
        Properties connectProperties = new Properties();
        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "10.134.28.85");
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,  "02");
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "801");
        connectProperties.setProperty(DestinationDataProvider.JCO_USER,   "SFC_USER");
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "yhpwd");
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG,   "en");
        createDataFile(ABAP_AS, "jcoDestination", connectProperties);

        connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, "3");
        connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT,    "10");
        createDataFile(ABAP_AS_POOLED, "jcoDestination", connectProperties);

        connectProperties.clear();
        connectProperties.setProperty(DestinationDataProvider.JCO_MSHOST, "10.134.28.99");
        connectProperties.setProperty(DestinationDataProvider.JCO_R3NAME,  "LH1");
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "801");
        connectProperties.setProperty(DestinationDataProvider.JCO_USER,   "SFC_USER");
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "yhpwd");
        connectProperties.setProperty(DestinationDataProvider.JCO_GROUP, "PUBLIC");
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG,   "en");
        createDataFile(ABAP_MS, "jcoDestination", connectProperties);
    }
    
    static void createDataFile(String name, String suffix, Properties properties)
    {
        File cfg = new File(name+"."+suffix);
        if(!cfg.exists())
        {
            try
            {
                FileOutputStream fos = new FileOutputStream(cfg, false);
                properties.store(fos, "for tests only !");
                fos.close();
            }
            catch (Exception e)
            {
                throw new RuntimeException("Unable to create the destination file " + cfg.getName(), e);
            }
        }
    }
    /**
     * 通过指定的rfc functionName,已经输入的字符串，获取返回的table信息
     * 例如下载物料
     * @param functionName
     * @param inputMap
     * @param outputTableName
     * @return
     * @throws JCoException 
     */
    public static JCoTable callFunction(String functionName,Map<String,String> inputMap,String outputTableName) throws JCoException {
    	JCoDestination destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
        JCoFunction function = destination.getRepository().getFunction(functionName);
        
        if(function == null)
            throw new RuntimeException(functionName +"not found in SAP.");
        setInputParameter(function, inputMap);
        function.execute(destination);
        JCoTable table =getOutPutTable(function,outputTableName);
       return table;
     
    }
    /**
     * 
     * @param functionName
     * @param inputTableName
     * @param inputList
     * @param outPutParam
     * @return
     * @throws JCoException
     */
    public static String callFunction(String functionName,String inputTableName,List<? extends JCOInputRecord> inputList,String outPutParam) throws JCoException {
    	JCoDestination destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
        JCoFunction function = destination.getRepository().getFunction(functionName);
        
        if(function == null)
            throw new RuntimeException(functionName +"not found in SAP.");
        setInputParameter(function,inputTableName,inputList);
        function.execute(destination);
        String outPut =getOutPutString(function,outPutParam);
        return outPut;
     
    }
    /**
     * 通过输入的String 字符串
     * 以及输入的table
     * 调用rfc函数
     * 返回字符串
     * @param functionName
     * @param inputMap
     * @param inputTableName
     * @param inputList
     * @param outPutParam
     * @return
     * @throws JCoException
     */
    public static String callFunction(String functionName,Map<String,String> inputMap,String inputTableName,List<? extends JCOInputRecord> inputList,String outPutParam) throws JCoException {
    	JCoDestination destination = JCoDestinationManager.getDestination(ABAP_AS_POOLED);
        JCoFunction function = destination.getRepository().getFunction(functionName);
        
        if(function == null)
            throw new RuntimeException(functionName +"not found in SAP.");
        setInputParameter(function,inputMap,inputTableName,inputList);
        function.execute(destination);
        String outPut =getOutPutString(function,outPutParam);
        return outPut;
     
    }
    
    public static void debugOutPutString(JCoTable table){
    	 List<String> outputFiledNames = new ArrayList<String>();
         JCoFieldIterator iterator =  table.getFieldIterator();
         while(iterator.hasNextField()){
         	JCoField jcoField = iterator.nextField();
         	outputFiledNames.add(jcoField.getName());
         }
         
     	for (int i = 0; i < table.getNumRows(); i++) {
     	
        	   table.setRow(i);
        	   for(int j=0;j<outputFiledNames.size();j++){
        		System.out.print(outputFiledNames.get(j)+":"+table.getString(outputFiledNames.get(j))+";");
        	   }
        	   System.out.println();
        }
    }
    
    public static void main(String[] args) throws JCoException{
    	
        Map<String,String> map = new HashMap<String, String>();
//        map.put("ORDERNUM", "0200247362");
//    	map.put("PLANT", "GHGG");
//        JCoTable table = callFunction("ZRFC_GET_SALES_HEADER2_GSC",map,"SOMASTER");
//        debugOutPutString(table);
//         table = callFunction("ZRFC_GET_SALES_HEADER2_GSC",map,"BILL_TO");
//         debugOutPutString(table);
        List <SFCGetDataforFactoryXMLResult> inputList = new ArrayList<SFCGetDataforFactoryXMLResult>();
        SFCGetDataforFactoryXMLResult sfcGetDataforFactoryXMLResult = new SFCGetDataforFactoryXMLResult();
        sfcGetDataforFactoryXMLResult.setSERIAL_NUMBER("TEST");
        sfcGetDataforFactoryXMLResult.setTYPE("testType");
        sfcGetDataforFactoryXMLResult.setVALUE1("testValue1");
        sfcGetDataforFactoryXMLResult.setVALUE2("testValue2");
        inputList.add(sfcGetDataforFactoryXMLResult);
        map.put("PLANT","GHUB");
    }
    /**
     * 设置输入的参数，针对输入的参数是字符串
     * @param function
     * @param inputMap
     * @return
     */
     private static JCoFunction setInputParameter(JCoFunction function,Map<String,String> inputMap){
     	JCoParameterList  inputString = function.getImportParameterList();
      	for(Entry<String, String> entry:inputMap.entrySet()){
      		inputString.setValue(entry.getKey(), entry.getValue());
      	}
     	return function;
     }
     /**
      * 设置输入的参数，针对输入的参数是table
      * 使用list 转载输入的table,其中list里面的元素
      * 需要继承JCOInputRecord，并且实现里面的两个方面
      * 便于取值时通过反射机制取出
      * @param function
      * @param inputTableName
      * @param inputList
      * @return
      */
     private static JCoFunction setInputParameter(JCoFunction function,String inputTableName,List<? extends JCOInputRecord> inputList){
     	JCoTable  inputJcoTable = function.getTableParameterList().getTable(inputTableName);
      	for(JCOInputRecord record :inputList){
      		for(String filed:record.getFiledList()){
      			inputJcoTable.appendRow();
      			inputJcoTable.setValue(filed, RefletUtil.getFieldValue(record, filed, String.class));
      		}
      	}
     	return function;
     }
     /**
      * 设置输入的参数，输入的参数为table和字符串两种
      * @param function
      * @param inputMap
      * @param inputTableName
      * @param inputList
      * @return
      */
     private static JCoFunction setInputParameter(JCoFunction function,Map<String,String> inputMap,String inputTableName,List<? extends JCOInputRecord> inputList){
      	JCoTable  inputJcoTable = function.getTableParameterList().getTable(inputTableName);
       	for(JCOInputRecord record :inputList){
       		for(String filed:record.getFiledList()){
       			inputJcoTable.appendRow();
       			inputJcoTable.setValue(filed, RefletUtil.getFieldValue(record, filed, String.class));
       		}
       	}
       	JCoParameterList  inputString = function.getImportParameterList();
      	for(Entry<String, String> entry:inputMap.entrySet()){
      		inputString.setValue(entry.getKey(), entry.getValue());
      	}
      	return function;
      }
     
     /**
      * 获取输出的jcoTable
      * @param function
      * @param outputTableName
      * @return
      */
     private static JCoTable getOutPutTable(JCoFunction function,String outputTableName){
    	 JCoTable  outputJcoTable = function.getTableParameterList().getTable(outputTableName);
    	 System.out.println(outputJcoTable.toString());
         System.out.println(outputJcoTable.toXML());
    	 return outputJcoTable;
     }
     
     /**
      * 获取输出的jcoTable
      * @param function
      * @param inputTableName
      * @return
      */
     private static String getOutPutString(JCoFunction function,String ouput){
    	 String result =  function.getExportParameterList().getString(ouput);
    	 return result;
     }
     
     
}
