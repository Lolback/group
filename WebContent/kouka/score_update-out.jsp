<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet"
href="${pageContext.request.contextPath}/css/style.css">
<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header.html" %>
<%@include file="../background.html" %>
<h1 class="toptitle">得点管理システム</h1>
<h2 class="subtitle">成績登録</h2>
<%@ include file="sidebar.jsp" %>
<h1></h1>
<div class="message-box">登録が完了しました。</div>
<div class="button-container">
<a href="ScoreList.action" class="btn btn-back">成績管理に戻る</a>
<a href="menu.jsp" class="btn btn-back">メニューに戻る</a>
</div>

<%@include file="../footer.html" %>