package org.openmrs.module.fctsupport.handler;

/**
 * Created with IntelliJ IDEA.
 * User: alfayo
 * Date: 3/26/13
 * Time: 12:48 PM
 * To change this template use File | Settings | File Templates.
 */

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.ConceptComplex;
import org.openmrs.FieldType;
import org.openmrs.Form;
import org.openmrs.User;


import java.io.CharArrayReader;
import java.io.Reader;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


import org.openmrs.api.APIException;
import org.openmrs.Field;
import org.openmrs.FormField;
import org.openmrs.Obs;

import org.openmrs.api.context.Context;

import org.openmrs.module.fctsupport.OpenMRSTableFields;
import org.openmrs.module.fctsupport.util.FctSupportUtil;
import org.openmrs.obs.ComplexData;
import org.openmrs.api.ObsService;
import org.openmrs.api.ConceptService;
import org.openmrs.api.AdministrationService;
import org.openmrs.obs.SerializableComplexObsHandler;

public class OutreachMotherHandler implements SerializableComplexObsHandler {

    private Log log = LogFactory.getLog(this.getClass());

    public Set<FormField> getFormFields() {
        Set<FormField> formFields = new HashSet<FormField>();


       //  List<OpenMRSTableFields>listFields= FctSupportUtil.getListDefaultNewPersonFields();
        List<OpenMRSTableFields>listFields= FctSupportUtil.getListRepeatingSectionFields("parentGuardian");

        listFields.addAll(FctSupportUtil.getListRepeatingSectionFields("nextOfKin"));

        listFields.addAll(FctSupportUtil.getListRepeatingSectionFields("treatmentSupporter"));


        for (OpenMRSTableFields tableField : listFields) {


            //Create a field
            FieldType sectionFieldType=new FieldType();
            sectionFieldType.setId(tableField.getFieldTypeId());

            Field fieldSection = new Field();
            fieldSection.setFieldType(sectionFieldType);
            fieldSection.setName(tableField.getOpemrsTag());
            fieldSection.setTableName(tableField.getOpemrsTable());
            fieldSection.setAttributeName(tableField.getOpenmrsAttribute());
            fieldSection.setDefaultValue(tableField.getDefaultValue());
            fieldSection.setConcept(tableField.getConceptId());

            FormField formFieldObjSection = getNewFormField();
            formFieldObjSection.setField(fieldSection);

            formFields.add(formFieldObjSection);



        }

        return formFields;
    }

    /**
     * @should

     * @param obs
     * @return
     * @throws APIException
     */
    public Obs saveObs(Obs obs) throws APIException {
        Integer locId=obs.getLocation().getId();
        //Integer providerId=obs.getEncounter().getProviderI
        log.info("=========================Start Saving");
       ObsService os = Context.getObsService();
        ConceptService cs = Context.getConceptService();
        //obs.ConceptAnswer mycs=cs.getConceptAnswer(obs.getConcept().getId());
        AdministrationService as = Context.getAdministrationService();
        User user=Context.getAuthenticatedUser();
         // the complex data to put onto an obs that will be saved
         Reader input = new CharArrayReader("This is a string to save to a file".toCharArray());
        ComplexData complexData = new ComplexData("Wonderful councellor", input);
        obs.setCreator(user);
        obs.setComplexData(complexData);
        ConceptComplex conceptComplex = Context.getConceptService().getConceptComplex(obs.getConcept().getId());
       //;
        obs.setValueComplex("internal user by alfayo"+conceptComplex.serialize() );
        ComplexData savedComplex= obs.getComplexData();
        String savedcmpx=savedComplex.getData().toString();
        try {
            //os.saveObs(obs, null);
        }
        finally {
            // we always have to delete this inside the same unit test because it is outside the
            // database and hence can't be "rolled back" like everything else
            //createdFile.delete();
        }
        return obs;
    }

    public Obs getObs(Obs obs, String view) {
        String t="kwatuha" ;
        String tb="TB";
        System.out.println(view);
        obs.setValueComplex("Kwatuha Alfayo Muminmamsdsdsdsdsds"+view);
        return obs;
    }

    public boolean purgeComplexData(Obs obs) {
        return false;
    }

    public String serializeFormData(String data) {
        System.out.println("This is is a more ") ;
       System.out.println(data);
        return null;
    }

    private static FormField getNewFormField(){
        FormField formField = new FormField();
        formField.setUuid(UUID.randomUUID().toString());
        formField.setCreator(Context.getAuthenticatedUser());
        formField.setSortWeight(1.0f);
        formField.setDateCreated(new Date());


        return formField;
    }

}