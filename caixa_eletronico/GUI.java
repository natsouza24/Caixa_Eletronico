package caixa_eletronico;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
    private ICaixaEletronico caixa;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI janela = new GUI(CaixaEletronico.class);
					janela.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GUI() {
		setTitle("Caixa Eletrônico");
		setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/images/icone-java.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(100, 100, 280, 380);
		setResizable(false);
		setLocationRelativeTo(null);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		/* CLIENTE */
		JLabel lblNewLabel = new JLabel("Modulo do Cliente:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(24, 20, 137, 12);
		contentPane.add(lblNewLabel);
		
		JButton btnSaque = new JButton("Efetuar Saque");
		btnSaque.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSaque.setBounds(24, 41, 216, 26);
		contentPane.add(btnSaque);
		
		
		/* ADMINISTRADOR */
		JLabel lblModuloDoAdministrador = new JLabel("Modulo do Administrador:");
		lblModuloDoAdministrador.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblModuloDoAdministrador.setBounds(24, 94, 194, 12);
		contentPane.add(lblModuloDoAdministrador);
		
		JButton btnRelatorio = new JButton("Relatório de Cédulas");
		btnRelatorio.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnRelatorio.setBounds(24, 116, 216, 26);
		contentPane.add(btnRelatorio);
		
		JButton btnReposicao = new JButton("Reposição de Cédulas");
		btnReposicao.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnReposicao.setBounds(24, 180, 216, 26);
		contentPane.add(btnReposicao);
		
		JButton btnValorTotal = new JButton("Valor Total Disponível");
		btnValorTotal.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnValorTotal.setBounds(24, 148, 216, 26);
		contentPane.add(btnValorTotal);
		
		JButton btnCotaMinima = new JButton("Cota Miníma");
		btnCotaMinima.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCotaMinima.setBounds(24, 214, 216, 26);
		contentPane.add(btnCotaMinima);
		
		
		/* AMBOS */
		JLabel lblModuloDeAmbos = new JLabel("Modulo de Ambos:");
		lblModuloDeAmbos.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblModuloDeAmbos.setBounds(24, 272, 194, 12);
		contentPane.add(lblModuloDeAmbos);
		
		JButton btnExtrato = new JButton("Sair");
		btnExtrato.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnExtrato.setBounds(24, 294, 216, 26);
		contentPane.add(btnExtrato);
		
		
		//UIManager.put("Label.font", new Font("Tahoma", Font.PLAIN, 12));
		
		
		/* JANELAS */
		
		/* SAQUE */		
		btnSaque.addActionListener(e -> {
		    try {
		        String valor = JOptionPane.showInputDialog(null,
		                "Digite o valor do saque:", "Saque", JOptionPane.QUESTION_MESSAGE);

		        if (valor == null) return;

		        if (valor.trim().isEmpty()) {
		            JOptionPane.showMessageDialog(null,
		                    "Digite um valor válido!", "Erro", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        int saque = Integer.parseInt(valor);

		        if (saque <= 0) {
		            JOptionPane.showMessageDialog(null,
		                    "O valor deve ser maior que zero!", "Erro", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        JOptionPane.showMessageDialog(null,
		                caixa.sacar(saque), "Saque", JOptionPane.INFORMATION_MESSAGE);

		    } catch (NumberFormatException ex) {
		        JOptionPane.showMessageDialog(null,
		                "Digite apenas números!", "Erro", JOptionPane.ERROR_MESSAGE);
		    }
		});
		
		
		/* RELATÓRIO */
        btnRelatorio.addActionListener(e -> {
        	JTextArea area = new JTextArea(caixa.pegaRelatorioCedulas());
        	area.setFont(new Font("Consolas", Font.PLAIN, 13));
        	area.setEditable(false);

        	// REMOVE FUNDO
        	area.setOpaque(false);
        	area.setBackground(null);
        	area.setBorder(null);


        	JOptionPane.showMessageDialog(null, area, "Relatório de Cédulas", JOptionPane.INFORMATION_MESSAGE);

        });
        
        
        
        /* VALOR TOTAL */
        btnValorTotal.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,
                caixa.pegaValorTotalDisponivel(), "Caixa Eletrônico", JOptionPane.INFORMATION_MESSAGE);
        });
        
        
        /* REPOSIÇÃO */
        btnReposicao.addActionListener(e -> {
            try {
                String ced = JOptionPane.showInputDialog(null,
                        "Digite a cédula (2, 5, 10, 20, 50 ou 100 reais):",
                        "Reposição", JOptionPane.QUESTION_MESSAGE);

                if (ced == null || ced.trim().isEmpty())
                    throw new Exception("Digite um valor válido!");

                int cedula = Integer.parseInt(ced);

                if (cedula != 2 && cedula != 5 && cedula != 10 &&
                    cedula != 20 && cedula != 50 && cedula != 100)
                    throw new Exception("Cédula inválida!");

                String qtd = JOptionPane.showInputDialog(null,
                        "Digite a quantidade:", "Reposição", JOptionPane.QUESTION_MESSAGE);

                if (qtd == null || qtd.trim().isEmpty())
                    throw new Exception("Digite um valor válido!");

                int quantidade = Integer.parseInt(qtd);

                if (quantidade <= 0)
                    throw new Exception("A quantidade deve ser maior que zero!");

                JOptionPane.showMessageDialog(null,
                        caixa.reposicaoCedulas(cedula, quantidade),
                        "Reposição", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Digite apenas números!", "Erro", JOptionPane.ERROR_MESSAGE);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null,
                        ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        
        /* COTA MINÍMA */
        btnCotaMinima.addActionListener(e -> {
            try {
                String cota = JOptionPane.showInputDialog(null,
                        "Digite a cota mínima:", "Cota Mínima", JOptionPane.QUESTION_MESSAGE);

                if (cota == null) return;

                if (cota.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(null,
                            "Digite um valor válido!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int valorCota = Integer.parseInt(cota);

                if (valorCota < 0) {
                    JOptionPane.showMessageDialog(null,
                            "A cota mínima não pode ser negativa!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JOptionPane.showMessageDialog(null,
                        caixa.armazenaCotaMinima(valorCota),
                        "Cota Mínima", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Digite apenas números!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        
        /* EXTRATO */
        btnExtrato.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,
                ((CaixaEletronico) caixa).getExtrato(), "Extrato", JOptionPane.INFORMATION_MESSAGE);

            System.exit(0); // Fechar todas as janelas
        });	
	}
	
	
    // CONSTRUTOR 
    public GUI(Class<?> clazz) {
        this();
        try {
            Object obj = clazz.getDeclaredConstructor().newInstance();
            caixa = (ICaixaEletronico) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
