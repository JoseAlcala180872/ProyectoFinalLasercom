/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.proyectofinallasercom;

import com.proyectofinallasercom.Pantallas.LoginForm;

/**
 *
 * @author montoya
 */
public class ProyectoFinalLasercom {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }
}
