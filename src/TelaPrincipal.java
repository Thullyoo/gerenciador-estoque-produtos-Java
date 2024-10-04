import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaPrincipal extends JFrame {

    private JLabel titulo;
    private int posY = -100;

    public TelaPrincipal() {
        setTitle("Gerenciador de Produtos");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        titulo = new JLabel("Bem-vindo ao Gerenciador de Produtos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 36));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weighty = 0.1;
        add(titulo, gbc);

        JButton botaoListar = createButton("📋 Listar Produtos");
        JButton botaoAdicionar = createButton("➕ Adicionar Produto");
        JButton botaoRemover = createButton("🗑️ Remover Produto");
        JButton botaoEditar = createButton("✏️ Editar Produto");


        gbc.weighty = 0.5;
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(botaoListar, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(botaoAdicionar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(botaoRemover, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(botaoEditar, gbc);

        getContentPane().setBackground(Color.LIGHT_GRAY);

        botaoListar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaListarProdutos();
                dispose();
            }
        });

        botaoAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new TelaAdicionarProduto();
                dispose();
            }
        });

        botaoRemover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                new TelaRemoverProduto();
                dispose();
            }
        });

        botaoEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaEditarProduto();
                dispose();
            }
        });

        animateTitle();

        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 100));
        button.setBackground(new Color(30, 144, 255));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(70, 130, 180));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(30, 144, 255));
            }
        });

        return button;
    }

    private void animateTitle() {
        Timer timer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (posY < 50) {
                    posY += 5;
                    titulo.setLocation(titulo.getX(), posY);
                } else {
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        timer.start();
    }


}
