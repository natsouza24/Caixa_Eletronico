package caixa_eletronico;

import java.util.ArrayList;

public class CaixaEletronico implements ICaixaEletronico{
	
	private int[][] cedulas = { // Matriz 6x2 = Coluna 0 (valor das células) e Coluna 1 (quantidade de cédulas)    
        {100, 100},
        {50, 200},
        {20, 300},
        {10, 350},
        {5, 450},
        {2, 500}
    };

    private int cotaMinima = 0;
    
    private ArrayList<String> extrato = new ArrayList<>();

    private int getTotal() {
        int total = 0;
        for (int[] coluna : cedulas) {
            total += coluna[0] * coluna[1];
        }
        return total;
    }
    
    
    /* EXTRATO */
    public String getExtrato() {
        if (extrato.isEmpty()) {
            return "Nenhum saque ou reposição foi realizado.";
        }

        String resposta = "===== EXTRATO =====\n";

        for (String e : extrato) {
            resposta += e + "\n";
        }

        resposta += "===================\n";
        resposta += "Saldo final: R$ " + getTotal();

        return resposta;
    }

    
    /* VALOR TOTAL */
    @Override
    public String pegaValorTotalDisponivel() {
        return "Valor Total Disponível:\nR$ " + getTotal();
    }

    
    /* REPOSIÇÃO */
    @Override
    public String reposicaoCedulas(Integer cedula, Integer quantidade) {
        if (cedula == null || quantidade == null) {
            return "Valores inválidos!";
        }

        if (quantidade <= 0) {
            return "Quantidade inválida!";
        }

        for (int i = 0; i < cedulas.length; i++) {
            if (cedulas[i][0] == cedula) {

                cedulas[i][1] += quantidade;

                // Registra no extrato
                extrato.add("Reposição: +" + quantidade + " notas de R$ " + cedula + " | Saldo restante: R$ " + getTotal());

                return "Reposição realizada: \n" + quantidade + " notas de R$ " + cedula;
            }
        }

        return "Cédula inválida!";
    }

    
    /* SAQUE */
    @Override
    public String sacar(Integer valor) {
        if (getTotal() < valor) {
            return "Saldo insuficiente!";
        }

        int restante = valor;
        int[] usadas = new int[6];
        int totalCedulas = 0;

        for (int i = 0; i < cedulas.length; i++) {

            int nota = cedulas[i][0];
            int disponivel = cedulas[i][1];

            int qtd = Math.min(restante / nota, disponivel);

            if (totalCedulas + qtd > 30) {
                qtd = 30 - totalCedulas;
            }

            usadas[i] = qtd;
            restante -= qtd * nota;
            totalCedulas += qtd;
        }

        if (restante != 0) {
            return "Saque não realizado por falta de cédulas";
        }

        // Atualiza o caixa
        for (int i = 0; i < cedulas.length; i++) {
            cedulas[i][1] -= usadas[i];
        }
        
        // Registra no extrato
        extrato.add("Saque: R$ " + valor + " | Saldo restante: R$ " + getTotal());

        // Veriricar após o saque
        if (getTotal() <= cotaMinima) {
            return "Caixa Vazio: Chame o Operador";
        }

        String resposta = "Saque realizado:\n";

        for (int i = 0; i < cedulas.length; i++) {
            if (usadas[i] > 0) {
                resposta += usadas[i] + " notas de R$ " + cedulas[i][0] + "\n";
            }
        }

        return resposta;
    }

    
    /* RELATÓRIO */
    @Override
    public String pegaRelatorioCedulas() {
        StringBuilder resposta = new StringBuilder();

        resposta.append(String.format("%-10s %-10s\n", "CÉDULA", "QUANTIDADE"));
        resposta.append("-----------------------\n");

        for (int[] coluna : cedulas) {
            resposta.append(String.format("%-10s %-10d\n", "R$ " + coluna[0], coluna[1]));
        }

        return resposta.toString();
    }

    
    /* COTA MINÍMA */
    @Override
    public String armazenaCotaMinima(Integer minimo) {
        if (minimo == null || minimo < 0) {
            return "Valor inválido!";
        }

        cotaMinima = minimo;

        return "Cota mínima definida: \nR$ " + minimo;
    }

    
    /* JANELA GUI */
    public static void main(String arg[]){
        GUI janela = new GUI(CaixaEletronico.class);
        janela.show();
    }
}
