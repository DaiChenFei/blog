package com.daofei.service;


import com.daofei.pojo.Type;
import com.daofei.pojo.User;

import java.util.List;
import java.util.Map;

public interface TypeService {
        List<Type> list();

        public int deleteTypeById(int id);

        public int updateName(Type type);
        public int addType(String name);
        public Type selectTypeById(int id);
}
