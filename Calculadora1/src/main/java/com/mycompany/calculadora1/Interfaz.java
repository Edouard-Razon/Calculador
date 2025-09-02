
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.calculadora1;
import java.awt.Color;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Stack;

/**
 *
 * @author Edu Nuñez
 */
public class Interfaz extends javax.swing.JFrame {
    // --- Helpers trigonométricos y notación π ---
    // Intenta representar un ángulo (radianes) como múltiplo racional de PI.
    // Devuelve cadenas como "π", "π/2", "-π/6", "0" o null si no se puede aproximar.
    private String representarMultiploDePi(double ang) {
        double pi = Math.PI;
        double ratio = ang / pi;
        double tol = 1e-10;
        for (int q = 1; q <= 24; q++) {
            double pD = Math.round(ratio * q);
            int p = (int) pD;
            double approx = ((double)p / q) * pi;
            if (Math.abs(ang - approx) < tol) {
                if (p == 0) return "0";
                StringBuilder sb = new StringBuilder();
                if (p < 0) sb.append("-");
                int absP = Math.abs(p);
                if (absP == q) sb.append("π");
                else if (absP == 1) sb.append("π/" + q);
                else sb.append(absP + "π/" + q);
                return sb.toString();
            }
        }
        double eps = 1e-10;
        double k = Math.round(ang / (2*pi));
        if (Math.abs(ang - k*(2*pi)) < eps) {
            long kk = (long) k;
            return kk + "·2π";
        }
        return null;
    }

    // Extrae el primer argumento entre paréntesis de la primera aparición de funcName en la entrada.
    private String extraerPrimerArgumento(String entrada, String funcName) {
        int idx = entrada.indexOf(funcName + "(");
        if (idx < 0) return null;
        int start = entrada.indexOf('(', idx);
        if (start < 0) return null;
        int depth = 1;
        StringBuilder sb = new StringBuilder();
        for (int i = start + 1; i < entrada.length(); i++) {
            char c = entrada.charAt(i);
            if (c == '(') depth++;
            else if (c == ')') {
                depth--;
                if (depth == 0) return sb.toString();
            }
            if (depth > 0) sb.append(c);
        }
        return null;
    }

    // Intenta formatear el resultado de sin/cos/tan usando π cuando el argumento es múltiplo racional de π.
    // Devuelve null si no se aplica un formateo especial y se debe usar el resultado numérico normal.
    // ...duplicado eliminado, se mantiene la definición más abajo...

    /**
     * Creates new form Interfaz
     */

     //poner stack metodos: push meter, pop sacar, clear limpiar
    private boolean shiftActivo=false;  //bandera
    private boolean encendida = false;
    
    public Interfaz() {
        initComponents();
        pantalla.setEditable(false); //para que acepte solo botones de la calcu
    System.out.println("DEBUG: Interfaz constructor executed");
    }

        // Método para agregar texto a la pantalla
        private void agregarPantalla(String texto) {
            if (encendida && pantalla.isEditable()) {
                pantalla.setText(pantalla.getText() + texto);
            }
        }

        // Método para borrar el último carácter
        private void borrarUltimo() {
            if (encendida && pantalla.isEditable()) {
                String txt = pantalla.getText();
                if (!txt.isEmpty()) {
                    pantalla.setText(txt.substring(0, txt.length() - 1));
                }
            }
        }

        // Método para limpiar la pantalla
        private void limpiarPantalla() {
            if (encendida && pantalla.isEditable()) {
                pantalla.setText("");
            }
        }

