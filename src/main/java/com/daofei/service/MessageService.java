package com.daofei.service;


import com.daofei.pojo.Comment;
import com.daofei.pojo.Message;

import java.util.List;
import java.util.Map;

public interface MessageService {
        List<Message> list();
        List<Message> selectMessageByContentAndTime(Map map);
        public int deleteMessageById(int id);
        public int addMessage(Message message);


        public int updateContent(Message message);
        public Message selectMessageById(int id);

}
