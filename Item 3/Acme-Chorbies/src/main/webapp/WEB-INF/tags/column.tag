<%@ tag language="java" body-content="empty" %>
 
 <%-- Taglibs --%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<%-- Attributes --%> 
 
<%@ attribute name="code" required="true" %>
<%@ attribute name="property" required="true" %>

<%@ attribute name="sortable" required="false" %>
<%@ attribute name="format" required="false" %>
<%@ attribute name="style" required="false" %>

<jstl:if test="${sortable == null}">
	<jstl:set var="sortable" value="true" />
</jstl:if>

<%-- Definition --%>

<spring:message code="${code}" var="titleHeader" />
<display:column property="${property}" title="${titleHeader}" sortable="${sortable}" format="${format}"  />
