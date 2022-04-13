package com.example.centrocomercialgui.model.loja;

import com.example.centrocomercialgui.model.comparators.SortId;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Formatter;
import java.util.Locale;
import java.util.Scanner;

/**
 * Classe que gere o carregamento e gravação de ficheiros. Gravam e carregam o estado de objetos do tipo
 * CentroComercial e das suas lojas.
 */
public class FileManager
{

    //numero de params corretas dos tipos de dados presentes
    private static final int CENTRO_PARAM = 5;
    private static final int QUIOSQUE_PARAM = 11;
    private static final int RESTAURACAO_PARAM = 15;
    private static final int ANCORAEX_PARAM = 13;
    private static final int ANCORAPRO_PARAM = 11;
    private static final int STATICVARS_PARAM = 2;

    //nomes dos tipos de dados presentes nos ficheiros
    private static final String CENTRO = "CentroComercial";
    private static final String QUIOSQUE = "LojaQuiosque";
    private static final String RESTAURANTE = "LojaRestauracao";
    private static final String ANCORAEX = "AncoraExterna";
    private static final String ANCORAPRO = "AncoraPropria";

    private static final String LOJAAP = "LojaAreaPequeno";
    private static final String LOJAAG = "LojaAreaGrande";

    private static final String RENDAAE = "RendaFixaAncoraExterna";
    private static final String RENDAQ = "RendaQuiosques";
    private static final String RENDARES = "RendaFixaRestaurante";
    private static final String VALORMRES = "ValorMesaRestaurante";


    /**
     * Carrega o conteúdo de um ficheiro para um objeto CentroComercial. Caso o ficheiro não exista,
     * retorna null
     * @param nome o nome do ficheiro
     * @return o CentroComercial populado com todos os dados ou null se o ficheiro não existe
     * @throws FileNotFoundException um erro
     */
    public static CentroComercial loadFile(String nome) throws FileNotFoundException
    {
        CentroComercial centro = new CentroComercial();

        File ficheiro = new File(nome);

        if (!ficheiro.exists())
        {
            return null;
        }

        Scanner sc = new Scanner(ficheiro);

        //parse das linhas do ficheiro
        while(sc.hasNextLine())
        {
            String[] tokens = sc.nextLine().split("\\W*;\\W*");

            switch (tokens[0])
            {
                //parse CentroComercial
                case CENTRO:
                    if (tokens.length != CENTRO_PARAM)
                    {
                        System.out.println(CENTRO+" nao tem parâmetros corretos");
                    }
                    else
                    {
                        centro.setNome(tokens[2]);
                        centro.setMorada(tokens[4]);
                    }
                    break;

                //parse dos 4 tipos de lojas
                case QUIOSQUE:
                    if (tokens.length != QUIOSQUE_PARAM)
                    {
                        System.out.println("tipo "+QUIOSQUE+" nao tem parâmetros corretos");
                    }
                    else
                    {
                        LojaQuiosque l = new LojaQuiosque(Integer.parseInt(tokens[2]),tokens[4], Integer.parseInt(tokens[6]),
                                Float.parseFloat(tokens[8]), Integer.parseInt(tokens[10]));

                        centro.adicionarLoja(l);
                    }
                    break;
                case RESTAURANTE:
                    if (tokens.length != RESTAURACAO_PARAM)
                    {
                        System.out.println("tipo "+RESTAURANTE+" nao tem parâmetros corretos");
                    }
                    else
                    {
                        LojaRestauracao l2 = new LojaRestauracao(Integer.parseInt(tokens[2]),tokens[4], Integer.parseInt(tokens[6]),
                                Float.parseFloat(tokens[8]), Integer.parseInt(tokens[10])
                                , Integer.parseInt(tokens[12]), Float.parseFloat(tokens[14]));

                        centro.adicionarLoja(l2);
                    }
                    break;
                case ANCORAEX:
                    if (tokens.length != ANCORAEX_PARAM)
                    {
                        System.out.println("tipo "+ANCORAEX+" nao tem parâmetros corretos");
                    }
                    else
                    {
                        AncoraExterna a1 = new AncoraExterna(Integer.parseInt(tokens[2]),tokens[4], Integer.parseInt(tokens[6]),
                                Float.parseFloat(tokens[8]), Integer.parseInt(tokens[10]), Float.parseFloat(tokens[12]));

                        centro.adicionarLoja(a1);
                    }
                    break;
                case ANCORAPRO:
                    if (tokens.length != ANCORAPRO_PARAM)
                    {
                        System.out.println("tipo "+ANCORAPRO+" nao tem parâmetros corretos");
                    }
                    else
                    {
                        AncoraPropria a2 = new AncoraPropria(Integer.parseInt(tokens[2]),tokens[4], Integer.parseInt(tokens[6]),
                                Float.parseFloat(tokens[8]), Float.parseFloat(tokens[10]));
                        centro.adicionarLoja(a2);
                    }
                    break;

                //parse dos valores static
                case LOJAAP:
                    if (tokens.length != STATICVARS_PARAM)
                    {
                        System.out.println("valor estatico incorreto");
                    }
                    else
                    {
                        Loja.setAreaPequeno(Integer.parseInt(tokens[1]));
                    }
                    break;
                case LOJAAG:
                    if (tokens.length != STATICVARS_PARAM)
                    {
                        System.out.println("valor estatico incorreto");
                    }
                    else
                    {
                        Loja.setAreaGrande(Integer.parseInt(tokens[1]));
                    }
                    break;
                case RENDAAE:
                    if (tokens.length != STATICVARS_PARAM)
                    {
                        System.out.println("valor estatico incorreto");
                    }
                    else
                    {
                        AncoraExterna.setRendaFixa(Float.parseFloat(tokens[1]));
                    }
                    break;
                case RENDAQ:
                    if (tokens.length != STATICVARS_PARAM)
                    {
                        System.out.println("valor estatico incorreto");
                    }
                    else
                    {
                        LojaQuiosque.setRenda(Float.parseFloat(tokens[1]));
                    }
                    break;
                case RENDARES:
                    if (tokens.length != STATICVARS_PARAM)
                    {
                        System.out.println("valor estatico incorreto");
                    }
                    else
                    {
                        LojaRestauracao.setRendaFixa(Float.parseFloat(tokens[1]));
                    }
                    break;
                case VALORMRES:
                    if (tokens.length != STATICVARS_PARAM)
                    {
                        System.out.println("valor estatico incorreto");
                    }
                    else
                    {
                        LojaRestauracao.setValorPorMesa(Float.parseFloat(tokens[1]));
                    }
                    break;
                //caso default
                default:
                    if (!tokens[0].startsWith("<"))
                    {
                        System.out.println("Tipo invalido " + tokens[0]);
                    }
                    break;
            }
        }

        sc.close();
        return centro;
    }

