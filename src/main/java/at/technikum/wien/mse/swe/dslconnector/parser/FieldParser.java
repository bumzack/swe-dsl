package at.technikum.wien.mse.swe.dslconnector.parser;

import at.technikum.wien.mse.swe.dslconnector.exception.FieldParserException;

public interface FieldParser {
    <T> T parseValue(final String source) throws FieldParserException;
}
