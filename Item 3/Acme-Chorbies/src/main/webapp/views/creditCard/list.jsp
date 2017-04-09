<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>

<display:table name="creditCard" id="row" requestURI="${requestURI }">
	
	<acme:column code="creditCard.holderName" property="holderName" />
	
	<acme:column code="creditCard.brandName" property="brandName" />
	
	<acme:column code="creditCard.number" property="number" />
	
	
	
	
</display:table>