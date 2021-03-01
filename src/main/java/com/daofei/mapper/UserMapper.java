package com.daofei.mapper;

import com.daofei.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    public List<User> list();
    public List<User> selectUserByUsernameAndTime(Map map);
    public int deleteUserById(int id);
    public  User selectUserById(int id);
    public  User selectUserByUsername(String username);
    public int updateState(User user);
    public int updatePass(User user);
    public int updateNick(User user);
    public int updateEmail(User user);
    public int updatePhone(User user);
    public int updateSex(User user);
    public int updateImg(User user);
    public int updateInfo(User user);
    public  int addUser(User user);
}
