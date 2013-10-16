package org.openmrs.module.fctsupport.util;

import java.util.ArrayList;
import java.util.UUID;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.fctsupport.OpenMRSTableFields;

/**
 * Created with IntelliJ IDEA.
 * User: alfayo
 * Date: 10/14/13
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class FctSupportUtil {


    public static ArrayList<OpenMRSTableFields> getListDefaultNewPersonFields() {
        //build fields for person
        ConceptService conceptService= Context.getConceptService();
        ArrayList<OpenMRSTableFields> listFields=new ArrayList();
        OpenMRSTableFields   familyNameField=new OpenMRSTableFields();
        familyNameField.setOpemrsTable("patient_name");
        familyNameField.setOpemrsTag("otherPerson.family_name") ;
        familyNameField.setOpenmrsAttribute("family_name");
        familyNameField.setFieldCaption("Family Name");
        familyNameField.setUuid(UUID.randomUUID().toString());
        familyNameField.setDefaultValue("$!{lotherPerson.getFamilyName()}");
        familyNameField.setFieldTypeId(2);

        OpenMRSTableFields   middleNameField=new OpenMRSTableFields();
        middleNameField.setOpemrsTable("patient_name");
        middleNameField.setOpemrsTag("otherPerson.middle_name") ;
        middleNameField.setOpenmrsAttribute("middle_name");
        middleNameField.setFieldCaption("Middle Name");
        middleNameField.setUuid(UUID.randomUUID().toString());
        middleNameField.setDefaultValue("$!{otherPerson.getMiddleName()}");
        middleNameField.setFieldTypeId(2);

        OpenMRSTableFields   givenNameField=new OpenMRSTableFields();
        givenNameField.setOpemrsTable("patient_name");
        givenNameField.setOpemrsTag("otherPerson.given_name") ;
        givenNameField.setOpenmrsAttribute("given_name");
        givenNameField.setFieldCaption("Given Name");
        givenNameField.setUuid(UUID.randomUUID().toString());
        givenNameField.setDefaultValue("$!{otherPerson.getGivenName()}");
        givenNameField.setFieldTypeId(2);

        OpenMRSTableFields   genderField=new OpenMRSTableFields();
        genderField.setOpemrsTable("patient");
        genderField.setOpemrsTag("otherPerson.sex ") ;
        genderField.setOpenmrsAttribute("gender");
        genderField.setFieldCaption("Gender");
        genderField.setUuid(UUID.randomUUID().toString());
        genderField.setDefaultValue("$!{otherPerson.getGender()}");
        genderField.setFieldTypeId(2);

        OpenMRSTableFields   birthDateEstimatedField=new OpenMRSTableFields();
        birthDateEstimatedField.setOpemrsTable("person");
        birthDateEstimatedField.setOpemrsTag("otherPerson.birthdate_estimated") ;
        birthDateEstimatedField.setOpenmrsAttribute("birthdate_estimated");
        birthDateEstimatedField.setFieldCaption("Estimated Birth date");
        birthDateEstimatedField.setUuid(UUID.randomUUID().toString());
        birthDateEstimatedField.setDefaultValue("$!{patient.getBirthDateEstimated()}");
        birthDateEstimatedField.setFieldTypeId(2);

        OpenMRSTableFields   birthDateField=new OpenMRSTableFields();
        birthDateField.setOpemrsTable("patient");
        birthDateField.setOpemrsTag("otherPerson.birthdate") ;
        birthDateField.setOpenmrsAttribute("birthdate");
        birthDateField.setFieldCaption("Birth date");
        birthDateField.setUuid(UUID.randomUUID().toString());
        birthDateField.setDefaultValue("$!{date.format($otherPerson.getBirthdate())}");
        birthDateField.setFieldTypeId(2);

        OpenMRSTableFields   siblingAgeField=new OpenMRSTableFields();
        siblingAgeField.setConceptId(conceptService.getConcept(5575));
        siblingAgeField.setOpemrsTag("otherPerson.siblingAge") ;
        siblingAgeField.setFieldCaption("Sibling Age");
        siblingAgeField.setUuid(UUID.randomUUID().toString());
        siblingAgeField.setFieldTypeId(1);

        listFields.add(familyNameField) ;
        listFields.add(middleNameField) ;
        listFields.add(givenNameField) ;
        listFields.add(birthDateEstimatedField) ;
        listFields.add(birthDateField) ;
        listFields.add(genderField) ;
        listFields.add(siblingAgeField) ;
        return listFields;
    }
}
