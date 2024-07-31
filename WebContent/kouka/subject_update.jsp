<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.sql.*" %>
<%@ page import="bean.Subject" %>
<%@ page import="bean.Teacher" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../background.html" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>科目更新処理</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
	<h1 class="toptitle">得点管理システム</h1>
    <h2 class="subtitle">科目更新</h2>
    <%@ include file="sidebar.jsp" %>
<%
    request.setCharacterEncoding("UTF-8");

	Teacher teacher = new Teacher();
	teacher = (Teacher) session.getAttribute("current_teacher");
	String schoolCode = teacher.getSchool().getCd();
    String originalSubjectCode = request.getParameter("originalSubjectCode");
    String subjectCode = request.getParameter("subjectCode");
    String subjectName = request.getParameter("subjectName");

    Connection conn = null;
    PreparedStatement pstmt = null;

    if (subjectName == null || subjectName.trim().isEmpty()) {
        // 科目名が未入力の場合のエラーメッセージ
        out.println("<p style='color:red;'>これは必須フィールドです。</p>");
        // 編集画面に戻るリンクを表示
        out.println("<a href='subject_edit.jsp?subjectCode=" + originalSubjectCode + "'>編集画面に戻る</a>");
    } else {
        try {
            Context initContext = new InitialContext();
            DataSource ds = (DataSource)initContext.lookup("java:/comp/env/jdbc/kouka");

            conn = ds.getConnection();

            String sql = "UPDATE SUBJECT SET CD = ?, NAME = ? WHERE CD = ? AND SCHOOL_CD = ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, subjectCode);
            pstmt.setString(2, subjectName);
            pstmt.setString(3, originalSubjectCode);
            pstmt.setString(4, schoolCode);

            int result = pstmt.executeUpdate();

            if (result > 0) {
                out.println("<div class='message-box'>科目が正常に更新されました。</div>");
                out.println("<div class='button-container'><a href='subject.jsp' class='btn btn-back'>科目一覧に戻る</a></div></div>");
            } else {
                out.println("<div class='message-box'>科目の更新に失敗しました。</div>");
                out.println("<div class='button-container'><div class='btn btn-back'><a href='subject_edit.jsp?subjectCode=" + originalSubjectCode + "'>編集画面に戻る</a></div></div>");
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
    }
%>
</body>
</html>
<%@include file="../footer.html" %>