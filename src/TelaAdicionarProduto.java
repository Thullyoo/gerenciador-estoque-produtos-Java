import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TelaAdicionarProduto extends JFrame {

    public TelaAdicionarProduto() {
        setTitle("Adicionar Produto");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel labelNome = new JLabel("Nome:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(labelNome, gbc);

        JTextField campoNome = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(campoNome, gbc);

        JLabel labelCor = new JLabel("Cor:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(labelCor, gbc);

        JTextField campoCor = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(campoCor, gbc);

        JLabel labelQuantidade = new JLabel("Quantidade:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(labelQuantidade, gbc);

        JTextField campoQuantidade = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(campoQuantidade, gbc);

        JLabel labelPreco = new JLabel("Preço:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(labelPreco, gbc);

        JTextField campoPreco = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(campoPreco, gbc);

        JButton botaoAdicionar = new JButton("Adicionar Produto");
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(botaoAdicionar, gbc);

        JButton botaoVoltar = new JButton("Voltar");
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(botaoVoltar, gbc);

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

        add(panel);
        setVisible(true);
    }

}
