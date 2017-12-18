<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="poll/search.do">

	<h4>
		<spring:message code="poll.keyword" />
	</h4>

	<input type="text" name="keyword" />
	<input type="submit" name="search" value="<spring:message code = "poll.search" />" />

</form:form>

<br>
<br>
		
	<display:table name = "polls" id = "row" requestURI = "${requestURI}" pagesize = "5" class = "displaytag" >

			<spring:message code = "poll.banner" var = "bannerHeader" />
			<display:column style="width:48px" title="${bannerHeader}"><img height="48" width="48" src="${row.banner}"></display:column>

			<spring:message code = "poll.title" var = "titleHeader" />
			<display:column property = "title" title = "${titleHeader}" />
		
			<spring:message code = "poll.description" var="descriptionHeader" />
			<display:column property="description" title="${descriptionHeader}" />
			
			<spring:message code = "poll.startActive" var = "startActiveHeader" />
			<display:column property = "startActive" title = "${startActiveHeader}" format="{0,date,dd/MM/yyyy HH:mm}"/>
			
			<spring:message code = "poll.endActive" var = "endActiveHeader" />
			<display:column property = "endActive" title = "${endActiveHeader}" format="{0,date,dd/MM/yyyy HH:mm}"/>
			
			<spring:message code = "poll.ticker" var = "tickerHeader" />
			<display:column property = "ticker" title = "${tickerHeader}" />
			
		 	<display:column>
		 		<jstl:choose>
		 			<jstl:when test="${row.endActive gt actualDate && row.startActive lt actualDate}">
		 				<a href="poll/display.do?pollId=${row.id}">
							<spring:message code="poll.display" />
						</a>
		 			</jstl:when>
		 			<jstl:when test="${row.startActive gt actualDate && row.endActive gt actualDate}">
		 				<spring:message code="poll.not.started"/>
		 			</jstl:when>
		 			<jstl:otherwise>
		 			
		 				<spring:message code="poll.finished"/>
		 				
		 			</jstl:otherwise>
		 		</jstl:choose>
			</display:column>
			
			<display:column>
				<jstl:choose>
					<jstl:when test="${row.startActive lt actualDate }">
						<a href="poll/results.do?pollId=${row.id}">
							<spring:message code="poll.results" />
						</a>
					</jstl:when>
					<jstl:otherwise>
						<spring:message code="poll.not.started"/>
					</jstl:otherwise>
				</jstl:choose>
			</display:column>
			
			<security:authorize access="hasRole('POLLER')">
				
				<display:column>
					<jstl:if test="${poller.id == row.poller.id}">
						<a href="poll/edit.do?pollId=${row.id}">
							<spring:message code="poll.edit" />
						</a>
					</jstl:if>
				</display:column>
				
			</security:authorize>
						
</display:table>


		<input type="button" name="cancel" value="<spring:message code="poll.cancel" />"
		onclick="javascript: window.location.replace('<spring:url value='/' />')" />