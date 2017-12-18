<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<!-- Listing table -->
	
<fieldset>

	<legend>
		<b> <spring:message code="bill.info" /></b>
	</legend>

	<spring:message code="bill.momentDue" />
	:
	<fmt:formatDate pattern = "dd/MM/yyyy HH:mm" value = "${bill.momentDue}" />
	<br>
	
	<spring:message code="bill.amountDue" />
	:
	<jstl:out value="${bill.amountDue}"></jstl:out>Euro/s
	<br>
	
	<jstl:if test="${bill.paid == false }">
		<spring:message code="bill.paid.no" />
	</jstl:if>
	
	<jstl:if test="${bill.paid == true }">
		<spring:message code="bill.paid.yes" />
	</jstl:if>

</fieldset>
<br>

<jstl:choose>
	<jstl:when test="${bill.receipt != null }">
		<a href="<spring:url value='/receipt/display.do?receiptId=${bill.receipt.id }' />"><spring:message code="bill.receipt.display"/></a>
	</jstl:when>
	<jstl:otherwise>
		<spring:message code="bill.no.receipt" />
	</jstl:otherwise>
</jstl:choose>

<security:authorize access="hasRole('ADMIN')">
	<input type="button" name="cancel" value="<spring:message code="bill.cancel" />" onclick="javascript: window.location.replace('<spring:url value='/poll/list.do' />')" />
</security:authorize>

