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
	</jstl:when>
	<jstl:otherwise>
		
		
			<jstl:forEach items="${poll.questions}" var="question" varStatus="status">
			
				<strong><jstl:out value="${question.statement }"></jstl:out></strong>
				<br>
				
				<jstl:forEach items="${question.answers}" var="answer">
				
					<jstl:out value="${answer.ans }"/> ------> <jstl:out value="${answer.counter }"/>
					<br>
				
				</jstl:forEach>
				
				<br><br>
			
			</jstl:forEach>
			
			<br>		
		
	</jstl:otherwise>
</jstl:choose>

<br />