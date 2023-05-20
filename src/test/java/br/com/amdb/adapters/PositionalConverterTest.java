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
        Foo foo = new Foo();
        foo.setFoo("test1");
        foo.setBar(1);
        foo.setBaz(2.0);
        foo.setQux(BigDecimal.TEN);
        foo.setFoobar(3);
        foo.setFoobaz(4.0);

        List<Foo.Bar> barList = new ArrayList<>();
        Foo.Bar bar1 = new Foo.Bar();
        bar1.setFoo("bar1");

        Foo.Bar bar2 = new Foo.Bar();
        bar2.setFoo("bar2");

        barList.add(bar1);
        barList.add(bar2);

        foo.setBarList(barList);

        PositionalConverter positionalConverter = new PositionalConverter();

        String result = positionalConverter.converter(foo);
        System.out.println("resultado aqui"+result);
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
        }
    }
}