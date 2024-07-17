<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<% request.setCharacterEncoding("UTF-8"); %>
<c:import url="/common/base.jsp">
    <c:param name="title">
        <h1 class="toptitle">得点管理システム</h1>
    </c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <h2 class="subtitle">得点登録</h2>
        <%@ include file="sidebar.jsp" %>
        <section class="me-4">
            <form action="add_score_process.jsp" method="POST" accept-charset="UTF-8">
                <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
                    <div class="col-4">
                        <label for="studentNo">学生番号:</label>
                        <input type="text" id="studentNo" name="studentNo" required>
                    </div>
                    <div class="col-4">
                        <label for="subjectCode">科目コード:</label>
                        <input type="text" id="subjectCode" name="subjectCode" required>
                    </div>
                    <div class="col-4">
                        <label for="point">点数:</label>
                        <input type="text" id="point" name="point" required>
                    </div>
                    <div class="col-1 text-center">
                        <button type="submit">登録</button>
                    </div>
                    <div class="mt-2 text-warning">${errors.get("f1")}</div>
                </div>
            </form>
            <a href="student_score.jsp">得点一覧に戻る</a>
        </section>
    </c:param>
</c:import>
<%@include file="../footer.html" %>
