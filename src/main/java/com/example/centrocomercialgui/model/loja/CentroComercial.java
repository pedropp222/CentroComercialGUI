package com.example.centrocomercialgui.model.loja;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Classe que representa um centro comercial. Contém um ArrayList do tipo Loja e várias funcionalidades que permitem
 * realizar consultas e cálculos baseados nas suas lojas.
 */
public class CentroComercial
{
    private String nome;
    private String morada;

    //contador private de lojasComuns a fim de conseguir calcular o número de todos os tipos de lojas neste centro comercial
    private int lojasComuns = 0;

    private final String DEFAULT_NAME = "Nenhum";
    private final String DEFAULT_MORADA = "Local Nenhum";

    private ArrayList<Loja> lojas = new ArrayList<>();

    /**
     * Construtor sem parâmetros
     */
    public CentroComercial()
    {
        this.nome = DEFAULT_NAME;
        this.morada = DEFAULT_MORADA;
    }

    /**
     * Construtor com parâmetros
     * @param nome o nome do centro comercial
     * @param morada a morada do centro comercial
     */
    public CentroComercial(String nome, String morada)
    {
        this.nome = nome;
        this.morada = morada;
    }

    /**
     * Obter o nome do centro comercial
     * @return o nome
     */
    public String getNome()
    {
        return nome;
    }

    /**
     * Substituir o nome do centro comercial por um valor novo
     * @param nome o novo nome
     */
    public void setNome(String nome)
    {
        this.nome = nome;
    }

    /**
     * Obter a morada do centro comercial
     * @return a morada
     */
    public String getMorada()
    {
        return morada;
    }

    /**
     * Substituir a morada do centro comercial para um valor novo
     * @param morada a nova morada
     */
    public void setMorada(String morada)
    {
        this.morada = morada;
    }

    /**
     * Permite ir buscar uma loja no array pelo seu indice, só procura pela loja se o parametro for maior ou igual a 0 e menor que o tamanho do array
     * @param index indica o indice da loja que queremos procurar
     * @return a loja posicionada no lugar "index" do array
     */
    public Loja obterLoja(int index)
    {
        if (index < 0 || index >= lojas.size())
        {
            return null;
        }
        return lojas.get(index);
    }

    /**
     * Tenta remover um objeto que representa uma loja e que exista na lista
     * @param x a loja a ser removida
     * @return se conseguiu eliminar
     */
    public boolean removerLoja(Loja x)
    {
        if (x instanceof Comum)
        {
            lojasComuns--;
        }

        return lojas.remove(x);
    }

    public boolean removerLoja(int index)
    {
        if (index < 0 || index >= lojas.size())
        {
            return false;
        }

        Loja x = lojas.get(index);

        if (x instanceof Comum)
        {
            lojasComuns--;
        }

        return lojas.remove(index) != null;
    }

    /**
     * Tenta remover uma loja a partir do seu ID interno (não o index do array)
     * @param id o ID a remover
     * @return se conseguiu eliminar
     */
    public boolean removerLojaID(int id)
    {
        for(Loja j : lojas)
        {
            if (j.getId() == id)
            {
                return removerLoja(j);
            }
        }

        return false;
    }


    /**
     * Tenta encontrar uma loja a partir do seu ID interno (não o index do array)
     * @param id o ID a encontrar
     * @return a Loja que encontrou ou null se não encontrou
     */
    public Loja encontrarLojaID(int id)
    {
        for(Loja j : lojas)
        {
            if (j.getId() == id) return j;
        }

        return null;
    }
    /**
     * Adiciona uma loja para o array. Caso a loja seja do tipo dinâmico 'Comum', incrementar o contador interno
     * @param x a loja a ser adicionada
     */
    public void adicionarLoja(Loja x)
    {
        if (x instanceof Comum)
        {
            lojasComuns++;
        }
        lojas.add(x);
    }

