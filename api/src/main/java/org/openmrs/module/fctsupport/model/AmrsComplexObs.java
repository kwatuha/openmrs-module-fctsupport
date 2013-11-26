package org.openmrs.module.fctsupport.model;


import java.util.Date;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.Concept;
import org.openmrs.Form;
import org.openmrs.Location;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.Provider;

public class AmrsComplexObs extends BaseOpenmrsData {


    private Integer id;
    private Person person;
    private Date encounterDatetime;
    private Location location;
    private Provider provider;
    private Patient patient;
    private Form formId;

    public Form getFormId() {
        return formId;
    }

    public void setFormId(Form formId) {
        this.formId = formId;
    }
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getEncounterDatetime() {
        return encounterDatetime;
    }

    public void setEncounterDatetime(Date encounterDatetime) {
        this.encounterDatetime = encounterDatetime;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    }
	