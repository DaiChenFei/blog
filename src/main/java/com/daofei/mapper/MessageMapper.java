package com.daofei.mapper;

import com.daofei.pojo.Comment;
import com.daofei.pojo.Message;

import java.util.List;
import java.util.Map;

public interface MessageMapper {
    public List<Message> list();
    public  Message selectMessageById(int id);
    public   List<Message> selectMessageByAid(int aid);
    public List<Message> selectMessagetByContentAndTime(Map map);
    public int deleteMessageById(int id);
    public  int addMessage(Message message);

    public int updateContent(Message message);




}
