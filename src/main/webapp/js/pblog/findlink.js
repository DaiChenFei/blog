//加载完成
$(function(){

    $.ajax({
        url:'http://localhost:8080/pblog/listLink',
        type:'post',
        dataType:'json',
        success:function(data){
            if (data.code=="1"){
                for (var i=0;i<data.data.length;i++){
                    var a=data.data[i];
                    $("#links").append(`<li><a href="//${a.url}"  target="_blank" title="${a.info}">${a.info}</a></li>`);
                }
            }else if(data.code=="0"){
                $("#links").append(` <li><b>暂无友情链接</b></li>`);
            }


            console.log(data.nickname);

        },
        error:function(){
            layer.msg("网络故障");
        }
    })


});




