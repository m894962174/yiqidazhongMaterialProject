package com.oracle.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oracle.dao.PartsMapper;
import com.oracle.vo.Parts;

@Service
public class PartsService {

	@Autowired
	PartsMapper partsdao;
	
	@Transactional(readOnly=true)
	public List<Parts> selectAll(){
		return partsdao.selectAll();
	}
	
	@Transactional
	public void deleteByPrimaryKey(Integer partsid) {
		partsdao.deleteByPrimaryKey(partsid);
	}
	
	@Transactional(readOnly=true)
	public Parts selectByPrimaryKey(Integer partsid){
		return partsdao.selectByPrimaryKey(partsid);
	}
	
	@Transactional
	public void insert(Parts parts) {
		partsdao.insert(parts);
	}
	
	
	@Transactional
	public void updateByPrimaryKeySelective(Parts record) {
		partsdao.updateByPrimaryKey(record);
	}
}
