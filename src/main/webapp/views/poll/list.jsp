<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing table -->
	
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
			
			<security:authorize access="hasRole('POLLER')">
				<spring:message code = "poll.min" var = "minHeader" />
				<display:column title="${minHeader}">
					<jstl:if test="${(row.endActive.time - row.startActive.time)/(1000*60*60*24) <= minCS}">
						
						<spring:message code="poll.time.short"/>
					</jstl:if>
				</display:column>
			</security:authorize>
			
			
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
			
			<security:authorize access="hasRole('POLLER')">
				
				<display:column>
					<jstl:if test="${poller.id == row.poller.id}">
						<a href="question/list.do?pollId=${row.id}">
							<spring:message code="poll.question.list" />
						</a>
					</jstl:if>
				</display:column>
				
			</security:authorize>
			
			<security:authorize access="hasRole('ADMIN')">
			
				<display:column>
					<jstl:choose>
						<jstl:when test="${row.bill == null}">
							<a href="bill/create.do?pollId=${row.id}">
							<spring:message code="poll.bill.create" />
							</a>
						</jstl:when>
						<jstl:otherwise>
							<a href="bill/display.do?pollId=${row.id}">
							<spring:message code="poll.bill.display" />
							</a>
						</jstl:otherwise>
					</jstl:choose>
				</display:column>
				
			</security:authorize>
			
	<display:column>
		<%-- <jstl:choose>
			<jstl:when test="${fn:length(row.newors) == 0}">
			<security:authorize access="hasRole('ADMIN')">
				<jstl:if test="${row.endActive gt actualDate && row.startActive gt actualDate}">
				<jstl:if test="${row.endActive gt actualDate && row.startActive lt actualDate}">
					<a href="newor/create.do?pollId=${row.id}"> <spring:message
							code="newor.create" />
					</a>
				</jstl:if>
			</security:authorize>
			</jstl:when>
			<jstl:otherwise>
				<jstl:if test="${fn:length(row.newors) != 0 && row.newors[0].displayMoment lt actualDate}">
					<a href="newor/listByPoll.do?pollId=${row.id}"> <spring:message
							code="poll.newor" />
					</a>
				</jstl:if>
			</jstl:otherwise>
		</jstl:choose> --%>
		
		<a href="newor/listByPoll.do?pollId=${row.id}"> <spring:message code="poll.newor" />
		</a>
		
	</display:column>
						
</display:table>

	<security:authorize access="hasRole('POLLER')">
		<a href="poll/create.do">
			<spring:message code="poll.create" />
		</a>
	</security:authorize>
	<input type="button" name="cancel" value="<spring:message code="poll.cancel" />" onclick="javascript: window.location.replace('<spring:url value='/welcome/index.do' />')" />