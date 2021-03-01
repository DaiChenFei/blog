package com.daofei.mapper;

import com.daofei.pojo.Recommend;
import java.util.List;
import java.util.Map;

public interface RecommendMapper {
    public List<Recommend> list();
    public List<Recommend> listByState();
    public  Recommend selectRecommendById(int id);

    public int deleteRecommendById(int id);
    public  int addRecommend(Recommend recommend);
    public int updateState(Recommend recommend);
    public int updateInfo(Recommend recommend);




}
