package com.daofei.controller;


import com.daofei.pojo.Tag;
import com.daofei.service.TagService;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 告诉spring mvc这是一个控制器类
@Controller
@RequestMapping("")
public class TagController {
	@Autowired
	TagService tagService;


	//查询所有分类,针对显示的
	@RequestMapping("admin/listTag")
	@ResponseBody
	public Map<String, Object> findAll(Integer page){
		Map<String,Object> tableData =new HashMap<String,Object>();
		PageHelper.offsetPage(page,10);//page是开始页，即第几页
		List<Tag> tags=tagService.list();
		int total = (int) new PageInfo<>(tags).getTotal();

		if(tags.size()>0){
			System.out.println("执行分类集合user不为空"+tags.size()+tags.get(0).getName().toString());
		}else{
			System.out.println("执行标签集合为空");
			tableData.put("code",0);
			tableData.put("msg","查找分页加数据为0");
			tableData.put("count",0);
			return tableData;

		}
		PageInfo<Tag> pageInfo=new PageInfo<>(tags);
		tableData.put("code",1);
		tableData.put("msg","标签分页加数据操作成功");
		tableData.put("count",pageInfo.getTotal());
		System.out.println("标签count"+pageInfo.getTotal());
		tableData.put("data",pageInfo.getList());
		return tableData;
	}

	//查询所有分类,针对添加文章的时候
	@RequestMapping("admin/listTagForArticle")
	@ResponseBody
	public Map<String, Object> findAllTag(){
		Map<String,Object> tableData =new HashMap<String,Object>();

		List<Tag> tags=tagService.list();


		if(tags.size()>0){
			System.out.println("执行分类集合user不为空"+tags.size()+tags.get(0).getName().toString());
		}else{
			System.out.println("执行标签集合为空");
			tableData.put("code",0);
			tableData.put("msg","查找分页加数据为0");

			return tableData;
		}

		tableData.put("code",1);
		tableData.put("msg","标签分页加数据操作成功");


		tableData.put("data",tags);
		return tableData;
	}
	//删除单个分类
	@RequestMapping("admin/deleteOneTag")
	@ResponseBody
	public String deleteTagById(Tag tag){
		System.out.println("执行删除分类"+tag.getId());
		int result=tagService.deleteTagById(tag.getId());


		return result+"";
	}
	//更新分类名字
	@RequestMapping("admin/updateOneTag")
	@ResponseBody
	public String updateTagById(Tag tag){

		if("".equals(tag.getId())){
			return "0";
		}
		System.out.println("执行更新分类，id"+tag.getId()+"name"+tag.getName());
		int result=tagService.deleteTagById(tag.getId());


		return result+"";
	}

	//删除多个
	@RequestMapping("admin/deleteAllTag")
	@ResponseBody
	public String deleteTagAll(String id,int num){
		if (id!=null &&!id.equals("")) {
			String[] ids = id.split(",");
			for (String uid : ids) {
				if(uid!=null &&!uid.equals("")){
					//
					tagService.deleteTagById(Integer.parseInt(uid));
					num--;
				}
			}
		}
		System.out.println("执行删除所有标签，执行成功剩余数"+num);
		if(num==0){
			return "1";
		}else {
			return "0";
		}
	}
	//增加一个分类
	@RequestMapping("admin/addOneTag")
	@ResponseBody
	public String addTag(Tag tag){
		System.out.println("执行添加分类"+tag.getName());
		int result=0;
		 result=tagService.addTag(tag.getName());
		return result+"";
	}
	//修改分类状
	@RequestMapping("admin/updateTag")
	@ResponseBody
	public String updateTag(Tag tag){
		System.out.println("执行更新分类"+tag.getName());
		int result=tagService.updateName(tag);
		return result+"";
	}
	//返回一个分类
	@RequestMapping("/admin/returnOneTag")
	@ResponseBody
	public Tag returnTag(Tag tag) {
		Tag tag1;
		System.out.println("返回一个分类");
		if(tag.getId()>0){
			tag1=tagService.selectTagById(tag.getId());
		}else {
			 tag1=new Tag();
			 tag.setId(0);
		}
		return tag1;
	}


}
