$(document).ready(function() {
    console.log("标签开始工作");
    $.ajax({
        url : "http://localhost:8080/pblog/admin/listTagForArticle",//请求地址
        dataType : "json",//数据格式
        type : "post",//请求方式
        async : false,//是否异步请求
        success : function(data) {
            console.log("get"+data.code+data.msg);
            console.log(data.length);
            if(data.code=="1"){
                var sj=data.data;
                for(var i=0;i<sj.length;i++){    //遍历data数组
                    var ls = sj[i];
                    console.log("接收用户数组");
                    //$("#usershuju").empty();
                    $("#tags").append(` <p><input type="checkbox" name="name${ls.id}" value="${ls.id}" title="${ls.name}" lay-skin="primary" /></p>`);
                }
            }else if(data.code=="0"){
                layer.msg("没有任何标签");
            }else {
                layer.msg("网络故障");
            }
        },
    })
    ,
        $.ajax({
            url : "http://localhost:8080/pblog/admin/listTypeForArticle",//请求地址
            dataType : "json",//数据格式
            type : "post",//请求方式
            async : false,//是否异步请求
            success : function(data) {
                console.log("get"+data.code+data.msg);
                console.log(data.length);
                if(data.code=="1"){
                    var sj=data.data;
                    for(var i=0;i<sj.length;i++){    //遍历data数组
                        var ls = sj[i];
                        console.log("接收用户数组");
                        //$("#usershuju").empty();
                        $("#type").append(` <option value="${ls.id}">${ls.name}</option>`);
                    }
                }else if(data.code=="0"){
                    layer.msg("没有任何分类");
                }else {
                    layer.msg("网络故障");
                }
            },
        })
})