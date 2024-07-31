<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../background.html" %>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <link rel="icon" type="image/x-icon" href="/favicon.ico" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
    <title>科目削除完了</title>
</head>
<body>
    <c:import url="/common/base.jsp">
        <c:param name="title">
            <h1 class="toptitle">科目管理システム</h1>
        </c:param>

        <c:param name="scripts"></c:param>

        <c:param name="content">
            <section class="me-4">
                <h2 class="subtitle">科目削除完了</h2>
                <div class="alert alert-success" role="alert">
                    科目情報が正常に削除されました。
                </div>
                <div class="button-container">
                    <a href="subject.jsp" class="btn btn-back">科目一覧に戻る</a>
                </div>
            </section>
        </c:param>
    </c:import>
</body>
</html>

<%@include file="../footer.html" %>