    /**
     * Grava para um ficheiro os dados completos de um CentroComercial
     * @param nome o nome do ficheiro a guardar
     * @param centro o objeto CentroComercial
     * @throws IOException um erro
     */
    public static void saveFile(String nome, CentroComercial centro) throws IOException
    {
        Formatter f = new Formatter(new File(nome), Charset.defaultCharset(),Locale.US);

        //Save do Centro Comercial
        f.format(CENTRO+" ; NOME ; %s ; MORADA ; %s",centro.getNome(),centro.getMorada());

        f.format("\n<------------- VALORES COMUNS ENTRE LOJAS DO MESMO TIPO ------------------>");

        centro.ordenarPor(new SortId());

        //Save do estado das vars static
        f.format("\n"+LOJAAP+" ; %d", Loja.getAreaPequeno());
        f.format("\n"+LOJAAG+" ; %d", Loja.getAreaGrande());

        f.format("\n"+RENDAAE+" ; %.2f",AncoraExterna.getRendaFixa());
        f.format("\n"+RENDAQ+" ; %.2f",LojaQuiosque.getRenda());
        f.format("\n"+RENDARES+" ; %.2f",LojaRestauracao.getRendaFixa());
        f.format("\n"+VALORMRES+" ; %.2f",LojaRestauracao.getValorPorMesa());

        f.format("\n<------------- LOJAS DESTE CENTRO COMERCIAL ------------------>");
        //Save do estado das lojas
        int maxName = getNomeMaior(centro);
        for(int i = 0 ; i < centro.getTotalLojas(); i++)
        {
            Loja j = centro.obterLoja(i);
            if (j instanceof LojaQuiosque)
            {
                f.format("\n"+QUIOSQUE+"    ; ID ; %4d ; NOME ; %"+maxName+"s ; AREA ; %4d ; RECEITAS ; %10.2f ; NUMERO FUNCIONARIOS ; %3d",j.getId(), j.getNome(), j.getArea(), j.getReceitas(), ((LojaQuiosque) j).getNumeroFuncionarios());
            }
            else if (j instanceof LojaRestauracao)
            {
                f.format("\n"+RESTAURANTE+" ; ID ; %4d ; NOME ; %"+maxName+"s ; AREA ; %4d ; RECEITAS ; %10.2f ; NUMERO FUNCIONARIOS ; %3d ; NUMERO MESAS ; %2d ; CUSTO MANUTENCAO ; %5.2f",j.getId(),j.getNome(), j.getArea(), j.getReceitas(), ((LojaRestauracao) j).getNumeroFuncionarios(), ((LojaRestauracao) j).getNumMesas(), ((LojaRestauracao) j).getCustoManutencao());
            }
            else if (j instanceof AncoraExterna)
            {
                f.format("\n"+ANCORAEX+"   ; ID ; %4d ; NOME ; %"+maxName+"s ; AREA ; %4d ; RECEITAS ; %10.2f ; NUMERO FUNCIONARIOS ; %3d ; CUSTO SEGURANCA ; %5.2f",j.getId(),j.getNome(), j.getArea(), j.getReceitas(), ((AncoraExterna) j).getNumeroFuncionarios(), ((AncoraExterna) j).calcularCustoSeguranca());
            }
            else if (j instanceof AncoraPropria)
            {
                f.format("\n"+ANCORAPRO+"   ; ID ; %4d ; NOME ; %"+maxName+"s ; AREA ; %4d ; RECEITAS ; %10.2f ; CUSTO SEGURANCA ; %5.2f",j.getId(),j.getNome(),j.getArea(),j.getReceitas(),((AncoraPropria) j).calcularCustoSeguranca());
            }
        }

        f.close();
        System.out.println("Ficheiro "+nome+" guardado.");
    }

    /**
     * Obtem o tamanho do maior nome de uma loja de um centro comercial
     * @param c o centro comercial
     * @return o comprimento do nome maior
     */
    private static int getNomeMaior(CentroComercial c)
    {
        int max = 0;
        for(int i = 0; i < c.getTotalLojas(); i++)
        {
            max = Math.max(max,c.obterLoja(i).getNome().length());
        }
        return max;
    }
}