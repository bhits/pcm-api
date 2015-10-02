<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:sdtc="urn:hl7-org:sdtc"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	exclude-result-prefixes="xs sdtc" version="2.0"
	xpath-default-namespace="urn:hl7-org:v3" xmlns:lcm="urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0"
	xmlns:rim="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0" xmlns:rs="urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0"
	xmlns="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0">

	<!-- Generate XDM metadata from a tagged and redacted C32 document. -->

	<xsl:output indent="yes" omit-xml-declaration="yes" />

	<xsl:variable name="ruleExecutionResponseContainer"
		select="document('ruleExecutionResponseContainer')" />

	<xsl:param name="authorTelecommunication" as="xs:string"
		select="'john.do@direct.com'" />

	<xsl:param name="intendedRecipient" as="xs:string"
		select="'joe.bloggs@direct.com'" />

	<xsl:param name="homeCommunityId" as="xs:string" select="'1.1.1.1.1'" />

	<xsl:variable name="patientRole"
		select="//ClinicalDocument/recordTarget/patientRole" />

	<xsl:variable name="patientIdentifier"
		select="string-join((concat($patientRole/id/@extension, '^^^'), $homeCommunityId, 'ISO'), '&amp;')" />

	<xsl:variable name="author"
		select="//ClinicalDocument/author/assignedAuthor" />


	<!--xsl:variable name="authorIdentifier" select="string-join((concat($author/id/@extension, 
		'^^^'), $author/id/@root, 'ISO'), '&amp;')"/ -->

	<xsl:variable name="authorIdentifier"
		select="string-join((concat('100010020002', '^^^'), $homeCommunityId, 'ISO'), '&amp;')" />

	<xsl:variable name="confidentialityCode"
		select="//ClinicalDocument/confidentialityCode/@code" />

	<xsl:variable name="documentTypeInLoincCode" select="//ClinicalDocument/code/@code" />

	<xsl:variable name="documentTypeInLoincDisplayName"
		select="//ClinicalDocument/code/@displayName" />

	<xsl:variable name="currentDateTimeUtc"
		select="string(adjust-dateTime-to-timezone(current-dateTime(), xs:dayTimeDuration('PT0H')))" />

	<xsl:variable name="currentEffectiveTime">
		<xsl:value-of select="substring($currentDateTimeUtc, 1, 4)" />
		<xsl:value-of select="substring($currentDateTimeUtc, 6, 2)" />
		<xsl:value-of select="substring($currentDateTimeUtc, 9, 2)" />
		<xsl:value-of select="substring($currentDateTimeUtc, 12, 2)" />
		<xsl:value-of select="substring($currentDateTimeUtc, 15, 2)" />
		<xsl:value-of select="substring($currentDateTimeUtc, 18, 2)" />
		<xsl:value-of select="substring($currentDateTimeUtc, 21, 2)" />
		<xsl:text>+0000</xsl:text>
	</xsl:variable>


	<xsl:template match="/">


		<lcm:SubmitObjectsRequest xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xmlns:lcm="urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0" xmlns:rim="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0"
			xmlns:rs="urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0" xmlns="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0">

			<RegistryObjectList>
				<ExtrinsicObject mimeType="text/xml"
					objectType="urn:uuid:7edca82f-054d-47f2-a032-9b2a5b5186c1" id="Document01">
					<Slot name="creationTime">
						<ValueList>
							<Value>
								<xsl:value-of select="$currentEffectiveTime" />
							</Value>
						</ValueList>
					</Slot>
					<Slot name="languageCode">
						<ValueList>
							<Value>
								<xsl:value-of select="//ClinicalDocument/languageCode/@code" />
							</Value>
						</ValueList>
					</Slot>
					<Slot name="sourcePatientId">
						<ValueList>
							<Value>
								<xsl:value-of select="$patientIdentifier" />
							</Value>
						</ValueList>
					</Slot>
					<Slot name="sourcePatientInfo">
						<ValueList>

							<Value>
								<xsl:value-of select="concat('PID-3|', $patientIdentifier)" />
							</Value>
							<Value>
								<xsl:value-of
									select="concat('PID-5|', $patientRole/patient/name/family, '^', $patientRole/patient/name/given[1], '^^^')" />
							</Value>
							<Value>
								<xsl:value-of
									select="concat('PID-7|', $patientRole/patient/birthTime/@value)" />
							</Value>
							<Value>
								<xsl:value-of
									select="concat('PID-8|', $patientRole/patient/administrativeGenderCode/@code)" />
							</Value>
							<Value>
								<xsl:value-of
									select="concat('PID-11|', $patientRole/addr/streetAddressLine[1], '^^', $patientRole/addr/city, '^', $patientRole/addr/state, '^', $patientRole/addr/postalCode, '^', $patientRole/addr/country)" />
							</Value>
						</ValueList>
					</Slot>

					<Slot name="authorTelecommunication">
						<ValueList>
							<Value>
								<xsl:value-of select="concat('^^Internet^', $authorTelecommunication)" />
							</Value>
						</ValueList>
					</Slot>

					<Slot name="intendedRecipient">
						<ValueList>
							<Value>
								<xsl:value-of select="concat('^^Internet^', $intendedRecipient)" />
							</Value>
						</ValueList>
					</Slot>


					<Slot name="urn:siframework.org:ds4p:purposeofuse">
						<ValueList>
							<Value>TREAT</Value>
						</ValueList>
					</Slot>



					<Slot name="urn:siframework.org:ds4p:obligationpolicy">
						<ValueList>
							<xsl:for-each
								select="$ruleExecutionResponseContainer//executionResponse[itemAction != 'REDACT']">
								<Value observationId="{observationId}">
									<xsl:value-of select="documentObligationPolicy" />
								</Value>
							</xsl:for-each>
						</ValueList>
					</Slot>


					<Slot name="urn:siframework.org:ds4p:refrainpolicy">
						<ValueList>
							<xsl:for-each
								select="$ruleExecutionResponseContainer//executionResponse[itemAction != 'REDACT']">
								<Value observationId="{observationId}">
									<xsl:value-of select="documentRefrainPolicy" />
								</Value>
							</xsl:for-each>
						</ValueList>
					</Slot>

					<Slot name="urn:siframework.org:ds4p:sensitivitypolicy">
						<ValueList>
							<xsl:for-each
								select="$ruleExecutionResponseContainer//executionResponse[itemAction != 'REDACT']">
								<Value observationId="{observationId}">
									<xsl:value-of select="sensitivity" />
								</Value>
							</xsl:for-each>
						</ValueList>
					</Slot>

					<Slot name="urn:siframework.org:ds4p:usprivacylaw">
						<ValueList>
							<xsl:for-each
								select="$ruleExecutionResponseContainer//executionResponse[itemAction != 'REDACT']">
								<Value observationId="{observationId}">
									<xsl:value-of select="USPrivacyLaw" />
								</Value>
							</xsl:for-each>
						</ValueList>
					</Slot>


					<Name>
						<LocalizedString value="Clinic Personal Health Record Extract" />
					</Name>
					<Description />
					<Classification classificationScheme="urn:uuid:93606bcf-9494-43ec-9b4e-a7748d1a838d"
						classifiedObject="Document01" id="cl01">
						<Slot name="authorPerson">
							<ValueList>
								<Value>
									<xsl:value-of select="$authorIdentifier" />
								</Value>
							</ValueList>
						</Slot>
						<Slot name="authorInstitution">
							<ValueList>
								<Value>
									XYZ
									Clinic<!--xsl:value-of select="$author/representedOrganization/name"/ -->
								</Value>
							</ValueList>
						</Slot>
						<Slot name="authorRole">
							<ValueList>
								<Value>Provider</Value>
							</ValueList>
						</Slot>
						<Slot name="authorSpecialty">
							<ValueList>
								<Value>General Medicine</Value>
							</ValueList>
						</Slot>



					</Classification>

					<Classification id="cl02"
						classificationScheme="urn:uuid:41a5887f-8865-4c09-adf7-e362475b143a"
						classifiedObject="Document01" nodeRepresentation="11488-4">
						<Slot name="codingScheme">
							<ValueList>
								<Value>HITSP C80, version 2.0.1, table 2-144</Value>
							</ValueList>
						</Slot>
						<Name>
							<LocalizedString value="Consult Note" />
						</Name>
					</Classification>
					<Classification id="cl03"
						classificationScheme="urn:uuid:f4f85eac-e6cb-4883-b524-f2705394840f"
						classifiedObject="Document01" nodeRepresentation="{$confidentialityCode}">
						<Slot name="codingScheme">
							<ValueList>
								<Value>2.16.840.1.113883.5.25</Value>
							</ValueList>
						</Slot>
						<Name>
							<LocalizedString value="{$confidentialityCode}" />
						</Name>
					</Classification>
					<Classification id="cl04"
						classificationScheme="urn:uuid:a09d5840-386c-46f2-b5ad-9c3699a4309d"
						classifiedObject="Document01" nodeRepresentation="urn:ihe:pcc:xphr:2007">
						<Slot name="codingScheme">
							<ValueList>
								<Value>HITSP C80, version 2.0.1, table 2-152</Value>
							</ValueList>
						</Slot>
						<Name>
							<LocalizedString value="HL7 CCD Document" />
						</Name>
					</Classification>
					<Classification id="cl05"
						classificationScheme="urn:uuid:f33fb8ac-18af-42cc-ae0e-ed0b0bdb91e1"
						classifiedObject="Document01" nodeRepresentation="14866005">
						<Slot name="codingScheme">
							<ValueList>
								<Value>HITSP C80, version 2.0.1, table 2-146</Value>
							</ValueList>
						</Slot>
						<Name>
							<LocalizedString value="Hospital outpatient mental health center" />
						</Name>
					</Classification>
					<Classification id="cl06"
						classificationScheme="urn:uuid:cccf5598-8b07-4b77-a05e-ae952c785ead"
						classifiedObject="Document01" nodeRepresentation="394587001">
						<Slot name="codingScheme">
							<ValueList>
								<Value>HITSP C80, version 2.0.1, table 2-149</Value>
							</ValueList>
						</Slot>
						<Name>
							<LocalizedString value="Psychiatry" />
						</Name>
					</Classification>
					<Classification id="cl07"
						classificationScheme="urn:uuid:f0306f51-975f-434e-a61c-c59651d33983"
						classifiedObject="Document01" nodeRepresentation="{$documentTypeInLoincCode}">
						<Slot name="codingScheme">
							<ValueList>
								<Value>LOINC</Value>
							</ValueList>
						</Slot>
						<Name>
							<LocalizedString value="{$documentTypeInLoincDisplayName}" />
						</Name>
					</Classification>
					<Classification id="c108"
						classificationScheme="urn:uuid:f33fb8ac-18af-42cc-ae0e-ed0b0bdb91e1"
						classifiedObject="Document01"
						objectType="urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Classification"
						nodeRepresentation="20078004">
						<Name>
							<LocalizedString value="Substance abuse treatment center" />
						</Name>
						<Slot name="codingScheme">
							<ValueList>
								<Value>HITSP C80, version 2.0.1, table 2-146</Value>
							</ValueList>
						</Slot>
					</Classification>
					<ExternalIdentifier registryObject="Document01"
						identificationScheme="urn:uuid:58a6f841-87b3-4a3e-92fd-a8ffeff98427"
						value="111111111^^&amp;2.16.840.1.113883.4.1&amp;ISO" id="ei01">
						<Name>
							<LocalizedString value="XDSDocumentEntry.patientId" />
						</Name>
					</ExternalIdentifier>
					<ExternalIdentifier registryObject="Document01"
						identificationScheme="urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab"
						value="1.3.6.1.4.1.21367.2005.3.9999.32" id="ei02">
						<Name>
							<LocalizedString value="XDSDocumentEntry.uniqueId" />
						</Name>
					</ExternalIdentifier>
				</ExtrinsicObject>
				<RegistryPackage id="SubmissionSet">
					<Slot name="submissionTime">
						<ValueList>
							<Value>
								<xsl:value-of select="$currentEffectiveTime" />
							</Value>
						</ValueList>
					</Slot>
					<Name>
						<LocalizedString value="Submission Set" />
					</Name>
					<Description>
						<LocalizedString
							value="This Submission Set contains a Clinical Exchange Document" />
					</Description>
					<Classification id="cl09"
						classificationScheme="urn:uuid:a7058bb9-b4e4-4307-ba5b-e3f0ab85e12d"
						classifiedObject="SubmissionSet" nodeRepresentation="">
						<Slot name="authorPerson">
							<ValueList>
								<Value>
									<xsl:value-of select="$authorIdentifier" />
								</Value>
							</ValueList>
						</Slot>
						<Slot name="authorInstitution">
							<ValueList>
								<Value>
									XYZ
									Clinic<!--xsl:value-of select="$author/representedOrganization/name"/ -->
								</Value>
							</ValueList>
						</Slot>
						<Slot name="authorRole">
							<ValueList>
								<Value>Provider</Value>
							</ValueList>
						</Slot>
						<Slot name="authorSpecialty">
							<ValueList>
								<Value>General Medicine</Value>
							</ValueList>
						</Slot>
					</Classification>
					<Classification id="cl10"
						classificationScheme="urn:uuid:aa543740-bdda-424e-8c96-df4873be8500"
						classifiedObject="SubmissionSet" nodeRepresentation="{$documentTypeInLoincCode}">
						<Slot name="codingScheme">
							<ValueList>
								<Value>LOINC</Value>
							</ValueList>
						</Slot>
						<Name>
							<LocalizedString value="{$documentTypeInLoincDisplayName}" />
						</Name>
					</Classification>

					<ExternalIdentifier id="ei03" registryObject="SubmissionSet"
						identificationScheme="urn:uuid:96fdda7c-d067-4183-912e-bf5ee74998a8"
						value="1.3.6.1.4.1.22812.5.998723538.0.1.421030037.1">
						<Name>
							<LocalizedString value="XDSSubmissionSet.uniqueId" />
						</Name>
					</ExternalIdentifier>
					<ExternalIdentifier id="ei04" registryObject="SubmissionSet"
						identificationScheme="urn:uuid:554ac39e-e3fe-47fe-b233-965d2a147832"
						value="1.3.6.1.4.1.21367.13.2015">
						<Name>
							<LocalizedString value="XDSSubmissionSet.sourceId" />
						</Name>
					</ExternalIdentifier>
					<ExternalIdentifier id="ei05" registryObject="SubmissionSet"
						identificationScheme="urn:uuid:6b5aea1a-874d-4603-a4bc-96a0a7b38446"
						value="">
						<Name>
							<LocalizedString value="XDSSubmissionSet.patientId" />
						</Name>
					</ExternalIdentifier>
				</RegistryPackage>
				<Classification id="cl11" classifiedObject="SubmissionSet"
					classificationNode="urn:uuid:a54d6aa5-d40d-43f9-88c5-b4633d873bdd" />
				<Association id="as01"
					associationType="urn:oasis:names:tc:ebxml-regrep:AssociationType:HasMember"
					sourceObject="SubmissionSet" targetObject="Document01">
					<Slot name="SubmissionSetStatus">
						<ValueList>
							<Value>Original</Value>
						</ValueList>
					</Slot>
				</Association>
			</RegistryObjectList>
		</lcm:SubmitObjectsRequest>

	</xsl:template>
</xsl:stylesheet>
