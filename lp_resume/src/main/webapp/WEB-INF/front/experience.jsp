<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html lang="en" class="crt crt-nav-on crt-nav-type1 crt-main-nav-on crt-sidebar-on crt-layers-2">
<head>
    <title>lpruoyu-工作经验</title>
    <%@include file="common/head.jsp" %>
</head>
<body class="">
<div class="crt-wrapper">
    <%@include file="common/header.jsp" %>
    <div id="crt-container" class="crt-container">
        <%@include file="common/crtnav.jsp" %>
        <div class="crt-container-sm">
            <div class="crt-paper-layers">
                <div class="crt-paper clear-mrg">
                    <div class="crt-paper-cont paper-padd clear-mrg">
                        <h2 class="title-lg text-upper padd-box">工作经验</h2>
                        <div class="education">
                            <c:forEach var="experience" items="${experiences}">
                                <div class="education-box">
                                    <time class="education-date">
                                    <span>
                                        <strong class="text-upper"><f:formatDate value="${experience.beginDay}"
                                                                                 pattern="yyyy"/></strong>/<f:formatDate
                                            value="${experience.beginDay}" pattern="MM"/> -
                                        <strong><f:formatDate value="${experience.endDay}"
                                                              pattern="yyyy"/></strong>/<f:formatDate
                                            value="${experience.endDay}" pattern="MM"/>
                                    </span>
                                    </time>
                                    <h3>${experience.company.name}</h3>
                                    <c:if test="${not empty experience.company.logo}">
                                        <div class="education-logo">
                                            <img src="${ctx}/${experience.company.logo}" alt="">
                                        </div>
                                    </c:if>
                                    <span class="education-company">${experience.job}</span>
                                    <p>${experience.intro}</p>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <!-- .crt-paper -->
            </div><!-- .crt-paper-layers -->
        </div><!-- .crt-container-sm -->
    </div>
    <!-- .crt-container -->
    <footer id="crt-footer" class="crt-container-lg">
        <div class="crt-container">
            <div class="crt-container-sm clear-mrg text-center"><p>${website.footer}</p></div>
        </div><!-- .crt-container -->
    </footer><!-- #crt-footer -->
    <%@ include file="common/svg.jsp" %>
</div><!-- .crt-wrapper --><!-- Scripts -->
<%@include file="common/footer.jsp" %>
</body>
</html>