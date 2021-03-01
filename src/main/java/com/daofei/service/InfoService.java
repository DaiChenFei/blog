package com.daofei.service;


import com.daofei.pojo.Info;
import com.daofei.pojo.Tag;

import java.util.List;

public interface InfoService {


        public int updateInfo(Info info);
        public int updateInfoImg(String imgurl);
        public Info selectInfo();
}
