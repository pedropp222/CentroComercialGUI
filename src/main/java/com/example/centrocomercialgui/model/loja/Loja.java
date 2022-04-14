package com.example.centrocomercialgui.model.loja;

/**
 * Classe abstrata que representa uma loja. Contém todas as funcionalidades base de uma loja como o id, nome, area
 * receitas e variaveis de classe que controlam a classificação das áreas das lojas e a contagem global de lojas
 * criadas.
 */
public abstract class Loja implements Comparable<Loja>
{
    private int id = 0;
    private String nome;
    private int area;
    private float receitas;
    private static int totalLojas = 0;

    private static int areaPequeno = 20;
    private static int areaGrande = 100;

    private final String DEFAULT_NAME = "sem nome";

    private TipoLoja tipoLoja;

    /**
     * Construtor sem parâmetros de uma Loja
     */
    public Loja()
    {
        this.id = totalLojas;
        this.nome = DEFAULT_NAME;
        totalLojas++;
        tipoLoja = TipoLoja.OUTRO;
    }

    /**
     * Construtor com parâmetros de uma Loja
     * @param nome o nome da Loja
     * @param area a área da Loja
     * @param receitas as receitas da Loja
     */
    public Loja(String nome, int area, float receitas)
    {
        this();
        this.nome = nome;
        this.area = area;
        this.receitas = receitas;
    }

    /**
     * Construtor com parâmetros de uma Loja. Este permite a entrada manual de um ID (Usar apenas no sistema de save/load)
     * @param id o id manualmente aplicado
     * @param nome o nome da Loja
     * @param area a área da Loja
     * @param receitas as receitas da Loja
     */
    public Loja(int id, String nome, int area, float receitas, TipoLoja tipoLoja)
    {
        this.id = id;
        this.nome = nome;
        this.area = area;
        this.receitas = receitas;
        totalLojas = Math.max(totalLojas,id);
        totalLojas++;
        this.tipoLoja = tipoLoja;
    }

    /**
     * Obter o nome desta Loja
     * @return o nome
     */
    public String getNome()
    {
        return nome;
    }

    /**
     * Atribuir um novo nome a esta Loja
     * @param nome o novo nome
     */
    public void setNome(String nome)
    {
        this.nome = nome;
    }

    /**
     * Obter o valor da área desta Loja
     * @return a área
     */
    public int getArea()
    {
        return area;
    }

    /**
     * Atribuir uma nova área a esta Loja. O valor tem que ser maior que zero.
     * @param area a nova area.
     */
    public void setArea(int area)
    {
        if (area < 0) return;
        this.area = area;
    }

    /**
     * Obter o valor da área que classifica uma Loja como 'pequeno'
     * @return o valor
     */
    public static int getAreaPequeno()
    {
        return areaPequeno;
    }

    /**
     * Atribuir um novo valor para o área pequeno que irá afetar a classificação das Lojas.
     * Este valor tem que ser menor que o área grande.
     * @param areaPequeno a nova area
     */
    public static void setAreaPequeno(int areaPequeno)
    {
        if (areaPequeno < areaGrande)
        {
            Loja.areaPequeno = areaPequeno;
        }
    }

    /**
     * Obter o valor da área que classifica uma Loja como 'grande'
     * @return o valor
     */
    public static int getAreaGrande()
    {
        return areaGrande;
    }

    /**
     * Atribuir um novo valor para a área grande que irá afetar a classificação das Lojas.
     * Este valor tem que ser maior que o área pequeno.
     * @param areaGrande o novo valor
     */
    public static void setAreaGrande(int areaGrande)
    {
        if (areaGrande > areaPequeno)
        {
            Loja.areaGrande = areaGrande;
        }
    }

    /**
     * Obter as receitas do ano passado desta Loja
     * @return o valor
     */
    public float getReceitas()
    {
        return receitas;
    }

    /**
     * Atribuir um novo valor para as receitas do ano passado
     * @param receitas o novo valor
     */
    public void setReceitas(float receitas)
    {
        this.receitas = receitas;
    }

    /**
     * Obter o número de identificação único desta Loja
     * @return o ID
     */
    public int getId()
    {
        return id;
    }

    /**
     * Obter o número global de Lojas que já foram instanciadas.
     * @return o número total
     */
    public static int getTotalLojas()
    {
        return totalLojas;
    }

    /**
     * Faz reset ao número total de lojas instanciadas (usado pelo sistema de Main Menu)
     */
    public static void resetTotalLojas()
    {
        totalLojas = 0;
    }


    /**
     * Obter o número global de Lojas Ancora que já foram instanciadas.
     * @return o número de lojas ancora
     */
    public static int getLojasAncora()
    {
        return totalLojas - Comum.getContagem();
    }

    /**
     * Obter a classificação desta Loja segundo a sua área e com as variaveis de classe que controlam
     * essa mesma classificação
     * @return a classificação
     */
    public String getClassificacao()
    {
        if (area < areaPequeno)
        {
            return LojaArea.PEQUENO;
        }
        else if (area > areaGrande)
        {
            return LojaArea.GRANDE;
        }

        return LojaArea.MEDIO;
    }

    public TipoLoja getTipoLoja()
    {
        return tipoLoja;
    }

    public void setTipoLoja(TipoLoja tipoLoja)
    {
        this.tipoLoja = tipoLoja;
    }

    /**
     * Compara esta Loja com outra, usando o nome como método de comparação. Usado por sistemas de sort.
     * @param o a Loja a comparar
     * @return o numero indicador (menor, maior, igual)
     */
    @Override
    public int compareTo(Loja o)
    {
        return nome.compareTo(o.nome);
    }

    /**
     * Representação textual desta Loja, com todos os atributos disponíveis.
     * @return o texto
     */
    @Override
    public String toString()
    {
        return String.format("ID: %d\n Tipo de Loja: %s\nNome: %s\n Área: %d\n Classificação: %s\n Receitas: %.2f€\n",id, tipoLoja, nome,area, getClassificacao(), receitas);
    }

    /**
     * Verificar se este objeto é igual ao objeto a ser comparado.
     * @param o o objeto a ser comparado
     * @return se é igual ou não
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Loja loja = (Loja) o;

        if (area != loja.area) return false;
        if (loja.receitas != receitas) return false;
        return nome.equals(loja.nome);
    }
}