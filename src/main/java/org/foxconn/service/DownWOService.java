package org.foxconn.service;

import java.util.Map;

import javax.annotation.Resource;

import org.foxconn.dao.DownWoDao;
import org.foxconn.report.client.WoDownLoadClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DownWOService {
	
	@Resource
	DownWoDao  downWoDao;
	
//	@ResponseBody
//	@GetMapping("/import")
//	@RequestMapping("/import")
	@RequestMapping(value="/import", method=RequestMethod.GET) 
	public String downWo(){
		Map<String, Object> downloadWO = WoDownLoadClient.downloadWO("GHUE","000650072756");
		downWoDao.insertWoHeader((Map<String, Object>)downloadWO.get("item"));
		return "123";
	}
}
