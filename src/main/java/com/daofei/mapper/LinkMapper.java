package com.daofei.mapper;

import com.daofei.pojo.Link;
import com.daofei.pojo.Recommend;

import java.util.List;

public interface LinkMapper {
    public List<Link> list();
    public List<Link> listByState();
    public  Link selectLinkById(int id);

    public int deleteLinkById(int id);
    public  int addLink(Link Link);
    public int updateState(Link link);
    public int updateInfo(Link link);
    public int updateUrl(Link link);



}
