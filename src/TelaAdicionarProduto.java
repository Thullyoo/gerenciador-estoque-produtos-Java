import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TelaAdicionarProduto extends JFrame {

    public TelaAdicionarProduto() {
        setTitle("Adicionar Produto");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBackground(Color.DARK_GRAY);
        painelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel labelNome = criarLabel("Nome:");
        gbc.gridx = 0; gbc.gridy = 0;
        painelPrincipal.add(labelNome, gbc);

        JTextField campoNome = criarCampoTexto();
        gbc.gridx = 1; gbc.gridy = 0;
        painelPrincipal.add(campoNome, gbc);

        JLabel labelCor = criarLabel("Cor:");
        gbc.gridx = 0; gbc.gridy = 1;
        painelPrincipal.add(labelCor, gbc);

        JTextField campoCor = criarCampoTexto();
        gbc.gridx = 1; gbc.gridy = 1;
        painelPrincipal.add(campoCor, gbc);

        JLabel labelQuantidade = criarLabel("Quantidade:");
        gbc.gridx = 0; gbc.gridy = 2;
        painelPrincipal.add(labelQuantidade, gbc);

        JTextField campoQuantidade = criarCampoTexto();
        gbc.gridx = 1; gbc.gridy = 2;
        painelPrincipal.add(campoQuantidade, gbc);

        JLabel labelPreco = criarLabel("Preço:");
        gbc.gridx = 0; gbc.gridy = 3;
        painelPrincipal.add(labelPreco, gbc);

        JTextField campoPreco = criarCampoTexto();
        gbc.gridx = 1; gbc.gridy = 3;
        painelPrincipal.add(campoPreco, gbc);

        JButton botaoAdicionar = criarBotao("Adicionar Produto");
        gbc.gridx = 1; gbc.gridy = 4;
        painelPrincipal.add(botaoAdicionar, gbc);

        JButton botaoVoltar = criarBotao("Voltar");
        gbc.gridx = 0; gbc.gridy = 4;
        painelPrincipal.add(botaoVoltar, gbc);

        add(painelPrincipal);

        botaoAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = campoNome.getText();
                String cor = campoCor.getText();
                int quantidade;
                double preco;

                try {
                    quantidade = Integer.parseInt(campoQuantidade.getText());
                    preco = Double.parseDouble(campoPreco.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Quantidade e preço devem ser numéricos!");
                    return;
                }

                try (Connection conexao = ConexaoBanco.getConnection()) {
                    String sql = "INSERT INTO produtos (nome, cor, valor, quantidade) VALUES (?, ?, ?, ?)";
                    PreparedStatement stmt = conexao.prepareStatement(sql);
                    stmt.setString(1, nome);
                    stmt.setString(2, cor);
                    stmt.setDouble(3, preco);
                    stmt.setInt(4, quantidade);

                    int linhasInseridas = stmt.executeUpdate();
                    if (linhasInseridas > 0) {
                        JOptionPane.showMessageDialog(null, "Produto adicionado com sucesso!");
                        campoNome.setText("");
                        campoCor.setText("");
                        campoQuantidade.setText("");
                        campoPreco.setText("");
                    }

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erro ao adicionar o produto: " + ex.getMessage());
                }
            }
        });

        botaoVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TelaPrincipal();
                dispose();
            }
        });

        setVisible(true);
    }

    private JLabel criarLabel(String texto) {
        JLabel label = new JLabel(texto);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 16));
        return label;
    }

    private JTextField criarCampoTexto() {
        JTextField campoTexto = new JTextField(20);
        campoTexto.setFont(new Font("Arial", Font.PLAIN, 16));
        campoTexto.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return campoTexto;
    }

    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 16));
        botao.setBackground(new Color(34, 139, 34));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setPreferredSize(new Dimension(180, 40));
        return botao;
    }
}
