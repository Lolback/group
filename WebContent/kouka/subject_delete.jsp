<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <link rel="icon" type="image/x-icon" href="/favicon.ico" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
    <title>科目削除確認</title>
</head>
<body>
    <c:import url="/common/base.jsp">
        <c:param name="title">
            <h1 class="toptitle">科目管理システム</h1>
        </c:param>

        <c:param name="scripts"></c:param>

        <c:param name="content">
            <section class="me-4">
                <h2 class="subtitle">科目削除確認</h2>
                <div class="alert alert-warning" role="alert">
                    本当にこの科目情報を削除してもよろしいですか？
                </div>
                <div class="my-2 text-center">
                    <p>科目コード: <%= request.getParameter("subjectCode") %></p>
                    <p>学校コード: <%= request.getParameter("schoolCode") %></p>
                    <p>科目名: <%= request.getParameter("subjectName") %></p>
                    <form action="SubjectDeleteExecute.action" method="post">
                        <input type="hidden" name="subjectCode" value="<%= request.getParameter("subjectCode") %>" />
                        <input type="hidden" name="schoolCode" value="<%= request.getParameter("schoolCode") %>" />
                        <input type="hidden" name="subjectName" value="<%= request.getParameter("subjectName") %>" />
                        <button type="submit" class="btn btn-danger">削除</button>
                        <a href="subject.jsp">キャンセル</a>
                    </form>
                </div>
            </section>
        </c:param>
    </c:import>
</body>
</html>
