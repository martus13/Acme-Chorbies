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
			<spring:message code="chorbi.userData" />
		</legend>
		<acme:input path="username" code="chorbi.username" />
		
		<acme:password path="password" code="chorbi.password" />
		
		<acme:password path="confirmPassword" code="chorbi.confirmPassword" />
		<br />
	</fieldset>
		
	<fieldset>
		<legend>
			<spring:message code="chorbi.personalData" />
		</legend>
		<acme:input path="name" code="chorbi.name" />
		
		<acme:input path="surname" code="chorbi.surname" />

		<acme:input path="email" code="chorbi.email" />
		
		<acme:input path="phoneNumber" code="chorbi.phoneNumber" />
		
		<acme:textarea path="picture" code="chorbi.picture" rows="3" />
		
		<acme:textarea path="description" code="chorbi.description" rows="3" />
		
		<acme:input path="birthDate" code="chorbi.birthDate" />
		
		<div>
			<form:label path="genre">
				<spring:message code="chorbi.genre" />
			</form:label>	
			<form:select path="genre">
				<form:option value="${null }" label="----" />		
				<jstl:forEach items="${genres }" var="genre">
					<form:option value="${genre}" label="${genre}" />
				</jstl:forEach>
			</form:select>
			<form:errors path="genre" cssClass="error" />
		</div>
		
		<div>
			<form:label path="relationshipEngage">
				<spring:message code="chorbi.relationshipEngage" />
			</form:label>	
			<form:select path="relationshipEngage">
				<form:option value="${null }" label="----" />		
				<jstl:forEach items="${relationshipTypes }" var="relationshipEngage">
					<form:option value="${relationshipEngage}" label="${relationshipEngage}" />
				</jstl:forEach>
			</form:select>
			<form:errors path="relationshipEngage" cssClass="error" />
		</div>
		
	</fieldset>
	<br>
	
	<fieldset>
		<legend>
			<spring:message code="chorbi.coordinates" />
		</legend>
		<acme:input path="coordinates.country" code="chorbi.country" />
		
		<acme:input path="coordinates.state" code="chorbi.state" />
		
		<acme:input path="coordinates.provice" code="chorbi.provice" />
		
		<acme:input path="coordinates.city" code="chorbi.city" />
		<br />
	</fieldset>
	<br>
	
	<security:authorize access="isAnonymous()">
		<form:checkbox path="isAgree"/>
		<form:label path="isAgree">
			<spring:message code="chorbi.acceptConditions" />
			<a href="misc/conditions.do" target="_blank"><spring:message code="chorbi.conditions" /></a>
		</form:label>
		<br />
	</security:authorize>
	
	<acme:submit name="save" code="chorbi.save" />
	<acme:cancel url="welcome/index.do" code="chorbi.cancel" />
		
</form:form>
	

<script>
	function GeneralYes(){
		document.getElementById("general").disabled = false;
	}
	
	function GeneralNo(){
		document.getElementById("general").disabled = true;
	}
</script>
