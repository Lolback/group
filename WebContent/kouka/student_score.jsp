
<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.sql.*" %>
<%@ page import="java.util.List" %>
<%@ page import="bean.Student" %>
<%@ page import="bean.Test" %>
<%@ page import="bean.TestListSubject" %>
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
<%
//検索済みかを取得
boolean filterFlag = (boolean) request.getAttribute("filterFlag");
%>
<h1 class="toptitle">得点一覧</h1>
    <h2 class="subtitle">成績参照</h2>
    <%@ include file="sidebar.jsp" %>
    <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
    <% if (errorMessage != null && !errorMessage.isEmpty()) { %>
        <div style="color: red;"><%= errorMessage %></div>
    <% } %>
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
					int oldEntYear = -1;
            		if (filterFlag == true) {
    					String oldEntYearStr = (String) request.getAttribute("academicYear");
    					if (!oldEntYearStr.isEmpty()) {
                    		oldEntYear = Integer.parseInt(oldEntYearStr);
    					}
            		}
			    	List<Integer> entYearSet = (List<Integer>) request.getAttribute("ent_year_set");
				    for (int i = 0; i < entYearSet.size(); i++) {
				    	int currentEntYear = entYearSet.get(i);
				%>
				   <option value="<%= currentEntYear %>" <% if (currentEntYear == oldEntYear) {%>selected<%} %>><%= currentEntYear %></option>
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
					int oldClassNum = -1;
	        		if (filterFlag == true) {
						String oldClassNumStr = (String) request.getAttribute("class");
    					if (!oldClassNumStr.isEmpty()) {
    						oldClassNum = Integer.parseInt(oldClassNumStr);
    					}
	        		}
			    	List<Integer> classNumSet = (List<Integer>) request.getAttribute("class_num_set");
				    for (int i = 0; i < classNumSet.size(); i++) {
				    	int currentClassNum = classNumSet.get(i);
				%>
				   <option value="<%= currentClassNum %>" <% if (currentClassNum == oldClassNum) {%>selected<%} %>><%= currentClassNum %></option>
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
					String oldSubject = null;
	        		if (filterFlag == true) {
						oldSubject = (String) request.getAttribute("subject");
	        		}
			    	List<String> subjectCdSet = (List<String>) request.getAttribute("subject_cd_set");
			    	List<String> subjectNameSet = (List<String>) request.getAttribute("subject_name_set");
				    for (int i = 0; i < subjectCdSet.size(); i++) {
					    String currentSubject = subjectCdSet.get(i);
				%>
				   <option value="<%= currentSubject %>" <% if (currentSubject.equals(oldSubject)) {%>selected<%} %>><%= subjectNameSet.get(i) %></option>
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
			<p class="stn">学生番号</p>
		</tr>
		<tr>
		<td>
			<input type="text" name="student_no" size="10" value="${f4}" placeholder="学生番号を入力してください">
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
<%
	// セッションから学生リストを取得
	List<Student> students = (List<Student>) request.getAttribute("students");
	List<Test> tests = (List<Test>) request.getAttribute("tests");
	List<TestListSubject> tlsub = (List<TestListSubject>) request.getAttribute("tlsubs");
	Student student = (Student) request.getAttribute("student");
	String from = (String) request.getAttribute("from");

	int resultCount = 0;
	if ( from.equals("testSearch") && filterFlag == true) { %>
	<p>科目：<%= request.getAttribute("subject_name") %></p>
		<table class="table table-hover">
		    <tr>
		        <th>入学年度</th>
		        <th>クラス</th>
		        <th>学生番号</th>
		        <th>氏名</th>
		        <th>1回</th>
		        <th>2回</th>
		    </tr>
		    <%
		            for (int i = 0; i < tlsub.size(); i++) {
		            	TestListSubject currentTest = tlsub.get(i);
		                resultCount++;
		                int entYear = currentTest.getEntYear();
		                String classNum = currentTest.getClassNum();
		                String no = currentTest.getStudentNo();
		                String name = currentTest.getStudentName();
		                int point1 = currentTest.getPoint(1);
		                int point2 = currentTest.getPoint(2);
		    %>
		                <tr>
		                    <td><%= entYear %></td>
		                    <td><%= classNum %></td>
		                    <td><%= no %></td>
		                    <td><%= name %></td>
		                    <td><%= point1 %></td>
		                    <td><%= point2 %></td>
		                </tr>
		    <%
		            }
		    %>
		</table>
		<div>検索結果：<%= resultCount %>件</div>
		<% } %>
		<% 	if (student != null && filterFlag == true) { %>
	<p>氏名：<%= student.getName() %>(<%= student.getNo() %>)</p>
		<table class="table table-hover">
		    <tr>
		        <th>科目名</th>
		        <th>科目コード</th>
		        <th>回数</th>
		        <th>点数</th>
		    </tr>
		    <%
		            for (int i = 0; i < tests.size(); i++) {
		            	Test currentTest = tests.get(i);
		                resultCount++;
		                String subjectName = currentTest.getSubject().getSubjectName();
		                String subjectCd = currentTest.getSubject().getSubjectCode();
		                int no = currentTest.getNo();
		                int point = currentTest.getPoint();
		    %>
		                <tr>
		                    <td><%= subjectName %></td>
		                    <td><%= subjectCd %></td>
		                    <td><%= no %></td>
		                    <td><%= point %></td>
		                </tr>
		    <%
		            }
		    %>
		</table>
		<div>検索結果：<%= resultCount %>件</div>
		<% } %>
		<% 	if (student == null && students.size() == 0 && filterFlag == true && from.equals("studentSearch")) { %>
	<p>氏名：大原 千太郎(<%= request.getAttribute("student_no") %>)</p>
		<p>成績情報が存在しませんでした</p>
		<% } %>
<%@include file="../footer.html" %>
</body>
</html>

