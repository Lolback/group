<%-- 学生一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../background.html" %>
<% request.setCharacterEncoding("UTF-8"); %>
<c:import url="/common/base.jsp">
<c:param name="title">
<h1 class="toptitle">得点管理システム</h1>
<h2 class="subtitle">登録完了</h2>
</c:param>
<c:param name="scripts"></c:param>
<c:param name="content">
<%@ include file="sidebar.jsp" %>
<section class="me-4">

<div class="message-box">登録が完了しました</div>

<h1></h1>

<div class="button-container">
<a href="StudentCreate.action" class="btn btn-back">戻る</a>
<a href="StudentList.action" class="btn btn-stn">学生一覧</a>
</div>

<c:choose>
<c:when test="${not empty students}">
<table class="table table-hover">
<tr>
<th>入学年度</th>
<th>学生番号</th>
<th>クラス</th>
<th>氏名</th>
<th>フリガナ</th>
<th class="text-center">在学中</th>
<th></th>
<th></th>
</tr>
<c:forEach var="student" items="${students}">

<tr>
<td>${student.entYear}</td>
<td>${student.no}</td>
<td>${student.name}</td>
<td>${student.furigana}</td>
<td>${student.classNum}</td>
<td class="text-center">
<c:choose>
<c:when test="${student.attend}">
                                           〇
</c:when>
<c:otherwise>
                                           ×
</c:otherwise>
</c:choose>
</td>
<td><a href="StudentUpdate.action?no=${student.no}">変更</a></td>
<td><a href="StudentDelete.action?no=${student.no}">削除</a></td>
</tr>
</c:forEach>
</table>
</c:when>
<c:otherwise>

</c:otherwise>
</c:choose>
</section>
</c:param>
</c:import>
<%@include file="../footer.html" %>
