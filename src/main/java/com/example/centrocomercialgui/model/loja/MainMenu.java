package com.example.centrocomercialgui.model.loja;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * Menu no terminal para interação com o utilizador. Suporta as operações para gerir centros comerciais e as suas lojas.
 */
public class MainMenu
{
    private static final Scanner sc = new Scanner(System.in);
    private static String currentFile = "";

    /**
     * Método que inicia o menu.
     *
     * @throws IOException erro de leitura/escrita de ficheiro
     */
    public static void iniciar() throws IOException
    {
        System.out.println("SISTEMA DE GESTÃO DE CENTROS COMERCIAIS");

        int esc = 0;
        CentroComercial atual = null;

        do
        {
            line();
            Loja.resetTotalLojas();
            System.out.println("Menu Principal");
            System.out.println("[1] - Criar novo Centro Comercial");
            System.out.println("[2] - Carregar Centro Comercial do disco");
            System.out.println("[3] - Sair");

            esc = sc.nextInt();

            switch (esc)
            {
                case 1:
                    atual = criarCentroComercial();
                    menuCentroComercial(atual);
                    break;
                case 2:
                    atual = carregarCentroComercial();
                    if (atual != null)
                    {
                        menuCentroComercial(atual);
                    }
                    break;
                case 3:
                    System.out.println("Obrigado por utilizar o nosso software. Copyright - José Aguiar, Pedro Martins 2022");
                    anyKey(true);
                    break;
                default:
                    noOpErro();
                    break;
            }
        }
        while (esc != 3);

    }

    /**
     * Corpo principal do menu onde é possível realizar as operações pretendidas sobre um centro comercial existente,
     *
     * @param c o centro comercial existente
     * @throws IOException erro de leitura/escrita do ficheiro
     */
    private static void menuCentroComercial(CentroComercial c) throws IOException
    {
        int esc = 0;

        do
        {
            line();
            System.out.println("A editar '" + c.getNome() + "'. Total lojas: " + c.getTotalLojas());
            System.out.println("[1] - Editar propriedades do Centro Comercial");
            System.out.println("[2] - Editar propriedades das lojas");
            System.out.println("[3] - Adicionar nova Loja");
            System.out.println("[4] - Remover Loja existente");
            System.out.println("[5] - Editar Loja existente");
            System.out.println("[6] - Listar Lojas do Centro Comercial");
            System.out.println("[7] - Guardar dados");
            System.out.println("[8] - Sair");
            esc = sc.nextInt();

            switch (esc)
            {
                case 1:
                    editarCentroComercial(c);
                    break;
                case 2:
                    editarLojasPropriedades();
                    break;
                case 3:
                    adicionarLoja(c);
                    break;
                case 4:
                    removerLoja(c);
                    break;
                case 5:
                    editarLoja(c);
                    break;
                case 6:
                    listarLojas(c);
                    break;
                case 7:
                    guardarDados(c);
                    break;
                case 8:
                    break;
                default:
                    noOpErro();
                    break;
            }
        }
        while (esc != 8);
    }

