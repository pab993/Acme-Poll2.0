<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="configurationSystem/edit.do" modelAttribute="configurationSystem">

	<form:hidden path="id" />
	<form:hidden path="version" />
<%-- 	<form:hidden path="chorbiFee"/> 
	<form:hidden path="managerFee"/>  --%>

	<fieldset >
		<legend> <b> <spring:message code="configurationSystem" /></b> </legend>
		
		<acme:textbox code="minimun.active" path="minimumPeriodActive"/>
		<br>

	</fieldset>

	<br />
	
	<acme:submit name="save" code="submit"/>
	<acme:cancel url="welcome/index.do" code="Cancel"/>
	
</form:form>