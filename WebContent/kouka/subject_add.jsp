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
<h2 class="subtitle">科目追加</h2>
<%@ include file="sidebar.jsp" %>
<section class="me-4">
<form action="add_subject_process.jsp" method="POST" accept-charset="UTF-8">
<div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">

<div class="col-4">
<label for="schoolCode">学校コード:</label>
<input type="text" id="schoolCode" name="schoolCode" required>
</div>

<div class="col-4">
<label for="subjectCode">科目コード:</label>
<input type="text" id="subjectCode" name="subjectCode" required>
</div>


<div class="col-4">
<label for="subjectName">科目名:</label>
<input type="text" id="subjectName" name="subjectName" required>
</div>

<div class="col-1 text-center">
<button type="submit">登録</button>
</div>
<div class="mt-2 text-warning">${errors.get("f1")}</div>
</div>
</form>
<a href="subject.jsp">科目一覧に戻る</a>
</section>
</c:param>
</c:import>
<%@include file="../footer.html" %>