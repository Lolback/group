
<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.sql.*" %>
<%@ page import="java.util.List" %>
<%@include file="../background.html" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>成績参照</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<h1 class="toptitle">得点一覧</h1>
    <h2 class="subtitle">成績参照</h2>
    <%@ include file="sidebar.jsp" %>
    <h1></h1>
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

<form method="post" action="ScoreListSubjectExecute.action">

    <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
        <div class="col-3">
            <label class="form-label" for="academicYear">入学年度</label>
            <select class="form-select" id="academicYear" name="academicYear" required>
	            <option value="">-----</option>
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
            <select class="form-select" id="class" name="class" required>
	            <option value="">-----</option>
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
        <div class="col-3">
            <label class="form-label" for="subject">科目</label>
            <select class="form-select" id="subject" name="subject" required>
	            <option value="">-----</option>
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
        <div class="col-1 text-center">
            <button type="submit" class="btn btn-secondary">検索</button>
        </div>
    </div>
</form>
            <!-- 学生情報 -->
<div id="studentInfo">
	<form method="post" action="TestListStudent.action">
		<table class="table table-hover">
		<tr>
			<th>学生番号</th>
		</tr>
		<tr>
		<td>
			<input type="text" name="f4" size="10" value="${f4}" placeholder="学生番号を入力してください">
		</td>
			<td><input type="submit" value="検索"></td>
		</tr>
		</table>
			</form>
		</div>
<!-- 利用方法案内メッセージ -->
<p id="usageMessage">科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</p>
<!-- 隠しフィールド -->
<input type="hidden" name="f" value="sj">
<input type="hidden" name="f" value="st">
<%@include file="../footer.html" %>
</body>
</html>

