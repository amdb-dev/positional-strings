package br.com.amdb.adapters;

import br.com.amdb.domain.annotation.Positional;
import br.com.amdb.domain.enumeration.Filler;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PositionalConverterTest {

    @Test
    public void mustConverterToString() {
        Foo foo = getFoo();

        PositionalConverter positionalConverter = new PositionalConverter();

        String result = positionalConverter.converter(foo);

        final String expected = "test1     000000000100000002.00000000010000000000300000004.0      bar1baz1000000baz2000000      bar2baz1000000baz2000000";
        assertEquals(expected, result);
    }

    @Test
    public void mustGenerateExceptionByAnnotationNotFound() {
        Qux qux = new Qux();
        qux.setFoo("test");

        PositionalConverter positionalConverter = new PositionalConverter();

        RuntimeException exception = assertThrows(RuntimeException.class, ()->positionalConverter.converter(qux));
        assertTrue(exception.getCause().getMessage().contains("@Position annotation not present in class"));
        assertTrue(exception.getMessage().contains("Error converting to String"));
    }

    @Test
    public void mustGenerateExceptionByObjectIsNull() {

        PositionalConverter positionalConverter = new PositionalConverter();

        RuntimeException exception = assertThrows(RuntimeException.class, ()->positionalConverter.converter(null));
        assertTrue(exception.getCause().getMessage().contains("The object to converter is null"));
        assertTrue(exception.getMessage().contains("Error converting to String"));
    }

    private static Foo getFoo() {
        Foo foo = new Foo();
        foo.setFoo("test1");
        foo.setBar(1);
        foo.setBaz(2.0);
        foo.setQux(BigDecimal.TEN);
        foo.setFoobar(3);
        foo.setFoobaz(4.0);

        List<Foo.Bar> barList = new ArrayList<>();
        List<Foo.Baz> bazList = new ArrayList<>();

        Foo.Baz baz1 = new Foo.Baz();
        baz1.setFoo("baz1");

        Foo.Baz baz2 = new Foo.Baz();
        baz2.setFoo("baz2");

        bazList.add(baz1);
        bazList.add(baz2);


        Foo.Bar bar1 = new Foo.Bar();
        bar1.setFoo("bar1");
        bar1.setBazList(bazList);

        Foo.Bar bar2 = new Foo.Bar();
        bar2.setFoo("bar2");
        bar2.setBazList(bazList);

        barList.add(bar1);
        barList.add(bar2);

        foo.setBarList(barList);
        return foo;
    }

    @Getter
    @Setter
    public static class Foo {

        @Positional(length = 10, filler = Filler.SPACES_RIGTH)
        private String foo;

        @Positional(length = 10, filler = Filler.ZEROS_LEFT)
        private Integer bar;

        @Positional(length = 10, filler = Filler.ZEROS_LEFT)
        private Double baz;

        @Positional(length = 10, filler = Filler.ZEROS_LEFT)
        private BigDecimal qux;

        @Positional(length = 10, filler = Filler.ZEROS_LEFT)
        private int foobar;

        @Positional(length = 10, filler = Filler.ZEROS_LEFT)
        private double foobaz;

        @Positional(isCollection = true)
        private List<Bar> barList;

        @Getter
        @Setter
        public static class Bar {
            @Positional(length = 10, filler = Filler.SPACES_LEFT)
            private String foo;

            @Positional(isCollection = true)
            private List<Baz> bazList;
        }

        @Getter
        @Setter
        public static class Baz {
            @Positional(length = 10, filler = Filler.ZEROS_RIGTH)
            private String foo;
        }
    }

    @Getter
    @Setter
    public static class Qux {
        public String foo;
    }
}