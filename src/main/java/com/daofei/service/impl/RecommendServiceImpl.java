package com.daofei.service.impl;

import com.daofei.mapper.RecommendMapper;
import com.daofei.pojo.Recommend;
import com.daofei.service.RecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RecommendServiceImpl implements RecommendService {
	@Autowired
	RecommendMapper recommendMapper;

	@Override
	public List<Recommend> list() {
		return recommendMapper.list();
	}

	@Override
	public List<Recommend> listByState() {
		return recommendMapper.listByState();
	}

	@Override
	public Recommend selectRecommendById(int id) {
		return recommendMapper.selectRecommendById(id);
	}

	@Override
	public int deleteRecommendById(int id) {
		return recommendMapper.deleteRecommendById(id);
	}

	@Override
	public int addRecommend(Recommend recommend) {
		return recommendMapper.addRecommend(recommend);
	}

	@Override
	public int updateState(Recommend recommend) {
		return recommendMapper.updateState(recommend);
	}

	@Override
	public int updateInfo(Recommend recommend) {
		return recommendMapper.updateInfo(recommend);
	}
}
