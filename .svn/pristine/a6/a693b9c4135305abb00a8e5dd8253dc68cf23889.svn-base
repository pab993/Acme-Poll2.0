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

<form:form action="poller/register.do" modelAttribute="pollerForm">
	
	<fieldset > 

	<legend><b> <spring:message code="poller.accountData" /> </b></legend>
	
		<acme:textbox code="poller.username" path="username" mandatory="true"/>
		<br/>
	
		<acme:password code="poller.password" path="password" mandatory="true"/>
		<br/>
	
		<acme:password code="poller.repeatPassword" path="repeatPassword" mandatory="true"/>
	
	</fieldset>
	
	
	<fieldset > 
	
		<legend><b> <spring:message code="poller.personalData" /></b> </legend>
	
	
		<acme:textbox code="poller.name" path="name" mandatory="true"/>
		<br />
			
		<acme:textbox code="poller.surname" path="surname" mandatory="true"/>
		<br />
		
		<acme:textbox code="poller.email" path="email" mandatory="true"/>
		<br />

		<acme:textbox code="poller.phoneNumber" path="phoneNumber"/>
		<br />
		
		<acme:textbox code="poller.postalAddress" path="postalAddress"/>
		<br/>
	
	</fieldset>
	
	
	<div>
	<form:checkbox id="myCheck" onclick="comprobar();" path="check"/>
		<form:label path="check">
			<spring:message code="poller.accept" />
			<a href="termAndCondition/termAndCondition.do"><spring:message code="poller.lopd"/></a>
		</form:label>
	</div>
	

	<acme:submit id="submitButton" name="save" code="poller.submit"/>

</form:form>


<script type="text/javascript">

document.getElementById("submitButton").disabled = true;
document.getElementById("myCheck").checked = false;

function comprobar() {
	
	var x = document.getElementById("myCheck").checked;
	
	if(x == true){
		document.getElementById("submitButton").disabled = false;
	}
	else{
		document.getElementById("submitButton").disabled = true;
	}
}



</script>