<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet"
href="${pageContext.request.contextPath}/css/style.css">
<%@page contentType="text/html; charset=UTF-8" %>
<title>ログアウト</title>
<%@include file="../header.html" %>
<%@include file="../background.html" %>
<h1 class="toptitle">得点管理システム</h1>
<h2 class="subtitle">ログアウト</h2>
<%@ include file="sidebar.jsp" %>
<h1></h1>
  <style>
    p{
      text-align:center;
    }
  </style>

<p>ログアウトしました。<p>
<a href="login.jsp">再ログインはこちらから</a>
</p>

<%@include file="../footer.html" %>