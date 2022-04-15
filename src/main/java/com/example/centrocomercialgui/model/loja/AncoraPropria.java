package com.example.centrocomercialgui.model.loja;

/**
 * Esta é a classe que representa as lojas do tipo Ancora Propria e que extende a Classe Loja.
 * Inclui ainda um sistema de segurança.
 */
public class AncoraPropria extends Loja implements ComSeguranca
{
    @UILojaElement
    private float custoSeguranca;

    /**
     * Construtor sem parâmetros de uma Âncora Própria
     */
    public AncoraPropria()
    {
        super();
        setTipoLoja(TipoLoja.ANCORA_PROPRIA);
    }

    /**
     * Construtor com parâmetros de uma Âncora Própria
     * @param nome o nome da Âncora Própria
     * @param area a área da Âncora Própria
     * @param receitas a receita da Âncora Própria
     * @param custoSeguranca o custo de segunrança da Âncora Própria
     */
    public AncoraPropria(String nome, int area, float receitas, float custoSeguranca)
    {
        super(nome, area, receitas);
        this.custoSeguranca = custoSeguranca;
        setTipoLoja(TipoLoja.ANCORA_PROPRIA);
    }

    /**
     * Construtor com parâmetros da Âncora Própria. Este aceita um ID manualmente introduzido (usar apenas no sistema save/load)
     * @param id o ID manualmente introduzido
     * @param nome o nome da Âncora Própria
     * @param area a área da Âncora Própria
     * @param receitas a receita da Âncora Própria
     * @param custoSeguranca o custo de segunrança da Âncora Própria
     */
    public AncoraPropria(int id, String nome, int area, float receitas, float custoSeguranca, TipoLoja tipoLoja)
    {
        super(id, nome, area, receitas, tipoLoja);
        this.custoSeguranca = custoSeguranca;
    }

    /**
     * Obter o Custo de Segurança
     * @return o valor do Custo de Segurança
     */
    public float getCustoSeguranca()
    {
        return custoSeguranca;
    }

    /**
     * Modifica o Custo de Segurança
     * @param custoSeguranca o valor do Custo de Segurança
     */
    public void setCustoSeguranca(float custoSeguranca)
    {
        this.custoSeguranca = custoSeguranca;
    }

    /**
     * Calcula o valor do Custo de Seguranca da Ancora Propria
     * @return o valor do Custo de Seguranca da Ancora Propria
     */
    @Override
    public float calcularCustoSeguranca()
    {
        return custoSeguranca;
    }

    /**
     * Representação textual da Ancora Propria. Inclui toda a informação relevante das superclasses.
     * @return o texto
     */
    @Override
    public String toString()
    {
        return String.format("%s Custo Segurança: %.2f€\n", super.toString(), calcularCustoSeguranca());
    }

    /**
     * Compara dois objetos do tipo Ancora Propria
     * @param o é um objeto do tipo Ancora Propria
     * @return "true" se forem iguais e "false" se forem diferentes
     */
    @Override
    public boolean equals(Object o)
    {
        if (!super.equals(o)) return false;

        AncoraPropria obj  = (AncoraPropria) o;

        return obj.custoSeguranca ==  custoSeguranca;
    }
}