    /**
     * Possibilita a alteração das variáveis static que fazem parte dos diversos tipos de lojas
     */
    private static void editarLojasPropriedades()
    {
        line();
        System.out.println("[1] - Area Loja Pequena (atual: "+Loja.getAreaPequeno()+"m2)");
        System.out.println("[2] - Area Loja Grande  (atual: "+Loja.getAreaGrande()+"m2)");
        System.out.println("[3] - Renda Fixa Ancoras Externas  (atual:"+AncoraExterna.getRendaFixa()+"€)");
        System.out.println("[4] - Renda Quiosques (atual: "+LojaQuiosque.getRenda()+"€)");
        System.out.println("[5] - Renda Fixa Restauração (atual: "+LojaRestauracao.getRendaFixa()+"€)");
        System.out.println("[6] - Valor por Mesa Restauração (atual: "+LojaRestauracao.getValorPorMesa()+"€)");

        System.out.println("Escreva o numero da propriedade que pretende alterar (ou 0 para sair): ");
        int esc = sc.nextInt();

        switch (esc)
        {
            case 0:
                anyKey(true);
                break;
            case 1:
                System.out.println("Escreva novo valor: ");
                Loja.setAreaPequeno(sc.nextInt());
                System.out.println("Valor alterado.");
                anyKey(true);
                break;
            case 2:
                System.out.println("Escreva novo valor: ");
                Loja.setAreaGrande(sc.nextInt());
                System.out.println("Valor alterado.");
                anyKey(true);
                break;
            case 3:
                System.out.println("Escreva novo valor: ");
                AncoraExterna.setRendaFixa(sc.nextFloat());
                System.out.println("Valor alterado.");
                anyKey(true);
                break;
            case 4:
                System.out.println("Escreva novo valor: ");
                LojaQuiosque.setRenda(sc.nextFloat());
                System.out.println("Valor alterado.");
                anyKey(true);
                break;
            case 5:
                System.out.println("Escreva novo valor: ");
                LojaRestauracao.setRendaFixa(sc.nextFloat());
                System.out.println("Valor alterado.");
                anyKey(true);
                break;
            case 6:
                System.out.println("Escreva novo valor: ");
                LojaRestauracao.setValorPorMesa(sc.nextFloat());
                System.out.println("Valor alterado.");
                anyKey(true);
                break;
            default:
                noOpErro();
                break;
        }
    }

    /**
     * Guarda todos os dados do centro comercial para um ficheiro
     *
     * @param c CentroComercial existente
     * @throws IOException erro escrita/leitura de ficheiro
     */
    private static void guardarDados(CentroComercial c) throws IOException
    {
        sc.nextLine();
        if (currentFile.length() > 0)
        {
            System.out.print("Nome do ficheiro para guardar (atual: '" + currentFile + "'): ");
        } else
        {
            System.out.print("Nome do ficheiro para guardar: ");
        }

        FileManager.saveFile(sc.nextLine(), c);
        anyKey(false);
    }

    /**
     * Adicionar uma Loja a um Centro Comercial. A loja e todos os seus valores são escolhidos pelo utilizador.
     *
     * @param c Centro Comercial existente.
     */
    private static void adicionarLoja(CentroComercial c)
    {
        line();
        System.out.println("[1] - Loja Ancora");
        System.out.println("[2] - Loja Comum");

        int esc = sc.nextInt();

        if (esc == 1)
        {
            System.out.println("[1] - Ancora Propria");
            System.out.println("[2] - Ancora Externa");

            int esc2 = sc.nextInt();

            switch (esc2)
            {
                case 1:
                    AncoraPropria loja = new AncoraPropria();
                    popularValoresComuns(loja);
                    popularSeguranca(loja);
                    lojaCriarSucesso(c, loja);
                    break;
                case 2:
                    AncoraExterna loja2 = new AncoraExterna();
                    popularValoresComuns(loja2);
                    popularFuncionarios(loja2);
                    popularSeguranca(loja2);
                    lojaCriarSucesso(c, loja2);
                    break;
                default:
                    sc.nextLine();
                    noOpErro();
                    break;
            }
        } else if (esc == 2)
        {
            System.out.println("[1] - Loja Quiosque");
            System.out.println("[2] - Loja Restauração");

            int esc2 = sc.nextInt();

            switch (esc2)
            {
                case 1:
                    LojaQuiosque loja = new LojaQuiosque();
                    popularValoresComuns(loja);
                    popularFuncionarios(loja);
                    lojaCriarSucesso(c, loja);
                    break;
                case 2:
                    LojaRestauracao loja2 = new LojaRestauracao();
                    popularValoresComuns(loja2);
                    popularFuncionarios(loja2);
                    popularRestauracao(loja2);
                    lojaCriarSucesso(c, loja2);
                    break;
                default:
                    sc.nextLine();
                    noOpErro();
                    break;
            }
        } else
        {
            sc.nextLine();
            noOpErro();
        }
    }

