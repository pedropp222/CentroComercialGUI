package com.example.centrocomercialgui.model.loja;

/**
 * Esta é a classe que represente as lojas do tipo Âncora Externa.
 * Contém funcionários e um sistema de segurança.
 */
public class AncoraExterna extends ComFuncionarios implements ComSeguranca
{
    /**
     *Esta variável de Classe "rendaFixa" define qual vai ser o grau com o qual vai ser calculada a renda.
     */
    private static float rendaFixa = 2000f;

    /**
     * O custo segurança desta AncoraExterna
     */
    @UILojaElement
    private float custoSeguranca;

    /**
     * Construtor sem parâmetros
     */
    public AncoraExterna()
    {
        super();
        setTipoLoja(TipoLoja.ANCORA_EXTERNA);
    }

    /**
     * Construtor com parâmetros
     * @param nome o nome da Âncora Externa
     * @param area a área da Âncora Externa
     * @param receitas a receita da Âncora Externa
     * @param numeroFuncionarios o número de funcionários da Âncora Externa
     * @param custoSeguranca o custo da segurança da Âncora Externa
     */
    public AncoraExterna(String nome, int area, float receitas, int numeroFuncionarios, float custoSeguranca)
    {
        super(nome, area, receitas, numeroFuncionarios);
        this.custoSeguranca = custoSeguranca;
        setTipoLoja(TipoLoja.ANCORA_EXTERNA);
    }

    /**
     * Construtor com parâmetros. Este aceita um ID manualmente introduzido (usar apenas no sistema save/load)
     * @param id o ID manualmente introduzido
     * @param nome o nome da Âncora Externa
     * @param area a área da Âncora Externa
     * @param receitas a receita da Âncora Externa
     * @param numeroFuncionarios o número de funcionários da Âncora Externa
     * @param custoSeguranca o custo da segurança da Âncora Externa
     */
    public AncoraExterna(int id, String nome, int area, float receitas, int numeroFuncionarios, float custoSeguranca, TipoLoja tipoLoja)
    {
        super(id, nome, area, receitas, numeroFuncionarios, tipoLoja);
        this.custoSeguranca = custoSeguranca;
    }

    /**
     * Altera o valor da variavel de classe rendaFixa
     * @param rendaFixa o novo valor da renda fixa
     */
    public static void setRendaFixa(float rendaFixa)
    {
        AncoraExterna.rendaFixa = rendaFixa;
    }

    /**
     * Obter a renda fixa desta classe
     * @return o valor da renda fixa
     */
    public static float getRendaFixa()
    {
        return rendaFixa;
    }

    /**
     * Obter o custo de seguranca desta loja
     * @return o custo
     */
    public float getCustoSeguranca()
    {
        return custoSeguranca;
    }

    /**
     * Substituir o custo de seguranca desta loja por um valor novo
     * @param custoSeguranca o novo valor
     */
    public void setCustoSeguranca(float custoSeguranca)
    {
        this.custoSeguranca = custoSeguranca;
    }

    /**
     * Calcula o valor da rendaFixa numa Loja do tipo Ancora Externa
     * @return o valor da rendaFixa
     */
    @Override
    public float getRendaFixaLoja()
    {
        return rendaFixa;
    }

    /**
     * Calcula o valor do Custo de Segurança de uma Ancora Externa
     * @return o valor do Custo de Segurança de uma Ancora Externa
     */
    @Override
    public float calcularCustoSeguranca()
    {
        return custoSeguranca;
    }

    /**
     * Compara dois objetos do tipo Ancora Externa
     * @param o é um objeto do tipo Ancora Externa
     * @return "true" se forem iguais e "false" se forem diferentes
     */
    @Override
    public boolean equals(Object o)
    {
        if (!super.equals(o)) return false;

        AncoraExterna obj = (AncoraExterna) o;

        return obj.custoSeguranca == custoSeguranca;
    }

    /**
     * Representação textual da Ancora Externa. Inclui toda a informação relevante das superclasses
     * @return o texto
     */
    @Override
    public String toString()
    {
        return String.format("%s Custo Segurança: %.2f€\n", super.toString(), calcularCustoSeguranca());
    }
}