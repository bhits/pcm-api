<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	version="2.0"
	xpath-default-namespace="urn:oasis:names:tc:xacml:2.0:policy:schema:os">
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

	<xsl:variable name="patientId"
		select="//Policy/Rule[@Effect='Permit']/Condition//SubjectAttributeDesignator[@AttributeId='urn:oasis:names:tc:xacml:1.0:resource:resource-id']/parent::Apply/following-sibling::AttributeValue/text()" />
	<xsl:variable name="patientIdentifier"
		select="concat($patientId,'^^^','&amp;',$homeCommunityId,'&amp;','ISO')" />

	<xsl:variable name="confidentialityCode" select="'R'" />

	<xsl:variable name="currentDateTimeUtc"
		select="string(adjust-dateTime-to-timezone(current-dateTime(), xs:dayTimeDuration('PT0H')))" />

	<xsl:variable name="currentEffectiveTime">
		<xsl:value-of select="substring($currentDateTimeUtc, 1, 4)" />
		<xsl:value-of select="substring($currentDateTimeUtc, 6, 2)" />
		<xsl:value-of select="substring($currentDateTimeUtc, 9, 2)" />
		<xsl:value-of select="substring($currentDateTimeUtc, 12, 2)" />
		<xsl:value-of select="substring($currentDateTimeUtc, 15, 2)" />
		<xsl:value-of select="substring($currentDateTimeUtc, 18, 2)" />
	</xsl:variable>

	<xsl:variable name="consentStartTime"
		select="//Policy/Rule[@Effect='Permit']/Condition/Apply/Apply[@FunctionId='urn:oasis:names:tc:xacml:1.0:function:dateTime-greater-than-or-equal']/AttributeValue" />
	<xsl:variable name="consentStartTimeInt">
		<xsl:value-of select="substring($consentStartTime,1,4)" />
		<xsl:value-of select="substring($consentStartTime,6,2)" />
		<xsl:value-of select="substring($consentStartTime,9,2)" />
		<xsl:value-of select="substring($consentStartTime,12,2)" />
		<xsl:value-of select="substring($consentStartTime,15,2)" />
		<xsl:value-of select="substring($consentStartTime,18,2)" />
	</xsl:variable>
	<xsl:variable name="consentFinishTime"
		select="//Policy/Rule[@Effect='Permit']/Condition/Apply/Apply[@FunctionId='urn:oasis:names:tc:xacml:1.0:function:dateTime-less-than-or-equal']/AttributeValue" />
	<xsl:variable name="consentFinishTimeInt">
		<xsl:value-of select="substring($consentFinishTime,1,4)" />
		<xsl:value-of select="substring($consentFinishTime,6,2)" />
		<xsl:value-of select="substring($consentFinishTime,9,2)" />
		<xsl:value-of select="substring($consentFinishTime,12,2)" />
		<xsl:value-of select="substring($consentFinishTime,15,2)" />
		<xsl:value-of select="substring($consentFinishTime,18,2)" />
	</xsl:variable>
	<xsl:template match="/">
		<!-- Assert the number of NPIs -->
		<!-- Assert recipientNPI -->
		<xsl:variable name="recipientNPI" select="//Condition//Apply[Apply/SubjectAttributeDesignator[@AttributeId='urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject']]/AttributeValue"/>
		<xsl:choose>
			<xsl:when test="count($recipientNPI) > 1 or count($recipientNPI) = 0">
				<xsl:message terminate="yes">ERROR: There must be exactly 1 (ONE) recipient NPI in the XACML policy. Found: <xsl:value-of select="count($recipientNPI)"/>.</xsl:message>
			</xsl:when>
		</xsl:choose>
		<!-- Assert intermediaryNPI -->
		<xsl:variable name="authorNPI" select="//Condition//Apply[Apply/SubjectAttributeDesignator[@AttributeId='urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject']]/AttributeValue"/>
		<xsl:choose>
			<xsl:when test="count($authorNPI) > 1 or count($authorNPI) = 0">
				<xsl:message terminate="yes">ERROR: There must be exactly 1 (ONE) intermediary NPI in the XACML policy. Found: <xsl:value-of select="count($authorNPI)"/>.</xsl:message>
			</xsl:when>
		</xsl:choose>
		
		<!-- Construct the authorIdentifier -->
		<!--<xsl:variable name="authorIdentifier" select="concat($authorNPI[1],'^^^','&amp;','2.16.840.1.113883.4.6','&amp;','ISO')" />-->
		<!-- 		<xsl:variable name="authorIdentifier" select="$authorNPI[1]" />
 -->
 		<xsl:variable name="authorIdentifier" as="xs:string"
		select="'C2S Health'" />
		
		<ns2:sourceSubmissionClientDto xmlns:ns2="http://remoting.ws.ehr.spirit.com/">
			<!-- XACML Policy -->
			<!--<documents>
				<authorPerson>
					<xsl:value-of select="$patientIdentifier" />
				</authorPerson>
				<!-\- XDSDocumentEntry.classCode: urn:uuid:41a5887f-8865-4c09-adf7-e362475b143a -\->
				<classCode>
					<nodeRepresentation>Consent</nodeRepresentation>
					<schema>Connect-a-thon classCodes</schema>
					<value>Consent</value>
				</classCode>
				<!-\- XDSDocumentEntry.confidentialityCode: urn:uuid:f4f85eac-e6cb-4883-b524-f2705394840f -\->
				<confidentialityCode>
					<nodeRepresentation>
						<xsl:value-of select="$confidentialityCode" />
					</nodeRepresentation>
					<schema>2.16.840.1.113883.5.25</schema>
					<value>
						<xsl:value-of select="$confidentialityCode" />
					</value>
				</confidentialityCode>
				<description>
					<xsl:value-of select="//Description" />
				</description>
				<serviceStartTime>
					<xsl:value-of select="$consentStartTimeInt" />
				</serviceStartTime>
				<serviceStopTime>
					<xsl:value-of select="$consentFinishTimeInt" />
				</serviceStopTime>
				<creationTime>
					<xsl:value-of select="$currentEffectiveTime" />
				</creationTime>
				<patientId>
					<xsl:value-of select="$patientIdentifier" />
				</patientId>
				<!-\- XDSDocumentEntry.formatCode: urn:uuid:a09d5840-386c-46f2-b5ad-9c3699a4309d -\->
				<formatCode>
					<nodeRepresentation>urn:oasis:names:tc:xacml:2.0:policy:schema:os</nodeRepresentation>
					<schema>OASIS</schema>
					<value>XACML Policy</value>
				</formatCode>
				<!-\- XDSDocumentEntry.healthCareFacilityTypeCode: urn:uuid:f33fb8ac-18af-42cc-ae0e-ed0b0bdb91e1 -\->
				<healthcareFacilityCode>
					<nodeRepresentation>Home</nodeRepresentation>
					<schema>Connect-a-thon healthcareFacilityTypeCodes</schema>
					<value>Home</value>
				</healthcareFacilityCode>
				<languageCode>en-us</languageCode>
				<legalAuthenticator>document.legalAuthenticator</legalAuthenticator>
				<mimeType>text/xml</mimeType>
				<name>XACML Policy: </name>
				<!-\- XDSDocumentEntry.practiceSettingCode: urn:uuid:cccf5598-8b07-4b77-a05e-ae952c785ead -\->
				<practiceSettingCode>
					<nodeRepresentation>Home</nodeRepresentation>
					<schema>Connect-a-thon practiceSettingCodes</schema>
					<value>Home</value>
				</practiceSettingCode>
				<!-\- XDSDocumentEntry.typeCode: urn:uuid:f0306f51-975f-434e-a61c-c59651d33983 -\->
				<typeCode>
					<nodeRepresentation>28636-9</nodeRepresentation>
					<schema>LOINC</schema>
					<value>Initial Evaluation Note</value>
				</typeCode>
			</documents>-->
			
			<!-- Signed PDF -->
			<documents>
				<author>
 					<person>
 						<xsl:value-of select="$authorIdentifier" /> 
 					</person>
				</author>
				<!-- XDSDocumentEntry.classCode: urn:uuid:41a5887f-8865-4c09-adf7-e362475b143a -->
				<classCode>
					<nodeRepresentation>Consent</nodeRepresentation>
					<schema>Connect-a-thon classCodes</schema>
					<value>Consent</value>
				</classCode>
				<!-- XDSDocumentEntry.confidentialityCode: urn:uuid:f4f85eac-e6cb-4883-b524-f2705394840f -->
				<confidentialityCode>
					<nodeRepresentation>
						<xsl:value-of select="$confidentialityCode" />
					</nodeRepresentation>
					<schema>2.16.840.1.113883.5.25</schema>
					<value>
						<xsl:value-of select="$confidentialityCode" />
					</value>
				</confidentialityCode>
				<description>
					<xsl:value-of select="//Description" />
				</description>
				<serviceStartTime>
					<xsl:value-of select="$consentStartTimeInt" />
				</serviceStartTime>
				<serviceStopTime>
					<xsl:value-of select="$consentFinishTimeInt" />
				</serviceStopTime>
				<creationTime>
					<xsl:value-of select="$currentEffectiveTime" />
				</creationTime>
				<patientId>
					<xsl:value-of select="$patientIdentifier" />
				</patientId>
				<!-- XDSDocumentEntry.formatCode: urn:uuid:a09d5840-386c-46f2-b5ad-9c3699a4309d -->
				<formatCode>
					<nodeRepresentation>PDF</nodeRepresentation>
					<schema>Connect-a-thon formatCodes</schema>
					<value>PDF</value>
				</formatCode>
				<!-- XDSDocumentEntry.healthCareFacilityTypeCode: urn:uuid:f33fb8ac-18af-42cc-ae0e-ed0b0bdb91e1 -->
				<healthcareFacilityCode>
					<nodeRepresentation>Home</nodeRepresentation>
					<schema>Connect-a-thon healthcareFacilityTypeCodes</schema>
					<value>Home</value>
				</healthcareFacilityCode>
				<languageCode>en-us</languageCode>
				<legalAuthenticator>document.legalAuthenticator</legalAuthenticator>
				<mimeType>application/pdf</mimeType>
				<name>Signed Consent PDF: </name>
				<!-- XDSDocumentEntry.practiceSettingCode: urn:uuid:cccf5598-8b07-4b77-a05e-ae952c785ead -->
				<practiceSettingCode>
					<nodeRepresentation>Home</nodeRepresentation>
					<schema>Connect-a-thon practiceSettingCodes</schema>
					<value>Home</value>
				</practiceSettingCode>
				<!-- XDSDocumentEntry.typeCode: urn:uuid:f0306f51-975f-434e-a61c-c59651d33983 -->
				<typeCode>
					<nodeRepresentation>28636-9</nodeRepresentation>
					<schema>LOINC</schema>
					<value>Initial Evaluation Note</value>
				</typeCode>
			</documents>
			
			<submissionSet>
				<author>
 					<person>
 						<xsl:value-of select="$patientIdentifier" /> 
 					</person>
				</author>
				<!-- XDSSubmissionSet.contentTypeCode: urn:uuid:aa543740-bdda-424e-8c96-df4873be8500 -->
				<contentTypeCode>
					<nodeRepresentation>Initial evaluation</nodeRepresentation>
					<schema>Connect-a-thon contentTypeCodes</schema>
					<value>Initial evaluation</value>
				</contentTypeCode>
				<description>
					<xsl:value-of select="//Description" />
				</description>
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