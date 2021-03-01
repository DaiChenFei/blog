package com.daofei.service.impl;

import com.daofei.mapper.AdminMapper;
import com.daofei.mapper.UserMapper;
import com.daofei.pojo.User;
import com.daofei.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> list() {
        return userMapper.list();
    }

    @Override
    public List<User> selectUserByUsernameAndTime(Map map) {
        return userMapper.selectUserByUsernameAndTime(map);
    }

    @Override
    public int deleteUserById(int id) {
        return userMapper.deleteUserById(id);
    }

    @Override
    public int addUser(User user) {
        return userMapper.addUser(user);
    }

    @Override
    public int updateState(User user) {
        return userMapper.updateState(user);
    }

    @Override
    public int updatePass(User user) {
        return userMapper.updatePass(user);
    }

    @Override
    public int updateNick(User user) {
        return userMapper.updateNick(user);
    }

    @Override
    public int updateEmail(User user) {
        return userMapper.updateEmail(user);
    }

    @Override
    public int updatePhone(User user) {
        return userMapper.updatePhone(user);
    }

    @Override
    public int updateSex(User user) {
        return userMapper.updateSex(user);
    }

    @Override
    public int updateImg(User user) {
        return userMapper.updateImg(user);
    }

    @Override
    public int updateInfo(User user) {
        return userMapper.updateInfo(user);
    }

    @Override
    public User selectUserById(int id) {
        return userMapper.selectUserById(id);
    }

    @Override
    public User selectUserByUsername(String username) {
        return userMapper.selectUserByUsername(username);
    }
}
