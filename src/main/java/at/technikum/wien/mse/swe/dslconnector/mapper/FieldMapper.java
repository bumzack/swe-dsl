package at.technikum.wien.mse.swe.dslconnector.mapper;

import at.technikum.wien.mse.swe.dslconnector.exception.FieldMapperException;

public interface FieldMapper {
    <T> T mapValue(final String source) throws FieldMapperException;
}
