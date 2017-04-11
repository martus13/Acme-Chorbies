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
					<b><spring:message code="searchTemplate.genre" />:</b>
					<jstl:out value="${searchTemplate.genre}" />
				</li>
				
				<li>
					<b><spring:message code="searchTemplate.relationshipType" />:</b>
					<jstl:out value="${searchTemplate.relationshipType}" />
				</li>
				
				<li>
					<b><spring:message code="searchTemplate.approximateAge" />:</b>
					<jstl:out value="${searchTemplate.approximateAge}" />
				</li>
				
				<li>
					<b><spring:message code="searchTemplate.singleKeyword" />:</b>
					<jstl:out value="${searchTemplate.singleKeyword}" />
				</li>
				
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
			<acme:button url="searchTemplate/chorbi/edit.do?searchTemplateId=${searchTemplate.id }" code="searchTemplate.edit"/>
			<%-- <a href="searchTemplate/chorbi/edit.do?searchTemplateId=${searchTemplate.id }"><spring:message code="searchTemplate.edit" /></a> --%>
		</jstl:otherwise>
	</jstl:choose>
	<br>
	<jstl:if test="${not empty searchTemplate }">
		<acme:button url="searchTemplate/chorbi/findBySearchTemplate.do?searchTemplateId=${searchTemplate.id }" code="searchTemplate.search"/>
		<%-- <a href="searchTemplate/chorbi/findBySearchTemplate.do?searchTemplateId=${searchTemplate.id }"><spring:message code="searchTemplate.search" /></a> --%>
	</jstl:if>
</div>
	