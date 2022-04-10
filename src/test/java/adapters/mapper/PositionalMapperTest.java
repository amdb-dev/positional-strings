package adapters.mapper;

import adapters.annotations.Position;
import domain.enumeration.Filler;
import domain.exception.PositionalException;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionalMapperTest {

    @Test
    public void mustConvertToString() throws PositionalException {
        Foo foo = new Foo();
        foo.setA("test");

        PositionalMapper mapper = new PositionalMapper();
        String test = mapper.mapperToString(foo);
        assertEquals("      test", test);
    }

    @Getter
    @Setter
    public static class Foo {
        @Position(length = 10, filler = Filler.SPACES_TO_LEFT)
        private String a;
        private Integer b;
        private int c;
        private Long d;
        private long e;
        private Double f;
        private double g;
        private Float h;
        private float i;
    }
}