        // Método para evaluar la expresión matemática
        private double evaluar(String expr) {
        System.out.println("DEBUG: evaluar - entrada='" + expr + "'");
        expr = expr.replace("π", String.valueOf(Math.PI));
    expr = expr.replace("asin", "asin");
    expr = expr.replace("acos", "acos");
    expr = expr.replace("atan", "atan");
    expr = expr.replace("x!", "!");
    expr = expr.replace("∛", "cbrt");
    // Si el usuario escribió una notación "3√" puede haber sido transformada a "3sqrt" arriba;
    // soportamos ambos formatos convirtiéndolos a cbrt(...)
    expr = expr.replaceAll("3sqrt\\(([^)]+)\\)", "cbrt($1)");
    expr = expr.replaceAll("3sqrt([0-9]+\\.?[0-9]*)", "cbrt($1)");
    // Transformaciones genéricas para raíz n y raíz cuadrada:
    // Primero: caso n√(expr) -> (expr)^(1/n)
    expr = expr.replaceAll("([0-9]+)√\\(([^)]+)\\)", "($2)^(1/$1)");
    // n√number -> (number)^(1/n)
    expr = expr.replaceAll("([0-9]+)√([0-9]+\\.?[0-9]*)", "($2)^(1/$1)");
    // Ahora casos sin índice: √(expr) -> (expr)^(1/2)
    expr = expr.replaceAll("√\\(([^)]+)\\)", "($1)^(1/2)");
    // √number -> (number)^(1/2)
    expr = expr.replaceAll("√([0-9]+\\.?[0-9]*)", "($1)^(1/2)");
    // Soporte para inverso: X^-1 => (1/(X)) – soporta números, decimales o expresiones entre paréntesis
    // Primera: expresiones entre paréntesis, por ejemplo (2+3)^-1
    expr = expr.replaceAll("(\\([^()]+\\))\\^-1", "(1/($1))");
    // Segunda: números con decimales o enteros, por ejemplo 7^-1 o 3.5^-1
    expr = expr.replaceAll("(\\d+(?:\\.\\d+)?)\\^-1", "(1/($1))");
    // Soporte para notación exponencial tipo 1.23E-4: colapsar posibles espacios entre E y signo/dígitos
    expr = expr.replaceAll("(\\d+(?:\\.\\d+)?)[eE]\\s*([+-]?\\d+)", "$1E$2");
            // Reemplazo para notación de raíz cúbica literal "3√expr" o "3√(expr)" -> cbrt(expr)
            // Caso con paréntesis: 3√(expr)
            expr = expr.replaceAll("3√\\(([^)]+)\\)", "cbrt($1)");
            // Caso con número o expresión simple sin paréntesis: 3√8 -> cbrt(8)
            expr = expr.replaceAll("3√([0-9]+\\.?[0-9]*)", "cbrt($1)");
    System.out.println("DEBUG: evaluar - normalizada='" + expr + "'");
    String[] tokens = tokenize(expr);
    System.out.println("DEBUG: evaluar - tokens=" + java.util.Arrays.toString(tokens));
        Queue<String> output = new LinkedList<>();
        Stack<String> ops = new Stack<>();
        for (String t: tokens) {
            if (t.isEmpty()) continue;
            if (isNumber(t)) output.add(t);
            else if (isFunc(t)) ops.push(t);
            else if (t.equals(",")) {
                while (!ops.isEmpty() && !ops.peek().equals("(")) output.add(ops.pop());
            } else if (isOperator(t)) {
                while (!ops.isEmpty() && isOperator(ops.peek()) && precedence(t) <= precedence(ops.peek()))
                    output.add(ops.pop());
                ops.push(t);
                } else if (t.equals("!")) {
                    // Factorial is a postfix operator — push directly to output
                    output.add(t);
            } else if (t.equals("(")) ops.push(t);
            else if (t.equals(")")) {
                while (!ops.isEmpty() && !ops.peek().equals("(")) output.add(ops.pop());
                if (!ops.isEmpty() && ops.peek().equals("(")) ops.pop();
                if (!ops.isEmpty() && isFunc(ops.peek())) output.add(ops.pop());
            }
        }
        while (!ops.isEmpty()) output.add(ops.pop());
    System.out.println("DEBUG: evaluar - postfix=" + output.toString());
        Stack<Double> stack = new Stack<>();
        for (String t: output) {
            if (isNumber(t)) stack.push(Double.valueOf(t));
            else if (isOperator(t)) {
                if (stack.size() < 2) throw new IllegalArgumentException("Operador faltante");
                double b = stack.pop(), a = stack.pop();
                switch (t) {
                    case "+": stack.push(a+b); break;
                    case "-": stack.push(a-b); break;
                    case "*": stack.push(a*b); break;
                    case "/": stack.push(a/b); break;
                    case "^": stack.push(Math.pow(a,b)); break;
                }
            } else if (isFunc(t)) {
                if (stack.isEmpty()) throw new IllegalArgumentException("Función sin argumento");
                double a = stack.pop();
                switch (t) {
                    case "sin": stack.push(Math.sin(a)); break;
                    case "cos": stack.push(Math.cos(a)); break;
                    case "tan": stack.push(Math.tan(a)); break;
                    case "asin": stack.push(Math.asin(a)); break;
                    case "acos": stack.push(Math.acos(a)); break;
                    case "atan": stack.push(Math.atan(a)); break;
                    case "sqrt": stack.push(Math.sqrt(a)); break;
                    case "cbrt": stack.push(Math.cbrt(a)); break;
                    case "log": stack.push(Math.log10(a)); break;
                    case "ln": stack.push(Math.log(a)); break;
                    case "exp": stack.push(Math.exp(a)); break;
                }
            } else if (t.equals("!")) {
                if (stack.isEmpty()) throw new IllegalArgumentException("Factorial sin operando");
                int n = stack.pop().intValue();
                stack.push((double) factorial(n));
            }
        }
        if (stack.isEmpty()) throw new IllegalArgumentException("Expresión vacía");
        return stack.pop();
    }

