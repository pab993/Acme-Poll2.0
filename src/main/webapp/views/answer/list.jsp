<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing table -->
	
	<display:table name = "answers" id = "row" requestURI = "${requestURI}" pagesize = "5" class = "displaytag" >

			<spring:message code = "answer.ans" var = "ansHeader" />
			<display:column property = "ans" title="${ansHeader }"/>
			
			<jstl:if test="${poll.startActive gt actualDate && poll.endActive gt actualDate}">
				<display:column>
					<a href="answer/edit.do?answerId=${row.id}">
						<spring:message code="answer.edit" />
					</a>
				</display:column>
			</jstl:if>
						
	</display:table>

	<security:authorize access="hasRole('POLLER')">
		<jstl:if test="${poll.startActive gt actualDate && poll.endActive gt actualDate}">
			<a href="answer/create.do?questionId=${question.id}">
				<spring:message code="answer.create" />
			</a>
		</jstl:if>
	</security:authorize>
	<input type="button" name="cancel" value="<spring:message code="answer.cancel" />" onclick="javascript: window.location.replace('<spring:url value='/question/list.do?pollId=${poll.id}' />')" />