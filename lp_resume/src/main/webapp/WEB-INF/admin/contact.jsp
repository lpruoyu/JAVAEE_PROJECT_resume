<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <title>LP简历管理-留言信息</title>
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
                        <h2>留言信息</h2>
                    </div>
                    <form name="list-form" method="post"
                          action="${ctx}/contact/admin">                    <%--                    pageIndex--%>

                        <input name="pageIndex" type="text" style="display: none" value="${result.search.pageIndex}">

                        <div class="body table-responsive">
                            <div class="menus">
                                <div class="search-box input-group">
                                    <select name="alreadyRead">
                                        <option value="2">全部</option>
                                        <option value="1">已读</option>
                                        <option value="0">未读</option>
                                    </select>
                                    <div class="form-line  datetime-input">
                                        <input type="date" name="beginDay" class="form-control"
                                               value="<f:formatDate value="${result.search.beginDay}" pattern="yyyy-MM-dd"/>"
                                               placeholder="开始日期">
                                    </div>
                                    <div class="form-line  datetime-input">
                                        <input type="date" name="endDay"

                                               value="<f:formatDate value="${result.search.endDay}" pattern="yyyy-MM-dd"/>"

                                               class="form-control" placeholder="结束日期">
                                    </div>
                                    <span class="input-group-addon">
                                        <i class="material-icons">search</i>
                                    </span>
                                    <div class="form-line keyword">
                                        <input type="text" name="keyWord" value="${result.search.keyWord}"
                                               class="form-control" placeholder="主题、内容等">
                                    </div>
                                    <button type="submit" class="btn bg-blue waves-effect btn-sm">搜索</button>
                                </div>
                            </div>
                            <c:if test="${not empty result.contacts}">

                                <table class="table table-bordered table-hover table-striped">
                                    <thead>
                                    <tr>
                                        <th>已读</th>
                                        <th>名称</th>
                                        <th>邮箱</th>
                                        <th>日期</th>
                                        <th>主题</th>
                                        <th>内容</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    <c:forEach items="${result.contacts}" var="contact">
                                        <tr>
                                            <td>
                                                <div class="switch">
                                                    <label>
                                                        <c:choose>
                                                            <c:when test="${contact.alreadyRead}">
                                                                <input id="read-${contact.id}" type="checkbox" checked
                                                                       disabled>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <input id="read-${contact.id}" type="checkbox" disabled>
                                                            </c:otherwise>
                                                        </c:choose>
                                                        <span class="lever switch-col-blue"></span>
                                                    </label>
                                                </div>
                                            </td>
                                            <td>${contact.name}</td>
                                            <td>${contact.email}</td>
                                            <td>
                                                <f:formatDate value="${contact.createdTime}"/>
                                            </td>
                                            <td>${contact.subject}</td>
                                            <td>${contact.comment}</td>
                                            <td>
                                                <button type="button" class="btn bg-blue waves-effect btn-xs"
                                                        onclick="view(${contact.json})">
                                                    <i class="material-icons">edit</i>
                                                    <span>查看</span>
                                                </button>
                                            </td>
                                        </tr>
                                    </c:forEach>

                                    </tbody>
                                </table>
                                <div class="page-box">
                                    <div class="page-size">
                                        共${result.totalSize}条 共${result.totalPage}页，每页
                                        <select name="pageSize">
                                            <option value="5">5</option>
                                            <option value="10">10</option>
                                            <option value="20">20</option>
                                            <option value="40">40</option>
                                            <option value="80">80</option>
                                            <option value="160">160</option>
                                        </select>
                                        条
                                    </div>
                                    <nav>
                                        <ul class="pagination">
                                            <li id="prevPage">
                                                <a onclick="go(${result.search.pageIndex - 1})">
                                                    <i class="material-icons">chevron_left</i>
                                                </a>
                                            </li>

                                                <%--                                                                                <li class="active"><a>1</a></li>--%>
                                                <%--                                                                                <li><a href="javascript:void(0);" class="waves-effect">2</a></li>--%>
                                                <%--                                                                                <li><a href="javascript:void(0);" class="waves-effect">3</a></li>--%>
                                                <%--                                                                                <li><a href="javascript:void(0);" class="waves-effect">4</a></li>--%>
                                                <%--                                                                                <li><a href="javascript:void(0);" class="waves-effect">5</a></li>--%>

                                            <c:forEach var="idx" begin="1" end="${result.totalPage}">

                                                <li
                                                        <c:if test="${idx == result.search.pageIndex}">class="active" </c:if>
                                                ><a onclick="go(${idx})"
                                                    class="waves-effect">${idx}</a></li>

                                            </c:forEach>

                                            <li id="nextPage">
                                                <a onclick="go(${result.search.pageIndex + 1})" class="waves-effect">
                                                    <i class="material-icons">chevron_right</i>
                                                </a>
                                            </li>
                                        </ul>
                                    </nav>
                                </div>
                            </c:if>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>
