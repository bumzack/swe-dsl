package at.technikum.wien.mse.swe.dslconnector.mapper.impl;

import at.technikum.wien.mse.swe.dslconnector.GenericMapper;
import at.technikum.wien.mse.swe.dslconnector.annotations.ComplexElement;
import at.technikum.wien.mse.swe.dslconnector.exception.FieldMapperException;
import at.technikum.wien.mse.swe.dslconnector.mapper.FieldMapper;
import at.technikum.wien.mse.swe.dslconnector.mapper.dto.ComplexTypeDto;
import at.technikum.wien.mse.swe.dslconnector.mapper.dto.SimpleTypeDto;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class ComplexTypeMapper implements FieldMapper {
    private Map<String, ComplexTypeDto> complexTypeDtos;
    private Field field;

    public ComplexTypeMapper(final Field f, final ComplexElement complexElement) {
        this.field = f;

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

    public <T> T mapValue(final String source) throws FieldMapperException {

        // \_(ツ)_/¯
        final Map<String, Field> availableFields = Arrays.stream(field.getType().getDeclaredFields())
                .collect(Collectors.toMap(Field::getName, entry -> entry));

        final boolean allPresent = complexTypeDtos.keySet().stream()
                .anyMatch(availableFields::containsKey);

        if (!allPresent) {
            final String err = "at least one of the required fields in the list of ComplexTYpe names is not available in the class " +
                    "fields in Annotation: " + complexTypeDtos.keySet().stream().toString() +
                    "available field in class " + ((Class) field.getType()).getSimpleName() + ": " +
                    availableFields.keySet().stream().toString();
            throw new FieldMapperException(err);
        }

        // order of fields is relevant for constructor if used
        final List<Field> fields = availableFields.entrySet().stream()
                .filter(entry -> complexTypeDtos.containsKey(entry.getKey()))
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());

        final GenericMapper genericMapper = new GenericMapper();
        final Map<Field, FieldMapper> fieldMappers = complexTypeDtos.entrySet().stream()
                .map(entry -> {
                    final Field f = availableFields.get(entry.getKey());
                    final SimpleTypeDto simpleTypeDto = SimpleTypeDto.map(entry.getValue());
                    final FieldMapper fieldMapper = new SimpleTypeMapper(f, simpleTypeDto);
                    return new AbstractMap.SimpleEntry<>(f, fieldMapper);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return (T) genericMapper.mapComplexTypeToObject(source, (Class) field.getType(), fields, fieldMappers);
    }
}
