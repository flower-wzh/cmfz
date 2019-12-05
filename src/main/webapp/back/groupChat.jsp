<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: shun
  Date: 2019/12/2
  Time: 下午7:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" pageEncoding="UTF-8" %>

<html>
<head>
    <c:set var="path" value="${pageContext.request.contextPath}" scope="page"/>
    <title>Title</title>
    <link rel="stylesheet" href="${path}/static/boot/css/bootstrap.min.css">
    <script src="${path}/static/boot/js/jquery-3.4.1.min.js"></script>
    <script src="${path}/static/boot/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script>
        let nameValue = "用户"+new Date().getTime();
        let goEasy = new GoEasy({
            host:'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
            appkey: "BC-fb7661a2d2ac4e84a56093683db351c5", //替换为您的应用appkey
        });

        $(function () {
            goEasy.subscribe({
                channel: "groupChat", //替换为您自己的channel
                onMessage: function (message) {
                    let m = JSON.parse(message.content);
                    let $span1 = $("<span>").text(m.name);
                    let $span2 = $("<span>").text(m.message);
                    let $div = $("<div/>").css("width","100%");
                    if(m.name==nameValue){
                        $div.append($span2).append(":").append($span1.addClass("label label-info")).css("text-align","right");
                        $("#getDiv").append($div);
                    }else{
                        $div.append($span1.addClass("label label-primary")).append(":").append($span2);
                        $("#getDiv").append($div);
                    }
                }
            });
            $("#nameA").text(nameValue);
            $("#changeBtn").click(function () {
                let name = $("#nickName").val();
                if(name==""){
                    alert("请输入名称！")
                }else{
                    nameValue = name;
                    $("#nickName").val("");
                    $("#nameA").text(name);
                }
            });
            $("#sendBtn").click(function () {
                let text = $("#sendText").val();
                $("#sendText").val("");
                if(text!=""){
                    goEasy.publish({
                        channel: "groupChat", //替换为您自己的channel
                        message: `{"name":"`+nameValue+`","message":"`+text+`"}` //替换为您想要发送的消息内容
                    });
                }else{
                    $("#sendText").attr("placeholder","请输入文字").focus();
                }
            });
        });
    </script>
</head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">群聊系统</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <form class="navbar-form navbar-right">
                <div class="form-group">
                    <input type="text" id="nickName" class="form-control" placeholder="请输入昵称">
                </div>
                <button type="button" id="changeBtn" class="btn btn-default">更换</button>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <li><a id="nameA" href="javascript:void(0);"></a></li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<div class="container" >
    <div class="row" style="height: 60%" data-spy="scroll">
        <div id="getDiv" class="col-sm-8 col-sm-offset-1 text-left">

        </div>
    </div>
    <div class="row">
        <div class="col-sm-10">
            <textarea id="sendText" class="form-control" rows="3"></textarea>
        </div>
        <div class="col-sm-2">
            <button id="sendBtn" class="btn btn-info" >发送</button>
        </div>
    </div>
</div>

</body>
</html>
