<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../background.html" %>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/css/style.css" />
<meta charset="UTF-8">
<title>ログイン画面</title>
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
<h1 class="toptitle">ログイン</h1>
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