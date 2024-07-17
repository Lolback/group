<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    // 現在のURLを取得
    String currentUrl = request.getRequestURI();
%>
<nav id="sidebar">
    <ul>
        <li class="<%= currentUrl.endsWith("menu.jsp") ? "current" : "" %>"><a href="menu.jsp">メニュー</a></li>
        <li class="<%= currentUrl.endsWith("StudentList.action") ? "current" : "" %>"><a href="StudentList.action">学生管理</a></li>
        <li class="<%= currentUrl.endsWith("test_list.jsp") ? "current" : "" %>"><a href="test_list.jsp">成績管理</a></li>
        <li class="<%= currentUrl.endsWith("score_add.jsp") ? "current" : "" %>"><a href="score_add.jsp">成績登録</a></li>
        <li class="<%= currentUrl.endsWith("score_view.jsp") ? "current" : "" %>"><a href="score_view.jsp">成績参照</a></li>
        <li class="<%= currentUrl.endsWith("subject.jsp") ? "current" : "" %>"><a href="subject.jsp">科目管理</a></li>
    </ul>
</nav>
