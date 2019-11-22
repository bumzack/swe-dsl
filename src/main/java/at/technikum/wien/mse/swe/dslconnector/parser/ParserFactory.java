package at.technikum.wien.mse.swe.dslconnector.parser;

import at.technikum.wien.mse.swe.dslconnector.annotations.ComplexElement;
import at.technikum.wien.mse.swe.dslconnector.annotations.SimpleElement;
import at.technikum.wien.mse.swe.dslconnector.parser.dto.SimpleTypeDto;
import at.technikum.wien.mse.swe.dslconnector.parser.impl.ComplexTypeParser;
import at.technikum.wien.mse.swe.dslconnector.parser.impl.SimpleTypeParser;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

public class ParserFactory {
    static public FieldParser getParserByAnnotation(final Type type, final Annotation a) {
        if (a instanceof SimpleElement) {
            SimpleElement element = (SimpleElement) a;
            final SimpleTypeDto simpleTypeDto = SimpleTypeDto.map(element);
            return new SimpleTypeParser(type, simpleTypeDto);
        }

        if (a instanceof ComplexElement) {
            ComplexElement element = (ComplexElement) a;
            return new ComplexTypeParser(type, element);
        }
        return null;
    }
}
