package com.daofei.controller;


import com.daofei.pojo.Comment;
import com.daofei.pojo.User;
import com.daofei.service.CommentService;
import com.daofei.util.Page;
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
public class CommentController {
	@Autowired
	CommentService commentService;

	//获得一个Comment集合，这里用maven配置项目时要导入json包，不然不会解析，我的pom.xml已经配置了
	@RequestMapping("admin/listComment")
	@ResponseBody
	public Map<String, Object> findAll(Integer page){
		Map<String,Object> tableData =new HashMap<String,Object>();
		PageHelper.offsetPage(page,10);//page是开始页，即第几页
		List<Comment> comments=commentService.list();
		int total = (int) new PageInfo<>(comments).getTotal();

		if(comments.size()>0){
			System.out.println("执行集合comments不为空"+comments.size()+comments.get(0).getContent().toString());
		}else{
			System.out.println("执行集合为空");
			tableData.put("code",0);
			tableData.put("msg","查找分页加数据为0");
			tableData.put("count",0);
			return tableData;

		}
		PageInfo<Comment> pageInfo=new PageInfo<>(comments);
		tableData.put("code",1);
		tableData.put("msg","分页加数据操作成功");
		tableData.put("count",pageInfo.getTotal());
		System.out.println("count"+pageInfo.getTotal());
		tableData.put("data",pageInfo.getList());
		return tableData;
	}
	//删除单个，针对前台
	@RequestMapping("/deleteOneComment")
	@ResponseBody
	public String deleteOneCommentById(Comment comment,HttpSession session){
		User user= (User) session.getAttribute("userLogin");
		System.out.println("执行用户自己删除评论"+comment.getId());
		int result=commentService.deleteCommentById(comment.getId());
		if (user==null){
			result=0;
		}



		return result+"";
	}
	//删除单个，针对后台
	@RequestMapping("admin/deleteOneComment")
	@ResponseBody
	public String deleteCommentById(Comment comment){
		System.out.println("执行删除用户"+comment.getId());
		int result=commentService.deleteCommentById(comment.getId());


		return result+"";
	}

	//更新一个评论的内容
	@RequestMapping("admin/updateOneComment")
	@ResponseBody
	public String updateCommentById(Comment comment){

		if("".equals(comment.getId())){
			return "0";
		}
		System.out.println("执行更新Comment"+comment.getId());
		int result=commentService.updateContent(comment);


		return result+"";
	}
	//删除多个
	@RequestMapping("admin/deleteAllComment")
	@ResponseBody
	public String deleteCommentAll(String id,int num){
		if (id!=null &&!id.equals("")) {
			String[] ids = id.split(",");
			for (String uid : ids) {
				if(uid!=null &&!uid.equals("")){
					commentService.deleteCommentById(Integer.parseInt(uid));
					num--;
				}
			}
		}
		System.out.println("执行删除所有comment，执行成功剩余数"+num);

		if(num==0){
			return "1";
		}else {
			return "0";
		}


	}
	//增加一个评论，此处是后台用的
	@RequestMapping("admin/addOneComment")
	@ResponseBody
	public String addComment(Comment comment){
		System.out.println("执行添加Comment"+comment.getContent());

		int result=0;
		 result=commentService.addComment(comment);


		return result+"";
	}
	//增加一个评论，针对前台用户
	@RequestMapping("/addOneComment")
	@ResponseBody
	public String addOneComment(Comment comment){
		System.out.println("执行添加用户Comment"+comment.getContent());

		//int result=0;
		commentService.addComment(comment);


		return comment.getId()+"";
	}
	//修改评论状态
	@RequestMapping("admin/updateStateForComment")
	@ResponseBody
	public String updateState(Comment comment){
		System.out.println("执行更新状态"+comment.getId());
		int result=commentService.updateState(comment);

		return result+"";
	}
	//返回一个评论
	@RequestMapping("/admin/returnOneComment")
	@ResponseBody
	public Comment returnComment(Comment comment) {
		Comment comment1;
		System.out.println("返回用户");
		if(comment.getId()>0){
			comment1=commentService.selectCommentById(comment.getId());
		}else {
			 comment1=commentService.selectCommentById(3);
		}
		return comment1;
	}
	//返回一个用户
	@RequestMapping("/admin/returnFindComment")
	@ResponseBody
	public Map<String, Object> returnFindComment(String content, String old,String old2,Integer page) {
		System.out.println("返回寻找内容"+content+",时间1"+old+"时间2"+old2);
		Map<String,Object> params = new HashMap<>();
		params.put("content", content);
		params.put("old", old);
		params.put("new", old2);

		Map<String,Object> tableData =new HashMap<String,Object>();
		PageHelper.offsetPage(page,10);//page是开始页，即第几页
		List<Comment> comments=commentService.selectCommentByContentAndTime(params);
		System.out.println("返回寻找到评论数"+comments.size());


		if(comments.size()>0){
			System.out.println("执行查找用户集合user不为空"+comments.size()+comments.get(0).getContent().toString());
		}else{
			tableData.put("code",0);
			tableData.put("msg","查找分页加数据操作失败");
			tableData.put("count",0);
			return tableData;


		}
		PageInfo<Comment> pageInfo=new PageInfo<>(comments);
		tableData.put("code",1);
		tableData.put("msg","查找分页加数据操作成功");
		tableData.put("count",pageInfo.getTotal());
		System.out.println("count"+pageInfo.getTotal());
		tableData.put("data",pageInfo.getList());
		return tableData;

	}

}
