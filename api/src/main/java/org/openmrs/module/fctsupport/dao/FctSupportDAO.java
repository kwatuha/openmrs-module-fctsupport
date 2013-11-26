
/**
 * 
 */
package org.openmrs.module.fctsupport.dao;

import java.util.List;
import org.openmrs.Field;
import org.openmrs.Form;
import org.openmrs.module.fctsupport.model.AmrsComplexObs;
import org.openmrs.module.fctsupport.model.AmrsPersonType;


/**
 *
 * @author Ampath Developers
 */
public interface FctSupportDAO {


  /**
	 *
	 * save AmrsComplexObs
	 * @param AmrsComplexObs to be saved
	 * @return AmrsComplexObs object
	 */

	public AmrsComplexObs saveAmrsComplexObs(AmrsComplexObs AmrsComplexObs);

    public List<AmrsComplexObs> getAmrsComplexObs();

    public AmrsComplexObs getAmrsComplexObsByUuid(String uuid);

    public List<Field> getComplexConceptFieldUuids() ;

    public AmrsPersonType saveAmrsPersonType(AmrsPersonType amrspersontype);

    public List<AmrsPersonType> getAmrsPersonType();

    public AmrsPersonType getAmrsPersonTypeByUuid(String uuid);
}