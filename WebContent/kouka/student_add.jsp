<%-- 学生一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../background.html" %>
<% request.setCharacterEncoding("UTF-8"); %>
<%@ page import="java.util.List" %>
<% List<String> ent_year_set = (List<String>)request.getAttribute("ent_year_set"); %>
<% List<String> class_num_set = (List<String>)request.getAttribute("class_num_set"); %>
<% boolean duplicate_flag = (boolean)request.getAttribute("duplicate_flag"); %>
<% System.out.println(duplicate_flag); %>

<c:import url="/common/base.jsp">
<c:param name="title">
<h1 class="toptitle">得点管理システム</h1>
<h2 class="subtitle">新規登録</h2>
</c:param>
<c:param name="scripts"></c:param>
<c:param name="content">
<%@ include file="sidebar.jsp" %>
<section class="me-4">
<form action="StudentCreateExecute.action" method="get">
<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">

<div class="col-4">
<label class="form-label" for="student-f1-select">入学年度</label>
<select class="form-select" id="student-f1-select" name="f1" required>
<option value="" >-----</option>
<c:forEach var="ent_year_set" items="<%=ent_year_set %>">
<option value="${ent_year_set}" >${ent_year_set}</option>
</c:forEach>
</select>
</div>

<div class="col-3">
<label class="form-label" for="student-f2-select">学生番号</label>
<input type="text" class="form-control" id="f2" name="f2" required>
</div>
<% if (duplicate_flag == true) { %>
	<font color="Gold">学生番号が重複しています</font>
<% } %>


<div class="col-4">
<label class="form-label" for="student-f3-select">クラス</label>
<select class="form-select" id="student-f3-select" name="f3">
<c:forEach var="num" items="${class_num_set}">
<option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
</c:forEach>
</select>
</div>


<div class="col-3">
<label class="form-label" for="student-f4-select">氏名</label>
<input type="text" class="form-control" id="f4" name="f4" required>
</div>


<div class="col-2 form-check text-center">
<label class="form-check-label" for="student-f5-select">在学中
<input type="checkbox" id="student-f5-check" name="f5">
</label>
</div>

<div class="col-2 text-center">
<button class="btn btn-secondary" id="filter-button">登録</button>
</div>
<div class="mt-2 text-warning">${errors.get("f1")}</div>
</div>
</form>

<a href="StudentList.action">学生一覧に戻る</a>

</section>
</c:param>
</c:import>
<%@include file="../footer.html" %>