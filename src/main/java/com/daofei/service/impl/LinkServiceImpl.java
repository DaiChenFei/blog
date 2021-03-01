package com.daofei.service.impl;

import com.daofei.mapper.LinkMapper;
import com.daofei.pojo.Link;
import com.daofei.pojo.Recommend;
import com.daofei.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LinkServiceImpl implements LinkService {
	@Autowired
	LinkMapper linkMapper;


	@Override
	public List<Link> list() {
		return linkMapper.list();
	}

	@Override
	public List<Link> listByState() {
		return linkMapper.listByState();
	}

	@Override
	public Link selectLinkById(int id) {
		return linkMapper.selectLinkById(id);
	}

	@Override
	public int deleteLinkById(int id) {
		return linkMapper.deleteLinkById(id);
	}

	@Override
	public int addLink(Link link) {
		return linkMapper.addLink(link);
	}

	@Override
	public int updateState(Link link) {
		return linkMapper.updateState(link);
	}

	@Override
	public int updateInfo(Link link) {
		return linkMapper.updateInfo(link);
	}

	@Override
	public int updateUrl(Link link) {
		return linkMapper.updateUrl(link);
	}
}
