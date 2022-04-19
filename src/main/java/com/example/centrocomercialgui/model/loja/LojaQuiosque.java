package com.example.centrocomercialgui.model.loja;

/**
 * Classe que representa um quiosque. Os quiosques pagam uma renda comum fixa entre todos.
 */
public class LojaQuiosque extends Comum
{
    private static float renda = 250f;

    /**
     * Construtor sem parâmetros do Quiosque
     */
    public LojaQuiosque()
    {
        super();
        setTipoLoja(TipoLoja.QUIOSQUE);
    }

    /**
     * Construtor com parâmetros do Quiosque
     * @param nome o nome do Quiosque
     * @param area a área do Quiosque
     * @param receitas a receita do Quiosque
     * @param numeroFuncionarios o número de funcionários do Quiosque
     */
    public LojaQuiosque(String nome, int area, float receitas, int numeroFuncionarios)
    {
        super(nome, area, receitas, numeroFuncionarios);
        setTipoLoja(TipoLoja.QUIOSQUE);
    }

    /**
     * Construtor com parâmetros do Quiosque. Este aceita um ID manualmente introduzido (usar apenas no sistema save/load)
     * @param id o ID manualmente introduzido
     * @param nome o nome do Quiosque
     * @param area a área do Quiosque
     * @param receitas a receita do Quiosque
     * @param numeroFuncionarios o número de funcionários do Quiosque
     */
    public LojaQuiosque(int id, String nome, int area, float receitas, int numeroFuncionarios, TipoLoja tipoLoja)
    {
        super(id, nome, area, receitas, numeroFuncionarios, tipoLoja);
    }

    /**
     * Obter a renda paga por todas as instâncias da classe
     * @return a renda
     */
    public static float getRenda()
    {
        return renda;
    }

    /**
     * Atribuir um novo valor a ser pago por todos os quiosques
     * @param renda o novo valor
     */
    public static void setRenda(float renda)
    {
        LojaQuiosque.renda = renda;
    }

    /**
     * Método da superclasse para obter a renda deste objeto
     * @return a renda
     */
    @Override
    public float getRendaFixaLoja()
    {
        return renda;
    }

    /**
     * Obter a renda desta loja
     * @return a renda desta loja
     */
    @Override
    public float calculaRenda()
    {
        return renda;
    }


    /**
     * Representação textual do Quiosque. Inclui toda a informação relevante das superclasses
     * @return o texto
     */
    @Override
    public String toString()
    {
        return String.format("%s Custo Segurança: %.2f€\n", super.toString(), renda);
    }
}
