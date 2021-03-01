package com.daofei.mapper;

import com.daofei.pojo.Collect;

import java.util.List;

public interface CollectMapper {

    public List<Collect>  selectCollectByUid(int id);
    public Collect  selectCollectById(int id);
    public int deleteCollectById(int id);
    public int deleteCollectByAid(int aid);
    public  int addCollect(Collect collect);




}
