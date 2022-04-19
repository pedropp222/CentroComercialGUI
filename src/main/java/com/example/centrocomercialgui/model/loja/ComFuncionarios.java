package com.example.centrocomercialgui.model.loja;

/**
 * Classe abstrata que implementa funcionalidades que diz respeito a lojas com funcionários.
 * Contém uma variavel de número de funcionários e métodos abstratos e implementados para calcular a renda de
 * qualquer subclasse
 */
public abstract class ComFuncionarios extends Loja
{
    @UILojaElement
    private int numeroFuncionarios;

    /**
     * Calcula a renda fixa de uma determinada loja com funcionários
     * @return o valor da renda fixa
     */
    public abstract float getRendaFixaLoja();

    /**
     * construtor
     */
    public ComFuncionarios()
    {
        super();
    }

    /**
     * Construtor com parâmetros
     * @param nome o nome
     * @param area a área
     * @param receitas as receitas
     * @param numeroFuncionarios numero de funcionários
     */
    public ComFuncionarios(String nome, int area, float receitas, int numeroFuncionarios)
    {
        super(nome, area, receitas);
        this.numeroFuncionarios = numeroFuncionarios;
    }

    /**
     * Construtor com parâmetros. Este permite a entrada manual de um ID (Usar apenas no sistema de save/load)
     * @param id o id a ser aplicado manualmente
     * @param nome o nome
     * @param area a área
     * @param receitas as receitas
     * @param numeroFuncionarios numero de funcionários
     */
    public ComFuncionarios(int id, String nome, int area, float receitas, int numeroFuncionarios, TipoLoja tipoLoja)
    {
        super(id, nome, area, receitas, tipoLoja);
        this.numeroFuncionarios = numeroFuncionarios;
    }

    /**
     * Implementação por defeito do método calculaRenda, usa uma fórmula
     * @return a renda
     */
    public float calculaRenda()
    {
        return getRendaFixaLoja() * (1 + getArea() / 100f) + getReceitas() / 100f;
    }

    /**
     * Obter o número de funcionarios desta loja
     * @return o número de funcionarios
     */
    public int getNumeroFuncionarios()
    {
        return numeroFuncionarios;
    }

    /**
     * Substituir o numero de funcionários pelo novo valor. Valores abaixo de 0 não são permitidos.
     * @param numeroFuncionarios o número de funcionários
     */
    public void setNumeroFuncionarios(int numeroFuncionarios)
    {
        if (numeroFuncionarios < 0) return;

        this.numeroFuncionarios = numeroFuncionarios;
    }

    /**
     * Representação textual desta classe
     * @return o texto
     */
    @Override
    public String toString()
    {
        return String.format("%s Número de Funcionários: %d\n", super.toString(), numeroFuncionarios);
    }

    /**
     * Continuação da implementação do método equals. Verifica primeiro as avaliações das superclasses antes de
     * fazer a sua propria avaliacao
     * @param o o objeto a avaliar
     * @return se é igual
     */
    @Override
    public boolean equals(Object o)
    {
        if (!super.equals(o)) return false;

        ComFuncionarios that = (ComFuncionarios) o;

        return numeroFuncionarios == that.numeroFuncionarios;
    }
}
