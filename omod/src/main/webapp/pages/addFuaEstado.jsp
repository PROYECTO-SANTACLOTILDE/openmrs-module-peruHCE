<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>

<h2><spring:message code="fua.estado.title" text="Estados del FUA" /></h2>

<!-- Formulario simple para agregar o editar un Estado FUA -->
<form action="fuaestado.form" method="post">
    <fieldset>
        <legend><spring:message code="fua.estado.add.title" text="Agregar o Editar Estado del FUA" /></legend>
        <table>
            <tr>
                <td><label for="nombre">Nombre del Estado:</label></td>
                <td><input type="text" id="nombre" name="nombre" value="${estado.nombre != null ? estado.nombre : ''}" /></td>
            </tr>
        </table>
        <input type="hidden" name="estadoId" value="${estado.id}" />
        <input type="submit" value="Guardar Estado" />
        <input type="submit" name="action" value="purge" onclick="return confirm('¿Estás seguro de que deseas eliminar este estado?');" />
    </fieldset>
</form>

<!-- Tabla con los Estados existentes -->
<h3><spring:message code="fua.estado.list" text="Lista de Estados de FUA" /></h3>

<table border="1">
    <thead>
        <tr>
            <th>ID</th>
            <th>Nombre</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="est" items="${estados}">
            <tr>
                <td>${est.id}</td>
                <td>${est.nombre}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>

<%@ include file="/WEB-INF/template/footer.jsp" %>
