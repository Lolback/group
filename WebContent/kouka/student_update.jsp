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
<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生編集</h2>
<form action="StudentUpdateExecute.action" method="get">
<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">

<div class="col-4">
<label class="form-label" for="student-f1-select">入学年度</label>
<%=request.getParameter("entYear") %>
<input type="hidden" name="f1" value=<%=request.getParameter("entYear") %>>
</div>

<div class="col-3">
<label class="form-label" for="student-name">学生番号</label>
<%=request.getParameter("no") %>
<input type="hidden" name="f2" value=<%=request.getParameter("no") %>>
</div>

<div class="col-4">
<label class="form-label" for="student-f3-select">クラス</label>
<select class="form-select" id="student-f3-select" name="f3">
<option value="0">--------</option>
<c:forEach var="num" items="${class_num_set}">
<option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
</c:forEach>
</select>
</div>


<div class="col-3">
<label class="form-label" for="student-f4-select">氏名</label>
<input type="text" class="form-control" id="f4" name="f4" value=<%=request.getParameter("name") %> required>
</div>

<div class="col-2 form-check text-center">
<label class="form-check-label" for="student-f5-select">在学中
<c:choose>
<c:when test="${isAttend == true}"><input type="checkbox" id="student-f5-check" name="f5" checked></c:when>
<c:otherwise><input type="checkbox" id="student-f5-check" name="f5"></c:otherwise>
</c:choose>
</label>
</div>

<div class="col-2 text-center">
<button class="btn btn-secondary" id="filter-button">更新</button>
</div>
<div class="mt-2 text-warning">${errors.get("f1")}</div>
</div>
</form>

</section>
</c:param>
</c:import>