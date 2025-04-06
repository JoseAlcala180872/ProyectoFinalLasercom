package com.proyectofinallasercom.Pantallas.dnd;

import com.proyectofinallasercom.Pantallas.AdministrarActividad;
import dominio.Actividad;
import dominio.EstadoActividad;
import java.awt.GridLayout;
import java.awt.datatransfer.DataFlavor;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class BoardPanel extends javax.swing.JPanel {
    public static final DataFlavor STRING_FLAVOR = new DataFlavor(String.class, "String");
    private final AdministrarActividad administrarActividad;

    public BoardPanel(List<Actividad> actividades, AdministrarActividad administrarActividad) {
        this.administrarActividad = administrarActividad;
        initComponents(actividades);
    }

    private void initComponents(List<Actividad> actividades) {
        setLayout(new GridLayout(1, 0));

        JPanel pendientes = createColumnPanel("Pendientes", actividades, EstadoActividad.PENDIENTE);
        JPanel enProceso = createColumnPanel("En Proceso", actividades, EstadoActividad.EN_PROGRESO);
        JPanel finalizadas = createColumnPanel("Finalizada", actividades, EstadoActividad.TERMINADO);

        add(new JScrollPane(pendientes));
        add(new JScrollPane(enProceso));
        add(new JScrollPane(finalizadas));
    }

    private JPanel createColumnPanel(String titulo, List<Actividad> actividades, EstadoActividad estadoFiltro) {
    JPanel container = new JPanel();
    container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
    
    ColumnPanel columnPanel = new ColumnPanel(titulo, administrarActividad, container);
    
    actividades.stream()
        .filter(a -> a.getEstado() == estadoFiltro)
        .forEach(a -> {
            CardPanel card = new CardPanel(a.getTitulo(), a.getIdActividad(), administrarActividad, a.getEstado());
            container.add(card);
        });
    
    container.add(Box.createVerticalGlue());
    return columnPanel;
}

    public static class StringTransferable implements java.awt.datatransfer.Transferable {
        private final String data;
        private static final DataFlavor[] SUPPORTED_FLAVORS = {STRING_FLAVOR};

        public StringTransferable(String data) {
            this.data = data;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return SUPPORTED_FLAVORS;
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return flavor.equals(STRING_FLAVOR);
        }

        @Override
        public Object getTransferData(DataFlavor flavor) throws java.io.IOException {
            if (isDataFlavorSupported(flavor)) {
                return data;
            }
            throw new java.io.IOException("Flavor no soportado: " + flavor);
        }
    }
}