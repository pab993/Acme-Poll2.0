<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
	<spring:message code="poll.instances" />
	:
	<jstl:out value="${fn:length(poll.instances)}"></jstl:out>
	<br>

</fieldset>
<br><br>

		<form:form action="bill/editC.do" modelAttribute="bill">
			
			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="poll"/>
			
					
			<acme:textbox code="bill.amountDue" path="amountDue" mandatory="true"/>
			<acme:textbox code="bill.momentDue" path="momentDue" mandatory="true"/>
					
			<br>
					
			<acme:submit name="save" code="bill.save"/>
			<acme:cancel code="bill.cancel" url ="poll/list.do"/>
			
		</form:form>