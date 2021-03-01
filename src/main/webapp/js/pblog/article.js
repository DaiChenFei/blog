//加载完成
$(function(){
    var flag=false;//没评论时用的
    var url=window.location.search;
    var aid=url.substring(5,url.length);
    //alert(aid);
    getArticle(aid);//获取人才列表

});

//自己封装获取数据方法
function getArticle(aid){
    //获取人才列表
    $.ajax({
        url:'http://localhost:8080/pblog/oneArticle',
        type:'post',
        data:{"id":aid},
        dataType:'json',
        success:function(data) {
            console.log(data);
            //layer.msg("查看文章");
            if(data.code=="1"){
                var content=data.data;
                var comment=data.comment;
                var user=data.user;
                var coll=data.coll;
                var cjtime=getTime(content.createtime);
                var types=data.types;
                var tags=data.tag;
                var asc=data.asc;
                var desc=data.desc;
                var tname='',tagsName='';
                var tArr=new Array();
                tArr=content.tagid.split(',');
                var newArray = tArr.filter(function(v){return v!==''});
                //上下篇
                if(desc!=null){
                    $(".nextinfo").append(` <p>上一篇：<a href="./article.html?aid=${desc.id}">${desc.title}</a></p>`);
                }else {
                    $(".nextinfo").append(` <p>上一篇：无</p>`);
                }

                if(asc!=null){
                    $(".nextinfo").append(` <p>下一篇：<a href="./article.html?aid=${asc.id}">${asc.title}</a></p>`);
                }else {
                    $(".nextinfo").append(` <p>下一篇：无</p>`);
                }


                //判断登陆
                if(user!=null){
                    $("#login").append(`  <i class="iconfont">&#xe6c7;</i><a href="uinfo.html">${user.nickname}</a>`);
                    $("#out").append(` <i class="iconfont">&#xe6b7;</i><a href="./userOut">登出</a>`);
                    $("#art").append(`  <i class="iconfont">&#xe6c7;</i><a href="#">${user.nickname}</a>`);
                    $("#collect").append(` <i class="iconfont">&#xe6a0;</i><a href="collect.html">收藏</a>`);
                    $("#aid").val(content.id);
                    $("#uid").val(user.id);
                    $("#imgurl").val(user.imgurl);
                    if (coll!=null){
                        $(".collect").append(` <i class="iconfont">&#xe6a0;</i>已收藏文章`);
                    }else if(coll==null){
                        $(".collect").append(` <i class="iconfont">&#xe6a0;</i>收藏`);
                    }

                    pinglun(comment,user.id);
                    fuwenben();
                    console.log(comment);

                }else {
                    pinglun(comment,0);
                    $("#comment").append(` <div class="gbko" >您还未登陆，登陆后可以评论</div>`);
                    $("#login").append(` <i class="iconfont">&#xe6b4;</i><a href="login.html">登陆</a>`);
                }




                for(var j=0;j<types.length;j++){
                    var ty=types[j];
                    //console.log(ty);
                    if (content.tid==ty.id){
                        //console.log("TID"+ty.id);
                        tname=ty.name;
                    }
                }
                for(var m=0;m<newArray.length;m++){
                    for(var j=0;j<tags.length;j++){
                        if (tags[j].id==newArray[m]){
                             console.log("aR"+newArray[m]);
                            if(m==newArray.length-1)
                                tagsName+='<a href="" target="_blank">'+tags[j].name+'</a> &nbsp; ';
                            else
                                tagsName+='<a href="" target="_blank">'+tags[j].name+'</a> ';
                        }
                    }
                }
                $("#abscontent").append(`${content.abscontent}`);
                var str = content.content;
                if(str.indexOf("src=\"../uploadFile")>0){

                    str=str.replace('src="../uploadFile','src="./uploadFile');
                    //console.log(str);
                }
                // var result = str.replace('data-mce-src="../', 'A');
                // console.log(result);
                $("#content").append(str);
                //$("#content").append(`${content.content}`);
                $("#title").append(`${content.title}`);
                $("#cjtime").append(`${cjtime}`);
                $("#hits").append(`${content.hits}`);
                $("#likes").append(`${content.xh}`);
                $("#tags").append(`${tagsName}`);


                //文章的左边分类
                for(var j=0;j<types.length;j++){
                    var ty=types[j];
                    $("#fl").append(` <li><a href="javascript:void(0);" name="${ty.id}" onclick="seachType(${ty.id},0)" class="fenlei">${ty.name}</a></li>`);
                }
                //文章的左边标签
                for(var j=0;j<tags.length;j++){
                    var tg=tags[j];
                    $("#bqy").append(`<a href="javascript:void(0);" name="${tg.id}" onclick="seachTag(${tg.id},0)">${tg.name}</a>`);
                }
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
    var da=y+'-'+add0(m)+'-'+add0(d)+' ';
    return da;
}
//生成部分
function  pinglun(comment,id) {

    if(comment.length>=1){
        flag=false;
        for(var i=0;i<comment.length;i++){
            var com=comment[i];
            var  t=getTime(com.writein)
            var s = " ";
            s += '<div class="comment">';
            s += '<div class="imgdiv"><img class="imgcss"  src="./headFile/'+com.uimg+'"/></div>';
            s += '<div class="conmment_details">';
            s +=  '<span class="comment_name">'+com.uid+' </span>     <span class="timeRight">'+t+'</span>';

            s += 	'<div class="comment_content" > '+com.content+'</div>';
            if(id==com.uid){
                s += 	'<div class="del"> ';
                s += 	'<a class="del_comment"  data-id="'+com.id+'"><i class="icon layui-icon" >删除</i></a></div></div><hr></div>';
            }else {

                s += 	'</div><hr></div>';
            }

            $('.comment_list').append(s);
        }
    }else {
        flag=true;
        $("#comment").append(` <div class="gbko" >暂时还没有评论</div>`);
    }
}
//生成富文本
function  fuwenben() {

            var s = " ";
            s += '<textarea class="layui-textarea" id="LAY_demo1" style="display: none">';
            s += '</textarea>';
            s += '<div class="site-demo-button" style="margin-top: 20px;">';
            // s +=  '<button class="layui-btn site-demo-layedit" data-type="text">获取编辑器纯文本内容</button>' +
            s +=   '<button class="layui-btn" id="add" data-type="text">评论</button>';
            s += 	'</div>';

            $('#fuwenben').append(s);
    layui.use('layedit', function(){
        var layedit = layui.layedit
            ,$ = layui.jquery;

        //构建一个默认的编辑器
        var index = layedit.build('LAY_demo1',{
            tool: []
        });

        //编辑器外部操作
        // var active = {
        //     content: function(){
        //         alert(layedit.getContent(index)); //获取编辑器内容
        //     }
        //     ,text: function(){
        //         alert(layedit.getText(index)); //获取编辑器纯文本内容
        //     }
        //     ,selection: function(){
        //         alert(layedit.getSelection(index));
        //     }
        // };

        // $('.site-demo-layedit').on('click', function(){
        //     var type = $(this).data('type');
        //     active[type] ? active[type].call(this) : '';
        // });

        //自定义工具栏
        layedit.build('LAY_demo2', {
            tool: ['face', 'link', 'unlink', '|', 'left', 'center', 'right']
            ,height: 100
        })
        //添加评论
        $(document).on('click', '#add', function() {
            //
            if (flag){
                $('.comment_list').empty();
                flag=false;
            }
            var aid=document.getElementById("aid").value;
            var uid=document.getElementById("uid").value;
            var url=document.getElementById("imgurl").value;
            var content=layedit.getText(index);
            alert(content);
            $.ajax({
                url: "./addOneComment",
                data: {"aid":aid,"uid":uid,"content":content,"state":"1"},
                type:"Post",
                dataType:"json",
                success:function(data){
                    console.log(data);
                    if (data!="0"){
                        //console.log("准备跳转页面");
                        //location.href="./index.html";

                        layer.msg('评论成功');
                        add(uid,content,url,data);
                        layedit.clear;
                    }else if (data=="-1"){
                        console.log("评论失败");
                        layer.msg('评论失败');
                        //layer.alert(data);
                    }else{
                        console.log("失败");
                        layer.alert(data);
                    }
                }
            });
            $(this).html( "点击");
        });

    });



}
//添加喜欢，即点赞
//跳转首页搜索
$(document).on('click', '#seach', function() {
    //
    var title=document.getElementById("keyboard").value;
    if (title=="请输入关键字"){
        alert("请输入正确的标题");
    }else {
        var url=encodeURI(encodeURI(title));
        //window.location.href=url;
        window.location.href="index.html?tit="+url;
    }

    //


});
//评论的部分
    layui.use(['form','layer','jquery','element','laypage','form','layedit'],function(){
        var form = layui.form;
        window.layer = layui.layer;
        layedit = layui.layedit;
        window.jQuery = window.$ = layui.jquery;
        var layedit = layui.layedit;

        var element = layui.element,
            form = layui.form,
            laypage = layui.laypage;
        //删除评论
        $(document).on('click', '.del_comment', function() {
            var id = $(this).attr("data-id");
            $(this).closest('.comment').css("display", "none");
            //alert(typeof id);
            $.ajax({
                url:'http://localhost:8080/pblog/deleteOneComment',
                type:'post',
                data:{"id":id},
                dataType:'json',
                success:function(data){
                    if(data=="1"){
                        layer.msg('删除成功');
                    }else {
                        layer.msg("网络故障,请刷新");
                    }
                },
                error:function(){
                    layer.msg("网络故障");
                }
            })
        });
        //增加收藏
        $(document).on('click', '.collect', function() {
            var uid=document.getElementById("uid").value;
            var aid=document.getElementById("aid").value;
            if(uid!=""){
                $.ajax({
                    url:'http://localhost:8080/pblog/addOneCollect',
                    type:'post',
                    data:{"aid":aid},
                    dataType:'json',
                    success:function(data){
                        if(data=="1"){
                            layer.msg('收藏成功');
                           // $(".collect").html('<i class=\"iconfont\">&#xe6a0;</i>收藏');

                            $(".collect").html(` <i class="iconfont">&#xe6a0;</i>已收藏`);
                        }else if(data=="0"){
                            //layer.msg('收藏失败，已有该收藏');
                            layer.msg('已取消收藏');
                            $(".collect").html(` <i class="iconfont">&#xe6a0;</i>收藏`);
                        }else {
                            layer.msg("网络故障,请刷新");
                        }
                    },
                    error:function(){
                        layer.msg("网络故障");
                    }
                })
            }else  {
                layer.msg('登陆后才能收藏');
            };
            //alert(typeof id);

        });
        // $(document).on('click', '.comment_add_or_last', function() {
        //     add();
        //     $(this).html( "点击加载更多");
        // });

        function  delComment(id) {
            alert(id);
        }
        //点赞
        var like=1;
        $(document).on('click', '.dianzan', function() {
            var uid=document.getElementById("uid").value;

            console.log(dianzan);
           // console.log(lk);
            //


            if(uid!=""&&like=="1"){
                like=0;
                console.log(uid+" "+like);

                console.log(uid+" "+lk);
                var dianzan=document.getElementById("likes").innerText;
                var lk=parseInt(dianzan);
                lk=lk+1;
                var aid=document.getElementById("aid").value;
                $.ajax({
                    url:'http://localhost:8080/pblog/addLike',
                    type:'post',
                    data:{"xh":lk,"id":aid},
                    dataType:'json',
                    success:function(data){
                     if(data=="1"){
                         $("#likes").html(lk);
                         console.log(uid+" "+lk);
                         layer.msg('赞成功,喜欢+1');
                     }else {
                         layer.msg("网络故障,请重试");
                     }

                    },
                    error:function(){
                        layer.msg("网络故障");
                    }
                })


            }else if (like=="0"){
                layer.msg('浏览一次只能赞一次');
            }else  {
                layer.msg('登陆后才能赞');
            };

        });

    });

//成功后添加评论
function  add(uid,content,url,id) {
    var date = new Date();
    var t=getTime(date);
    var s = " ";
    s += '<div class="comment">';
    s += '<div class="imgdiv"><img class="imgcss"  src="./headFile/'+url+'"/></div>';
    s += '<div class="conmment_details">';
    s +=  '<span class="comment_name">'+uid+' </span>     <span class="timeRight">'+t+'</span>';
    s += 	'<div class="comment_content" > '+content+'</div>';
    s += 	'<div class="del"> ';
    s += 	'<a class="del_comment"  data-id="'+id+'"><i class="icon layui-icon" >删除</i></a></div></div><hr></div>';
    $('.comment_list').append(s);
}
