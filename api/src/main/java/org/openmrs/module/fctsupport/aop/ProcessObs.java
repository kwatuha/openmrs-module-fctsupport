package org.openmrs.module.fctsupport.aop;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.*;
import org.openmrs.api.FormService;
import org.openmrs.api.PatientService;
import org.openmrs.api.PersonService;
import org.openmrs.api.context.Context;

import org.openmrs.module.fctsupport.api.FCTSupportService;
import org.openmrs.module.fctsupport.model.Amrscomplexobs;
import org.openmrs.serialization.SerializationException;
import org.springframework.aop.Advisor;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.apache.commons.io.IOUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.openmrs.serialization.SimpleXStreamSerializer;
import org.openmrs.module.fctsupport.AMRSComplexObsConstants;
import org.openmrs.module.fctsupport.util.FctSupportUtil;

public class ProcessObs extends StaticMethodMatcherPointcutAdvisor implements Advisor {

    private static final long serialVersionUID = 3333L;
    private DocumentBuilder db;
    private static final Log log = LogFactory.getLog(ProcessObs.class);
    private static final DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    private DocumentBuilder docBuilder;
    private XPathFactory xPathFactory;


    public boolean matches(Method method, Class targetClass) {
        // only 'run' this advice on the getter methods
         if (method.getName().startsWith("getSubmittedXformDo"))
            return true;

        return false;
    }

    @Override
    public Advice getAdvice() {
        log.debug("Getting new around advice");
        return new PrintingAroundAdvice();
    }

    private class PrintingAroundAdvice implements MethodInterceptor {
        public Object invoke(MethodInvocation invocation) throws Throwable {

            log.debug("Before " + invocation.getMethod().getName() + ".");


            Map handlerDocumentMap = new HashMap<String, Document>();

           Object o=invocation.proceed();

            String formData = (String)o;
            docBuilder = docBuilderFactory.newDocumentBuilder();
            XPathFactory xpf = getXPathFactory();
            XPath xp = xpf.newXPath();
            Document doc = docBuilder.parse(IOUtils.toInputStream(formData));

            NodeList nodeList = doc.getElementsByTagName("obs");
            Integer ln=nodeList.getLength()  ;

            showResultsR(formData ) ;



            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);

                if (node.hasAttributes()) {
                    Attr attr = (Attr) node.getAttributes().getNamedItem("openmrs_field_uuid");
                    if (attr != null) {
                        String attribute= attr.getValue();
                        System.out.println("attribute: xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx" + attribute+"\n");
                    }
                }
            }
            /////////////////////

