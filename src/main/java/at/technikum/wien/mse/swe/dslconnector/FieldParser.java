package at.technikum.wien.mse.swe.dslconnector;

public interface FieldParser<T> {
    T parseValue(final String source) throws FieldParserException;
}
