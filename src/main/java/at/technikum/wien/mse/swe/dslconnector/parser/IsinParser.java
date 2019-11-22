package at.technikum.wien.mse.swe.dslconnector.parser;

import at.technikum.wien.mse.swe.dslconnector.annotations.AlignmentEnum;
import at.technikum.wien.mse.swe.dslconnector.exception.FieldParserException;
import at.technikum.wien.mse.swe.model.ISIN;

public class IsinParser implements FieldParser {
    private StringParser stringParser;

    public IsinParser(int pos, int len, AlignmentEnum alignment, boolean padding, char paddingCharacter) {
        stringParser = new StringParser(pos, len, alignment, padding, paddingCharacter);
    }

    public ISIN parseValue(final String source) throws FieldParserException {
        final String isinString = stringParser.parseValue(source);
        final ISIN isin = new ISIN(isinString);
        return isin;
    }
}
