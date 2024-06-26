<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>科目追加</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <h2>科目追加</h2>
    <form action="add_subject_process.jsp" method="POST" accept-charset="UTF-8">
        <div>
            <label for="schoolCode">学校コード:</label>
            <input type="text" id="schoolCode" name="schoolCode" required>
        </div>
        <div>
            <label for="subjectCode">科目コード:</label>
            <input type="text" id="subjectCode" name="subjectCode" required>
        </div>
        <div>
            <label for="subjectName">科目名:</label>
            <input type="text" id="subjectName" name="subjectName" required>
        </div>
        <button type="submit">登録</button>
    </form>
    <a href="subject.jsp">科目一覧に戻る</a>
</body>
</html>
