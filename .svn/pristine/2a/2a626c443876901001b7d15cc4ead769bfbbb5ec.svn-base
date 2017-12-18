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
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<spring:message code="actor.name"/>:
<jstl:out value="${actor.name}"></jstl:out>
<br>
<spring:message code="actor.surname"/>:
<jstl:out value="${actor.surname}"></jstl:out>
<br>
<spring:message code="actor.email"/>:
<jstl:out value="${actor.email}"></jstl:out>
<br>
<spring:message code="actor.phoneNumber"/>:
<jstl:out value="${actor.phoneNumber}"></jstl:out>
<br>
<spring:message code="actor.postalAddress"/>:
<jstl:out value="${actor.postalAddress}"></jstl:out>
<br>


<br>
<br>
<br>

<jstl:if test="${actor.id == principal.id}">
	<a href="actor/editProfile.do"><spring:message code="actor.edit.profile"/></a>
</jstl:if>