    /**
     * Permite a pesquisa e eliminação de uma Loja existente no Centro Comercial.
     *
     * @param c Centro Comercial existente.
     */
    private static void removerLoja(CentroComercial c)
    {
        line();
        if (c.getTotalLojas() == 0)
        {
             semLojasErro();
        }
        else
        {
            for (int i = 0; i < c.getTotalLojas(); i++)
            {
                Loja j = c.obterLoja(i);
                System.out.println("ID [" + j.getId() + "]" + " - " + j.getNome());
            }

            line();
            System.out.print("Escreve o ID da loja a remover: ");
            if (c.removerLojaID(sc.nextInt()))
            {
                System.out.println("Loja removida com sucesso.");
                anyKey(true);
            } else
            {
                System.out.println("Erro. Loja com esse ID nao foi encontrada.");
                anyKey(true);
            }
        }
    }

    /**
     * Método intermédio para o utilizador popular os 3 valores comuns entre todos os tipos de Lojas.
     *
     * @param j uma Loja
     */
    private static void popularValoresComuns(Loja j)
    {
        sc.nextLine();
        System.out.print("Nome da Loja: ");
        j.setNome(sc.nextLine());
        System.out.print("Area da Loja: ");
        j.setArea(sc.nextInt());
        System.out.print("Receitas da Loja: ");
        j.setReceitas(sc.nextFloat());
    }

    /**
     * Método intermédio para o utilizador popular os valores relativos às LojasRestauração.
     *
     * @param l uma LojaRestauração
     */
    private static void popularRestauracao(LojaRestauracao l)
    {
        System.out.print("Numero de mesas: ");
        l.setNumMesas(sc.nextInt());
        System.out.print("Custo de manutencao: ");
        l.setCustoManutencao(sc.nextInt());
    }

    /**
     * Método intermédio para o utilizador popular com os valores relativos ao número de funcionários.
     *
     * @param c uma Loja que seja subclass de ComFuncionarios.
     */
    private static void popularFuncionarios(ComFuncionarios c)
    {
        System.out.print("Numero de funcionarios: ");
        c.setNumeroFuncionarios(sc.nextInt());
    }

    /**
     * Método intermédio para o utilizador popular com os valores relativos ao custo de segurança.
     *
     * @param p uma AncoraPropria
     */
    private static void popularSeguranca(AncoraPropria p)
    {
        System.out.print("Custo de seguranca: ");
        p.setCustoSeguranca(sc.nextFloat());
    }

    /**
     * Método intermédio para o utilizador popular com os valores relativos ao custo de segurança.
     *
     * @param p uma AncoraPropria
     */
    private static void popularSeguranca(AncoraExterna p)
    {
        System.out.print("Custo de seguranca: ");
        p.setCustoSeguranca(sc.nextFloat());
    }

    /**
     * Permite a alteração dos valores referentes a um CentroComercial.
     *
     * @param c CentroComercial existente.
     */
    private static void editarCentroComercial(CentroComercial c)
    {
        line();
        System.out.println("[1] - Alterar Nome (Atualmente " + c.getNome() + ")");
        System.out.println("[2] - Alterar Morada (Atualmente " + c.getMorada() + ")");

        int esc = sc.nextInt();
        sc.nextLine();

        switch (esc)
        {
            case 1:
                System.out.print("Novo nome: ");
                c.setNome(sc.nextLine());
                System.out.println("Nome atualizado com sucesso.");
                anyKey(false);
                break;
            case 2:
                System.out.print("Nova morada: ");
                c.setMorada(sc.nextLine());
                System.out.println("Morada atualizada com sucesso.");
                anyKey(false);
                break;
            default:
                noOpErro();
                break;
        }
    }

    /**
     * Permite a criação de um novo CentroComercial.
     *
     * @return um novo CentroComercial
     */
    private static CentroComercial criarCentroComercial()
    {
        CentroComercial c = new CentroComercial();

        line();
        sc.nextLine();
        System.out.print("Nome do Centro Comercial: ");
        c.setNome(sc.nextLine());
        System.out.print("Morada do Centro Comercial: ");
        c.setMorada(sc.nextLine());
        return c;
    }

    /**
     * Tenta carregar através de um ficheiro um CentroComercial.
     *
     * @return o CentroComercial que carregou, ou null se não conseguiu carregar.
     * @throws FileNotFoundException erro de leitura de ficheiro
     */
    private static CentroComercial carregarCentroComercial() throws FileNotFoundException
    {
        CentroComercial c;

        sc.nextLine();
        System.out.print("Nome do ficheiro: ");

        currentFile = sc.nextLine();

        c = FileManager.loadFile(currentFile);

        if (c == null)
        {
            System.out.println("O ficheiro que introduziu nao foi encontrado.");
            currentFile = "";
        } else
        {
            System.out.println("Ficheiro carregado com sucesso.");
        }
        anyKey(false);

        return c;
    }

