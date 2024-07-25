<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<title>ログアウト画面</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header.html" %>
<%@include file="../background.html" %>

<h1 class="toptitle">得点管理システム</h1>
<h2 class="subtitle">ログアウト</h2>
<%@ include file="sidebar.jsp" %>

<p>ログアウトしますか？</p>
<p><a href="Logout.action">ログアウト</a></p>

<%@include file="../footer.html" %>