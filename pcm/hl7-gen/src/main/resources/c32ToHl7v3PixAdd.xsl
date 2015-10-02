<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema"
    exclude-result-prefixes="xs"
    xmlns:hl7="urn:hl7-org:v3"
    version="2.0">
    <xsl:output method="xml" version="1.0" indent="yes" encoding="UTF-8"/>   
 <xsl:template match="/">
    
     <urn:PRPA_IN201301UV02 xmlns:urn="urn:hl7-org:v3"  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="urn:hl7-org:v3 ../schema/HL7V3/NE2008/multicacheschemas/PRPA_IN201301UV02.xsd"
         xmlns="urn:hl7-org:v3" ITSVersion="XML_1.0">
         <urn:id root="21acf7be-007c-41e6-b176-d0969794983b"/>
         <urn:creationTime value="20091112115139"/>
         <urn:interactionId extension="PRPA_IN201301UV02" root="2.16.840.1.113883.1.6"/>
         <urn:processingCode code="P"/>
         <urn:processingModeCode code="T"/>
         <urn:acceptAckCode code="AL"/>
         <urn:receiver typeCode="RCV">
             <urn:device classCode="DEV" determinerCode="INSTANCE">
                 <urn:id root="1.2.840.114350.1.13.99999.4567"/>
                 <urn:asAgent classCode="AGNT">
                     <urn:representedOrganization determinerCode="INSTANCE" classCode="ORG">
                         <urn:id root="1.2.840.114350.1.13.99999.1234"/>
                     </urn:representedOrganization>
                 </urn:asAgent>
             </urn:device>
         </urn:receiver>
         <urn:sender typeCode="SND">
             <urn:device classCode="DEV" determinerCode="INSTANCE">
                 <urn:id root="1.2.840.114350.1.13.99998.8734"/>
                 <urn:asAgent classCode="AGNT">
                     <urn:representedOrganization determinerCode="INSTANCE" classCode="ORG">
                         <urn:id root="1.2.840.114350.1.13.99998"/>
                     </urn:representedOrganization>
                 </urn:asAgent>
             </urn:device>
         </urn:sender>
         <urn:controlActProcess classCode="CACT" moodCode="EVN">
             <urn:code code="PRPA_TE201301UV02" codeSystem="2.16.840.1.113883.1.6"/>
             <urn:subject typeCode="SUBJ">
                 <urn:registrationEvent classCode="REG" moodCode="EVN">
                     <urn:id nullFlavor="NA"/>
                     <urn:statusCode code="active"/>
                     <urn:subject1 typeCode="SBJ">
                         <urn:patient classCode="PAT">
                             <urn:id extension="{/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:id/@extension}" assigningAuthorityName="NIST2010" root="2.16.840.1.113883.3.72.5.9.1"/>
                             <urn:statusCode code="active"/>
                             <urn:patientPerson>
                                 <urn:name>
                                     <urn:given><xsl:value-of select="/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:patient/hl7:name/hl7:given"/></urn:given>
                                     <urn:family><xsl:value-of select="/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:patient/hl7:name/hl7:family"/></urn:family>
                                 </urn:name>
                                 <urn:telecom use="H" value="tel:610-220-4354"/>
                                 <urn:administrativeGenderCode code="{/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:patient/hl7:administrativeGenderCode/@code}"/>
                                 <urn:birthTime value="{/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:patient/hl7:birthTime/@value}"/>
                                 <urn:addr>
                                     <urn:streetAddressLine><xsl:value-of select="/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:addr/hl7:streetAddressLine"/></urn:streetAddressLine>
                                     <urn:city><xsl:value-of select="/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:addr/hl7:city"/></urn:city>
                                     <urn:state><xsl:value-of select="/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:addr/hl7:state"/></urn:state>
                                     <urn:postalCode><xsl:value-of select="/hl7:ClinicalDocument/hl7:recordTarget/hl7:patientRole/hl7:addr/hl7:postalCode"/></urn:postalCode>
                                 </urn:addr>
                                 <urn:asOtherIDs classCode="CIT">
                                     <urn:id root="2.16.840.1.113883.4.1" extension="197-18-9761"/>
                                     <urn:scopingOrganization classCode="ORG" determinerCode="INSTANCE">
                                         <urn:id root="2.16.840.1.113883.4.1"/>
                                     </urn:scopingOrganization>
                                 </urn:asOtherIDs>
                                 <urn:personalRelationship classCode="PRS">
                                     <urn:code codeSystem="2.16.840.1.113883.5.111" codeSystemName="PersonalRelationshipRoleType" code="MTH" displayName="Mother"/>
                                     <urn:relationshipHolder1 classCode="PSN" determinerCode="INSTANCE">
                                     </urn:relationshipHolder1>
                                 </urn:personalRelationship>
                             </urn:patientPerson>
                             <urn:providerOrganization classCode="ORG" determinerCode="INSTANCE">
                                 <urn:id root="1.2.840.114350.1.13.99998.8734"/>
                                 <urn:contactParty classCode="CON"/>
                             </urn:providerOrganization>
                         </urn:patient>
                     </urn:subject1>
                     <urn:custodian typeCode="CST">
                         <urn:assignedEntity classCode="ASSIGNED">
                             <urn:id root="1.2.840.114350.1.13.99998.8734"/>
                         </urn:assignedEntity>
                     </urn:custodian>
                 </urn:registrationEvent>
             </urn:subject>
         </urn:controlActProcess>
     </urn:PRPA_IN201301UV02>
     
 </xsl:template>   
    
    
    
</xsl:stylesheet>