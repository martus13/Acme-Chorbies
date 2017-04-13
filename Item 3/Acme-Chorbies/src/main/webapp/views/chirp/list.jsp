<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="chirps" id="row" requestURI="${requestURI}">

	<acme:column code="chirp.subject" property="subject"/>
	
	<acme:column code="chirp.text" property="text"/>

	<acme:column code="chirp.attachments" property="attachments"/>
	
	<acme:column code="chirp.sentMoment" property="sentMoment"/>
	
	<jstl:choose>
		<jstl:when test="${imSender}">
			<acme:column code="chirp.recipient" property="recipient.name"/>
		</jstl:when>
		<jstl:otherwise>
			<acme:column code="chirp.sender" property="sender.name"/>
		</jstl:otherwise>
	</jstl:choose>

</display:table>