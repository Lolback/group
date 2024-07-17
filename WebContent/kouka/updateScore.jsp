<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../background.html" %>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>得点更新</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<h1 class="toptitle">得点管理システム</h1>
<h2 class="subtitle">ポイント更新結果</h2>
<%@ include file="sidebar.jsp" %>
<%
    request.setCharacterEncoding("UTF-8");

    // フォームからのデータを取得
    String studentNo = request.getParameter("studentNo");
    int newPoint = Integer.parseInt(request.getParameter("point"));

    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        // データソースを取得
        Context initContext = new InitialContext();
        DataSource ds = (DataSource)initContext.lookup("java:/comp/env/jdbc/kouka");

        // データベース接続
        conn = ds.getConnection();

        // 更新クエリの準備
        String updateSql = "UPDATE STUDENT SET POINT = ? WHERE STUDENT_NO = ?";
        pstmt = conn.prepareStatement(updateSql);
        pstmt.setInt(1, newPoint);
        pstmt.setString(2, studentNo);

        // 実行
        int rowsAffected = pstmt.executeUpdate();

        // 更新後の結果を取得するクエリ
        String selectSql = "SELECT ENT_YEAR, CLASS_NUM, STUDENT_NO, NAME, POINT FROM STUDENT WHERE STUDENT_NO = ?";
        pstmt = conn.prepareStatement(selectSql);
        pstmt.setString(1, studentNo);
        rs = pstmt.executeQuery();

        if (rs.next()) {
            // 更新されたデータを表示
            out.println("<p>更新が完了しました。</p>");
            int entYear = rs.getInt("ENT_YEAR");
            String classNum = rs.getString("CLASS_NUM");
            String name = rs.getString("NAME");
            int point = rs.getInt("POINT");

%>
    <table class="table table-hover">
        <tr>
            <th>入学年度</th>
            <th>クラス</th>
            <th>学生番号</th>
            <th>氏名</th>
            <th>点数</th>
        </tr>
        <tr>
            <td><%= entYear %></td>
            <td><%= classNum %></td>
            <td><%= studentNo %></td>
            <td><%= name %></td>
            <td><%= point %></td>
        </tr>
    </table>
<%
        } else {
            out.println("<p>更新後のデータを取得できませんでした。</p>");
        }
    } catch (Exception e) {
        e.printStackTrace();
        out.println("<p>エラーが発生しました: " + e.getMessage() + "</p>");
    } finally {
        // リソースの解放
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
<a href="student_score.jsp">成績一覧に戻る</a>
</body>
</html>
<%@include file="../footer.html" %>

