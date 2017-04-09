<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="http://example.com/functions" prefix="f" %>

<jstl:choose>
	<jstl:when test="${not empty creditCard }">
		<display:table name="creditCard" id="row" requestURI="${requestURI }">
			
			<acme:column code="creditCard.holderName" property="holderName" />
			
			<acme:column code="creditCard.brandName" property="brandName" />
			
			<acme:column code="creditCard.number" property="number" />
			
			<display:column>
				<jstl:if test="${row.chorbi.id==principalId }">
					<a href="creditCard/chorbi/edit.do?creditCardId=${row.id }"><spring:message code="creditCard.edit" /></a>
				</jstl:if>
			</display:column>
			
			<display:column>
				<jstl:if test="${row.chorbi.id==principalId }">
						<form:form action="creditCard/chorbi/delete.do?creditCardId=${row.id }" modelAttribute="delete">
								<input type="submit" name="delete" value="<spring:message code="creditCard.delete" />" />
						</form:form>
				</jstl:if>
			</display:column>
			
		</display:table>
	</jstl:when>
	<jstl:otherwise>
		<a href="creditCard/chorbi/create.do">
			<spring:message code="creditCard.create" />
		</a>
	</jstl:otherwise>
</jstl:choose>

