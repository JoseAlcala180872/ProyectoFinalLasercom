package com.proyectofinallasercom.Pantallas.dnd;

import com.proyectofinallasercom.Pantallas.ActividadTerminada;
import com.proyectofinallasercom.Pantallas.AdministrarActividad;
import com.proyectofinallasercom.Pantallas.EditarActividad;
import dominio.Actividad;
import dominio.EstadoActividad;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.datatransfer.Transferable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.TransferHandler;

public class CardPanel extends JPanel {
    private final Long actividadId;
    private final AdministrarActividad administrarActividad;
    private EstadoActividad estado;

    public CardPanel(String titulo, Long actividadId, AdministrarActividad administrarActividad, EstadoActividad estado) {
        this.actividadId = actividadId;
        this.administrarActividad = administrarActividad;
        this.estado = estado;
        initComponents(titulo);
    }

    private void initComponents(String titulo) {
        setBackground(Color.WHITE);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        add(new JLabel(titulo));
        configurarEventos();
    }

    private void configurarEventos() {
        // Configurar el TransferHandler
        setTransferHandler(new CardTransferHandler());
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (estado != EstadoActividad.TERMINADO && e.getClickCount() == 1) {
                    JComponent comp = (JComponent) e.getSource();
                    comp.putClientProperty("originalParent", comp.getParent());
                    comp.getTransferHandler().exportAsDrag(comp, e, TransferHandler.MOVE);
                }
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirVentanaCorrespondiente();
            }
        });
    }

    public void setEstado(EstadoActividad nuevoEstado) {
        this.estado = nuevoEstado;
        if (nuevoEstado == EstadoActividad.TERMINADO) {
            setTransferHandler(null); // Deshabilitar drag and drop
        }
    }

    private void abrirVentanaCorrespondiente() {
        Actividad actividad = administrarActividad.obtenerActividadPorId(actividadId);
        if (actividad != null) {
            if (actividad.getEstado() == EstadoActividad.TERMINADO) {
                // Verificar primero si ya tiene monto
                if (actividad.getMonto() != null) {
                    JOptionPane.showMessageDialog(administrarActividad,
                        "Esta actividad ya tiene un monto registrado: $" + actividad.getMonto(),
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE);
                    return; // Salir sin abrir ventana
                }

                // Solo abrir si no tiene monto
                SwingUtilities.invokeLater(() -> {
                    new ActividadTerminada(actividad).setVisible(true);
                    administrarActividad.dispose();
                });
            } else {
                // Para actividades no terminadas
                SwingUtilities.invokeLater(() -> {
                    new EditarActividad(actividad).setVisible(true);
                    administrarActividad.dispose();
                });
            }
        }
    }

    public Long getActividadId() {
        return actividadId;
    }

    public EstadoActividad getEstado() {
        return estado;
    }

    private class CardTransferHandler extends TransferHandler {
        @Override
        protected Transferable createTransferable(JComponent c) {
            return new BoardPanel.StringTransferable(actividadId.toString());
        }

        @Override
        public int getSourceActions(JComponent c) {
            return estado == EstadoActividad.TERMINADO ? NONE : MOVE;
        }

        @Override
        protected void exportDone(JComponent source, Transferable data, int action) {
            if (action == MOVE) {
                Container originalParent = (Container) source.getClientProperty("originalParent");
                if (originalParent != null) {
                    originalParent.remove(source);
                    originalParent.revalidate();
                    originalParent.repaint();
                }
            }
        }
    }
}