   $(document).ready(function() {
	   console.log(222);
      $.ajax({
      url : "http://localhost:8080/pblog/admin/returnOneUser",//请求地址
      data: {"id":"2"},
      dataType : "json",//数据格式

      type : "post",//请求方式
      async : false,//是否异步请求
      success : function(data) {   //如何发送成功
    	  console.log(data);
          //var ls = data[i];
          $("#L_email").attr("value","3")

     }, 
 })
 })