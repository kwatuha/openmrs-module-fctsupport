/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.fctsupport.web.controller;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.fctsupport.model.AmrsPersonType;
import org.openmrs.module.fctsupport.service.FctSupportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;

/**
 * The main controller.
 */
@Controller
public class  FCTSupportManageController {

	protected final Log log = LogFactory.getLog(getClass());

	@RequestMapping(value = "/module/fctsupport/manage", method = RequestMethod.GET)
	public void manage(ModelMap model) {
		model.addAttribute("user", Context.getAuthenticatedUser());

        FctSupportService service=Context.getService(FctSupportService.class);
        List<AmrsPersonType> listAmrsPersonTypes=service.getAmrsPersonType();

        model.addAttribute("listAmrsPersonTypes",listAmrsPersonTypes);

	}


    @RequestMapping(method=RequestMethod.POST, value="module/fctsupport/manage")
    public void savePage(
            ModelMap map,
            @RequestParam(required=false, value="amrs_person_type") String persontType,
            @RequestParam(required=false, value="display_position") Integer displayPositiopen,
            @RequestParam(required=false, value="amrs_person_type_field_name") String fieldName,
            @RequestParam(required=false, value="amrs_person_type_description") String description,
            @RequestParam(required=false, value="voidform") String vvoid,
            @RequestParam(required=false, value="Edit") String  editbtn,
            @RequestParam(required=false, value="void") String  voidbtn,
            @RequestParam(required=false, value="voidreason") String  voidReason){

                FctSupportService service=Context.getService(FctSupportService.class);

                List<AmrsPersonType> listAmrsPersonTypes=service.getAmrsPersonType();

                AmrsPersonType amrspersontype=new AmrsPersonType();

                amrspersontype.setPersonTypeName(persontType);

                amrspersontype.setDisplayPosition(displayPositiopen);

                amrspersontype.setFieldName(fieldName);

                amrspersontype.setDescription(description);

        AmrsPersonType savedPersonType= service.saveAmrsPersonType(amrspersontype);
            Integer savedId=savedPersonType.getId();

                map.addAttribute("listAmrsPersonTypes",listAmrsPersonTypes);

    }
}
