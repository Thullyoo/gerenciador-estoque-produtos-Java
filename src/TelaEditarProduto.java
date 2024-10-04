import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TelaEditarProduto extends JFrame {

    public TelaEditarProduto() {
        setTitle("Editar Produto");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel labelId = new JLabel("ID do Produto:");
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(labelId, gbc);

        JTextField campoId = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(campoId, gbc);

        JLabel labelNome = new JLabel("Nome:");
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(labelNome, gbc);

        JTextField campoNome = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(campoNome, gbc);

        JLabel labelCor = new JLabel("Cor:");
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(labelCor, gbc);

        JTextField campoCor = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(campoCor, gbc);

        JLabel labelQuantidade = new JLabel("Quantidade:");
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(labelQuantidade, gbc);

        JTextField campoQuantidade = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 3;
        panel.add(campoQuantidade, gbc);

        JLabel labelPreco = new JLabel("Preço:");
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(labelPreco, gbc);

        JTextField campoPreco = new JTextField(20);
        gbc.gridx = 1; gbc.gridy = 4;
        panel.add(campoPreco, gbc);

        JButton botaoBuscar = new JButton("Buscar Produto");
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(botaoBuscar, gbc);

        JButton botaoEditar = new JButton("Salvar Alterações");
        gbc.gridx = 1; gbc.gridy = 5;
        panel.add(botaoEditar, gbc);

        JButton botaoVoltar = new JButton("Voltar");
        gbc.gridx = 0; gbc.gridy = 6;
        panel.add(botaoVoltar, gbc);

        botaoBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idProduto = campoId.getText();
                buscarProduto(idProduto, campoNome, campoCor, campoQuantidade, campoPreco);
            }
        });

        botaoEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idProduto = campoId.getText();
                String nome = campoNome.getText();
                String cor = campoCor.getText();
                String quantidade = campoQuantidade.getText();
                String preco = campoPreco.getText();

                editarProduto(idProduto, nome, cor, quantidade, preco);
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

    private void buscarProduto(String idProduto, JTextField campoNome, JTextField campoCor,
                               JTextField campoQuantidade, JTextField campoPreco) {
        try (Connection conexao = ConexaoBanco.getConnection()) {
            String sql = "SELECT * FROM produtos WHERE id = ?";
            PreparedStatement pstmt = conexao.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(idProduto));

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                campoNome.setText(rs.getString("nome"));
                campoCor.setText(rs.getString("cor"));
                campoQuantidade.setText(String.valueOf(rs.getInt("quantidade")));
                campoPreco.setText(String.valueOf(rs.getDouble("valor")));
            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado.");
            }
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao buscar o produto: " + ex.getMessage());
        }
    }

    private void editarProduto(String idProduto, String nome, String cor,
                               String quantidade, String preco) {
        try (Connection conexao = ConexaoBanco.getConnection()) {
            String sql = "UPDATE produtos SET nome = ?, cor = ?, quantidade = ?, valor = ? WHERE id = ?";
            PreparedStatement pstmt = conexao.prepareStatement(sql);
            pstmt.setString(1, nome);
            pstmt.setString(2, cor);
            pstmt.setInt(3, Integer.parseInt(quantidade));
            pstmt.setDouble(4, Double.parseDouble(preco));
            pstmt.setInt(5, Integer.parseInt(idProduto));

            int linhasAfetadas = pstmt.executeUpdate();
            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Produto editado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Produto não encontrado.");
            }
        } catch (SQLException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao editar o produto: " + ex.getMessage());
        }
    }


}
