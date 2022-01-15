package view.exibir;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import bean.Pessoa;
import dao.PessoaDAO;


public class JanelaExibirPessoas extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel painelFundo;
	private JPanel painelBotoes;

	private JScrollPane barraRolagem;

	private List<Pessoa> list = new ArrayList<Pessoa>();

	private JTable tabela = null;
	private PessoaTableModel modelo;
	private JButton btnExcluir;
	private JButton btnEditar;


	public JanelaExibirPessoas(String titulo) {
        super();
        this.setTitle( titulo );
        criaJanela();
        criaJTable();
        ajustarPropriedadesJanela();
    }

	public void criaJanela() {
      tabela = new JTable(modelo);
      tabela.setFillsViewportHeight(true);

      btnExcluir = new JButton("Excluir");
      btnEditar = new JButton("Editar");

      painelBotoes = new JPanel();
      barraRolagem = new JScrollPane(tabela);

      painelFundo = new JPanel();
      painelFundo.setLayout(new BorderLayout());
      painelFundo.add(BorderLayout.CENTER, barraRolagem);
      painelFundo.add(tabela.getTableHeader(), BorderLayout.PAGE_START);
      painelFundo.add(tabela, BorderLayout.CENTER);

      painelBotoes.add(btnEditar);
      painelBotoes.add(btnExcluir);

      painelFundo.add(BorderLayout.SOUTH, painelBotoes);

      getContentPane().add(painelFundo);

      btnEditar.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed( ActionEvent e ) {
            int linhaSelecionada = -1;
            linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada >= 0) {
               int opcao = JOptionPane.showConfirmDialog( null, "Deseja salvar as alterações?", "", JOptionPane.YES_NO_OPTION );

               if (opcao == 0) {
                  Pessoa p = modelo.getPessoa( linhaSelecionada );
                  try {
                     validatePessoa(p);
                     PessoaDAO dao = new PessoaDAO();
                     p = dao.save( p );
                     if (p == null) {
                        JOptionPane.showMessageDialog(null, "Falha ao salvar alteração.");
                     }
                  } catch (Exception ex) {
                     JOptionPane.showMessageDialog(null, ex.getMessage());
                  }

               }

            } else {
                JOptionPane.showMessageDialog(null, "É necessário selecionar uma linha.");
            }
         }
      });


      btnExcluir.addActionListener(new ActionListener() {

         @Override
         public void actionPerformed( ActionEvent e ) {
            int linhaSelecionada = -1;
            linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada >= 0) {
               int opcao = JOptionPane.showConfirmDialog( null, "Confirma a exclusão?", "", JOptionPane.YES_NO_OPTION );

               if (opcao == 0) {
                  int idPessoa = (int) tabela.getValueAt(linhaSelecionada, 0);
                  PessoaDAO dao = new PessoaDAO();
                  dao.remove(idPessoa);
                  modelo.removePessoa(linhaSelecionada);
               }

            } else {
                JOptionPane.showMessageDialog(null, "É necessário selecionar uma linha.");
            }
         }
      });
   }

   private void criaJTable() {
      PessoaDAO dao = new PessoaDAO();
      list = dao.findAll();
      modelo = new PessoaTableModel(list);
      tabela.setModel(modelo);
      hasInfoToShow();
   }

    private void ajustarPropriedadesJanela() {
        setVisible(true);
        setSize(500, 320);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private void hasInfoToShow() {
       if (list == null || list.isEmpty()) {
          JOptionPane.showMessageDialog(null, "Nenhuma pessoa cadastrada ainda!");
          JDialog dialog = this;
          SwingUtilities.invokeLater( new Runnable() {
            public void run() {
               dialog.dispose();
            }
         } );
       }
    }

    private void validatePessoa(final Pessoa p) throws Exception {
       if (p.getNome().isEmpty()) {
          throw new Exception( "Campo nome inválido!" );
       } else if (p.getEmail().isEmpty()) {
          throw new Exception( "Campo email inválido!" );
       } else if (p.getTelefone().isEmpty()) {
          throw new Exception( "Campo telefone inválido!" );
       } else if (p.getCpfCnpj().isEmpty()) {
          throw new Exception( "Campo CPF/CNPJ inválido!" );
       }
    }



}

