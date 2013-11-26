<%@ include file="/WEB-INF/template/include.jsp" %>
<%@ include file="/WEB-INF/template/header.jsp" %>
<openmrs:require privilege="View Mobile Form Errors" otherwise="/login.htm"
                 redirect="/module/fctsupport/outreachlocator.list"/>



<openmrs:htmlInclude file="/scripts/calendar/calendar.js"/>
<openmrs:htmlInclude file="/scripts/dojoConfig.js"/>
<openmrs:htmlInclude file="/scripts/dojo/dojo.js"/>



<h2>Life in the </h2>




</head>

  Here are the details
<form method="POST" id="complexConceptFieldsform">

    Information retrived by Kenya Developer
    <c:forEach var='conceptlst' items='${model.listFieldTypes}'>
        ${conceptlst.fieldTypeId} </b><br/>
    </c:forEach>


    Information retrived by Kenya Developer  concepts
    <c:forEach var='concept' items='${model.listConcepts}'>
        ${concept.version} </b><br/>
    </c:forEach>

    patient details
    ${model.patient.patientId}

</form>
The

<%@ include file="/WEB-INF/template/footer.jsp" %>



<br/>

<h4 style="border-top: 1px black dashed">Add another note</h4>
<form method="post" action="<openmrs:contextPath/>/module/patientnotes/addNote.form">
    <input type="hidden" name="patient_id" value="${patient.patientId}"/>
    <input type="hidden" name="returnUrl" value="/patientDashboard.form?patientId=${patient.patientId}"/>
    Title: <input type="text" name="title"/><br/>
    Text: <input type="text" name="text"/><br/>
    <input type="submit" value="Add Note"/>
</form>
