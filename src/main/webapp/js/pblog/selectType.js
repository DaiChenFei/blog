//自己封装,这是为了搜索
function seachType(tid,index) {
    // var url=window.location.search;
    // var tid=url.substring(5,url.length);
    if (index!="1"){
        window.location.href="index.html?tid="+tid;

    }
    getArticleTypeList(1,0,tid);
}


function getArticleTypeList(crr,page,search){
    layui.use(['layer'],function(){
        var layer = layui.layer;})
    //获取列表
    $.ajax({
        url:'http://localhost:8080/pblog/TypeArticle',
        type:'post',
        data:{
            "page":page,
            "lx":search

        },
        dataType:'json',
        success:function(data){
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
                $("#login").empty();
                if(user!=null){
                    $("#login").append(`  <i class="iconfont">&#xe6c7;</i><a href="uinfo.html">${user.nickname}</a>`);
                    $("#out").append(` <i class="iconfont">&#xe6b7;</i><a href="./userOut">登出</a>`);
                }else {
                    $("#login").append(` <i class="iconfont">&#xe6b4;</i><a href="login.html">登陆</a>`);
                }
                $("#fl").empty();
                for(var j=0;j<types.length;j++){
                    var ty=types[j];
                    $("#fl").append(` <li><a href="javascript:void(0);" name="${ty.id}" onclick="seachType(${ty.id},1)" class="fenlei">${ty.name}</a></li>`);
                }
                $("#bqy").empty();
                //console.log(tags);
                for(var j=0;j<tags.length;j++){
                    var tg=tags[j];
                  //  console.log(tg);
                    $("#bqy").append(`<a href="javascript:void(0);" name="${tg.id}" onclick="seachTag(${tg.id},1)">${tg.name}</a>`);
                }
                $("#blogs").empty();
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
                <li> <span class="blogpic"><a href="./article.html?aid=${ls.id}"><img width="250px" height="250px"  src="uploadFile/${ls.imgurl}"></a></span>
      <h3 class="blogtitle"><span class="fc-blue">【${tname}】</span><a href="./article.html?aid=${ls.id}">${ls.title}</a></h3>
      <div class="bloginfo">
        <p>${ls.abscontent}</p>
      </div>
      <div class="autor"><div class="f-fl tags"> <i class="iconfont">&#xe6f7;</i> ${tagsName}   &nbsp<i class="iconfont">&#xe829;</i> ${cjtime}  &nbsp<i class="iconfont">&#xe7ce;</i>&nbsp喜欢( ${ls.xh})&nbsp &nbsp<i class="iconfont">&#xe6e6;</i>&nbsp浏览（${ls.hits}） </div><span class="readmore"><a href="./article.html?aid=${ls.id}">阅读原文</a></span> </div>
    
    </li>
         `);

                }

                getPageList(currt,conter,10);
            }else if(data.code=="0"){
                var types=data.types;
                var tags=data.tag;
                var user=data.user;
                $("#login").empty();
                if(user!=null){
                    $("#login").append(`  <i class="iconfont">&#xe6c7;</i><a href="#">${user.nickname}</a>`);
                }else {
                    $("#login").append(` <i class="iconfont">&#xe6b4;</i><a href="login.html">登陆</a>`);
                }
                $("#fl").empty();
                for(var j=0;j<types.length;j++){
                    var ty=types[j];
                    $("#fl").append(` <li><a href="javascript:void(0);" name="${ty.id}" onclick="seachType(${ty.id},1)" class="fenlei">${ty.name}</a></li>`);
                }
                $("#bqy").empty();
                for(var j=0;j<tags.length;j++){
                    var tg=tags[j];
                    $("#bqy").append(`<a href="javascript:void(0);" name="${tg.id}" onclick="seachTag(${tg.id},1)">${tg.name}</a>`);
                }
                $("#blogs").empty();
                $("#blogs").append(`
                <li><h3 class="blogtitle"><span class="fc-blue">【没有该分类文章】</span></h3></li> `);
                $("#demo0").empty();
                alert("没有该类文章");
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


//自己封装分页方法
function getPageListForType(currt,count,search){
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
                    $("#blogs").empty();
                    getArticleTypeList(obj.curr,(obj.curr-1)*10,search);

                }
            }
        });



    });

    //分页方法

}
