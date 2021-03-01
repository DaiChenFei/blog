package com.daofei.controller;


import com.daofei.pojo.Comment;
import com.daofei.pojo.Message;
import com.daofei.pojo.User;
import com.daofei.service.CommentService;
import com.daofei.service.MessageService;
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
public class MessageController {
	@Autowired
	MessageService messageService;

	//获得一个Message集合，这里用maven配置项目时要导入json包，不然不会解析，我的pom.xml已经配置了
	@RequestMapping("/listMessage")
	@ResponseBody
	public Map<String, Object> findAll(Integer page,HttpSession session){
		Map<String,Object> tableData =new HashMap<String,Object>();
		PageHelper.offsetPage(page,10);//page是开始页，即第几页
		List<Message> messages=messageService.list();

		if(messages.size()>0){
			System.out.println("执行集合message不为空");
		}else{
			System.out.println("执行集合为空");
			tableData.put("code",0);
			tableData.put("msg","查找分页加数据为0");
			User user= (User) session.getAttribute("userLogin");
			tableData.put("user",user);
			tableData.put("count",0);
			return tableData;

		}
		PageInfo<Message> pageInfo=new PageInfo<>(messages);
		tableData.put("code",1);
		tableData.put("msg","分页加数据操作成功");
		tableData.put("count",pageInfo.getTotal());
		System.out.println("count"+pageInfo.getTotal());
		User user= (User) session.getAttribute("userLogin");
		tableData.put("data",pageInfo.getList());
		tableData.put("user",user);
		return tableData;
	}
	//删除单个，针对前台
	@RequestMapping("/deleteOneMessage")
	@ResponseBody
	public String deleteOneMessageById(Message message,HttpSession session){
		User user= (User) session.getAttribute("userLogin");
		if (user==null){
			return "0";
		}
		System.out.println("执行用户自己删除留言"+message.getId());
		int result=messageService.deleteMessageById(message.getId());

		return result+"";
	}
	//删除单个，针对后台
	@RequestMapping("admin/deleteOneMessage")
	@ResponseBody
	public String deleteMessageById(Message message){
		System.out.println("执行删除留言");
		int result=messageService.deleteMessageById(message.getId());


		return result+"";
	}

	//更新一个评论的内容
	@RequestMapping("admin/updateOneMessage")
	@ResponseBody
	public String updateMessageById(Message message){

		if("".equals(message.getId())){
			return "0";
		}
		System.out.println("执行更新Message"+message.getId());
		int result=messageService.updateContent(message);


		return result+"";
	}
	//删除多个
	@RequestMapping("admin/deleteAllMessage")
	@ResponseBody
	public String deleteMessageAll(String id,int num){
		if (id!=null &&!id.equals("")) {
			String[] ids = id.split(",");
			for (String uid : ids) {
				if(uid!=null &&!uid.equals("")){

					messageService.deleteMessageById(Integer.parseInt(uid));
					num--;
				}
			}
		}
		System.out.println("执行删除所有Message，执行成功剩余数"+num);

		if(num==0){
			return "1";
		}else {
			return "0";
		}


	}
	//增加一个评论，此处是后台用的
	@RequestMapping("admin/addOneMessage")
	@ResponseBody
	public String addMessage(Message message){
		System.out.println("执行添加Message"+message.getContent());

		int result=0;
		 result=messageService.addMessage(message);


		return result+"";
	}
	//增加一个评论，针对前台用户
	@RequestMapping("/addOneMessage")
	@ResponseBody
	public String addOneMessage(Message message){
		System.out.println("执行添加用户Message"+message.getContent());

		//int result=0;
		messageService.addMessage(message);

		System.out.println("执行添加用户result"+message.getId());

		return message.getId()+"";
	}

	//返回一个评论
	@RequestMapping("/admin/returnOneMessage")
	@ResponseBody
	public Message returnMessage(Message message) {
		Message message1;
		System.out.println("返回用户");
		if(message.getId()>0){
			message1=messageService.selectMessageById(message.getId());
		}else {
			 message1=messageService.selectMessageById(3);
		}
		return message1;
	}
	//返回一个用户
	@RequestMapping("/admin/returnFindMessage")
	@ResponseBody
	public Map<String, Object> returnFindMessage(String content, String old,String old2,Integer page) {
		System.out.println("返回寻找内容"+content+",时间1"+old+"时间2"+old2);
		Map<String,Object> params = new HashMap<>();
		params.put("content", content);
		params.put("old", old);
		params.put("new", old2);

		Map<String,Object> tableData =new HashMap<String,Object>();
		PageHelper.offsetPage(page,10);//page是开始页，即第几页
		List<Message> messages=messageService.selectMessageByContentAndTime(params);
		System.out.println("返回寻找到评论数"+messages.size());


		if(messages.size()>0){
			System.out.println("执行查找用户集合Message不为空"+messages.size()+messages.get(0).getContent().toString());
		}else{
			tableData.put("code",0);
			tableData.put("msg","查找分页加数据操作失败");
			tableData.put("count",0);
			return tableData;


		}
		PageInfo<Message> pageInfo=new PageInfo<>(messages);
		tableData.put("code",1);
		tableData.put("msg","查找分页加数据操作成功Message");
		tableData.put("count",pageInfo.getTotal());
		System.out.println("count"+pageInfo.getTotal());
		tableData.put("data",pageInfo.getList());
		return tableData;

	}

}
