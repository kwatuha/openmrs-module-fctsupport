
package org.openmrs.module.fctsupport.service.impl;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Field;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.fctsupport.dao.FctSupportDAO;
import org.openmrs.module.fctsupport.model.AmrsComplexObs;
import org.openmrs.module.fctsupport.model.AmrsPersonType;
import org.openmrs.module.fctsupport.service.FctSupportService;


/**
 * @author Ampath developers
 *
 */
public class FctSupportServiceImpl extends BaseOpenmrsService implements FctSupportService {

    protected static final Log log = LogFactory.getLog(FctSupportServiceImpl.class);
	private FctSupportDAO fctSupportDAO;
	

	public void setFctSupportDAO(FctSupportDAO fctSupportDAO) {
		this.fctSupportDAO = fctSupportDAO;
	}

    public AmrsComplexObs saveAmrsComplexObs(AmrsComplexObs amrsComplexObs) {

            return fctSupportDAO.saveAmrsComplexObs(amrsComplexObs);
		 
	}

	public List<AmrsComplexObs> getAmrsComplexObs() {
		
		return fctSupportDAO.getAmrsComplexObs();
	}
	
	public AmrsComplexObs getAmrsComplexObsByUuid(String uuid) {
		
		return fctSupportDAO.getAmrsComplexObsByUuid(uuid);
	}


    public List<Field> getComplexConceptFieldUuids(){
            return fctSupportDAO.getComplexConceptFieldUuids();
    }

    public AmrsPersonType saveAmrsPersonType(AmrsPersonType amrsPersonType) {

        return fctSupportDAO.saveAmrsPersonType(amrsPersonType);

    }

    public List<AmrsPersonType> getAmrsPersonType() {

        return fctSupportDAO.getAmrsPersonType();
    }

    public  AmrsPersonType getAmrsPersonTypeByUuid(String uuid) {

        return fctSupportDAO.getAmrsPersonTypeByUuid(uuid);
    }


}