package donnafin.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import donnafin.commons.exceptions.IllegalValueException;
import donnafin.model.person.Liability;

public class JsonAdaptedLiability {
    private final String liabilityName;
    private final String liabilityType;
    private final String liabilityValue;
    private final String liabilityRemarks;

    /**
     * Constructs a {@code JsonAdaptedLiability} with the given {@code liabilityName}.
     */
    @JsonCreator
    public JsonAdaptedLiability(String liabilityName, String liabilityType, String liabilityValue,
                                String liabilityRemarks) {
        this.liabilityName = liabilityName;
        this.liabilityRemarks = liabilityRemarks;
        this.liabilityValue = liabilityValue;
        this.liabilityType = liabilityType;
    }

    /**
     * Converts a given {@code Liability} into this class for Jackson use.
     */
    public JsonAdaptedLiability(Liability source) {
        liabilityName = source.name;
        liabilityType = source.type;
        liabilityValue = source.value;
        liabilityRemarks = source.remarks;
    }

    @JsonValue
    public String getLiabilityName() {
        return liabilityName;
    }

    @JsonValue
    public String getLiabilityType() {
        return liabilityType;
    }

    @JsonValue
    public String getLiabilityValue() {
        return liabilityValue;
    }

    @JsonValue
    public String getLiabilityRemarks() {
        return liabilityRemarks;
    }

    /**
     * Converts this Jackson-friendly adapted assets object into the model's {@code Liability} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted liability.
     */
    public Liability toModelType() throws IllegalValueException {
        if (!Liability.isValidLiability(liabilityName)) {
            throw new IllegalValueException(Liability.MESSAGE_CONSTRAINTS);
        }
        return new Liability(liabilityName, liabilityType, liabilityValue, liabilityRemarks);
    }
}
