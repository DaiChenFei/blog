package com.daofei.mapper;

import com.daofei.pojo.Info;

public interface InfoMapper {
    public Info selectInfo();
    public int updateInfo(Info info);
    public int updateInfoImg(String imgurl);
}
