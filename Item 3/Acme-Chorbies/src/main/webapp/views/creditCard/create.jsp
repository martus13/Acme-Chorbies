<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="creditCard/chorbi/create.do" modelAttribute="creditCard">
	
	<form:hidden path="id" />
	<form:hidden path="chorbi" />
	
	<acme:input path="holderName" code="creditCard.holderName" />
	<acme:input path="brandName" code="creditCard.brandName" />
	<acme:input path="number" code="creditCard.number" />
	<acme:input path="expirationMonth" code="creditCard.expirationMonth" />
	<acme:input path="expirationYear" code="creditCard.expirationYear" />
	<acme:input path="cvv" code="creditCard.cvv" />
	
	<acme:submit name="save" code="creditCard.save" />
	<acme:cancel url="creditCard/chorbi/list.do" code="creditCard.cancel" />
		
</form:form>
