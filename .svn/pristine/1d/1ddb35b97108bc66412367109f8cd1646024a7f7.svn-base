<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<fieldset>

	<legend>
		<b> <spring:message code="poll.info" /></b>
	</legend>

	<spring:message code="poll.banner" />
	:
	<img height="48" width="48" src="${poll.banner}">
	<br>
	
	<spring:message code="poll.title" />
	:
	<jstl:out value="${poll.title}"></jstl:out>
	<br>
	<spring:message code="poll.description" />
	:
	<jstl:out value="${poll.description}"></jstl:out>
	<br>
	<spring:message code="poll.ticker" />
	:
	<jstl:out value="${poll.ticker}"></jstl:out>
	<br>

</fieldset>
<br><br>

<jstl:choose>
	<jstl:when test="${fn:length(poll.questions) == 0}">
		<h1>This poll has not any question. :C</h1>
		
		<input type="button" name="cancel" value="<spring:message code="poll.cancel.list" />"
				onclick="javascript: window.location.replace('/Acme-Poll/poll/list.do')" />
		
	</jstl:when>

	<jstl:otherwise>
		
		<form:form action="poll/display.do" modelAttribute="pollForm">
		
			<form:hidden path="pollId" />
			
			<fieldset>

				<legend>
					<b> <spring:message code="poll.info.optional" /></b>
				</legend>
				
				<acme:textbox code="poll.name" path="name"/>
				
				<spring:message code="poll.genre" />
				<form:select path="genre">	
					<spring:message code="poll.default" var="defaultHeader"/><form:option value="" label="${defaultHeader}" />	
					<spring:message code="poll.man" var="manHeader"/><form:option value="MAN" label="${manHeader}" />
					<spring:message code="poll.woman" var="womanHeader"/><form:option value="WOMAN" label="${womanHeader}" />		
				</form:select>
				<acme:textbox code="poll.city" path="city"/>
				
			</fieldset>
			<br>
			<br>
		
			<jstl:forEach items="${poll.questions}" var="question" varStatus="status">
			
				<strong><jstl:out value="${question.statement }"></jstl:out></strong>
				<br>
				
				<jstl:forEach items="${question.answers}" var="answer">
				
					<form:radiobutton value="${answer}" path="listOfAnswers[${status.index}]" /> <jstl:out value="${answer.ans }"/><br>
				
				</jstl:forEach>
				
				<br><br>
			
			</jstl:forEach>
			
			<br>
			
			<acme:submit id="submitButton" name="save" code="poll.submit"/>
			<input type="button" name="cancel" value="<spring:message code="poll.cancel.list" />"
				onclick="javascript: window.location.replace('<spring:url value='/poll/list.do' />')" />
		
		</form:form>
		
		
	</jstl:otherwise>
</jstl:choose>

<br />

