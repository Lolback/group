<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../background.html" %>
<% request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <meta charset="UTF-8">
    <title>ログイン画面</title>
    <style>
        label {
            display: block;
            margin: 10px 0 5px;
        }
        .error-message {
            color: red;
            margin: 10px 0;
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
    <h1></h1>
<form action="LoginExecute.action" method="post" class="login_form">
    <label for="id">ログインID</label>
    <input type="text" id="id" name="id" maxlength="20" placeholder="半角でご入力ください" required>

    <label for="password">パスワード</label>
    <input type="password" id="password" name="password" maxlength="20" placeholder="20文字以内の半角英数字でご入力ください" required>

    <div class="checkbox-group">
        <input type="checkbox" id="chk_d_ps" onclick="togglePasswordVisibility()">
        <label for="chk_d_ps">パスワードを表示</label>
    </div>
    <c:if test="${not empty error}">
    <div class="error-message">IDまたはパスワードが確認できませんでした</div>
    </c:if>
    <button type="submit" id="login">ログイン</button>
</form>

	<h1></h1>
</body>
</html>

<%@include file="../footer.html" %>

