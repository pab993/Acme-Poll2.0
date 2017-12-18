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

	
		<form:form action="spam/edit.do" modelAttribute="spam">
			
			<form:hidden path="id" />
			<form:hidden path="version" />
			
			<acme:textbox code="spam.keywords" path="keywords" mandatory="true"/>
			
			<br>
			
			<acme:submit name="save" code="spam.save"/>
			<jstl:if test="${spam.id != 0}">
				<input type="submit" name="delete" value="<spring:message code="spam.delete" />" onclick="return confirm('<spring:message code="spam.confirm" />')" />
			</jstl:if>
			<acme:cancel code="spam.cancel" url ="spam/list.do"/>
			
		</form:form>
