package com.proyectofinallasercom.Pantallas.dnd;

import bo.ActividadBO;
import com.proyectofinallasercom.Pantallas.AdministrarActividad;
import dominio.Actividad;
import dominio.EstadoActividad;
import excepciones.BOException;
import java.awt.BorderLayout;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.TransferHandler;

public class ColumnPanel extends JPanel {
    private final String title;
    private final AdministrarActividad administrarActividad;
    private final JPanel cardPanelContainer;
    private final ActividadBO actividadBO;

    public ColumnPanel(String title, AdministrarActividad administrarActividad, JPanel cardPanelContainer) {
        this.title = title;
        this.administrarActividad = administrarActividad;
        this.cardPanelContainer = cardPanelContainer;
        this.actividadBO = administrarActividad.getActividadBO();
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        add(new JLabel("  " + title + "  "), BorderLayout.NORTH);
        cardPanelContainer.setLayout(new BoxLayout(cardPanelContainer, BoxLayout.Y_AXIS));
        add(cardPanelContainer, BorderLayout.CENTER);
        setTransferHandler(new ColumnTransferHandler());
    }

    private class ColumnTransferHandler extends TransferHandler {
        @Override
        public boolean canImport(TransferHandler.TransferSupport support) {
            try {
                if (!support.isDataFlavorSupported(BoardPanel.STRING_FLAVOR)) {
                    return false;
                }
                
                String actividadIdStr = (String) support.getTransferable().getTransferData(BoardPanel.STRING_FLAVOR);
                Long actividadId = Long.parseLong(actividadIdStr);
                Actividad actividad = administrarActividad.obtenerActividadPorId(actividadId);
                
                // Permitir mover solo si la actividad no está terminada
                return actividad != null && actividad.getEstado() != EstadoActividad.TERMINADO;
                
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        public boolean importData(TransferHandler.TransferSupport support) {
            try {
                String actividadIdStr = (String) support.getTransferable().getTransferData(BoardPanel.STRING_FLAVOR);
                Long actividadId = Long.parseLong(actividadIdStr);
                
                EstadoActividad nuevoEstado = determinarEstado();
                actividadBO.cambiarEstadoActividad(actividadId, nuevoEstado);
                
                actualizarInterfaz();
                return true;
                
            } catch (UnsupportedFlavorException | IOException | BOException | NumberFormatException e) {
                e.printStackTrace();
                return false;
            }
        }

        private EstadoActividad determinarEstado() {
            switch (title) {
                case "Pendientes": return EstadoActividad.PENDIENTE;
                case "En Proceso": return EstadoActividad.EN_PROGRESO;
                case "Finalizada": return EstadoActividad.TERMINADO;
                default: throw new IllegalArgumentException("Columna no válida");
            }
        }

        private void actualizarInterfaz() {
            cardPanelContainer.removeAll();
            administrarActividad.cargarTablero();
        }

        @Override
        public int getSourceActions(JComponent c) {
            return TransferHandler.MOVE;
        }
    }
}