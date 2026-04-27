package caixa_eletronico;

/**
* Interface (contrato) para utilização da interface gráfica.
* Nesse contrato e definido as operações de entrada e saída de dinheiro do caixa eletrônico
*/
public interface ICaixaEletronico{
		
	/**
	* Pega o valor total disponível no caixa eletrônico
	* @retorna uma string formatada com o valor total disponível
	*/
	public String pegaValorTotalDisponivel();
	
	/**
	* Efetua o saque
	* @param valor a ser sacado
	* @retorna uma string formatada informando o resultado da operação
	*/
	public String sacar(Integer valor);
	
	/**
	* Pega um relatório informando as cédulas e a quantidade de cédulas disponíveis
	* @retorna uma string formatada com as cédulas e suas quantidades
	*/
	public String pegaRelatorioCedulas();
	
	/**
	* Efetua a reposição de cédulas
	* @param cédula de reposição
	* @param quantidade de cédulas para reposição
	* @retorna uma string formatada informando o resultado da operação
	*/
	public String reposicaoCedulas(Integer cedula, Integer quantidade);
	
	/**
	* Efetua a leitura da cota miníma de atendimento
	* @param minímo
	* @retorna uma string formatada informando o resultado da operação
	*/
	public String armazenaCotaMinima(Integer minimo);
}