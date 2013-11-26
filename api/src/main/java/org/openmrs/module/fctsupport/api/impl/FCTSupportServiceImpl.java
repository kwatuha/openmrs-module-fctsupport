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
package org.openmrs.module.fctsupport.api.impl;

import java.util.List;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.fctsupport.api.FCTSupportService;
import org.openmrs.module.fctsupport.api.db.FCTSupportDAO;
import org.openmrs.Field;
import org.openmrs.module.fctsupport.model.AmrsComplexObs;
import org.openmrs.module.fctsupport.model.AmrsPersonType;

/**
 * It is a default implementation of {@link FCTSupportService}.
 */
public class FCTSupportServiceImpl extends BaseOpenmrsService implements FCTSupportService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private FCTSupportDAO dao;
	
	/**
     * @param dao the dao to set
     */
    public void setDao(FCTSupportDAO dao) {
	    this.dao = dao;
    }
    
    /**
     * @return the dao
     */
    public FCTSupportDAO getDao() {
	    return dao;
    }

    public List<Field> getComplexConceptFieldUuids(){
        return dao.getComplexConceptFieldUuids();
    }

    public AmrsComplexObs saveAmrscomplexobs(AmrsComplexObs amrsComplexObs) {

        return dao.saveAmrscomplexobs(amrsComplexObs);

    }

    public List<AmrsComplexObs> getAmrscomplexobs() {

        return dao.getAmrscomplexobs();
    }

    public AmrsComplexObs getAmrscomplexobsByUuid(String uuid) {

        return dao.getAmrscomplexobsByUuid(uuid);
    }


    public List<AmrsPersonType> getAmrsPersonTypes(){
        return dao.getAmrsPersonTypes();
    }

    public AmrsPersonType saveAmrsPersonType(AmrsPersonType amrsPersonType) {

        return dao.saveAmrsPersonType(amrsPersonType);

    }

    public AmrsPersonType getAmrsPersonTypeByUuid(String uuid) {

        return dao.getAmrsPersonTypeByUuid(uuid);
    }


}