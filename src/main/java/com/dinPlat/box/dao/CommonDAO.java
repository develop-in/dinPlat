package com.dinPlat.box.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository ("CommonDAO")
public class CommonDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	/*
	 * This method get List type data in use.
	 */
	public List getListData (String sqlId, Object param) throws Exception {
		return sqlSession.selectList(sqlId, param);
	}
	
	/*
	 * This method get single type data in use.
	 */
	public Object getSingleData (String sqlId, Object param) throws Exception {
		return sqlSession.selectOne(sqlId, param);
	}
	
	/*
	 * insert
	 */
	public int insert (String sqlId, Object param) throws Exception {
		return sqlSession.insert(sqlId, param);
	}
	
	/*
	 * delete
	 */
	public int delete (String sqlId, Object param) throws Exception {
		return sqlSession.delete(sqlId, param);
	}
	
	/*
	 * update
	 */
	public int update (String sqlId, Object param) throws Exception {
		return sqlSession.update(sqlId, param);
	}
}
