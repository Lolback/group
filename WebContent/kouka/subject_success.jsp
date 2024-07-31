<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../background.html" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>登録完了</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <h1 class="toptitle">得点管理システム</h1>
    <h2 class="subtitle">科目登録完了</h2>
    <%@include file="sidebar.jsp" %>
    <div class="message-box">科目の登録が完了しました。</div>
    <div class="button-container">
    <a href="subject.jsp" class="btn btn-back">科目一覧に戻る</a>
    </div>
</body>
</html>


<%@include file="../footer.html" %>