//加载完成
$(function(){

    getPeopleList(1,0,10);//获取人才列表

});

//自己封装获取数据方法
function getPeopleList(crr,page,lmt){
    //获取人才列表
    $.ajax({
        url:'http://localhost:8080/pblog/admin/listArticle',
        type:'post',
        data:{
            "page":page
        },
        dataType:'json',
        success:function(data){
            console.log("get"+data.code+data.msg);
            console.log(data.length);
            if(data.code=="1"){
                var conter=data.count;
                var sj=data.data;
                var currt=crr;
                var types=data.types;
                var tags=data.tag;
                console.log(types);
                var tArr=new Array();

                for(var i=0;i<sj.length;i++){    //遍历data数组
                    var ls = sj[i];
                   // var ts=types
                    console.log("接收文章数组");
                    var cjtime=getTime(ls.createtime);
                    var uptime=getTime(ls.updatetime);
                    var state1='<span class="layui-btn layui-btn-normal layui-btn-mini">已上线</span>';
                    var state2='<span class="layui-btn layui-btn-normal layui-btn-mini layui-btn-disabled">草稿</span>';
                    var state='';
                    var title;
                    var tname;
                    var tagsName='';
                    tArr=ls.tagid.split(',');
                    var newArray = tArr.filter(function(v){return v!==''});

                    console.log("new"+newArray+"old"+tArr);
                    for(var j=0;j<types.length;j++){
                        var ty=types[j];
                        //console.log(ty);
                        if (ls.tid==ty.id){
                            //console.log("TID"+ty.id);
                            tname=ty.name;
                        }
                    }
                    if(ls.tagid==""){
                        tagsName+="无";
                    }
                    for(var m=0;m<tArr.length;m++){
                        for(var j=0;j<tags.length;j++){
                            if (tags[j].id==tArr[m]){
                              //  console.log("aR"+tArr[m]);
                                if(m==tArr.length-1)
                                    tagsName+=tags[j].name+" ";
                                else
                                    tagsName+=tags[j].name+"、";
                            }
                        }
                    }

                    if(ls.status==1){
                        state=state1;
                        title='草稿';
                    }else {
                        state=state2;
                        title='上线';
                    }
                    //$("#usershuju").empty();
                    $("#usershuju").append(`
                <tr>
                                    <td>
                                      <input type="checkbox" name="id" value="${ls.id}"   lay-skin="primary"> 
                                    </td>
                                    <td id="a_id">${ls.id}</td>
                                    
                                    <td id="a_tid">${tname}</td>
                                    <td id="a_tagid">${tagsName}</td>
                                    <td id="a_titele">${ls.title}</td>
                                    <td id="a_abs">${ls.abscontent}</td>
                                    <td id="a_url">${ls.imgurl}</td>
                                    <td id="a_cj">${cjtime}</td>
                                    <td id="a_up">${uptime}</td>
                                    <td id="a_dz">${ls.hits}</td>
                                    <td id="a_xh">${ls.xh}</td>
                                    <td class="td-status">
                                      ${state}</td>
                                    <td class="td-manage">
                                      <a onclick="member_stop(this,${ls.id})" href="javascript:;"  title="${title}">
                                        <i class="layui-icon">&#xe601;</i>
                                      </a>
                                     
                                      <a title="编辑"  onclick="layer.open({
                resize: false,
                title: '编辑',
                shadeClose: true,
                area:['1200px','900px'],
                type: 2,
                content: 'article-edit2.html?aid=${ls.id}',
                success: function (layero, index) {
                   
                    
                } })"  href="javascript:;">
                                        <i class="layui-icon">&#xe642;</i>
                                      </a>
                                      
                                      <a title="删除" onclick="member_del(this,${ls.id})" href="javascript:;">
                                        <i class="layui-icon">&#xe640;</i>
                                      </a>
                                    </td>
                                  </tr>    
         `);

                }

                getPageList(currt,conter,10);
            }else if(data.code=="0"){
                layer.msg("没有任何文章");
            }else {
                layer.msg("网络故障");
            }
        },
        error:function(){
            layer.msg("网络故障");
        }
    })
}
function getTime(t) {

    var time = new Date(t);
    var y = time.getFullYear();
    var m = time.getMonth()+1;
    var d = time.getDate();
    var h = time.getHours();
    var mm = time.getMinutes();
    var s = time.getSeconds();
    function add0(m){return m<10?'0'+m:m }
    var da=y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
    return da;
}

//自己封装分页方法
function getPageList(currt,count,limit){
    layui.use(['laypage','laydate','form'], function(){
        var laydate = layui.laydate;
        var  laypage = layui.laypage;
        var  form = layui.form;
        //分页
        laypage.render({
            elem: 'demo0'
            ,count: count //数据总数
            ,curr:currt
            ,layout: ['count', 'prev', 'page', 'next',  'refresh', 'skip']
            ,jump: function(obj, first){
                //obj包含了当前分页的所有参数，比如：
                console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
                console.log(obj.limit); //得到每页显示的条数

                //首次不执行
                if(!first){
                    //do something
                    $("#usershuju").empty();
                    getPeopleList(obj.curr,(obj.curr-1)*10,10);

                }
            }
        });
        form.render('checkbox');

        // 监听全选
        form.on('checkbox(checkall)', function(data){

            if(data.elem.checked){
                $('tbody input').prop('checked',true);
            }else{
                $('tbody input').prop('checked',false);
            }
            form.render('checkbox');
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#start' //指定元素
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#end' //指定元素
        });


    });

    //分页方法

}

