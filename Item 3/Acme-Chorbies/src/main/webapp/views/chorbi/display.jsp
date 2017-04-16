<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="f" uri="http://example.com/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<security:authentication var="principalUserAccount" property="principal" />

<div>
	<ul>
		<li>
			<b><spring:message code="chorbi.name" />:</b>
			<jstl:out value="${f:replaceAllPhoneAndEmail(chorbi.name, '***')}" />
		</li>
		
		<li>
			<b><spring:message code="chorbi.surname" />:</b>
			<jstl:out value="${f:replaceAllPhoneAndEmail(chorbi.surname, '***')}" />
		</li>
		
		<li>
			<b><spring:message code="chorbi.picture" />:</b>
			<img src="${chorbi.picture }" style="max-height: 200px;" />
		</li>
		
		<li>
			<b><spring:message code="chorbi.birthDate" />:</b>
			<fmt:formatDate value="${chorbi.birthDate}" pattern="dd/MM/yyyy" />
		</li>
		
		<li>
			<b><spring:message code="chorbi.description" />:</b>
			<jstl:out value="${f:replaceAllPhoneAndEmail(chorbi.description, '***')}" />
		</li>
		
		<li>
			<b><spring:message code="chorbi.genre" />:</b>
			<jstl:out value="${chorbi.genre}" />
		</li>
		
		<li>
			<b><spring:message code="chorbi.relationshipEngage" />:</b>
			<jstl:out value="${chorbi.relationshipEngage}" />
		</li>
		
		<li>
			<b><spring:message code="chorbi.coordinates" />:</b>
			<ul>
				<li>
					<spring:message code="chorbi.country" />: 
					<jstl:out value="${f:replaceAllPhoneAndEmail(chorbi.coordinates.country, '***') }" />
				</li>
				
				<jstl:if test="${not empty row.coordinates.state }">
					<li>
						<spring:message code="chorbi.state" />: 
						<jstl:out value="${f:replaceAllPhoneAndEmail(chorbi.coordinates.state, '***') }" />
					</li>
				</jstl:if>
				
				<jstl:if test="${not empty chorbi.coordinates.provice }">
					<li>
						<spring:message code="chorbi.provice" />: 
						<jstl:out value="${f:replaceAllPhoneAndEmail(chorbi.coordinates.provice, '***') }" />
					</li>
				</jstl:if>
				
				<li>
					<spring:message code="chorbi.city" />: 
					<jstl:out value="${f:replaceAllPhoneAndEmail(chorbi.coordinates.city, '***') }" />
				</li>
			</ul>
		</li>
		
		<li>
			<b><spring:message code="chorbi.receivedLikes" />:</b><br />
			<display:table name="${chorbi.receivedLikes}" id="row" >
			
				<acme:column code="chorbi.like.likeMoment" property="likeMoment" format="{0,date,dd/MM/yyyy HH:mm}" />
				
				<spring:message code="chorbi.like.comment" var="commentHeader" />
				<display:column title="${commentHeader}" sortable="false">
					<jstl:out value="${f:replaceAllPhoneAndEmail(row.comment, '***')}" />
				</display:column>
				
				<spring:message code="chorbi.like.givenBy" var="givenByHeader" />
				<display:column title="${givenByHeader}" sortable="false">
					<a href="chorbi/actor/display.do?chorbiId=${row.givenBy.id }">
						<jstl:out value="${f:replaceAllPhoneAndEmail(row.givenBy.name, '***') }" />
					</a>
				</display:column>
				
				<display:column sortable="false">
					<jstl:if test="${principalUserAccount.id == row.givenBy.userAccount.id }">
						<a href="like/chorbi/cancel.do?chorbiToId=${chorbi.id }">
							<spring:message code="chorbi.like.cancel" />
						</a>
					</jstl:if>
				</display:column>
			</display:table>
			
			<security:authorize access="hasRole('CHORBI')" >
				<jstl:set var="canLike" value="true" />
					
				<jstl:forEach items="${chorbi.receivedLikes }" var="receivedLikes" >
					<jstl:if test="${receivedLikes.givenBy.userAccount.id == principalUserAccount.id }">
						<jstl:set var="canLike" value="false" />
					</jstl:if>
				</jstl:forEach>
				
				<jstl:if test="${canLike && principalUserAccount.id != chorbi.userAccount.id }">
					<a href="like/chorbi/create.do?chorbiToId=${chorbi.id }">
						<spring:message code="chorbi.like" />
					</a>
				</jstl:if>
			</security:authorize>
		</li>
		
		<security:authorize access="hasRole('CHORBI')" >
			<jstl:if test="${principalUserAccount.id != chorbi.userAccount.id}">
				<li>
					<input type="submit" name="chirp" value="<spring:message code="chorbi.chirp" />" onclick="location='chirp/chorbi/create.do?receiverId=${chorbi.id}'"/>
				</li>
			</jstl:if>
		</security:authorize>
		
	</ul>
</div>