package com.daofei.service.impl;

import com.daofei.mapper.AdminMapper;
import com.daofei.pojo.Admin;
import com.daofei.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
	@Autowired
    AdminMapper adminMapper;


	@Override
	public Admin selectById(int id) {
		return adminMapper.get(id);
	}
}
