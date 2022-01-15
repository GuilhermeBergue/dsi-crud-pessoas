package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import view.exibir.JanelaExibirPessoas;

public class JanelaPrincipal extends JFrame{

	private static final long serialVersionUID = 1L;

	private JPanel panel = new JPanel();

	private JButton btnInserePessoa, btnExibePessoas;


	public JanelaPrincipal(String titulo) {
        super(titulo);
        criarComponentes();
        ajustarPropriedadesJanela();
    }

	private void criarComponentes() {

		btnInserePessoa = new JButton("Inserir Pessoa");
		btnInserePessoa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				abrirJanelaCliente();
			}
		});

		btnExibePessoas = new JButton("Exibir Pessoas");
		btnExibePessoas.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					abrirJanelaExibirPessoas();
				}
		});

      adicionarComponentes();
    }

    private void adicionarComponentes() {
    	panel.add(btnInserePessoa);
    	panel.add(btnExibePessoas);

      add(panel);
    }

    private void ajustarPropriedadesJanela() {
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void abrirJanelaCliente() {
    	new JanelaCadastroPessoa("Cadastrar pessoa");
    }

    public void abrirJanelaExibirPessoas() {
       new JanelaExibirPessoas("Exibir pessoas");
    }



}
