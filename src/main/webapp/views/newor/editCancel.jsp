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

<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<form:form action="newor/editCancel.do" modelAttribute="newor">

  <form:hidden path="id" />
  <form:hidden path="version" />

  <form:hidden path="mark" />
  <form:hidden path="displayMoment" />
  <form:hidden path="cancel" />
  <form:hidden path="title" />
  <form:hidden path="description" />
  <form:hidden path="momentCreated" />

  <form:hidden path="administrator" />
  <form:hidden path="poll" />

  <fieldset>

    <legend>
      <b> <spring:message code="newor.cancelForms" />
      </b>
    </legend>

<%--     <h3 style="color: red;">
      <spring:message code="newor.info" />
    </h3>
    <br> --%>

    <acme:textarea code="newor.justification" path="justification" />
    <br />

  </fieldset>

  <div>
    <acme:submit name="save" code="submit" />
    <acme:cancel code="cancel" url="newor/list.do" />
  </div>

</form:form>