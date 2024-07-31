<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.sql.*" %>
<%@ page import="bean.Teacher" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../background.html" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>科目編集</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<script>
function validateForm() {
    var subjectCode = document.getElementById('subjectCode').value.trim();
    var subjectName = document.getElementById('subjectName').value.trim();
    var errorMessage = '';

    if (subjectCode === '') {
        errorMessage += '科目コードは必須フィールドです。<br>';
    }

    if (subjectName === '') {
        errorMessage += '科目名は必須フィールドです。<br>';
    }

    if (errorMessage !== '') {
        document.getElementById('errorMessages').innerHTML = errorMessage;
        return false; // フォーム送信を中止
    }

    return true; // フォーム送信を続行
}
</script>
</head>
<body>
	<h1 class="toptitle">得点管理システム</h1>
    <h2 class="subtitle">科目編集</h2>
    <%@ include file="sidebar.jsp" %>
    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null && !errorMessage.isEmpty()) { %>
        <div style="color: red;"><%= errorMessage %></div>
    <% } %>
    <%
        String subjectCode = request.getParameter("subjectCode");
	    Teacher teacher = new Teacher();
	    teacher = (Teacher) session.getAttribute("current_teacher");
        String schoolCode = teacher.getSchool().getCd();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Context initContext = new InitialContext();
            DataSource ds = (DataSource)initContext.lookup("java:/comp/env/jdbc/kouka");

            conn = ds.getConnection();

            String sql = "SELECT SCHOOL_CD, CD, NAME FROM SUBJECT WHERE CD = ? AND SCHOOL_CD = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, subjectCode);
            pstmt.setString(2, schoolCode);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                String subjectName = rs.getString("NAME");
    %>
				<h1></h1>
                <form action="subject_update.jsp" method="post" onsubmit="return validateForm()">
                <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
                    <input type="hidden" name="originalSubjectCode" value="<%= subjectCode %>">
                    <div class="col-5">
                        <label for="subjectCode">科目コード:</label>
                        <%= subjectCode %>
                    </div>
                    <div class="col-5">
                        <label for="subjectName">科目名:</label>
                        <input type="text" id="subjectName" name="subjectName" value="<%= subjectName %>" required>
                    </div>
                    <div class="col-1" id="errorMessages" style="color: red;"></div>
                    <input type="submit" value="更新">
                </div>
                </form>

    <%
            } else {
                out.println("<div class='message-box'>指定された科目が見つかりません。</div>");
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>エラーが発生しました: " + e.getMessage() + "</p>");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    %>
    <div class="button-container">
    <a href="subject.jsp" class="btn btn-back">科目一覧に戻る</a>
    </div>
</body>
</html>
<%@include file="../footer.html" %>