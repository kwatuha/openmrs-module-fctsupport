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
package org.openmrs.module.fctsupport.api.db;

import java.util.List;
import org.openmrs.Field;

import org.openmrs.module.fctsupport.model.AmrsComplexObs;
import org.openmrs.module.fctsupport.model.AmrsPersonType;
import org.openmrs.module.fctsupport.api.FCTSupportService;
/**
 *  Database methods for {@link FCTSupportService}.
 */
public interface FCTSupportDAO {
	
	/*
	 * Add DAO methods here
	 */

    public List<Field> getComplexConceptFieldUuids();

    public AmrsComplexObs saveAmrscomplexobs(AmrsComplexObs amrsComplexObs);

    public List<AmrsComplexObs> getAmrscomplexobs();

    public AmrsComplexObs getAmrscomplexobsByUuid(String uuid);
    public List<AmrsPersonType> getAmrsPersonTypes();

    public AmrsPersonType saveAmrsPersonType(AmrsPersonType amrsPersonType);

    public AmrsPersonType getAmrsPersonTypeByUuid(String uuid);
}