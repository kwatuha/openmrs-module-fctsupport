
/**
 * 
 */
package org.openmrs.module.fctsupport.extension.html;

import org.openmrs.module.web.extension.PatientDashboardTabExt;

/**
 * @author Ampath Developers
 *
 */
public class AmrscomplexobsPatientDashboardTab extends PatientDashboardTabExt {

	/* (non-Javadoc)
	 * @see org.openmrs.module.web.extension.PatientDashboardTabExt#getPortletUrl()
	 */
	@Override
	public String getPortletUrl() {
		// TODO Auto-generated method stub
		return "outreachlocator";
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.web.extension.PatientDashboardTabExt#getRequiredPrivilege()
	 */
	@Override
	public String getRequiredPrivilege() {
		// TODO Auto-generated method stub
		return "Manage Outreach Locators";
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.web.extension.PatientDashboardTabExt#getTabId()
	 */
	@Override
	public String getTabId() {
		// TODO Auto-generated method stub
		return "outreachlocator";
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.web.extension.PatientDashboardTabExt#getTabName()
	 */
	@Override
	public String getTabName() {
		// TODO Auto-generated method stub
		return "Out Reach Locator Forms";
	}

}
