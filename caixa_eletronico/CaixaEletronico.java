package caixa_eletronico;

import java.util.ArrayList;

public class CaixaEletronico implements ICaixaEletronico{
	
	private int[][] cedulas = { // Matriz 6x2 = Coluna 0 (valor das cédulas) e Coluna 1 (quantidade de cédulas)    
        {100, 100},
        {50, 200},
        {20, 300},
        {10, 350},
        {5, 450},
        {2, 500}
    };

    private int cotaMinima = 0; // Inicialmente a cota começa em 0
    
    private ArrayList<String> extrato = new ArrayList<>(); // Guarda o histórico das operações no extrato do caixa

    private int getTotal() { // Calcula o valor total no caixa
        int total = 0;
        for (int[] coluna : cedulas) {
            total += coluna[0] * coluna[1];
        }
        return total;
    }
    
    
    /* EXTRATO */
    public String getExtrato() {
        if (extrato.isEmpty()) { 
            return "Nenhuma operação realizada.";
        }

        StringBuilder resposta = new StringBuilder(); // Montando o extrato linha por linha

        for (String e : extrato) { // Percorre o extrato e adiciona cada operação
            resposta.append("• ").append(e).append("\n");
        }

        resposta.append("\n-----------------------------------------------\n\n");
        resposta.append(String.format("Saldo final: R$ %,d\n", getTotal()));
        
        if (cotaMinima > 0) { // Se for digitado a cota miníma
            resposta.append(String.format("Cota mínima: R$ %,d\n", cotaMinima));
        }
        
        return resposta.toString();
    }

    
    /* VALOR TOTAL */
    @Override
    public String pegaValorTotalDisponivel() { // Exibir o valor
        return "Valor Total Disponível:\nR$ " + getTotal();
    }

    
    /* REPOSIÇÃO */
    @Override
    public String reposicaoCedulas(Integer cedula, Integer quantidade) {
        if (cedula == null || quantidade == null) { // Verifica se os dados recebidos são nulos
            return "Valores inválidos!";
        }

        for (int i = 0; i < cedulas.length; i++) { // Percorre a matriz de cédulas
            if (cedulas[i][0] == cedula) { // Verifica se o valor da cédula digitada é igual ao da posição atual

                cedulas[i][1] += quantidade; // Soma a quantidade digitada ao estoque atual dessa cédula

                String texto = (quantidade == 1) ? "nota" : "notas"; // Define o texto no singular ou plural

                // Registra no extrato
                extrato.add("Reposição: " + quantidade + " " + texto + " de R$ " + cedula +
                            " | Saldo restante: R$ " + getTotal());

                return "Reposição realizada:\n" + quantidade + " " + texto + " de R$ " + cedula;
            }
        }

        return "Cédula inválida!";
    }

    
    /* SAQUE */
    @Override
    public String sacar(Integer valor) {
        if (getTotal() < valor) { // Não tem saldo suficiente para fazer o saque
            return "Saldo insuficiente!";
        }

        int restante = valor; // Quando ainda falta para sacar
        int[] usadas = new int[6]; // Quantas notas de cada tipo foram usadas
        int totalUsadas = 0; // Totas de notas usadas
        boolean ultrapassouLimite = false; // Indica se o saque ultrapassou o limite de notas

        for (int i = 0; i < cedulas.length; i++) { // Percorre todos os tipos de cédulas

            int nota = cedulas[i][0]; // valor da cédula
            int disponivel = cedulas[i][1]; // quantas cédulas tem no caixa

            int qtd = Math.min(restante / nota, disponivel); // Verifica o menor valor
            
            if (totalUsadas + qtd > 30) { // O caixa não pode entrgar mais de 30 notas por vez
                qtd = 30 - totalUsadas;
                ultrapassouLimite = true;
            }
            
            if (restante != 0) {
            	// Caso ultrapasse o limite de notas
                if (ultrapassouLimite) {
                    return "Saque não realizado! Limite de 30 notas ultrapassado.";
                }
            }

            usadas[i] = qtd; // Guarda quantas notas foram usadas
            restante -= qtd * nota; // Diminui o valor restante
            totalUsadas += qtd; // Soma no total das notas
        }

        if (restante != 0) { // Não tem notas suficientes e cancela o saque
            return "Saque não realizado por falta de cédulas";
        }

        for (int i = 0; i < cedulas.length; i++) { // Atualiza o caixa, tirando as cédulas usadas para o saque
            cedulas[i][1] -= usadas[i];
        }
        
        extrato.add("Saque: R$ " + valor + " | Saldo restante: R$ " + getTotal()); // Registra no extrato

        if (getTotal() <= cotaMinima) { // Veririca se o caixa ficou vazio
            return "Caixa Vazio: Chame o Operador";
        }

        String resposta = "Saque realizado:\n";

        for (int i = 0; i < cedulas.length; i++) { // Mostra quantas notas de cada valor foram entregues
            if (usadas[i] > 0) {
                String texto = (usadas[i] == 1) ? "nota" : "notas"; // Define se a palavra será singular ou plural, dependendo da quantidade
                resposta += usadas[i] + " " + texto + " de R$ " + cedulas[i][0] + "\n";
            }
        }

        return resposta;
    }

    
    /* RELATÓRIO */
    @Override
    public String pegaRelatorioCedulas() {
        StringBuilder resposta = new StringBuilder(); // Cria um objeto para montar a tabela linha por linha

        // Adiciona o cabeçalho da tabela
        resposta.append(String.format("%-10s %-10s\n", "CÉDULA", "QUANTIDADE")); // %-10s = coloca o texto alinhado à esquerda com largura de 10 caracteres
        resposta.append("-----------------------\n");

        for (int[] coluna : cedulas) { // Percorre a matriz de cédulas
            resposta.append(String.format("%-10s %-10s\n", "R$ " + coluna[0], coluna[1])); // Formata cada linha da tabela
        }

        return resposta.toString(); // Converte para String e retorna o resultado final
    }

    
    /* COTA MINÍMA */
    @Override
    public String armazenaCotaMinima(Integer minimo) {
        if (minimo == null || minimo < 0) { // Verifica se o valor é nulo ou negativo
            return "Valor inválido!";
        }

        cotaMinima = minimo; // Armazena o valor da cota mínima na variável da classe

        return "Cota mínima definida: \nR$ " + minimo;
    }

    
    /* JANELA GUI */
    public static void main(String arg[]){
        GUI janela = new GUI(CaixaEletronico.class);
        janela.show();
    }
}
