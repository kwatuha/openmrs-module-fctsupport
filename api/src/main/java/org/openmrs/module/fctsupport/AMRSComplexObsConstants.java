package org.openmrs.module.fctsupport;

/**
 * Created with IntelliJ IDEA.
 * User: alfayo
 * Date: 8/8/13
 * Time: 3:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class AMRSComplexObsConstants {

    public final static String ENCOUNTER_NODE = "/form/encounter";
    public final static String ENCOUNTER_PROVIDER = "encounter.provider_id";
    public final static String ENCOUNTER_LOCATION= "encounter.location_id";
    public final static String ENCOUNTER_ENCOUNTERDATETIME= "encounter.encounter_datetime";

    public final static String PATIENT_NODE = "/form/patient";
    public final static String PATIENT_ID = "patient.patient_id";
    /** The global property key for the datetime submit format.*/
    public static final String GLOBAL_PROP_KEY_DATE_TIME_SUBMIT_FORMAT = "xforms.dateTimeSubmitFormat";
    /** The default submit datetime format. */
    public static final String DEFAULT_DATE_TIME_SUBMIT_FORMAT = "yyyy-MM-dd hh:mm a";

    public final static String ENTER_NODE = "/form/enter";
    public final static String DATE_ENTERED= "date_entered";

    /** The global property key for the date submit format.*/
    public static final String GLOBAL_PROP_KEY_DATE_SUBMIT_FORMAT = "xforms.dateSubmitFormat";

    /** The default submit date format. */
    public static final String DEFAULT_DATE_SUBMIT_FORMAT = "yyyy-MM-dd";//yyyy-mm-dd

    public static final String VELOCITY_TEMPLATE_FOLDER = "/org/openmrs/module/amrscustomization/templates/";

}
