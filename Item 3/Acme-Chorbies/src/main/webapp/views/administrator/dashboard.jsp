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
<%@ taglib prefix="f" uri="http://example.com/functions" %>

<p><spring:message code="admin.dashboard.numChorbiesPerCountryCity" /></p>
<display:table name="numChorbiesPerCountryAndCity" id="row" requestURI="${requestURI }"
	pagesize="5" class="displaytag">
	
	<acme:column code="admin.dashboard.country" property="[0]" />
	
	<acme:column code="admin.dashboard.city" property="[1]" />
	
	<acme:column code="admin.dashboard.numChorbies" property="[2]" />
	
</display:table>

<div>
	<spring:message code="admin.dashboard.minMaxAvgAges" />
	<ul>
		<li>
			<b><spring:message code="admin.dashboard.min"/>:</b>
			<jstl:out value="${minMaxAvgAges[0]}"/>
		</li>
		<li>
			<b><spring:message code="admin.dashboard.max"/>:</b>
			<jstl:out value="${minMaxAvgAges[1]}"/>
		</li>
		<li>
			<b><spring:message code="admin.dashboard.avg"/>:</b>
			<jstl:out value="${minMaxAvgAges[2]}"/>
		</li>
	</ul>
</div>

<p>
	<spring:message code="admin.dashboard.ratioInvalidCreditCard" />: 
	<jstl:out value="${ratioInvalidCreditCard }" />.
</p>

<p><spring:message code="admin.dashboard.ratioActivitiesLoveFriendship" /></p>
<display:table name="ratioActivitiesLoveFriendship" id="row" requestURI="${requestURI }"
	pagesize="5" class="displaytag">
	
	<acme:column code="admin.dashboard.relationshipType" property="[0]" />
	
	<acme:column code="admin.dashboard.ratio" property="[1]" />
	
</display:table>

<p><spring:message code="admin.dashboard.chorbiesSortedByNumLikes" /></p>
<display:table name="chorbiesSortedByNumLikes" id="row" requestURI="${requestURI }"
	pagesize="5" class="displaytag">
	
	<spring:message code="admin.dashboard.chorbi.name" var="nameHeader" />
	<display:column title="${nameHeader}" sortable="true">
		<jstl:out value="${f:replaceAllPhoneAndEmail(row.name, '***')}" />
	</display:column>
	
	<spring:message code="admin.dashboard.chorbi.surname" var="surnameHeader" />
	<display:column title="${surnameHeader}" sortable="true">
		<jstl:out value="${f:replaceAllPhoneAndEmail(row.surname, '***')}" />
	</display:column>
	
	<acme:columnImages code="admin.dashboard.chorbi.picture" properties="${row.picture}" />
	
	<spring:message code="admin.dashboard.chorbi.description" var="descriptionHeader" />
	<display:column title="${descriptionHeader}" sortable="false">
		<jstl:out value="${f:replaceAllPhoneAndEmail(row.description, '***')}" />
	</display:column>
	
	<acme:column code="admin.dashboard.chorbi.birthDate" property="birthDate" format="{0,date,dd/MM/yyyy}" />
	
	<acme:column code="admin.dashboard.chorbi.genre" property="genre" />
	
	<acme:column code="admin.dashboard.chorbi.relationshipEngage" property="relationshipEngage" />
	
	<spring:message code="admin.dashboard.chorbi.coordinates" var="coordinatesHeader" />
	<display:column title="${coordinatesHeader}" sortable="false">
		<ul>
			<li>
				<spring:message code="admin.dashboard.chorbi.country" />: 
				<jstl:out value="${row.coordinates.country }" />
			</li>
			
			<jstl:if test="${not empty row.coordinates.state }">
				<li>
					<spring:message code="admin.dashboard.chorbi.state" />: 
					<jstl:out value="${row.coordinates.state }" />
				</li>
			</jstl:if>
			
			<jstl:if test="${not empty row.coordinates.provice }">
				<li>
					<spring:message code="admin.dashboard.chorbi.provice" />: 
					<jstl:out value="${row.coordinates.provice }" />
				</li>
			</jstl:if>
			
			<li>
				<spring:message code="admin.dashboard.chorbi.city" />: 
				<jstl:out value="${row.coordinates.city }" />
			</li>
		</ul>
	</display:column>
	
