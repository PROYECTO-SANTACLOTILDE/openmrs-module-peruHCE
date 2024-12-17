<%@ include file="/WEB-INF/template/include.jsp"%> 
<%@ include file="/WEB-INF/template/header.jsp"%>

<h2><spring:message code="department.title" /></h2>

<br/>
<h3>Department Form</h3>
<form method="post">
    <fieldset>
        <legend><openmrs:message code="department.edit.title"/></legend>
        <table>
            <tr>
                <td><openmrs:message code="general.name"/></td>
                <td>
                    <spring:bind path="department.name">
                        <input type="text" name="name" value="${status.value}" size="35" />
                        <c:if test="${status.errorMessage != ''}">
                            <span class="error">${status.errorMessage}</span>
                        </c:if>
                    </spring:bind>
                </td>
            </tr>
            <tr>
                <td valign="top"><openmrs:message code="general.description"/></td>
                <td valign="top">
                    <spring:bind path="department.description">
                        <textarea name="description" rows="3" cols="40" onkeypress="return forceMaxLength(this, 1024);" >${status.value}</textarea>
                        <c:if test="${status.errorMessage != ''}">
                            <span class="error">${status.errorMessage}</span>
                        </c:if>
                    </spring:bind>
                </td>
            </tr>
        </table>
        <br />
        <input type="submit" value="<openmrs:message code="department.save"/>" name="save">
    </fieldset>
</form>

<br/>
<h3>Department List</h3>
<table>
  <tr>
   <th>Department Name</th>
   <th>Description</th>
  </tr>
  <c:forEach var="department" items="${departments}">
      <tr>
        <td>${department.name}</td>
        <td>${department.description}</td>
      </tr>		
  </c:forEach>
</table>

<%@ include file="/WEB-INF/template/footer.jsp"%>
