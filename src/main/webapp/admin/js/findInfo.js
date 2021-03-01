//加载完成
$(function(){

    getPeopleList(1,0,10);//获取列表


});
//自己封装获取数据方法
function getPeopleList(crr,page,lmt){
    //获取人才列表
    $.ajax({
        url:'http://localhost:8080/pblog/admin/returnOneInfo',
        type:'post',
        dataType:'json',
        success:function(data){
            $("#name").val(data.name);
            $("#nickname").val(data.nickname);
            $("#info").val(data.info);
            $("#qq").val(data.qq);
            $("#wxurl").val(data.wxurl);
            $("#email").val(data.email);
            $("#job").val(data.job);
            console.log(data.nickname);

            // if(data.code=="1"){
            //
            //
            // }else if(data.code=="0"){
            //     layer.msg("没有任何标签");
            // }else {
            //     layer.msg("网络故障");
            // }
        },
        error:function(){
            layer.msg("网络故障");
        }
    })
}