    private String[] tokenize(String expr) {
    // No separar el signo + o - que forma parte de un exponente, por ejemplo 1E-3
    expr = expr.replaceAll("([()+\\-*/^!,])", " $1 ");
        expr = expr.replaceAll("\\s+", " ");
        // unir tokens del tipo '1E' '-' '3' en '1E-3' ya que el replaceAll anterior pudo separar
        expr = expr.replaceAll("(\\d+(?:\\.\\d+)?E)( )([+-]) (\\d+)", "$1$3$4");
        return expr.trim().split(" ");
    }
    // Método factorial necesario para el evaluador
    private long factorial(int n) {
        if (n < 0) throw new ArithmeticException("Negativo");
        long r=1; for (int i=2;i<=n;i++) r*=i;
        return r;
    }

    private boolean isNumber(String t) {
        // Acepta notación con exponente: 123, -123.45, 1.23E4, -1.2e-3
        return t.matches("-?\\d+(\\.\\d+)?([eE][+-]?\\d+)?");
    }

    private boolean isFunc(String t) {
    return java.util.Arrays.asList("sin","cos","tan","asin","acos","atan","sqrt","cbrt","log","ln","exp").contains(t);
    }

    private boolean isOperator(String t) {
        return java.util.Arrays.asList("+","-","*","/","^").contains(t);
    }

    private int precedence(String op) {
        switch (op) {
            case "+": case "-": return 1;
            case "*": case "/": return 2;
            case "^": return 3;
        }
        return 0;
    }

