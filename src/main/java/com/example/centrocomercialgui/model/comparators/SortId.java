package com.example.centrocomercialgui.model.comparators;

import com.example.centrocomercialgui.model.loja.Loja;

import java.util.Comparator;

/**
 * Classe que implementa a interface Comparator do tipo Loja e que compara o ID dos 2 elementos.
 */
public class SortId implements Comparator<Loja>
{
    @Override
    public int compare(Loja o1, Loja o2)
    {
        return o1.getId()-o2.getId();
    }
}
