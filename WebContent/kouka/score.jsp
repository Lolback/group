<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>点数登録</title>
</head>
<body>
    <h1>点数登録</h1>
    <form action="ScoreServlet" method="post">
        <table border="1">
            <tr>
                <th>入学年度</th>
                <th>クラス</th>
                <th>学生番号</th>
                <th>氏名</th>
                <th>点数</th>
            </tr>
            <c:forEach var="student" items="${students}">
                <tr>
                    <td>${student.entYear}</td>
                    <td>${student.classNum}</td>
                    <td>${student.studentId}</td>
                    <td>${student.name}</td>
                    <td><input type="number" name="score_${student.studentId}" value="${student.score}"></td>
                </tr>
            </c:forEach>
        </table>
        <button type="submit">登録して終了</button>
    </form>
</body>
</html>

