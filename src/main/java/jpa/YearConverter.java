package jpa;

import java.time.Year;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class YearConverter implements AttributeConverter<Year, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Year year) {
        return year != null ? year.getValue() : null;
    }

    @Override
    public Year convertToEntityAttribute(Integer value) {
        return value != null ? Year.of(value) : null;
    }

}
