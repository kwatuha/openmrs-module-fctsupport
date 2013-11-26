/**
 * 
 */
package org.openmrs.module.fctsupport.service;

import java.util.List;
import org.openmrs.Field;
import org.openmrs.api.OpenmrsService;

import org.openmrs.module.fctsupport.model.AmrsComplexObs;
import org.openmrs.module.fctsupport.model.AmrsPersonType;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ampath Developers
 *
 */
@Transactional

public interface FctSupportService extends OpenmrsService{

	public AmrsComplexObs saveAmrsComplexObs(AmrsComplexObs amrsComplexObs);

	public List<AmrsComplexObs> getAmrsComplexObs();

    public AmrsComplexObs getAmrsComplexObsByUuid(String uuid);

    public List<Field> getComplexConceptFieldUuids();

    public AmrsPersonType saveAmrsPersonType(AmrsPersonType amrspersontype);

    public List<AmrsPersonType> getAmrsPersonType();

    public AmrsPersonType getAmrsPersonTypeByUuid(String uuid);


}