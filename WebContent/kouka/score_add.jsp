<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>点数登録</title>
</head>
<body>
    <h1>点数登録</h1>
    <form method="post" action="score">
                <div class="form-group">
                    <label for="academicYear">入学年度</label>
                    <select id="academicYear" name="academicYear">
                        <option value="2023">2023</option>
                        <option value="2022">2022</option>
                        <option value="2021">2021</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="class">クラス</label>
                    <select id="class" name="class">
                        <option value="A">A</option>
                        <option value="B">B</option>
                        <option value="C">C</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="subject">科目</label>
                    <select id="subject" name="subject">
                        <option value="math">数学</option>
                        <option value="science">科学</option>
                        <option value="history">歴史</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="times">回数</label>
                    <select id="times" name="times">
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                    </select>
                </div>
                <button type="submit">検索</button>
            </form>

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
                    <td>${student.id}</td>
                    <td>${student.name}</td>
                    <td><input type="number" name="score_${student.id}" value="${student.score}"></td>
                </tr>
            </c:forEach>
        </table>
        <button type="submit">登録して終了</button>
    </form>
</body>
</html>
