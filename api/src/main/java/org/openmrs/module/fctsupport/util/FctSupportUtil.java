package org.openmrs.module.fctsupport.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.commons.lang.StringUtils;
import org.openmrs.Concept;
import org.openmrs.Field;
import org.openmrs.Form;
import org.openmrs.Location;
import org.openmrs.Obs;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.PersonAddress;
import org.openmrs.PersonAttribute;
import org.openmrs.PersonAttributeType;
import org.openmrs.PersonName;
import org.openmrs.Provider;
import org.openmrs.User;
import org.openmrs.api.ConceptService;
import org.openmrs.api.FormService;
import org.openmrs.api.ObsService;
import org.openmrs.api.PersonService;
import org.openmrs.api.context.Context;
import org.openmrs.module.fctsupport.AMRSComplexObsConstants;
import org.openmrs.module.fctsupport.OpenMRSTableFields;
import org.openmrs.module.fctsupport.api.FCTSupportService;
import org.openmrs.module.fctsupport.model.AmrsComplexObs;
import org.openmrs.module.fctsupport.model.AmrsPersonType;
import org.openmrs.serialization.SerializationException;
import org.openmrs.serialization.SimpleXStreamSerializer;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Created with IntelliJ IDEA.
 * User: alfayo
 * Date: 10/14/13
 * Time: 2:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class FctSupportUtil {


    public static ArrayList<OpenMRSTableFields> getListDefaultNewPersonFields() {
        //build fields for person
        ConceptService conceptService= Context.getConceptService();
        ArrayList<OpenMRSTableFields> listFields=new ArrayList();
        OpenMRSTableFields   familyNameField=new OpenMRSTableFields();
        familyNameField.setOpemrsTable("patient_name");
        familyNameField.setOpemrsTag("otherPerson.family_name") ;
        familyNameField.setOpenmrsAttribute("family_name");
        familyNameField.setFieldCaption("Family Name");
        familyNameField.setUuid(UUID.randomUUID().toString());
        familyNameField.setDefaultValue("$!{lotherPerson.getFamilyName()}");
        familyNameField.setFieldTypeId(2);

        OpenMRSTableFields   middleNameField=new OpenMRSTableFields();
        middleNameField.setOpemrsTable("patient_name");
        middleNameField.setOpemrsTag("otherPerson.middle_name") ;
        middleNameField.setOpenmrsAttribute("middle_name");
        middleNameField.setFieldCaption("Middle Name");
        middleNameField.setUuid(UUID.randomUUID().toString());
        middleNameField.setDefaultValue("$!{otherPerson.getMiddleName()}");
        middleNameField.setFieldTypeId(2);

        OpenMRSTableFields   givenNameField=new OpenMRSTableFields();
        givenNameField.setOpemrsTable("patient_name");
        givenNameField.setOpemrsTag("otherPerson.given_name") ;
        givenNameField.setOpenmrsAttribute("given_name");
        givenNameField.setFieldCaption("Given Name");
        givenNameField.setUuid(UUID.randomUUID().toString());
        givenNameField.setDefaultValue("$!{otherPerson.getGivenName()}");
        givenNameField.setFieldTypeId(2);

        OpenMRSTableFields   genderField=new OpenMRSTableFields();
        genderField.setOpemrsTable("patient");
        genderField.setOpemrsTag("otherPerson.sex") ;
        genderField.setOpenmrsAttribute("gender");
        genderField.setFieldCaption("Gender");
        genderField.setUuid(UUID.randomUUID().toString());
        genderField.setDefaultValue("$!{otherPerson.getGender()}");
        genderField.setFieldTypeId(2);

        OpenMRSTableFields   birthDateEstimatedField=new OpenMRSTableFields();
        birthDateEstimatedField.setOpemrsTable("person");
        birthDateEstimatedField.setOpemrsTag("otherPerson.birthdate_estimated") ;
        birthDateEstimatedField.setOpenmrsAttribute("birthdate_estimated");
        birthDateEstimatedField.setFieldCaption("Estimated Birth date");
        birthDateEstimatedField.setUuid(UUID.randomUUID().toString());
        birthDateEstimatedField.setDefaultValue("$!{patient.getBirthDateEstimated()}");
        birthDateEstimatedField.setFieldTypeId(2);

        OpenMRSTableFields   birthDateField=new OpenMRSTableFields();
        birthDateField.setOpemrsTable("patient");
        birthDateField.setOpemrsTag("otherPerson.birthdate") ;
        birthDateField.setOpenmrsAttribute("birthdate");
        birthDateField.setFieldCaption("Birth date");
        birthDateField.setUuid(UUID.randomUUID().toString());
        birthDateField.setDefaultValue("$!{date.format($otherPerson.getBirthdate())}");
        birthDateField.setFieldTypeId(2);

        OpenMRSTableFields   siblingAgeField=new OpenMRSTableFields();
        siblingAgeField.setConceptId(conceptService.getConcept(5575));
        siblingAgeField.setOpemrsTag("otherPerson.siblingAge") ;
        siblingAgeField.setFieldCaption("Sibling Age");
        siblingAgeField.setUuid(UUID.randomUUID().toString());
        siblingAgeField.setFieldTypeId(1);

        listFields.add(familyNameField) ;
        listFields.add(middleNameField) ;
        listFields.add(givenNameField) ;
        listFields.add(birthDateEstimatedField) ;
        listFields.add(birthDateField) ;
        listFields.add(genderField) ;
        listFields.add(siblingAgeField) ;
        return listFields;
    }

    public static Date fromSubmitString2Date(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(Context.getAdministrationService().getGlobalProperty(
                AMRSComplexObsConstants.GLOBAL_PROP_KEY_DATE_SUBMIT_FORMAT, AMRSComplexObsConstants.DEFAULT_DATE_SUBMIT_FORMAT));
        return dateFormat.parse(date);
    }


    public static Integer getProviderId(String userName) {
        User userProvider;
        Person personProvider;

        // assume its a normal user-name or systemId formatted with a dash
        userProvider = Context.getUserService().getUserByUsername(userName);
        if (userProvider != null) {
            return userProvider.getPerson().getPersonId();
        }

        // next assume it is a internal providerId (Note this is a person_id
        // not a user_id) and try again
        try {
            personProvider = Context.getPersonService().getPerson(Integer.parseInt(userName));
            if (personProvider != null) {
                return personProvider.getPersonId();
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


        // now assume its a systemId without a dash: fix the dash and try again
        if (userName != null && userName.trim() != "") {
            if (userName.indexOf("-") == -1 && userName.length() > 1) {
                userName = userName.substring(0, userName.length() - 1) + "-" + userName.substring(userName.length() - 1);
                userProvider = Context.getUserService().getUserByUsername(userName);
                if (userProvider != null) {
                    return userProvider.getPerson().getPersonId();
                }
            }
        }

        return null;
    }

    public static String cleanLocationEntry(String householdLocation) {
        householdLocation=householdLocation.trim();
        String locationId=null;
        if(householdLocation.length()>0)  {

            if(householdLocation.length()>2) {
                String lastDec=householdLocation.substring(householdLocation.length()-2);
                if (lastDec.equals(".0")) {
                    locationId=householdLocation.substring(0,householdLocation.length()-2);
                } else {
                    locationId= householdLocation;
                }

            }
            else{
                locationId= householdLocation;
            }

        }

        return   locationId;
    }

    /**
     * Retrieves a patient identifier from a patient form
     *
     * @param doc
     * @return patientIdentifier
     */
    public static String getPatientIdentifier(Document doc) {
        NodeList elemList = doc.getDocumentElement().getElementsByTagName("patient");
        if (!(elemList != null && elemList.getLength() > 0)) {
            return null;
        }

        Element patientNode = (Element) elemList.item(0);

        NodeList children = patientNode.getChildNodes();
        int len = patientNode.getChildNodes().getLength();
        for (int index = 0; index < len; index++) {
            Node child = children.item(index);
            if (child.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }

            if ("patient_identifier".equalsIgnoreCase(((Element) child).getAttribute("openmrs_table"))
                    && "identifier".equalsIgnoreCase(((Element) child).getAttribute("openmrs_attribute"))) {
                return child.getTextContent();
            }
        }

        return null;
    }


    public static ArrayList<OpenMRSTableFields> getListRepeatingSectionFields(String otherperson) {
        ConceptService conceptService= Context.getConceptService();
        ArrayList<OpenMRSTableFields> listFields=new ArrayList();

        ///fields

        OpenMRSTableFields   personSearchField=new OpenMRSTableFields();
         personSearchField.setOpemrsTag(otherperson+"person_search_option") ;
        personSearchField.setOpenmrsAttribute("person_search_option");
        personSearchField.setFieldCaption("Person Search ");
        personSearchField.setUuid(UUID.randomUUID().toString());
        personSearchField.setFieldTypeId(2);

        OpenMRSTableFields   personSearchWidgetField=new OpenMRSTableFields();
        personSearchWidgetField.setOpemrsTag(otherperson+"person_find_widget") ;
        personSearchWidgetField.setOpenmrsAttribute("person_find_widget");
        personSearchWidgetField.setFieldCaption("Find");
        personSearchWidgetField.setUuid(UUID.randomUUID().toString());
        personSearchWidgetField.setFieldTypeId(2);

        OpenMRSTableFields   familyNameField=new OpenMRSTableFields();
        familyNameField.setOpemrsTable("patient_name");
        familyNameField.setOpemrsTag(otherperson+"family_name") ;
        familyNameField.setOpenmrsAttribute("family_name");
        familyNameField.setFieldCaption("Family Name");
        familyNameField.setUuid(UUID.randomUUID().toString());
        familyNameField.setDefaultValue("$!{lotherPerson.getFamilyName()}");
        familyNameField.setFieldTypeId(2);

        OpenMRSTableFields   middleNameField=new OpenMRSTableFields();
        middleNameField.setOpemrsTable("patient_name");
        middleNameField.setOpemrsTag(otherperson+"middle_name") ;
        middleNameField.setOpenmrsAttribute("middle_name");
        middleNameField.setFieldCaption("Middle Name");
        middleNameField.setUuid(UUID.randomUUID().toString());
        middleNameField.setDefaultValue("$!{otherPerson.getMiddleName()}");
        middleNameField.setFieldTypeId(2);

        OpenMRSTableFields   givenNameField=new OpenMRSTableFields();
        givenNameField.setOpemrsTable("patient_name");
        givenNameField.setOpemrsTag(otherperson+"given_name") ;
        givenNameField.setOpenmrsAttribute("given_name");
        givenNameField.setFieldCaption("Given Name");
        givenNameField.setUuid(UUID.randomUUID().toString());
        givenNameField.setDefaultValue("$!{otherPerson.getGivenName()}");
        givenNameField.setFieldTypeId(2);

        OpenMRSTableFields   nameCommonlyCalledField=new OpenMRSTableFields();
        nameCommonlyCalledField.setOpemrsTable("patient");
        nameCommonlyCalledField.setOpemrsTag(otherperson+"common_name") ;
        nameCommonlyCalledField.setOpenmrsAttribute("Common name");
        nameCommonlyCalledField.setFieldCaption("Name Commonly Called");
        nameCommonlyCalledField.setUuid(UUID.randomUUID().toString());
        nameCommonlyCalledField.setDefaultValue("$!{patient.getAttribute('Common name').getValue()} ");
        nameCommonlyCalledField.setFieldTypeId(2);

        OpenMRSTableFields   tribeField=new OpenMRSTableFields();
        tribeField.setOpemrsTable("patient");
        tribeField.setOpemrsTag(otherperson+"tribe") ;
        tribeField.setOpenmrsAttribute("Tribe");
        tribeField.setFieldCaption("Tribe");
        tribeField.setUuid(UUID.randomUUID().toString());
        tribeField.setDefaultValue("$!{patient.getAttribute('Tribe').getValue()} ");
        tribeField.setFieldTypeId(2);

        OpenMRSTableFields   maritalStatusField=new OpenMRSTableFields();
        maritalStatusField.setConceptId(conceptService.getConcept(1054));
        maritalStatusField.setOpemrsTag(otherperson+"marital_status") ;
        maritalStatusField.setFieldCaption("Marital Status");
        maritalStatusField.setUuid(UUID.randomUUID().toString());
        maritalStatusField.setFieldTypeId(1);

        OpenMRSTableFields   phoneNumberField=new OpenMRSTableFields();
        phoneNumberField.setOpemrsTable("patient");
        phoneNumberField.setOpemrsTag(otherperson+"phone_number") ;
        phoneNumberField.setOpenmrsAttribute("Contact Phone Number");
        phoneNumberField.setFieldCaption("Phone #");
        phoneNumberField.setUuid(UUID.randomUUID().toString());
        phoneNumberField.setDefaultValue("$!{patient.getAttribute('Contact Phone Number').getValue()} ");
        phoneNumberField.setFieldTypeId(2);

        OpenMRSTableFields   phoneNumberOwnerField=new OpenMRSTableFields();
        phoneNumberOwnerField.setOpemrsTable("patient");
        phoneNumberOwnerField.setOpemrsTag(otherperson+"phone_number_owner") ;
        phoneNumberOwnerField.setOpenmrsAttribute("Cell phone owner name");
        phoneNumberOwnerField.setFieldCaption("Phone # Owner");
        phoneNumberOwnerField.setUuid(UUID.randomUUID().toString());
        phoneNumberOwnerField.setDefaultValue("$!{patient.getAttribute('Cell phone owner name').getValue()} ");
        phoneNumberOwnerField.setFieldTypeId(2);

        OpenMRSTableFields   relationshipToPhoneNumberOwnerField=new OpenMRSTableFields();
        relationshipToPhoneNumberOwnerField.setOpemrsTable("patient");
        relationshipToPhoneNumberOwnerField.setOpemrsTag(otherperson+"relationship_to_alternative_phone_number_owner") ;
        relationshipToPhoneNumberOwnerField.setOpenmrsAttribute("Relationship to phone number owner");
        relationshipToPhoneNumberOwnerField.setFieldCaption("Relationship to Phone # Owner");
        relationshipToPhoneNumberOwnerField.setUuid(UUID.randomUUID().toString());
        relationshipToPhoneNumberOwnerField.setDefaultValue("$!{patient.getAttribute('Relationship to phone number owner').getValue()} ");
        relationshipToPhoneNumberOwnerField.setFieldTypeId(2);

        OpenMRSTableFields   alternativePhoneNumberField=new OpenMRSTableFields();
        alternativePhoneNumberField.setOpemrsTable("patient");
        alternativePhoneNumberField.setOpemrsTag(otherperson+"alternative_phone_number") ;
        alternativePhoneNumberField.setOpenmrsAttribute("Alternative contact phone number");
        alternativePhoneNumberField.setFieldCaption("Alternative Phone #");
        alternativePhoneNumberField.setUuid(UUID.randomUUID().toString());
        alternativePhoneNumberField.setDefaultValue("$!{patient.getAttribute('Alternative contact phone number').getValue()} ");
        alternativePhoneNumberField.setFieldTypeId(2);

        OpenMRSTableFields   alternativePhoneNumberOwnerField=new OpenMRSTableFields();
        alternativePhoneNumberOwnerField.setOpemrsTable("patient");
        alternativePhoneNumberOwnerField.setOpemrsTag(otherperson+"alternative_phone_number_owner") ;
        alternativePhoneNumberOwnerField.setOpenmrsAttribute("Alternative contact phone number owner name");
        alternativePhoneNumberOwnerField.setFieldCaption("Alternative Phone # Owner");
        alternativePhoneNumberOwnerField.setUuid(UUID.randomUUID().toString());
        alternativePhoneNumberOwnerField.setDefaultValue("$!{patient.getAttribute('Alternative contact phone number owner name').getValue()} ");
        alternativePhoneNumberOwnerField.setFieldTypeId(2);

        OpenMRSTableFields   relationshipToAlternativePhoneNumberOwnerField=new OpenMRSTableFields();
        relationshipToAlternativePhoneNumberOwnerField.setOpemrsTable("patient");
        relationshipToAlternativePhoneNumberOwnerField.setOpemrsTag(otherperson+"relationship_alternative_phone_number_owner") ;
        relationshipToAlternativePhoneNumberOwnerField.setOpenmrsAttribute("Relationship to alternative phone owner");
        relationshipToAlternativePhoneNumberOwnerField.setFieldCaption("Relationship to Alternative Phone # Owner");
        relationshipToAlternativePhoneNumberOwnerField.setUuid(UUID.randomUUID().toString());
        relationshipToAlternativePhoneNumberOwnerField.setDefaultValue("$!{patient.getAttribute('Relationship to alternative phone owner').getValue()} ");
        relationshipToAlternativePhoneNumberOwnerField.setFieldTypeId(2);

        OpenMRSTableFields   occupationField=new OpenMRSTableFields();
        occupationField.setOpemrsTable("patient");
        occupationField.setOpemrsTag(otherperson+"occupation") ;
        occupationField.setOpenmrsAttribute("Occupation");
        occupationField.setFieldCaption("Occupation");
        occupationField.setUuid(UUID.randomUUID().toString());
        occupationField.setDefaultValue("$!{patient.getAttribute('Occupation').getValue()} ");
        occupationField.setFieldTypeId(2);

        OpenMRSTableFields   placeOfWorkField=new OpenMRSTableFields();
        placeOfWorkField.setOpemrsTable("patient");
        placeOfWorkField.setOpemrsTag(otherperson+"place_of_work") ;
        placeOfWorkField.setOpenmrsAttribute("Workplace");
        placeOfWorkField.setFieldCaption("Place of Work");
        placeOfWorkField.setUuid(UUID.randomUUID().toString());
        placeOfWorkField.setDefaultValue("$!{patient.getAttribute('Workplace').getValue()} ");
        placeOfWorkField.setFieldTypeId(2);

        OpenMRSTableFields   placeOfWorkDepartmentField=new OpenMRSTableFields();
        placeOfWorkDepartmentField.setOpemrsTable("patient");
        placeOfWorkDepartmentField.setOpemrsTag(otherperson+"place_of_work_department") ;
        placeOfWorkDepartmentField.setOpenmrsAttribute("Workplace department");
        placeOfWorkDepartmentField.setFieldCaption("Place of Work, Department");
        placeOfWorkDepartmentField.setUuid(UUID.randomUUID().toString());
        placeOfWorkDepartmentField.setDefaultValue("$!{patient.getAttribute('Workplace department').getValue()} ");
        placeOfWorkDepartmentField.setFieldTypeId(2);

        OpenMRSTableFields   sectionOrHomesteadField=new OpenMRSTableFields();
        sectionOrHomesteadField.setOpemrsTable("person_address");
        sectionOrHomesteadField.setOpemrsTag(otherperson+"person_address.address2") ;
        sectionOrHomesteadField.setOpenmrsAttribute("address2");
        sectionOrHomesteadField.setFieldCaption("Section/Homestead");
        sectionOrHomesteadField.setUuid(UUID.randomUUID().toString());
        sectionOrHomesteadField.setDefaultValue("$!{patient.getPersonAddress().getAddress2()}");
        sectionOrHomesteadField.setFieldTypeId(2);

        OpenMRSTableFields   residentialAddressTownOrHouseField=new OpenMRSTableFields();
        residentialAddressTownOrHouseField.setOpemrsTable("person_address");
        residentialAddressTownOrHouseField.setOpemrsTag(otherperson+"residential_address_village_or_home") ;
        residentialAddressTownOrHouseField.setOpenmrsAttribute("city_village");
        residentialAddressTownOrHouseField.setFieldCaption("Residential Address (town/house)");
        residentialAddressTownOrHouseField.setUuid(UUID.randomUUID().toString());
        residentialAddressTownOrHouseField.setDefaultValue("$!{patient.getPersonAddress().getCityVillage()}");
        residentialAddressTownOrHouseField.setFieldTypeId(2);

        OpenMRSTableFields   residentialAddressVillageOrHomeField=new OpenMRSTableFields();
        residentialAddressVillageOrHomeField.setOpemrsTable("person_address");
        residentialAddressVillageOrHomeField.setOpemrsTag(otherperson+"person_address.city_village") ;
        residentialAddressVillageOrHomeField.setOpenmrsAttribute("city_village");
        residentialAddressVillageOrHomeField.setFieldCaption("Residential Address (village/home)");
        residentialAddressVillageOrHomeField.setUuid(UUID.randomUUID().toString());
        residentialAddressVillageOrHomeField.setDefaultValue("$!{patient.getPersonAddress().getCityVillage()}");
        residentialAddressVillageOrHomeField.setFieldTypeId(2);

        OpenMRSTableFields   subLocationField=new OpenMRSTableFields();
        subLocationField.setOpemrsTable("person_address");
        subLocationField.setOpemrsTag(otherperson+"sub_location") ;
        subLocationField.setOpenmrsAttribute("address5");
        subLocationField.setFieldCaption("Sub-location");
        subLocationField.setUuid(UUID.randomUUID().toString());
        subLocationField.setDefaultValue("$!{patient.getPersonAddress().getAddress5()}");
        subLocationField.setFieldTypeId(2);

        OpenMRSTableFields   landmarkField=new OpenMRSTableFields();
        landmarkField.setOpemrsTable("person_address");
        landmarkField.setOpemrsTag(otherperson+"landmark") ;
        landmarkField.setOpenmrsAttribute("address3");
        landmarkField.setFieldCaption("Landmark");
        landmarkField.setUuid(UUID.randomUUID().toString());
        landmarkField.setDefaultValue("$!{patient.getPersonAddress().getAddress3()}");
        landmarkField.setFieldTypeId(2);

        OpenMRSTableFields   nearestChurchField=new OpenMRSTableFields();
        nearestChurchField.setOpemrsTable("person_address");
        nearestChurchField.setOpemrsTag(otherperson+"nearest_church") ;
        nearestChurchField.setOpenmrsAttribute("address3");
        nearestChurchField.setFieldCaption("Nearest Church");
        nearestChurchField.setUuid(UUID.randomUUID().toString());
        nearestChurchField.setDefaultValue("$!{patient.getPersonAddress().getAddress3()}");
        nearestChurchField.setFieldTypeId(2);

        OpenMRSTableFields   nearestSchoolField=new OpenMRSTableFields();
        nearestSchoolField.setOpemrsTable("person_address");
        nearestSchoolField.setOpemrsTag(otherperson+"nearest_school") ;
        nearestSchoolField.setOpenmrsAttribute("address3");
        nearestSchoolField.setFieldCaption("Nearest School");
        nearestSchoolField.setUuid(UUID.randomUUID().toString());
        nearestSchoolField.setDefaultValue("$!{patient.getPersonAddress().getAddress3()}");
        nearestSchoolField.setFieldTypeId(2);

        OpenMRSTableFields   nearestShoppingCenterField=new OpenMRSTableFields();
        nearestShoppingCenterField.setOpemrsTable("person_address");
        nearestShoppingCenterField.setOpemrsTag(otherperson+"nearest_shopping_center") ;
        nearestShoppingCenterField.setOpenmrsAttribute("address3");
        nearestShoppingCenterField.setFieldCaption("Nearest Shopping Center");
        nearestShoppingCenterField.setUuid(UUID.randomUUID().toString());
        nearestShoppingCenterField.setDefaultValue("$!{patient.getPersonAddress().getAddress3()}");
        nearestShoppingCenterField.setFieldTypeId(2);

        OpenMRSTableFields   estateField=new OpenMRSTableFields();
        estateField.setOpemrsTable("person_address");
        estateField.setOpemrsTag(otherperson+"estate") ;
        estateField.setOpenmrsAttribute("address3");
        estateField.setFieldCaption("Estate");
        estateField.setUuid(UUID.randomUUID().toString());
        estateField.setDefaultValue("$!{patient.getPersonAddress().getAddress3()}");
        estateField.setFieldTypeId(2);

        OpenMRSTableFields   generalRouteField=new OpenMRSTableFields();
        generalRouteField.setOpemrsTable("person_address");
        generalRouteField.setOpemrsTag(otherperson+"general_route") ;
        generalRouteField.setOpenmrsAttribute("address3");
        generalRouteField.setFieldCaption("General Route");
        generalRouteField.setUuid(UUID.randomUUID().toString());
        generalRouteField.setDefaultValue("$!{patient.getPersonAddress().getAddress3()}");
        generalRouteField.setFieldTypeId(2);

        OpenMRSTableFields   stageField=new OpenMRSTableFields();
        stageField.setOpemrsTable("patient");
        stageField.setOpemrsTag(otherperson+"bus_stage") ;
        stageField.setOpenmrsAttribute("Bus Stage");
        stageField.setFieldCaption("Stage");
        stageField.setUuid(UUID.randomUUID().toString());
        stageField.setDefaultValue("$!{patient.getAttribute('Bus Stage').getValue()} ");
        stageField.setFieldTypeId(2);

        OpenMRSTableFields   plotNumberField=new OpenMRSTableFields();
        plotNumberField.setOpemrsTable("patient");
        plotNumberField.setOpemrsTag(otherperson+"plot_number") ;
        plotNumberField.setOpenmrsAttribute("Plot number");
        plotNumberField.setFieldCaption("Plot");
        plotNumberField.setUuid(UUID.randomUUID().toString());
        plotNumberField.setDefaultValue("$!{patient.getAttribute('Plot number').getValue()} ");
        plotNumberField.setFieldTypeId(2);

        OpenMRSTableFields   wellKnownNeighborField=new OpenMRSTableFields();
        wellKnownNeighborField.setOpemrsTable("patient");
        wellKnownNeighborField.setOpemrsTag(otherperson+"well_known_neighbor") ;
        wellKnownNeighborField.setOpenmrsAttribute("Well known neighbour name");
        wellKnownNeighborField.setFieldCaption("Well Known Neighbor");
        wellKnownNeighborField.setUuid(UUID.randomUUID().toString());
        wellKnownNeighborField.setDefaultValue("$!{patient.getAttribute('Well known neighbour name').getValue()} ");
        wellKnownNeighborField.setFieldTypeId(2);

        OpenMRSTableFields   whomToAskForField=new OpenMRSTableFields();
        whomToAskForField.setOpemrsTable("patient");
        whomToAskForField.setOpemrsTag(otherperson+"whom_to_ask_for") ;
        whomToAskForField.setOpenmrsAttribute("Locator Contact Name");
        whomToAskForField.setFieldCaption("Whom To Ask For");
        whomToAskForField.setUuid(UUID.randomUUID().toString());
        whomToAskForField.setDefaultValue("$!{patient.getAttribute('Locator Contact Name').getValue()} ");
        whomToAskForField.setFieldTypeId(2);

        OpenMRSTableFields   landlordField=new OpenMRSTableFields();
        landlordField.setOpemrsTable("patient");
        landlordField.setOpemrsTag(otherperson+"landlord") ;
        landlordField.setOpenmrsAttribute("Landlord name");
        landlordField.setFieldCaption("Landlord");
        landlordField.setUuid(UUID.randomUUID().toString());
        landlordField.setDefaultValue("$!{patient.getAttribute('Landlord name').getValue()} ");
        landlordField.setFieldTypeId(2);

        OpenMRSTableFields   religiousAffiliationField=new OpenMRSTableFields();
        religiousAffiliationField.setOpemrsTable("patient");
        religiousAffiliationField.setOpemrsTag(otherperson+"religious_affiliation") ;
        religiousAffiliationField.setOpenmrsAttribute("Religious Affiliation");
        religiousAffiliationField.setFieldCaption("Religious Affiliation");
        religiousAffiliationField.setUuid(UUID.randomUUID().toString());
        religiousAffiliationField.setDefaultValue("$!{patient.getAttribute('Religious Affiliation').getValue()} ");
        religiousAffiliationField.setFieldTypeId(2);

        OpenMRSTableFields   locationOfReligiousAffiliationField=new OpenMRSTableFields();
        locationOfReligiousAffiliationField.setOpemrsTable("patient");
        locationOfReligiousAffiliationField.setOpemrsTag(otherperson+"location_of_religious_affiliation") ;
        locationOfReligiousAffiliationField.setOpenmrsAttribute("Location of Religious Worship");
        locationOfReligiousAffiliationField.setFieldCaption("Location (Of Religious Affiliation)");
        locationOfReligiousAffiliationField.setUuid(UUID.randomUUID().toString());
        locationOfReligiousAffiliationField.setDefaultValue("$!{patient.getAttribute('Location of Religious Worship').getValue()} ");
        locationOfReligiousAffiliationField.setFieldTypeId(2);

        OpenMRSTableFields   genderField=new OpenMRSTableFields();
        genderField.setOpemrsTable("patient");
        genderField.setOpemrsTag(otherperson+"sex") ;
        genderField.setOpenmrsAttribute("gender");
        genderField.setFieldCaption("Gender");
        genderField.setUuid(UUID.randomUUID().toString());
        genderField.setDefaultValue("$!{otherPerson.getGender()}");
        genderField.setFieldTypeId(2);

        OpenMRSTableFields   birthDateEstimatedField=new OpenMRSTableFields();
        birthDateEstimatedField.setOpemrsTable("person");
        birthDateEstimatedField.setOpemrsTag(otherperson+"birthdate_estimated") ;
        birthDateEstimatedField.setOpenmrsAttribute("birthdate_estimated");
        birthDateEstimatedField.setFieldCaption("Estimated Birth date");
        birthDateEstimatedField.setUuid(UUID.randomUUID().toString());
        birthDateEstimatedField.setDefaultValue("$!{patient.getBirthDateEstimated()}");
        birthDateEstimatedField.setFieldTypeId(2);

        OpenMRSTableFields   birthDateField=new OpenMRSTableFields();
        birthDateField.setOpemrsTable("patient");
        birthDateField.setOpemrsTag(otherperson+"birthdate") ;
        birthDateField.setOpenmrsAttribute("birthdate");
        birthDateField.setFieldCaption("Birth date");
        birthDateField.setUuid(UUID.randomUUID().toString());
        birthDateField.setDefaultValue("$!{date.format($otherPerson.getBirthdate())}");
        birthDateField.setFieldTypeId(2);


//addfieldstoalist

        listFields.add( personSearchField );
        listFields.add( personSearchWidgetField );
        listFields.add( familyNameField );
        listFields.add( middleNameField );
        listFields.add( givenNameField );
        listFields.add( nameCommonlyCalledField );
        listFields.add( tribeField );
        listFields.add( maritalStatusField );
        listFields.add( phoneNumberField );
        listFields.add( phoneNumberOwnerField );
        listFields.add( relationshipToPhoneNumberOwnerField );
        listFields.add( alternativePhoneNumberField );
        listFields.add( alternativePhoneNumberOwnerField );
        listFields.add( relationshipToAlternativePhoneNumberOwnerField );
        listFields.add( occupationField );
        listFields.add( placeOfWorkField );
        listFields.add( placeOfWorkDepartmentField );
        listFields.add( sectionOrHomesteadField );
        listFields.add( residentialAddressTownOrHouseField );
        listFields.add( residentialAddressVillageOrHomeField );
        listFields.add( subLocationField );
        listFields.add( landmarkField );
        listFields.add( nearestChurchField );
        listFields.add( nearestSchoolField );
        listFields.add( nearestShoppingCenterField );
        listFields.add( estateField );
        listFields.add( generalRouteField );
        listFields.add( stageField );
        listFields.add( plotNumberField );
        listFields.add( wellKnownNeighborField );
        listFields.add( whomToAskForField );
        listFields.add( landlordField );
        listFields.add( religiousAffiliationField );
        listFields.add( locationOfReligiousAffiliationField );

        listFields.add( birthDateEstimatedField );
        listFields.add( birthDateField );
        listFields.add( genderField );

        return listFields;
    }

    public static ArrayList<OpenMRSTableFields> getDefaultRequiredField() {

        ArrayList<OpenMRSTableFields> listFields=new ArrayList();

        OpenMRSTableFields   personTypeSelectorField=new OpenMRSTableFields();
        personTypeSelectorField.setOpemrsTable("amrs_person_type_selector");
        personTypeSelectorField.setOpemrsTag("amrs_person_type_selector") ;
        personTypeSelectorField.setOpenmrsAttribute("amrs_person_type_name");
        personTypeSelectorField.setFieldCaption("Person Type Selector");
        personTypeSelectorField.setUuid(UUID.randomUUID().toString());
        personTypeSelectorField.setFieldTypeId(2);

        listFields.add(personTypeSelectorField);

        return listFields;
    }


    public static Map<Integer, String> getMappedPersonAttributeData(){
        Map<Integer, String> map=new HashMap<Integer, String>() ;
        map.put(20	,"tribe");
        map.put(10	,"phone_number");
        map.put(38	,"phone_number_owner");
        map.put(39	,"relationship_to_alternative_phone_number_owner");
        map.put(40	,"alternative_phone_number");
        map.put(48	,"alternative_phone_number_owner");
        map.put(56	,"relationship_alternative_phone_number_owner");
        map.put(42	,"occupation");
        map.put(41	,"place_of_work");
        map.put(51	,"place_of_work_department");
        map.put(47	,"bus_stage");
        map.put(44	,"plot_number");
        map.put(43	,"well_known_neighbor");
        map.put(55	,"whom_to_ask_for");
        map.put(46	,"landlord");
        map.put(49	,"religious_affiliation");
        map.put(50	,"location_of_religious_affiliation");

        return map;
    }

    public static Map<Integer, String> getMappedConceptData(){
        Map<Integer, String> map=new HashMap<Integer, String>() ;
        map.put(1054	,"marital_status");

        return map;
    }



    //////////migraded
    public static void showResultsR(String xml ) throws SerializationException, ParserConfigurationException, SAXException, IOException, XPathExpressionException,ParseException {
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
                Map<String, String> map = new HashMap<String, String>();

                //Collect General details
                map.put("providerId",providerId.toString());
                map.put("locationId",locationId);
                map.put("encounterDate",encounterDate);
                map.put("formId",formIdStr);
                map.put("patientId",patientIdentifier);
                map.put("complexConceptdMapped",complexConcept.getId().toString());
                //end of generail information


                for (int i = 0; i < complexFieldNodeList.getLength(); ++i) {

                    String subFieldUuid=((Element)complexFieldNodeList.item(i)).getAttribute("openmrs_field_uuid");
                    Field subField=Context.getFormService().getFieldByUuid(subFieldUuid);
                    String subFieldValue=(complexFieldNodeList.item(i)).getFirstChild().getNodeValue();



                    NodeList NodeListChild= complexFieldNodeList.item(i).getChildNodes();

                    // Map<String, String> map = new HashMap<String, String>();

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
                    //String serializedSavedData=F(map,providerId.toString(),patientIdentifier,formIdStr,encounterDate,locationId,complexConcept);
                    //Save Data related to various persons
                    // savePersonTypeDetails(map,providerId.toString(),patientIdentifier,formIdStr,encounterDate,locationId,complexConcept,"parentGuardian");

                    createPersonsFrmSubmitXml(map,providerId.toString(),patientIdentifier,formIdStr,encounterDate,locationId,complexConcept);
                    // savePersonTypeDetails(map,providerId.toString(),patientIdentifier,formIdStr,encounterDate,locationId,complexConcept,"nextOfKin");
                    // savePersonTypeDetails(map,providerId.toString(),patientIdentifier,formIdStr,encounterDate,locationId,complexConcept,"treatmentSupporter");




                }


            }

        }


    }
    //yet other
    public static void savePatientPersonRelationship(Person person ,String encounterDate , String locationId , String providerId , String patientIdentifier , String formId) throws ParseException{

        Location location=Context.getLocationService().getLocation(Integer.parseInt(locationId));
        Provider provider=Context.getProviderService().getProvider(Integer.parseInt(providerId));
        Patient patient=Context.getPatientService().getPatient(Integer.parseInt(patientIdentifier));
        Form form=Context.getFormService().getForm(Integer.parseInt(formId)) ;
        Date strToEncounterDate=FctSupportUtil.fromSubmitString2Date(encounterDate);


        if((person.getId()>0)&&(patient.getPatientId()>0)){
            AmrsComplexObs complexObs= new AmrsComplexObs();
            complexObs.setPatient(patient);
            complexObs.setPerson(person);
            complexObs.setFormId(form);
            complexObs.setEncounterDatetime(strToEncounterDate);
            complexObs.setLocation(location);
            complexObs.setProvider(provider);

            if(Context.isAuthenticated()){
                FCTSupportService service=Context.getService(FCTSupportService.class);
                service.saveAmrscomplexobs(complexObs);
            }

        }

    }

    //other migrade
    public static void createPersonsFrmSubmitXml(Map<String,String>map,String providerId,String patientIdentifier,String formId,String encounterDate,String locationId, Concept complexConcept)
            throws SerializationException,ParseException {
        FCTSupportService service=Context.getService(FCTSupportService.class);
        List<AmrsPersonType> personTpesList=service.getAmrsPersonTypes();
        if(personTpesList.size()>0){
            for(AmrsPersonType personType :personTpesList){
                String fieldName=personType.getFieldName();
                String familyName=map.get(fieldName+"family_name");
                String givenName=map.get(fieldName+"given_name");
                String middleName=map.get(fieldName+"middle_name");
                String personIdofExistingPerson=map.get(fieldName+"person_search_option");
                //ensure we have name for the patient when names are provided when saving new persons
                if((StringUtils.isBlank(personIdofExistingPerson))&&(StringUtils.isNotBlank(fieldName))&&((StringUtils.isNotBlank(familyName))||(StringUtils.isNotBlank(givenName))||(StringUtils.isNotBlank(middleName)))){


                    FctSupportUtil.savePersonTypeDetails(map, providerId.toString(), patientIdentifier, formId, encounterDate, locationId, complexConcept,fieldName);
                }

                //ensure the patient/person is found when saving related person's details
                if(StringUtils.isNotBlank(personIdofExistingPerson)) {
                    Person foundPerson=Context.getPersonService().getPerson(Integer.parseInt(personIdofExistingPerson));


                    savePatientPersonRelationship(foundPerson, map.get("encounterDate"),map.get("locationId"),map.get("providerId"),map.get("patientId") ,map.get("formId")) ;
                }


            }

        }
    }


    //aoth other
    public static  String savePersonTypeDetails(Map<String,
            String>map,String providerId,String patientIdentifier,String formId,String encounterDate,String locationId, Concept complexConcept,String personType) throws SerializationException,ParseException {
        SimpleXStreamSerializer sn=new SimpleXStreamSerializer();
        String serializedData= sn.serialize(map);
        PersonService personService=Context.getPersonService();
        ObsService obsService=Context.getObsService();
        ConceptService conceptService=Context.getConceptService();
        if(!StringUtils.isBlank(serializedData)){
            Person person =new Person();

            PersonName personName= new PersonName();


            personName.setFamilyName(map.get(personType+"family_name"));
            personName.setGivenName(map.get(personType+"given_name"));
            personName.setMiddleName(map.get(personType+"middle_name"));
            personName.setPreferred(true);
            personName.setVoided(false);

            person.addName(personName);
            person.setGender(map.get(personType+"sex"));
            String strBirthDate=StringUtils.trim(map.get(personType+"birthdate"));
            Date birthDate = FctSupportUtil.fromSubmitString2Date(strBirthDate);

            if(map.get(personType+"birthdate_estimated")=="0")
                person.setBirthdateEstimated(true);

            if(map.get(personType+"birthdate_estimated")=="1")
                person.setBirthdateEstimated(false);


            person.setBirthdate(birthDate);

            //Adding Address Details
            PersonAddress address = new PersonAddress();

            address.setAddress2(map.get(personType+"person_address.address2"));

            String  address3="Landmark:"+map.get(personType+"landmark")+
                    "Nearest Church:"+map.get(personType+"nearest_church")+
                    "Nearest School:"+map.get(personType+"nearest_school")+
                    "Nearest Shopping Center:"+map.get(personType+"nearest_shopping_center")+
                    "Estate:"+map.get(personType+"estate")+
                    "General Route:"+map.get(personType+"general_route");
            address.setCityVillage("Town/House:"+map.get(personType+"residential_address_village_or_home")+
                    "Home/House:"+map.get(personType+"person_address.city_village"));
            address.setAddress3(address3);
            address.setAddress5(map.get(personType+"sub_location"));
            person.addAddress(address);


            //add person Attributes

            List<PersonAttribute> listPersonAttributes=getPersonAttributeData(map, FctSupportUtil.getMappedPersonAttributeData(),personType);

            for(PersonAttribute personAttribute:listPersonAttributes) {
                person.addAttribute(personAttribute);
            }


            //Saving person details
            Person personSaved=personService.savePerson(person);
            Integer pid=personSaved.getId();
            if(pid>0){
                map.put("personID",pid.toString()) ;
                /*map.put("providerId",providerId);
                map.put("patientIdentifier",patientIdentifier);
                map.put("formId",formId);
                map.put("encounterDate",encounterDate);
                map.put("locationId",locationId);
*/
                //Now save observations
                List<Obs> listObs=FctSupportUtil.getPersonObsToSave(map,FctSupportUtil.getMappedConceptData(),personSaved,encounterDate,locationId, personType);
                for(Obs obs:listObs) {
                    obsService.saveObs(obs,"Saving Via Complex Obs Handler");

                }

                //save the person relation ship details

                savePatientPersonRelationship(personSaved,encounterDate,locationId,providerId,patientIdentifier ,formId) ;
            }
            //
        }
        //Final serialized data with personId and other essential data
        serializedData=sn.serialize(map);

        return serializedData;
    }


    //end of savinf details of a person type

    public static List<PersonAttribute> getPersonAttributeData(Map<String, String> mappedFormData,Map<Integer, String> mappedPersonAttibutes,String personType){
        List<PersonAttribute> listPersonAttributes=new ArrayList<PersonAttribute>() ;



        for (Map.Entry<Integer, String> entry : mappedPersonAttibutes.entrySet())
        {

            PersonAttributeType personAttributeType = new PersonAttributeType();
            personAttributeType.setPersonAttributeTypeId(entry.getKey()	);
            PersonAttribute personAttribute = new PersonAttribute() ;
            personAttribute.setAttributeType(personAttributeType);
            personAttribute.setValue(mappedFormData.get(personType+entry.getValue()));
            listPersonAttributes.add(personAttribute);

        }


        return listPersonAttributes;
    }

    public static List<Obs> getPersonObsToSave(Map<String, String> mappedFormData,Map<Integer, String> mappedObsTosave,Person person,String encounterDate,String locationId,String personType){
        List<Obs> listObsToSave=new ArrayList<Obs>() ;

        Obs o=new Obs();
        ObsService os = Context.getObsService();
        ConceptService cs = Context.getConceptService();




        for (Map.Entry<Integer, String> entry : mappedObsTosave.entrySet())
        {

            String[] oa = StringUtils.trim(mappedFormData.get(personType+entry.getValue())).split("^");
            String s= StringUtils.trim(mappedFormData.get(personType+entry.getValue()));
            Integer valudeCodedConceptId= Integer.valueOf(s.substring(0,s.indexOf('^') ));
            o.setConcept(cs.getConcept(entry.getKey()));
            o.setDateCreated(new Date());
            o.setCreator(Context.getAuthenticatedUser());
            o.setLocation(new Location(Integer.parseInt(locationId)));
            o.setObsDatetime(new Date());
            o.setValueCoded(cs.getConcept(valudeCodedConceptId));
            o.setPerson(person);
            listObsToSave.add(o);

        }
        return listObsToSave;
    }


    //end migrade

}
