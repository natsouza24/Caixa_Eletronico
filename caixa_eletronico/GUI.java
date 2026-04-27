package caixa_eletronico;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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
		
		
		/* EVENTOS */
		btnSaque.addActionListener(e -> {
		    String valor = JOptionPane.showInputDialog("Digite o valor do saque:");

		    if (valor != null) {
		        JOptionPane.showMessageDialog(null,
		            caixa.sacar(Integer.parseInt(valor)));
		    }
		});
		
        btnRelatorio.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,
             caixa.pegaRelatorioCedulas());
        });
        
        btnValorTotal.addActionListener(e -> {
            JOptionPane.showMessageDialog(null,
                caixa.pegaValorTotalDisponivel());
        });
        

        btnReposicao.addActionListener(e -> {
            try {
                String ced = JOptionPane.showInputDialog("Digite a cédula (2, 5, 10, 20, 50 ou 100 reais):");

                if (ced == null) return;

                int cedula = Integer.parseInt(ced);

                // VALIDA ANTES
                if (cedula != 2 && cedula != 5 && cedula != 10 &&
                    cedula != 20 && cedula != 50 && cedula != 100) {

                    JOptionPane.showMessageDialog(null, "Cédula inválida!");
                    return; // PARA AQUI
                }

                String qtd = JOptionPane.showInputDialog("Digite a quantidade:");

                if (qtd == null) return;

                int quantidade = Integer.parseInt(qtd);

                JOptionPane.showMessageDialog(null,
                    caixa.reposicaoCedulas(cedula, quantidade)
                );

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Digite apenas números!");
            }
        });
        
        
        btnCotaMinima.addActionListener(e -> {
            try {
                String c = JOptionPane.showInputDialog("Digite a cota mínima:");
                if (c != null) {
                    JOptionPane.showMessageDialog(null,
                            caixa.armazenaCotaMinima(Integer.parseInt(c)));
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Digite apenas números!");
            }
        });

        btnExtrato.addActionListener(e -> {

            JOptionPane.showMessageDialog(null,
                ((CaixaEletronico) caixa).getExtrato()
            );

            System.exit(0);
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