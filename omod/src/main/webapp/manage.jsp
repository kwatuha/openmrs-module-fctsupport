<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>

<%@ include file="template/localHeader.jsp"%>
<openmrs:htmlInclude file="/dwr/interface/DWRFctSupportService.js"/>


<openmrs:htmlInclude file="/scripts/calendar/calendar.js"/>
<openmrs:htmlInclude file="/scripts/dojoConfig.js"/>
<openmrs:htmlInclude file="/scripts/dojo/dojo.js"/>


<openmrs:htmlInclude file="/moduleResources/fctsupport/js/jquery.dataTables.min.js"/>
<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables.css"/>
<openmrs:htmlInclude file="/moduleResources/fctsupport/css/smoothness/jquery-ui-1.8.16.custom.css"/>
<openmrs:htmlInclude file="/moduleResources/fctsupport/css/dataTables_jui.css"/>


<script>
    var $j= jQuery.noConflict();

    function showResponse(responseStr){
        alert(responseStr) ;
    }

    function savePersonType(){

        var persontypename=$j("#amrs_person_type").val();
        var displayPosition=$j("#display_position").val();
        var fieldName=$j("#amrs_person_type_field_name").val();
        var description=$j("#amrs_person_type_description").val();

        if(persontypename){

            DWRFctSupportService.savePersonTypeDwr(showResponse) ;
            //DWRFctSupportService.saveAmrsPersonType(persontypename,displayPosition,fieldName,description,showResponse);
        }

    }




    function savePersonTypeInformation(){

        var persontypename=$j("#amrs_person_type").val();
        var displayPosition=$j("#display_position").val();
        var fieldName=$j("#amrs_person_type_field_name").val();
        var description=$j("#amrs_person_type_description").val();

        if(persontypename){

          DWRFctSupportService.savePersonTypeDetails(persontypename,displayPosition,fieldName,description,showResponse);
        }

    }
</script>
<form id="frmPersonType">
<p>Hello ${user.systemId}!</p>

<legend>Define Person Type</legend>
<table>
    <tr>
        <td>Person Type Name</td>
        <td><input type='text' id='amrs_person_type'></td>
    </tr>

    <tr>
        <td>Field Name</td>
        <td><input id='amrs_person_type_field_name'/></td>
    </tr>

    <tr>
        <td>Display Position</td>
        <td><input id='display_position'/></td>
    </tr>

    <tr>
        <td>Description </td>
        <td><textarea id='amrs_person_type_description'></textarea></td>
    </tr>

    <tr>
        <td> show </td>
        <td><input type='button' value='Save PersonType dwr' onclick="savePersonTypeInformation()"/> </td>

        <td><input type='button' value='Save PersonType dwr 3' onclick="savePersonType()"/> </td>
    </tr>
</table>
</form>
<%@ include file="/WEB-INF/template/footer.jsp"%>