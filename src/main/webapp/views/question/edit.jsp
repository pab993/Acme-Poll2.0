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
	
		<form:form action="question/edit.do" modelAttribute="question">
			
			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="poll"/>
			
			<acme:textbox code="question.statement" path="statement" mandatory="true"/>
			
			<br>
			
			<acme:submit name="save" code="question.save"/>
			<jstl:if test="${question.id != 0}">
				<input type="submit" name="delete" value="<spring:message code="question.delete" />" onclick="return confirm('<spring:message code="question.confirm" />')" />
			</jstl:if>
			<acme:cancel code="question.cancel.list" url ="question/list.do?pollId=${poll.id }"/>
			
		</form:form>
	
	</jstl:otherwise>
</jstl:choose>