<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing table -->
	
	<display:table name = "bills" id = "row" requestURI = "${requestURI}" pagesize = "5" class = "displaytag" >
			
			<spring:message code = "bill.momentDue" var = "momentDueHeader" />
			<display:column property = "momentDue" title = "${momentDueHeader}" format="{0,date,dd/MM/yyyy HH:mm}"/>
			
			<spring:message code = "bill.amountDue" var = "amountDueHeader" />
			<display:column property = "amountDue" title = "${amountDueHeader}"/>
			
			<spring:message code = "bill.poll" var = "pollHeader" />
			<display:column title = "${pollHeader}">
				<jstl:out value="${row.poll.ticker }"></jstl:out>
				<br>
				<jstl:out value="${row.poll.title }"></jstl:out>
			</display:column>
			
			<display:column>
				
				<jstl:choose>
		 			<jstl:when test="${row.paid == true}">
		 				
						<spring:message code="bill.paid.yes" />
						
		 			</jstl:when>
		 			<jstl:otherwise>
		 			
		 				<spring:message code="bill.paid.no"/>
		 				
		 			</jstl:otherwise>
		 		</jstl:choose>
				
			</display:column>
			
			
			<security:authorize access="hasRole('POLLER')">
				
				<display:column>
					<jstl:if test="${row.receipt != null }">
						<a href="receipt/display.do?receiptId=${row.receipt.id}">
							<spring:message code="bill.receipt" />
						</a>
					</jstl:if>
					<jstl:if test="${row.receipt == null && row.paid == false}">
						<a href="receipt/create.do?billId=${row.id}">
							<spring:message code="bill.receipt.create" />
						</a>
					</jstl:if>
					
				</display:column>
				
			</security:authorize>
			
			<security:authorize access="hasRole('ADMIN')">
				
				<display:column>
					<jstl:if test="${row.receipt != null }">
						<a href="receipt/display.do?receiptId=${row.receipt.id}">
							<spring:message code="bill.receipt" />
						</a>
					</jstl:if>
				</display:column>
				
			</security:authorize>
						
</display:table>


<input type="button" name="cancel" value="<spring:message code="bill.cancel" />" onclick="javascript: window.location.replace('<spring:url value='/welcome/index.do' />')" />


