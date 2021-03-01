package com.daofei.service.impl;

import com.daofei.mapper.TagMapper;
import com.daofei.pojo.Tag;
import com.daofei.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagMapper tagMapper;


    @Override
    public List<Tag> list() {
        return tagMapper.list();
    }

    @Override
    public int deleteTagById(int id) {
        return tagMapper.deleteTagById(id);
    }

    @Override
    public int updateName(Tag tag) {
        return tagMapper.updateName(tag);
    }

    @Override
    public int addTag(String name) {
        return tagMapper.addTag(name);
    }

    @Override
    public Tag selectTagById(int id) {
        return tagMapper.selectTagById(id);
    }
}
