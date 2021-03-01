//加载完成
$(function(){
        getPeopleList(1,0,10);//获取人才列表
});

//自己封装获取数据方法
function getPeopleList(crr,page,lmt){
    layui.use(['form','layer','jquery','element','laypage','upload','layedit'],function(){
        var form = layui.form;
        var layer = layui.layer;
        //删除评论
        $(document).on('click', '.del_comment', function() {
            var id = $(this).attr("data-id");

            $(this).closest('.comment').css("display", "none");
            //alert(typeof id);
            $.ajax({
                url:'http://localhost:8080/pblog/deleteOneMessage',
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

    })
    //获取人才列表
    $.ajax({
        url:'http://localhost:8080/pblog/listMessage',
        type:'post',
        data:{"page":page},
        dataType:'json',
        success:function(data){
            console.log("get"+data.code+data.msg);
            console.log(data.length);
            var user=data.user;
            if(data.code=="1"){
                var conter=data.count;
                var sj=data.data;
                // var user=data.user;
                console.log(user);
                var currt=crr;


                if(user!=null){
                    $("#login").append(`  <i class="iconfont">&#xe6c7;</i><a href="uinfo.html">${user.nickname}</a>`);
                    $("#collect").append(` <i class="iconfont">&#xe6a0;</i><a href="collect.html">收藏</a>`);
                    $("#out").append(` <i class="iconfont">&#xe6b7;</i><a href="./userOut">登出</a>`);
                    $("#uid").val(user.id);
                    $("#url").val(user.imgurl);
                    $("#counter").val(sj.length);
                    fuwenben();

                }else {
                    $("#login").append(` <i class="iconfont">&#xe6b4;</i><a href="login.html">登陆</a>`);
                    $("#check").append(` <div class="gbko" >登陆后留言</div>`);
                }


                for(var i=0;i<sj.length;i++){    //遍历data数组
                    var ls = sj[i];
                    var t=getTime(ls.writein);
                    var s = " ";
                    s += '<div class="comment">';
                    s += '<div class="imgdiv"><img class="imgcss"  src="./headFile/'+ls.uimg+'"/></div>';
                    s += '<div class="conmment_details">';
                    s +=  '<span class="comment_name">'+ls.nickname+' </span>     <span class="timeRight">'+t+'</span>';

                    s += 	'<div class="comment_content" > '+ls.content+'</div>';
                    if(user!=null){
                        if(user.id==ls.uid) {
                            s += '<div class="del"> ';
                            s += '<a class="del_comment"  data-id="' + ls.id + '"><i class="icon layui-icon" >删除</i></a></div></div><hr></div>';
                        }else{
                            s += 	'</div><hr></div>';
                        }
                    }else {

                        s += 	'</div><hr></div>';
                    }

                    $('.comment_list').append(s);
                }

                getPageList(currt,conter,10);

            }else if(data.code=="0"){
                if(user!=null){
                    $("#login").append(`  <i class="iconfont">&#xe6c7;</i><a href="uinfo.html">${user.nickname}</a>`);
                    $("#uid").val(user.username);//保存用户名
                    $("#url").val(user.imgurl);
                    $("#counter").val(1);
                    fuwenben();

                }else {
                    $("#login").append(` <i class="iconfont">&#xe6b4;</i><a href="login.html">登陆</a>`);
                    $("#check").append(` <div class="gbko" >登陆后留言</div>`);
                }
                layer.msg("没有任何评论");
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
    var da=y+'-'+add0(m)+'-'+add0(d)+' ';
    return da;
}

//自己封装分页方法
function getPageList(currt,count,limit) {
    layui.use(['laypage', 'laydate', 'form'], function () {
        var laydate = layui.laydate;
        var laypage = layui.laypage;
        var form = layui.form;
        //分页
        laypage.render({
            elem: 'demo0'
            , count: count //数据总数
            , curr: currt
            , layout: ['count', 'prev', 'page', 'next', 'refresh', 'skip']
            , jump: function (obj, first) {
                //obj包含了当前分页的所有参数，比如：
                console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
                console.log(obj.limit); //得到每页显示的条数
                layui.use('element', function () {
                    var element = layui.element;
                    element.init();
                    element.render();
                });

                //首次不执行
                if (!first) {
                    //do something
                    $(".comment_list").empty();
                    $('#fuwenben').empty();
                    $('#login').empty();
                    $('#check').empty();
                    getPeopleList(obj.curr, (obj.curr - 1) * 10, 10);

                }
            }
        });


    });


}

//生成富文本
function  fuwenben() {

    var s = " ";
    s += '<textarea class="layui-textarea" id="LAY_demo1" style="display: none">';
    s += '</textarea>';
    s += '<div class="site-demo-button" style="margin-top: 20px;">';
    // s +=  '<button class="layui-btn site-demo-layedit" data-type="text">获取编辑器纯文本内容</button>' +
    s +=  '<button class="layui-btn" id="add" data-type="text">评论</button>';
    s += 	'</div>';

    $('#fuwenben').append(s);
    layui.use('layedit', function(){
        var layedit = layui.layedit
            ,$ = layui.jquery;

        //构建一个默认的编辑器
        var index = layedit.build('LAY_demo1',{
            tool: [  ]
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
        //
        // $('.site-demo-layedit').on('click', function(){
        //     var type = $(this).data('type');
        //     active[type] ? active[type].call(this) : '';
        // });

        //自定义工具栏

        //添加评论
        $(document).on('click', '#add', function() {

            var uid=document.getElementById("uid").value;
            var url=document.getElementById("url").value;
            var ct=document.getElementById("counter").value;
            var content=layedit.getText(index);
            alert(content);
            $.ajax({
                url: "./addOneMessage",
                data: {"uid":uid,"content":content},
                type:"Post",
                dataType:"json",
                success:function(data){
                    console.log(data);
                    if (data!="0"){
                        console.log("准备跳转页面");
                        //location.href="./index.html";
                        layer.msg('提交成功');
                        if(ct<10){
                            add(uid,content,url,data);
                        }else{
                            alert("请刷新，您的评论在最后一页");
                        }

                    }else if (data=="-1"){
                        console.log("失败login");
                        layer.msg('提交失败');
                    }else{
                        console.log("失败");
                        layer.msg('提交失败');
                    }
                }
            });
            $(this).html( "点击");
        });
    });
}
//成功后添加评论
function  add(uid,content,url,id) {
    var date = new Date();
    var t=getTime(date);
    var s = " ";
    s += '<div class="comment">';
    s += '<div class="imgdiv"><img class="imgcss"  src="./headFile/'+url+'"/></div>';
    s += '<div class="conmment_details">';
    s +=  '<span class="comment_name">'+uid+' </span>     <span>'+t+'</span>';
    s += 	'<div class="comment_content" > '+content+'</div>';
    s += 	'<div class="del"> ';
    s += 	'<a class="del_comment"  data-id="'+id+'"><i class="icon layui-icon" >删除</i></a></div></div><hr></div>';
    $('.comment_list').append(s);
}
