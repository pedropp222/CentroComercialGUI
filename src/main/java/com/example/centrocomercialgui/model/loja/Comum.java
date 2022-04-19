package com.example.centrocomercialgui.model.loja;

/**
 * Classe abstrata que agrupa os tipos de lojas que sejam consideradas por 'Comuns'
 */
public abstract class Comum extends ComFuncionarios
{
    private static int contagem = 0;

    /**
     * Construtor
     */
    public Comum()
    {
        super();
        contagem++;
    }

    /**
     * Construtor com parâmetros
     * @param nome o nome
     * @param area a área
     * @param receitas as receitas
     * @param numeroFuncionarios o número de funcionários
     */
    public Comum(String nome, int area, float receitas, int numeroFuncionarios)
    {
        super(nome, area, receitas, numeroFuncionarios);
        contagem++;
    }

    /**
     * Construtor com parâmetros. Este aceita a entrada de um ID manualmente (Usar apenas no sistema save/load)
     * @param id o id a ser aplicado manualmente
     * @param nome o nome
     * @param area a área
     * @param receitas as receitas
     * @param numeroFuncionarios o número de funcionários
     */
    public Comum(int id, String nome, int area, float receitas, int numeroFuncionarios, TipoLoja tipoLoja)
    {
        super(id, nome, area, receitas, numeroFuncionarios, tipoLoja);
        contagem++;
    }

    /**
     * Obter o numero total de instancias de lojas do tipo Comum
     * @return o numero de lojas
     */
    public static int getContagem()
    {
        return contagem;
    }
}