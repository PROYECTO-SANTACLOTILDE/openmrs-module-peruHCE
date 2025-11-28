package org.openmrs.module.fua;

import org.junit.Before;
import org.junit.Test;
import org.openmrs.api.APIException;
import org.openmrs.module.fua.api.dao.FuaEstadoDao;
import org.openmrs.module.fua.api.impl.FuaEstadoServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class FuaEstadoServiceTest {

    private InMemoryFuaEstadoDao inMemoryDao;
    private FuaEstadoServiceImpl service;

    @Before
    public void setUp() {
        inMemoryDao = new InMemoryFuaEstadoDao();
        service = new FuaEstadoServiceImpl();
        service.setDao(inMemoryDao);
    }

    @Test
    public void getAllEstados_shouldReturnListFromDao() throws APIException {
        List<FuaEstado> estados = Arrays.asList(new FuaEstado(), new FuaEstado());
        inMemoryDao.allEstadosToReturn = estados;

        List<FuaEstado> result = service.getAllEstados();

        assertSame(estados, result);
    }

    @Test
    public void getEstado_shouldDelegateToDao() throws APIException {
        Integer id = 10;
        FuaEstado estado = new FuaEstado();
        inMemoryDao.estadoToReturn = estado;

        FuaEstado result = service.getEstado(id);

        assertSame(estado, result);
        assertEquals(id, inMemoryDao.lastId);
    }

    @Test
    public void saveEstado_shouldGenerateUuidWhenBlank() throws APIException {
        FuaEstado estado = new FuaEstado();
        

        FuaEstado result = service.saveEstado(estado);

        assertNotNull("Debe generar un UUID cuando está vacío", estado.getUuid());
        assertFalse("UUID no debe ser cadena vacía", estado.getUuid().trim().isEmpty());
        assertSame(estado, inMemoryDao.lastSaved);
        assertSame(estado, result);
    }

    @Test
    public void saveEstado_shouldNotOverrideExistingUuid() throws APIException {
        FuaEstado estado = new FuaEstado();
        estado.setUuid("ya-existe-uuid");

        FuaEstado result = service.saveEstado(estado);

        assertEquals("ya-existe-uuid", estado.getUuid());
        assertSame(estado, inMemoryDao.lastSaved);
        assertSame(estado, result);
    }

    @Test
    public void purgeEstado_shouldDelegateToDao() throws APIException {
        FuaEstado estado = new FuaEstado();

        service.purgeEstado(estado);

        assertSame(estado, inMemoryDao.lastPurged);
    }

    @Test
    public void getByUuid_shouldDelegateToDao() {
        String uuid = "estado-123";
        FuaEstado estado = new FuaEstado();
        inMemoryDao.estadoByUuidToReturn = estado;

        FuaEstado result = service.getByUuid(uuid);

        assertSame(estado, result);
        assertEquals(uuid, inMemoryDao.lastUuid);
    }

    // ---- DAO en memoria para pruebas ----
    private static class InMemoryFuaEstadoDao extends FuaEstadoDao {

        List<FuaEstado> allEstadosToReturn = new ArrayList<>();
        FuaEstado estadoToReturn;
        FuaEstado estadoByUuidToReturn;

        List<FuaEstado> lastAllEstadosCall;
        Integer lastId;
        String lastUuid;
        FuaEstado lastSaved;
        FuaEstado lastPurged;

        @Override
        public List<FuaEstado> getAllEstados() {
            lastAllEstadosCall = allEstadosToReturn;
            return allEstadosToReturn;
        }

        @Override
        public FuaEstado getEstado(Integer id) {
            this.lastId = id;
            return estadoToReturn;
        }

        @Override
        public FuaEstado saveEstado(FuaEstado estado) {
            this.lastSaved = estado;
            return estado;
        }

        @Override
        public void purgeEstado(FuaEstado estado) {
            this.lastPurged = estado;
        }

        @Override
        public FuaEstado getByUuid(String uuid) {
            this.lastUuid = uuid;
            return estadoByUuidToReturn;
        }
    }
}
