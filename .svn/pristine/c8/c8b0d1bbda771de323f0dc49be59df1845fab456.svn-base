<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

	<display:table name = "pollers" id = "row" requestURI = "${requestURI}" pagesize = "5" class = "displaytag" >

			<spring:message code = "poller.name" var = "nameHeader" />
			<display:column property = "name" title = "${nameHeader}" />
		
			<spring:message code = "poller.surname" var="surnameHeader" />
			<display:column property="surname" title="${surnameHeader}" />
			
			<spring:message code = "poller.email" var = "emailHeader" />
			<display:column property = "email" title = "${emailHeader}" />
			
			<spring:message code = "poller.phoneNumber" var = "phoneNumberHeader" />
			<display:column property = "phoneNumber" title = "${phoneNumberHeader}" />
			
			<spring:message code = "poller.postalAddress" var = "postalAddressHeader" />
			<display:column property = "postalAddress" title = "${postalAddressHeader}" />
			
			<security:authorize access="hasRole('ADMIN')">
				<display:column>
					<a href="administrator/ban.do?pollerId=${row.id}">
						<jstl:choose>
							<jstl:when test="${row.userAccount.enabled == false}">
								<spring:message code="poller.unban" />
							</jstl:when>
							<jstl:otherwise>
								<spring:message code="poller.ban" />
							</jstl:otherwise>
						</jstl:choose>
					</a>
				</display:column>
			</security:authorize>
			
									
</display:table>


			<input type="button" name="cancel" value="<spring:message code="poller.cancel" />"
		onclick="javascript: window.location.replace('<spring:url value='/' />')" />