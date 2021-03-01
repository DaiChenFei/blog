//加载完成
$(function(){

    $.ajax({
        url:'http://localhost:8080/pblog/hitsArticle',
        type:'post',
        dataType:'json',
        success:function(data){
            if (data.code=="1"){
                for (var i=0;i<data.data.length;i++){
                    var a=data.data[i];
                    $("#hitslist").append(` <li><b><a href="./article.html?aid=${a.id}" target="_blank">${a.title}</a></b>
          <p><i><img src="./uploadFile/${a.imgurl}"></i>${a.abscontent}</p>
        </li>`);
                }
            }else if(data.code=="0"){
                $("#hitslist").append(` <li><b>暂无文章</b></li>`);
            }


            console.log(data.nickname);

        },
        error:function(){
            layer.msg("网络故障");
        }
    })


});




