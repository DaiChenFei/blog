package com.daofei.mapper;

import com.daofei.pojo.Type;
import com.daofei.pojo.User;

import java.util.List;
import java.util.Map;

public interface TypeMapper {
    public List<Type> list();
    public int deleteTypeById(int id);
    public  Type selectTypeById(int id);
    public int updateName(Type type);
    public int addType(String name);
}
