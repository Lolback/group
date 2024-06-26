<%-- 学生一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% request.setCharacterEncoding("UTF-8"); %>
<c:import url="/common/base.jsp">
<c:param name="title">
<h1 class="toptitle">得点管理システム</h1>
</c:param>
<c:param name="scripts"></c:param>
<c:param name="content">
<%@ include file="sidebar.jsp" %>
<section class="me-4">
<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">新規登録</h2>

<div>変更が完了しました</div>

<a href="student_list.jsp">学生一覧</a>

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