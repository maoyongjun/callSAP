package org.foxconn.report.client;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import com.sap.conn.jco.ext.DestinationDataProvider;

public class SAPBaseClient {
	static String ABAP_AS = "ABAP_AS_WITHOUT_POOL";
	static String ABAP_AS_POOLED = "ABAP_AS_WITH_POOL";
	static String ABAP_MS = "ABAP_MS_WITHOUT_POOL";
	static {
		Properties connectProperties = new Properties();
		connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "10.134.28.85");
		connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, "02");
		connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "801");
		connectProperties.setProperty(DestinationDataProvider.JCO_USER, "SFC_USER");
		connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "yhpwd");
		connectProperties.setProperty(DestinationDataProvider.JCO_LANG, "en");
		createDataFile(ABAP_AS, "jcoDestination", connectProperties);

		connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, "3");
		connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT, "10");
		createDataFile(ABAP_AS_POOLED, "jcoDestination", connectProperties);

		connectProperties.clear();
		connectProperties.setProperty(DestinationDataProvider.JCO_MSHOST, "10.134.28.99");
		connectProperties.setProperty(DestinationDataProvider.JCO_R3NAME, "LH1");
		connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "801");
		connectProperties.setProperty(DestinationDataProvider.JCO_USER, "SFC_USER");
		connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "yhpwd");
		connectProperties.setProperty(DestinationDataProvider.JCO_GROUP, "PUBLIC");
		connectProperties.setProperty(DestinationDataProvider.JCO_LANG, "en");
		createDataFile(ABAP_MS, "jcoDestination", connectProperties);
	}

	static void createDataFile(String name, String suffix, Properties properties) {
		File cfg = new File(name + "." + suffix);
		if (!cfg.exists()) {
			try {
				FileOutputStream fos = new FileOutputStream(cfg, false);
				properties.store(fos, "for tests only !");
				fos.close();
			} catch (Exception e) {
				throw new RuntimeException("Unable to create the destination file " + cfg.getName(), e);
			}
		}
	}

}
