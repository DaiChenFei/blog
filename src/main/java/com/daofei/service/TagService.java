package com.daofei.service;


import com.daofei.pojo.Tag;

import java.util.List;

public interface TagService {
        List<Tag> list();

        public int deleteTagById(int id);

        public int updateName(Tag tag);
        public int addTag(String name);
        public Tag selectTagById(int id);
}
