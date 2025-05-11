import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class main {
    private static final String BDS = "habitos";
    public static JFrame frameMENU ;
    public static JPanel panelMENU ;
    public static JPanel panelCentral ;
    public static JPanel panelBotonesMenu;

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

        panelBotonesMenu = new JPanel();

        btnhabitos = new JButton("habitos");
        btnhabitos.addActionListener(e -> {
            ventanaHabitos();
        });
        btnhabitos.setFont(new Font("Arial", Font.BOLD, 18));
        panelBotonesMenu.add(btnhabitos);

        btnregistro = new JButton("registro");
        btnregistro.addActionListener(e -> {
            ventanaRegistro();
        });
        btnregistro.setFont(new Font("Arial", Font.BOLD, 18));
        panelBotonesMenu.add(btnregistro);


        panelMENU.add(panelCentral, BorderLayout.CENTER);
        panelMENU.add(panelBotonesMenu, BorderLayout.SOUTH);
        frameMENU.add(panelMENU);
        panelCentral.setBackground(COLOR_FONDO_LIGHT);
        panelBotonesMenu.setBackground(COLOR_FONDO_LIGHT);
        btnregistro.setBackground(COLOR_BOTON_LIGHT);
        btnhabitos.setBackground(COLOR_BOTON_LIGHT);

    }

    private static void ventanaHabitos() {
        JDialog dialogHabitos = new JDialog(frameMENU, "Habitos", true);
        dialogHabitos.setSize(frameMENU.getWidth(), frameMENU.getHeight());
        dialogHabitos.setLocationRelativeTo(frameMENU);





        crearPanelTitulo();
        crearPanelTabla();
        crearPanelBotones();

        dialogHabitos.add(panelTitulo, BorderLayout.NORTH);
        dialogHabitos.add(panelJTable, BorderLayout.CENTER);
        dialogHabitos.add(panelBotonesMenu, BorderLayout.SOUTH);




        dialogHabitos.setVisible(true);
    }
    private static void ventanaRegistro() {
        JDialog dialogRegistro = new JDialog(frameMENU, "Registro", true);
        dialogRegistro.setSize(frameMENU.getWidth(), frameMENU.getHeight());
        dialogRegistro.setLocationRelativeTo(frameMENU);



        dialogRegistro.setVisible(true);
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
                btnregistro.setSize(new Dimension(120,50));
                btnhabitos.setSize(new Dimension(120,50));            }
        });
        JRadioButtonMenuItem radio2 = new JRadioButtonMenuItem("Botones pequeños");
                radio2.addActionListener(e -> {
                    if (radio2.isSelected()){
                        btnregistro.setSize(90,30);
                        btnhabitos.setSize(90,30);                     }
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
