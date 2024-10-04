import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TelaRemoverProduto extends JFrame {

    public TelaRemoverProduto() {
        setTitle("Remover Produto");
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

        JButton botaoRemover = new JButton("Remover Produto");
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(botaoRemover, gbc);

        JButton botaoVoltar = new JButton("Voltar");
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(botaoVoltar, gbc);

        botaoRemover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String idProduto = campoId.getText();

                if (idProduto.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Por favor, insira o ID do produto.");
                    return;
                }

                int resposta = JOptionPane.showConfirmDialog(null,
                        "Você tem certeza que deseja remover o produto com ID: " + idProduto + "?",
                        "Confirmação de Remoção", JOptionPane.YES_NO_OPTION);

                if (resposta == JOptionPane.YES_OPTION) {
                    removerProduto(idProduto);
                    campoId.setText("");
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

    private void removerProduto(String idProduto) {
        try (Connection conexao = ConexaoBanco.getConnection()) {
            String sql = "DELETE FROM produtos WHERE id = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(idProduto));

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(null, "Produto removido com sucesso!");
            } else {
                JOptionPane.showMessageDialog(null, "Nenhum produto encontrado com o ID fornecido.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao remover o produto: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "ID inválido. Insira um número.");
        }
    }
}
