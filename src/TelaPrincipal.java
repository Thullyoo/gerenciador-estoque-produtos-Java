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
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBackground(Color.BLACK);
        add(painelPrincipal);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        titulo = new JLabel("Bem-vindo ao Gerenciador de Produtos", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 30));
        titulo.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.NORTH;
        painelPrincipal.add(titulo, gbc);

        JButton botaoListar = createButton("Listar Produtos");
        JButton botaoAdicionar = createButton("Adicionar Produto");
        JButton botaoRemover = createButton("Remover Produto");
        JButton botaoEditar = createButton("Editar Produto");

        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 1;
        painelPrincipal.add(botaoListar, gbc);

        gbc.gridx = 1;
        painelPrincipal.add(botaoAdicionar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        painelPrincipal.add(botaoRemover, gbc);

        gbc.gridx = 1;
        painelPrincipal.add(botaoEditar, gbc);

        botaoListar.addActionListener(e -> {
            new TelaListarProdutos();
            dispose();
        });

        botaoAdicionar.addActionListener(e -> {
            new TelaAdicionarProduto();
            dispose();
        });

        botaoRemover.addActionListener(e -> {
            new TelaRemoverProduto();
            dispose();
        });

        botaoEditar.addActionListener(e -> {
            new TelaEditarProduto();
            dispose();
        });

        animateTitle();

        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(250, 80));
        button.setBackground(new Color(60, 179, 113));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(46, 139, 87));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(60, 179, 113));
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
