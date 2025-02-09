
<!DOCTYPE html>
<html>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.sql.*" %>
<%@ page import="bean.Student" %>
<%@ page import="bean.Test" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../background.html" %>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>成績登録</title>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<h1 class="toptitle">得点管理システム</h1>
<h2 class="subtitle">成績登録</h2>
<%@ include file="sidebar.jsp" %>
<h1></h1>
<%
	//検索済みかを取得
	boolean filterFlag = (boolean) request.getAttribute("filterFlag");

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

<form method="post" action="ScoreList.action">

    <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
        <div class="col-3">
            <label class="form-label" for="academicYear">入学年度</label>
            <select class="form-select" id="academicYear" name="academicYear" required>
	            <option value="">-----</option>
				<%
					int oldEntYear = -1;
            		if (filterFlag == true) {
    					String oldEntYearStr = (String) request.getAttribute("academicYear");
                		oldEntYear = Integer.parseInt(oldEntYearStr);
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
						oldClassNum = Integer.parseInt(oldClassNumStr);
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
        <div class="col-2">
            <label class="form-label" for="times">回数</label>
            <select class="form-select" id="times" name="times" required>
            	<%
            		int oldTimes = -1;
	        		if (filterFlag == true) {
						String oldTimesStr = (String) request.getAttribute("times");
						oldTimes = Integer.parseInt(oldTimesStr);
	        		}
	        		%>
    	        <option value="">-----</option>
                <option value="1" <% if (oldTimes == 1) {%>selected<%} %>>1</option>
                <option value="2" <% if (oldTimes == 2) {%>selected<%} %>>2</option>
            </select>
        </div>
        <div class="col-1 text-center">
            <button type="submit" class="btn btn-secondary">
            検索</button>
        </div>
    </div>
</form>

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

<%
	// セッションから学生リストを取得
	List<Student> students = (List<Student>) request.getAttribute("students");
	List<Test> tests = (List<Test>) request.getAttribute("tests");
	int resultCount = 0;
	if (students.size() > 0 && filterFlag == true) { %>
	<form action="ScoreUpdate.action" method="post">
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
		            for (int i = 0; i < tests.size(); i++) {
		            	Test currentTest = tests.get(i);
		                resultCount++;
		                String studentNo = currentTest.getStudent().getNo();
		                Integer entYear = currentTest.getStudent().getEntYear();
		                String classNum = currentTest.getClassNum();
		                String no = currentTest.getStudent().getNo();
		                String name = currentTest.getStudent().getName();
		                String subjectCd = currentTest.getSubject().getSubjectCode();
		                String subjectName = currentTest.getSubject().getSubjectName();
		                Integer point = currentTest.getPoint();
		                Integer num = currentTest.getNo();
		    %>
		                <tr>
		                    <td><%= entYear %></td>
		                    <td><%= classNum %></td>
		                    <td><%= no %></td>
		                    <td><%= name %></td>
		                    <td><%= subjectName %></td>
		                    <td>
		                            <input type="number" name="point" value="<%= point %>" min=0 max=100 step="1" required>
		                            <input type="text" name="student_no" value="<%= studentNo %>" hidden="">
		                            <input type="text" name="subject_cd" value="<%= subjectCd %>" hidden="">
		                            <input type="text" name="num" value="<%= num %>" hidden="">
		                    </td>
		                </tr>
		    <%
		            }
		    %>
		</table>
		<input type="text" name="result_count" value="<%= resultCount %>" hidden="">
		<input type="submit" value="更新">
	</form>
		<% } %>
		<%if (filterFlag == true) { %>
		<div>検索結果：<%= resultCount %>件</div>
		<% } %>
</body>
</html>
<%@include file="../footer.html" %>
