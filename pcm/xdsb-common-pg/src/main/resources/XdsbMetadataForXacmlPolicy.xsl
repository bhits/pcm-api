<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:sdtc="urn:hl7-org:sdtc"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	exclude-result-prefixes="xs sdtc" version="2.0"
	xpath-default-namespace="urn:oasis:names:tc:xacml:2.0:policy:schema:os" xmlns:lcm="urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0"
	xmlns:rim="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0" xmlns:rs="urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0"
	xmlns="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0">

	<!-- Generate XDS.b metadata from a Xacml Consent Policy. -->

	<xsl:output indent="yes" omit-xml-declaration="yes" />

	<xsl:param name="homeCommunityId" as="xs:string" />
	<xsl:param name="XDSDocumentEntry_uniqueId" as="xs:string" />
	<xsl:param name="XDSSubmissionSet_uniqueId" as="xs:string" />	
	<xsl:param name="XDSSubmissionSet_sourceId" as="xs:string" select="'1.3.6.1.4.1.21367.13.2015'" />	
	<xsl:param name="XDSDocumentEntry_entryUUID" as="xs:string" select="'Document01'" />
	<xsl:param name="XDSSubmissionSet_entryUUID" as="xs:string" select="'SubmissionSet'" />
	
	<xsl:variable name="patientId"
		select="//Policy/Rule[@Effect='Permit']/Target/Resources/Resource/ResourceMatch/AttributeValue" />		
	<xsl:variable name="patientIdentifier"
		select="concat($patientId,'^^^','&amp;',$homeCommunityId,'&amp;','ISO')" />		

	<xsl:variable name="authorIdentifier"
		select="$patientIdentifier" />

	<xsl:variable name="confidentialityCode" select="'R'" />

	<xsl:variable name="currentDateTimeUtc" select="string(adjust-dateTime-to-timezone(current-dateTime(), xs:dayTimeDuration('PT0H')))" />

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

		<lcm:SubmitObjectsRequest xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xmlns:lcm="urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0" xmlns:rim="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0"
			xmlns:rs="urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0" xmlns="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0">

			<RegistryObjectList>
				<ObjectRef id="urn:uuid:7edca82f-054d-47f2-a032-9b2a5b518fff" />
				<ObjectRef id="urn:uuid:a54d6aa5-d40d-43f9-88c5-b4633d873bdd" />
				<ObjectRef id="urn:uuid:f64ffdf0-4b97-4e06-b79f-a52b38ec2f8a" />
				<ObjectRef id="urn:uuid:96fdda7c-d067-4183-912e-bf5ee74998a8" />
				<ObjectRef id="urn:uuid:554ac39e-e3fe-47fe-b233-965d2a147832" />
				<ObjectRef id="urn:uuid:6b5aea1a-874d-4603-a4bc-96a0a7b38446" />
				<ObjectRef id="urn:uuid:d9d542f3-6cc4-48b6-8870-ea235fbc94c2" />
				<ObjectRef id="urn:uuid:f33fb8ac-18af-42cc-ae0e-ed0b0bdb91e1" />
				<ObjectRef id="urn:uuid:aa543740-bdda-424e-8c96-df4873be8500" />
				<ObjectRef id="urn:uuid:75df8f67-9973-4fbe-a900-df66cefecc5a" />
				<ObjectRef id="urn:uuid:f4f85eac-e6cb-4883-b524-f2705394840f" />
				<ObjectRef id="urn:uuid:a09d5840-386c-46f2-b5ad-9c3699a4309d" />
				<ObjectRef id="urn:uuid:cccf5598-8b07-4b77-a05e-ae952c785ead" />
				<ObjectRef id="urn:uuid:f0306f51-975f-434e-a61c-c59651d33983" />
				<ObjectRef id="urn:uuid:41a5887f-8865-4c09-adf7-e362475b143a" />
				<ObjectRef id="urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab" />
				<ObjectRef id="urn:uuid:58a6f841-87b3-4a3e-92fd-a8ffeff98427" />
				<ObjectRef id="urn:uuid:a7058bb9-b4e4-4307-ba5b-e3f0ab85e12d" />
				<ObjectRef id="urn:uuid:93606bcf-9494-43ec-9b4e-a7748d1a838d" />
				
				<ExtrinsicObject mimeType="text/xml"
					objectType="urn:uuid:7edca82f-054d-47f2-a032-9b2a5b5186c1" id="{$XDSDocumentEntry_entryUUID}">
					<Slot name="creationTime">
						<ValueList>
							<Value>
								<xsl:value-of select="$currentEffectiveTime" />
							</Value>
						</ValueList>
					</Slot>
					<Slot name="languageCode">
						<ValueList>
							<Value>en-us
							</Value>
						</ValueList>
					</Slot>
		            <Slot name="serviceStartTime">
		                <ValueList>
		                    <Value>
		                    	<xsl:value-of select="$consentStartTimeInt" />
		                    </Value>
		                </ValueList>
		            </Slot>
		            <Slot name="serviceStopTime">
		                <ValueList>
		                    <Value>
		                    	<xsl:value-of select="$consentFinishTimeInt" />
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
									select="concat('PID-5|', '', '^', '', '^^^')" />
							</Value>
							<Value>
								<xsl:value-of
									select="concat('PID-7|', '^')" /> <!-- '^' added just for validation -->
							</Value>
							<Value>
								<xsl:value-of
									select="concat('PID-8|', '^')" /> <!-- '^' added just for validation -->
							</Value>
							<Value>
								<xsl:value-of
									select="concat('PID-11|', '', '^^', '', '^', '', '^', '', '^', '')" />
							</Value>
						</ValueList>
					</Slot>

					<Name>
						<LocalizedString value="Privacy Consent Policy" />
					</Name>
					<Description />
					
					<Classification id="cl01" classificationScheme="urn:uuid:93606bcf-9494-43ec-9b4e-a7748d1a838d"
						classifiedObject="{$XDSDocumentEntry_entryUUID}" nodeRepresentation="">
						<Slot name="authorPerson">
							<ValueList>
								<Value>
									<xsl:value-of select="$patientIdentifier" />
								</Value>
							</ValueList>
						</Slot>
					</Classification>

					<Classification id="cl02"
						classificationScheme="urn:uuid:41a5887f-8865-4c09-adf7-e362475b143a"
						classifiedObject="{$XDSDocumentEntry_entryUUID}" nodeRepresentation="Consent">
						<Slot name="codingScheme">
							<ValueList>
								<Value>Connect-a-thon classCodes</Value>
							</ValueList>
						</Slot>
						<Name>
							<LocalizedString value="Consent" />
						</Name>
					</Classification>

					<Classification id="cl03"
						classificationScheme="urn:uuid:f4f85eac-e6cb-4883-b524-f2705394840f"
						classifiedObject="{$XDSDocumentEntry_entryUUID}" nodeRepresentation="{$confidentialityCode}">
						<Slot name="codingScheme">
							<ValueList>
								<Value>2.16.840.1.113883.5.25</Value>
							</ValueList>
						</Slot>
						<Name>
							<LocalizedString value="{$confidentialityCode}" />
						</Name>
					</Classification>
					
					<!-- XDSDocumentEntry.formatCode -->
					<Classification id="cl04"
						classificationScheme="urn:uuid:a09d5840-386c-46f2-b5ad-9c3699a4309d"
						classifiedObject="{$XDSDocumentEntry_entryUUID}" nodeRepresentation="1.3.6.1.4.1.19376.1.5.3.1.1.7">
						<Slot name="codingScheme">
							<ValueList>
								<Value>IHE BPPC</Value>
							</ValueList>
						</Slot>
						<Name>
							<LocalizedString value="Privacy Consent Policy" />
						</Name>
					</Classification>
					
					<!-- XDSDocumentEntry.healthcareFacilityTypeCode -->
					<Classification id="cl05"
						classificationScheme="urn:uuid:f33fb8ac-18af-42cc-ae0e-ed0b0bdb91e1"
						classifiedObject="{$XDSDocumentEntry_entryUUID}" nodeRepresentation="Home">
						<Slot name="codingScheme">
							<ValueList>
								<Value>Connect-a-thon healthcareFacilityTypeCodes</Value>
							</ValueList>
						</Slot>
						<Name>
							<LocalizedString value="Home" />
						</Name>
					</Classification>
					
					<!-- XDSDocumentEntry.practiceSettingCode -->
					<Classification id="cl06"
						classificationScheme="urn:uuid:cccf5598-8b07-4b77-a05e-ae952c785ead"
						classifiedObject="{$XDSDocumentEntry_entryUUID}" nodeRepresentation="Home">
						<Slot name="codingScheme">
							<ValueList>
								<Value>Connect-a-thon practiceSettingCodes</Value>
							</ValueList>
						</Slot>
						<Name>
							<LocalizedString value="Home" />
						</Name>
					</Classification>
					
					<!-- XDSDocumentEntry.typeCode -->
					<Classification id="cl07"
						classificationScheme="urn:uuid:f0306f51-975f-434e-a61c-c59651d33983"
						classifiedObject="{$XDSDocumentEntry_entryUUID}" nodeRepresentation="28636-9">
						<Slot name="codingScheme">
							<ValueList>
								<Value>LOINC</Value>
							</ValueList>
						</Slot>
						<Name>
							<LocalizedString value="Initial Evaluation Note" />
						</Name>
					</Classification>
					
					<ExternalIdentifier id="ei01" registryObject="{$XDSDocumentEntry_entryUUID}"
						identificationScheme="urn:uuid:58a6f841-87b3-4a3e-92fd-a8ffeff98427"
						value="{$patientIdentifier}">
						<Name>
							<LocalizedString value="XDSDocumentEntry.patientId" />
						</Name>
					</ExternalIdentifier>
					
					<ExternalIdentifier id="ei02" registryObject="{$XDSDocumentEntry_entryUUID}"
						identificationScheme="urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab"
						value="{$XDSDocumentEntry_uniqueId}">
						<Name>
							<LocalizedString value="XDSDocumentEntry.uniqueId" />
						</Name>
					</ExternalIdentifier>
				</ExtrinsicObject>
				
				<RegistryPackage id="{$XDSSubmissionSet_entryUUID}">
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
						<LocalizedString value="This Submission Set contains a Privacy Consent Policy Document" />
					</Description>
					
					<Classification id="cl09"
						classificationScheme="urn:uuid:a7058bb9-b4e4-4307-ba5b-e3f0ab85e12d"
						classifiedObject="{$XDSSubmissionSet_entryUUID}" nodeRepresentation="">
						<Slot name="authorPerson">
							<ValueList>
								<Value>
									<xsl:value-of select="$authorIdentifier" />
								</Value>
							</ValueList>
						</Slot>
					</Classification>
					
					<Classification id="cl10"
						classificationScheme="urn:uuid:aa543740-bdda-424e-8c96-df4873be8500"
						classifiedObject="{$XDSSubmissionSet_entryUUID}" nodeRepresentation="Initial evaluation">
						<Slot name="codingScheme">
							<ValueList>
								<Value>Connect-a-thon contentTypeCodes</Value>
							</ValueList>
						</Slot>
						<Name>
							<LocalizedString value="'Initial evaluation'" />
						</Name>
					</Classification>

					<ExternalIdentifier id="ei03" registryObject="{$XDSSubmissionSet_entryUUID}"
						identificationScheme="urn:uuid:96fdda7c-d067-4183-912e-bf5ee74998a8"
						value="{$XDSSubmissionSet_uniqueId}">
						<Name>
							<LocalizedString value="XDSSubmissionSet.uniqueId" />
						</Name>
					</ExternalIdentifier>
					
					<ExternalIdentifier id="ei04" registryObject="{$XDSSubmissionSet_entryUUID}"
						identificationScheme="urn:uuid:554ac39e-e3fe-47fe-b233-965d2a147832"
						value="{$XDSSubmissionSet_sourceId}">
						<Name>
							<LocalizedString value="XDSSubmissionSet.sourceId" />
						</Name>
					</ExternalIdentifier>
					
					<ExternalIdentifier id="ei05" registryObject="{$XDSSubmissionSet_entryUUID}"
						identificationScheme="urn:uuid:6b5aea1a-874d-4603-a4bc-96a0a7b38446"
						value="{$patientIdentifier}">
						<Name>
							<LocalizedString value="XDSSubmissionSet.patientId" />
						</Name>
					</ExternalIdentifier>
				</RegistryPackage>
				
				<Classification id="cl11" classifiedObject="{$XDSSubmissionSet_entryUUID}"
					classificationNode="urn:uuid:a54d6aa5-d40d-43f9-88c5-b4633d873bdd" />
				
				<Association id="as01"
					associationType="urn:oasis:names:tc:ebxml-regrep:AssociationType:HasMember"
					sourceObject="{$XDSSubmissionSet_entryUUID}" targetObject="{$XDSDocumentEntry_entryUUID}">
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