</display:table>

<spring:message code="admin.dashboard.minMaxAvgLikesPerChorbi" />
<ul>
	<li>
		<b><spring:message code="admin.dashboard.min"/>:</b>
		<jstl:out value="${minMaxAvgLikesPerChorbi[0]}"/>
	</li>
	<li>
		<b><spring:message code="admin.dashboard.max"/>:</b>
		<jstl:out value="${minMaxAvgLikesPerChorbi[1]}"/>
	</li>
	<li>
		<b><spring:message code="admin.dashboard.avg"/>:</b>
		<jstl:out value="${minMaxAvgLikesPerChorbi[2]}"/>
	</li>
</ul>

<spring:message  code="admin.dashboard.minMaxAvgReceivedChirps" />
<ul>
	<li>
		<b><spring:message code="admin.dashboard.min"/>:</b>
		<jstl:out value="${minMaxAvgReceivedChirps[0]}"/>
	</li>
	<li>
		<b><spring:message code="admin.dashboard.max"/>:</b>
		<jstl:out value="${minMaxAvgReceivedChirps[1]}"/>
	</li>
	<li>
		<b><spring:message code="admin.dashboard.avg"/>:</b>
		<jstl:out value="${minMaxAvgReceivedChirps[2]}"/>
	</li>
</ul>

<spring:message  code="admin.dashboard.MinMaxAvgChirpsSentPerChorbi" />
<ul>
	<li>
		<b><spring:message code="admin.dashboard.min"/>:</b>
		<jstl:out value="${minMaxAvgChirpsSentPerChorbi[0]}"/>
	</li>
	<li>
		<b><spring:message code="admin.dashboard.max"/>:</b>
		<jstl:out value="${minMaxAvgChirpsSentPerChorbi[1]}"/>
	</li>
	<li>
		<b><spring:message code="admin.dashboard.avg"/>:</b>
		<jstl:out value="${minMaxAvgChirpsSentPerChorbi[2]}"/>
	</li>
</ul>

<spring:message code="admin.dashboard.chorbiesMoreReceived" />
<display:table name="chorbiWithMostReceivedChirps" id="row" requestURI="${requestURI }"
	pagesize="5" class="displaytag">

	<spring:message code="admin.dashboard.chorbi.name" var="nameHeader" />
	<display:column title="${nameHeader}" sortable="true">
		<jstl:out value="${f:replaceAllPhoneAndEmail(row[1].name, '***')} ${f:replaceAllPhoneAndEmail(row[1].surname, '***')}" />
	</display:column>
	<spring:message code="admin.dashboard.chorbi.quantity" var="quantityHeader" />
	<display:column title="${quantityHeader}" sortable="true">
		<jstl:out value="${row[0]}" />
	</display:column>
</display:table>

<spring:message code="admin.dashboard.chorbiesMoreSent" />
<display:table name="chorbiWithMostSentChirps" id="row" requestURI="${requestURI }"
	pagesize="5" class="displaytag">

	<spring:message code="admin.dashboard.chorbi.name" var="nameHeader" />
	<display:column title="${nameHeader}" sortable="true">
		<jstl:out value="${f:replaceAllPhoneAndEmail(row[1].name, '***')} ${f:replaceAllPhoneAndEmail(row[1].surname, '***')} " />
	</display:column>
	<spring:message code="admin.dashboard.chorbi.quantity" var="quantityHeader" />
	<display:column title="${quantityHeader}" sortable="true">
		<jstl:out value="${row[0]}" />
	</display:column>
</display:table>

