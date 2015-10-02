<?xml version="1.0" encoding="UTF-8"?>
<!-- <xsl:stylesheet  -->
<!-- xmlns:xsl="http://www.w3.org/1999/XSL/Transform" -->
<!-- 	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:sdtc="urn:hl7-org:sdtc" -->
<!-- 	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" -->
<!-- 	exclude-result-prefixes="xs sdtc" version="2.0" -->
<!-- 	xpath-default-namespace="urn:hl7-org:v3" xmlns:lcm="urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0" -->
<!-- 	xmlns:rim="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0" xmlns:rs="urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0" -->
<!-- 	xmlns="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0"> -->
	
	<xsl:stylesheet	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	version="2.0"
	xpath-default-namespace="urn:hl7-org:v3">
	
	<xsl:output indent="yes" omit-xml-declaration="yes" />

	<xsl:param name="homeCommunityId" as="xs:string" />
	<xsl:param name="XDSDocumentEntry_uniqueId" as="xs:string" />
	<xsl:param name="XDSSubmissionSet_uniqueId" as="xs:string" />
	<xsl:param name="XDSSubmissionSet_sourceId" as="xs:string"
		select="'1.3.6.1.4.1.21367.13.2015'" />
	<xsl:param name="XDSDocumentEntry_entryUUID" as="xs:string"
		select="'Document01'" />
	<xsl:param name="XDSSubmissionSet_entryUUID" as="xs:string"
		select="'SubmissionSet'" />

	<xsl:variable name="IsConsentCda" select="count(/ClinicalDocument/templateId[@root='2.16.840.1.113883.3.445.1'])>0"></xsl:variable>
	
	<xsl:variable name="XDSDocumentEntry_classCode">
		<xsl:choose>
			<xsl:when test="$IsConsentCda">
				<xsl:value-of select="'Consent'"></xsl:value-of>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="'Consult'" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>
	
	<xsl:variable name="XDSDocumentEntry_classCodeDisplayName">
		<xsl:choose>
			<xsl:when test="$IsConsentCda">
				<xsl:value-of select="'Consent Notes'"></xsl:value-of>
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="'Consult Notes'" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>

	<xsl:variable name="patientRole"
		select="//ClinicalDocument/recordTarget/patientRole" />

	<xsl:variable name="patientIdentifier"
		select="string-join((concat($patientRole/id/@extension, '^^^'), $homeCommunityId, 'ISO'), '&amp;')" />

	<xsl:variable name="author"
		select="//ClinicalDocument/author/assignedAuthor" />
		
		<xsl:variable name="authorIdentifier" select="$author/id/@extension"/>

	<xsl:variable name="confidentialityCode_orig"
		select="//ClinicalDocument/confidentialityCode/@code" />
	
	<xsl:variable name="confidentialityCode">
		<xsl:choose>
			<xsl:when test="not(//ClinicalDocument/confidentialityCode/@code)">
				<xsl:value-of select="'N'" />
			</xsl:when>
			<xsl:when test="$confidentialityCode_orig=''">
				<xsl:value-of select="'N'" />
			</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$confidentialityCode_orig" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:variable>

	<xsl:variable name="documentTypeInLoincCode" select="//ClinicalDocument/code/@code" />

	<xsl:variable name="documentTypeInLoincDisplayName"
		select="//ClinicalDocument/code/@displayName" />

	<xsl:variable name="currentDateTimeUtc" select="string(adjust-dateTime-to-timezone(current-dateTime(), xs:dayTimeDuration('PT0H')))" />

	<xsl:variable name="currentEffectiveTime">
		<xsl:value-of select="substring($currentDateTimeUtc, 1, 4)" />
		<xsl:value-of select="substring($currentDateTimeUtc, 6, 2)" />
		<xsl:value-of select="substring($currentDateTimeUtc, 9, 2)" />
		<xsl:value-of select="substring($currentDateTimeUtc, 12, 2)" />
		<xsl:value-of select="substring($currentDateTimeUtc, 15, 2)" />
		<xsl:value-of select="substring($currentDateTimeUtc, 18, 2)" />
	</xsl:variable>

	<xsl:template match="/">
		
 		<!-- Construct the authorIdentifier --> 
