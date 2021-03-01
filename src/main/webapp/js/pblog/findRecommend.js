//加载完成
$(function(){

    $.ajax({
        url:'http://localhost:8080/pblog/listRecommend',
        type:'post',
        dataType:'json',
        success:function(data){
            if (data.code=="1"){
                for (var i=0;i<data.data.length;i++){
                    var a=data.data[i];
                    $("#recommendslist").append(`  <li><b><a href="./article.html?aid=${a.id}" target="_blank">${a.title}</a></b>
          <p><i><img src="./uploadFile/${a.imgurl}"></i>${a.info}</p>
        </li>`);
                }
            }else if(data.code=="0"){
                $("#recommendslist").append(` <li><b>暂无推荐</b></li>`);
            }


            console.log(data.nickname);

        },
        error:function(){
            layer.msg("网络故障");
        }
    })


});




