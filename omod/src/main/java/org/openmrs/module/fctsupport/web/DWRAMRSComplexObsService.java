/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.fctsupport.web;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.openmrs.api.ConceptService;
import org.openmrs.api.FormService;
import org.openmrs.api.PersonService;
import org.openmrs.api.context.Context;

import org.openmrs.module.fctsupport.model.AmrsPersonType;
import org.openmrs.module.fctsupport.service.FctSupportService;


/**
 *DWR class for AMRS Complex Obs module
 */
public class DWRAMRSComplexObsService {

    private static final Log log = LogFactory.getLog(DWRAMRSComplexObsService.class);

    PersonService personService=Context.getPersonService() ;
    FormService formService=Context.getFormService() ;
    ConceptService cservice=Context.getConceptService();
    FctSupportService amrsComplexObsservice=Context.getService(FctSupportService.class);


    public String saveAmrsPersonType(String  persontypename,String  description){

        FctSupportService service=Context.getService(FctSupportService.class);

        AmrsPersonType amrspersontype=new AmrsPersonType();
        amrspersontype.setPersonTypeName(persontypename);

        amrspersontype.setDescription(description);

        service.saveAmrsPersonType(amrspersontype);

        return  "Saved Successfully" ;
    }


}