package com.daofei.controller;


import com.daofei.pojo.*;
import com.daofei.service.*;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 告诉spring mvc这是一个控制器类
@Controller
@RequestMapping("")
public class ArticleController {
	@Autowired
	CollectService collectService;
	@Autowired
	ArticleService articleService;
	@Autowired
	TypeService typeService;
	@Autowired
	TagService tagService;
	@Autowired
	CommentService commentService;
	////修改文章喜欢
	@RequestMapping("/addLike")
	@ResponseBody
	public String addLike(Article article,int xh){
		//System.out.println("执行更新文章like状态"+article.getId()+article.getXh()+xh);
		//article.setLikes(likes);
		int result=articleService.updateLike(article);
		return result+"";
	}
	//增加一个文章
	@RequestMapping("admin/addOneArticle")
	@ResponseBody
	public String addArticle(Article article){
		//System.out.println("执行添加分类"+article.getContent());
		int result=0;
		result=articleService.addArticle(article);
		return result+"";
	}
	//更新一个文章的信息
	@RequestMapping("admin/updateOneArticleInfo")
	@ResponseBody
	public String updateArticleInfoById(Article article){

		if("".equals(article.getId())){
			return "0";
		}
		//System.out.println("执行更新文章");
		int result=articleService.updateArticle(article);


		return result+"";
	}
	//上传图片
	@RequestMapping("admin/uploadImg")
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
		String finalFilePath = filePath + file.getOriginalFilename().trim();
		File desFile = new File(finalFilePath);
		if (desFile.exists()) {
			desFile.delete();
		}
		try {
			file.transferTo(desFile);
			result.put("code",0);
			result.put("msg","ok");
			result.put("src",finalFilePath);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			result.put("code",1);
		}
		System.out.println(result.toString());
		return result;

	}
	//上传图片，针对富文本, {"code": 0, "msg": "success", "data": "/demo/demo.jpg"}，
	@RequestMapping("admin/uploadImgFwb")
	@ResponseBody
	public Map<String, Object> uploadImgFwb(MultipartFile edit, HttpServletRequest request, HttpServletResponse response)throws Exception{
		Map<String,Object> result =new HashMap<String,Object>();
		Map<String,Object> result2 =new HashMap<String,Object>();
		//String nodePath="F:\\iwork\\pblog\\";
		String nodePath=request.getSession().getServletContext().getRealPath("/");
		//String filePath = System.getProperty("user.dir")+"/src/main/resources/static/uploadFile/";//上传到这个文件夹
		String filePath =nodePath+"uploadFile/";//上传到这个文件夹
		String back="../uploadFile/"+edit.getOriginalFilename().trim();
		File file1 = new File(filePath);
		if (!file1.exists()) {
			file1.mkdirs();
		}
		String finalFilePath = filePath + edit.getOriginalFilename().trim();
		File desFile = new File(finalFilePath);
		if (desFile.exists()) {
			desFile.delete();
		}
		try {
			edit.transferTo(desFile);
			result.put("code",0);
			result.put("msg","ok");
			result.put("data",back);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			result.put("code",1);
			result.put("msg","bad");
			result.put("data",finalFilePath);
		}
		System.out.println(result.toString());
		return result;

	}
	//返回符合条件的文章
	@RequestMapping("/admin/returnFindArticle")
	@ResponseBody
	public Map<String, Object> returnFindArticle(String title, String old,String old2,Integer page,HttpSession session) throws Exception{
		//System.out.println("返回寻找的标题文章"+title+",时间1"+old+"时间2"+old2);
		Map<String,Object> params = new HashMap<>();
		params.put("title", title);
		params.put("old", old);
		params.put("new", old2);
		User user= (User) session.getAttribute("userLogin");
		Map<String,Object> tableData =new HashMap<String,Object>();
		PageHelper.offsetPage(page,10);//page是开始页，即第几页
		List<Article> article=articleService.selectArticleByTitleAndTime(params);
		//System.out.println("返回寻找到文章数"+article.size());
		if(article.size()>0){
			//System.out.println("执行查找文章集合user不为空"+article.size()+article.get(0).getId());
			PageInfo<Article> pageInfo=new PageInfo<>(article);
			tableData.put("code",1);
			tableData.put("msg","文章分页加数据操作成功");
			tableData.put("count",pageInfo.getTotal());
			tableData.put("types",typeService.list());
			tableData.put("tag",tagService.list());
			tableData.put("user",user);
			System.out.println("标签count"+pageInfo.getTotal());
			tableData.put("data",pageInfo.getList());
			return tableData;
		}else{
			tableData.put("code",0);
			tableData.put("msg","查找分页加数据操作失败");
			tableData.put("count",0);
			tableData.put("user",user);
			tableData.put("types",typeService.list());
			tableData.put("tag",tagService.list());
			return tableData;
		}

	}
	//返回符合条件的文章,针对前台
	@RequestMapping("/returnFindArticle")
	@ResponseBody
	public Map<String, Object> returnFindArticleByTitle(String title,Integer page,HttpSession session) throws Exception{

		Map<String,Object> params = new HashMap<>();
		params.put("title", title);

		User user= (User) session.getAttribute("userLogin");
		Map<String,Object> tableData =new HashMap<String,Object>();
		PageHelper.offsetPage(page,10);//page是开始页，即第几页
		List<Article> article=articleService.selectArticleByTitle(params);
		//System.out.println("返回寻找到文章数"+article.size());
		if(article.size()>0){
			//System.out.println("执行查找文章集合user不为空"+article.size()+article.get(0).getId());
			PageInfo<Article> pageInfo=new PageInfo<>(article);
			tableData.put("code",1);
			tableData.put("msg","文章分页加数据操作成功");
			tableData.put("count",pageInfo.getTotal());
			tableData.put("types",typeService.list());
			tableData.put("tag",tagService.list());
			tableData.put("user",user);
			System.out.println("标签count"+pageInfo.getTotal());
			tableData.put("data",pageInfo.getList());
			return tableData;
		}else{
			tableData.put("code",0);
			tableData.put("msg","查找分页加数据操作失败");
			tableData.put("count",0);
			tableData.put("user",user);
			tableData.put("types",typeService.list());
			tableData.put("tag",tagService.list());
			return tableData;
		}

	}
	//查询所有文章
	@RequestMapping("admin/listArticle")
	@ResponseBody
	public Map<String, Object> findAllArticle(Integer page,HttpSession session){
		Map<String,Object> tableData =new HashMap<String,Object>();
		PageHelper.offsetPage(page,10);//page是开始页，即第几页
		User user= (User) session.getAttribute("userLogin");
		List<Article> articles=articleService.list();


		if(articles.size()>0){
			System.out.println("执行文章集合user不为空"+articles.size()+articles.get(0).getId());
			PageInfo<Article> pageInfo=new PageInfo<>(articles);
			tableData.put("code",1);
			tableData.put("msg","文章分页加数据操作成功");
			tableData.put("count",pageInfo.getTotal());
			tableData.put("types",typeService.list());
			tableData.put("tag",tagService.list());
			tableData.put("user",user);
			System.out.println("标签count"+pageInfo.getTotal());
			tableData.put("data",pageInfo.getList());
			return tableData;
		}else{
			System.out.println("执行文章集合为空");
			tableData.put("code",0);
			tableData.put("msg","查找分页加数据为0");
			tableData.put("count",0);
			if (user==null){
			//	user.setId(0);
			}
			tableData.put("user",user);
			tableData.put("types",typeService.list());
			tableData.put("tag",tagService.list());
			return tableData;
		}

	}
	//查询所有文章
	@RequestMapping("/listArticle")
	@ResponseBody
	public Map<String, Object> findAllArticleForIndex (Integer page,HttpSession session){
		Map<String,Object> tableData =new HashMap<String,Object>();
		PageHelper.offsetPage(page,10);//page是开始页，即第几页
		User user= (User) session.getAttribute("userLogin");
		List<Article> articles=articleService.listByStatus();


		if(articles.size()>0){
			//System.out.println("执行文章集合user不为空"+articles.size()+articles.get(0).getId());
			PageInfo<Article> pageInfo=new PageInfo<>(articles);
			tableData.put("code",1);
			tableData.put("msg","文章分页加数据操作成功");
			tableData.put("count",pageInfo.getTotal());
			tableData.put("types",typeService.list());
			tableData.put("tag",tagService.list());
			tableData.put("user",user);
			System.out.println("标签count"+pageInfo.getTotal());
			tableData.put("data",pageInfo.getList());
			return tableData;
		}else{
			System.out.println("执行文章集合为空");
			tableData.put("code",0);
			tableData.put("msg","查找分页加数据为0");
			tableData.put("count",0);
			if (user==null){
				//	user.setId(0);
			}
			tableData.put("user",user);
			tableData.put("types",typeService.list());
			tableData.put("tag",tagService.list());
			return tableData;
		}

	}
	//查询点击量前五文章
	@RequestMapping("/hitsArticle")
	@ResponseBody
	public Map<String, Object> findAllHitsArticle(HttpSession session){
		Map<String,Object> tableData =new HashMap<String,Object>();
		User user= (User) session.getAttribute("userLogin");
		List<Article> articles=articleService.selectArticleByHits();
		if(articles.size()>0){
			//System.out.println("执行文章集合user不为空"+articles.size()+articles.get(0).getId());
			tableData.put("code",1);
			tableData.put("msg","文章分页加数据操作成功");
			tableData.put("user",user);
			tableData.put("data",articles);
			return tableData;
		}else{
			System.out.println("执行文章集合为空");
			tableData.put("code",0);
			tableData.put("msg","查找分页加数据为0");
			tableData.put("count",0);
			if (user==null){
				//	user.setId(0);
			}
			return tableData;
		}


	}
	//查询点击量前五文章,针对统计
	@RequestMapping("admin/hitsArticleForCensus")
	@ResponseBody
	public Map<String, Object> findAllHitsArticleForCensus(HttpSession session){
		Map<String,Object> tableData =new HashMap<String,Object>();
		User user= (User) session.getAttribute("userLogin");
		List<Article> articles=articleService.selectArticleForCensus();
		if(articles.size()>0){
			//System.out.println("执行文章集合user不为空"+articles.size()+articles.get(0).getId());
			tableData.put("code",1);
			tableData.put("msg","文章分页加数据操作成功");
			tableData.put("user",user);
			tableData.put("data",articles);
			return tableData;
		}else{
			//System.out.println("执行文章集合为空");
			tableData.put("code",0);
			tableData.put("msg","查找分页加数据为0");
			tableData.put("count",0);
			if (user==null){
				//	user.setId(0);
			}
			return tableData;
		}


	}
	//查询单个分类文章
	@RequestMapping("/TypeArticle")
	@ResponseBody
	public Map<String, Object> findTypeArticle(Integer page,Integer lx,HttpSession session){
		Map<String,Object> tableData =new HashMap<String,Object>();
		PageHelper.offsetPage(page,10);//page是开始页，即第几页
		User user= (User) session.getAttribute("userLogin");
		List<Article> articles=articleService.selectArticleByType(lx);
		if(articles.size()>0){
			System.out.println("执行文章集合type不为空"+articles.size()+articles.get(0).getId());
		}else{
			//System.out.println("执行文章集合为空");
			tableData.put("code",0);
			tableData.put("msg","查找分页加数据为0");
			tableData.put("count",0);
			if (user==null){
				//user.setId(0);
			}
			tableData.put("user",user);
			tableData.put("types",typeService.list());
			tableData.put("tag",tagService.list());
			return tableData;
		}
		PageInfo<Article> pageInfo=new PageInfo<>(articles);
		tableData.put("code",1);
		tableData.put("msg","文章分页加数据操作成功");
		tableData.put("count",pageInfo.getTotal());
		tableData.put("types",typeService.list());
		tableData.put("tag",tagService.list());
		tableData.put("user",user);
		System.out.println("标签count"+pageInfo.getTotal());
		tableData.put("data",pageInfo.getList());
		return tableData;
	}
	//查询所有文章
	@RequestMapping("/TagArticle")
	@ResponseBody
	public Map<String, Object> findTagrticle(Integer page,Integer tagid,HttpSession session){
		Map<String,Object> tableData =new HashMap<String,Object>();
		PageHelper.offsetPage(page,10);//page是开始页，即第几页
		User user= (User) session.getAttribute("userLogin");
		List<Article> articles=articleService.selectArticleByTag(tagid);
		if(articles.size()>0){
			System.out.println("执行文章集合type不为空"+articles.size()+articles.get(0).getId());
		}else{
			System.out.println("执行文章集合为空");
			tableData.put("code",0);
			tableData.put("msg","查找分页加数据为0");
			tableData.put("count",0);
			if (user==null){
				//user.setId(0);
			}
			tableData.put("user",user);
			tableData.put("types",typeService.list());
			tableData.put("tag",tagService.list());
			return tableData;
		}
		PageInfo<Article> pageInfo=new PageInfo<>(articles);
		tableData.put("code",1);
		tableData.put("msg","文章分页加数据操作成功");
		tableData.put("count",pageInfo.getTotal());
		tableData.put("types",typeService.list());
		tableData.put("tag",tagService.list());
		tableData.put("user",user);
		System.out.println("标签count"+pageInfo.getTotal());
		tableData.put("data",pageInfo.getList());
		return tableData;
	}
	//查询单个文章,用户页面专用
	@RequestMapping("/oneArticle")
	@ResponseBody
	public Map<String, Object> findOneArticle(int id,HttpSession session){
		Map<String,Object> tableData =new HashMap<String,Object>();

		User user= (User) session.getAttribute("userLogin");
		Article article=articleService.selectArticleById(id);
		Collect collect=collectService.selectCollectById(id);

		//更新浏览量
		article.setHits(article.getHits()+1);

		articleService.updateHits(article);
		List<Comment> comments=commentService.selectCommentByAid(id);
		if(article.getId()>0){
			System.out.println("执行文章集合不为空");

		}else{
			System.out.println("执行文章为空");
			tableData.put("code",0);
			tableData.put("msg","查找文章是失败");
			tableData.put("count",0);
			if (user==null){
				//user.setId(0);
			}
			tableData.put("user",user);
			return tableData;
		}

		tableData.put("code",1);
		tableData.put("msg","文章操作成功");
		tableData.put("types",typeService.list());
		tableData.put("tag",tagService.list());
		tableData.put("comment",comments);
		tableData.put("user",user);
		tableData.put("coll",collect);
		tableData.put("asc",articleService.selectArticleByAsc(id));
		tableData.put("desc",articleService.selectArticleByDesc(id));
		tableData.put("data",article);
		return tableData;
	}
	//查询单个文章
	@RequestMapping("/oneArticle2")
	@ResponseBody
	public Map<String, Object> findOneArticle2(int id,HttpSession session){
		Map<String,Object> tableData =new HashMap<String,Object>();

		User user= (User) session.getAttribute("userLogin");
		Article article=articleService.OneArticleById(id);
		//更新浏览量
		article.setHits(article.getHits()+1);

		articleService.updateHits(article);
		List<Comment> comments=commentService.selectCommentByAid(id);
		if(article.getId()>0){
			System.out.println("执行文章集合不为空");

		}else{
			System.out.println("执行文章为空");
			tableData.put("code",0);
			tableData.put("msg","查找文章是失败");
			tableData.put("count",0);
			if (user==null){
				//user.setId(0);
			}
			tableData.put("user",user);
			return tableData;
		}

		tableData.put("code",1);
		tableData.put("msg","文章操作成功");
		tableData.put("types",typeService.list());
		tableData.put("tag",tagService.list());
		//tableData.put("comment",comments);
		tableData.put("user",user);
		tableData.put("data",article);
		return tableData;
	}
	//删除单个文章
	@RequestMapping("admin/deleteOneArticle")
	@ResponseBody
	public String deleteArticleById(Article article){
		System.out.println("执行删除分类"+article.getId());
		commentService.deleteCommentByAid(article.getId());
		int result=articleService.deleteArticleById(article.getId());

		return result+"";
	}
	//修改用户状态
	@RequestMapping("admin/updateArticleState")
	@ResponseBody
	public String updateArticleState(Article article){
		System.out.println("执行更新文章状态"+article.getId());
		int result=articleService.updateStatus(article);
		return result+"";
	}

	//删除多个文章
	@RequestMapping("admin/deleteAllArticle")
	@ResponseBody
	public String deleteArticleAll(String id,int num){
		if (id!=null &&!id.equals("")) {
			String[] ids = id.split(",");
			for (String uid : ids) {
				if(uid!=null &&!uid.equals("")){
					//
					articleService.deleteArticleById(Integer.parseInt(uid));
					num--;
				}
			}
		}
		System.out.println("执行删除所有文章，执行成功剩余数"+num);
		if(num==0){
			return "1";
		}else {
			return "0";
		}
	}
	//返回一个文章
	@RequestMapping("/admin/returnOneArticle")
	@ResponseBody
	public Article returnArticle(Article article) {
		Article article1;
		System.out.println("返回一个分类");
		if(article.getId()>0){
			article1=articleService.selectArticleById(article.getId());
		}else {
			article1=new Article();
			article1.setId(0);
		}
		return article1;
	}

}
