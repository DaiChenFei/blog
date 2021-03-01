package com.daofei.service.impl;

import com.daofei.mapper.InfoMapper;
import com.daofei.mapper.TagMapper;
import com.daofei.pojo.Info;
import com.daofei.pojo.Tag;
import com.daofei.service.InfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InfoServiceImpl implements InfoService {
   @Autowired
    InfoMapper infoMapper;
    @Override
    public int updateInfo(Info info) {
        return infoMapper.updateInfo(info);
    }

    @Override
    public int updateInfoImg(String imgurl) {
        return infoMapper.updateInfoImg(imgurl);
    }

    @Override
    public Info selectInfo() {
        return infoMapper.selectInfo();
    }


}
