package com.daofei.controller;



import com.daofei.pojo.Recommend;
import com.daofei.pojo.User;

import com.daofei.service.RecommendService;
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
public class RecommendController {
	@Autowired
	RecommendService recommendService;

	//获得一个Comment集合，这里用maven配置项目时要导入json包，不然不会解析，我的pom.xml已经配置了
	@RequestMapping("admin/listRecommend")
	@ResponseBody
	public Map<String, Object> findAll(Integer page){
		Map<String,Object> tableData =new HashMap<String,Object>();
		PageHelper.offsetPage(page,10);//page是开始页，即第几页
		List<Recommend> recommends=recommendService.list();
		int total = (int) new PageInfo<>(recommends).getTotal();

		if(recommends.size()>0){
			System.out.println("执行集合comments不为空");
		}else{
			System.out.println("执行集合为空");
			tableData.put("code",0);
			tableData.put("msg","查找分页加数据为0");
			tableData.put("count",0);
			return tableData;

		}
		PageInfo<Recommend> pageInfo=new PageInfo<>(recommends);
		tableData.put("code",1);
		tableData.put("msg","分页加数据操作成功");
		tableData.put("count",pageInfo.getTotal());
		System.out.println("count"+pageInfo.getTotal());
		tableData.put("data",pageInfo.getList());
		return tableData;
	}
	@RequestMapping("/listRecommend")
	@ResponseBody
	public Map<String, Object> findAllByState(){
		Map<String,Object> tableData =new HashMap<String,Object>();

		List<Recommend> recommends=recommendService.listByState();


		if(recommends.size()>0){
			System.out.println("执行集合comments不为空");
		}else{
			System.out.println("执行集合为空");
			tableData.put("code",0);
			tableData.put("msg","查找分页加数据为0");
			tableData.put("count",0);
			return tableData;

		}
		PageInfo<Recommend> pageInfo=new PageInfo<>(recommends);
		tableData.put("code",1);
		tableData.put("msg","分页加数据操作成功");
		tableData.put("count",pageInfo.getTotal());
		System.out.println("count"+pageInfo.getTotal());
		tableData.put("data",recommends);
		return tableData;
	}

	//删除单个，针对后台
	@RequestMapping("admin/deleteOneRecommend")
	@ResponseBody
	public String deleteCommentById(Recommend recommend){
		System.out.println("执行删除用户"+recommend.getId());
		int result=recommendService.deleteRecommendById(recommend.getId());


		return result+"";
	}

	//更新一个评论的内容
	@RequestMapping("admin/updateOneRecommend")
	@ResponseBody
	public String updateCommentById(Recommend recommend){

		if("".equals(recommend.getId())){
			return "0";
		}
		System.out.println("执行更新Comment"+recommend.getId());
		int result=recommendService.updateInfo(recommend);


		return result+"";
	}
	//删除多个
	@RequestMapping("admin/deleteAllRecommend")
	@ResponseBody
	public String deleteCommentAll(String id,int num){
		if (id!=null &&!id.equals("")) {
			String[] ids = id.split(",");
			for (String uid : ids) {
				if(uid!=null &&!uid.equals("")){
					recommendService.deleteRecommendById(Integer.parseInt(uid));
					num--;
				}
			}
		}
		System.out.println("执行删除所有Recommend，执行成功剩余数"+num);

		if(num==0){
			return "1";
		}else {
			return "0";
		}


	}
	//增加一个评论，此处是后台用的
	@RequestMapping("admin/addOneRecommend")
	@ResponseBody
	public String addComment(Recommend recommend){
		System.out.println("执行添加Comment"+recommend.getInfo());

		int result=0;
		 result=recommendService.addRecommend(recommend);


		return result+"";
	}

	//修改评论状态
	@RequestMapping("admin/updateStateForRecommend")
	@ResponseBody
	public String updateState(Recommend recommend){
		System.out.println("执行更新状态"+recommend.getId());
		int result=recommendService.updateState(recommend);

		return result+"";
	}
	//返回一个评论
	@RequestMapping("/admin/returnOneRecommend")
	@ResponseBody
	public Recommend returnRecommend(Recommend recommend) {
		Recommend recommend1;
		System.out.println("返回用户");
		if(recommend.getId()>0){
			recommend1=recommendService.selectRecommendById(recommend.getId());
		}else {
			 recommend1=recommendService.selectRecommendById(3);
		}
		return recommend1;
	}


}
