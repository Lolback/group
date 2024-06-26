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
<section class="me-4">
<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>
<form action="student_list.jsp" method="get">
<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">

<div class="col-4">
<label class="form-label" for="student-f1-select">入学年度</label>
<%=request.getParameter("entYer") %>
</div>

<div class="col-3">
<label class="form-label" for="student-name">学生番号</label>
<%=request.getParameter("no") %>
</div>


<div class="col-4">
<label class="form-label" for="student-f2-select">クラス</label>
<select class="form-select" id="student-f2-select" name="f2">
<option value="0">--------</option>
<c:forEach var="num" items="${class_num_set}">
<option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
</c:forEach>
</select>
</div>


<div class="col-3">
<label class="form-label" for="student-name">氏名</label>
<input type="text" class="form-control" id="student-name" name="studentName" value="${param.studentName}">
</div>

<div class="col-2 form-check text-center">
<label class="form-check-label" for="student-f3-check">在学中
<input type="checkbox" id="student-f3-check" name="f3" value="t" <c:if test="${!empty f3}">checked</c:if>/>
</label>
</div>

<div class="col-2 text-center">
<button class="btn btn-secondary" id="filter-button">変更</button>
</div>
<div class="mt-2 text-warning">${errors.get("f1")}</div>
</div>
</form>
<c:choose>
<c:when test="${not empty students}">
<table class="table table-hover">
<tr>
<th>クラス</th>
<th>氏名</th>
<th>フリガナ</th>
<th class="text-center">在学中</th>
<th></th>
<th></th>
</tr>
<c:forEach var="student" items="${students}">
<tr>
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