        // Método para mostrar el resultado
        private void mostrarResultado() {
        try {
            String entrada = pantalla.getText();
            if (entrada == null) entrada = "";
            String[] funcs = {"sin","cos","tan","asin","acos","atan","log","ln","sqrt","cbrt","exp"};
            for (String f : funcs) {
                if (entrada.contains(f + "(")) {
                    String arg = extraerPrimerArgumento(entrada, f);
                    if (arg == null || arg.trim().isEmpty()) {
                        pantalla.setText("No es un número");
                        return;
                    }
                }
            }
            int idxRaiz = entrada.indexOf('√');
            if (idxRaiz >= 0) {
                if (idxRaiz == entrada.length() - 1) {
                    pantalla.setText("No es un número");
                    return;
                } else {
                    char nc = entrada.charAt(idxRaiz + 1);
                    if (!(Character.isDigit(nc) || nc == '(' || nc == 'π' || nc == '.' || nc == '-')) {
                        pantalla.setText("No es un número");
                        return;
                    }
                }
            }

            double res;
            try {
                res = evaluar(entrada);
                // Validar resultado para funciones trigonométricas
                if (entrada.contains("sin(") || entrada.contains("cos(") || entrada.contains("tan(") || entrada.contains("asin(") || entrada.contains("acos(") || entrada.contains("atan(")) {
                    if (Double.isNaN(res) || Double.isInfinite(res)) {
                        pantalla.setText("No es un número");
                        return;
                    }
                }
            } catch (IllegalArgumentException e) {
                String msg = e.getMessage();
                if (msg.contains("fuera de dominio") || msg.contains("indefinida") || msg.contains("no definido")) {
                    pantalla.setText("No existe número");
                } else if (msg.contains("falta argumento") || msg.contains("no es un numero")) {
                    pantalla.setText("No es un número");
                } else {
                    pantalla.setText("Error");
                }
                return;
            }

            // Trigonométricas inversas: mostrar en notación π si posible, si no 'No existe número'
            if (entrada.contains("asin(") || entrada.contains("acos(") || entrada.contains("atan(")) {
                String func = null;
                if (entrada.contains("asin(")) func = "asin";
                else if (entrada.contains("acos(")) func = "acos";
                else if (entrada.contains("atan(")) func = "atan";
                String arg = extraerPrimerArgumento(entrada, func);
                String asPi = representarMultiploDePi(res);
                if (arg != null && arg.contains("π")) {
                    if (asPi != null) {
                        pantalla.setText(asPi);
                        return;
                    } else {
                        pantalla.setText("No existe número");
                        return;
                    }
                } else {
                    if (asPi != null) {
                        pantalla.setText(asPi);
                        return;
                    }
                }
            }
            // Trigonométricas directas: si argumento contiene π, mostrar resultado exacto o 'No existe número'
            if (entrada.contains("sin(") || entrada.contains("cos(") || entrada.contains("tan(")) {
                String func = null;
                if (entrada.contains("sin(")) func = "sin";
                else if (entrada.contains("cos(")) func = "cos";
                else if (entrada.contains("tan(")) func = "tan";
                String arg = extraerPrimerArgumento(entrada, func);
                if (arg != null && arg.contains("π")) {
                    String fmt = formatearTrigConPi(entrada);
                    if (fmt == null || fmt.equals("Undefined")) {
                        pantalla.setText("No existe número");
                        return;
                    } else {
                        pantalla.setText(fmt);
                        return;
                    }
                }
            }
            if (res == (long) res) pantalla.setText(String.valueOf((long) res));
            else pantalla.setText(String.valueOf(res));
        } catch (Exception e) {
            pantalla.setText("Error");
        }
    }

    // Intenta representar un ángulo (radianes) como múltiplo racional de PI.
    // Devuelve cadenas como "π", "π/2", "-π/6", "0" o null si no se puede aproximar.
    // ...duplicado eliminado, se mantiene la definición al inicio...

    // Extrae el primer argumento entre paréntesis de la primera aparición de funcName en la entrada.
    // ...duplicado eliminado, se mantiene la definición al inicio...

