/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.calculadora1;

import javax.swing.SwingUtilities;

/**
 *
 * @author Edu Nuñez
 */
public class Calculadora1 {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        Interfaz ventana = new Interfaz();
        ventana.setVisible(true);
    });
        
    }
    
}
