package com.daofei.controller;


import com.daofei.pojo.Type;
import com.daofei.pojo.User;
import com.daofei.service.TypeService;
import com.daofei.service.UserService;
import com.daofei.util.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 告诉spring mvc这是一个控制器类
@Controller
@RequestMapping("")
public class TypeController {
	@Autowired
	TypeService typeService;


	//查询所有分类
	@RequestMapping("admin/listType")
	@ResponseBody
	public Map<String, Object> findAll(Integer page){
		Map<String,Object> tableData =new HashMap<String,Object>();
		PageHelper.offsetPage(page,10);//page是开始页，即第几页
		List<Type> types=typeService.list();
		int total = (int) new PageInfo<>(types).getTotal();

		if(types.size()>0){
			System.out.println("执行分类集合user不为空"+types.size()+types.get(0).getName().toString());
		}else{
			System.out.println("执行用户集合为空");
			tableData.put("code",0);
			tableData.put("msg","查找分页加数据为0");
			tableData.put("count",0);
			return tableData;

		}
		PageInfo<Type> pageInfo=new PageInfo<>(types);
		tableData.put("code",1);
		tableData.put("msg","分类分页加数据操作成功");
		tableData.put("count",pageInfo.getTotal());
		System.out.println("分类count"+pageInfo.getTotal());
		tableData.put("data",pageInfo.getList());
		return tableData;
	}
	//查询所有分类,针对添加文章
	@RequestMapping("admin/listTypeForArticle")
	@ResponseBody
	public Map<String, Object> findAllForArticle(){
		Map<String,Object> tableData =new HashMap<String,Object>();

		List<Type> types=typeService.list();


		if(types.size()>0){
			System.out.println("执行分类集合user不为空"+types.size()+types.get(0).getName().toString());
		}else{
			System.out.println("执行用户集合为空");
			tableData.put("code",0);
			tableData.put("msg","查找分页加数据为0");
			tableData.put("count",0);
			return tableData;

		}

		tableData.put("code",1);
		tableData.put("msg","分类分页加数据操作成功");

		tableData.put("data",types);
		return tableData;
	}
	//删除单个分类
	@RequestMapping("admin/deleteOneType")
	@ResponseBody
	public String deleteTypeById(Type type){
		System.out.println("执行删除分类"+type.getId());
		int result=typeService.deleteTypeById(type.getId());


		return result+"";
	}
	//更新分类名字
	@RequestMapping("admin/updateOneType")
	@ResponseBody
	public String updateTypeById(Type type){

		if("".equals(type.getId())){
			return "0";
		}
		System.out.println("执行更新分类，id"+type.getId()+"name"+type.getName());
		int result=typeService.deleteTypeById(type.getId());


		return result+"";
	}

	//删除多个
	@RequestMapping("admin/deleteAllType")
	@ResponseBody
	public String deleteTypeAll(String id,int num){
		if (id!=null &&!id.equals("")) {
			String[] ids = id.split(",");
			for (String uid : ids) {
				if(uid!=null &&!uid.equals("")){
					//
					typeService.deleteTypeById(Integer.parseInt(uid));
					num--;
				}
			}
		}
		System.out.println("执行删除所有分类，执行成功剩余数"+num);
		if(num==0){
			return "1";
		}else {
			return "0";
		}
	}
	//增加一个分类
	@RequestMapping("admin/addOneType")
	@ResponseBody
	public String addType(Type type){
		System.out.println("执行添加分类"+type.getName());
		int result=0;
		 result=typeService.addType(type.getName());
		return result+"";
	}
	//修改分类状
	@RequestMapping("admin/updateType")
	@ResponseBody
	public String updateType(Type type){
		System.out.println("执行更新分类"+type.getName());
		int result=typeService.updateName(type);
		return result+"";
	}
	//返回一个分类
	@RequestMapping("/admin/returnOneType")
	@ResponseBody
	public Type returnType(Type type) {
		Type type1;
		System.out.println("返回一个分类");
		if(type.getId()>0){
			type1=typeService.selectTypeById(type.getId());
		}else {
			 type1=new Type();
			 type.setId(0);
		}
		return type1;
	}


}
