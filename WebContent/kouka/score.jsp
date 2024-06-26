<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>成績管理システム</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>得点管理システム</h1>
            <nav>
                <ul>
                    <li><a href="#">メニュー</a></li>
                    <li><a href="#">学生管理</a></li>
                    <li><a href="#">成績管理</a></li>
                    <li><a href="#">試験管理</a></li>
                    <li><a href="#">科目管理</a></li>
                </ul>
            </nav>
        </header>
        <section>
            <h2>成績管理</h2>
            <form method="post" action="scoreManagement">
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
            <div>
                <h3>検索結果</h3>
                <p>${result}</p>
            </div>
        </section>
    </div>
</body>
</html>
