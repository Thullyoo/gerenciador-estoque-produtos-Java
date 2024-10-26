import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TelaRemoverProduto extends JFrame {

    public TelaRemoverProduto() {
        setTitle("Remover Produto");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBackground(Color.DARK_GRAY);
        painelPrincipal.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel labelId = criarLabel("ID do Produto:");
        gbc.gridx = 0; gbc.gridy = 0;
        painelPrincipal.add(labelId, gbc);

        JTextField campoId = criarCampoTexto();
        gbc.gridx = 1; gbc.gridy = 0;
        painelPrincipal.add(campoId, gbc);

        JButton botaoRemover = criarBotao("Remover Produto");
        gbc.gridx = 1; gbc.gridy = 1;
        painelPrincipal.add(botaoRemover, gbc);

        JButton botaoVoltar = criarBotao("Voltar");
        gbc.gridx = 0; gbc.gridy = 1;
        painelPrincipal.add(botaoVoltar, gbc);

        add(painelPrincipal);

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
        botao.setBackground(new Color(220, 20, 60));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setPreferredSize(new Dimension(180, 40));
        return botao;
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
