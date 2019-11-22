package at.technikum.wien.mse.swe.dslconnector.parser.impl;

import at.technikum.wien.mse.swe.dslconnector.Parser;
import at.technikum.wien.mse.swe.dslconnector.annotations.ComplexElement;
import at.technikum.wien.mse.swe.dslconnector.exception.FieldParserException;
import at.technikum.wien.mse.swe.dslconnector.parser.FieldParser;
import at.technikum.wien.mse.swe.dslconnector.parser.impl.dto.ComplexTypeDto;
import at.technikum.wien.mse.swe.dslconnector.parser.impl.dto.SimpleTypeDto;
import at.technikum.wien.mse.swe.model.RiskCategory;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class ComplexTypeParser implements FieldParser {
    private Map<String, ComplexTypeDto> complexTypeDtos;
    private Type t;

    public ComplexTypeParser(final Type t, final ComplexElement complexElement) {
        this.t = t;

        complexTypeDtos = new LinkedHashMap<>();

        for (int i = 0; i < complexElement.align().length; i++) {
            complexTypeDtos.put(complexElement.name()[i],
                    ComplexTypeDto.map(complexElement.name()[i],
                            complexElement.position()[i],
                            complexElement.length()[i],
                            complexElement.align()[i],
                            complexElement.padding()[i],
                            complexElement.paddingCharacter()[i]));
        }
    }

    public <T> T parseValue(final String source) throws FieldParserException {

        // \_(ツ)_/¯
        final Map<String, Field> availableFields = Arrays.asList(((Class) t).getDeclaredFields()).stream()
                .collect(Collectors.toMap(Field::getName, entry -> entry));

        /// System.out.println("ComplexTypeParser   avaliable fields");
        // availableFields.keySet().forEach(k -> System.out.println("      name: " + k));

        // System.out.println("ComplexTypeParser   complexTypeDtos fields");
        //  complexTypeDtos.keySet().forEach(k -> System.out.println("      name: " + k));

        final boolean allPresent = complexTypeDtos.keySet().stream()
                .anyMatch(availableFields::containsKey);

        if (!allPresent) {
            final String err = "at least one of the required fields in the list of ComplexTYpe names is not available in the class \n" +
                    "fields in Annotation: " + complexTypeDtos.keySet().stream().toString() + "\n" +
                    "available field in class " + ((Class) t).getSimpleName() + ": \n" +
                    availableFields.keySet().stream().toString();
            throw new FieldParserException(err);
        }

        // order of fields is relevant for constructor in case
        final List<Field> fields = availableFields.entrySet().stream()
                .filter(entry -> complexTypeDtos.containsKey(entry.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        final Parser parser = new Parser();
        final Map<Field, FieldParser> fieldParsers = complexTypeDtos.entrySet().stream()
                .map(entry -> {
                    final Field f = availableFields.get(entry.getKey());
                    final SimpleTypeDto simpleTypeDto = SimpleTypeDto.map(entry.getValue());

                    if (((Class) t).getSimpleName().equals(RiskCategory.class.getSimpleName())) {
                        final FieldParser fieldParser = new RiskCategoryParser(simpleTypeDto);
                        return new AbstractMap.SimpleEntry<>(f, fieldParser);
                    }
                    final FieldParser fieldParser = new SimpleTypeParser(f.getType(), simpleTypeDto);
                    return new AbstractMap.SimpleEntry<>(f, fieldParser);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return (T) parser.parseComplexTypeObject(source, (Class) t, fields, fieldParsers);
    }
}
