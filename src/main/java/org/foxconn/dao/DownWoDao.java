package org.foxconn.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DownWoDao {
	//返回值是否需要map
	public void insertWoHeader(Map<String,Object> map);
	public void	insertWoDetail(Map<String,Object> map);
}
