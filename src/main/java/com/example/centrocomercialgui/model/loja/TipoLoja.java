package com.example.centrocomercialgui.model.loja;

public enum TipoLoja
{
    ANCORA_EXTERNA
            {
                @Override
                public String toString()
                {
                    return "Ancora Externa";
                }
            },
    ANCORA_PROPRIA
            {
                @Override
                public String toString()
                {
                    return "Ancora Própria";
                }
            },
    RESTAURANTE
            {
                @Override
                public String toString()
                {
                    return "Restaurante";
                }
            },
    QUIOSQUE
            {
                @Override
                public String toString()
                {
                    return "Quiosque";
                }
            },
    OUTRO
            {
                @Override
                public String toString()
                {
                    return "Outro";
                }
            }
}
