//加载完成
$(function(){
    layui.use(['layer'],function(){
        var layer = layui.layer;})

    $.ajax({
        url:'http://localhost:8080/pblog/returnOneUser',
        type:'post',
        dataType:'json',
        success:function(data){
            $("#zh").html(data.username);
            $("#reg").html(getTime(data.register));
            $("#nick").html(data.nickname);
            $("#email").html(data.email);
            $("#tel").html(data.phone);
            $("#sex").html(data.sex);
            $("#uid").val(data.id);
            $("#tximg").attr('src', "./headFile/"+data.imgurl);
            $("#pass").html(data.password);
            if(data.id!="0"){
                $("#login").append(`  <i class="iconfont">&#xe6c7;</i><a href="uinfo.html">${data.nickname}</a>`);
                $("#collect").append(` <i class="iconfont">&#xe6a0;</i><a href="collect.html">收藏</a>`);
                $("#out").append(` <i class="iconfont">&#xe6b7;</i><a href="./userOut">登出</a>`);
            }else {
                $("#login").append(` <i class="iconfont">&#xe6b4;</i><a href="login.html">登陆</a>`);
            }
            console.log(data.nickname);

        },
        error:function(){
            layer.msg("网络故障");
        }
    })


});
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



