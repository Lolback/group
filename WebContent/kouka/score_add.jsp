<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet"
href="${pageContext.request.contextPath}/css/style.css">
<!DOCTYPE html>
<html>
<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.sql.*" %>
<%@include file="../background.html" %>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title>学生管理</title>
        <h1 class="toptitle green">得点管理システム</h1>
<meta charset="UTF-8">
<title>成績登録</title>
<h2 class="subtitle">成績登録</h2>
<style>
       table {
           width: 100%;
           border-collapse: collapse;
       }
       th, td {
           border: 1px solid black;
           padding: 8px;
           text-align: left;
       }
       th {
           background-color: #f2f2f2;
       }
</style>
</head>


<body>
<span>
<h2>科目名:
<form action="registerGrades" method="post">
<select class="form-select" id="student-f1-select" name="f10">
<option value="0">--------</option>
<c:forEach var="subjectName" items="${ent_year_set}">
<option value="${subjectName}" <c:if test="${subjectName == f10}">selected</c:if>>${subjectName}</option>
</c:forEach>
</select>
第
<select class="form-select" id="student-f1-select" name="f10">
<option value="0">--------</option>
<c:forEach var="subjectName" items="${ent_year_set}">
<option value="${times}" <c:if test="${times == f10}">selected</c:if>>${times}</option>
</c:forEach>
</select>
回</h2>
</span>



<table>
<thead>
<tr>
<th>入学年度</th>
<th>クラス</th>
<th>学生番号</th>
<th>氏名</th>
<th>点数</th>
</tr>
</thead>

<tbody>
<tr>



<td><select class="form-select" id="student-f1-select" name="f1">
<option value="0">--------</option>
<c:forEach var="year" items="${ent_year_set}">
<option value="${year}" <c:if test="${year == f1}">selected</c:if>>${year}</option>
</c:forEach>
</select></td>
<td><select class="form-select" id="student-f2-select" name="f2">
<option value="0">--------</option>
<c:forEach var="num" items="${class_num_set}">
<option value="${num}" <c:if test="${num == f2}">selected</c:if>>${num}</option>
</c:forEach>
</select></td>
<td><input type="text" class="form-control" id="student-number" name="studentNumber" value="${param.studentNumber}"pattern="^[0-9]{1,7}$" title="0から9999999の数値を入力してください"></td>
<td><input type="text" class="form-control" id="student-name" name="studentName" value="${param.studentName}"></td>

<td>

<input type="text" name="point_${student.studentNumber}" value="${student.point}" pattern="^[0-9]{1,3}$" title="0から100の数値を入力してください">
</td>
</tr>

</tbody>
</table>





<form action="searchAdd.jsp" method="post">
<input type="submit" value="登録して終了">
</form>
</body>
</html>