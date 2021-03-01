//加载完成
$(function(){
    var url=window.location.search;
    var type=url.substring(1,4);
    if(type=="tid"){
        console.log(type);
        var tid=url.substring(5,url.length);
        console.log(tid);
        if(tid!=""){
            seachType(tid,1);
            console.log("主页分类2");
            var urllocal = window.location.href;
            var valiable = urllocal.split("?")[0];
            window.history.pushState({},0,valiable);
        }else {
            getPeopleList(1,0,10);//获取人才列表
            console.log("主页1");
        }

    }else if(type=="tag"){
        console.log(type);
        var tid=url.substring(5,url.length);
        console.log(tid);
        if(tid!=""){
            seachTag(tid,1);
            console.log("主页分类2");
            var urllocal = window.location.href;
            var valiable = urllocal.split("?")[0];
            window.history.pushState({},0,valiable);
        }else {
            getPeopleList(1,0,10);//获取人才列表
            console.log("主页1");
        }

    }
    else if(type=="tit"){
        console.log(type);

        var title=url.substring(5,url.length);
        //双重解码
        var name=decodeURI(decodeURI(title));

        console.log(name);
        if(title!=""){
            layui.use(['layer'],function(){
                var layer = layui.layer;
                layer.msg("有文章");
            })
            getArticleList(1,0,name,'2010-1-1','2030-3-1');
            console.log("主页分类2");
            var urllocal = window.location.href;
            var valiable = urllocal.split("?")[0];
            window.history.pushState({},0,valiable);
        }else {
            getPeopleList(1,0,10);//获取人才列表
            console.log("主页1");
        }

    }else {

        getPeopleList(1,0,10);//获取人才列表
        console.log("主页1");
    }



});

//自己封装获取数据方法
function getPeopleList(crr,page,lmt){
    //获取人才列表
    layui.use(['layer'],function(){
        var layer = layui.layer;

    $.ajax({
        url:'http://localhost:8080/pblog/listArticle',
        type:'post',
        data:{"page":page},
        dataType:'json',
        success:function(data){
            //layer.msg("有文章");
            console.log("get"+data.code+data.msg);
            console.log(data.length);
            if(data.code=="1"){
                var conter=data.count;
                var sj=data.data;
                var user=data.user;
                console.log(user);
                var currt=crr;
                var types=data.types;
                var tags=data.tag;
                //console.log(types);
                var tArr=new Array();
                if(user!=null){
                    $("#login").append(`  <i class="iconfont">&#xe6c7;</i><a href="uinfo.html">${user.nickname}</a>`);
                    $("#collect").append(` <i class="iconfont">&#xe6a0;</i><a href="collect.html">收藏</a>`);
                    $("#out").append(` <i class="iconfont">&#xe6b7;</i><a href="./userOut">登出</a>`);
                }else {
                    $("#login").append(` <i class="iconfont">&#xe6b4;</i><a href="login.html">登陆</a>`);
                }
                for(var j=0;j<types.length;j++){
                    var ty=types[j];
                    $("#fl").append(` <li><a href="javascript:void(0);" name="${ty.id}" onclick="seachType(${ty.id},1)" class="fenlei">${ty.name}</a></li>`);
                }
                for(var j=0;j<tags.length;j++){
                    var tg=tags[j];
                    $("#bqy").append(`<a href="javascript:void(0);" name="${tg.id}" onclick="seachTag(${tg.id},1)">${tg.name}</a>`);
                }

                for(var i=0;i<sj.length;i++){    //遍历data数组
                    var ls = sj[i];
                    // var ts=types
                    console.log("接收文章数组");
                    var cjtime=getTime(ls.createtime);
                    var uptime=getTime(ls.updatetime);
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

                                    tagsName+='<a class="tag">'+tags[j].name+'</a>';
                                else
                                    tagsName+='<a class="tag">'+tags[j].name+'</a>';
                            }
                        }
                    }
                    //$("#usershuju").empty();
                    $("#blogs").append(`
                <li> <span class="blogpic"><a href="./article.html?aid=${ls.id}"><img width="250px" height="250px" src="uploadFile/${ls.imgurl}"></a></span>
      <h3 class="blogtitle"><span class="fc-blue">【${tname}】</span><a href="./article.html?aid=${ls.id}">${ls.title}</a></h3>
      <div class="bloginfo">
        <p>${ls.abscontent}</p>
      </div>
      <div class="autor"><div class="f-fl tags"> <i class="iconfont">&#xe6f7;</i> ${tagsName}   &nbsp<i class="iconfont">&#xe829;</i> ${cjtime}  &nbsp<i class="iconfont">&#xe7ce;</i>&nbsp喜欢( ${ls.xh})&nbsp &nbsp<i class="iconfont">&#xe6e6;</i>&nbsp浏览（${ls.hits}） </div><span class="readmore"><a href="./article.html?aid=${ls.id}">阅读原文</a></span> </div>
    
    </li>
         `);

                }
                //layer.msg("有文章");//未失效，layui样式与本地冲突
                getPageList(currt,conter,10);
            }else if(data.code=="0"){
                //layer.msg("没有任何文章");
                alert("没有任何文章");
                $("#blogs").append(`
                <li><h3 class="blogtitle"><span class="fc-blue">【没有文章】</span></h3></li> `);
                console.log("没有任何文章");
                var user=data.user;
                if(user!=null){
                    $("#login").append(`  <i class="iconfont">&#xe6c7;</i><a href="uinfo.html">${user.nickname}</a>`);
                }else {
                    $("#login").append(` <i class="iconfont">&#xe6b4;</i><a href="login.html">登陆</a>`);
                }
                //alert("123");

                return false;
            }else {
                layer.msg("网络故障");
            }
        },
        error:function(){
            layer.msg("网络故障");
        }
    })
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
    var da=y+'-'+add0(m)+'-'+add0(d)+' ';
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
                layui.use('element', function() {
                    var element = layui.element;
                    element.init();
                    element.render();
                });

                //首次不执行
                if(!first){
                    //do something
                    $("#blogs").empty();
                    $("#fl").empty();
                    $("#bqy").empty();
                    getPeopleList(obj.curr,(obj.curr-1)*10,10);

                }
            }
        });



    });

    //类型方法
$(document).on('click', '.fenlei', function() {
    layer.msg('赞成功,喜欢+1');
    });
}




