
/**
 * 
 */
package org.openmrs.module.fctsupport.dao.hibernate;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.openmrs.Field;
import org.openmrs.module.fctsupport.dao.FctSupportDAO;
import org.openmrs.module.fctsupport.model.AmrsComplexObs;
import org.openmrs.module.fctsupport.model.AmrsPersonType;


/**
 * @author Ampath Developers
 *
 */
public class HibernateFctSupportDAO implements FctSupportDAO {
	
	private SessionFactory sessionFactory;
	
	
    /**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
    	return sessionFactory;
    }

	
    /**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
    	this.sessionFactory = sessionFactory;
    }

    public AmrsComplexObs saveAmrsComplexObs(AmrsComplexObs amrsComplexObs) {
		// TODO Auto-generated method stub
		
		sessionFactory.getCurrentSession().saveOrUpdate(amrsComplexObs);
		
		return amrsComplexObs;
	
	}


	@SuppressWarnings("unchecked")
	public List<AmrsComplexObs> getAmrsComplexObs() {
		// TODO Auto-generated method stub
		
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AmrsComplexObs.class);
		
		
		return criteria.list();
		
	}

		@SuppressWarnings("unchecked")
	public AmrsComplexObs getAmrsComplexObsByUuid(String uuid) {
		
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AmrsComplexObs.class).add(
		Expression.eq("uuid", uuid));
		
		@SuppressWarnings("unchecked")
		
		List<AmrsComplexObs>amrscomplexobs=criteria.list();
		if (null==amrscomplexobs||amrscomplexobs.isEmpty()){
		return null;
		}
		return amrscomplexobs.get(0);
		}

    public List<Field> getComplexConceptFieldUuids() {

        String hql = " FROM Field WHERE concept_id in (select  conceptId from ConceptComplex)";

        Query q = sessionFactory.getCurrentSession().createQuery(hql);

        List<Field> fieldUuids = q.list();
      return fieldUuids;
    }

    public AmrsPersonType saveAmrsPersonType(AmrsPersonType amrspersontype) {
        // TODO Auto-generated method stub

        sessionFactory.getCurrentSession().saveOrUpdate(amrspersontype);

        return amrspersontype;

    }


    @SuppressWarnings("unchecked")
    public List<AmrsPersonType> getAmrsPersonType() {
        // TODO Auto-generated method stub
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AmrsPersonType.class);
        return criteria.list();

    }
    @SuppressWarnings("unchecked")
    public AmrsPersonType getAmrsPersonTypeByUuid(String uuid) {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AmrsPersonType.class).add(
                Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")

        List<AmrsPersonType>amrspersontype=criteria.list();
        if (null==amrspersontype||amrspersontype.isEmpty()){
            return null;
        }
        return amrspersontype.get(0);
    }


	}