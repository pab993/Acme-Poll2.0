<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<!-- Listing table -->

<jstl:choose>
	<jstl:when test="${cancel == true }">
		<h3 style="color:red;"><spring:message code="trackson.cancel"/></h3>
	</jstl:when>
	<jstl:otherwise>
		<display:table name="tracksons" id="row" requestURI="${requestURI}"
			pagesize="5" class="displaytag">
		<%-- 		<jstl:choose>
			 		<jstl:when test="${row.course.endDate gt currentMoment && row.administrator.id eq principal.id}">
						<jstl:set value="${'myTrackson'}" var="boldItalic" />
					</jstl:when>
					<jstl:when test="${row.course.endDate gt currentMoment && row.administrator.id ne principal.id}">
						<jstl:set value="${'notMyTrackson'}" var="boldItalic" />
					</jstl:when>
					<jstl:when test="${row.course.endDate lt currentMoment && (row.administrator.id eq principal.id)}">
						<jstl:set value="${'passEventMy'}" var="boldItalic" />
					</jstl:when>
					<jstl:when test="${row.course.endDate lt currentMoment && row.administrator.id ne principal.id}">
						<jstl:set value="${'passEventNotMy'}" var="boldItalic" />
					</jstl:when>
					<jstl:when test="${row.course.endDate lt currentMoment && actorVar == true}">
						<jstl:set value="${'passEvent'}" var="boldItalic" />
					</jstl:when>
				</jstl:choose> --%>
				
				<jstl:choose>
					<jstl:when test="${row.administrator.id eq principal.id && fn:length(row.description) >= 20}">
						<jstl:set value="${'myTrackson'}" var="boldItalic" />
					</jstl:when>
					<jstl:when test="${fn:length(row.description) < 20}">
						<jstl:set value="${'littleDescriptionTrackson'}" var="boldItalic" />
					</jstl:when>
					<jstl:otherwise>
						<jstl:set value="${'notMyTrackson'}" var="boldItalic" />
					</jstl:otherwise>
				</jstl:choose>
		
			
			<security:authorize access="hasRole('ADMIN')">
				<spring:message code="trackson.name" var="codeHeader" />
				<display:column property="name" title="${codeHeader}" class="${boldItalic}"/>
			</security:authorize>
		
			<spring:message code="trackson.displayMoment" var="displayMomentHeader" />
			<display:column property="displayMoment" title="${displayMomentHeader}"
				format="{0,date,dd/MM/yyyy HH:mm}" class="${boldItalic}"/>
		
			<spring:message code="trackson.title" var="titleHeader" />
			<display:column property="title" title="${titleHeader}" class="${boldItalic}"/>
		
			<spring:message code="trackson.description" var="descriptionHeader" />
			<display:column property="description" title="${descriptionHeader}" class="${boldItalic}"/>
		
			<spring:message code="trackson.score" var="scoreHeader" />
			<display:column property="score" title="${scoreHeader}" class="${boldItalic}"/>
		
			<spring:message code="trackson.administrator" var="administratorHeader" />
			<display:column property="administrator.name" title="${administratorHeader}" class="${boldItalic}"/>
		
			<spring:message code="trackson.poll" var="pollHeader" />
			<display:column property="poll.title" title="${pollHeader}" class="${boldItalic}"/>
		
			<security:authorize access="hasRole('ADMIN')">
		
				<spring:message code="trackson.justification" var="justificationHeader" />
				<display:column property="justification"
					title="${justificationHeader}" class="${boldItalic}"/>
		
				<spring:message code="trackson.cancel" var="cancelHeader" />
				<%-- <display:column property="cancel" title="${cancelHeader}" class="${boldItalic}"/> --%>
				<display:column class="${boldItalic}">
						<jstl:if test="${row.cancel == false}">
						<%-- 	<spring:message code="trackson.not.cancel" var="headerCancel"/>
							<jstl:out value="${headerCancel}"/> --%>
							<security:authorize access="hasRole('ADMIN')">
					
								<a href="trackson/editCancel.do?tracksonId=${row.id}"> <spring:message
									code="cancel" />
								</a>
					
							</security:authorize>
						</jstl:if>
						<jstl:if test="${row.cancel == true}">
						<spring:message code="trackson.cancelled" var="headerCancel"/>
							<jstl:out value="${headerCancel}"/>
						</jstl:if>
				</display:column>
		
				<%-- <security:authorize access="hasRole('ADMIN')">
					<display:column>
						<jstl:if test="${row.cancel == false}">
							<a href="trackson/editCancel.do?tracksonId=${row.id}"> <spring:message
									code="cancel" />
							</a>
						</jstl:if>
					</display:column>
				</security:authorize> --%>
		
			</security:authorize>
		
		</display:table>
	</jstl:otherwise>
</jstl:choose>