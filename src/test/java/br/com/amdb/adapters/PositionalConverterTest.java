package br.com.amdb.adapters;

import br.com.amdb.utils.ProviderTestUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionalConverterTest {

    @Test
    public void mustConverterToString() {
        ProviderTestUtils.Foo foo = ProviderTestUtils.getFoo();

        PositionalConverter positionalConverter = new PositionalConverter();

        String result = positionalConverter.converter(foo);

        final String expected = "test1     000000000100000002.00000000010000000000300000004.02023-05-20      bar1baz1000000baz2000000      bar2baz1000000baz2000000";
        assertEquals(expected, result);
    }

    @Test
    public void mustGenerateExceptionByAnnotationNotFound() {
        ProviderTestUtils.Qux qux = new ProviderTestUtils.Qux();
        qux.setFoo("test");

        PositionalConverter positionalConverter = new PositionalConverter();

        RuntimeException exception = assertThrows(RuntimeException.class, ()->positionalConverter.converter(qux));
        assertTrue(exception.getCause().getMessage().contains("@Position annotation not present in class"));
        assertTrue(exception.getMessage().contains("Error converting to String"));
    }

    @Test
    public void mustConverterToObject() {
        //final String transcode = "test1     000000000100000002.00000000010000000000300000004.02023-05-20      bar1baz1000000baz2000000      bar2baz1000000baz2000000";
        PositionalConverter positionalConverter = new PositionalConverter();
        final String transcode = "      bar1baz1000000baz2000000      bar2baz1000000baz2000000";
        ProviderTestUtils.Foo.Bar foo = positionalConverter.converter(transcode, ProviderTestUtils.Foo.Bar.class);
        System.out.println("Aqui "+ foo);
    }

    @Test
    public void mustGenerateExceptionByObjectIsNull() {

        PositionalConverter positionalConverter = new PositionalConverter();

        RuntimeException exception = assertThrows(RuntimeException.class, ()->positionalConverter.converter(null));
        assertTrue(exception.getCause().getMessage().contains("The object to converter is null"));
        assertTrue(exception.getMessage().contains("Error converting to String"));
    }
}