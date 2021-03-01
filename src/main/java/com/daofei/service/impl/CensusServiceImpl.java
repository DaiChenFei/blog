package com.daofei.service.impl;

import com.daofei.mapper.CensusMapper;
import com.daofei.mapper.CollectMapper;
import com.daofei.pojo.Census;
import com.daofei.pojo.Collect;
import com.daofei.service.CensusService;
import com.daofei.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CensusServiceImpl implements CensusService {
   @Autowired
   CensusMapper censusMapper;


    @Override
    public Census getCensus() {
        return censusMapper.getCensus();
    }
}
