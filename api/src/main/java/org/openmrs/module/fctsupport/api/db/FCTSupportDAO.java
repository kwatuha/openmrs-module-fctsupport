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
import org.openmrs.module.fctsupport.api.FCTSupportService;
import org.openmrs.module.fctsupport.model.Amrscomplexobs;

/**
 *  Database methods for {@link FCTSupportService}.
 */
public interface FCTSupportDAO {
	
	/*
	 * Add DAO methods here
	 */

    public List<Field> getComplexConceptFieldUuids();

    public Amrscomplexobs saveAmrscomplexobs(Amrscomplexobs amrscomplexobs);

    public List<Amrscomplexobs> getAmrscomplexobs();

    public Amrscomplexobs getAmrscomplexobsByUuid(String uuid);
}