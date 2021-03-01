package com.daofei.controller;


import com.daofei.pojo.Admin;
import com.daofei.pojo.User;
import com.daofei.service.AdminService;
import com.daofei.service.CommentService;
import com.daofei.service.UserService;
import com.daofei.util.MD5Util;
import com.daofei.util.Page;
import com.daofei.util.RandomValidateCode;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.soap.SOAPBinding;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

// 告诉spring mvc这是一个控制器类
@Controller
@RequestMapping("")
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	CommentService commentService;

	//返回一个信息
	@RequestMapping("/returnOneUser")
	@ResponseBody
	public User returnInfo(HttpSession session) {
		User user=null;
		User userLogin;
		userLogin= (User) session.getAttribute("userLogin");
		System.out.println(userLogin+""+userLogin.getId());
		user=userService.selectUserById(userLogin.getId());
		return user;
	}

	/**
	 * 获取生成验证码显示到 UI 界面
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value="/checkCode")
	public void checkCode(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//设置相应类型,告诉浏览器输出的内容为图片

		response.setContentType("image/jpeg");
//设置响应头信息，告诉浏览器不要缓存此内容
		response.setHeader("pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expire", 0);
		RandomValidateCode randomValidateCode = new RandomValidateCode();
		try {
			randomValidateCode.getRandcode(request, response);//输出图片方法

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@RequestMapping("/userLogin")
	@ResponseBody
	public String userLogin(HttpServletRequest request,
						HttpServletResponse response,User user,String captcha) throws Exception {
		System.out.println("使用user"+user.getUsername());
		HttpSession session = request.getSession();

		String vcode= (String) session.getAttribute("randomcode_key");
		vcode=vcode.toUpperCase();
		captcha=captcha.toUpperCase();
		System.out.println(vcode);
		User user1=userService.selectUserByUsername(user.getUsername());

		if(user1!=null){
			String pwd=user.getPassword();
			pwd=MD5Util.encode(pwd);
			if(pwd.equals(user1.getPassword())){
				if (vcode.equals(captcha)){
					if(user1.getState()==1){
						user1.setPassword("");
						session.setAttribute("userLogin",user1);
						return "1";
					}else {
						return "3";//账号已被禁用
					}

				}
				return "-2";//验证码错误


			}else {
				//密码错误
				return "0";
			}
		}
		else{
			//没有账号
			return  "-1";
		}

	}
	//上传头像图片
	@RequestMapping("/uploadUserImg")
	@ResponseBody
	public Map<String, Object> uploadUserImg(MultipartFile file, HttpServletRequest request, HttpServletResponse response,HttpSession session)throws Exception{
		Map<String,Object> result =new HashMap<String,Object>();
		Map<String,Object> result2 =new HashMap<String,Object>();
		//String nodePath="F:\\iwork\\pblog\\";
		String nodePath=request.getSession().getServletContext().getRealPath("/");
		//String filePath = System.getProperty("user.dir")+"/src/main/resources/static/uploadFile/";//上传到这个文件夹
		String filePath =nodePath+"/headFile/";//上传到这个文件夹
		File file1 = new File(filePath);
		if (!file1.exists()) {
			file1.mkdirs();
		}
		User userLogin;
		userLogin= (User) session.getAttribute("userLogin");
		userLogin.setImgurl(file.getOriginalFilename().trim());
		int imgResult=userService.updateImg(userLogin);
		String finalFilePath = filePath + file.getOriginalFilename().trim();
		File desFile = new File(finalFilePath);
		if (desFile.exists()) {
			desFile.delete();
		}
		try {
			file.transferTo(desFile);
			result.put("code",0);
			result.put("msg","ok");
			result.put("imgresult",imgResult);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			result.put("code",1);
		}
		System.out.println("执行更新img信息"+finalFilePath);


		return result;

	}
	@RequestMapping("/userOut")

	public String userOut(HttpServletRequest request,
						   HttpServletResponse response,Admin admin,HttpSession session) throws Exception {
		System.out.println("user退出");
		session.removeAttribute("userLogin");
		return "redirect:http://localhost:8080/pblog/login.html";
	}
	//获得一个user集合，这里用maven配置项目时要导入json包，不然不会解析，我的pom.xml已经配置了
	@RequestMapping("admin/listUser")
	@ResponseBody
	public List<User> findAllUser(Page page){
		PageHelper.offsetPage(page.getStart(),10);
		List<User> users=userService.list();
		int total = (int) new PageInfo<>(users).getTotal();
		page.caculateLast(total);
		if(users.size()>0){
			System.out.println("执行用户集合user不为空"+users.size()+users.get(0).getUsername().toString());
		}else{
			System.out.println("执行用户集合为空");

		}
		return users;
	}
	//获得一个user集合，这里用maven配置项目时要导入json包，不然不会解析，我的pom.xml已经配置了
	@RequestMapping("admin/listUserTest")
	@ResponseBody
	public Map<String, Object> findAll(Integer page,Integer limit){
		Map<String,Object> tableData =new HashMap<String,Object>();
		PageHelper.offsetPage(page,10);//page是开始页，即第几页
		List<User> users=userService.list();
		int total = (int) new PageInfo<>(users).getTotal();

		if(users.size()>0){
			System.out.println("执行用户集合user不为空"+users.size()+users.get(0).getUsername().toString());
		}else{
			System.out.println("执行用户集合为空");
			tableData.put("code",0);
			tableData.put("msg","查找分页加数据为0");
			tableData.put("count",0);
			return tableData;

		}
		PageInfo<User> pageInfo=new PageInfo<>(users);
		tableData.put("code",1);
		tableData.put("msg","分页加数据操作成功");
		tableData.put("count",pageInfo.getTotal());
		System.out.println("count"+pageInfo.getTotal());
		tableData.put("data",pageInfo.getList());
		return tableData;
	}
	//删除单个
	@RequestMapping("admin/deleteOneUser")
	@ResponseBody
	public String deleteUserById(User user){
		System.out.println("执行删除用户"+user.getId());
		commentService.deleteCommentByUid(user.getId());
		int result=userService.deleteUserById(user.getId());


		return result+"";
	}
	//更新密码,针对后台的管理
	@RequestMapping("admin/updateOneUser")
	@ResponseBody
	public String updateUserById(User user){

		if("".equals(user.getId())){
			return "0";
		}
		System.out.println("执行更新用户"+user.getId()+"pass"+user.getPassword());
		String pwd=user.getPassword();
		try {
			pwd= MD5Util.encode(pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		user.setPassword(pwd);
		int result=userService.updatePass(user);


		return result+"";
	}
	//更新密码,针对前台
	@RequestMapping("/updatePass")
	@ResponseBody
	public String updateUserPassById(User user){

		if("".equals(user.getId())){
			return "0";
		}
		System.out.println("执行更新用户Pass"+user.getId()+"pass"+user.getPassword());
		String pwd=user.getPassword();
		try {
			pwd= MD5Util.encode(pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		user.setPassword(pwd);
		int result=userService.updatePass(user);


		return result+"";
	}
	//更新昵称,针对前台
	@RequestMapping("/updateUserNickname")
	@ResponseBody
	public String updateUserNicknameById(User user,HttpSession session){
		User userLogin= (User) session.getAttribute("userLogin");
//		if("".equals(user.getId())){
//			return "0";
//		}
		System.out.println("执行更新用户"+user.getId()+"pass"+user.getNickname());
		int result=userService.updateNick(user);

		return result+"";
	}
	//更新邮箱,针对前台
	@RequestMapping("/updateUserEmail")
	@ResponseBody
	public String updateUserEmailById(User user,HttpSession session){
		User userLogin= (User) session.getAttribute("userLogin");

		int result=userService.updateEmail(user);
		return result+"";
	}
	//更新性别,针对前台
	@RequestMapping("/updateUserSex")
	@ResponseBody
	public String updateUserSexById(User user,HttpSession session){
		User userLogin= (User) session.getAttribute("userLogin");
		int result=userService.updateSex(user);
		return result+"";
	}
	//更新手机,针对前台
	@RequestMapping("/updateUserPhone")
	@ResponseBody
	public String updateUserPhoneById(User user,HttpSession session){
		User userLogin= (User) session.getAttribute("userLogin");

		int result=userService.updatePhone(user);
		return result+"";
	}

	//更新一个用户的信息
	@RequestMapping("admin/updateOneUseInfo")
	@ResponseBody
	public String updateUserInfoById(User user){

		if("".equals(user.getId())){
			return "0";
		}
		System.out.println("执行更新用户"+user.getId()+"pass"+user.getPassword());
		String pwd=user.getPassword();
		try {
			pwd= MD5Util.encode(pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		user.setPassword(pwd);
		int result=userService.updateInfo(user);


		return result+"";
	}
	//删除多个
	@RequestMapping("admin/deleteAllUser")
	@ResponseBody
	public String deleteUserAll(String id,int num){
		if (id!=null &&!id.equals("")) {
			String[] ids = id.split(",");
			for (String uid : ids) {
				if(uid!=null &&!uid.equals("")){
					userService.deleteUserById(Integer.parseInt(uid));
					num--;
				}
			}
		}
		System.out.println("执行删除所有用户，执行成功剩余数"+num);

		if(num==0){
			return "1";
		}else {
			return "0";
		}


	}
	//增加一个用户
	@RequestMapping("admin/addOneUser")
	@ResponseBody
	public String addUser(User user){
		System.out.println("执行添加用户"+user.getEmail());

		user.setNickname("请修改昵称");
		user.setImgurl("null");
		user.setPhone("无");
		user.setState(1);
		user.setSex("男");

		String pwd=user.getPassword();
		try {
			pwd= MD5Util.encode(pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		user.setPassword(pwd);
		int result=0;
		 result=userService.addUser(user);


		return result+"";
	}
	//增加一个用户
	@RequestMapping("/userRegister")
	@ResponseBody
	public String userRegister(User user) throws Exception {
		System.out.println("执行注册用户"+user.getEmail());
//		user.setUsername("test");
//		user.setNickname("test");
		user.setImgurl("us.jpg");
//		user.setPhone("123");
		user.setState(1);
//		user.setSex("男");

		int result=0;
		User check=userService.selectUserByUsername(user.getUsername());
		if(check!=null){
			//数据库有了
			return "-1";
		}else if (check==null){
			//数据库没有，成功返回1
			String pwd=user.getPassword();
			pwd= MD5Util.encode(pwd);
			user.setPassword(pwd);
			result=userService.addUser(user);
			return result+"";
		}
		//注册失败
		return "0";
	}
	//修改用户状态
	@RequestMapping("admin/updateState")
	@ResponseBody
	public String updateState(User user){
		System.out.println("执行更新状态"+user.getId());
		int result=userService.updateState(user);

		return result+"";
	}
	//返回一个用户
	@RequestMapping("/admin/returnOneUser")
	@ResponseBody
	public User returnUser(User user) throws Exception{
		User user1;
		System.out.println("返回用户");
		if(user.getId()>0){
			user1=userService.selectUserById(user.getId());
		}else {
			 user1=userService.selectUserById(3);
		}
		return user1;
	}
	//返回一个符合条件的用户
	@RequestMapping("/admin/returnFindUser")
	@ResponseBody
	public Map<String, Object> returnFindUser(String username, String old,String old2,Integer page) throws Exception{
		System.out.println("返回寻找用户"+username+",时间1"+old+"时间2"+old2);
		Map<String,Object> params = new HashMap<>();
		params.put("username", username);
		params.put("old", old);
		params.put("new", old2);

		Map<String,Object> tableData =new HashMap<String,Object>();
		PageHelper.offsetPage(page,10);//page是开始页，即第几页
		List<User> users=userService.selectUserByUsernameAndTime(params);
		System.out.println("返回寻找到用户数"+users.size());


		if(users.size()>0){
			System.out.println("执行查找用户集合user不为空"+users.size()+users.get(0).getUsername().toString());
		}else{
			tableData.put("code",0);
			tableData.put("msg","查找分页加数据操作失败");
			tableData.put("count",0);
			return tableData;


		}
		PageInfo<User> pageInfo=new PageInfo<>(users);
		tableData.put("code",1);
		tableData.put("msg","查找分页加数据操作成功");
		tableData.put("count",pageInfo.getTotal());
		System.out.println("count"+pageInfo.getTotal());
		tableData.put("data",pageInfo.getList());
		return tableData;

	}

}
