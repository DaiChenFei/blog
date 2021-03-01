//加载完成
$(function(){

    getPeopleList(1,0,10);//获取列表

});


//自己封装获取数据方法
function getPeopleList(crr,page,lmt){
    //获取人才列表
    $.ajax({
        url:'http://localhost:8080/pblog/admin/listUserTest',
        type:'post',
        data:{
            "page":page,
            "limit":lmt

        },
        dataType:'json',
        success:function(data){
            console.log("get"+data.code+data.msg);
            console.log(data.length);
            if(data.code=="1"){
            var conter=data.count;
            var sj=data.data;
            var currt=crr;
            for(var i=0;i<sj.length;i++){    //遍历data数组
                var ls = sj[i];
                console.log("接收用户数组");
                var email=ls.email;
                var nickname=ls.nickname;
                var time = new Date(ls.register);
                var y = time.getFullYear();
                var m = time.getMonth()+1;
                var d = time.getDate();
                var h = time.getHours();
                var mm = time.getMinutes();
                var s = time.getSeconds();
                function add0(m){return m<10?'0'+m:m }
                var da=y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
                var state1='<span class="layui-btn layui-btn-normal layui-btn-mini">已启用</span>';
                var state2='<span class="layui-btn layui-btn-normal layui-btn-mini layui-btn-disabled">已停用</span>';
                var state='';
                var title;
                if(ls.state==1){
                   state=state1;
                    title='停用';
                }else {
                    state=state2;

                    title='启用';
                }
                //$("#usershuju").empty();
                $("#usershuju").append(`
                <tr>
                                    <td>
                                      <input type="checkbox" name="id" value="${ls.id}"   lay-skin="primary"> 
                                    </td>
                                    <td id="user_id">${ls.id}</td>
                                    <td id="user_name">${ls.username}</td>
                                    <td id="nick_name">${ls.nickname}</td>
                                    <td id="sex">${ls.sex}</td>
                                    <td id="sex">${ls.phone}</td>
                                    <td id="email">${ls.email}</td>
                                    <td id="url">${ls.imgurl}</td>
                                    <td id="sex">${da}</td>
                                  
                                    <td class="td-status">
                                      ${state}</td>
                                    <td class="td-manage">
                                      <a onclick="member_stop(this,${ls.id})" href="javascript:;"  title="${title}">
                                        <i class="layui-icon">&#xe601;</i>
                                      </a>
                                     
                                      <a title="编辑"  onclick="layer.open({
                resize: false,
                title: '编辑',
                shadeClose: true,
                area:['600px','500px'],
                type: 2,
                content: 'member-edit.html',
                success: function (layero, index) {
                    var body = layer.getChildFrame('body', index);
                     body.find('#L_id').val('${ls.id}');
                    body.find('#L_email').val('${ls.email}');
                    body.find('#L_username').val('${ls.nickname}');
                    body.find('#shouji').val('${ls.phone}');
                } })"  href="javascript:;">
                                        <i class="layui-icon">&#xe642;</i>
                                      </a>
                                      <a title="修改密码"  onclick="layer.open({
                                        resize: false,
                                        title: '修改密码',
                                        shadeClose: true,
                                        area:['600px','500px'],
                                        type: 2,
                                        content: 'member-password.html',
                                        success: function (layero, index) {
                                            var body = layer.getChildFrame('body', index); 
                                            body.find('#L_id').val('${ls.id}');
                                            body.find('#L_username').val('${ls.nickname}');
                                        } })"  href="javascript:;">
                                        <i class="layui-icon">&#xe631;</i>
                                      </a>
                                      <a title="删除" onclick="member_del(this,${ls.id})" href="javascript:;">
                                        <i class="layui-icon">&#xe640;</i>
                                      </a>
                                    </td>
                                  </tr>    
         `);

            }

            getPageList(currt,conter,10);
            }else if(data.code=="0"){
                layer.msg("没有任何用户");
            }else {
                layer.msg("网络故障");
            }
        },
        error:function(){
            layer.msg("网络故障");
        }
    })
}


//自己封装分页方法
function getPageList(currt,count,limit){
    layui.use(['laypage','laydate','form'], function(){
        var laydate = layui.laydate;
        var  laypage = layui.laypage;
        var  form = layui.form;
        //分页
        laypage.render({
            elem: 'demo0'
            ,count: count //数据总数
            ,curr:currt
                ,layout: ['count', 'prev', 'page', 'next',  'refresh', 'skip']
            ,jump: function(obj, first){
                //obj包含了当前分页的所有参数，比如：
                console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
                console.log(obj.limit); //得到每页显示的条数

                //首次不执行
                if(!first){
                    //do something
                    $("#usershuju").empty();
                    getPeopleList(obj.curr,(obj.curr-1)*10,10);

                }
            }
        });
        form.render('checkbox');

        // 监听全选
        form.on('checkbox(checkall)', function(data){

            if(data.elem.checked){
                $('tbody input').prop('checked',true);
            }else{
                $('tbody input').prop('checked',false);
            }
            form.render('checkbox');
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#start' //指定元素
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#end' //指定元素
        });


    });

    //分页方法

}

