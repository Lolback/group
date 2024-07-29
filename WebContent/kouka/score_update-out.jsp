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
  <style>
    p{
      text-align:center;
    }
  </style>
<p>更新が完了しました。<p>
<p><a href="ScoreList.action">成績管理に戻る</a>
または
<a href="menu.jsp">メニューに戻る</a>
</p>


<%@include file="../footer.html" %>