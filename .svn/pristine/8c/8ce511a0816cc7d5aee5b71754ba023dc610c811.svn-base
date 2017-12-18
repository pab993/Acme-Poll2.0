<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing table -->
	
	<display:table name = "spams" id = "row" requestURI = "${requestURI}" pagesize = "5" class = "displaytag" >

			<spring:message code = "spam.keywords" var = "keywordsHeader" />
			<display:column property = "keywords" title="${keywordsHeader }"/>
			
			
			<display:column>
				<a href="spam/edit.do?spamId=${row.id}">
						<spring:message code="spam.edit" />
				</a>
			</display:column>
			
						
</display:table>

			<a href="spam/create.do">
				<spring:message code="spam.create" />
			</a>

	<input type="button" name="cancel" value="<spring:message code="spam.cancel" />" onclick="javascript: window.location.replace('<spring:url value='/' />')" />