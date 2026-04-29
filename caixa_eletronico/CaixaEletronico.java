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

    private int cotaMinima = 0; // Inicialmente a cota começa no 0
    
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
        if (extrato.isEmpty()) { // Não ocorreu nenhuma operação no caixa
            return "Nenhum saque ou reposição foi realizado.";
        }

        String resposta = "======= EXTRATO =======\n";

        for (String e : extrato) {
            resposta += e + "\n";
        }

        resposta += "=====================\n";
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

                String texto = (quantidade == 1) ? "nota" : "notas";

                // Registra no extrato
                extrato.add("Reposição: +" + quantidade + " " + texto + " de R$ " + cedula +
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
        int totalCedulas = 0; // Totas de notas usadas

        for (int i = 0; i < cedulas.length; i++) { // Percorre todos os tipos de cédulas

            int nota = cedulas[i][0]; // valor da cédula
            int disponivel = cedulas[i][1]; // quantas cédulas tem no caixa

            int qtd = Math.min(restante / nota, disponivel); // Verifica o menor valor

            if (totalCedulas + qtd > 30) { // O caixa não pode entrgar mais de 30 notas por vez
                qtd = 30 - totalCedulas;
            }

            usadas[i] = qtd; // Guarda quantas notas foram usadas
            restante -= qtd * nota; // Diminui o valor restante
            totalCedulas += qtd; // Soma no total das notas
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
                String texto = (usadas[i] == 1) ? "nota" : "notas";
                resposta += usadas[i] + " " + texto + " de R$ " + cedulas[i][0] + "\n";
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
