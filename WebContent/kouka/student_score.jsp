<!DOCTYPE html>
<html>
<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.sql.*" %>
<%@include file="../background.html" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>科目管理</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<h1 class="subtitle">成績管理</h1>
<table border="1">
<tr>
<th>入学年度</th> <!-- ID: 2, 検索条件項目のヘッダー(固定値) -->
<th>クラス</th> <!-- ID: 3, 検索条件項目のヘッダー(固定値) -->
<th>科目</th> <!-- ID: 4, 検索条件項目のヘッダー(固定値) -->
<th>回目</th> <!-- ID: 5, 検索条件項目のヘッダー(固定値) -->
</tr>
<tr>
<td>
<select name="f1"> <!-- ID: 6, 入学年度セレクトボックス -->
<c:forEach var="year" items="${yearList}">
<option value="${year}">${year}</option>
</c:forEach>
</select>
</td>
<td>
<select name="f2"> <!-- ID: 7, クラスセレクトボックス -->
<c:forEach var="num" items="${classList}">
<option value="${num}">${num}</option>
</c:forEach>
</select>
</td>
<td>
<select name="f3"> <!-- ID: 8, 科目セレクトボックス -->
<c:forEach var="subject" items="${subjectList}">
<option value="${subject.cd}">${subject.name}</option>
</c:forEach>
</select>
</td>
<td>
<select name="f4"> <!-- ID: 9, 回目セレクトボックス -->
<c:forEach var="num" items="${timesList}">
<option value="${num}">${num}</option>
</c:forEach>
</select>
</td>
</tr>
</table>
<form action="searchResults.jsp" method="post">
<button type="submit">検索</button> <!-- ID: 10, 自画面に遷移して指定された科目情報の条件に該当する成績を一覧表示する -->
</form>
</body>
</html>