<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>成績追加処理</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%
    request.setCharacterEncoding("UTF-8");

    String schoolCode = request.getParameter("schoolCode");
    String subjectCode = request.getParameter("subjectCode");
    String subjectCode = request.getParameter("schoolCode");
    Int subjectCode = request.getParameter("No");
    String subjectCode = request.getParameter("Point");
    String subjectCode = request.getParameter("classCode");


    Connection conn = null;
    PreparedStatement pstmt = null;

    try {
        Context initContext = new InitialContext();
        DataSource ds = (DataSource)initContext.lookup("java:/comp/env/jdbc/kouka");

        conn = ds.getConnection();

        String sql = ("INSERT INTO TEST (STUDENT_NO, SUBJECT_CD, SCHOOL_CD, NO, POINT,CLASS_NUM
) VALUES (?, ?, ?, ?, ?, ?)";

        pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, schoolCode);
        pstmt.setString(2, subjectCode);
        pstmt.setString(3, schoolCode);
        pstmt.setInt(4, No);
        pstmt.setString(5, Point);
        pstmt.setString(6, classCode);


        int result = pstmt.executeUpdate();

        if (result > 0) {
            out.println("<p>成績が正常に追加されました。</p>");
        } else {
            out.println("<p>成績の追加に失敗しました。</p>");
        }
    } catch (Exception e) {
        e.printStackTrace();
        out.println("<p>エラーが発生しました: " + e.getMessage() + "</p>");
    } finally {
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