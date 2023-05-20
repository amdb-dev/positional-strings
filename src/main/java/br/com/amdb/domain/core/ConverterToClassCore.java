package br.com.amdb.domain.core;

public class ConverterToClassCore {

    private final UtilsCore utilsCore;

    public ConverterToClassCore() {
        this.utilsCore = new UtilsCore();
    }

    public <T> T converter(String transcode, Class<T> dataClass) {
        try {
            return null;
        }catch (Throwable t) {
            throw new RuntimeException("Error converting to Class", t);
        }
    }
}
