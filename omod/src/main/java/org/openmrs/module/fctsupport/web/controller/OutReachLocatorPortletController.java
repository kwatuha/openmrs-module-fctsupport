package org.openmrs.module.fctsupport.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.openmrs.Concept;
import org.openmrs.FieldType;
import org.openmrs.Patient;
import org.openmrs.PersonAttributeType;
import org.openmrs.api.ConceptService;
import org.openmrs.api.FormService;
import org.openmrs.api.PersonService;
import org.openmrs.api.context.Context;


import org.openmrs.web.controller.PortletController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("**/outreachlocator.portlet")
public class OutReachLocatorPortletController extends PortletController {

    @Override
    protected void populateModel(HttpServletRequest request, Map<String, Object> model) {
// your code here

        ////H
        FormService formService=Context.getFormService() ;
        ConceptService cservice=Context.getConceptService();
        Patient patient = (Patient) model.get("patient");

        List<FieldType> listFieldTypes=formService.getAllFieldTypes();

        List<Concept> listConcepts=cservice.getAllConcepts();


        model.put("listFieldTypes", listFieldTypes);


        model.put("listConcepts",listConcepts);

        model.put("patient",patient);

    }

    private List<String, Object> generateObjectMap(MobileFormEntryError error) {
        // try to stick to basic types; String, Integer, etc (not Date)
        // JSP expects: [id, error, details, form name, comment]
        MobileFormEntryErrorModel errorModel = new MobileFormEntryErrorModel(error, getFormType( error.getFormName()));
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("id", error.getMobileFormEntryErrorId());
        result.put("location", error.getLocationId());
        result.put("provider", error.getProviderId());
        result.put("error", error.getError());
        result.put("errorDetails", error.getErrorDetails());
        result.put("formName", error.getFormName());
        result.put("comment", error.getComment());
        return result;
    }

}

