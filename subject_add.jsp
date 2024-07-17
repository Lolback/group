<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.sql.*" %>
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
        <button type="submit">登録</button>
    </form>
    <a href="subject.jsp">科目一覧に戻る</a>

    <%-- データベースから学校コードを取得する処理 --%>
    <%
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Context initContext = new InitialContext();
            DataSource ds = (DataSource)initContext.lookup("java:/comp/env/jdbc/kouka");
            conn = ds.getConnection();

            // 学校コードを取得するSQLクエリ（STUDENTとSUBJECTの結合）
            String sql = "SELECT DISTINCT S.SCHOOL_CD " +
                         "FROM SUBJECT S " +
                         "INNER JOIN STUDENT ST ON S.SCHOOL_CD = ST.SCHOOL_CD";

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                String schoolCode = rs.getString("SCHOOL_CD");
    %>
                <input type="hidden" name="schoolCode" value="<%= schoolCode %>">
    <%
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>エラーが発生しました: " + e.getMessage() + "</p>");
        } finally {
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (pstmt != null) try { pstmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    %>
</body>
</html>
