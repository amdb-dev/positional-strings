package br.com.amdb.adapters;

import br.com.amdb.utils.ProviderTestUtils;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class PositionalConverterTest {

    @Test
    public void mustConverterToString() {
        ProviderTestUtils.Foo foo = ProviderTestUtils.getFoo();

        PositionalConverter positionalConverter = new PositionalConverter();
        String result = positionalConverter.converter(foo);

        final String expected = "test1     000000000100000002.00000000010000000000300000004.02024-01-01      bar1baz1000000baz2000000      bar2baz1000000baz2000000";
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
        final String transcode = "test1     000000000100000002.00000000010000000000300000004.02024-01-01      bar1baz1000000baz2000000      bar2baz1000000baz2000000";
        PositionalConverter positionalConverter = new PositionalConverter();

        ProviderTestUtils.Foo foo = positionalConverter.converter(transcode, ProviderTestUtils.Foo.class);
        assertEquals( "test1", foo.getFoo());
        assertEquals(2, foo.getBarList().size());
        foo.getBarList().forEach(bar -> {
            assertEquals(2, bar.getBazSet().size());
            assertEquals(1, bar.getBazSet().stream().filter(baz -> baz.getFoo().contains("baz1")).collect(Collectors.toSet()).size());
            assertEquals(1, bar.getBazSet().stream().filter(baz -> baz.getFoo().contains("baz2")).collect(Collectors.toSet()).size());
        });
    }

    @Test
    public void mustGenerateExceptionByObjectIsNull() {
        PositionalConverter positionalConverter = new PositionalConverter();
        RuntimeException exception = assertThrows(RuntimeException.class, ()->positionalConverter.converter(null));
        assertTrue(exception.getCause().getMessage().contains("The object to converter is null"));
        assertTrue(exception.getMessage().contains("Error converting to String"));
    }
}