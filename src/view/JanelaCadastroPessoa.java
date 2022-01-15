package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import bean.Pessoa;
import dao.PessoaDAO;
import helper.InputValidations;
import helper.TrataString;

public class JanelaCadastroPessoa extends JDialog {

	private static final long serialVersionUID = 1L;

	private JPanel panel;

	private JLabel lblNome;
	private JTextField fieldNome;

	private JRadioButton radioFisica, radioJurid;

	private JLabel lblCpfCnpj;
	private JTextField fieldCpfCnpj;

	private JLabel lblEmail;
	private JTextField fieldEmail;

	private JLabel lblTelefone;
	private JTextField fieldTelefone;

	private JButton btnCadastrar;


	public JanelaCadastroPessoa(String titulo) {
        super();
        this.setTitle( titulo );
        criarComponentes();
        ajustarPropriedadesJanela();
    }

	private void criarComponentes() {
	   panel = new JPanel(new GridBagLayout());

		lblNome = new JLabel("Nome");
		fieldNome = new JTextField(10);

		lblEmail = new JLabel("Email");
		fieldEmail = new JTextField(10);

		lblTelefone = new JLabel("Telefone");
      fieldTelefone = new JTextField(10);

		radioFisica = new JRadioButton("Pessoa Fisica");
		radioFisica.setSelected(true);
		radioFisica.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			   SwingUtilities.invokeLater( new Runnable() {
               public void run() {
                  lblCpfCnpj.setText( "CPF" );
               }
            } );
			}
		});

		radioJurid = new JRadioButton("Pessoa Juridica");
        radioJurid.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
			   SwingUtilities.invokeLater( new Runnable() {
               public void run() {
                  lblCpfCnpj.setText( "CNPJ" );
               }
            } );
			}
		});

		ButtonGroup group = new ButtonGroup();
      group.add(radioFisica);
      group.add(radioJurid);

      lblCpfCnpj = new JLabel("CPF ");
      fieldCpfCnpj = new JTextField(10);


      btnCadastrar = new JButton("Cadastrar");
      btnCadastrar.addActionListener(new ActionListener() {
         @Override
			public void actionPerformed(ActionEvent e) {
				cadastrarCliente();
			}
		});

      adicionarComponentes();
    }

    private void adicionarComponentes() {
      GridBagConstraints c = new GridBagConstraints();
      int linha = 0;
      int coluna = 0;
      c.gridheight = 1;
      c.gridwidth = 1;
      c.gridx = coluna;
      c.gridy = linha;

    	panel.add(lblNome, c);
    	coluna++;
    	c.gridx = coluna;
    	panel.add(fieldNome, c);

    	coluna = 0;
    	linha++;
    	c.gridy = linha;
    	c.gridx = coluna;
    	panel.add(lblEmail, c);
    	coluna++;
      c.gridx = coluna;
    	panel.add(fieldEmail, c);

    	coluna = 0;
      linha++;
      c.gridy = linha;
      c.gridx = coluna;
    	panel.add(lblTelefone, c);
    	coluna++;
      c.gridx = coluna;
    	panel.add(fieldTelefone, c);

    	coluna = 0;
      linha++;
      c.gridy = linha;
      c.gridx = coluna;
    	panel.add(radioFisica, c);
    	coluna++;
      c.gridx = coluna;
    	panel.add(radioJurid, c);

    	coluna = 0;
      linha++;
      c.gridy = linha;
      c.gridx = coluna;
    	panel.add(lblCpfCnpj, c);
    	coluna++;
      c.gridx = coluna;
    	panel.add(fieldCpfCnpj, c);

    	coluna = 0;
      linha++;
      c.gridy = linha;
      c.gridx = coluna;
      c.gridwidth = 2;
      c.fill = GridBagConstraints.HORIZONTAL;
      panel.add(btnCadastrar, c);

      add(panel);
    }

    private void ajustarPropriedadesJanela() {
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    private Pessoa validateData() throws Exception {
       String nome      = TrataString.trim( fieldNome.getText() );
       String email     = TrataString.trim( fieldEmail.getText() );
       String telefone  = TrataString.trim( fieldTelefone.getText() );
       if (nome.isEmpty()) {
          throw new Exception( "Campo nome inválido!" );
       } else if (email.isEmpty()) {
          throw new Exception( "Campo email inválido!" );
       } else if (telefone.isEmpty()) {
          throw new Exception( "Campo telefone inválido!" );
       }

       boolean isPessoaFisica = radioFisica.isSelected();
       String doc = fieldCpfCnpj.getText();
       doc = isPessoaFisica ? InputValidations.isCpfValido( doc ) : InputValidations.isCnpjValido( doc );
       if (doc.isEmpty()) {
          String msg = isPessoaFisica ? "CPF" : "CNPJ";
          throw new Exception( "Campo " + msg + " inválido!" );
       }
       return new Pessoa( email, nome, telefone, doc );
    }

    private void cadastrarCliente() {
      boolean gravou = false;
      Pessoa p = null;
      try {
        p = validateData();
      } catch (Exception e) {
         JOptionPane.showMessageDialog( null, e.getMessage(), "", JOptionPane.WARNING_MESSAGE );
      }

      if (p != null) {
         PessoaDAO dao = new PessoaDAO();
         p = dao.save( p );

         if (p != null) {
            JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!");
            gravou = true;
         } else {
            JOptionPane.showMessageDialog( null, "Falha ao cadastrar!", "Erro", ERROR );
         }
      }
    	if (gravou == true) {
    	   limpaCampos();
    	}
    }

    private void limpaCampos() {
       fieldNome.setText("");
       fieldEmail.setText("");
       fieldTelefone.setText("");
       fieldCpfCnpj.setText("");
    }

}