    /**
     * Ordena a lista de lojas usando um qualquer comparator do tipo loja
     * @param sorter o comparator a ser usado
     */
    public void ordenarPor(Comparator<Loja> sorter)
    {
        lojas.sort(sorter);
    }

    /**
     * Calcula o valor da renda a ser paga por todas as lojas do tipo 'Comum'
     * @return o total da renda
     */
    public float calcularRendaComuns()
    {
        float total = 0f;

        for(Loja x : lojas)
        {
            if (x instanceof Comum)
            {
                total += ((Comum) x).calculaRenda();
            }
        }

        return total;
    }

    /**
     * Retorna o numero total de lojas neste centro comercial.
     * @return o total
     */
    public int getTotalLojas()
    {
        return lojas.size();
    }

    /**
     * Retorna o número total de lojas do tipo 'Comum' neste centro comercial
     * @return o número de lojas 'Comum'
     */
    public int getLojasComuns()
    {
        return lojasComuns;
    }

    /**
     * Retorna o número total de lojas do tipo 'Ancora' neste centro comercial
     * @return o número de lojas 'Ancora'
     */
    public int getLojasAncora()
    {
        return lojas.size()-lojasComuns;
    }

    /**
     * Calcula o valor da renda de todas as lojas do tipo 'Ancora' que paguem renda
     * @return o total da renda
     */
    public float calcularRendaAncora()
    {
        float total = 0f;

        for(Loja x : lojas)
        {
            if (x instanceof AncoraExterna)
            {
                total += ((AncoraExterna) x).calculaRenda();
            }
        }

        return total;
    }

    /**
     * Calcula o total a ser pago em segurança apenas pelas lojas 'Ancora'
     * @return o total
     */
    public float calcularTotalSegurancaAncora()
    {
        float total = 0f;

        for(Loja x : lojas)
        {
            if (x instanceof ComSeguranca && !(x instanceof Comum))
            {
                total += ((ComSeguranca) x).calcularCustoSeguranca();
            }
        }

        return total;
    }

    /**
     * Calcula o total a ser pago em segurança apenas pelas lojas 'Comum'
     * @return o total
     */
    public float calcularTotalSegurancaComum()
    {
        float total = 0f;

        for (Loja x : lojas)
        {
            if (x instanceof ComSeguranca && x instanceof Comum)
            {
                total += ((ComSeguranca) x).calcularCustoSeguranca();
            }
        }

        return total;
    }

    /**
     * Calcula o valor total de receitas deste centro comercial
     * @return o total
     */
    public float calcularTotalReceitas()
    {
        return calcularTotalSegurancaComum()+calcularTotalSegurancaAncora()+calcularRendaAncora()+calcularRendaComuns();
    }

    /**
     * Calcula a percentagem das receitas que as lojas Ancora trazem para o Centro Comercial
     * @return o valor da percentagem
     */
    public float calculaPercentagemReceitasLojasAncora()
    {
        return ((calcularRendaAncora()+calcularTotalSegurancaAncora())*100f)/calcularTotalReceitas();
    }

    /**
     * Calcula a percentagem das receitas que as Lojas Comuns trazem para o Centro Comercial
     * @return o valor da percentagem
     */
    public float calculaPercentagemReceitasLojasComuns()
    {
        return 100f-calculaPercentagemReceitasLojasAncora();
    }

    /**
     * Ordena a lista usando a implementação da ‘interface’ Comparable nas lojas, ordenando assim pelo nome
     */
    public void ordenarPorNome()
    {
        Collections.sort(lojas);
    }

    /**
     * Representação textual do objeto e da sua informação básica
     * @return uma String
     */
    @Override
    public String toString()
    {
        return String.format("Centro comercial:\n Nome: %s\n Morada: %s\n Quantidade de Lojas Âncora: %d\n Quantidade de Lojas Comuns: %d\n\n", nome, morada, getLojasAncora(), getLojasComuns());
    }
}