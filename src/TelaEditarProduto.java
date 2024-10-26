import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
        setSize(800, 600);
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

        JLabel labelNome = criarLabel("Nome:");
        gbc.gridx = 0; gbc.gridy = 1;
        painelPrincipal.add(labelNome, gbc);

        JTextField campoNome = criarCampoTexto();
        gbc.gridx = 1; gbc.gridy = 1;
        painelPrincipal.add(campoNome, gbc);

        JLabel labelCor = criarLabel("Cor:");
        gbc.gridx = 0; gbc.gridy = 2;
        painelPrincipal.add(labelCor, gbc);

        JTextField campoCor = criarCampoTexto();
        gbc.gridx = 1; gbc.gridy = 2;
        painelPrincipal.add(campoCor, gbc);

        JLabel labelQuantidade = criarLabel("Quantidade:");
        gbc.gridx = 0; gbc.gridy = 3;
        painelPrincipal.add(labelQuantidade, gbc);

        JTextField campoQuantidade = criarCampoTexto();
        gbc.gridx = 1; gbc.gridy = 3;
        painelPrincipal.add(campoQuantidade, gbc);

        JLabel labelPreco = criarLabel("Preço:");
        gbc.gridx = 0; gbc.gridy = 4;
        painelPrincipal.add(labelPreco, gbc);

        JTextField campoPreco = criarCampoTexto();
        gbc.gridx = 1; gbc.gridy = 4;
        painelPrincipal.add(campoPreco, gbc);

        JButton botaoBuscar = criarBotao("Buscar Produto");
        gbc.gridx = 0; gbc.gridy = 5;
        painelPrincipal.add(botaoBuscar, gbc);

        JButton botaoEditar = criarBotao("Salvar Alterações");
        gbc.gridx = 1; gbc.gridy = 5;
        painelPrincipal.add(botaoEditar, gbc);

        JButton botaoVoltar = criarBotao("Voltar");
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        painelPrincipal.add(botaoVoltar, gbc);

        add(painelPrincipal);

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
        botao.setBackground(new Color(70, 130, 180));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setPreferredSize(new Dimension(180, 40));
        return botao;
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
