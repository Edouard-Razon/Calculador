/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.calculadora1;

/**
 *
 * @author Edu Nuñez
 */
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Interfaz extends javax.swing.JFrame {

    private boolean shiftActivo = false; // bandera para shift

    public Interfaz() {
        initComponents();
        pantalla.setEditable(false); // solo botones
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        pantalla = new javax.swing.JTextField();
        btnShift = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        btnInverso = new javax.swing.JButton();
        btnCubo = new javax.swing.JButton();
        btnRaiz = new javax.swing.JButton();
        btnOn = new javax.swing.JButton();
        btnCuadrado = new javax.swing.JButton();
        btnPotencia = new javax.swing.JButton();
        btnLog = new javax.swing.JButton();
        btnLn = new javax.swing.JButton();
        btnSin = new javax.swing.JButton();
        btnCos = new javax.swing.JButton();
        btnTan = new javax.swing.JButton();
        btnParentesisIzq = new javax.swing.JButton();
        btnParentesisDer = new javax.swing.JButton();
        btn7 = new javax.swing.JButton();
        btn4 = new javax.swing.JButton();
        btn1 = new javax.swing.JButton();
        btn0 = new javax.swing.JButton();
        btn8 = new javax.swing.JButton();
        btn5 = new javax.swing.JButton();
        btn2 = new javax.swing.JButton();
        btnPunto = new javax.swing.JButton();
        btn9 = new javax.swing.JButton();
        btn6 = new javax.swing.JButton();
        btn3 = new javax.swing.JButton();
        btnExp = new javax.swing.JButton();
        btnDel = new javax.swing.JButton();
        btnDivision = new javax.swing.JButton();
        btnMultiplicacion = new javax.swing.JButton();
        btnResta = new javax.swing.JButton();
        btnAC = new javax.swing.JButton();
        btnSuma = new javax.swing.JButton();
        btnIgual = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pantalla.setToolTipText("");

        btnShift.setFont(new java.awt.Font("Segoe UI", 1, 18)); 
        btnShift.setText("Shift");
    btnShift.addActionListener(evt -> btnShiftActionPerformed(evt));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); 
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("x!");

        btnInverso.setText("x^-1");
    btnInverso.addActionListener(evt -> btnInversoActionPerformed(evt));

        btnCubo.setText("x^3");
    btnCubo.addActionListener(evt -> btnCuboActionPerformed(evt));

        btnRaiz.setText("raiz");
    btnRaiz.addActionListener(evt -> btnRaizActionPerformed(evt));

        btnOn.setText("ON");
    btnOn.addActionListener(evt -> btnOnActionPerformed(evt));

        btnCuadrado.setText("x^2");
    btnCuadrado.addActionListener(evt -> btnCuadradoActionPerformed(evt));

        btnPotencia.setText("x^y");
    btnPotencia.addActionListener(evt -> btnPotenciaActionPerformed(evt));

        btnLog.setText("LOG");
    btnLog.addActionListener(evt -> btnLogActionPerformed(evt));

        btnLn.setText("Ln");
    btnLn.addActionListener(evt -> btnLnActionPerformed(evt));

        btnSin.setText("sin");
    btnSin.addActionListener(evt -> btnSinActionPerformed(evt));

        btnCos.setText("cos");
    btnCos.addActionListener(evt -> btnCosActionPerformed(evt));

        btnTan.setText("tan");
    btnTan.addActionListener(evt -> btnTanActionPerformed(evt));

        btnParentesisIzq.setText("(");
    btnParentesisIzq.addActionListener(evt -> btnParentesisIzqActionPerformed(evt));

        btnParentesisDer.setText(")");
    btnParentesisDer.addActionListener(evt -> btnParentesisDerActionPerformed(evt));

        btn7.setText("7");
    btn7.addActionListener(evt -> agregarTexto("7"));

        btn4.setText("4");
    btn4.addActionListener(evt -> agregarTexto("4"));

        btn1.setText("1");
    btn1.addActionListener(evt -> agregarTexto("1"));

        btn0.setText("0");
    btn0.addActionListener(evt -> agregarTexto("0"));

        btn8.setText("8");
    btn8.addActionListener(evt -> agregarTexto("8"));

        btn5.setText("5");
    btn5.addActionListener(evt -> agregarTexto("5"));

        btn2.setText("2");
    btn2.addActionListener(evt -> agregarTexto("2"));

        btnPunto.setText(".");
    btnPunto.addActionListener(evt -> agregarTexto("."));

        btn9.setText("9");
    btn9.addActionListener(evt -> agregarTexto("9"));

        btn6.setText("6");
    btn6.addActionListener(evt -> agregarTexto("6"));

        btn3.setText("3");
    btn3.addActionListener(evt -> agregarTexto("3"));

        btnExp.setText("EXP");
    btnExp.addActionListener(evt -> agregarTexto("exp("));

        btnDel.setText("DEL");
        btnDel.addActionListener(evt -> {
            String txt = pantalla.getText();
            if (!txt.isEmpty()) pantalla.setText(txt.substring(0, txt.length()-1));
        });

        btnDivision.setText("/");
    btnDivision.addActionListener(evt -> agregarTexto("/"));

        btnMultiplicacion.setText("*");
    btnMultiplicacion.addActionListener(evt -> agregarTexto("*"));

        btnResta.setText("-");
    btnResta.addActionListener(evt -> agregarTexto("-"));

        btnAC.setText("AC");
    btnAC.addActionListener(evt -> pantalla.setText(""));

        btnSuma.setText("+");
    btnSuma.addActionListener(evt -> agregarTexto("+"));

        btnIgual.setText("=");
        btnIgual.addActionListener(evt -> {
            try {
                double res = evaluar(pantalla.getText());
                pantalla.setText(String.valueOf(res));
            } catch (Exception e) {
                pantalla.setText("Error");
            }
        });

        jLabel2.setText("3√");
        jLabel3.setText("√");
        jLabel4.setText("sin^-1");
        jLabel5.setText("cos^-1");
        jLabel6.setText("π");
        jLabel6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (shiftActivo) agregarTexto(String.valueOf(Math.PI));
            }
        });
        jLabel7.setText("tan^-1");

        // ----------- aquí iría tu GroupLayout tal cual lo generaste ----------
        // (por espacio no lo repito, ya lo tienes en tu archivo original)
        pack();
    }// </editor-fold>

    // ----------------- Lógica de los botones -----------------
    private void btnShiftActionPerformed(java.awt.event.ActionEvent evt) {                                         
        shiftActivo = !shiftActivo;
        btnShift.setBackground(shiftActivo ? Color.YELLOW : null);
    }                                        

    private void btnInversoActionPerformed(java.awt.event.ActionEvent evt) {                                           
        agregarTexto("1/(");
    }                                          

    private void btnCuboActionPerformed(java.awt.event.ActionEvent evt) {                                        
        if (shiftActivo) agregarTexto("fact(");
        else agregarTexto("^3");
    }                                       

    private void btnRaizActionPerformed(java.awt.event.ActionEvent evt) {                                        
        if (shiftActivo) agregarTexto("cbrt(");
        else agregarTexto("sqrt(");
    }                                       

    private void btnOnActionPerformed(java.awt.event.ActionEvent evt) {                                      
        pantalla.setText("");
    }                                     

    private void btnCuadradoActionPerformed(java.awt.event.ActionEvent evt) {                                            
        agregarTexto("^2");
    }                                           

    private void btnPotenciaActionPerformed(java.awt.event.ActionEvent evt) {                                            
        agregarTexto("^");
    }                                           

    private void btnLogActionPerformed(java.awt.event.ActionEvent evt) {                                       
        agregarTexto("log(");
    }                                      

    private void btnLnActionPerformed(java.awt.event.ActionEvent evt) {                                      
        agregarTexto("ln(");
    }                                     

    private void btnSinActionPerformed(java.awt.event.ActionEvent evt) {                                       
        if (shiftActivo) agregarTexto("asin(");
        else agregarTexto("sin(");
    }                                      

    private void btnCosActionPerformed(java.awt.event.ActionEvent evt) {                                       
        if (shiftActivo) agregarTexto("acos(");
        else agregarTexto("cos(");
    }                                      

    private void btnTanActionPerformed(java.awt.event.ActionEvent evt) {                                       
        if (shiftActivo) agregarTexto("atan(");
        else agregarTexto("tan(");
    }                                      

    private void btnParentesisIzqActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        agregarTexto("(");
    }                                                

    private void btnParentesisDerActionPerformed(java.awt.event.ActionEvent evt) {                                                 
        agregarTexto(")");
    }                                                

    private void pantallaActionPerformed(java.awt.event.ActionEvent evt) {                                         
    }                                        

    // -------------------- Utilidades --------------------
    private void agregarTexto(String t) {
        pantalla.setText(pantalla.getText() + t);
    }

    // Evaluador matemático simple
    private double evaluar(String expr) {
        expr = expr.replaceAll("√", "sqrt");
    java.util.List<String> tokens = tokenizar(expr);
    java.util.List<String> postfija = infijaAPostfija(tokens);
        return evalPostfija(postfija);
    }

    private java.util.List<String> tokenizar(String expr) {
        java.util.List<String> tokens = new java.util.ArrayList<>();
        Matcher m = Pattern.compile("\\d+\\.?\\d*|[()+\\-*/^]|[a-zA-Z]+").matcher(expr);
        while (m.find()) tokens.add(m.group());
        return tokens;
    }

    private java.util.List<String> infijaAPostfija(java.util.List<String> tokens) {
        java.util.List<String> salida = new java.util.ArrayList<>();
        java.util.Stack<String> stack = new java.util.Stack<>();
        java.util.Map<String, Integer> prec = new java.util.HashMap<>();
        prec.put("+",1); prec.put("-",1); prec.put("*",2); prec.put("/",2); prec.put("^",3);

        for (String t : tokens) {
            if (t.matches("\\d+\\.?\\d*")) salida.add(t);
            else if (t.matches("[a-zA-Z]+")) stack.push(t);
            else if (prec.containsKey(t)) {
                while (!stack.isEmpty() && prec.containsKey(stack.peek()) &&
                        prec.get(stack.peek()) >= prec.get(t)) {
                    salida.add(stack.pop());
                }
                stack.push(t);
            } else if (t.equals("(")) stack.push(t);
            else if (t.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) salida.add(stack.pop());
                stack.pop();
                if (!stack.isEmpty() && stack.peek().matches("[a-zA-Z]+")) salida.add(stack.pop());
            }
        }
        while (!stack.isEmpty()) salida.add(stack.pop());
        return salida;
    }

    private double evalPostfija(java.util.List<String> postfija) {
        java.util.Stack<Double> pila = new java.util.Stack<>();
        for (String t : postfija) {
            if (t.matches("\\d+\\.?\\d*")) pila.push(Double.parseDouble(t));
            else if (t.equals("+")) pila.push(pila.pop() + pila.pop());
            else if (t.equals("-")) { double b = pila.pop(), a = pila.pop(); pila.push(a - b); }
            else if (t.equals("*")) pila.push(pila.pop() * pila.pop());
            else if (t.equals("/")) { double b = pila.pop(), a = pila.pop(); pila.push(a / b); }
            else if (t.equals("^")) { double b = pila.pop(), a = pila.pop(); pila.push(Math.pow(a, b)); }
            else if (t.equals("sin")) pila.push(Math.sin(pila.pop()));
            else if (t.equals("cos")) pila.push(Math.cos(pila.pop()));
            else if (t.equals("tan")) pila.push(Math.tan(pila.pop()));
            else if (t.equals("asin")) pila.push(Math.asin(pila.pop()));
            else if (t.equals("acos")) pila.push(Math.acos(pila.pop()));
            else if (t.equals("atan")) pila.push(Math.atan(pila.pop()));
            else if (t.equals("sqrt")) pila.push(Math.sqrt(pila.pop()));
            else if (t.equals("cbrt")) pila.push(Math.cbrt(pila.pop()));
            else if (t.equals("ln")) pila.push(Math.log(pila.pop()));
            else if (t.equals("log")) pila.push(Math.log10(pila.pop()));
            else if (t.equals("exp")) pila.push(Math.exp(pila.pop()));
            else if (t.equals("fact")) pila.push((double) factorial(pila.pop().intValue()));
        }
        return pila.pop();
    }

    private long factorial(int n) {
        if (n < 0) throw new ArithmeticException("Factorial indefinido");
        long res = 1;
        for (int i = 2; i <= n; i++) res *= i;
        return res;
    }

    // ------------------ main ------------------
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new Interfaz().setVisible(true);
        });
    }

    // Variables declaration
    private javax.swing.JButton btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    private javax.swing.JButton btnAC, btnCos, btnCuadrado, btnCubo, btnDel, btnDivision, btnExp;
    private javax.swing.JButton btnIgual, btnInverso, btnLn, btnLog, btnMultiplicacion, btnOn;
    private javax.swing.JButton btnParentesisDer, btnParentesisIzq, btnPotencia, btnPunto;
    private javax.swing.JButton btnRaiz, btnResta, btnShift, btnSin, btnSuma, btnTan;
    private javax.swing.JLabel jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7;
    private javax.swing.JTextField pantalla;
    // End of variables declaration
}