<%@ page import="dao.SubjectDao" %>
<%@ page import="bean.Subject" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>科目追加</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <h2>科目追加</h2>
    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null && !errorMessage.isEmpty()) { %>
        <div style="color: red;"><%= errorMessage %></div>
    <% } %>
    <form action="add_subject_process.jsp" method="POST" accept-charset="UTF-8">
        <div>
            <label for="subjectCode">科目コード:</label>
            <input type="text" id="subjectCode" name="subjectCode" required>
        </div>
        <div>
            <label for="subjectName">科目名:</label>
            <input type="text" id="subjectName" name="subjectName" required>
        </div>
        <%-- 学校コードを取得してhiddenフィールドとして設定 --%>
        <%
            SubjectDao subjectDao = new SubjectDao();
            String schoolCode = subjectDao.getStudentSchoolCode();
        %>
        <input type="hidden" name="schoolCode" value="<%= schoolCode %>">
        <button type="submit" href="subject_success.jsp">登録</button>
    </form>
    <a href="subject.jsp">科目一覧に戻る</a>
</body>
</html>