</section>

<!--  view-form-box  -->
<div class="modal fade" id="view-form-box" style="display: none;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">留言详情</h4>
            </div>
            <div class="modal-body">
                <form method="post">
                    <div class="row">
                        <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3 form-control-label">
                            <label for="name">名称</label>
                        </div>
                        <div class="col-lg-10 col-md-10 col-sm-9 col-xs-9">
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" id="name" name="name" maxlength="20" class="form-control"
                                           placeholder="名称"
                                           disabled>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3 form-control-label">
                            <label for="email">邮箱</label>
                        </div>
                        <div class="col-lg-10 col-md-10 col-sm-9 col-xs-9">
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="email" id="email" name="email" maxlength="50" class="form-control"
                                           placeholder="邮箱"
                                           disabled>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3 form-control-label">
                            <label for="createdTime">时间</label>
                        </div>
                        <div class="col-lg-10 col-md-10 col-sm-9 col-xs-9">
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" id="createdTime" name="createdTime" class="form-control"
                                           placeholder="时间"
                                           disabled>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3 form-control-label">
                            <label for="subject">主题</label>
                        </div>
                        <div class="col-lg-10 col-md-10 col-sm-9 col-xs-9">
                            <div class="form-group">
                                <div class="form-line">
                                    <input type="text" id="subject" name="subject" class="form-control"
                                           placeholder="主题" maxlength="20"
                                           disabled>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-lg-2 col-md-2 col-sm-3 col-xs-3 form-control-label">
                            <label for="comment">内容</label>
                        </div>
                        <div class="col-lg-10 col-md-10 col-sm-9 col-xs-9">
                            <div class="form-group">
                                <div class="form-line">
                                        <textarea name="comment" id="comment"
                                                  maxlength="1000"
                                                  cols="30" rows="5" class="form-control no-resize"
                                                  placeholder="内容" disabled></textarea>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-lg-offset-2 col-md-offset-2 col-sm-offset-3 col-xs-offset-3">
                            <button class="btn btn-info waves-effect m-l-15" data-dismiss="modal">关闭</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%@include file="common/footer.jsp" %>

<script>

    addValidatorRules('.form-validation')
    $('.contact').addClass('active')

    const $box = $('#view-form-box')
    const $form = $box.find('form')
    let $alreadyRead = $('[name=alreadyRead]');
    let $pageSize = $('[name=pageSize]');

    function change() {
        $('[name=list-form]').submit()
    }

    if (${result.search.pageIndex} == 1
    )
    {
        $('#prevPage').addClass('disabled')
    }

    if (${result.search.pageIndex} == ${result.totalPage}) {
        $('#nextPage').addClass('disabled')
    }

    if (${result.totalPage} == 1
    )
    {
        $('#nextPage').addClass('disabled')
        $('#prevPage').addClass('disabled')
    }

    function go(pageIndex) {
        if (pageIndex > ${result.totalPage} || pageIndex < 1) {
            return
        }
        $('[name=pageIndex]').val(pageIndex)
        change()
    }


    $alreadyRead.change(change)
    $pageSize.change(change)

    $alreadyRead.val('${result.search.alreadyRead}')
    $pageSize.val('${result.search.pageSize}')

    function view(json) {
        $form[0].reset()
        for (const k in json) {
            $form.find('[name=' + k + ']').val(json[k])
        }
        $.getJSON('${ctx}/contact/read', {id: json.id}, function (data) {
            if (data.success) {
                $('#read-' + json.id).attr('checked', 'checked')
            } else {

            }
        })
        $box.modal()
    }

    const $set = $(".table tbody tr input[type=checkbox]")
    const $removeAll = $('.table-responsive .removeAll')
    $('.table thead th input[type=checkbox]').change(function () {
        let checked = $(this).is(":checked")
        if (checked) {
            $set.each(function () {
                $(this).prop("checked", true)
                $(this).parents('tr').addClass("active")
            })
            $removeAll.removeClass('disabled')
            $removeAll.prop('disabled', false)
        } else {
            $set.each(function () {
                $(this).prop("checked", false)
                $(this).parents('tr').removeClass("active")
            })
            $removeAll.addClass('disabled')
            $removeAll.prop('disabled', true)
        }
    })
    $set.change(function () {
        $(this).parents('tr').toggleClass("active")
        if ($('.table tbody tr input[type=checkbox]:checked').length > 0) {
            $removeAll.removeClass('disabled')
            $removeAll.prop('disabled', false)
        } else {
            $removeAll.addClass('disabled')
            $removeAll.prop('disabled', true)
        }
    })
</script>

</body>

</html>
