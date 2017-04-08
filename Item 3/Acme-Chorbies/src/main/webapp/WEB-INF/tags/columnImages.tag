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
<%@ attribute name="properties" required="true" %>

<%@ attribute name="maxHeight" required="false" %>

<jstl:if test="${maxHeight == null}">
	<jstl:set var="maxHeight" value="200px" />
</jstl:if>

<%-- Definition --%>

<spring:message code="${code}" var="titleHeader" />
<display:column title="${titleHeader}" sortable="false">
	<jstl:forEach items="${properties}" var="picture">
		<img src="${picture }" style="max-height: ${maxHeight};" />
	</jstl:forEach>
</display:column> 