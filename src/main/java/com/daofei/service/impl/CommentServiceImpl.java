package com.daofei.service.impl;

import com.daofei.mapper.CommentMapper;
import com.daofei.pojo.Comment;
import com.daofei.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	CommentMapper commentMapper;

	@Override
	public List<Comment> list() {
		return commentMapper.list();
	}

	@Override
	public List<Comment> selectCommentByContentAndTime(Map map) {
		return commentMapper.selectCommentByContentAndTime(map);
	}

	@Override
	public int deleteCommentById(int id) {
		return commentMapper.deleteCommentById(id);
	}

	@Override
	public int deleteCommentByAid(int id) {
		return commentMapper.deleteCommentByAid(id);
	}

	@Override
	public int deleteCommentByUid(int id) {
		return commentMapper.deleteCommentByUid(id);
	}

	@Override
	public int addComment(Comment comment) {
		return commentMapper.addComment(comment);
	}

	@Override
	public int updateState(Comment comment) {
		return commentMapper.updateState(comment);
	}

	@Override
	public int updateContent(Comment comment) {
		return commentMapper.updateContent(comment);
	}

	@Override
	public Comment selectCommentById(int id) {
		return commentMapper.selectCommentById(id);
	}

	@Override
	public  List<Comment> selectCommentByAid(int aid) {
		return  commentMapper.selectCommentByAid(aid);
	}
}
