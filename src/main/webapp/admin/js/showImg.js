$(document).ready(function() {

    $.ajax({
        url:'http://localhost:8080/pblog/admin/selectImg',
        type:'post',

        dataType:'json',
        success:function(data){

            console.log(data);
            if(data.code=="0"){
                layer.msg("没有任何头像");


            }else if(data.code=="1"){
                $("#tximg").attr('src', "../uploadFile/"+data.srcurl);
            }
        },
        error:function(){
            layer.msg("网络故障");
        }
    })
})