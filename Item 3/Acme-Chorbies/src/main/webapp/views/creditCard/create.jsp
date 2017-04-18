<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="creditCard/chorbi/create.do" modelAttribute="creditCard">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="chorbi" />
	
	<acme:input path="holderName" code="creditCard.holderName" />
	<div>
		<form:label path="brandName">
			<spring:message code="creditCard.brandName" />
		</form:label>	
		<form:select path="brandName">
			<form:option value="${null }" label="----" />
			<form:option value="VISA" label="VISA" />
			<form:option value="MASTERCARD" label="MASTERCARD" />
			<form:option value="DISCOVER" label="DISCOVER" />
			<form:option value="DINNERS" label="DINNERS" />
			<form:option value="AMEX" label="AMEX" />
		</form:select>
		<form:errors path="brandName" cssClass="error" />
	</div>
	<acme:input path="number" code="creditCard.number" />
	<acme:input path="expirationMonth" code="creditCard.expirationMonth" type="number" min="1" max="12" />
	<acme:input path="expirationYear" code="creditCard.expirationYear" type="number" />
	<acme:input path="cvv" code="creditCard.cvv" type="number" />
	
	<acme:submit name="save" code="creditCard.save" />
	<acme:cancel url="creditCard/chorbi/list.do" code="creditCard.cancel" />
		
</form:form>
