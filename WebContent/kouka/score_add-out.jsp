<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet"
href="${pageContext.request.contextPath}/css/style.css">
<%@page contentType="text/html; charset=UTF-8" %>
<%@include file="../header.html" %>
<%@include file="../background.html" %>
<h1 class="toptitle">得点管理システム</h1>
<h2 class="subtitle">成績登録</h2>
  <style>
    p{
      text-align:center;
    }
  </style>

<p>登録が完了しました。<p>
<p><a href="menu.jsp">メニューに戻る</a>
さらに
<a href="ScoreList.action">追加の場合はこちらから</a>
</p>


<%@include file="../footer.html" %>