<!-- 		<xsl:variable name="authorIdentifier" select="concat($authorNPI[1],'^^^','&amp;','2.16.840.1.113883.4.6','&amp;','ISO')" /> -->
		
		<ns2:sourceSubmissionClientDto xmlns:ns2="http://remoting.ws.ehr.spirit.com/">
			<!-- C32 -->
			<documents>
				<author>
					
 					<person>
 						<xsl:value-of select="$authorIdentifier" /> 
 					</person>
 				
				</author>
				
				<!-- XDSDocumentEntry.classCode: urn:uuid:41a5887f-8865-4c09-adf7-e362475b143a -->
				<classCode>
					<nodeRepresentation><xsl:value-of select="$XDSDocumentEntry_classCode"/></nodeRepresentation>
					<schema>Connect-a-thon classCodes</schema>
					<value><xsl:value-of select="$XDSDocumentEntry_classCodeDisplayName"/></value>
				</classCode>
				<!-- XDSDocumentEntry.confidentialityCode: urn:uuid:f4f85eac-e6cb-4883-b524-f2705394840f -->
				<confidentialityCode>
					<nodeRepresentation><xsl:value-of select="$confidentialityCode"/></nodeRepresentation>
					<schema>2.16.840.1.113883.5.25</schema>
					<value><xsl:value-of select="$confidentialityCode"/></value>
				</confidentialityCode>
				<description>Summarization of episode</description>
<!-- 				<serviceStartTime> -->
<!-- 					<xsl:value-of select="$consentStartTimeInt" /> -->
<!-- 				</serviceStartTime> -->
<!-- 				<serviceStopTime> -->
<!-- 					<xsl:value-of select="$consentFinishTimeInt" /> -->
<!-- 				</serviceStopTime> -->
				<creationTime>
					<xsl:value-of select="$currentEffectiveTime" />
				</creationTime>
				<patientId>
					<xsl:value-of select="$patientIdentifier" />
				</patientId>
				<!-- XDSDocumentEntry.formatCode: urn:uuid:a09d5840-386c-46f2-b5ad-9c3699a4309d -->
				<formatCode>
					<nodeRepresentation>2.16.840.1.113883.10.20.1</nodeRepresentation>
					<schema>HITSP</schema>
					<value>HL7 CCD Document</value>
				</formatCode>
				<!-- XDSDocumentEntry.healthCareFacilityTypeCode: urn:uuid:f33fb8ac-18af-42cc-ae0e-ed0b0bdb91e1 -->
				<healthcareFacilityCode>
					<nodeRepresentation>OF</nodeRepresentation>
					<schema>2.16.840.1.113883.5.11</schema>
					<value>Outpatient facility</value>
				</healthcareFacilityCode>
				<languageCode><xsl:value-of select="//ClinicalDocument/languageCode/@code" /></languageCode>
				<legalAuthenticator>document.legalAuthenticator</legalAuthenticator>
				<mimeType>text/xml</mimeType>
				<name>Summarization of episode</name>
				<!-- XDSDocumentEntry.practiceSettingCode: urn:uuid:cccf5598-8b07-4b77-a05e-ae952c785ead -->
				<practiceSettingCode>
					<nodeRepresentation>Psychiatry</nodeRepresentation>
					<schema>Connect-a-thon practiceSettingCodes</schema>
					<value>Psychiatry</value>
				</practiceSettingCode>
				<!-- XDSDocumentEntry.typeCode: urn:uuid:f0306f51-975f-434e-a61c-c59651d33983 -->
				<typeCode>
					<nodeRepresentation><xsl:value-of select="$documentTypeInLoincCode"/></nodeRepresentation>
					<schema>LOINC</schema>
					<value><xsl:value-of select="$documentTypeInLoincDisplayName"/></value>
				</typeCode>
			</documents>
			
			<submissionSet>
				<author>
 					<person>
 						<xsl:value-of select="$authorIdentifier" /> 						
 					</person>
				</author>
				<!-- XDSSubmissionSet.contentTypeCode: urn:uuid:aa543740-bdda-424e-8c96-df4873be8500 -->
				<contentTypeCode>
					<nodeRepresentation>Summarization of episode</nodeRepresentation>
					<schema>Connect-a-thon contentTypeCodes</schema>
					<value><xsl:value-of select="$documentTypeInLoincDisplayName"/></value>
				</contentTypeCode>
				<description>Summarization of episode</description>
				<submissionTime>
					<xsl:value-of select="$currentEffectiveTime" />
				</submissionTime>
				<patientId>
					<xsl:value-of select="$patientIdentifier" />
				</patientId>
				<sourceId>
					<xsl:value-of select="$XDSSubmissionSet_sourceId" />
				</sourceId>
			</submissionSet>
		</ns2:sourceSubmissionClientDto>
	</xsl:template>
</xsl:stylesheet>