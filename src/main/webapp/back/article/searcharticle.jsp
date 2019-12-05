<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
    <style>
        img{
            max-width: 300px;
            max-height: 200px;
        }
        tr{
            text-align: center;
        }
        td{
            text-align: center;
        }
    </style>
    <script>
        $(function () {
            //搜索按钮事件
            $("#queryBtn").on('click',function(){
                //获取数据
                var message = $("#message").val();
                $.ajax({
                    url:"${path}/article/search",
                    type:"post",
                    datatype:"json",
                    data:{
                        "message":message
                    },
                    success:function(data){
                        $("#result").html("");
                        $("#result").append("<tr><td>标题</td><td>内容</td><td>创建日期</td></tr>");
                        $.each(data.list,function(index,article){
                            $("#result").append("<tr>" +
                                "<td>"+article.title+"</td>" +
                                "<td>"+article.content+"</td>" +
                                "<td>"+article.createDate+"</td>" +
                                "</tr>");
                        });
                    }
                });
            });
        });

    </script>

<div class="col-sm-12">
    <div align="center">
        <div class="input-group" style="width: 300px">
            <input id="message" type="text" class="form-control" placeholder="请输入搜索内容">
            <span class="input-group-btn" id="basic-addon2">
            <button type="button" class="btn btn-info" id="queryBtn">点击搜索</button>
        </span>
        </div><br>
    </div>


    <div class="panel panel-default">
        <!-- 标题 -->
        <div class="panel-heading">搜索结果</div>

        <!-- 表格 -->
        <table class="table" id="result"></table>

    </div>
</div>

