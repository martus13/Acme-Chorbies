<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="banners" id="row" requestURI="${requestURI }">
	
	<acme:columnImages code="banner.picture" properties="${row.picture}" maxHeight="150px" />
	
	<display:column>
		<a href="banner/administrator/edit.do?bannerId=${row.id}">
			<spring:message code="banner.edit" />
		</a>
	</display:column>
	
	<display:column>
		<form:form action="banner/administrator/delete.do?bannerId=${row.id}" modelAttribute="banner">
			<acme:submit name="delete" code="banner.delete" />
		</form:form>
	</display:column>
	
</display:table>

<a href="banner/administrator/create.do">
	<spring:message code="banner.create" />
</a>
