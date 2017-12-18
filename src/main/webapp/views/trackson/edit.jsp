<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<form:form action="trackson/edit.do" modelAttribute="trackson">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:hidden path="cancel" />
	
	<form:hidden path="administrator" />
	<form:hidden path="poll" />

	<fieldset>

		<legend>
			<b> <spring:message code="trackson.create" />
			</b>
		</legend>
		
		<acme:textbox code="trackson.name" path="name"/>
		<br/>
		
		<acme:textarea code="trackson.title" path="title"/>
		<br/>
		
		<acme:textarea code="trackson.description" path="description"/>
		<br/>
		
		<acme:textbox code="trackson.displayMoment" path="displayMoment"/>
		<br/>
		
		<spring:message code="trackson.score" />:
		<form:select path="score">
			<spring:message code="score.n1" var="HRHeader"/><form:option value="-1" label="${HRHeader}"/>	
			<spring:message code="score.0" var="RHeader"/><form:option value="0" label="${RHeader}" />	
			<spring:message code="score.1" var="NRHeader"/><form:option value="1" label="${NRHeader}" />
		</form:select>
		
	</fieldset>
	
	<div>
		<acme:submit name="save" code="submit" />
		<acme:cancel code="cancel" url="course/list.do" />
	</div>
	
</form:form>