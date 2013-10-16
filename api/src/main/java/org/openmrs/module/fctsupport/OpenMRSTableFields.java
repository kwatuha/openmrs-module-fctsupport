package org.openmrs.module.fctsupport;

import org.openmrs.Concept;

/**
 * Created with IntelliJ IDEA.
 * User: alfayo
 * Date: 9/1/13
 * Time: 4:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class OpenMRSTableFields {

    private  String  opemrsTable;
    private  String  opemrsTag;
    private  String  openmrsAttribute;
    private  String  fieldCaption;
    private  String  defaultValue;
    private  String  uuid;
    private Integer  fieldTypeId;
    private Concept conceptId;

    public Concept getConceptId() {
        return conceptId;
    }

    public void setConceptId(Concept conceptId) {
        this.conceptId = conceptId;
    }

    public Integer getFieldTypeId() {
        return fieldTypeId;
    }

    public void setFieldTypeId(Integer fieldTypeId) {
        this.fieldTypeId = fieldTypeId;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }


    public String getFieldCaption() {
        return fieldCaption;
    }

    public void setFieldCaption(String fieldCaption) {
        this.fieldCaption = fieldCaption;
    }


    public String getOpemrsTag() {
        return opemrsTag;
    }

    public void setOpemrsTag(String opemrsTag) {
        this.opemrsTag = opemrsTag;
    }

    public String getOpemrsTable() {
        return opemrsTable;
    }

    public void setOpemrsTable(String opemrsTable) {
        this.opemrsTable = opemrsTable;
    }

    public String getOpenmrsAttribute() {
        return openmrsAttribute;
    }

    public void setOpenmrsAttribute(String openmrsAttribute) {
        this.openmrsAttribute = openmrsAttribute;
    }





}
