/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.fctsupport.web;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import org.openmrs.api.context.Context;
import org.openmrs.module.fctsupport.model.AmrsPersonType;
import org.openmrs.module.fctsupport.service.FctSupportService;


/**
 *DWR class for AMRS Complex Obs module
 */
public class DWRFctSupportService {

    private static final Log log = LogFactory.getLog(DWRFctSupportService.class);


    public String savePersonTypeDetails(String  persontypename,Integer displayPosition,String  fieldName,String  description){

        //return  "Saved Successfully"+description+ persontypename+displayPosition+fieldName;
        FctSupportService service=Context.getService(FctSupportService.class);

        AmrsPersonType amrspersontype=new AmrsPersonType();

        amrspersontype.setPersonTypeName(persontypename);
        amrspersontype.setDisplayPosition(displayPosition);
        amrspersontype.setFieldName(fieldName);
        amrspersontype.setDescription(description);

        AmrsPersonType savedPerson=service.saveAmrsPersonType(amrspersontype);
       Integer ids= savedPerson.getId();

        return "saved succedded "+ids;
    }


    public String savePersonTypeDwr(){

        FctSupportService service=Context.getService(FctSupportService.class);
        String  persontypename="parentguardian";
        Integer displayPosition=3;
        String  fieldName="parent";
        String  description="Kwsdfdsfds" ;
        AmrsPersonType amrspersontype=new AmrsPersonType();

        amrspersontype.setPersonTypeName(persontypename);
        amrspersontype.setDisplayPosition(displayPosition);
        amrspersontype.setFieldName(fieldName);
        amrspersontype.setDescription(description);

        service.saveAmrsPersonType(amrspersontype);

        AmrsPersonType savedPerson=service.saveAmrsPersonType(amrspersontype);

        Integer ids= savedPerson.getId();
        return  "Saved Successfully" ;
    }



}