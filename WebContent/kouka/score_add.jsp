<!DOCTYPE html>
<html>
<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../background.html" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>成績登録</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <h1 class="toptitle">得点管理システム</h1>
    <h2 class="subtitle">成績登録</h2>
    <%@ include file="sidebar.jsp" %>

    <!-- メッセージ表示 -->
    <%
        String updateMessage = (String) request.getSession().getAttribute("updateMessage");
        if (updateMessage != null && !updateMessage.isEmpty()) {
    %>
        <div class="alert alert-success" role="alert">
            <%= updateMessage %>
        </div>
    <%
            request.getSession().removeAttribute("updateMessage");
        }
    %>

    <!-- 成績登録フォーム -->
    <form method="post" action="ScoreCreate.action">
        <table class="table table-hover">
            <tr>
                <th>入学年度</th>
                <th>クラス</th>
                <th>学生番号</th>
                <th>氏名</th>
                <th>科目</th>
                <th>点数</th>
                <th>操作</th>
            </tr>
            <tr>
                <td><input type="text" name="entYear" size="3"></td>
                <td><input type="text" name="classNum" size="3"></td>
                <td><input type="text" name="no" size="3"></td>
                <td><input type="text" name="name" size="3"></td>
                <td><input type="text" name="subject" size="3"></td>
                <td><input type="text" name="point" size="3"></td>
                <td><input type="submit" value="登録"></td>
            </tr>
        </table>
    </form>

    <%@include file="../footer.html" %>
</body>
</html>
