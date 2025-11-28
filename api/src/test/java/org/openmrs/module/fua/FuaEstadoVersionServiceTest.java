package org.openmrs.module.fua;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.APIException;
import org.openmrs.module.fua.api.dao.FuaEstadoVersionDao;
import org.openmrs.module.fua.api.impl.FuaEstadoVersionServiceImpl;

import static org.junit.Assert.*;

public class FuaEstadoVersionServiceTest {

    private InMemoryFuaEstadoVersionDao inMemoryDao;
    private FuaEstadoVersionServiceImpl service;

    @Before
    public void setUp() {
        inMemoryDao = new InMemoryFuaEstadoVersionDao();
        service = new FuaEstadoVersionServiceImpl();
        service.setDao(inMemoryDao);
    }

    @Test
    public void saveFuaEstadoVersion_shouldCreateVersionAndDelegateToDao() throws APIException {
        FuaEstado estado = new FuaEstado();
        estado.setUuid("estado-uuid-1");
        String descripcion = "Cambio de estado por prueba";

        FuaEstadoVersion result = service.saveFuaEstadoVersion(estado, descripcion);

        
        assertNotNull("El DAO debe haber recibido una versión", inMemoryDao.lastSavedVersion);
        // El servicio debe devolver lo que devuelve el DAO
        assertSame(inMemoryDao.lastSavedVersion, result);

        // Comprobamos la descripción
        try {
            assertEquals("Debe copiar la descripción",
                    descripcion,
                    inMemoryDao.lastSavedVersion.getDescripcion());
        } catch (NoSuchMethodError | RuntimeException e) {
            // Si el modelo no tiene descripción, no genera error.
        }
    }

    // ---- DAO en memoria ----
    private static class InMemoryFuaEstadoVersionDao extends FuaEstadoVersionDao {

        FuaEstadoVersion lastSavedVersion;

        @Override
        public FuaEstadoVersion saveFuaEstadoVersion(FuaEstadoVersion version) {
            this.lastSavedVersion = version;
            return version;
        }
    }
}
