package com.daofei.controller;


import com.daofei.pojo.Admin;
import com.daofei.pojo.Info;
import com.daofei.service.AdminService;
import com.daofei.service.InfoService;
import com.daofei.util.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// 告诉spring mvc这是一个控制器类
@Controller
@RequestMapping("")
public class AdminController {
	@Autowired
	AdminService adminService;
	@Autowired
	InfoService infoService;
	//返回一个信息
	@RequestMapping("/admin/returnOneInfo")
	@ResponseBody
	public Info returnInfo() {
		Info info;
		info=infoService.selectInfo();
		System.out.println("返回信息info");

		return info;
	}
	//修改信息
	@RequestMapping("admin/updateInfo")
	@ResponseBody
	public String updateInfo(Info info){
		//System.out.println("执行更新信息"+info.getEmail());
		int result=infoService.updateInfo(info);

		return result+"";
	}

	//上传头像图片
	@RequestMapping("admin/uploadInfoImg")
	@ResponseBody
	public Map<String, Object> uploadImg(MultipartFile file, HttpServletRequest request, HttpServletResponse response)throws Exception{
		Map<String,Object> result =new HashMap<String,Object>();
		Map<String,Object> result2 =new HashMap<String,Object>();
		//String nodePath="F:\\iwork\\pblog\\";
		String nodePath=request.getSession().getServletContext().getRealPath("/");
		//String filePath = System.getProperty("user.dir")+"/src/main/resources/static/uploadFile/";//上传到这个文件夹
		String filePath =nodePath+"/uploadFile/";//上传到这个文件夹
		File file1 = new File(filePath);
		if (!file1.exists()) {
			file1.mkdirs();
		}
		int imgResult=infoService.updateInfoImg( file.getOriginalFilename().trim());
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
	@RequestMapping("admin/selectImg")
	@ResponseBody
	public Map<String, Object> selectImg(){
		Map<String,Object> tableData =new HashMap<String,Object>();
		Info info=infoService.selectInfo();
		if(info.getImgurl().equals("")){
			tableData.put("code",0);
			System.out.println("url"+info.getImgurl());

			return tableData;
		}
		tableData.put("code",1);
		tableData.put("srcurl",info.getImgurl());
		return tableData;
	}
	@RequestMapping("/adminlogin")
	public String login(Admin admin,HttpSession session) throws Exception{
		Admin adminLogin = adminService.selectById(1);
		//System.out.println(adminLogin+"管理员信息111111111111");
		if(adminLogin!=null){
			String pwd=adminLogin.getPassword();
			if(pwd.equals(admin.getPassword())){
				//登录成功
				session.setAttribute("adminId", adminLogin.getId());
				session.setAttribute("adminUsername", adminLogin.getUsername());
				session.setAttribute("adminLoginTime", adminLogin.getLogin());
				session.setAttribute("adminNickname",adminLogin.getNickname());
				session.setAttribute("adminUrl",adminLogin.getImgurl());
				return "redirect:/admin/index.html";
			}
		}
		return "redirect:/admin/login.html";
	}
	public static String renderString(HttpServletResponse response, String string)
	{
		try
		{
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(string);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	@RequestMapping("/adminLogin")
	@ResponseBody
	public String login(HttpServletRequest request,
						  HttpServletResponse response,Admin admin,HttpSession session) throws Exception {

		//System.out.println("使用"+admin.getUsername());
		Admin adminLogin = adminService.selectById(1);
			if(adminLogin!=null){
				String pwd=adminLogin.getPassword();
				if(pwd.equals(admin.getPassword())){
					session.setAttribute("adminLogin",adminLogin);
					return "1";
				}
			}
		return "-1";
	}
	@RequestMapping("/adminOut")
	public String adminOut(HttpServletRequest request,
						HttpServletResponse response,Admin admin,HttpSession session) throws Exception {
				System.out.println("退出");
				session.removeAttribute("adminLogin");
		return "redirect:http://localhost:8080/pblog/admin/login.html";
	}
	//获得一个admin
	@RequestMapping("listAdmin")
	public ModelAndView listAdmin(Page page){
		ModelAndView mav = new ModelAndView();
		PageHelper.offsetPage(page.getStart(),5);
		Admin admin=adminService.selectById(1);
		page.caculateLast(5);
		// 放入转发参数
		mav.addObject("admin", admin);
		// 放入jsp路径
		mav.setViewName("listAdmin");
		return mav;
	}
}
