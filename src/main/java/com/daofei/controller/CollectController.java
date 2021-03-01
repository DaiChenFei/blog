package com.daofei.controller;


import com.daofei.pojo.Collect;
import com.daofei.pojo.User;
import com.daofei.service.CollectService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 告诉spring mvc这是一个控制器类
@Controller
@RequestMapping("")
public class CollectController {
	@Autowired
	CollectService collectService;

	//获得一个集合，这里用maven配置项目时要导入json包，不然不会解析，我的pom.xml已经配置了
	@RequestMapping("/listCollect")
	@ResponseBody
	public Map<String, Object> findAll(Integer page,HttpSession session){
		Map<String,Object> tableData =new HashMap<String,Object>();
		PageHelper.offsetPage(page,10);//page是开始页，即第几页
		User user= (User) session.getAttribute("userLogin");
		List<Collect> collects=collectService.selectCollectByUid(user.getId());
		if(collects.size()>0){
			System.out.println("执行集合收藏不为空");
		}else{
			System.out.println("执行集合为空");
			tableData.put("code",0);
			tableData.put("msg","查找分页加数据为0");
			tableData.put("user",user);
			tableData.put("count",0);
			return tableData;
		}
		PageInfo<Collect> pageInfo=new PageInfo<>(collects);
		tableData.put("code",1);
		tableData.put("msg","分页加数据操作成功");
		tableData.put("count",pageInfo.getTotal());
		System.out.println("count"+pageInfo.getTotal());
		tableData.put("user",user);
		tableData.put("data",pageInfo.getList());
		return tableData;
	}


	//删除单个，针对前台
	@RequestMapping("/deleteOneCollect")
	@ResponseBody
	public String deleteCollectById(Collect collect){
		System.out.println("执行删除收藏"+collect.getId());
		int result=collectService.deleteCollectById(collect.getId());
		return result+"";
	}


	//增加一个收藏，此处是前台用的
	@RequestMapping("/addOneCollect")
	@ResponseBody
	public String addCollect(Collect collect,HttpSession session){
		int result=0;
		System.out.println("执行添加Collect"+collect.getAbscontent());
		User user= (User) session.getAttribute("userLogin");
		Collect collect1=collectService.selectCollectById(collect.getAid());
		if (collect1==null){
			collect.setUid(user.getId());
			result=collectService.addCollect(collect);
			return result+"";
		}else if (collect1!=null){
			//针对用户点击已收藏，改为取消收藏
			collectService.deleteCollectByAid(collect.getAid());
			System.out.println("取消收藏");
		}





		return result+"";
	}



}
