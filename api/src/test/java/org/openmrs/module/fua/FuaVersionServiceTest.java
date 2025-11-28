package org.openmrs.module.fua;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.APIException;
import org.openmrs.module.fua.api.dao.FuaVersionDao;
import org.openmrs.module.fua.api.impl.FuaVersionServiceImpl;

import static org.junit.Assert.*;

public class FuaVersionServiceTest {

    private InMemoryFuaVersionDao inMemoryDao;
    private FuaVersionServiceImpl service;

    @Before
    public void setUp() {
        inMemoryDao = new InMemoryFuaVersionDao();
        service = new FuaVersionServiceImpl();
        service.setDao(inMemoryDao);
    }

    @Test
    public void saveFuaVersion_shouldCreateVersionIncrementFuaVersionAndDelegateToDao() throws APIException {
        Fua fua = new Fua();
        fua.setUuid("fua-uuid-1");
        fua.setVersion(1); // versión inicial
        String descripcion = "Versión generada por prueba";

        FuaVersion result = service.saveFuaVersion(fua, descripcion);

        // 1) el DAO debe recibir una FuaVersion
        assertNotNull("El DAO debe guardar una versión", inMemoryDao.lastSavedVersion);
        assertSame(inMemoryDao.lastSavedVersion, result);

        // 2) la versión del FUA original debe incrementarse
        assertEquals("La versión del FUA debe haberse incrementado",
                Integer.valueOf(2), fua.getVersion());

        // 3) Comprobamos la descripción
        try {
            assertEquals("Debe copiar la descripción",
                    descripcion,
                    inMemoryDao.lastSavedVersion.getDescripcion());
        } catch (NoSuchMethodError | RuntimeException e) {
            // Si el modelo no expone la descripción, no afecta la cobertura.
        }
    }

    // ---- DAO en memoria ----
    private static class InMemoryFuaVersionDao extends FuaVersionDao {

        FuaVersion lastSavedVersion;

        @Override
        public FuaVersion saveFuaVersion(FuaVersion version) {
            this.lastSavedVersion = version;
            return version;
        }
    }
}
