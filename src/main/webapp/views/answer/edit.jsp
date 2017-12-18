<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<jstl:choose>
	<jstl:when test="${poll.id != 0 && poll.startActive lt actualDate}">
		<h1><spring:message code="poll.edit.deny" /></h1>
	</jstl:when>
	<jstl:otherwise>
	
		<form:form action="answer/edit.do" modelAttribute="answer">
			
			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="question"/>
			
			<acme:textbox code="answer.ans" path="ans" mandatory="true"/>
			
			<br>
			
			<acme:submit name="save" code="answer.save"/>
			<jstl:if test="${answer.id != 0}">
				<input type="submit" name="delete" value="<spring:message code="answer.delete" />" onclick="return confirm('<spring:message code="answer.confirm" />')" />
			</jstl:if>
			<acme:cancel code="answer.cancel.list" url ="answer/list.do?questionId=${question.id }"/>
			
		</form:form>
	
	</jstl:otherwise>
</jstl:choose>