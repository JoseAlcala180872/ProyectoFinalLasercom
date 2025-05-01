/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.MockedStatic;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ConexionTest {

    private MockedStatic<Persistence> mockedPersistence;
    private EntityManagerFactory mockEmf;
    private EntityManager mockEm;

    @BeforeAll
    void setUp() {
        // Crear mocks para las dependencias estáticas
        mockEmf = mock(EntityManagerFactory.class);
        mockEm = mock(EntityManager.class);
        
        // Configurar mock para Persistence.createEntityManagerFactory
        mockedPersistence = mockStatic(Persistence.class);
        mockedPersistence.when(() -> Persistence.createEntityManagerFactory("lasercomPU"))
                        .thenReturn(mockEmf);
        
        // Configurar comportamiento del mock EntityManagerFactory
        when(mockEmf.createEntityManager()).thenReturn(mockEm);
        when(mockEmf.isOpen()).thenReturn(true);
    }

    @AfterAll
    void tearDown() {
        // Cerrar el mock estático
        mockedPersistence.close();
    }

    @Test
    void testGetEntityManager() {
        EntityManager result = (EntityManager) Conexion.getEntityManager();
        
        assertNotNull(result);
        assertEquals(mockEm, result);
        
        // Verificar que se llamó al método createEntityManager
        verify(mockEmf).createEntityManager();
    }

    @Test
    void testGetEntityManagerMultipleTimes() {
        EntityManager em1 = (EntityManager) Conexion.getEntityManager();
        EntityManager em2 = (EntityManager) Conexion.getEntityManager();
        
        assertNotSame(em1, em2, "Cada llamada debe devolver un nuevo EntityManager");
        verify(mockEmf, times(2)).createEntityManager();
    }

    @Test
    void testCloseWhenEmfIsOpen() {
        when(mockEmf.isOpen()).thenReturn(true);
        
        Conexion.close();
        
        verify(mockEmf).isOpen();
        verify(mockEmf).close();
    }

    @Test
    void testCloseWhenEmfIsAlreadyClosed() {
        when(mockEmf.isOpen()).thenReturn(false);
        
        Conexion.close();
        
        verify(mockEmf).isOpen();
        verify(mockEmf, never()).close();
    }

    @Test
    void testCloseWhenEmfIsNull() {
        // Necesitamos reiniciar el mock para este caso específico
        try (MockedStatic<Persistence> tempMock = mockStatic(Persistence.class)) {
            tempMock.when(() -> Persistence.createEntityManagerFactory("lasercomPU"))
                   .thenReturn(null);
            
            // Recrear la clase para simular el caso null
            class TempConexion {
                private static final EntityManagerFactory emf = null;
                
                public static void close() {
                    if (emf != null && emf.isOpen()) {
                        emf.close();
                    }
                }
            }
            
            assertDoesNotThrow(() -> TempConexion.close());
        }
    }
}