            //////////////////////////////////////////////////
            return o;
        }
    }



    /////////////////////////////////////////////////////////////

    public  void showResultsR(String xml ) throws SerializationException,UnsupportedEncodingException, ParserConfigurationException, SAXException, IOException, XPathExpressionException,ParseException {
       final Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes("UTF-8")));
        final XPath xPath = XPathFactory.newInstance().newXPath();


        FCTSupportService service=Context.getService(FCTSupportService.class);
        List<Field> listComplexConceptFields=service.getComplexConceptFieldUuids();
        Integer cml=listComplexConceptFields.size();
        String providerUsername;
        Integer providerId;
        String locationId;
        String encounterDate;

        if(listComplexConceptFields.size()>0) {

              for(Field complexField:listComplexConceptFields){

                  String fieldUuid = complexField.getUuid();
                  Concept complexConcept=complexField.getConcept();

                  //search form xml by field uuid
                  final XPathExpression expression = xPath.compile("//*[@openmrs_field_uuid='"+fieldUuid+"']");
                  final NodeList complexFieldNodeList = (NodeList) expression.evaluate(doc, XPathConstants.NODESET);

                  //get general form details
                  Node curNode=(Node)  xPath.evaluate(AMRSComplexObsConstants.ENCOUNTER_NODE, doc, XPathConstants.NODE);
                  providerUsername = xPath.evaluate(AMRSComplexObsConstants.ENCOUNTER_PROVIDER, curNode);
                  providerUsername=providerUsername.trim();
                  providerId=FctSupportUtil.getProviderId(providerUsername);

                  //Clean location id by removing decimal points
                  locationId= FctSupportUtil.cleanLocationEntry(xPath.evaluate(AMRSComplexObsConstants.ENCOUNTER_LOCATION, curNode)) ;
                  encounterDate=xPath.evaluate(AMRSComplexObsConstants.ENCOUNTER_ENCOUNTERDATETIME, curNode);

                  curNode=(Node)  xPath.evaluate(AMRSComplexObsConstants.PATIENT_NODE, doc, XPathConstants.NODE);
                  String patientIdentifier = xPath.evaluate(AMRSComplexObsConstants.PATIENT_ID, curNode);
                  String formIdStr=xPath.evaluate("/form/@id", doc);


                 //end of generail information


                  for (int i = 0; i < complexFieldNodeList.getLength(); ++i) {

                      String subFieldUuid=((Element)complexFieldNodeList.item(i)).getAttribute("openmrs_field_uuid");
                      Field subField=Context.getFormService().getFieldByUuid(subFieldUuid);
                      String subFieldValue=(complexFieldNodeList.item(i)).getFirstChild().getNodeValue();



                      NodeList NodeListChild= complexFieldNodeList.item(i).getChildNodes();

                      Map<String, String> map = new HashMap<String, String>();

                      for (int x = 0; x < NodeListChild.getLength(); ++x) {
                          Node node = NodeListChild.item(x);
                              if (node.hasAttributes()) {
                                  Attr attr = (Attr) node.getAttributes().getNamedItem("openmrs_field_uuid");
                                  if (attr != null) {
                                      String attribute= attr.getValue();

                                      String complexConceptData= node.getTextContent();
                                      //map.put(attribute,complexConceptData);
                                      //Now get the field_name
                                      FormService formService=Context.getFormService();
                                      Field field=formService.getFieldByUuid(attribute);
                                      map.put(StringUtils.trim(field.getName()),complexConceptData);
                                  }

                              }





                      }
                      String serializedSavedData=saveOtherPerson(map,providerId.toString(),patientIdentifier,formIdStr,encounterDate,locationId,complexConcept);

                  }


              }

        }


    }

    /**
     * @return XPathFactory to be used for obtaining data from the parsed XML
     */
    private XPathFactory getXPathFactory() {
        if (xPathFactory == null) {
            xPathFactory = XPathFactory.newInstance();
        }
        return xPathFactory;
    }

    public String saveOtherPerson(Map<String, String>map,String providerId,String patientIdentifier,String formId,String encounterDate,String locationId, Concept complexConcept) throws SerializationException,ParseException {
        SimpleXStreamSerializer sn=new SimpleXStreamSerializer();
        String serializedData= sn.serialize(map);
        if(!StringUtils.isBlank(serializedData)){
            Person person =new Person();
            PersonService personService=Context.getPersonService();
            PersonName personName= new PersonName();

            personName.setFamilyName(map.get("otherPerson.family_name"));
            personName.setGivenName(map.get("otherPerson.given_name"));
            personName.setMiddleName(map.get("otherPerson.middle_name"));
            personName.setPreferred(true);
            personName.setVoided(false);

            person.addName(personName);
            person.setGender(map.get("otherPerson.sex"));
            String strBirthDate=StringUtils.trim(map.get("otherPerson.birthdate"));
            Date birthDate = FctSupportUtil.fromSubmitString2Date(strBirthDate);
            person.setBirthdateEstimated(false);
            person.setBirthdate(birthDate);
            PersonAddress address = new PersonAddress();
            address.setAddress1("Eldoret Kenya");
            PersonAddress preferredAddress = new PersonAddress();
            preferredAddress.setAddress1("kakamega Uganda");
            preferredAddress.setPreferred(true);
            preferredAddress.setVoided(true);
            person.addAddress(address);
            person.addAddress(preferredAddress);

            Person personSaved=personService.savePerson(person);
            Integer pid=personSaved.getId();
            if(pid>0){
                map.put("personID",pid.toString()) ;
                map.put("providerId",providerId);
                map.put("patientIdentifier",patientIdentifier);
                map.put("formId",formId);
                map.put("encounterDate",encounterDate);
                map.put("locationId",locationId);

            }
            //
        }
        //Final serialized data with personId and other essential data
        serializedData=sn.serialize(map);

        return serializedData;
    }

    private void saveSerializedPersonData(String data,Concept complexConcept) throws SerializationException {
        SimpleXStreamSerializer sn=new SimpleXStreamSerializer();
        Map<String, String> map=  sn.deserialize(data,Map.class) ;

        Amrscomplexobs amrscomplexobs=new Amrscomplexobs();
        amrscomplexobs.setConceptData(data);
        amrscomplexobs.setConcept(complexConcept);
        //amrscomplexobs.setPatient(Context.getPatientService().getPatientB);
        /*private String conceptData;
        private Date encounterDatetime;
        private Concept concept;
        private Location location;
        private Provider provider;
        private Patient patient;
        private String formId;*/
    }

}