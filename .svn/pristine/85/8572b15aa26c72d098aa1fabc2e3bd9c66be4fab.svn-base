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


<form:form action="actor/editProfile.do" modelAttribute="actorForm">
	
	<fieldset > 
	
		<legend><b> <spring:message code="actor.personalData" /></b> </legend>
	
	
		<acme:textbox code="actor.name" path="name" mandatory="true"/>
		<br />
			
		<acme:textbox code="actor.surname" path="surname" mandatory="true"/>
		<br />
			
		<acme:textbox code="actor.email" path="email" mandatory="true"/>
		<br />
			
		<acme:textbox code="actor.phoneNumber" path="phoneNumber"/>
		<br />
		
		<acme:textbox code="actor.postalAddress" path="postalAddress"/>
		<br/>
	
	</fieldset>
	

	<acme:submit id="submitButton" name="save" code="actor.submit"/>
	<acme:cancel code="actor.cancel" url="actor/seeProfile.do" />

</form:form>