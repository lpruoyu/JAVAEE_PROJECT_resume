<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>LP简历管理-登录</title>
    <%@ include file="/WEB-INF/common/head.jsp" %>
</head>

<body class="login-page">
<div class="login-box">
    <div class="logo">
        <a href="javascript:void(0);"><b>LP简历管理</b></a>
        <small>您身边最好用的简历助手</small>
    </div>
    <div class="card">
        <div class="body">
            <form action="${ctx}/user/login" class="form-validation" method="post">
                <div class="msg">赶紧登录吧</div>
                <div class="input-group form-group form-float">
                        <span class="input-group-addon">
                            <i class="material-icons">email</i>
                        </span>
                    <div class="form-line">
                        <input type="email"
                               class="form-control" name="email" maxlength="50" placeholder="邮箱" required
                               autofocus>
                    </div>
                </div>
                <div class="input-group form-group">
                        <span class="input-group-addon">
                            <i class="material-icons">lock</i>
                        </span>
                    <div class="form-line">
                        <input type="text" style="display: none" name="password">
                        <input type="password"
                               class="form-control"
                               id="originalPassword" maxlength="20"
                               placeholder="密码"
                               required>
                    </div>
                </div>
                <div class="input-group form-group captcha">
                        <span class="input-group-addon">
                            <i class="material-icons">security</i>
                        </span>
                    <div class="form-line">
                        <input type="text" class="form-control" name="captcha" placeholder="验证码"
                               required
                        >
                    </div>
                    <img id="captcha" src="${ctx}/user/captcha" alt="验证码">
                </div>
                <div class="row">
                    <div class="col-xs-8 p-t-5">
                        <input type="checkbox" name="rememberme" id="rememberme" class="filled-in chk-col-pink">
                        <label for="rememberme">记住密码</label>
                    </div>
                    <div class="col-xs-4">
                        <button class="btn btn-block bg-pink waves-effect" type="submit">登录</button>
                    </div>
                </div>
                <%--                不支持注册功能                   --%>
                <%--                <div class="row m-t-15 m-b--20">--%>
                <%--                    <div class="col-xs-6">--%>
                <%--                        <a href="WEB-INF/register.html">现在注册</a>--%>
                <%--                    </div>--%>
                <%--                </div>--%>
            </form>
        </div>
    </div>
</div>

<script src="${ctx}/asset/plugin/jquery/jquery.min.js"></script>
<script src="${ctx}/asset/plugin/bootstrap/bootstrap.min.js"></script>
<script src="${ctx}/asset/plugin/node-waves/waves.js"></script>
<script src="${ctx}/asset/plugin/jquery-validation/jquery.validate.js"></script>
<script src="${ctx}/asset/plugin/jquery-validation/messages_zh.js"></script>

<!-- Custom Js -->
<script src="${ctx}/asset/admin/js/default.js"></script>
<script src="${ctx}/asset/admin/js/main.js"></script>
<script src="${ctx}/asset/plugin/Javascript-md5/md5.min.js"></script>
<script src="${ctx}/asset/plugin/sweetalert/sweetalert.min.js"></script>
<script>
    addValidatorRules('.form-validation', function () {

        $('[name=password]').val(md5($('#originalPassword').val()))

        // 先弹框
        // 利用该弹框，既能阻止用户多次点击登录发送异步请求，又能提升用户体验
        swal({
            title: '正在登录中...',
            text: ' ',
            icon: 'info',
            button: false,
            closeOnClickOutside: false
        })

        // 利用AJAX发送请求给服务器
        $.post('${ctx}/user/login', {
            email: $('[name=email]').val(),
            password: $('[name=password]').val(),
            captcha: $('[name=captcha]').val()
        }, function (data) {
            if (data.success) {
                location.href = '${ctx}/user/admin'
            } else {
                swal({
                    title: "提示",
                    text: data.message,
                    icon: 'error',
                    dangerMode: true,
                    buttons: false,
                    timer: 1500
                })
            }
        }, 'json')

        return false
    })

    $('#captcha').click(function () {
        $(this).attr('src', '${ctx}/user/captcha?time=' + new Date().getTime())
        <%--$(this).hide().attr('src', '${ctx}/user/captcha?time=' + new Date().getTime()).fadeIn()--%>
    })
</script>
</body>

</html>