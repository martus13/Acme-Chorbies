<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<form:form method="post" action="searchTemplate/chorbi/edit.do" modelAttribute="searchTemplate" >
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="chorbi"/>
	<form:hidden path="results"/>
	<form:hidden path="searchTime"/>
	
	
	<div>
			<form:label path="genre">
				<spring:message code="searchTemplate.genre" />
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
			<form:label path="relationshipType">
				<spring:message code="searchTemplate.relationshipType" />
			</form:label>	
			<form:select path="relationshipType">
				<form:option value="${null }" label="----" />		
				<jstl:forEach items="${relationshipTypes }" var="relationshipType">
					<form:option value="${relationshipType}" label="${relationshipType}" />
				</jstl:forEach>
			</form:select>
			<form:errors path="relationshipType" cssClass="error" />
		</div>
	
	<acme:input code="searchTemplate.approximateAge" path="approximateAge" />
	<acme:input code="searchTemplate.singleKeyword" path="singleKeyword" />
	<acme:input code="searchTemplate.country" path="country" />
	<acme:input code="searchTemplate.state" path="state" />
	<acme:input code="searchTemplate.province" path="province" />
	<acme:input code="searchTemplate.city" path="city" />
	
	<acme:submit name="save" code="searchTemplate.save" />
	<jstl:if test="${searchTemplate.id!=0 }">
		<acme:submit name="delete" code="searchTemplate.delete" />
	</jstl:if>
	
	<acme:cancel url="searchTemplate/chorbi/display.do" code="searchTemplate.cancel" />
	
</form:form>
	