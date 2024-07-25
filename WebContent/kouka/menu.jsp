<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../background.html" %>
<% request.setCharacterEncoding("UTF-8"); %>
<link rel="stylesheet" href="/css/style.css" />
<title>得点管理システム</title>
<c:import url="/common/base.jsp">
    <c:param name="title">
        <h1 class="toptitle">得点管理システム</h1>
    </c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="subtitle">メニュー</h2>
			<%@ include file="sidebar.jsp" %>
            <div class="row text-center justify-content-center">
                <div class="button-container">
                    <a class="btn-student" href="StudentList.action">学生管理</a>
                    <div class="btn-test">
                        <a href="student_score.jsp">成績管理</a>
                        <a href="score_add.jsp">成績登録</a>
                        <a href="ScoreList.action">成績参照</a>

                    </div>
                    <a class="btn-subject" href="subject.jsp">科目管理</a>
                </div>



        </section>
    </c:param>
</c:import>

<%@include file="../footer.html" %>