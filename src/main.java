import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class main {
    private static final String BDS = "habitos";
    public static JFrame frameMENU ;
    public static JPanel panelMENU ;
    public static JPanel panelCentral ;
    public static JPanel panelBotonesMenu;
    static JDialog dialogHabitos;

    public static JPanel paneltitulo;
    public static JPanel panelJTable;
    public static JPanel panelBotones;
    public static JTable tabla;

    private static JPanel panelTituloRegistro;
    private static JPanel panelAreaTexto;
    private static JTextArea areaTexto;

    static JButton btnhabitos;
    static JButton btnregistro;
    private static final Color COLOR_FONDO_LIGHT = new Color(215, 200, 200);
    private static final Color COLOR_FONDO_DARK = new Color(50, 50, 50);
    private static final Color COLOR_BOTON_LIGHT = new Color(135, 206, 250);
    private static final Color COLOR_BOTON_DARK = new Color(168, 143, 243);


    public static void main(String[] args) {
        frameMENU = new JFrame();
        frameMENU.setTitle("Habitos saludables");
        frameMENU.setSize(800, 600);
        frameMENU.setDefaultCloseOperation(EXIT_ON_CLOSE);
        crearPanel();
        crearJMenu();
        frameMENU.setLocationRelativeTo(null);
        frameMENU.setResizable(false);
        frameMENU.setVisible(true);
    }

    private static void crearPanel() {
        panelMENU = new JPanel();
        panelMENU.setLayout(new BorderLayout());

        panelCentral = new JPanel();
        ImageIcon habitosIMG = new ImageIcon("imagen.jpg");
        Image imagenOriginal = habitosIMG.getImage();
        Image imagenEscalada = imagenOriginal
                .getScaledInstance(400, 400, Image.SCALE_SMOOTH);
        ImageIcon imagenIconoEscalado = new ImageIcon(imagenEscalada);
        JLabel imagenLabel = new JLabel(imagenIconoEscalado);
        panelCentral.add(imagenLabel);

        crearPanelBotonesMenu(0);

        panelMENU.add(panelCentral, BorderLayout.CENTER);
        frameMENU.add(panelMENU);

        panelBotonesMenu.setBackground(COLOR_FONDO_LIGHT);
        panelCentral.setBackground(COLOR_FONDO_LIGHT);
        panelBotonesMenu.setBackground(COLOR_FONDO_LIGHT);
        btnregistro.setBackground(COLOR_BOTON_LIGHT);
        btnhabitos.setBackground(COLOR_BOTON_LIGHT);

    }

    private static void crearPanelBotonesMenu(int n) {
        if (panelBotonesMenu!=null){
            panelMENU.remove(panelBotonesMenu);
        }

        panelBotonesMenu = new JPanel();
        btnhabitos = new JButton("habitos");
        btnhabitos.addActionListener(e -> {
            ventanaHabitos();
        });
        btnhabitos.setFont(new Font("Arial", Font.BOLD, 18));

        btnregistro = new JButton("registro");
        btnregistro.addActionListener(e -> {
            ventanaRegistro();
        });
        btnregistro.setFont(new Font("Arial", Font.BOLD, 18));

        if (n==1){
            btnregistro.setPreferredSize(new Dimension(150,70));
            btnhabitos.setPreferredSize(new Dimension(150,70));
        } else if (n==2) {
            btnregistro.setPreferredSize(new Dimension(110,20));
            btnhabitos.setPreferredSize(new Dimension(110,20));
        }

        if (panelCentral.getBackground()!=COLOR_FONDO_LIGHT){
            panelBotonesMenu.setBackground(COLOR_FONDO_DARK);
            btnregistro.setBackground(COLOR_BOTON_DARK);
            btnhabitos.setBackground(COLOR_BOTON_DARK);
        }else {
            panelBotonesMenu.setBackground(COLOR_FONDO_LIGHT);
            btnregistro.setBackground(COLOR_BOTON_LIGHT);
            btnhabitos.setBackground(COLOR_BOTON_LIGHT);

        }

        panelBotonesMenu.add(btnhabitos);
        panelBotonesMenu.add(btnregistro);

        panelMENU.add(panelBotonesMenu, BorderLayout.SOUTH);
        panelMENU.revalidate();
        panelMENU.repaint();
    }

    private static void ventanaHabitos() {
        dialogHabitos = new JDialog(frameMENU, "Habitos", true);
        dialogHabitos.setSize(frameMENU.getWidth(), frameMENU.getHeight());
        dialogHabitos.setLocationRelativeTo(frameMENU);
        dialogHabitos.setLayout(new BorderLayout());

        crearPanelTitulo();
        crearPanelTabla();
        crearPanelBotones();

        dialogHabitos.add(paneltitulo, BorderLayout.NORTH);
        dialogHabitos.add(panelJTable, BorderLayout.CENTER);
        dialogHabitos.add(panelBotones, BorderLayout.SOUTH);

        dialogHabitos.setVisible(true);
    }
    private static void ActualizarPanelTabla(){
        dialogHabitos.remove(panelJTable);
        crearPanelTabla();
        dialogHabitos.add(panelJTable, BorderLayout.CENTER);
        dialogHabitos.revalidate();
        dialogHabitos.repaint();

    }
    private static void crearPanelTabla() {
        panelJTable = new JPanel(new BorderLayout());
        panelJTable.setBorder(BorderFactory
                .createEmptyBorder(0, 20, 0, 20));

        DefaultTableModel dtm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        dtm.addColumn("col1");
        dtm.addColumn("col2");
        dtm.addColumn("col3");
        dtm.addColumn("col4");

        tabla = new JTable(dtm);
        tabla.setRowHeight(25);
        tabla.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        panelJTable.add(scrollTabla, BorderLayout.CENTER);

        cargarDatosTabla(dtm);
    }

    private static void cargarDatosTabla(DefaultTableModel dtm) {
        try {
            ArrayList<Object[]> datosBaseDatos = GestionBaseDatos.mostrarDatosTabla();

            if (datosBaseDatos != null  && !datosBaseDatos.isEmpty()) {
                for (Object[] fila : datosBaseDatos) {
                    dtm.addRow(fila);
                }
            } else {
                JOptionPane.showMessageDialog(dialogHabitos, "No se encuentran datos en la tabla",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(dialogHabitos, "Error en la base de datos "+e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void crearPanelTitulo() {
        paneltitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        paneltitulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Titulo ventana");
        titulo.setFont(new Font("Montserrat", Font.BOLD, 25));
        paneltitulo.setBackground(Color.gray);
        titulo.setForeground(Color.white);
        paneltitulo.add(titulo);
    }

    private static void crearPanelBotones() {
        panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton boton1 = new JButton("anadir");
        boton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialogoAnadir = new JDialog(dialogHabitos, "Anadir", true);
                dialogoAnadir.setSize(500, 400);
                dialogoAnadir.setLocationRelativeTo(dialogHabitos);
                dialogoAnadir.setResizable(false);
                dialogoAnadir.setModal(true);
                dialogoAnadir.setLayout(new BorderLayout());

                JPanel panelSuperiorDialog = new JPanel();
                panelSuperiorDialog.setLayout(new GridLayout(2, 2, 10, 10));
                panelSuperiorDialog.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

                JLabel textoAnadir = new JLabel("Nombre:");
                JTextField campoAnadir = new JTextField(20);

                JLabel textoFrecuencia = new JLabel("Frecuencia");
                JComboBox<String> listaOpciones = new JComboBox<>(new String[]{"diaria", "semanal"}); //opcion 1 y opcion2 de la lista

                panelSuperiorDialog.add(textoAnadir);
                panelSuperiorDialog.add(campoAnadir);
                panelSuperiorDialog.add(textoFrecuencia);
                panelSuperiorDialog.add(listaOpciones);

                JPanel panelCentralDialog = new JPanel(new BorderLayout(10, 0));
                panelCentralDialog.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

                JTextArea areaTexto = new JTextArea();
                areaTexto.setLineWrap(true);
                areaTexto.setWrapStyleWord(true);

                JScrollPane scrollPane = new JScrollPane(areaTexto);
                scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

                JPanel panelEtiqueta = new JPanel(new BorderLayout());
                JLabel etiquetaAreaTexto = new JLabel("Descripcion:");
                panelEtiqueta.add(etiquetaAreaTexto, BorderLayout.NORTH);

                panelCentralDialog.add(panelEtiqueta, BorderLayout.WEST);
                panelCentralDialog.add(scrollPane, BorderLayout.CENTER);

                JPanel panelInferiorDialog = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
                panelInferiorDialog.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

                JButton botonAceptar = new JButton("Aceptar");
                botonAceptar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String nombre = campoAnadir.getText();
                        String frecuencia = (String) listaOpciones.getSelectedItem();
                        String descripcion = areaTexto.getText();

                        if (campoAnadir.getText().trim().isEmpty() || areaTexto.getText().trim().isEmpty()) {
                            JOptionPane.showMessageDialog(dialogoAnadir, "Aviso, no has introducido ningun dato.",
                                    "Aviso",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            GestionBaseDatos.insertarDatos(nombre, frecuencia, descripcion);
                            campoAnadir.setText("");
                            areaTexto.setText("");

                            dialogoAnadir.dispose();
                            ActualizarPanelTabla();

                        }
                    }
                });

                JButton botonCancelar = new JButton("Cancelar");
                botonCancelar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dialogoAnadir.dispose();
                    }
                });
                panelInferiorDialog.add(botonAceptar);
                panelInferiorDialog.add(botonCancelar);

                dialogoAnadir.add(panelSuperiorDialog, BorderLayout.NORTH);
                dialogoAnadir.add(panelCentralDialog, BorderLayout.CENTER);
                dialogoAnadir.add(panelInferiorDialog, BorderLayout.SOUTH);

                dialogoAnadir.setVisible(true);
            }
        });

        JButton boton2 = new JButton("modificar");
        boton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccinada = tabla.getSelectedRow();

                if (filaSeleccinada < 0) {
                    JOptionPane.showMessageDialog(dialogHabitos, "Porfavor selecciona un dato",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    int id = (int) tabla.getValueAt(filaSeleccinada, 0);
                    String nombre = (String) tabla.getValueAt(filaSeleccinada, 1);
                    String descripcion = (String) tabla.getValueAt(filaSeleccinada, 2);
                    String frecuencia = (String) tabla.getValueAt(filaSeleccinada, 3);

                    JDialog dialogoModificar = new JDialog(dialogHabitos, "Modificar", true);
                    dialogoModificar.setSize(500, 400);
                    dialogoModificar.setLocationRelativeTo(dialogHabitos);
                    dialogoModificar.setResizable(false);
                    dialogoModificar.setModal(true);
                    dialogoModificar.setLayout(new BorderLayout());

                    JPanel panelSuperiorDialog = new JPanel();
                    panelSuperiorDialog.setLayout(new GridLayout(2, 2, 10, 10));
                    panelSuperiorDialog.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

                    JLabel textoNombre = new JLabel("Nombre:");
                    JTextField campoNombre = new JTextField(nombre);

                    JLabel textoFrecuencia = new JLabel("Frecuencia:");
                    String[] opciones = {"diaria", "semanal"};
                    JComboBox<String> listaOpciones = new JComboBox<>(opciones);

                    if (frecuencia.equals("diaria")) {
                        listaOpciones.setSelectedIndex(0);
                    } else if (frecuencia.equals("semanal")) {
                        listaOpciones.setSelectedIndex(1);
                    } else {
                        listaOpciones.setSelectedIndex(0);
                    }

                    panelSuperiorDialog.add(textoNombre, BorderLayout.WEST);
                    panelSuperiorDialog.add(campoNombre, BorderLayout.CENTER);
                    panelSuperiorDialog.add(textoFrecuencia);
                    panelSuperiorDialog.add(listaOpciones);

                    JPanel panelCentralDialog = new JPanel(new BorderLayout(10, 0));
                    panelCentralDialog.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

                    JLabel textoDescripcion = new JLabel("Descripcion");
                    JTextArea campoDescripcion = new JTextArea(descripcion);
                    campoDescripcion.setLineWrap(true);
                    campoDescripcion.setWrapStyleWord(true);

                    JScrollPane scrollPane = new JScrollPane(campoDescripcion);
                    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

                    panelCentralDialog.add(textoDescripcion, BorderLayout.WEST);
                    panelCentralDialog.add(scrollPane, BorderLayout.CENTER);

                    JPanel panelInferiorDialog = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
                    panelInferiorDialog.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

                    JButton botonAceptar = new JButton("Aceptar");
                    botonAceptar.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (campoNombre.getText().trim().isEmpty() || campoDescripcion.getText().trim().isEmpty()) {
                                JOptionPane.showMessageDialog(dialogoModificar, "No puedes dejar campos vacios!",
                                        "Error de modificacion",
                                        JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            String nuevoNombre = campoNombre.getText().trim();
                            String nuevaDescripcion = campoDescripcion.getText().trim();
                            String nuevaFrecuencia = (String) listaOpciones.getSelectedItem();

                            boolean exitoActualizar = GestionBaseDatos.modificarDatos(nuevoNombre, nuevaDescripcion, nuevaFrecuencia, id);

                            if (exitoActualizar) {
                                JOptionPane.showMessageDialog(dialogoModificar, "Datos modificados correctamente",
                                        "Exito",
                                        JOptionPane.INFORMATION_MESSAGE);

                                dialogoModificar.dispose();
                                ActualizarPanelTabla();

                            } else {
                                JOptionPane.showMessageDialog(dialogoModificar, "Error al actualizar",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });
                    panelInferiorDialog.add(botonAceptar);


                    JButton botonCancelar = new JButton("Cancelar");
                    botonCancelar.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            dialogoModificar.dispose();

                        }
                    });
                    panelInferiorDialog.add(botonCancelar);


                    dialogoModificar.add(panelSuperiorDialog, BorderLayout.NORTH);
                    dialogoModificar.add(panelCentralDialog, BorderLayout.CENTER);
                    dialogoModificar.add(panelInferiorDialog, BorderLayout.SOUTH);
                    dialogoModificar.setVisible(true);
                }
            }
        });

        JButton boton3 = new JButton("eliminar");
        boton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int filaSeleccinada = tabla.getSelectedRow();

                if (filaSeleccinada < 0) {
                    JOptionPane.showMessageDialog(dialogHabitos, "Porfavor selecciona un dato",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    int id = (int) tabla.getValueAt(filaSeleccinada, 0);

                    int confirmarDelete = JOptionPane.showConfirmDialog(dialogHabitos,
                            "Seguro que quieres eliminar el registro?",
                            "Confirmacion",
                            JOptionPane.YES_NO_OPTION);

                    if (confirmarDelete == JOptionPane.YES_OPTION) {
                        boolean exitoEliminar = GestionBaseDatos.eliminarDatos(id);

                        if (exitoEliminar) {
                            JOptionPane.showMessageDialog(dialogHabitos,
                                    "Datos eliminados",
                                    "Exito",
                                    JOptionPane.INFORMATION_MESSAGE);
                            ActualizarPanelTabla();

                        } else {
                            JOptionPane.showMessageDialog(dialogHabitos,
                                    "Error al eliminar el registro",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
        panelBotones.add(boton1);
        panelBotones.add(boton2);
        panelBotones.add(boton3);
    }



    private static void ventanaRegistro() {
        JDialog dialogRegistro = new JDialog(frameMENU, "Registro", true);
        dialogRegistro.setSize(frameMENU.getWidth(), frameMENU.getHeight());
        dialogRegistro.setLocationRelativeTo(frameMENU);
        dialogRegistro.setLayout(new BorderLayout());

        crearPanelTituloRegistro();
        crearPanelAreaTexto();

        dialogRegistro.add(panelTituloRegistro, BorderLayout.NORTH);
        dialogRegistro.add(panelAreaTexto, BorderLayout.CENTER);



        dialogRegistro.setVisible(true);
    }
    private static void crearPanelTituloRegistro() {
        panelTituloRegistro = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelTituloRegistro.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Titulo ventana 2");
        titulo.setFont(new Font("Montserrat", Font.BOLD, 30));

        panelTituloRegistro.add(titulo);
    }
    private static void crearPanelAreaTexto() {
        panelAreaTexto = new JPanel(new BorderLayout());
        panelAreaTexto.setBorder(BorderFactory.createEmptyBorder(50, 100, 100, 100));

        areaTexto = new JTextArea();
        areaTexto.setFont(new Font("Montserrat", Font.PLAIN, 12));
        areaTexto.setEditable(false);
        areaTexto.setLineWrap(true);
        areaTexto.setWrapStyleWord(true);



        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;

        try {
            conn = connect();

            String sql = "SELECT h.nombre,\n" +
                    "COUNT(r.id) AS total_registros,\n" +
                    "SUM(r.cumplido) AS veces_cumplido\n" +
                    "FROM habitos h LEFT JOIN registro r ON h.id = r.id_habito\n" +
                    "GROUP BY h.id, h.nombre;";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                int total = rs.getInt("total_registros");
                int cumplido = rs.getInt("veces_cumplido");

                int porcentaje = (total > 0) ? (cumplido * 100 / total) : 0;
                areaTexto.append(nombre + "\n");
                areaTexto.append("Se ha cumplido el " + porcentaje + "% de las veces (" + cumplido + "/" + total + ")\n\n");
            }

        } catch (SQLException e) {
            System.out.println("Debug: Error al consultar datos "+e.getMessage());
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (rs != null) rs.close();
                main.disconnect(conn);
            } catch (SQLException e) {
                System.out.println("Debug: "+e.getMessage());
            }
        }



        JScrollPane scrollPane = new JScrollPane(areaTexto);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        panelAreaTexto.add(scrollPane, BorderLayout.CENTER);


    }







    public static void crearJMenu(){
        JMenuBar menuBar = new JMenuBar(); // Crear barra de menú
        JMenu menuAspecto = new JMenu("Configuracion"); // Crear el menú
        JCheckBoxMenuItem checkBox = new JCheckBoxMenuItem("Modo oscuro");
        checkBox.addActionListener(e -> {
            if (checkBox.isSelected()){
                panelCentral.setBackground(COLOR_FONDO_DARK);
                panelBotonesMenu.setBackground(COLOR_FONDO_DARK);
                btnregistro.setBackground(COLOR_BOTON_DARK);
                btnhabitos.setBackground(COLOR_BOTON_DARK);
            }else {
                panelCentral.setBackground(COLOR_FONDO_LIGHT);
                panelBotonesMenu.setBackground(COLOR_FONDO_LIGHT);
                btnregistro.setBackground(COLOR_BOTON_LIGHT);
                btnhabitos.setBackground(COLOR_BOTON_LIGHT);

            }
        });
        JRadioButtonMenuItem radio1 = new JRadioButtonMenuItem("Botones grandes");
                radio1.setSelected(true);
            radio1.addActionListener(e -> {
                if (radio1.isSelected()){
                    crearPanelBotonesMenu(1);
                }
        });
        JRadioButtonMenuItem radio2 = new JRadioButtonMenuItem("Botones pequeños");
            radio2.addActionListener(e -> {
                if (radio2.isSelected()){
                    crearPanelBotonesMenu(2);
                }
            });
        ButtonGroup grupo = new ButtonGroup(); // Grupo para los radio
        grupo.add(radio1);
        grupo.add(radio2);
        menuAspecto.add(checkBox);
        menuAspecto.addSeparator();
        menuAspecto.add(radio1);
        menuAspecto.add(radio2);
        menuBar.add(menuAspecto); // Añadir menú a la barra de menú
        frameMENU.setJMenuBar(menuBar); // Establecer barra menú en ventana
    }
    public static Connection connect() {
        String URL = "jdbc:sqlite:"+BDS+".db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos");
            e.printStackTrace();
        }
        return conn;
    }

    public static void disconnect(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
}
