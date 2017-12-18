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
	
		<form:form action="poll/edit.do" modelAttribute="poll">
			
			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="ticker"/>
			
			<acme:textbox code="poll.title" path="title" mandatory="true"/>
			<acme:textbox code="poll.description" path="description" mandatory="true"/>
			<acme:textbox code="poll.banner" path="banner" mandatory="true"/>
			<acme:textbox code="poll.startActive" path="startActive" mandatory="true"/>
			<acme:textbox code="poll.endActive" path="endActive" mandatory="true"/>
			
			<br>
			
			<acme:submit name="save" code="poll.save"/>
			<jstl:if test="${poll.id != 0}">
				<input type="submit" name="delete" value="<spring:message code="poll.delete" />" onclick="return confirm('<spring:message code="poll.confirm" />')" />
			</jstl:if>
			<acme:cancel code="poll.cancel.myList" url ="poll/myList.do"/>
			
		</form:form>
	
	</jstl:otherwise>
</jstl:choose>