    /**
     * Método para pausar o terminal.
     *
     * @param clear limpar lixo do scanner se necessário
     */
    private static void anyKey(boolean clear)
    {
        if (clear)
        {
            sc.nextLine();
        }
        System.out.println("Prima enter para continuar...");
        sc.nextLine();
    }

    /**
     * Desenha uma bela linha no terminal.
     */
    private static void line()
    {
        System.out.println("--------------------------------");
    }

    /**
     * Escreve no terminal uma mensagem de erro.
     */
    private static void noOpErro()
    {
        System.out.println("Erro. Operação não existe.");
        anyKey(false);
    }

    /**
     * Adiciona uma nova Loja e escreve no terminal uma indicação de sucesso.
     *
     * @param c CentroComercial existente
     * @param j uma Loja para adicionar
     */
    private static void lojaCriarSucesso(CentroComercial c, Loja j)
    {
        c.adicionarLoja(j);
        System.out.println("Loja criada com sucesso.");
        anyKey(true);
    }

    /**
     * Método intermédio para alterar as variáveis comuns de uma Loja.
     * @param variavelLoja opção escolhida.
     * @param x uma Loja.
     */
    private static void opcoesEditarLojaGeral(int variavelLoja, Loja x)
    {
        if (variavelLoja == 1)
        {
            System.out.println("Introduza o novo nome");
            sc.nextLine();
            String nome = sc.nextLine();
            x.setNome(nome);
            System.out.println("Nome atualizado com sucesso!");
        } else if (variavelLoja == 2)
        {
            System.out.println("Introduza nova Área");
            int area = sc.nextInt();
            x.setArea(area);
            System.out.println("Área atualizada com sucesso!");
        } else if (variavelLoja == 3)
        {
            System.out.println("Introduza novo valor de receitas");
            float receitas = sc.nextFloat();
            x.setReceitas(receitas);
        }
    }

    /**
     * Método intermédio para alterar o valor de funcionários de uma Loja existente.
     * @param variavelLoja opção selecionada
     * @param x uma Loja.
     */
    private static void opcoesEditarLojaComFunc(int variavelLoja, ComFuncionarios x)
    {
        if (variavelLoja == 4)
        {
            System.out.println("Introduza novo número de Funcionários:");
            int nrFunc = sc.nextInt();
            x.setNumeroFuncionarios(nrFunc);
            System.out.println("Número de funcionários atiualizado com sucesso!");
        }
    }

    /**
     * Método intermédio para escrever todas as variáveis de uma Loja existente.
     * @param l uma Loja.
     */
    private static void escreverVariaveisDaLoja(Loja l)
    {
        System.out.printf("[0] Sair\n[1] Nome: %s\n[2] Área: %d\n[3] Receitas: %.2f\n", l.getNome(), l.getArea(), l.getReceitas());
        if (l instanceof AncoraPropria)
        {
            System.out.printf("[4] Custo de Segurança: %.2f", ((AncoraPropria) l).getCustoSeguranca());
        } else if (l instanceof ComFuncionarios)
        {
            System.out.printf("[4] Número de Funcionários: %d", ((ComFuncionarios) l).getNumeroFuncionarios());
            if (l instanceof AncoraExterna)
            {
                System.out.printf("\n[5] Custo de Segurança: %.2f", ((AncoraExterna) l).getCustoSeguranca());
            } else if (l instanceof LojaRestauracao)
            {
                System.out.printf("\n[5] Número de mesas: %d\n[6] Custo de Manutenção: %.2f", ((LojaRestauracao) l).getNumMesas(), ((LojaRestauracao) l).getCustoManutencao());
            }
        }
    }

