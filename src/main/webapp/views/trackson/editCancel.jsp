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
<form:form action="trackson/editCancel.do" modelAttribute="trackson">

  <form:hidden path="id" />
  <form:hidden path="version" />

  <form:hidden path="name" />
  <form:hidden path="displayMoment" />
  <form:hidden path="cancel" />
  <form:hidden path="title" />
  <form:hidden path="description" />
  <form:hidden path="score" />

  <form:hidden path="administrator" />
  <form:hidden path="poll" />

  <fieldset>

    <legend>
      <b> <spring:message code="trackson.cancelForms" />
      </b>
    </legend>

<%--     <h3 style="color: red;">
      <spring:message code="trackson.info" />
    </h3>
    <br> --%>

    <acme:textarea code="trackson.justification" path="justification" />
    <br />

  </fieldset>

  <div>
    <acme:submit name="save" code="submit" />
    <acme:cancel code="cancel" url="trackson/list.do" />
  </div>

</form:form>