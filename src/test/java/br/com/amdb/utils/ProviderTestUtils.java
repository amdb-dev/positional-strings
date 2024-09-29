package br.com.amdb.utils;

import br.com.amdb.domain.annotation.Positional;
import br.com.amdb.domain.enumeration.Filler;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ProviderTestUtils {

    public static Foo getFoo() {
        Foo foo = new Foo();
        foo.setFoo("test1");
        foo.setBar(1);
        foo.setBaz(2.0);
        foo.setQux(BigDecimal.TEN);
        foo.setFoobar(3);
        foo.setFoobaz(4.0);
        foo.setLocalDate(LocalDate.parse("2024-01-01"));

        List<Foo.Bar> barList = new ArrayList<>();
        Set<Foo.Baz> bazSet = new LinkedHashSet<>();

        Foo.Baz baz1 = new Foo.Baz();
        baz1.setFoo("baz1");

        Foo.Baz baz2 = new Foo.Baz();
        baz2.setFoo("baz2");

        bazSet.add(baz1);
        bazSet.add(baz2);


        Foo.Bar bar1 = new Foo.Bar();
        bar1.setFoo("bar1");
        bar1.setBazSet(bazSet);

        Foo.Bar bar2 = new Foo.Bar();
        bar2.setFoo("bar2");
        bar2.setBazSet(bazSet);

        barList.add(bar1);
        barList.add(bar2);

        foo.setBarList(barList);
        return foo;
    }

    @Getter
    @Setter
    @ToString
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

        @Positional(length = 10, filler = Filler.SPACES_RIGTH)
        private LocalDate localDate;

        @Positional(length = 20, collectionSize = 2)
        private List<Bar> barList;

        @Getter
        @Setter
        @ToString
        public static class Bar {
            @Positional(length = 10, filler = Filler.SPACES_LEFT)
            private String foo;

            @Positional(length = 10, collectionSize = 2)
            private Set<Baz> bazSet;
        }

        @Getter
        @Setter
        @ToString
        public static class Baz {
            @Positional(length = 10, filler = Filler.ZEROS_RIGTH)
            private String foo;
        }
    }

    @Getter
    @Setter
    @ToString
    public static class Qux {
        public String foo;
    }
}
