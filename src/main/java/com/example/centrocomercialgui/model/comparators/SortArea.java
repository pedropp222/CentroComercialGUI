package com.example.centrocomercialgui.model.comparators;

import java.util.Comparator;

import com.example.centrocomercialgui.model.loja.Loja;

/**
 * Classe que implementa a interface Comparator do tipo Loja e que compara as áreas dos 2 elementos.
 */
public class SortArea implements Comparator<Loja>
{
    @Override
    public int compare(Loja o1, Loja o2)
    {
        return o1.getArea() - o2.getArea();
    }
}