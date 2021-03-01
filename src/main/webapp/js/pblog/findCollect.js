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
            url:'http://localhost:8080/pblog/listCollect',
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

                    if(user!=null){

                        $("#out").append(` <i class="iconfont">&#xe6b7;</i><a href="./userOut">登出</a>`);
                        $("#collect").append(` <i class="iconfont">&#xe6a0;</i><a href="collect.html">收藏</a>`);
                        $("#login").append(`  <i class="iconfont">&#xe6c7;</i><a href="uinfo.html">${user.nickname}</a>`);

                    }else {
                        $("#login").append(` <i class="iconfont">&#xe6b4;</i><a href="login.html">登陆</a>`);
                    }


                    for(var i=0;i<sj.length;i++){    //遍历data数组
                        var ls = sj[i];
                        // var ts=types
                        console.log("接收收藏数组");

                        //$("#usershuju").empty();
                        $("#collects").append(`<div class="layui-col-md12">
                <div class="layui-card">
                    <div class="layui-card-header"><a href="./article.html?aid=${ls.aid}">${ls.title}</a><button id="coll${ls.id}" type="button" class="layui-btn layui-btn-normal layui-btn-sm btn-ic del_collect" data-id="${ls.id}">删除</button></div>
                    <div class="layui-card-body" id="pass">
                        <a href="./article.html?aid=${ls.aid}">${ls.abscontent}</a>
                    </div>
                </div>
            </div>
         `);

                    }
                    //layer.msg("有文章");//未失效，layui样式与本地冲突
                    getPageList(currt,conter,10);
                }else if(data.code=="0"){
                    //layer.msg("没有任何文章");
                    alert("没有任何收藏");
                    $("#collects").append(`
                <li><h3 class="blogtitle"><span class="fc-blue">【没有收藏】</span></h3></li> `);
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