//自己封装获取数据方法
function getUserList(crr,page,name,kaishi,zuihou){
    //获取列表
    $.ajax({
        url:'http://localhost:8080/pblog/admin/returnFindUser',
        type:'post',
        data:{
            "page":page,
            "username":name,
            "old":kaishi,
            "old2":zuihou,
        },
        dataType:'json',
        success:function(data){
            console.log("get"+data.code+data.msg);
            console.log(data.length);
            if(data.code=="1"){
                var conter=data.count;
                var sj=data.data;
                var currt=crr;
                for(var i=0;i<sj.length;i++){    //遍历data数组
                    var ls = sj[i];
                    console.log("接收用户数组");
                    var email=ls.email;
                    var nickname=ls.nickname;
                    var time = new Date(ls.register);
                    var y = time.getFullYear();
                    var m = time.getMonth()+1;
                    var d = time.getDate();
                    var h = time.getHours();
                    var mm = time.getMinutes();
                    var s = time.getSeconds();
                    function add0(m){return m<10?'0'+m:m }
                    var da=y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s);
                    var state1='<span class="layui-btn layui-btn-normal layui-btn-mini">已启用</span>';
                    var state2='<span class="layui-btn layui-btn-normal layui-btn-mini layui-btn-disabled">已停用</span>';
                    var state='';
                    var title;
                    if(ls.state==1){
                        state=state1;
                        title='停用';
                    }else {
                        state=state2;

                        title='启用';
                    }
                    //$("#usershuju").empty();
                    $("#usershuju").append(`
                <tr>
                                    <td>
                                      <input type="checkbox" name="id" value="${ls.id}"   lay-skin="primary"> 
                                    </td>
                                    <td id="user_id">${ls.id}</td>
                                    <td id="user_name">${ls.username}</td>
                                    <td id="nick_name">${ls.nickname}</td>
                                    <td id="sex">${ls.sex}</td>
                                    <td id="sex">${ls.phone}</td>
                                    <td id="email">${ls.email}</td>
                                    <td id="url">${ls.imgurl}</td>
                                    <td id="sex">${da}</td>
                                  
                                    <td class="td-status">
                                      ${state}</td>
                                    <td class="td-manage">
                                      <a onclick="member_stop(this,${ls.id})" href="javascript:;"  title="${title}">
                                        <i class="layui-icon">&#xe601;</i>
                                      </a>
                                     
                                      <a title="编辑"  onclick="layer.open({
                resize: false,
                title: '编辑',
                shadeClose: true,
                area:['600px','500px'],
                type: 2,
                content: 'member-edit.html',
                success: function (layero, index) {
                    var body = layer.getChildFrame('body', index);
                     body.find('#L_id').val('${ls.id}');
                    body.find('#L_email').val('${ls.email}');
                    body.find('#L_username').val('${ls.nickname}');
                } })"  href="javascript:;">
                                        <i class="layui-icon">&#xe642;</i>
                                      </a>
                                      <a title="修改密码"  onclick="layer.open({
                                        resize: false,
                                        title: '修改密码',
                                        shadeClose: true,
                                        area:['600px','500px'],
                                        type: 2,
                                        content: 'member-password.html',
                                        success: function (layero, index) {
                                            var body = layer.getChildFrame('body', index); 
                                            body.find('#L_id').val('${ls.id}');
                                            body.find('#L_username').val('${ls.nickname}');
                                        } })"  href="javascript:;">
                                        <i class="layui-icon">&#xe631;</i>
                                      </a>
                                      <a title="删除" onclick="member_del(this,${ls.id})" href="javascript:;">
                                        <i class="layui-icon">&#xe640;</i>
                                      </a>
                                    </td>
                                  </tr>    
         `);

                }

                getPageListForChaZhao(currt,conter,name,kaishi,zuihou);
            }else if(data.code=="0"){
                layer.msg("这个时间段是没有这个用户的");
            }else {
                layer.msg("网络故障");
            }

        },
        error:function(){
            layer.msg("网络故障");
        }
    })
}


//自己封装分页方法
function getPageListForChaZhao(currt,count,name,kaishi,zuihou){
    layui.use(['laypage','laydate','form'], function(){
        var laydate = layui.laydate;
        var  laypage = layui.laypage;
        var  form = layui.form;
        //分页
        laypage.render({
            elem: 'demo0'
            ,count: count //数据总数
            ,curr:currt
            ,layout: ['count', 'prev', 'page', 'next',  'refresh', 'skip']
            ,jump: function(obj, first){
                //obj包含了当前分页的所有参数，比如：
                console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
                console.log(obj.limit); //得到每页显示的条数

                //首次不执行
                if(!first){
                    //do something
                    $("#usershuju").empty();
                    getUserList(obj.curr,(obj.curr-1)*10,name,kaishi,zuihou);

                }
            }
        });
        form.render('checkbox');

        // 监听全选
        form.on('checkbox(checkall)', function(data){

            if(data.elem.checked){
                $('tbody input').prop('checked',true);
            }else{
                $('tbody input').prop('checked',false);
            }
            form.render('checkbox');
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#start' //指定元素
        });

        //执行一个laydate实例
        laydate.render({
            elem: '#end' //指定元素
        });


    });

    //分页方法

}