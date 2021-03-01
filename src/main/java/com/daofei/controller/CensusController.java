package com.daofei.controller;

import com.daofei.pojo.Census;
import com.daofei.pojo.Collect;
import com.daofei.pojo.User;
import com.daofei.service.CensusService;
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

@Controller
@RequestMapping("")
public class CensusController {
    @Autowired
    CensusService censusService;
    @RequestMapping("admin/getCensus")
    @ResponseBody
    public Map<String, Object> findCensus( HttpSession session){
        Map<String,Object> tableData =new HashMap<String,Object>();
        Census census=censusService.getCensus();
        if(census!=null){
            tableData.put("code",1);
            tableData.put("msg","统计数据返回成功");
            tableData.put("data",census);
        }else {
            tableData.put("code",0);
            tableData.put("msg","统计数据返回失败");
        }
        return tableData;
    }

}
