<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<link rel="stylesheet"
href="${pageContext.request.contextPath}/css/style.css">
<%@include file="../background.html" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>成績情報検索画面</title>
</head>
<body>
<h1 class="toptitle">得点管理システム</h1>
<h1 class="subtitle">科目情報</h1>
<%@ include file="sidebar.jsp" %>
<div>
<table>
<thead>
<tr>
<th>入学年度</th>
<th>クラス</th>
<th>科目</th>
</tr>
</thead>
<tbody>
<tr>
<td>
<select name="enrollmentYear" id="enrollmentYear">
<!-- Options for enrollment year -->
</select>
</td>
<td>
<select name="class" id="class">
<!-- Options for class -->
</select>
</td>
<td>
<select name="subject" id="subject">
<!-- Options for subject -->
</select>
</td>
<td>
<button type="button" onclick="searchGradesBySubject()">検索</button>
</td>
</tr>
</tbody>
</table>
</div>
<h2>学生情報</h2>
<div>
<label for="studentNumber">学生番号</label>
<input type="text" id="studentNumber" name="studentNumber" value="学生番号を入力してください" maxlength="10" required>
<button type="button" onclick="searchGradesByStudent()">検索</button>
</div>
<input type="hidden" id="subjectCode" name="subjectCode" value="sj">
<input type="hidden" id="studentCode" name="studentCode" value="st">
<script>
       function searchGradesBySubject() {
           // Clear student search criteria
           document.getElementById('studentNumber').value = '学生番号を入力してください';
           // Perform subject search
           // Implement the logic to search grades by subject here
       }
       function searchGradesByStudent() {
           // Clear subject search criteria
           document.getElementById('enrollmentYear').selectedIndex = 0;
           document.getElementById('class').selectedIndex = 0;
           document.getElementById('subject').selectedIndex = 0;
           // Perform student search
           // Implement the logic to search grades by student here
       }
</script>
</body>
</html>