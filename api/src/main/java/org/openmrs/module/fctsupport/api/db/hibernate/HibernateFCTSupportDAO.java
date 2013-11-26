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
package org.openmrs.module.fctsupport.api.db.hibernate;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.openmrs.Field;
import org.openmrs.module.fctsupport.api.db.FCTSupportDAO;
import org.openmrs.module.fctsupport.model.AmrsComplexObs;
import org.openmrs.module.fctsupport.model.AmrsPersonType;

/**
 * It is a default implementation of  {@link FCTSupportDAO}.
 */
public class HibernateFCTSupportDAO implements FCTSupportDAO {
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private SessionFactory sessionFactory;
	
	/**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
	    this.sessionFactory = sessionFactory;
    }
    
	/**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
	    return sessionFactory;
    }

    public List<Field> getComplexConceptFieldUuids() {

        String hql = " FROM Field WHERE concept_id in (select  conceptId from ConceptComplex)";

        Query q = sessionFactory.getCurrentSession().createQuery(hql);

        List<Field> fieldUuids = q.list();
        return fieldUuids;
    }

    public AmrsComplexObs saveAmrscomplexobs(AmrsComplexObs amrsComplexObs) {
        // TODO Auto-generated method stub

        sessionFactory.getCurrentSession().saveOrUpdate(amrsComplexObs);

        return amrsComplexObs;

    }


    @SuppressWarnings("unchecked")
    public List<AmrsComplexObs> getAmrscomplexobs() {
        // TODO Auto-generated method stub


        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AmrsComplexObs.class);


        return criteria.list();

    }




    @SuppressWarnings("unchecked")
    public AmrsComplexObs getAmrscomplexobsByUuid(String uuid) {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AmrsComplexObs.class).add(
                Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")

        List<AmrsComplexObs>amrscomplexobs=criteria.list();
        if (null==amrscomplexobs||amrscomplexobs.isEmpty()){
            return null;
        }
        return amrscomplexobs.get(0);
    }

    public List<AmrsPersonType> getAmrsPersonTypes(){
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AmrsPersonType.class);


        return criteria.list();

    }

    public AmrsPersonType saveAmrsPersonType(AmrsPersonType amrsPersonType) {

        sessionFactory.getCurrentSession().saveOrUpdate(amrsPersonType);

        return amrsPersonType;

    }

    public AmrsPersonType getAmrsPersonTypeByUuid(String uuid) {

        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(AmrsPersonType.class).add(
                Expression.eq("uuid", uuid));

        @SuppressWarnings("unchecked")

        List<AmrsPersonType>amrsPersonTypes=criteria.list();
        if (null==amrsPersonTypes||amrsPersonTypes.isEmpty()){
            return null;
        }
        return amrsPersonTypes.get(0);
    }
}