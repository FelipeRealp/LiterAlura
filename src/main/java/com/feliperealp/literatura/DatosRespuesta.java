package com.feliperealp.literatura;

import com.feliperealp.literatura.libro.DatosLibro;

import java.util.List;

public record DatosRespuesta(
         List<DatosLibro> results
) {
}
