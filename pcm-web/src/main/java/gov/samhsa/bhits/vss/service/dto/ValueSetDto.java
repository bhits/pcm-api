package gov.samhsa.bhits.vss.service.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * The Class ValueSetDto.
 */
public class ValueSetDto extends AbstractNodeDto {

    /**
     * The description.
     */
    private String description;

    /**
     * The value set category id.
     */
    private Long valueSetCategoryId;

    /**
     * The value set cat name.
     */
    private String valueSetCatName;

    private Map<Long, String> valueSetCategoryMap = new HashMap<Long, String>();


    /**
     * The value set cat code.
     */
    private String valueSetCatCode;
    /**
     * The rows updated.
     */
    private int rowsUpdated;

    /**
     * The error row.
     */
    private int errorRow;

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     *
     * @param description the new description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the value set category id.
     *
     * @return the value set category id
     */
    public Long getValueSetCategoryId() {
        return valueSetCategoryId;
    }

    /**
     * Sets the value set category id.
     *
     * @param valueSetCategoryId the new value set category id
     */
    public void setValueSetCategoryId(Long valueSetCategoryId) {
        this.valueSetCategoryId = valueSetCategoryId;
    }

    /**
     * Gets the value set cat code.
     *
     * @return the value set cat code
     */
    public String getValueSetCatCode() {
        return valueSetCatCode;
    }

    /**
     * Sets the value set cat code.
     *
     * @param valueSetCatCode the new value set cat code
     */
    public void setValueSetCatCode(String valueSetCatCode) {
        this.valueSetCatCode = valueSetCatCode;
    }

    /**
     * Gets the value set cat name.
     *
     * @return the value set cat name
     */
    public String getValueSetCatName() {
        return valueSetCatName;
    }

    /**
     * Sets the value set cat name.
     *
     * @param valueSetCatName the new value set cat name
     */
    public void setValueSetCatName(String valueSetCatName) {
        this.valueSetCatName = valueSetCatName;
    }


    /**
     * Gets the rows updated.
     *
     * @return the rows updated
     */
    public int getRowsUpdated() {
        return rowsUpdated;
    }

    /**
     * Sets the rows updated.
     *
     * @param rowsUpdated the new rows updated
     */
    public void setRowsUpdated(int rowsUpdated) {
        this.rowsUpdated = rowsUpdated;
    }

    /**
     * Gets the error row.
     *
     * @return the error row
     */
    public int getErrorRow() {
        return errorRow;
    }

    /**
     * Sets the error row.
     *
     * @param errorRow the new error row
     */
    public void setErrorRow(int errorRow) {
        this.errorRow = errorRow;
    }


    public Map<Long, String> getValueSetCategoryMap() {
        return valueSetCategoryMap;
    }

    public void setValueSetCategoryMap(Map<Long, String> valueSetCategoryMap) {
        this.valueSetCategoryMap = valueSetCategoryMap;
    }

    /* (non-Javadoc)
     * @see gov.samhsa.consent2share.service.dto.AbstractNodeDto#toString()
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
