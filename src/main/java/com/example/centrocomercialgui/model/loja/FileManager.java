package com.example.centrocomercialgui.model.loja;

import com.example.centrocomercialgui.model.comparators.SortId;
import javafx.util.Pair;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.text.Normalizer;
import java.util.*;

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

    public static final String TXT_FORMAT = ".txt";
    public static final String CENTRO_FORMAT = ".ccf";

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
                case CENTRO -> {
                    if (tokens.length != CENTRO_PARAM)
                    {
                        System.out.println(CENTRO + " nao tem parâmetros corretos");
                    } else
                    {
                        centro.setNome(tokens[2]);
                        centro.setMorada(tokens[4]);
                    }
                }

                case QUIOSQUE, RESTAURANTE, ANCORAEX, ANCORAPRO -> {
                    //parse dos 4 tipos de lojas
                    Loja l = parseLoja(tokens[0],tokens,true);
                    centro.adicionarLoja(l);
                }

                //parse dos valores static
                case LOJAAP -> {
                    if (tokens.length != STATICVARS_PARAM)
                    {
                        System.out.println("valor estatico incorreto");
                    } else
                    {
                        Loja.setAreaPequeno(Integer.parseInt(tokens[1]));
                    }
                }
                case LOJAAG -> {
                    if (tokens.length != STATICVARS_PARAM)
                    {
                        System.out.println("valor estatico incorreto");
                    } else
                    {
                        Loja.setAreaGrande(Integer.parseInt(tokens[1]));
                    }
                }
                case RENDAAE -> {
                    if (tokens.length != STATICVARS_PARAM)
                    {
                        System.out.println("valor estatico incorreto");
                    } else
                    {
                        AncoraExterna.setRendaFixa(Float.parseFloat(tokens[1]));
                    }
                }
                case RENDAQ -> {
                    if (tokens.length != STATICVARS_PARAM)
                    {
                        System.out.println("valor estatico incorreto");
                    } else
                    {
                        LojaQuiosque.setRenda(Float.parseFloat(tokens[1]));
                    }
                }
                case RENDARES -> {
                    if (tokens.length != STATICVARS_PARAM)
                    {
                        System.out.println("valor estatico incorreto");
                    } else
                    {
                        LojaRestauracao.setRendaFixa(Float.parseFloat(tokens[1]));
                    }
                }
                case VALORMRES -> {
                    if (tokens.length != STATICVARS_PARAM)
                    {
                        System.out.println("valor estatico incorreto");
                    } else
                    {
                        LojaRestauracao.setValorPorMesa(Float.parseFloat(tokens[1]));
                    }
                }
                //caso default
                default -> {
                    if (!tokens[0].startsWith("<") && tokens[0].length() > 0)
                    {
                        System.out.println("Tipo invalido " + tokens[0]);
                    }
                }
            }
        }

        sc.close();
        return centro;
    }

    private static Loja parseLoja(String input, String[] tokens, boolean getId)
    {
        switch (input)
        {
            case QUIOSQUE:
                if (tokens.length != QUIOSQUE_PARAM)
                {
                    System.out.println("tipo " + QUIOSQUE + " nao tem parâmetros corretos");
                } else
                {
                    if (getId)
                    {
                        return new LojaQuiosque(Integer.parseInt(tokens[2]), tokens[4], Integer.parseInt(tokens[6]),
                                Float.parseFloat(tokens[8]), Integer.parseInt(tokens[10]), TipoLoja.QUIOSQUE);
                    }
                    else
                    {
                        return new LojaQuiosque(tokens[4], Integer.parseInt(tokens[6]),
                                Float.parseFloat(tokens[8]), Integer.parseInt(tokens[10]));
                    }
                }
                break;
            case RESTAURANTE:
                if (tokens.length != RESTAURACAO_PARAM)
                {
                    System.out.println("tipo " + RESTAURANTE + " nao tem parâmetros corretos");
                } else
                {
                    if (getId)
                    {
                        return new LojaRestauracao(Integer.parseInt(tokens[2]), tokens[4], Integer.parseInt(tokens[6]),
                                Float.parseFloat(tokens[8]), Integer.parseInt(tokens[10])
                                , Integer.parseInt(tokens[12]), Float.parseFloat(tokens[14]), TipoLoja.RESTAURANTE);
                    }
                    else
                    {
                        return new LojaRestauracao(tokens[4], Integer.parseInt(tokens[6]),
                                Float.parseFloat(tokens[8]), Integer.parseInt(tokens[10])
                                , Integer.parseInt(tokens[12]), Float.parseFloat(tokens[14]));
                    }
                }
                break;
            case ANCORAEX:
                if (tokens.length != ANCORAEX_PARAM)
                {
                    System.out.println("tipo " + ANCORAEX + " nao tem parâmetros corretos");
                } else
                {
                    if (getId)
                    {
                        return new AncoraExterna(Integer.parseInt(tokens[2]), tokens[4], Integer.parseInt(tokens[6]),
                                Float.parseFloat(tokens[8]), Integer.parseInt(tokens[10]), Float.parseFloat(tokens[12]), TipoLoja.ANCORA_EXTERNA);
                    }
                    else
                    {
                        return new AncoraExterna(tokens[4], Integer.parseInt(tokens[6]),
                                Float.parseFloat(tokens[8]), Integer.parseInt(tokens[10]), Float.parseFloat(tokens[12]));
                    }
                }
                break;
            case ANCORAPRO:
                if (tokens.length != ANCORAPRO_PARAM)
                {
                    System.out.println("tipo " + ANCORAPRO + " nao tem parâmetros corretos");
                } else
                {
                    if (getId)
                    {
                        return new AncoraPropria(Integer.parseInt(tokens[2]), tokens[4], Integer.parseInt(tokens[6]),
                                Float.parseFloat(tokens[8]), Float.parseFloat(tokens[10]), TipoLoja.ANCORA_PROPRIA);
                    }
                    else
                    {
                        return new AncoraPropria(tokens[4], Integer.parseInt(tokens[6]),
                                Float.parseFloat(tokens[8]), Float.parseFloat(tokens[10]));
                    }
                }
                break;
        }

        return null;
    }

    public static List<Loja> loadLojas(String filename) throws FileNotFoundException
    {
        List<Loja> lojas = new ArrayList<>();

        Scanner sc = new Scanner(new File(filename));

        while (sc.hasNextLine())
        {
            String[] tokens = sc.nextLine().split("\\W*;\\W*");

            if (tokens.length > 1)
            {
                lojas.add(parseLoja(tokens[0],tokens,false));
            }
        }

        return lojas;
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

    public static void saveLojasBinary(File folder, String filename, CentroComercial centro, boolean saveExt, boolean savePro, boolean saveQui, boolean saveRes) throws IOException
    {
        Path filePath = Path.of(folder.getAbsolutePath(),filename);

        List<Pair<TipoLoja,List<Loja>>> listLojas = new ArrayList<>();

        if (saveExt)
        {
            listLojas.add(new Pair<>(TipoLoja.ANCORA_EXTERNA,centro.getLojasTipo(TipoLoja.ANCORA_EXTERNA)));
        }
        if (savePro)
        {
            listLojas.add(new Pair<>(TipoLoja.ANCORA_PROPRIA,centro.getLojasTipo(TipoLoja.ANCORA_PROPRIA)));
        }
        if (saveQui)
        {
            listLojas.add(new Pair<>(TipoLoja.QUIOSQUE,centro.getLojasTipo(TipoLoja.QUIOSQUE)));

        }
        if (saveRes)
        {
            listLojas.add(new Pair<>(TipoLoja.RESTAURANTE,centro.getLojasTipo(TipoLoja.RESTAURANTE)));
        }

        for(Pair<TipoLoja,List<Loja>> p : listLojas)
        {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath+p.getKey().name()+CENTRO_FORMAT));
            oos.writeObject(p.getValue());
            oos.close();
        }
    }

    public static void saveLojas(File folder, String filename, CentroComercial centro, boolean saveExt, boolean savePro, boolean saveQui, boolean saveRes) throws IOException
    {
        Path filePath = Path.of(folder.getAbsolutePath(),filename);

        List<Pair<Class<? extends Loja>, Formatter>> fileList = new ArrayList<>();

        if (saveExt)
        {
            fileList.add(new Pair<>(AncoraExterna.class,new Formatter(filePath+"Ext.txt")));
        }
        if (savePro)
        {
            fileList.add(new Pair<>(AncoraPropria.class,new Formatter(filePath+"Pro.txt")));
        }
        if (saveQui)
        {
            fileList.add(new Pair<>(LojaQuiosque.class,new Formatter(filePath+"Qui.txt")));
        }
        if (saveRes)
        {
            fileList.add(new Pair<>(LojaRestauracao.class,new Formatter(filePath+"Res.txt")));
        }


        //Save do estado das lojas
        int maxName = getNomeMaior(centro);
        for(int i = 0 ; i < centro.getTotalLojas(); i++)
        {
            Loja j = centro.obterLoja(i);
            if (j instanceof LojaQuiosque && containsClass(fileList,j.getClass()))
            {
                getFormatter(fileList,j.getClass()).format((i>0?"\n":"")+QUIOSQUE+"    ; ID ; %4d ; NOME ; %"+maxName+"s ; AREA ; %4d ; RECEITAS ; %10.2f ; NUMERO FUNCIONARIOS ; %3d",j.getId(), j.getNome(), j.getArea(), j.getReceitas(), ((LojaQuiosque) j).getNumeroFuncionarios());
            }
            else if (j instanceof LojaRestauracao && containsClass(fileList,j.getClass()))
            {
                getFormatter(fileList,j.getClass()).format((i>0?"\n":"")+RESTAURANTE+" ; ID ; %4d ; NOME ; %"+maxName+"s ; AREA ; %4d ; RECEITAS ; %10.2f ; NUMERO FUNCIONARIOS ; %3d ; NUMERO MESAS ; %2d ; CUSTO MANUTENCAO ; %5.2f",j.getId(),j.getNome(), j.getArea(), j.getReceitas(), ((LojaRestauracao) j).getNumeroFuncionarios(), ((LojaRestauracao) j).getNumMesas(), ((LojaRestauracao) j).getCustoManutencao());
            }
            else if (j instanceof AncoraExterna && containsClass(fileList,j.getClass()))
            {
                getFormatter(fileList,j.getClass()).format((i>0?"\n":"")+ANCORAEX+"   ; ID ; %4d ; NOME ; %"+maxName+"s ; AREA ; %4d ; RECEITAS ; %10.2f ; NUMERO FUNCIONARIOS ; %3d ; CUSTO SEGURANCA ; %5.2f",j.getId(),j.getNome(), j.getArea(), j.getReceitas(), ((AncoraExterna) j).getNumeroFuncionarios(), ((AncoraExterna) j).calcularCustoSeguranca());
            }
            else if (j instanceof AncoraPropria && containsClass(fileList,j.getClass()))
            {
                getFormatter(fileList,j.getClass()).format((i>0?"\n":"")+ANCORAPRO+"   ; ID ; %4d ; NOME ; %"+maxName+"s ; AREA ; %4d ; RECEITAS ; %10.2f ; CUSTO SEGURANCA ; %5.2f",j.getId(),j.getNome(),j.getArea(),j.getReceitas(),((AncoraPropria) j).calcularCustoSeguranca());
            }
        }

        for(Pair<Class<? extends Loja>, Formatter> pair : fileList)
        {
            pair.getValue().close();
        }
    }

    private static Formatter getFormatter(List<Pair<Class<? extends Loja>, Formatter>> fileList, Class<? extends Loja> clazz)
    {
        for(Pair<Class<? extends Loja>, Formatter> p : fileList)
        {
            if (p.getKey().equals(clazz))
            {
                return p.getValue();
            }
        }

        return null;
    }

    private static boolean containsClass(List<Pair<Class<? extends Loja>, Formatter>> fileList, Class<? extends Loja> clazz)
    {
        for(Pair<Class<? extends Loja>, Formatter> p : fileList)
        {
            if (p.getKey().equals(clazz))
            {
                return true;
            }
        }

        return false;
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

    public static void saveBinaryFile(File nome, CentroComercial centro)
    {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nome)))
        {
            oos.writeObject(centro);

            oos.writeInt(Loja.getAreaPequeno());
            oos.writeInt(Loja.getAreaGrande());
            oos.writeInt(Loja.getTotalLojas());
            oos.writeInt(Comum.getContagem());

            oos.writeFloat(AncoraExterna.getRendaFixa());
            oos.writeFloat(LojaQuiosque.getRenda());
            oos.writeFloat(LojaRestauracao.getRendaFixa());
            oos.writeFloat(LojaRestauracao.getValorPorMesa());
        }
        catch (IOException e)
        {
            System.out.println("Erro ao gravar ficheiro binario: "+e.getMessage());
        }
    }

    public static CentroComercial openBinaryFile(File f)
    {
        CentroComercial c = null;

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f)))
        {
            c = (CentroComercial) ois.readObject();

            Loja.setAreaPequeno(ois.readInt());
            Loja.setAreaGrande(ois.readInt());
            Loja.setTotalLojas(ois.readInt());
            Comum.setContagem(ois.readInt());

            AncoraExterna.setRendaFixa(ois.readFloat());
            LojaQuiosque.setRenda(ois.readFloat());
            LojaRestauracao.setRendaFixa(ois.readFloat());
            LojaRestauracao.setValorPorMesa(ois.readFloat());
        }
        catch (IOException | ClassNotFoundException e)
        {
            System.out.println("Erro ao abrir ficheiro binario: "+e.getMessage());
        }

        return c;
    }
}