<!DOCTYPE html>
<html>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.*" %>
<%@ page import="javax.naming.*" %>
<%@ page import="javax.sql.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../background.html" %>
<title>ログイン</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
<style>
       label {
           display: block;
           margin: 10px 0 5px;
       }
</style>
<script>
       function togglePasswordVisibility() {
           var passwordInput = document.getElementById("password");
           var checkbox = document.getElementById("chk_d_ps");
           if (checkbox.checked) {
               passwordInput.type = "text";
           } else {
               passwordInput.type = "password";
           }
       }
</script>
</head>
<body>
<h1 class="toptitle">得点管理システム</h1>
<h2 class="subtitle">ログイン</h2>

<c:choose>
    <c:when test="${error == true}">
	    <div>ログインに失敗しました。IDまたはパスワードが正しくありません。</div>
    </c:when>
    <c:otherwise>
    </c:otherwise>
</c:choose>
<form action="LoginExecute.action" method="post">
<label for="id">ログインID</label>
<input type="text" id="id" name="id" maxlength="20" placeholder="半角でご入力ください" required>
<label for="password">パスワード</label>
<input type="password" id="password" name="password" maxlength="20" placeholder="20文字以内の半角英数字でご入力ください" required>
<label for="chk_d_ps">
<input type="checkbox" id="chk_d_ps" onclick="togglePasswordVisibility()">
           パスワードを表示
</label>
<button type="submit" id="login">ログイン</button>
</form>
</body>
</html>
<%@include file="../footer.html" %>
