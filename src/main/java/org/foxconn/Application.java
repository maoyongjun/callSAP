package org.foxconn;

import org.foxconn.report.client.WoDownLoadClient;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import com.sap.conn.jco.JCoException;

@SpringBootApplication
@MapperScan("org.foxconn.dao")
//@EntityScan(basePackages = "org.foxconn")
public class Application {
	public static void main(String[] args) {
//		StepByStepClient.step3SimpleCall();
//		StepByStepClient.step5DownMMprodmastercalls();
//		WoDownLoadClient.downloadWO();
//		WoDownLoadClient.downloadWODetail();
		SpringApplication.run(Application.class, args);
		
	}
}
