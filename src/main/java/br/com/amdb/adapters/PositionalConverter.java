package br.com.amdb.adapters;

import br.com.amdb.domain.core.ConverterToClassCore;
import br.com.amdb.domain.core.ConverterToStringCore;

public class PositionalConverter {

    private final ConverterToClassCore converterToClassCore;
    private final ConverterToStringCore converterToStringCore;

    public PositionalConverter() {
        this.converterToClassCore = new ConverterToClassCore();
        this.converterToStringCore = new ConverterToStringCore();
    }

    public <T> String converter(T data) {
        return converterToStringCore.converter(data);
    }

    public <T> T converter(String transcode, Class<T> dataClass) {
        return converterToClassCore.converter(transcode, dataClass);
    }
}
