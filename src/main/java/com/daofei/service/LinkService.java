package com.daofei.service;



import com.daofei.pojo.Link;

import java.util.List;


public interface LinkService {
        public List<Link> list();
        public List<Link> listByState();
        public  Link selectLinkById(int id);
        public int deleteLinkById(int id);
        public  int addLink(Link link);
        public int updateState(Link link);
        public int updateInfo(Link link);
        public int updateUrl(Link link);
}
