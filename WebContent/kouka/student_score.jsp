<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../background.html" %>
<% request.setCharacterEncoding("UTF-8"); %>
<c:import url="../common/base.jsp">
    <c:param name="title">
        <h1 class="toptitle">得点管理システム</h1>
    </c:param>

    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="subtitle">成績管理</h2>
            <%@ include file="sidebar.jsp" %>
            <div class="my-2 text-end px-4">
                <a href="score_add.jsp">新規登録</a>
            </div>
            <form method="post" action="scoreManagement">
                <div class="row border mx-3 mb-3 py-2 align-items-center rounded" id="filter">
                    <div class="col-3">
                        <label class="form-label" for="academicYear">入学年度</label>
                        <select class="form-select" id="academicYear" name="academicYear">
                            <option value="2023">2023</option>
                            <option value="2022">2022</option>
                            <option value="2021">2021</option>
                        </select>
                    </div>
                    <div class="col-2">
                        <label class="form-label" for="class">クラス</label>
                        <select class="form-select" id="class" name="class">
                            <option value="A">A</option>
                            <option value="B">B</option>
                            <option value="C">C</option>
                        </select>
                    </div>
                    <div class="col-2">
                        <label class="form-label" for="subject">科目</label>
                        <select class="form-select" id="subject" name="subject">
                            <option value="math">数学</option>
                            <option value="science">科学</option>
                            <option value="history">歴史</option>
                        </select>
                    </div>
                    <div class="col-2">
                        <label class="form-label" for="times">回数</label>
                        <select class="form-select" id="times" name="times">
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
            <c:choose>
                <c:when test="${scores.size() > 0}">
                    <div>検索結果：${scores.size()}件</div>
                    <table class="table table-hover">
                        <thead>
                            <tr>
                                <th>入学年度</th>
                                <th>クラス</th>
                                <th>科目</th>
                                <th>回数</th>
                                <th></th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="score" items="${scores}">
                                <tr>
                                    <td>${score.student.entYear}</td>
                                    <td>${score.student.classNum}</td>
                                    <td>${score.subject}</td>
                                    <td>${score.times}</td>
                                    <td><a href="ScoreUpdate.action?scoreId=${score.id}">変更</a></td>
                                    <td><a href="ScoreDelete.action?scoreId=${score.id}">削除</a></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:when>
                <c:otherwise>
                    <div>成績情報が存在しませんでした</div>
                </c:otherwise>
            </c:choose>
        </section>
    </c:param>
</c:import>
<%@include file="../footer.html" %>
