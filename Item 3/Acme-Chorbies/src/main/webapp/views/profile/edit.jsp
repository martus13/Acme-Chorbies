<%--
 * action-1.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form action="${requestURI }" modelAttribute="${actorForm }">
	
	<fieldset>
		<legend>
			<spring:message code="profile.userData" />
		</legend>
		<acme:input path="username" code="profile.username" />
		
		<acme:password path="password" code="profile.password" />
		
		<acme:password path="confirmPassword" code="profile.confirmPassword" />
		<br />
	</fieldset>
		
	<fieldset>
		<legend>
			<spring:message code="profile.personalData" />
		</legend>
		<acme:input path="name" code="profile.name" />
		
		<acme:input path="surname" code="profile.surname" />

		<acme:input path="email" code="profile.email" />
		
		<acme:input path="phoneNumber" code="profile.phoneNumber" />
		
		<security:authorize access="hasRole('CHORBI')">
		
				<acme:textarea path="picture" code="profile.picture" rows="3" />
				
				<acme:textarea path="description" code="profile.description" rows="3" />
				
				<acme:input path="birthDate" code="profile.birthDate" />
				
				<div>
					<form:label path="genre">
						<spring:message code="profile.genre" />
					</form:label>	
					<form:select path="genre">
						<form:option value="0" label="----" />		
						<jstl:forEach items="${genres }" var="genre">
							<form:option value="${genre}" label="${genre}" />
						</jstl:forEach>
					</form:select>
					<form:errors path="genre" cssClass="error" />
				</div>
				
				<div>
					<form:label path="relationshipEngage">
						<spring:message code="profile.relationshipEngage" />
					</form:label>	
					<form:select path="relationshipEngage">
						<form:option value="0" label="----" />		
						<jstl:forEach items="${relationshipTypes }" var="relationshipEngage">
							<form:option value="${relationshipEngage}" label="${relationshipEngage}" />
						</jstl:forEach>
					</form:select>
					<form:errors path="relationshipEngage" cssClass="error" />
				</div>
				
		</security:authorize>
		
	</fieldset>
	<br>
		
	<security:authorize access="hasRole('CHORBI')">
		
		<fieldset>
			<legend>
				<spring:message code="profile.coordinates" />
			</legend>
			<acme:input path="coordinates.country" code="profile.country" />
			
			<acme:input path="coordinates.state" code="profile.state" />
			
			<acme:input path="coordinates.provice" code="profile.provice" />
			
			<acme:input path="coordinates.city" code="profile.city" />
			<br />
		</fieldset>
		<br>
		
	</security:authorize>
		
	<acme:submit name="save" code="profile.save" />
	<acme:cancel url="welcome/index.do" code="profile.cancel" />
		
</form:form>
