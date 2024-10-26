import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TelaListarProdutos extends JFrame {

    private JTable tabelaProdutos;
    private DefaultTableModel modeloTabela;
    private JTextField campoBusca;

    public TelaListarProdutos() {
        setTitle("Listar Produtos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBackground(Color.DARK_GRAY);
        painelPrincipal.setBorder(new EmptyBorder(15, 15, 15, 15));
        add(painelPrincipal);

        campoBusca = new JTextField(20);
        campoBusca.setToolTipText("Buscar pelo nome do produto...");
        campoBusca.setFont(new Font("Arial", Font.PLAIN, 16));
        campoBusca.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        JPanel painelBusca = new JPanel(new BorderLayout());
        painelBusca.add(campoBusca, BorderLayout.CENTER);
        painelBusca.setBorder(new EmptyBorder(10, 0, 10, 0));
        painelBusca.setBackground(Color.DARK_GRAY);

        painelPrincipal.add(painelBusca, BorderLayout.NORTH);

        modeloTabela = new DefaultTableModel();
        tabelaProdutos = new JTable(modeloTabela) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaProdutos.setFont(new Font("Arial", Font.PLAIN, 14));
        tabelaProdutos.setRowHeight(25);
        tabelaProdutos.setGridColor(Color.GRAY);
        tabelaProdutos.setBackground(Color.LIGHT_GRAY);
        tabelaProdutos.setForeground(Color.BLACK);

        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("Cor");
        modeloTabela.addColumn("Quantidade");
        modeloTabela.addColumn("Preço");

        carregarProdutos();

        JScrollPane scrollPane = new JScrollPane(tabelaProdutos);
        scrollPane.setBackground(Color.LIGHT_GRAY);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);

        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.setFont(new Font("Arial", Font.BOLD, 16));
        botaoVoltar.setBackground(new Color(70, 130, 180));
        botaoVoltar.setForeground(Color.WHITE);
        botaoVoltar.setFocusPainted(false);
        botaoVoltar.setPreferredSize(new Dimension(100, 40));
        botaoVoltar.addActionListener(e -> {
            new TelaPrincipal();
            dispose();
        });

        JPanel painelBotaoVoltar = new JPanel();
        painelBotaoVoltar.setBackground(Color.DARK_GRAY);
        painelBotaoVoltar.add(botaoVoltar);
        painelPrincipal.add(painelBotaoVoltar, BorderLayout.SOUTH);

        campoBusca.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarProdutos(campoBusca.getText());
            }
        });

        setVisible(true);
    }

    private void carregarProdutos() {
        try (Connection conexao = ConexaoBanco.getConnection()) {
            String sql = "SELECT * FROM produtos";
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            modeloTabela.setRowCount(0);

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cor = rs.getString("cor");
                int quantidade = rs.getInt("quantidade");
                double preco = rs.getDouble("valor");

                modeloTabela.addRow(new Object[]{id, nome, cor, quantidade, preco});
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao carregar os produtos: " + ex.getMessage());
        }
    }

    private void filtrarProdutos(String busca) {
        modeloTabela.setRowCount(0);

        try (Connection conexao = ConexaoBanco.getConnection()) {
            String sql = "SELECT * FROM produtos WHERE nome LIKE '%" + busca + "%'";
            Statement stmt = conexao.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cor = rs.getString("cor");
                int quantidade = rs.getInt("quantidade");
                double preco = rs.getDouble("valor");

                modeloTabela.addRow(new Object[]{id, nome, cor, quantidade, preco});
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao filtrar os produtos: " + ex.getMessage());
        }
    }
}
