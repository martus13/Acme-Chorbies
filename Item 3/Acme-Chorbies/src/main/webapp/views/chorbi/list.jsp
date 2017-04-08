<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>

<display:table name="chorbies" id="row" requestURI="${requestURI }">
	
	<acme:column code="chorbi.name" property="name" />
	
	<acme:column code="chorbi.surname" property="surname" />
	
	<acme:columnImages code="chorbi.picture" properties="${row.picture}" />
	
	<spring:message code="chorbi.description" var="descriptionHeader" />
	<display:column title="${descriptionHeader}" sortable="false">
		<jstl:set var="string2" value="${f:replaceAllPhoneAndEmail(row. description, '***')}" />
		<jstl:out value="${string2 }" />
	</display:column>
	
	<acme:column code="chorbi.birthDate" property="birthDate" format="{0,date,dd/MM/yyyy}" />
	
	<acme:column code="chorbi.genre" property="genre" />
	
	<acme:column code="chorbi.relationshipEngage" property="relationshipEngage" />
	
	<spring:message code="chorbi.coordinates" var="coordinatesHeader" />
	<display:column title="${coordinatesHeader}" sortable="false">
		<ul>
			<li>
				<spring:message code="chorbi.country" />: 
				<jstl:out value="${row.coordinates.country }" />
			</li>
			
			<jstl:if test="${not empty row.coordinates.state }">
				<li>
					<spring:message code="chorbi.state" />: 
					<jstl:out value="${row.coordinates.state }" />
				</li>
			</jstl:if>
			
			<jstl:if test="${not empty row.coordinates.provice }">
				<li>
					<spring:message code="chorbi.provice" />: 
					<jstl:out value="${row.coordinates.provice }" />
				</li>
			</jstl:if>
			
			<li>
				<spring:message code="chorbi.city" />: 
				<jstl:out value="${row.coordinates.city }" />
			</li>
		</ul>
	</display:column>  
	
	<security:authorize access="hasRole('ADMIN')">
		<display:column>
			<jstl:choose>
				<jstl:when test="${row.banned }">
					<form:form action="chorbi/administrator/unban.do?chorbiId=${row.id}" modelAttribute="chorbi">
						<acme:submit name="unban" code="booking.unban" />
					</form:form>
				</jstl:when>
				<jstl:otherwise>
					<form:form action="chorbi/administrator/ban.do?chorbiId=${row.id}" modelAttribute="chorbi">
						<acme:submit name="ban" code="booking.ban" />
					</form:form>
				</jstl:otherwise>
			</jstl:choose>
		</display:column>
	</security:authorize>
	
	
</display:table>