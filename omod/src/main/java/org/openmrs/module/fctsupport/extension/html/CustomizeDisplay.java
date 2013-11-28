package org.openmrs.module.fctsupport.extension.html;

/**
 * This class defines the links that will appear on the administration page under the
 * "fctsupport.title" heading. 
 */
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.String;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.app.event.EventCartridge;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.CommonsLogLogChute;
import org.openmrs.Form;
import org.openmrs.User;
import org.openmrs.api.APIException;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.Extension;

import org.openmrs.module.fctsupport.AMRSComplexObsConstants;
import org.openmrs.util.OpenmrsUtil;
import org.openmrs.util.VelocityExceptionHandler;

/**
 *
 * @author jkeiper
 */
public class CustomizeDisplay extends Extension {

    private final Log log = LogFactory.getLog(getClass());

    @Override
    public MEDIA_TYPE getMediaType() {
        return MEDIA_TYPE.html;
    }

    @Override
    public String getOverrideContent(String bodyContent) {
        User user = Context.getAuthenticatedUser();
       // List<Form> forms = Context.getService(AMRSCustomizationService.class).getPopularRecentFormsForUser(user);

        Map<String, Object> modelMap = new HashMap<String, Object>();
        modelMap.put("forms", "kwatuha alfayo");
        modelMap.put("user", user);

        Map<String, String> parameterMap = this.getParameterMap();
        if (!parameterMap.containsKey("patientId"))
            return "oh man ... no patient!";

        // TODO this is hacky -- need to get personId from patient, but I'm lazy.
        modelMap.put("personId", parameterMap.get("patientId"));
        modelMap.put("patientId", parameterMap.get("patientId"));

        modelMap.put("months", 1);

        return renderHTML("formsHotlist.html", modelMap);
    }

    protected String renderHTML(String templateName, Map<String, Object> modelMap) {
        AdministrationService as = Context.getAdministrationService();

        // set up the engine
        VelocityEngine velocityEngine = new VelocityEngine();

        velocityEngine.setProperty(
                RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS,
                "org.apache.velocity.runtime.log.CommonsLogLogChute");
        velocityEngine.setProperty(
                CommonsLogLogChute.LOGCHUTE_COMMONS_LOG_NAME,
                "amrscustomization_velocity");

        try {
            velocityEngine.init();
        } catch (Exception e) {
            log.error("Error initializing Velocity engine", e);
        }

        VelocityContext velocityContext = new VelocityContext();

        // add the error handler
        EventCartridge ec = new EventCartridge();
        ec.addEventHandler(new VelocityExceptionHandler());
        velocityContext.attachEventCartridge(ec);

        // Set up velocity context
        for (Entry<String, Object> entry: modelMap.entrySet())
            velocityContext.put(entry.getKey(), entry.getValue());

        // get template
        String template = "";

        // grab resource folder using Class.getResource()
        Class c = this.getClass();
        URL url = c.getResource(AMRSComplexObsConstants.VELOCITY_TEMPLATE_FOLDER);
        if (url == null) {
            String err = "Could not open resource folder directory: "
                    + AMRSComplexObsConstants.VELOCITY_TEMPLATE_FOLDER;
            log.error(err);
            throw new APIException(err);
        }
        File templateFolder = OpenmrsUtil.url2file(url);

        // look for the template and load it
        if (OpenmrsUtil.folderContains(templateFolder, templateName)) {
            File templateFile = new File(templateFolder, templateName);
            try {
                template = OpenmrsUtil.getFileAsString(templateFile);
            } catch (IOException e) {
                log.error("could not read velocity template: " + templateName, e);
            }
        }

        // process the velocity script
        StringWriter swriter = new StringWriter();
        PrintWriter pwriter = new PrintWriter(swriter);
        try {
            velocityEngine.evaluate(velocityContext, pwriter, "Forms Hotlist", template);
        } catch (Exception e) {
            log.error(e);
        } finally {
            pwriter.close();
            velocityContext = null;
            velocityEngine = null;
            template = null;
        }

        // send back the evaluation
        return swriter.toString();
    }
}