package org.openmrs.module.fctsupport.model;
import org.openmrs.BaseOpenmrsData;

public class AmrsPersonType extends BaseOpenmrsData{

private  String personTypeName;
private  String description;
private  Integer id;
private  Integer displayPosition;
private  String fieldName;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }


    public Integer getDisplayPosition() {
        return displayPosition;
    }

    public void setDisplayPosition(Integer displayPosition) {
        this.displayPosition = displayPosition;
    }

	public Integer getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public void setId(Integer id) {
		// TODO Auto-generated method stub
		this.id=id;
		
	}
	
		public String   getPersonTypeName() {
		
		// TODO Auto-generated method stub
		return  personTypeName;
	    }
		public  void   setPersonTypeName (String  persontypename ) {
		
		// TODO Auto-generated method stub
		 this.personTypeName=persontypename;
		 
	    }
		public String   getDescription() {
		
		// TODO Auto-generated method stub
		return  description;
	    }
		public  void   setDescription (String  description ) {
		
		// TODO Auto-generated method stub
		 this.description=description;
		 
	    }
}