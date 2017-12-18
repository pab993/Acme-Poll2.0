<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing table -->
	
	<display:table name = "questions" id = "row" requestURI = "${requestURI}" pagesize = "5" class = "displaytag" >

			<spring:message code = "question.statement" var = "statementHeader" />
			<display:column property = "statement" title="${statementHeader }"/>
			
			
<%-- 			<display:column>
				<a href="question/edit.do?questionId=${row.id}">
					<spring:message code="question.edit" />
				</a>
			</display:column> --%>
			
			<jstl:if test="${poll.startActive gt actualDate && poll.endActive gt actualDate}">
				<display:column>
					<a href="question/edit.do?questionId=${row.id}">
						<spring:message code="question.edit" />
					</a>
				</display:column>
			</jstl:if>
			
			
			<display:column>
				<a href="answer/list.do?questionId=${row.id}">
					<spring:message code="question.answers.list" />
				</a>
			</display:column>
			
						
</display:table>

	<security:authorize access="hasRole('POLLER')">
		<jstl:if test="${poll.startActive gt actualDate && poll.endActive gt actualDate}">
			<a href="question/create.do?pollId=${poll.id}">
				<spring:message code="question.create" />
			</a>
		</jstl:if>
	</security:authorize>
	<input type="button" name="cancel" value="<spring:message code="question.cancel" />" onclick="javascript: window.location.replace('<spring:url value='/poll/myList.do' />')" />