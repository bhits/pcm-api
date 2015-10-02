<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:sdtc="urn:hl7-org:sdtc"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	exclude-result-prefixes="xs sdtc" version="2.0"
	xpath-default-namespace="urn:oasis:names:tc:xacml:2.0:policy:schema:os" xmlns:lcm="urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0"
	xmlns:rim="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0" xmlns:rs="urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0"
	xmlns="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0">

	<!-- Generate XDS.b metadata to deprecate a Xacml Consent Policy. -->

	<xsl:output indent="yes" omit-xml-declaration="yes" />

	<xsl:param name="homeCommunityId" as="xs:string" />
	<xsl:param name="XDSDocumentEntry_uniqueId" as="xs:string" />
	<xsl:param name="XDSSubmissionSet_uniqueId" as="xs:string" />	
	<xsl:param name="XDSSubmissionSet_sourceId" as="xs:string" select="'1.3.6.1.4.1.21367.13.2015'" />	
	<xsl:param name="XDSDocumentEntry_entryUUID" as="xs:string" />
	<xsl:param name="XDSSubmissionSet_entryUUID" as="xs:string" select="'SubmissionSet'" />
	<xsl:param name="patientUniqueId" as="xs:string"/>
		
	<xsl:variable name="patientIdentifier" select="$patientUniqueId" />		

	<xsl:variable name="authorIdentifier" select="$patientIdentifier" />

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

		<lcm:SubmitObjectsRequest xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xmlns:lcm="urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0" xmlns:rim="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0"
			xmlns:rs="urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0" xmlns="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0">

			<RegistryObjectList>
							
				<RegistryPackage id="{$XDSSubmissionSet_entryUUID}" objectType="urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:RegistryPackage">
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
						<LocalizedString value="This Submission Set deprecates a Privacy Consent Policy Document" />
					</Description>
					
					<Classification id="cl09"
						classificationScheme="urn:uuid:a7058bb9-b4e4-4307-ba5b-e3f0ab85e12d"
						classifiedObject="{$XDSSubmissionSet_entryUUID}" nodeRepresentation="" objectType="urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Classification">
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
						classifiedObject="{$XDSSubmissionSet_entryUUID}" nodeRepresentation="Initial evaluation" objectType="urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Classification">
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
						value="{$XDSSubmissionSet_uniqueId}" objectType="urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ExternalIdentifier">
						<Name>
							<LocalizedString value="XDSSubmissionSet.uniqueId" />
						</Name>
					</ExternalIdentifier>
					
					<ExternalIdentifier id="ei04" registryObject="{$XDSSubmissionSet_entryUUID}"
						identificationScheme="urn:uuid:554ac39e-e3fe-47fe-b233-965d2a147832"
						value="{$XDSSubmissionSet_sourceId}" objectType="urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ExternalIdentifier">
						<Name>
							<LocalizedString value="XDSSubmissionSet.sourceId" />
						</Name>
					</ExternalIdentifier>
					
					<ExternalIdentifier id="ei05" registryObject="{$XDSSubmissionSet_entryUUID}"
						identificationScheme="urn:uuid:6b5aea1a-874d-4603-a4bc-96a0a7b38446"
						value="{$patientIdentifier}" objectType="urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:ExternalIdentifier">
						<Name>
							<LocalizedString value="XDSSubmissionSet.patientId" />
						</Name>
					</ExternalIdentifier>
				</RegistryPackage>
				
				<Classification id="cl11" classifiedObject="{$XDSSubmissionSet_entryUUID}"
					classificationNode="urn:uuid:a54d6aa5-d40d-43f9-88c5-b4633d873bdd" objectType="urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Classification" />
				
				<Association id="as01" objectType="urn:oasis:names:tc:ebxml-regrep:ObjectType:RegistryObject:Association"
					associationType="urn:oasis:names:tc:ebxml-regrep:AssociationType:HasMember"
					sourceObject="{$XDSSubmissionSet_entryUUID}" targetObject="{$XDSDocumentEntry_entryUUID}">
					<Slot name="OriginalStatus">
						<ValueList>
							<Value>urn:oasis:names:tc:ebxml-regrep:StatusType:Approved</Value>
						</ValueList>
					</Slot>
					<Slot name="NewStatus">
						<ValueList>
							<Value>urn:oasis:names:tc:ebxml-regrep:StatusType:Deprecated</Value>
						</ValueList>
					</Slot>					
				</Association>
				
			</RegistryObjectList>
		</lcm:SubmitObjectsRequest>

	</xsl:template>
</xsl:stylesheet>