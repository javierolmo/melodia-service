package com.javi.uned.pfgbackend.domain.instrument;

import com.javi.uned.melodiacore.model.Instrumento;
import com.javi.uned.melodiacore.model.constants.Instrumentos;
import org.springframework.stereotype.Service;

@Service
public class InstrumentService {

    public Instrumento completarInstrumento(Instrumento instrumentoIncompleto){
        for(Instrumento instrument : Instrumentos.getInstrumentos()){
            if(instrument.getRef().equals(instrumentoIncompleto.getRef())) return instrument;
        }
        throw new IllegalArgumentException("No se ha podido encontrar el instrumento "+instrumentoIncompleto.getRef());
    }
    
}