    // Intenta formatear el resultado de sin/cos/tan usando π cuando el argumento es múltiplo racional de π.
    // Devuelve null si no se aplica un formateo especial y se debe usar el resultado numérico normal.
    private String formatearTrigConPi(String entrada) {
        try {
            if (entrada.contains("sin(")) {
                String arg = extraerPrimerArgumento(entrada, "sin");
                if (arg == null) return null;
                double val = evaluar(arg);
                // Normalizar por pi
                double ratio = val / Math.PI;
                // buscar p/q racional simple
                for (int q = 1; q <= 24; q++) {
                    double pD = Math.round(ratio * q);
                    int p = (int) pD;
                    double approx = ((double)p / q) * Math.PI;
                    if (Math.abs(val - approx) < 1e-10) {
                        // calcular sin(p*pi/q) con precisión y redondear a 0/±1 cuando corresponda
                        double r = Math.sin(approx);
                        if (Math.abs(r) < 1e-12) return "0";
                        if (Math.abs(Math.abs(r) - 1.0) < 1e-12) return String.valueOf((int)Math.signum(r));
                        // en caso general, devolver valor numérico formateado
                        return String.valueOf(r);
                    }
                }
            }
            if (entrada.contains("cos(")) {
                String arg = extraerPrimerArgumento(entrada, "cos");
                if (arg == null) return null;
                double val = evaluar(arg);
                double ratio = val / Math.PI;
                for (int q = 1; q <= 24; q++) {
                    double pD = Math.round(ratio * q);
                    int p = (int) pD;
                    double approx = ((double)p / q) * Math.PI;
                    if (Math.abs(val - approx) < 1e-10) {
                        double r = Math.cos(approx);
                        if (Math.abs(r) < 1e-12) return "0";
                        if (Math.abs(Math.abs(r) - 1.0) < 1e-12) return String.valueOf((int)Math.signum(r));
                        return String.valueOf(r);
                    }
                }
            }
            if (entrada.contains("tan(")) {
                String arg = extraerPrimerArgumento(entrada, "tan");
                if (arg == null) return null;
                double val = evaluar(arg);
                double ratio = val / Math.PI;
                for (int q = 1; q <= 24; q++) {
                    double pD = Math.round(ratio * q);
                    int p = (int) pD;
                    double approx = ((double)p / q) * Math.PI;
                    if (Math.abs(val - approx) < 1e-10) {
                        double cosv = Math.cos(approx);
                        if (Math.abs(cosv) < 1e-12) return "Undefined"; // tan indefinida
                        double r = Math.tan(approx);
                        if (Math.abs(r) < 1e-12) return "0";
                        return String.valueOf(r);
                    }
                }
            }
        } catch (Exception e) {
            // si algo falla, no interrumpir; devolver null para proceder con evaluación normal
            System.out.println("DEBUG: formatearTrigConPi excepción: " + e.getMessage());
            return null;
        }
        return null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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

        btnIgual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIgualActionPerformed(evt);
            }
        });
        pantalla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pantallaActionPerformed(evt);
            }
        });

        btnShift.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnShift.setText("Shift");
        btnShift.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShiftActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("x!");

        btnInverso.setText("x^-1");
        btnInverso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInversoActionPerformed(evt);
            }
        });

    btnCubo.setText("x³");
        btnCubo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCuboActionPerformed(evt);
            }
        });

        btnRaiz.setText("raiz");
        btnRaiz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRaizActionPerformed(evt);
            }
        });

        btnOn.setText("ON");
        btnOn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOnActionPerformed(evt);
            }
        });

        btnCuadrado.setText("x^2");
        btnCuadrado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCuadradoActionPerformed(evt);
            }
        });

        btnPotencia.setText("x^y");
        btnPotencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPotenciaActionPerformed(evt);
            }
        });

        btnLog.setText("LOG");
        btnLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogActionPerformed(evt);
            }
        });

        btnLn.setText("In");
        btnLn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLnActionPerformed(evt);
            }
        });

        btnSin.setText("sin");
        btnSin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSinActionPerformed(evt);
            }
        });

        btnCos.setText("cos");
        btnCos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCosActionPerformed(evt);
            }
        });

        btnTan.setText("tan");
        btnTan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTanActionPerformed(evt);
            }
        });

        btnParentesisIzq.setText("(");
        btnParentesisIzq.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnParentesisIzqActionPerformed(evt);
            }
        });

        btnParentesisDer.setText(")");
        btnParentesisDer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnParentesisDerActionPerformed(evt);
            }
        });

        btn7.setText("7");
        btn7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn7ActionPerformed(evt);
            }
        });

        btn4.setText("4");
        btn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn4ActionPerformed(evt);
            }
        });

        btn1.setText("1");
        btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn1ActionPerformed(evt);
            }
        });

        btn0.setText("0");
        btn0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn0ActionPerformed(evt);
            }
        });

        btn8.setText("8");
        btn8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn8ActionPerformed(evt);
            }
        });

        btn5.setText("5");
        btn5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn5ActionPerformed(evt);
            }
        });

        btn2.setText("2");
        btn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn2ActionPerformed(evt);
            }
        });

        btnPunto.setText(".");
        btnPunto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPuntoActionPerformed(evt);
            }
        });

        btn9.setText("9");
        btn9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn9ActionPerformed(evt);
            }
        });

        btn6.setText("6");
        btn6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn6ActionPerformed(evt);
            }
        });

        btn3.setText("3");
        btn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn3ActionPerformed(evt);
            }
        });

        btnExp.setText("EXP");
        btnExp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExpActionPerformed(evt);
            }
        });

        btnDel.setText("DEL");
        btnDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDelActionPerformed(evt);
            }
        });

        btnDivision.setText("/");
        btnDivision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDivisionActionPerformed(evt);
            }
        });

        btnMultiplicacion.setText("*");
        btnMultiplicacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMultiplicacionActionPerformed(evt);
            }
        });

        btnResta.setText("-");
        btnResta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRestaActionPerformed(evt);
            }
        });

        btnAC.setText("AC");
        btnAC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnACActionPerformed(evt);
            }
        });

        btnSuma.setText("+");
        btnSuma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSumaActionPerformed(evt);
            }
        });

        btnIgual.setText("=");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("3 √");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText(" √");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("sin^-1");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("cos^-1");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("π");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("tan^-1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pantalla)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnShift, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnOn, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(btnInverso, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnCubo, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btn7, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btn4, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btn0, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btn8, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btn5, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btn2, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnPunto, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(btn9, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(btn6, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(btn3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(btnExp, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(btnSin, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnCos, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnTan, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnDel, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnDivision, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnParentesisIzq, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnAC, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnSuma, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnParentesisDer, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(btnResta, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(btnMultiplicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnIgual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnRaiz, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(6, 6, 6)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(btnCuadrado, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnPotencia, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnLog, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnLn, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(0, 10, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pantalla, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnShift, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnOn, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(btnInverso)
                                        .addComponent(btnCubo))
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnRaiz)
                                    .addComponent(btnCuadrado)
                                    .addComponent(btnPotencia)
                                    .addComponent(btnLog)
                                    .addComponent(btnLn))
                                .addGap(10, 10, 10)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnSin)
                                    .addComponent(btnCos)
                                    .addComponent(btnTan)
                                    .addComponent(btnParentesisIzq)
                                    .addComponent(btnParentesisDer))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(btn7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btn4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btn1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btn0))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnDel)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnDivision))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnAC)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnSuma)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(btnMultiplicacion)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(btnResta))
                                            .addComponent(btnIgual, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(btn8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btn2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnPunto)))
                        .addContainerGap(35, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn3)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnExp)
                        .addGap(18, 18, 18))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnShiftActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShiftActionPerformed
    shiftActivo = !shiftActivo;
    btnShift.setBackground(shiftActivo ? Color.YELLOW : null);

    if (shiftActivo) {
        btnSin.setText("asin");
        btnCos.setText("acos");
        btnTan.setText("atan");
    btnCubo.setText("∛");   // raíz cúbica cuando Shift
        btnRaiz.setText("∛");    // raíz cúbica
        btnInverso.setText("x!");
    btnPotencia.setText("√");
    btnExp.setText("π");
    } else {
        btnSin.setText("sin");
        btnCos.setText("cos");
        btnTan.setText("tan");
        btnCubo.setText("x³");
        btnRaiz.setText("√");
        btnInverso.setText("x^-1");
    btnPotencia.setText("x^y");
    btnExp.setText("EXP");
    }
    }//GEN-LAST:event_btnShiftActionPerformed

    private void btnInversoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInversoActionPerformed
    if (!encendida || !pantalla.isEditable()) {
        javax.swing.JOptionPane.showMessageDialog(this, "Enciende la calculadora antes de operar (botón ON)", "Info", javax.swing.JOptionPane.INFORMATION_MESSAGE);
        return;
    }
    String txt = pantalla.getText();
    if (txt == null) txt = "";
    if (txt.isEmpty()) {
        // nothing to invert or factorial
        return;
    }
    // Añadir el sufijo aunque la pantalla tenga una expresión (ej: (2+3) o 7)
    if (shiftActivo) {
        pantalla.setText(txt + "!");
        System.out.println("DEBUG: pantalla after factorial button -> " + pantalla.getText());
    } else {
        pantalla.setText(txt + "^-1");
        System.out.println("DEBUG: pantalla after inverse button -> " + pantalla.getText());
    }
    }//GEN-LAST:event_btnInversoActionPerformed

    private void btnCuboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCuboActionPerformed
        if (!encendida || !pantalla.isEditable()) return;
        if (shiftActivo) {
            // Cuando Shift: anteponer "3√" al último número o expresión entre paréntesis
            String txt = pantalla.getText();
            if (txt.matches(".*(\\([^)]*\\))$")) {
                // termina en paréntesis: anteponer 3√ justo antes del último paréntesis
                pantalla.setText(txt.replaceFirst("(\\([^)]*\\))$", "3√$1"));
            } else if (txt.matches(".*([0-9]+(\\.[0-9]+)?)$")) {
                // termina en número: anteponer 3√ al número final
                pantalla.setText(txt.replaceFirst("([0-9]+(\\.[0-9]+)?)$", "3√$1"));
            } else {
                // no hay número ni paréntesis final, simplemente insertar el símbolo
                agregarPantalla("3√");
            }
        } else {
            // Sin Shift: añadir ^3 para que la expresión quede, por ejemplo, 7^3 y se calcule con '='
            agregarPantalla("^3");
        }
    }//GEN-LAST:event_btnCuboActionPerformed

    private void btnRaizActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRaizActionPerformed
        if (!encendida || !pantalla.isEditable()) return;
        if (shiftActivo) {
            // raíz cúbica
            agregarPantalla("cbrt(");
        } else {
            agregarPantalla("sqrt(");
        }
    }//GEN-LAST:event_btnRaizActionPerformed

    private void btnOnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOnActionPerformed
        encendida = !encendida;
        if (encendida) {
            pantalla.setText("");
            pantalla.setEditable(true);
            pantalla.setBackground(Color.WHITE);
        } else {
            pantalla.setText("");
            pantalla.setEditable(false);
            pantalla.setBackground(Color.LIGHT_GRAY);
        }
    }//GEN-LAST:event_btnOnActionPerformed

    private void btnCuadradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCuadradoActionPerformed
    // Toma el texto actual y agrega ^2 al final
    pantalla.setText(pantalla.getText() + "^2");
        
    }//GEN-LAST:event_btnCuadradoActionPerformed

    private void btnPotenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPotenciaActionPerformed
    // En modo normal, agregar operador potencia '^'.
    // Cuando Shift está activo, insertar símbolo de raíz '√' en la pantalla
    // y delegar el cálculo al presionar '=' (evaluador ya transforma '√' a potencia 1/2).
    if (!encendida || !pantalla.isEditable()) return;
    if (shiftActivo) {
        // Insertar el símbolo de raíz. El usuario puede escribir el número a continuación
        // o usar paréntesis: ejemplo '√9' o '√(9+7)'.
        agregarPantalla("√");
    } else {
        agregarPantalla("^");
    }
    }//GEN-LAST:event_btnPotenciaActionPerformed

    private void btnLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogActionPerformed
    if (encendida && pantalla.isEditable()) agregarPantalla("log(");
    }//GEN-LAST:event_btnLogActionPerformed

    private void btnLnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLnActionPerformed
    if (encendida && pantalla.isEditable()) agregarPantalla("ln(");
    
    }//GEN-LAST:event_btnLnActionPerformed

    private void btnSinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSinActionPerformed
    if (!encendida || !pantalla.isEditable()) return;
    if (shiftActivo) {
        agregarPantalla("asin(");
    } else {
        agregarPantalla("sin(");
    }
    // Ahora el usuario puede ingresar cualquier número como argumento
    }//GEN-LAST:event_btnSinActionPerformed

    private void btnCosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCosActionPerformed
    if (!encendida || !pantalla.isEditable()) return;
    if (shiftActivo) {
        agregarPantalla("acos(");
    } else {
        agregarPantalla("cos(");
    }
    // Ahora el usuario puede ingresar cualquier número como argumento
    }//GEN-LAST:event_btnCosActionPerformed

    private void btnTanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTanActionPerformed
    if (!encendida || !pantalla.isEditable()) return;
    if (shiftActivo) {
        agregarPantalla("atan(");
    } else {
        agregarPantalla("tan(");
    }
    // Ahora el usuario puede ingresar cualquier número como argumento
    }//GEN-LAST:event_btnTanActionPerformed

    private void btnParentesisIzqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnParentesisIzqActionPerformed
    agregarPantalla("(");
    }//GEN-LAST:event_btnParentesisIzqActionPerformed

    private void btnParentesisDerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnParentesisDerActionPerformed
    agregarPantalla(")");
    }//GEN-LAST:event_btnParentesisDerActionPerformed

    private void btn7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn7ActionPerformed
    agregarPantalla("7");
    }//GEN-LAST:event_btn7ActionPerformed

    private void btn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn4ActionPerformed
    agregarPantalla("4");
    }//GEN-LAST:event_btn4ActionPerformed

    private void btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn1ActionPerformed
    if (encendida && pantalla.isEditable()) agregarPantalla("1");
    }//GEN-LAST:event_btn1ActionPerformed

    private void btn0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn0ActionPerformed
    if (encendida && pantalla.isEditable()) agregarPantalla("0");
    }//GEN-LAST:event_btn0ActionPerformed

    private void btn8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn8ActionPerformed
    if (encendida && pantalla.isEditable()) agregarPantalla("8");
    }//GEN-LAST:event_btn8ActionPerformed

    private void btn5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn5ActionPerformed
    if (encendida && pantalla.isEditable()) agregarPantalla("5");
    }//GEN-LAST:event_btn5ActionPerformed

    private void btn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn2ActionPerformed
    if (encendida && pantalla.isEditable()) agregarPantalla("2");
    }//GEN-LAST:event_btn2ActionPerformed

    private void btnPuntoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPuntoActionPerformed
    if (encendida && pantalla.isEditable()) agregarPantalla(".");
    }//GEN-LAST:event_btnPuntoActionPerformed

    private void btn9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn9ActionPerformed
    if (encendida && pantalla.isEditable()) agregarPantalla("9");
    }//GEN-LAST:event_btn9ActionPerformed

    private void btn6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn6ActionPerformed
    if (encendida && pantalla.isEditable()) agregarPantalla("6");
    }//GEN-LAST:event_btn6ActionPerformed

    private void btn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn3ActionPerformed
    if (encendida && pantalla.isEditable()) agregarPantalla("3");
    }//GEN-LAST:event_btn3ActionPerformed

    private void btnExpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExpActionPerformed
    if (!encendida || !pantalla.isEditable()) return;
    if (shiftActivo) {
        // Shift + EXP => insertar símbolo de pi (visualizado en el botón)
        agregarPantalla("π");
    } else {
        // EXP normal => notación científica (E)
        agregarPantalla("E");
    }
    }//GEN-LAST:event_btnExpActionPerformed

    private void btnDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDelActionPerformed
    borrarUltimo();
    }//GEN-LAST:event_btnDelActionPerformed

    private void btnDivisionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDivisionActionPerformed
    agregarPantalla("/");
    }//GEN-LAST:event_btnDivisionActionPerformed

    private void btnMultiplicacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMultiplicacionActionPerformed
    agregarPantalla("*");
    }//GEN-LAST:event_btnMultiplicacionActionPerformed

    private void btnRestaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRestaActionPerformed
    agregarPantalla("-");
    }//GEN-LAST:event_btnRestaActionPerformed

    private void btnACActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnACActionPerformed
    limpiarPantalla();
    }//GEN-LAST:event_btnACActionPerformed

    private void btnSumaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSumaActionPerformed
    agregarPantalla("+");
    }//GEN-LAST:event_btnSumaActionPerformed

    private void pantallaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pantallaActionPerformed
    // No hace nada
    }//GEN-LAST:event_pantallaActionPerformed

    private void btnIgualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIgualActionPerformed
        mostrarResultado();
    }//GEN-LAST:event_btnIgualActionPerformed


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Interfaz.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                System.out.println("DEBUG: About to create Interfaz and setVisible(true)");
                new Interfaz().setVisible(true);
                System.out.println("DEBUG: setVisible(true) returned");
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn0;
    private javax.swing.JButton btn1;
    private javax.swing.JButton btn2;
    private javax.swing.JButton btn3;
    private javax.swing.JButton btn4;
    private javax.swing.JButton btn5;
    private javax.swing.JButton btn6;
    private javax.swing.JButton btn7;
    private javax.swing.JButton btn8;
    private javax.swing.JButton btn9;
    private javax.swing.JButton btnAC;
    private javax.swing.JButton btnCos;
    private javax.swing.JButton btnCuadrado;
    private javax.swing.JButton btnCubo;
    private javax.swing.JButton btnDel;
    private javax.swing.JButton btnDivision;
    private javax.swing.JButton btnExp;
    private javax.swing.JButton btnIgual;
    private javax.swing.JButton btnInverso;
    private javax.swing.JButton btnLn;
    private javax.swing.JButton btnLog;
    private javax.swing.JButton btnMultiplicacion;
    private javax.swing.JButton btnOn;
    private javax.swing.JButton btnParentesisDer;
    private javax.swing.JButton btnParentesisIzq;
    private javax.swing.JButton btnPotencia;
    private javax.swing.JButton btnPunto;
    private javax.swing.JButton btnRaiz;
    private javax.swing.JButton btnResta;
    private javax.swing.JButton btnShift;
    private javax.swing.JButton btnSin;
    private javax.swing.JButton btnSuma;
    private javax.swing.JButton btnTan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField pantalla;
    // End of variables declaration//GEN-END:variables
}