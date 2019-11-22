package at.technikum.wien.mse.swe.dslconnector.parser;

import at.technikum.wien.mse.swe.dslconnector.annotations.*;
import at.technikum.wien.mse.swe.dslconnector.parser.impl.*;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class ParserFactory {
    private Map<String, Class> supportedSimpleTypes;

    public ParserFactory() {
        supportedSimpleTypes = new LinkedHashMap<>();

        supportedSimpleTypes.put(String.class.getTypeName(), StringParser.class);
        supportedSimpleTypes.put(BigDecimal.class.getTypeName(), BigDecimalParser.class);
    }

    static public FieldParser getParserForType(final String typeName, final Annotation a) {
        if (a instanceof SimpleElement) {
            SimpleElement field = (SimpleElement) a;

            if (typeName.equals(String.class.getTypeName())) {
                return new StringParser(field.position(), field.length(), field.align(), field.padding(), field.paddingCharacter());
            }

            if (typeName.equals(BigDecimal.class.getTypeName())) {
                return new BigDecimalParser(field.position(), field.length(), field.align(), field.padding(), field.paddingCharacter());
            }
        }

        if (a instanceof RiskCategoryField) {
            RiskCategoryField field = (RiskCategoryField) a;
            return new RiskCategoryParser(field.position(), field.length(), field.align(), field.padding(), field.paddingChar());
        }

        if (a instanceof DepotOwnerField) {
            DepotOwnerField field = (DepotOwnerField) a;
            return new DeptOwnerParser(field.position(), field.lengthFirstName(), field.lengthLastName(), field.align(), field.padding(), field.paddingChar());
        }

        if (a instanceof AmountField) {
            AmountField field = (AmountField) a;
            return new AmountParser(field.positionBalance(), field.lengthBalance(), field.alignBalance(), field.paddingBalance(), field.paddingCharacterBalance(),
                    field.positionCurrency(), field.lengthCurrency(), field.alignCurrency(), field.paddingCurrency(), field.paddingCharacterCurrency());
        }

        if (a instanceof IsinField) {
            IsinField field = (IsinField) a;
            return new IsinParser(field.position(), field.length(), field.align(), field.padding(), field.paddingCharacter());
        }

        return null;
    }
}