    /**
     * Lista todas as lojas ou lojas de uma certa categoria.
     * @param centro CentroComercial existente.
     */
    private static void listarLojas(CentroComercial centro)
    {
        boolean sairListarLojas = false;
        while (!sairListarLojas)
        {
            if (centro.getTotalLojas() > 0)
            {
                System.out.println("Que tipo de lojas deseja listar?");
                System.out.println("[1] Lojas Âncora");
                System.out.println("[2] Lojas Comuns");
                System.out.println("[3] Todas");
                System.out.println("[0] Sair");
                int escolhaListarLojas = sc.nextInt();
                if (escolhaListarLojas == 1)
                {
                    if (centro.getLojasAncora() > 0)
                    {
                        Printer.listarLojasAncora(centro);
                    } else
                    {
                        System.out.println("Não há Lojas Âncora registadas!");
                    }
                    anyKey(true);
                } else if (escolhaListarLojas == 2)
                {
                    if (centro.getLojasComuns() > 0)
                    {
                        Printer.listarLojasComuns(centro);
                    }
                    else
                    {
                        System.out.println("Não há Lojas Comuns registadas!");
                    }
                    anyKey(true);
                } else if (escolhaListarLojas == 3)
                {
                    Printer.listarLojasTodas(centro);
                    anyKey(true);
                } else if (escolhaListarLojas == 0)
                {
                    sairListarLojas = true;
                } else
                {
                    System.out.println("Opção Inválida!");
                }
            } else
            {
                semLojasErro();
                sairListarLojas = true;
            }
        }
    }

    /**
     * Edita os valores de uma loja existente num Centro Comercial
     * @param centro Centro Comercial existente.
     */
    private static void editarLoja(CentroComercial centro)
    {
        if (centro.getTotalLojas ()>0)
        {
            Printer.listarLojasSoNome (centro);
            System.out.println ("Introduza o ID da loja a editar:");
            int idLoja = sc.nextInt ();
            Loja x = centro.encontrarLojaID (idLoja);
            if (x != null)
            {
                boolean sairEditarLoja = false;
                while (! sairEditarLoja)
                {
                    escreverVariaveisDaLoja (x);
                    System.out.println ("\nEscolha a característica a editar:");
                    int variavelLoja = sc.nextInt ();

                    if (variavelLoja == 0)
                    {
                        sairEditarLoja = true;
                    } else
                    {
                        opcoesEditarLojaGeral (variavelLoja, x);
                        if (x instanceof AncoraPropria)
                        {
                            if (variavelLoja == 4)
                            {
                                System.out.println ("Introduza novo Custo de Segurança:");
                                float cs = sc.nextFloat ();
                                ((AncoraPropria) x).setCustoSeguranca (cs);
                                System.out.println ("Custo de Segurança atualizado com sucesso!");
                            }
                        } else if (x instanceof ComFuncionarios)
                        {
                            opcoesEditarLojaComFunc (variavelLoja, (ComFuncionarios) x);
                            if (x instanceof AncoraExterna)
                            {
                                if (variavelLoja == 5)
                                {
                                    System.out.println ("Introduza novo Custo de Segurança:");
                                    float cs = sc.nextFloat ();
                                    ((AncoraExterna) x).setCustoSeguranca (cs);
                                    System.out.println ("Custo de Segurança atualizado com sucesso!");
                                }
                            } else if (x instanceof LojaRestauracao)
                            {
                                if (variavelLoja == 5)
                                {
                                    System.out.println ("Introduza novo número de mesas");
                                    int numMesas = sc.nextInt ();
                                    ((LojaRestauracao) x).setNumMesas (numMesas);
                                    System.out.println ("Número de mesas atualizado com sucesso!");
                                } else if (variavelLoja == 6)
                                {
                                    System.out.println ("Introduza novo Custo de Manutenção");
                                    float cManu = sc.nextFloat ();
                                    ((LojaRestauracao) x).setCustoManutencao (cManu);
                                    System.out.println ("Custo de Manutenção atualizado com sucesso!");
                                }
                            }
                        }
                    }
                }
            }
            else
            {
                System.out.println("Erro. Loja com esse ID não existe.");
                anyKey(true);
            }
        }
        else
        {
           semLojasErro();
        }
    }

    /**
     * Escreve no terminal uma mensagem de erro.
     */
    private static void semLojasErro()
    {
        System.out.println("Não há lojas registadas neste Centro Comercial!");
        anyKey(false);
    }
}