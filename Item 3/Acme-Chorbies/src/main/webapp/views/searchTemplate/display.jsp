<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div>
	<jstl:choose>
		<jstl:when test="${empty searchTemplate }">
			<a href="searchTemplate/chorbi/create.do"><spring:message code="searchTemplate.create" /></a>
		</jstl:when>
		<jstl:otherwise>
			<ul>
				<li>
					<b><spring:message code="searchTemplate.city"/>:</b>
					<jstl:out value="${searchTemplate.city}"/>
				</li>
				
				<li>
					<b><spring:message code="searchTemplate.state"/>:</b>
					<jstl:out value="${searchTemplate.state}"/>
				</li>
				
				<li>
					<b><spring:message code="searchTemplate.country"/>:</b>
					<jstl:out value="${searchTemplate.country}"/>
				</li>
				
				<li>
					<b><spring:message code="searchTemplate.province" />:</b>
					<jstl:out value="${searchTemplate.province}" />
				</li>
						
			</ul>
			
			<a href="searchTemplate/chorbi/edit.do?searchTemplateId=${searchTemplate.id }"><spring:message code="searchTemplate.edit" /></a>
		</jstl:otherwise>
	</jstl:choose>
	<br>
	<%-- <jstl:if test="${not empty searchTemplate }">
		<a href="property/tenant/findByFinder.do?searchTemplateId=${searchTemplate.id }"><spring:message code="searchTemplate.searchProperties" /></a>
	</jstl:if> --%>
</div>
	