package com.daofei.service.impl;

import com.daofei.mapper.TypeMapper;
import com.daofei.mapper.UserMapper;
import com.daofei.pojo.Type;
import com.daofei.pojo.User;
import com.daofei.service.TypeService;
import com.daofei.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    TypeMapper typeMapper;

    @Override
    public List<Type> list() {
        return typeMapper.list();
    }

    @Override
    public int deleteTypeById(int id) {
        return typeMapper.deleteTypeById(id);
    }

    @Override
    public int updateName(Type type) {
        return typeMapper.updateName(type);
    }

    @Override
    public int addType(String name) {
        return typeMapper.addType(name);
    }

    @Override
    public Type selectTypeById(int id) {
        return typeMapper.selectTypeById(id);
    }
}
