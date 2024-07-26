<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.sql.*" %>
<%@include file="../background.html" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>成績管理</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<h1 class="toptitle">得点管理システム</h1>
    <h2 class="subtitle">成績管理</h2>
    <%@ include file="sidebar.jsp" %>
            <form method="post" action="score_results.jsp">
                <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
                    <div class="col-3">
                        <label class="form-label" for="academicYear">入学年度</label>
                        <select class="form-select" id="academicYear" name="academicYear">
                        	<option value="---">---</option>
                            <option value="2023">2023</option>
                            <option value="2022">2022</option>
                            <option value="2021">2021</option>
                        </select>
                    </div>
                    <div class="col-2">
                        <label class="form-label" for="class">クラス</label>
                        <select class="form-select" id="class" name="class">
                        	<option value="---">---</option>
                            <option value="A">A</option>
                            <option value="B">B</option>
                            <option value="C">C</option>
                        </select>
                    </div>
                    <div class="col-2">
                        <label class="form-label" for="subject">科目</label>
                        <select class="form-select" id="subject" name="subject">
                        	<option value="---">---</option>
                            <option value="math">数学</option>
                            <option value="science">科学</option>
                            <option value="history">歴史</option>
                        </select>
                    </div>
                    <div class="col-2">
                        <label class="form-label" for="times">回数</label>
                        <select class="form-select" id="times" name="times">
                        	<option value="---">---</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                        </select>
                    </div>
                    <div class="col-1 text-center">
                        <button type="submit" class="btn btn-secondary">検索</button>
                    </div>

                </div>
            </form>
            <!-- 学生情報 -->
<div id="studentInfo">
	<form method="post" action="score_results.jsp">
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
</div>
<%@include file="../footer.html" %>
</body>
</html>

