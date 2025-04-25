<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>

<h2>Formato FUA</h2>

<form method="post" action="formatofua.form">
    <table>
        <tr>
            <td>Campos (JSON u otro texto):</td>
            <td><textarea name="campos" rows="4" cols="50">${formatofua.campos}</textarea></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="hidden" name="uuid" value="${formatofua.uuid}" />
                <input type="hidden" name="formatofuaId" value="${formatofua.id}" />
                <input type="submit" value="Guardar Formato FUA" />
            </td>
        </tr>
    </table>
</form>

<h3>Formatos FUA existentes</h3>
<table border="1">
    <tr>
        <th>ID</th>
        <th>UUID</th>
        <th>Campos</th>
    </tr>
    <c:forEach var="f" items="${formatofuas}">
        <tr>
            <td>${f.id}</td>
            <td>${f.uuid}</td>
            <td><pre>${f.campos}</pre></td>
        </tr>
    </c:forEach>
</table>

<%@ include file="/WEB-INF/template/footer.jsp" %>
