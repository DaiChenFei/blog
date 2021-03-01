package com.daofei.service.impl;

import com.daofei.mapper.CollectMapper;
import com.daofei.pojo.Collect;
import com.daofei.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollectServiceImpl implements CollectService {
   @Autowired
   CollectMapper collectMapper;


    @Override
    public List<Collect> selectCollectByUid(int id) {
        return collectMapper.selectCollectByUid(id);
    }

    @Override
    public Collect selectCollectById(int id) {
        return collectMapper.selectCollectById(id);
    }

    @Override
    public int deleteCollectById(int id) {
        return collectMapper.deleteCollectById(id);
    }

    @Override
    public int deleteCollectByAid(int aid) {
        return collectMapper.deleteCollectByAid(aid);
    }

    @Override
    public int addCollect(Collect collect) {
        return collectMapper.addCollect(collect);
    }
}
