<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <h2>科目編集</h2>
    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null && !errorMessage.isEmpty()) { %>
        <div style="color: red;"><%= errorMessage %></div>
    <% } %>
    <%
        String subjectCode = request.getParameter("subjectCode");

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Context initContext = new InitialContext();
            DataSource ds = (DataSource)initContext.lookup("java:/comp/env/jdbc/kouka");

            conn = ds.getConnection();

            String sql = "SELECT SCHOOL_CD, CD, NAME FROM SUBJECT WHERE CD = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, subjectCode);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                String schoolCode = rs.getString("SCHOOL_CD");
                String subjectName = rs.getString("NAME");
    %>
                <form action="subject_update.jsp" method="post" onsubmit="return validateForm()">
                    <input type="hidden" name="originalSubjectCode" value="<%= subjectCode %>">
                    <div>
                        <label for="schoolCode">学校コード:</label>
                        <input type="text" id="schoolCode" name="schoolCode" value="<%= schoolCode %>" required>
                    </div>
                    <div>
                        <label for="subjectCode">科目コード:</label>
                        <input type="text" id="subjectCode" name="subjectCode" value="<%= subjectCode %>" required>
                    </div>
                    <div>
                        <label for="subjectName">科目名:</label>
                        <input type="text" id="subjectName" name="subjectName" value="<%= subjectName %>" required>
                    </div>
                    <div id="errorMessages" style="color: red;"></div>
                    <input type="submit" value="更新">
                </form>
    <%
            } else {
                out.println("<p>指定された科目が見つかりません。</p>");
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
    <a href="subject.jsp">科目一覧に戻る</a>
</body>
</html>
