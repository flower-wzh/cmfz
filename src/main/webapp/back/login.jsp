<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set var="path">${pageContext.request.contextPath}</c:set>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>持明法舟后台</title>
    <link rel="stylesheet" href="${path}/static/vendors/iconfonts/mdi/css/materialdesignicons.min.css">
    <link rel="stylesheet" href="${path}/static/vendors/css/vendor.bundle.base.css">
    <link rel="stylesheet" href="${path}/static/boot/css/style.css">
    <script src="${path}/static/boot/js/jquery-3.4.1.min.js"></script>
</head>

<body>
<div class="container-scroller">
    <div class="container-fluid page-body-wrapper full-page-wrapper">
        <div class="content-wrapper d-flex align-items-center auth">
            <div class="row w-100">
                <div class="col-lg-4 mx-auto">
                    <div class="auth-form-light text-left p-5">
                        <div class="brand-logo">
                            <h2>持明法舟</h2>
                        </div>
                        <h4>你好!</h4>
                        <h6 class="font-weight-light">登录以继续.</h6>
                            <div class="form-group">
                                <input type="text" class="form-control form-control-lg" id="username" placeholder="Username">
                            </div>
                            <div class="form-group">
                                <input type="password" class="form-control form-control-lg" id="password" placeholder="Password">
                            </div>
                            <div class="mt-3">
                                <a class="btn btn-block btn-gradient-primary btn-lg font-weight-medium auth-form-btn" href="javascript:void(0);" id="login">登录</a>
                            </div>
                            <span style="color: red"><a id="error"></a></span>
                        <script>
                            $(function () {
                               $('#login').on('click',function () {
                                   var username = $('#username').val();
                                   var password = $('#password').val();
                                  $.ajax({
                                      url:'${path}/admin/login',
                                      data:{
                                          'username':username,
                                          'password':password
                                      },
                                      type:'POST',
                                      dataType:'text',
                                      success:function (data) {
                                          console.log(data)
                                          if(data == '"success"'){
                                             location.href = '${path}/back/index.jsp'
                                          }else {
                                            $('#error').html(data);
                                          }
                                      }
                                  }) ;
                               });
                            });
                        </script>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${path}/static/vendors/js/vendor.bundle.base.js"></script>
<script src="${path}/static/vendors/js/vendor.bundle.addons.js"></script>
<script src="${path}/static/boot/js/off-canvas.js"></script>
<script src="${path}/static/boot/js/misc.js"></script>
</body>

</html>

