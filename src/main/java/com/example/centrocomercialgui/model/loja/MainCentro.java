package com.example.centrocomercialgui.model.loja;

import com.example.centrocomercialgui.model.comparators.SortArea;
import com.example.centrocomercialgui.model.comparators.SortType;

import java.io.IOException;
import java.util.Scanner;

/**
 * Classe principal que agrupa o conteudo criado e que faz todos os tipos de operacoes necessarias e implementadas.
 */
public class MainCentro
{
    /**
     * método main
     * @param args passado do exterior por parametros do terminal
     * @throws IOException um erro de leitura / escrita de ficheiros
     */
//    public static void main(String[] args) throws IOException
//    {
//        System.out.println("O que pretende fazer?");
//        System.out.println("[0] - MENU [1] - DEMO");
//
//        Scanner sc = new Scanner(System.in);
//
//        if (sc.nextInt() == 0)
//        {
//            MainMenu.iniciar();
//        }
//        else
//        {
//            mainDemo();
//        }
//    }

    /**
     * Método base de demonstração das funcionalidades do programa (alíneas da ficha do sprint)
     * @throws IOException um erro de leitura / escrita de ficheiros
     */
    private static void mainDemo() throws IOException
    {
        final String NOME_FICHEIRO = "dados.txt";

        CentroComercial c1 = FileManager.loadFile(NOME_FICHEIRO);

        if (c1 == null)
        {
            System.out.println("Criar um novo Centro Comercial...");
            c1 = new CentroComercial("centro", "rua");

            c1.adicionarLoja(new AncoraPropria("cafe", 20, 6000, 500));
            c1.adicionarLoja(new AncoraPropria("animais", 60, 10000, 1000));
            c1.adicionarLoja(new AncoraPropria("baloes", 100, 5000, 750));
            c1.adicionarLoja(new AncoraExterna("barcos", 240, 18000, 7, 1500));
            c1.adicionarLoja(new LojaRestauracao("mac", 19, 20000, 14, 7, 1500));
            c1.adicionarLoja(new LojaRestauracao("bk", 909, 27000, 20, 12, 2000));
            c1.adicionarLoja(new LojaRestauracao("wendy", 200, 250000, 36, 21, 15000));
            c1.adicionarLoja(new LojaRestauracao("taco", 50, 1000, 5, 4, 1100));
            c1.adicionarLoja(new LojaQuiosque("cigarrilhas", 30, 12000, 3));
            c1.adicionarLoja(new LojaQuiosque("raspadinhas", 30, 120000, 3));
            c1.adicionarLoja(new LojaQuiosque("jornais", 12, 8000, 3));
        }
        else
        {
            System.out.println("Carregou Centro Comercial do disco.");
        }

        LojaRestauracao.setValorPorMesa(7f);

        System.out.println("\n\n ##NOME DO CENTRO COMERCIAL:##\n" + c1.getNome());

        System.out.println("\n\n ##QTD LOJAS CENTRO COMERCIAL:##\n" + c1.getTotalLojas());

        System.out.printf("\n\n ##TOTAL RENDAS LOJAS ANCORA: ##\n%.2f\n\n ##TOTAL RENDAS LOJAS COMUNS##: \n  %.2f", c1.calcularRendaAncora(), c1.calcularRendaComuns());

        System.out.println("\n\n ##TOTAL CUSTOS SEGURANCA CENTRO COMERCIAL##:\n" + (c1.calcularTotalSegurancaAncora() + c1.calcularTotalSegurancaComum()));

        System.out.println("\n\n ##TOTAL RECEITAS CENTRO COMERCIAL##:\n" + c1.calcularTotalReceitas());

        System.out.printf("\n\n ##PESO LOJAS ANCORA:## \n%.2f%%\n\n ##PESO LOJAS COMUNS##:\n %.2f%%", c1.calculaPercentagemReceitasLojasAncora(), c1.calculaPercentagemReceitasLojasComuns());

        System.out.println("\n\n ##LOJAS ORDENADAS ALFABETICAMENTE##\n");


        c1.ordenarPorNome();

        Printer.listarLojasTodas(c1);


        System.out.println("\n\n ##LOJAS ORDENADAS ALFABETICAMENTE PELO TIPO E INVERSAMENTE PELA AREA##\n");

        c1.ordenarPor(new SortArea().reversed());
        c1.ordenarPor(new SortType());

        Printer.listarLojasTodas(c1);

        System.out.println("\n\n ##LOJA ANCORA 'barcos' ELIMINADA##\n");
        Loja ae1 = new AncoraExterna("barcos", 240, 18000, 7, 1500);
        c1.removerLoja(ae1);
        Printer.listarLojasTodas(c1);

        FileManager.saveFile(NOME_FICHEIRO,c1);
    }
}