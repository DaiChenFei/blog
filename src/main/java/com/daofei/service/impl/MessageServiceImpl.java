package com.daofei.service.impl;

import com.daofei.mapper.MessageMapper;
import com.daofei.pojo.Message;
import com.daofei.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements MessageService {
	@Autowired
	MessageMapper messageMapper;

	@Override
	public List<Message> list() {
		return messageMapper.list();
	}

	@Override
	public List<Message> selectMessageByContentAndTime(Map map) {
		return messageMapper.selectMessagetByContentAndTime(map);
	}

	@Override
	public int deleteMessageById(int id) {
		return messageMapper.deleteMessageById(id);
	}

	@Override
	public int addMessage(Message message) {
		return messageMapper.addMessage(message);
	}

	@Override
	public int updateContent(Message message) {
		return messageMapper.updateContent(message);
	}

	@Override
	public Message selectMessageById(int id) {
		return selectMessageById(id);
	}
}
