package uk.co.emg.converter;

import uk.co.emg.enumeration.MutationType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class MutationTypeConverter implements AttributeConverter<MutationType, String> {
    @Override
    public String convertToDatabaseColumn(MutationType mutationType) {
        if (mutationType == null) {
            return null;
        }
        return mutationType.name();
    }

    @Override
    public MutationType convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }
        return Stream.of(MutationType.values())
                .filter(mutationType -> mutationType.name().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
