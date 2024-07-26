<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet"
href="${pageContext.request.contextPath}/css/style.css">
<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header.html" %>
<%@include file="../background.html" %>
<h1 class="toptitle">得点管理システム</h1>
<h2 class="subtitle">ログアウト</h2>
<%@ include file="sidebar.jsp" %>
  <style>
    p{
      text-align:center;
    }
  </style>
<p>すでにログアウトしています。</p>
<p><a href="menu.jsp">メニューに戻る</a>
または
<a href="login.jsp">再ログインはこちらから</a>
</p>
<%@include file="../footer.html" %>