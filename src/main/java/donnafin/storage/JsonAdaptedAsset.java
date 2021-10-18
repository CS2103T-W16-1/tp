package donnafin.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import donnafin.commons.exceptions.IllegalValueException;
import donnafin.model.person.Asset;

/**
 * Jackson-friendly version of {@link Asset}.
 */
class JsonAdaptedAsset {

    private final String assetName;
    private final String assetType;
    private final String assetValue;
    private final String assetRemarks;

    /**
     * Constructs a {@code JsonAdaptedAsset} with the given {@code assetName}.
     */
    @JsonCreator
    public JsonAdaptedAsset(String assetName, String assetType, String assetValue, String assetRemarks) {
        this.assetName = assetName;
        this.assetRemarks = assetRemarks;
        this.assetType = assetType;
        this.assetValue = assetValue;
    }

    /**
     * Converts a given {@code Asset} into this class for Jackson use.
     */
    public JsonAdaptedAsset(Asset source) {
        assetName = source.name;
        assetValue = source.value;
        assetType = source.type;
        assetRemarks = source.remarks;
    }

    @JsonValue
    public String getAssetName() {
        return assetName;
    }

    @JsonValue
    public String getAssetRemarks() {
        return assetRemarks;
    }

    @JsonValue
    public String getAssetType() {
        return assetType;
    }

    @JsonValue
    public String getAssetValue() {
        return assetValue;
    }

    /**
     * Converts this Jackson-friendly adapted assets object into the model's {@code Asset} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted assets.
     */
    public Asset toModelType() throws IllegalValueException {
        if (!Asset.isValidAsset(assetName)) {
            throw new IllegalValueException(Asset.MESSAGE_CONSTRAINTS);
        }
        return new Asset(assetName, assetType, assetValue, assetRemarks);
    }

}
