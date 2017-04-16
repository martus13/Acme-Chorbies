<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="f" uri="http://example.com/functions" %>

<security:authentication var="principalUserAccount" property="principal" />
<display:table name="chorbies" id="row" requestURI="${requestURI }">
	
	<spring:message code="chorbi.name" var="nameHeader" />
	<display:column title="${nameHeader}" sortable="true">
		<jstl:out value="${f:replaceAllPhoneAndEmail(row.name, '***')}" />
	</display:column>
	
	<spring:message code="chorbi.surname" var="surnameHeader" />
	<display:column title="${surnameHeader}" sortable="true">
		<jstl:out value="${f:replaceAllPhoneAndEmail(row.surname, '***')}" />
	</display:column>
	
	<acme:columnImages code="chorbi.picture" properties="${row.picture}" />
	
	<spring:message code="chorbi.description" var="descriptionHeader" />
	<display:column title="${descriptionHeader}" sortable="false">
		<jstl:out value="${f:replaceAllPhoneAndEmail(row.description, '***')}" />
	</display:column>
	
	<acme:column code="chorbi.birthDate" property="birthDate" format="{0,date,dd/MM/yyyy}" />
	
	<acme:column code="chorbi.genre" property="genre" />
	
	<acme:column code="chorbi.relationshipEngage" property="relationshipEngage" />
	
	<spring:message code="chorbi.coordinates" var="coordinatesHeader" />
	<display:column title="${coordinatesHeader}" sortable="false">
		<ul>
			<li>
				<spring:message code="chorbi.country" />: 
				<jstl:out value="${f:replaceAllPhoneAndEmail(row.coordinates.country, '***') }" />
			</li>
			
			<jstl:if test="${not empty row.coordinates.state }">
				<li>
					<spring:message code="chorbi.state" />: 
					<jstl:out value="${f:replaceAllPhoneAndEmail(row.coordinates.state, '***') }" />
				</li>
			</jstl:if>
			
			<jstl:if test="${not empty row.coordinates.provice }">
				<li>
					<spring:message code="chorbi.provice" />: 
					<jstl:out value="${f:replaceAllPhoneAndEmail(row.coordinates.provice, '***') }" />
				</li>
			</jstl:if>
			
			<li>
				<spring:message code="chorbi.city" />: 
				<jstl:out value="${f:replaceAllPhoneAndEmail(row.coordinates.city, '***') }" />
			</li>
		</ul>
	</display:column>  
	
	<display:column>
		<a href="chorbi/actor/display.do?chorbiId=${row.id }">
			<spring:message code="chorbi.display" />
		</a>
	</display:column>
	
	<security:authorize access="hasRole('CHORBI')">
		<display:column>
			<jstl:if test="${principalUserAccount.id != row.userAccount.id }">
				<jstl:set var="canLike" value="true" />
				
				<jstl:forEach items="${row.receivedLikes }" var="receivedLikes" >
					<jstl:if test="${receivedLikes.givenBy.userAccount.id == principalUserAccount.id }">
						<jstl:set var="canLike" value="false" />
					</jstl:if>
				</jstl:forEach>
				
				<jstl:choose>
					<jstl:when test="${canLike }">
						<a href="like/chorbi/create.do?chorbiToId=${row.id }">
							<spring:message code="chorbi.like" />
						</a>
					</jstl:when>
					<jstl:otherwise>
						<a href="like/chorbi/cancel.do?chorbiToId=${row.id }">
							<spring:message code="chorbi.like.cancel" />
						</a>
					</jstl:otherwise>
				</jstl:choose>
			</jstl:if>
		
		</display:column>
			
			
		<display:column>
		<jstl:if test="${principalUserAccount.id != row.userAccount.id }">
			<a href="chirp/chorbi/create.do?receiverId=${row.id}">
				<spring:message code="chorbi.chirp" />
			</a>
		</jstl:if>
		</display:column>
	</security:authorize>
	
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