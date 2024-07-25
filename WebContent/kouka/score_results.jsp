
<!DOCTYPE html>
<html>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../background.html" %>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>検索結果</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<h1 class="toptitle">得点管理システム</h1>
<h2 class="subtitle">検索結果</h2>
<%@ include file="sidebar.jsp" %>
<%
    // セッションからメッセージを取得
    String updateMessage = (String) request.getSession().getAttribute("updateMessage");

    // メッセージが存在する場合にのみ表示
    if (updateMessage != null && !updateMessage.isEmpty()) {
%>
    <div class="alert alert-success" role="alert">
        <%= updateMessage %>
    </div>
<%
        // メッセージを表示した後、セッションから削除（次の更新時に再表示しないため）
        request.getSession().removeAttribute("updateMessage");
    }
%>

<h1></h1>
<form method="post" action="ScoreListSubjectExecute.action">
    <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
        <div class="col-3">
            <label class="form-label" for="academicYear">入学年度</label>
            <select class="form-select" id="academicYear" name="academicYear">
	            <option value="0">--------</option>
				<%
			    	List<Integer> entYearSet = (List<Integer>) request.getAttribute("ent_year_set");
				    for (int i = 0; i < entYearSet.size(); i++) {
				%>
				   <option value="<%= entYearSet.get(i) %>"><%= entYearSet.get(i) %></option>
				<%
				    }
				%>
            </select>
        </div>
        <div class="col-2">
            <label class="form-label" for="class">クラス</label>
            <select class="form-select" id="class" name="class">
	            <option value="0">---</option>
				<%
			    	List<Integer> classNumSet = (List<Integer>) request.getAttribute("class_num_set");
				    for (int i = 0; i < classNumSet.size(); i++) {
				%>
				   <option value="<%= classNumSet.get(i) %>"><%= classNumSet.get(i) %></option>
				<%
				    }
				%>
            </select>
        </div>
        <div class="col-2">
            <label class="form-label" for="subject">科目</label>
            <select class="form-select" id="subject" name="subject">
	            <option value="0">-----</option>
				<%
			    	List<String> subjectCdSet = (List<String>) request.getAttribute("subject_cd_set");
			    	List<String> subjectNameSet = (List<String>) request.getAttribute("subject_name_set");
				    for (int i = 0; i < subjectCdSet.size(); i++) {
				%>
				   <option value="<%= subjectCdSet.get(i) %>"><%= subjectNameSet.get(i) %></option>
				<%
				    }
				%>
            </select>
        </div>
        <div class="col-2">
            <label class="form-label" for="times">回数</label>
            <select class="form-select" id="times" name="times">
                <option value="1">1</option>
                <option value="2">2</option>
            </select>
        </div>
        <div class="col-1 text-center">
            <button type="submit" class="btn btn-secondary">検索</button>
        </div>
    </div>
</form>
<div>
    <label for="studentNumber">学生番号</label>
    <input type="text" id="studentNumber" name="studentNumber" maxlength="10" required>
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
        var studentNumber = document.getElementById('studentNumber').value.trim();

        // Clear subject search criteria
        document.getElementById('academicYear').selectedIndex = 0;
        document.getElementById('class').selectedIndex = 0;
        document.getElementById('subject').selectedIndex = 0;
        document.getElementById('times').selectedIndex = 0;

        // Perform student search
        // Redirect to score_view.jsp with studentNumber parameter
        window.location.href = 'score_view.jsp?studentNumber=' + encodeURIComponent(studentNumber);
    }
</script>

<table class="table table-hover">
    <tr>
        <th>入学年度</th>
        <th>クラス</th>
        <th>学生番号</th>
        <th>氏名</th>
        <th>科目</th>
        <th>点数</th>
    </tr>
    <%
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int resultCount = 0;

        try {
            Context initContext = new InitialContext();
            DataSource ds = (DataSource) initContext.lookup("java:/comp/env/jdbc/kouka");

            conn = ds.getConnection();

            // SQLの組み立て
            String sql = "SELECT s.ENT_YEAR, s.CLASS_NUM, s.NO, s.NAME, sub.NAME AS SUBJECT_NAME, " +
                         "COALESCE(t.POINT, 0) AS POINT " +
                         "FROM STUDENT s " +
                         "JOIN SUBJECT sub ON sub.SCHOOL_CD = s.SCHOOL_CD " +
                         "LEFT JOIN TEST t ON s.NO = t.STUDENT_NO AND sub.CD = t.SUBJECT_CD";

            // 学生番号が指定されている場合、条件を追加
            String studentNumberParam = request.getParameter("studentNumber");
            if (studentNumberParam != null && !studentNumberParam.isEmpty()) {
                sql += " WHERE s.NO = ?";
            }

            pstmt = conn.prepareStatement(sql);

            // 学生番号が指定されている場合、プレースホルダに値を設定
            if (studentNumberParam != null && !studentNumberParam.isEmpty()) {
                pstmt.setString(1, studentNumberParam);
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                resultCount++;
                Integer entYear = rs.getInt("ENT_YEAR");
                String classNum = rs.getString("CLASS_NUM");
                String no = rs.getString("NO");
                String name = rs.getString("NAME");
                String subjectName = rs.getString("SUBJECT_NAME");
                Integer point = rs.getInt("POINT");
    %>
                <tr>
                    <td><%= entYear %></td>
                    <td><%= classNum %></td>
                    <td><%= no %></td>
                    <td><%= name %></td>
                    <td><%= subjectName %></td>
                    <td>
                        <form class="score" method="post" action="ScoreUpdate.action">
                            <input type="hidden" name="no" value="<%= no %>">
                            <input type="hidden" name="subject" value="<%= subjectName %>">
                            <input type="text" name="point" value="<%= point %>">
                            <input type="submit" value="更新">
                        </form>
                    </td>
                </tr>

    <%
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
</table>
<div>検索結果：<%= resultCount %>件</div>
</body>
</html>
<%@include file="../footer.html" %>
