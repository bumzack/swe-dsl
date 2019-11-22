package at.technikum.wien.mse.swe.dslconnector.parser;

import at.technikum.wien.mse.swe.dslconnector.annotations.AlignmentEnum;
import at.technikum.wien.mse.swe.dslconnector.exception.FieldParserException;
import at.technikum.wien.mse.swe.model.Amount;

import java.math.BigDecimal;

public class AmountParser implements FieldParser {

    private BigDecimalParser balanceParser;
    private StringParser currencyParser;

    public AmountParser(int posBalance, int lenBalance, AlignmentEnum alignmentBalance, boolean paddingBalance, char paddingCharacterBalance,
                        int posCurrency, int lenCurrency, AlignmentEnum alignmentCurrency, boolean paddingCurrency, char paddingCharacterCurrency) {
        balanceParser = new BigDecimalParser(posBalance, lenBalance, alignmentBalance, paddingBalance, paddingCharacterBalance);
        currencyParser = new StringParser(posCurrency, lenCurrency, alignmentCurrency, paddingCurrency, paddingCharacterCurrency);
    }

    public Amount parseValue(final String source) throws FieldParserException {
        final String currency = currencyParser.parseValue(source);
        final BigDecimal balance = balanceParser.parseValue(source);
        final Amount amount = new Amount(currency, balance);

        return amount;
    }
}
