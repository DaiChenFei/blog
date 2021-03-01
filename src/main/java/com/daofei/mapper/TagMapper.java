package com.daofei.mapper;

import com.daofei.pojo.Tag;

import java.util.List;

public interface TagMapper {
    public List<Tag> list();
    public int deleteTagById(int id);
    public  Tag selectTagById(int id);
    public int updateName(Tag tag);
    public int addTag(String name);
}
