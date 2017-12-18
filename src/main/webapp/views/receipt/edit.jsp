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

<form:form action="receipt/edit.do" modelAttribute="receipt" onsubmit="return validateForm()">
			
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="endorsed"/>
	<form:hidden path="bill"/>
			
	<acme:textbox code="receipt.pdf" path="pdf" mandatory="true"/>
			
	<br>
			
	<acme:submit name="save" code="receipt.save"/>
	<acme:cancel code="receipt.cancel" url ="bill/myList.do"/>
			
</form:form>

<script type="text/javascript">

function validateForm() {
	<spring:message code="receipt.ask" var="ask"/>
    return confirm('<jstl:out value="${ask}"/>');

}

</script>