//自己封装获取数据方法
function getArticleList(crr,page,search,kaishi,zuihou){
    //获取列表
    $.ajax({
        url:'http://localhost:8080/pblog/admin/returnFindArticle',
        type:'post',
        data:{
            "page":page,
            "title":search,
            "old":kaishi,
            "old2":zuihou,
        },
        dataType:'json',
        success:function(data){console.log("get"+data.code+data.msg);
            console.log(data.length);
            if(data.code=="1"){
                var conter=data.count;
                var sj=data.data;
                var currt=crr;
                var types=data.types;
                var tags=data.tag;
                console.log(types);
                var tArr=new Array();

                for(var i=0;i<sj.length;i++){    //遍历data数组
                    var ls = sj[i];
                    // var ts=types
                    console.log("接收文章数组");
                    var cjtime=getTime(ls.createtime);
                    var uptime=getTime(ls.updatetime);
                    var state1='<span class="layui-btn layui-btn-normal layui-btn-mini">已上线</span>';
                    var state2='<span class="layui-btn layui-btn-normal layui-btn-mini layui-btn-disabled">草稿</span>';
                    var state='';
                    var title;
                    var tname;
                    var tagsName='';
                    tArr=ls.tagid.split(',');
                    var newArray = tArr.filter(function(v){return v!==''});

                    console.log("new"+newArray+"old"+tArr);
                    for(var j=0;j<types.length;j++){
                        var ty=types[j];
                        //console.log(ty);
                        if (ls.tid==ty.id){
                            //console.log("TID"+ty.id);
                            tname=ty.name;
                        }
                    }
                    for(var m=0;m<tArr.length;m++){
                        for(var j=0;j<tags.length;j++){
                            if (tags[j].id==tArr[m]){
                                //  console.log("aR"+tArr[m]);
                                if(m==tArr.length-1)
                                    tagsName+=tags[j].name+" ";
                                else
                                    tagsName+=tags[j].name+"、";
                            }
                        }
                    }

                    if(ls.status==1){
                        state=state1;
                        title='停用';
                    }else {
                        state=state2;
                        title='启用';
                    }
                    //$("#usershuju").empty();
                    $("#usershuju").append(`
                <tr>
                                    <td>
                                      <input type="checkbox" name="id" value="${ls.id}"   lay-skin="primary"> 
                                    </td>
                                    <td id="a_id">${ls.id}</td>
                                    
                                    <td id="a_tid">${tname}</td>
                                    <td id="a_tagid">${tagsName}</td>
                                    <td id="a_titele">${ls.title}</td>
                                    <td id="a_abs">${ls.abscontent}</td>
                                    <td id="a_url">${ls.imgurl}</td>
                                    <td id="a_cj">${cjtime}</td>
                                    <td id="a_up">${uptime}</td>
                                    <td id="a_dz">${ls.hits}</td>
                                    <td id="a_xh">${ls.xh}</td>
                                    <td class="td-status">
                                      ${state}</td>
                                    <td class="td-manage">
                                      <a onclick="member_stop(this,${ls.id})" href="javascript:;"  title="${title}">
                                        <i class="layui-icon">&#xe601;</i>
                                      </a>
                                     
                                      <a title="编辑"  onclick="layer.open({
                resize: false,
                title: '编辑',
                shadeClose: true,
                area:['1200px','900px'],
                type: 2,
                content: 'article-edit2.html?aid=${ls.id}',
                success: function (layero, index) {
                   
                    
                } })"  href="javascript:;">
                                        <i class="layui-icon">&#xe642;</i>
                                      </a>
                                      
                                      <a title="删除" onclick="member_del(this,${ls.id})" href="javascript:;">
                                        <i class="layui-icon">&#xe640;</i>
                                      </a>
                                    </td>
                                  </tr>    
         `);

                }

                getPageList(currt,conter,10);
            }else if(data.code=="0"){
                layer.msg("没有任何用户");
            }else {
                layer.msg("网络故障");
            }

        },
        error:function(){
            layer.msg("网络故障");
        }
    })
}


//自己封装分页方法
function getPageListForChaZhao2(currt,count,search,kaishi,zuihou){
    layui.use(['laypage','laydate','form'], function(){
        var laydate = layui.laydate;
        var  laypage = layui.laypage;
        var  form = layui.form;
        //分页
        laypage.render({
            elem: 'demo0'
            ,count: count //数据总数
            ,curr:currt
            ,layout: ['count', 'prev', 'page', 'next',  'refresh', 'skip']
            ,jump: function(obj, first){
                //obj包含了当前分页的所有参数，比如：
                console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
                console.log(obj.limit); //得到每页显示的条数

                //首次不执行
                if(!first){
                    //do something
                    $("#usershuju").empty();
                    getArticleList(obj.curr,(obj.curr-1)*10,search,kaishi,zuihou);

                }
            }
        });
        form.render('checkbox');

        // 监听全选
        form.on('checkbox(checkall)', function(data){

            if(data.elem.checked){
                $('tbody input').prop('checked',true);
            }else{
                $('tbody input').prop('checked',false);
            }
            form.render('checkbox');
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#start' //指定元素
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#end' //指定元素
        });


    });

    //分页方法

}



