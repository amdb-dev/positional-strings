package ports;

public interface Mapper {
    <T> String mapper(T t);
    <T> T mapper(String value, Class c);
}
