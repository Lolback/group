<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/css/bootstrap.min.css" />
    <title>学生削除確認</title>
</head>
<body>
    <c:import url="/common/base.jsp">
        <c:param name="title">
            <h1 class="toptitle">得点管理システム</h1>
        </c:param>

        <c:param name="scripts"></c:param>

        <c:param name="content">
            <section class="me-4">
                <h2 class="subtitle">学生削除確認</h2>
                <div class="alert alert-warning" role="alert">
                    本当にこの学生情報を削除してもよろしいですか？
                </div>
                <div class="my-2 text-center">
                    <p>学生番号: ${no}</p>
                    <p>氏名: ${name}</p>
                    <p>クラス: ${classNum}</p>
                    <p>入学年度: ${entYear}</p>
                    <p>在学中:
                        <c:choose>
                            <c:when test="${isAttend}">〇</c:when>
                            <c:otherwise>×</c:otherwise>
                        </c:choose>
                    </p>
                    <form action="StudentDeleteExecute.action" method="post">
                        <input type="hidden" name="no" value="${no}" />
                        <button type="submit" class="btn btn-danger">削除</button>
                        <a href="StudentList.action" class="btn btn-secondary">キャンセル</a>
                    </form>
                </div>
            </section>
        </c:param>
    </c:import>
</body>
</html>
