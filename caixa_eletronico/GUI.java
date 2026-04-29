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
		// Frame
		setTitle("Caixa Eletrônico");
		setIconImage(Toolkit.getDefaultToolkit().getImage(GUI.class.getResource("/images/icone-java.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Área
		setBounds(100, 100, 280, 380);
		setResizable(false);
		setLocationRelativeTo(null);
		
		// Panel
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
		
				
		
		/* JANELAS */
		
		/* SAQUE */		
		btnSaque.addActionListener(e -> {
		    try {
		    	// Janela para digitar o valor
		        String valor = JOptionPane.showInputDialog(null,
		                "Digite o valor do saque:", "Saque", JOptionPane.QUESTION_MESSAGE);

		        if (valor == null) return;

		        if (valor.trim().isEmpty()) { // Se campo estiver vazio
		            JOptionPane.showMessageDialog(null,
		                    "Digite um valor válido!", "Erro", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        int saque = Integer.parseInt(valor);

		        if (saque <= 0) { // Se digitar um valor menor que 0
		            JOptionPane.showMessageDialog(null,
		                    "O valor deve ser maior que zero!", "Erro", JOptionPane.ERROR_MESSAGE);
		            return;
		        }

		        // Janela do resultado do saque
		        JOptionPane.showMessageDialog(null,
		                caixa.sacar(saque), "Saque", JOptionPane.INFORMATION_MESSAGE);

		    } catch (NumberFormatException ex) { // Se digitar letras ou símbolos
		        JOptionPane.showMessageDialog(null,
		                "Digite apenas números!", "Erro", JOptionPane.ERROR_MESSAGE);
		    }
		});
		
		
		/* RELATÓRIO */
        btnRelatorio.addActionListener(e -> {
        	// Área para exibir o relatório
        	JTextArea area = new JTextArea(caixa.pegaRelatorioCedulas());
        	area.setFont(new Font("Consolas", Font.BOLD, 13));
        	
    		area.setEditable(false);
        	area.setOpaque(false);
        	area.setBackground(null);
        	area.setBorder(null);

        	// Janela
        	JOptionPane.showMessageDialog(null, area, "Relatório de Cédulas", JOptionPane.INFORMATION_MESSAGE);
        });
        
        
        /* VALOR TOTAL */
        btnValorTotal.addActionListener(e -> { // Exibe o valor total disponível no caixa
            JOptionPane.showMessageDialog(null,
                caixa.pegaValorTotalDisponivel(), "Caixa Eletrônico", JOptionPane.INFORMATION_MESSAGE);
        });
        
        
        /* REPOSIÇÃO */
        btnReposicao.addActionListener(e -> {
            try {
            	// Cédula
                String ced = JOptionPane.showInputDialog(null,
                        "Digite a cédula (2, 5, 10, 20, 50 ou 100 reais):",
                        "Reposição", JOptionPane.QUESTION_MESSAGE);

                if (ced == null || ced.trim().isEmpty()) // Se o campo cédula estiver vazio
                    throw new Exception("Digite um valor válido!");

                int cedula = Integer.parseInt(ced);

                if (cedula != 2 && cedula != 5 && cedula != 10 &&
                    cedula != 20 && cedula != 50 && cedula != 100) // Se colocar cédula que não existe
                    throw new Exception("Cédula inválida!"); 

                // Quantidade
                String qtd = JOptionPane.showInputDialog(null,
                        "Digite a quantidade:", "Reposição", JOptionPane.QUESTION_MESSAGE);

                if (qtd == null || qtd.trim().isEmpty()) // Se o campo quantidade estiver vazio
                    throw new Exception("Digite um valor válido!"); 

                int quantidade = Integer.parseInt(qtd);

                if (quantidade <= 0) // Se colocar quantidade menor que 0
                    throw new Exception("A quantidade deve ser maior que zero!"); 
                
                
                // Janela da Reposição
                JOptionPane.showMessageDialog(null,
                        caixa.reposicaoCedulas(cedula, quantidade),
                        "Reposição", JOptionPane.INFORMATION_MESSAGE); 

            } catch (NumberFormatException ex) { // Se digitar letras ou símbolos
                JOptionPane.showMessageDialog(null,
                        "Digite apenas números!", "Erro", JOptionPane.ERROR_MESSAGE); 

            } catch (Exception ex) { // Captura e exibe todos os erros apresentados
                JOptionPane.showMessageDialog(null,
                        ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE); 
            }
        });
        
        
        /* COTA MINÍMA */
        btnCotaMinima.addActionListener(e -> {
            try {
            	// Janela para digitar a cota
                String cota = JOptionPane.showInputDialog(null,
                        "Digite a cota mínima:", "Cota Mínima", JOptionPane.QUESTION_MESSAGE);

                if (cota == null) return;

                if (cota.trim().isEmpty()) { // Se o campo estiver vazio
                    JOptionPane.showMessageDialog(null,
                            "Digite um valor válido!", "Erro", JOptionPane.ERROR_MESSAGE); 
                    return;
                }

                int valorCota = Integer.parseInt(cota);

                if (valorCota < 0) { // Ser digitar valor menor que 0
                    JOptionPane.showMessageDialog(null,
                            "A cota mínima não pode ser negativa!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Janela com o resultado digitado
                JOptionPane.showMessageDialog(null,
                        caixa.armazenaCotaMinima(valorCota),
                        "Cota Mínima", JOptionPane.INFORMATION_MESSAGE);

            } catch (NumberFormatException ex) { // Se digitar letras ou símbolos
                JOptionPane.showMessageDialog(null,
                        "Digite apenas números!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        
        /* EXTRATO */
        btnExtrato.addActionListener(e -> { 
        	// Exibi o histórico de operações
            JOptionPane.showMessageDialog(null,
                ((CaixaEletronico) caixa).getExtrato(), "Extrato", JOptionPane.INFORMATION_MESSAGE); 

            System.exit(0); // Fechar todas as janelas
        });	
	}
	
	
    // Construtor que inicia o caixa em tempo de execução
	public GUI(Class<?> clazz) { // Representa qualquer classe em execução
        this();
        try {
            Object obj = clazz.getDeclaredConstructor().newInstance(); // Criando um objeto no momento da execução
            caixa = (ICaixaEletronico) obj; // Adiciona uma ligação com a interface ICaixaEletronico
        } catch (Exception e) {
            e.printStackTrace(); // Para caso tiver erro na execução
        }
    }

}
