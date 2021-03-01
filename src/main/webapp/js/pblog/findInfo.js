//加载完成
$(function(){

    $.ajax({
        url:'http://localhost:8080/pblog/admin/returnOneInfo',
        type:'post',
        dataType:'json',
        success:function(data){
            $("#name").html(data.nickname+"|"+data.name);
            $("#nickname").html(data.nickname);
            $("#info").html(data.info);
            $("#qq").html(data.qq);
            $("#wxurl").html(data.wxurl);
            $("#email").html(data.email);
            $("#job").html(data.job);
            $("#tximg").attr('src', "./uploadFile/"+data.imgurl);
            $("#coll").append(` <li>QQ:${data.qq}</a></li>`);
            $("#coll").append(` <li>微信:${data.wxurl}</a></li>`);
            console.log(data.nickname);

        },
        error:function(){
            layer.msg("网络故障");
        }
    })


});




