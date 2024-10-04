import javax.swing.*;
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
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        campoBusca = new JTextField();
        campoBusca.setToolTipText("Buscar pelo nome do produto...");
        panel.add(campoBusca, BorderLayout.NORTH);


        modeloTabela = new DefaultTableModel();
        tabelaProdutos = new JTable(modeloTabela) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("Cor");
        modeloTabela.addColumn("Quantidade");
        modeloTabela.addColumn("Preço");

        carregarProdutos();

        JScrollPane scrollPane = new JScrollPane(tabelaProdutos);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton botaoVoltar = new JButton("Voltar");
        botaoVoltar.addActionListener(e -> {
            new TelaPrincipal();
            dispose();
        });

        panel.add(botaoVoltar, BorderLayout.SOUTH);

        campoBusca.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filtrarProdutos(campoBusca.getText());
            }
        });

        add(panel);

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
