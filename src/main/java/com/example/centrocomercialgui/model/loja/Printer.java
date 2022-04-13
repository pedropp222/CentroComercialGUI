package com.example.centrocomercialgui.model.loja;

/**
 * Classe que contém funcionalidades de impressão de vários tipos de dados e informação relativa a CentrosComerciais.
 */
public class Printer
{
    /**
     * Lista todas as lojas que estao no centro comercial
     * @param x o centro comercial a ser lido
     */
    public static void listarLojasTodas(CentroComercial x)
    {
        for (int i = 0; i < x.getTotalLojas(); i++)
        {
            Loja loja = x.obterLoja(i);
            System.out.println(loja);
        }
    }

    /**
     * Lista as lojas âncora todas de um centro comercial, exibindo todas as informações das lojas
     * @param x CentroComercial existente
     */
    public static void listarLojasAncora(CentroComercial x)
    {
        for (int i = 0; i < x.getTotalLojas (); i++)
        {
            Loja loja = x.obterLoja(i);
            if (loja instanceof AncoraExterna || loja instanceof AncoraPropria)
                System.out.println(loja);
        }
    }

    /**
     * Lista as lojas comuns todas de um centro comercial, exibindo todas as informações das lojas
     * @param x Centro Comercial de onde vamos listar as Lojas Comuns
     */
    public static void listarLojasComuns(CentroComercial x)
    {
        for (int i = 0; i < x.getTotalLojas (); i++)
        {
            Loja loja = x.obterLoja(i);
            if (loja instanceof Comum)
                System.out.println(loja);
        }
    }

    /**
     * Listar as lojas todas de um centro comercial, exibindo apenas o nome e o id das lojas
     * @param x Centro Comercial de onde vamos listar as lojas
     */
    public static void listarLojasSoNome(CentroComercial x)
    {
        for (int i=0; i < x.getTotalLojas (); i++)
        {
            Loja loja = x.obterLoja (i);
            System.out.printf ("ID - [%d] || Nome - %s\n", loja.getId (), loja.getNome ());
        }
    }
}
