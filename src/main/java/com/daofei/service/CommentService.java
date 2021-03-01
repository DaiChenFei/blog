package com.daofei.service;


import com.daofei.pojo.Comment;

import java.util.List;
import java.util.Map;

public interface CommentService {
        List<Comment> list();
        List<Comment> selectCommentByContentAndTime(Map map);
        public int deleteCommentById(int id);
        public int deleteCommentByAid(int id);
        public int deleteCommentByUid(int id);
        public int addComment(Comment comment);
        public int updateState(Comment comment);

        public int updateContent(Comment comment);
        public Comment selectCommentById(int id);
        public  List<Comment> selectCommentByAid(int aid);
}
