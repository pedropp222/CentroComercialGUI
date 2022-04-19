package com.example.centrocomercialgui.model.loja;

/**
 * Classe que representa um restaurante. Extende a loja comum e implementa sistema de segurança.
 * Contém o valor de renda fixa para por todas as lojas do mesmo tipo e de um custo de segurança por cada
 * mesa. Inclui ainda um custo de manutenção variável e independente de outras Lojas.
 */
public class LojaRestauracao extends Comum implements ComSeguranca
{
    private static float rendaFixa = 700f;
    private static float valorPorMesa = 5f;

    @UILojaElement
    private int numMesas;
    @UILojaElement
    private float custoManutencao;

    /**
     * Construtor sem parâmetros do Restaurante
     */
    public LojaRestauracao()
    {
        super();
        setTipoLoja(TipoLoja.RESTAURANTE);
    }

    /**
     * Construtor com parâmetros do Restaurante
     * @param nome o nome do Restaurante
     * @param area a área do Restaurante
     * @param receitas a receita do Restaurante
     * @param numeroFuncionarios o número de funcionários do Restaurante
     * @param numMesas o número de mesas do Restaurante
     * @param custoManutencao o custo de manutenção do Restaurante
     */
    public LojaRestauracao(String nome, int area, float receitas, int numeroFuncionarios,int numMesas, float custoManutencao)
    {
        super(nome, area, receitas, numeroFuncionarios);
        this.numMesas = numMesas;
        this.custoManutencao = custoManutencao;
        setTipoLoja(TipoLoja.RESTAURANTE);
    }

    /**
     * Construtor com parâmetros do Restaurante. Este aceita um ID manualmente introduzido (usar apenas no sistema save/load)
     * @param id o ID manualmente introduzido
     * @param nome o nome do Restaurante
     * @param area a área do Restaurante
     * @param receitas a receita do Restaurante
     * @param numeroFuncionarios o número de funcionários do Restaurante
     * @param numMesas o número de mesas do Restaurante
     * @param custoManutencao o custo de manutenção do Restaurante
     */
    public LojaRestauracao(int id, String nome, int area, float receitas, int numeroFuncionarios, int numMesas, float custoManutencao, TipoLoja tipoLoja)
    {
        super(id, nome, area, receitas, numeroFuncionarios,tipoLoja);
        this.numMesas = numMesas;
        this.custoManutencao = custoManutencao;
    }

    /**
     * Obter o valor pago por mesa à segurança
     * @return o valor
     */
    public static float getValorPorMesa()
    {
        return valorPorMesa;
    }

    /**
     * Registar um novo valor pago por mesa à segurança
     * @param valorPorMesa o valor
     */
    public static void setValorPorMesa(float valorPorMesa)
    {
        LojaRestauracao.valorPorMesa = valorPorMesa;
    }

    /**
     * Obter o valor da renda fixa paga por todos os restaurantes
     * @return o valor
     */
    public static float getRendaFixa()
    {
        return rendaFixa;
    }

    /**
     * Atribuir um novo valor para a renda fixa paga por todos os restaurantes
     * @param rendaFixa o novo valor
     */
    public static void setRendaFixa(float rendaFixa)
    {
        LojaRestauracao.rendaFixa = rendaFixa;
    }

    /**
     * Obter o número de mesas deste restaurante
     * @return o número de mesas
     */
    public int getNumMesas()
    {
        return numMesas;
    }

    /**
     * Atribuir um novo número de mesas para este restaurante. O valor não pode ser negativo.
     * @param numMesas o novo número
     */
    public void setNumMesas(int numMesas)
    {
        if (numMesas < 0) return;
        this.numMesas = numMesas;
    }

    /**
     * Obter o custo de manutenção deste restaurante.
     * @return o custo
     */
    public float getCustoManutencao()
    {
        return custoManutencao;
    }

    /**
     * Atribuir um novo custo de manutenção a este restaurante.
     * @param custoManutencao o novo custo
     */
    public void setCustoManutencao(float custoManutencao)
    {
        this.custoManutencao = custoManutencao;
    }

    /**
     * Obter o custo relativo à segurança deste restaurante.
     * @return o custo
     */
    @Override
    public float calcularCustoSeguranca()
    {
        return valorPorMesa*numMesas;
    }

    /**
     * Obter a renda fixa deste restaurante
     * @return a renda
     */
    @Override
    public float getRendaFixaLoja()
    {
        return rendaFixa;
    }

    /**
     * Obter a representação textual completa desta loja. Inclui toda a informação relevante das superclasses.
     * @return o texto
     */
    @Override
    public String toString()
    {
       return String.format("%s Custos de Manutencao: %.2f€\n Número de Mesas: %d\n", super.toString(), custoManutencao, numMesas);
    }

    /**
     * Verifica se este objeto é igual a um objeto passado por parâmetro
     * @param o o objeto a avaliar
     * @return se é igual
     */
    @Override
    public boolean equals(Object o)
    {
        if (!super.equals(o)) return false;

        LojaRestauracao obj = (LojaRestauracao) o;

        if (numMesas != obj.numMesas) return false;
        return obj.custoManutencao == custoManutencao;
    }
}
