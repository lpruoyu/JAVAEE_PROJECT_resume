<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh">

<head>
    <title>LP简历管理-修改密码</title>
    <%@include file="common/head.jsp" %>
</head>

<body class="theme-blue">

<%@include file="common/top.jsp" %>

<section class="content">
    <div class="container-fluid">
        <div class="row">
            <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                <div class="card">
                    <div class="header">
                        <h2>修改密码</h2>
                    </div>
                    <div class="body">
                        <form action="${ctx}/user/updatePassword" class="form-validation" method="post">
                            <input type="text" style="display: none" name="newPassword">
                            <input type="text" style="display: none" name="oldPassword">
                            <div class="row">
                                <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3 form-control-label">
                                    <label for="oldPassword">旧密码</label>
                                </div>
                                <div class="col-lg-10 col-md-10 col-sm-9 col-xs-9">
                                    <div class="form-group">
                                        <div class="form-line">
                                            <input type="password"
                                                   id="oldPassword" maxlength="20"
                                                   class="form-control"
                                                   placeholder="旧密码"
                                                   required>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3 form-control-label">
                                    <label for="newPassword">新密码</label>
                                </div>
                                <div class="col-lg-10 col-md-10 col-sm-9 col-xs-9">
                                    <div class="form-group">
                                        <div class="form-line">
                                            <input type="password"
                                                   id="newPassword" maxlength="20"
                                                   class="form-control"
                                                   placeholder="新密码"
                                                   required>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3 form-control-label">
                                    <label for="oldPassword">确认密码</label>
                                </div>
                                <div class="col-lg-10 col-md-10 col-sm-9 col-xs-9">
                                    <div class="form-group">
                                        <div class="form-line">
                                            <input type="password" maxlength="20"
                                                   class="form-control"
                                                   placeholder="确认密码"
                                                   id="againPassword"
                                                   required>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-lg-offset-2 col-md-offset-2 col-sm-offset-3 col-xs-offset-3">
                                    <button id="submitButton" class="btn btn-primary waves-effect m-l-15" type="submit">
                                        保存
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<%@include file="common/footer.jsp" %>
<script src="${ctx}/asset/plugin/Javascript-md5/md5.min.js"></script>

<script>
    $('.password').addClass('active')
    $('#submitButton').click(function () {
        const oldPasswordVal = $('#oldPassword').val()
        const newPasswordVal = $('#newPassword').val()
        const againPasswordVal = $('#againPassword').val()
        if (oldPasswordVal !== '' && newPasswordVal !== '' && againPasswordVal !== '') {
            if (newPasswordVal === againPasswordVal) {
                $('[name=newPassword]').val(md5(newPasswordVal))
                $('[name=oldPassword]').val(md5(oldPasswordVal))
                return true
            }
        }
        return false
    })
</script>
</body>

</html>
