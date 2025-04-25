<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>

<h2><spring:message code="fua.title" text="Formulario Único de Atención" /></h2>
<!-- Botón para ir a la gestión de Formatos FUA -->
<div style="margin-bottom: 20px;">
    <a href="formatofua.form" class="button" style="background-color: #4CAF50; color: white; padding: 10px 15px; text-decoration: none; border-radius: 5px;">
        + Crear / Ver Formatos FUA
    </a>
</div>

<!-- Formulario simple para agregar un nuevo FUA -->
<form action="fua.form" method="post">
    <fieldset>
        <legend><spring:message code="fua.add.title" text="Agregar nuevo FUA" /></legend>
        <table>
            <tr>
                <td><label for="name">Nombre:</label></td>
                <td><input type="text" id="name" name="name" value="${fua.name != null ? fua.name : ''}" /></td>
            </tr>
            <tr>
                <td><label for="visitUuid">UUID de Visita:</label></td>
                <td><input type="text" id="visitUuid" name="visitUuid" value="${fua.visitUuid != null ? fua.visitUuid : ''}" /></td>
            </tr>
            <tr>
                <td><label for="formatoFuaUuid">UUID del Formato FUA:</label></td>
                <td><input type="text" id="formatoFuaUuid" name="formatoFuaUuid" value="${fua.formatoFuaUuid != null ? fua.formatoFuaUuid : ''}" /></td>
            </tr>
            <tr>
                <td><label for="payload">Contenido JSON:</label></td>
                <td><textarea id="payload" name="payload" rows="5" cols="60">${fua.payload != null ? fua.payload : ''}</textarea></td>
            </tr>
        </table>
        <input type="submit" value="Guardar FUA" />
    </fieldset>
</form>

<!-- Tabla con los FUAs existentes -->
<h3><spring:message code="fua.list" text="Lista de FUAs registrados" /></h3>

<table border="1">
    <tr>
        <th>ID</th>
        <th>UUID</th>
        <th>Nombre</th>
        <th>Visita</th>
        <th>Acción</th>
    </tr>
    <c:forEach items="${fuas}" var="fua">
        <tr>
            <td><c:out value="${fua.id}" /></td>
            <td><c:out value="${fua.uuid}" /></td>
            <td><c:out value="${fua.name}" /></td>
            <td><c:out value="${fua.visitUuid}" /></td>
            <td>
                <a href="fua.form?fuaId=${fua.id}">Editar</a>
            </td>
        </tr>
    </c:forEach>
</table>

<%@ include file="/WEB-INF/template/footer.jsp" %>
