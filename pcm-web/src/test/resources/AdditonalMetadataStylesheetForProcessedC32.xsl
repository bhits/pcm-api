<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:sdtc="urn:hl7-org:sdtc"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	exclude-result-prefixes="xs sdtc" version="2.0"
	xpath-default-namespace="urn:hl7-org:v3" xmlns:lcm="urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0"
	xmlns:rim="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0" xmlns:rs="urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0"
	xmlns:ds4p="http://www.siframework.org/ds4p" xmlns="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0">

	<!-- Generate additional metadata for processed C32 document. -->

	<xsl:output indent="yes" omit-xml-declaration="yes" />
	<xsl:variable name="ruleExecutionResponseContainer"
		select="document('ruleExecutionResponseContainer')" />

	<xsl:param name="authorTelecommunication" as="xs:string"
		select="''" />

	<xsl:param name="intendedRecipient" as="xs:string" select="''" />

	<xsl:param name="purposeOfUse" as="xs:string" select="''" />

	<xsl:param name="xdsDocumentEntryUniqueId" as="xs:string"
		select="''" />

	<xsl:variable name="confidentialityCode"
		select="//ClinicalDocument/confidentialityCode/@code" />
	<xsl:variable name="documentRefrainPolicy"
		select="$ruleExecutionResponseContainer//executionResponse[position()= 1]//documentRefrainPolicy" />
	<xsl:variable name="documentObligationPolicy"
		select="$ruleExecutionResponseContainer//executionResponse[position()= 1]//documentObligationPolicy" />

	<xsl:variable name="documentTypeInLoincCode" select="//ClinicalDocument/code/@code" />

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

	<!--This function maps a char (V, N, or R) confidentiality code to string. -->
	<xsl:function name="ds4p:mapConfCodeFromChartoString" as="xs:string">
		<xsl:param name="confidentialityCodeString" />
		<xsl:choose>
			<xsl:when test="$confidentialityCodeString = 'V'">Very Restricted</xsl:when>
			<xsl:when test="$confidentialityCodeString = 'R'">Restricted</xsl:when>
			<xsl:when test="$confidentialityCodeString = 'N'">Normal</xsl:when>
			<xsl:otherwise>Normal</xsl:otherwise>
		</xsl:choose>
	</xsl:function>

	<!--This function maps a char purpose of use to string. -->
	<xsl:function name="ds4p:mapPurposeCodeFromChartoString"
		as="xs:string">
		<xsl:param name="purposeOfUseString" />
		<xsl:choose>
			<xsl:when test="$purposeOfUseString = 'TREAT'">Treatment</xsl:when>
			<xsl:when test="$purposeOfUseString = 'EMERGENCY'">Emergency Treatment</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$purposeOfUseString" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:function>

	<!--This function maps a char refrain policy to string. -->
	<xsl:function name="ds4p:mapRefrainPolicyFromChartoString"
		as="xs:string">
		<xsl:param name="refrainPolicyString" />
		<xsl:choose>
			<xsl:when test="$refrainPolicyString = 'NORDSCLCD'">No redisclosure without privacy consent</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="$refrainPolicyString" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:function>

	<xsl:template match="/">


		<lcm:SubmitObjectsRequest xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xmlns:lcm="urn:oasis:names:tc:ebxml-regrep:xsd:lcm:3.0" xmlns:rim="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0"
			xmlns:rs="urn:oasis:names:tc:ebxml-regrep:xsd:rs:3.0" xmlns="urn:oasis:names:tc:ebxml-regrep:xsd:rim:3.0">

			<RegistryObjectList>
				<rim:ExtrinsicObject id="Document01" mimeType="text/plain"
					objectType="urn:uuid:7edca82f-054d-47f2-a032-9b2a5b5186c1">
					<rim:Slot name="creationTime">
						<rim:ValueList>
							<rim:Value>
								<xsl:value-of select="$currentEffectiveTime" />
							</rim:Value>
						</rim:ValueList>
					</rim:Slot>
					<rim:Slot name="languageCode">
						<rim:ValueList>
							<rim:Value>
								<xsl:value-of select="//ClinicalDocument/languageCode/@code" />
							</rim:Value>
						</rim:ValueList>
					</rim:Slot>

					<!-- HL7 confidentialty code referencing the IHE IT TF classification 
						scheme id: f4f85eac-e6cb-4883-b524-f2705394840f -->
					<rim:Classification classificationScheme="urn:uuid:f4f85eac-e6cb-4883-b524-f2705394840f"
						classifiedObject="Document01" id="CLS0-1"
						objectType="urn:oasis:names:tc:ebxmlregrep:ObjectType:RegistryObject:Classification"
						nodeRepresentation="{$confidentialityCode}">
						<rim:Slot name="codingScheme">
							<rim:ValueList>
								<rim:Value>2.16.840.1.113883.1.11.16926</rim:Value>
							</rim:ValueList>
						</rim:Slot>
						<rim:Name>
							<rim:LocalizedString
								value="{ds4p:mapConfCodeFromChartoString($confidentialityCode)}" />
						</rim:Name>
					</rim:Classification>
					<!-- HL7 Purpose of Use - Treatment - NEW for DS4P as an element of 
						this metdata -->
					<rim:Classification classificationScheme="urn:uuid:8366586d-5989-4770-82a5-782a7c565d7c"
						classifiedObject="Document01" id="CLS0-2"
						objectType="urn:oasis:names:tc:ebxmlregrep:ObjectType:RegistryObject:Classification"
						nodeRepresentation="{$purposeOfUse}">
						<rim:Slot name="codingScheme">
							<rim:ValueList>
								<rim:Value>2.16.840.1.113883.1.11.20448</rim:Value>
							</rim:ValueList>
						</rim:Slot>
						<rim:Name>
							<rim:LocalizedString
								value="{ds4p:mapPurposeCodeFromChartoString($purposeOfUse)}" />
						</rim:Name>
					</rim:Classification>

					<!-- HL7 Obligation Code - NEW for DS4P as an element -->
					<rim:Classification classificationScheme="urn:uuid:44847bd0-b68a-497e-9558-51b38cad659d"
						classifiedObject="Document01" id="CLS0-3"
						objectType="urn:oasis:names:tc:ebxmlregrep:ObjectType:RegistryObject:Classification"
						nodeRepresentation="{$documentObligationPolicy}">
						<rim:Slot name="codingScheme">
							<rim:ValueList>
								<rim:Value>2.16.840.1.113883.1.11.20445</rim:Value>
							</rim:ValueList>
						</rim:Slot>
						<rim:Name>
							<rim:LocalizedString value="{$documentObligationPolicy}" />
						</rim:Name>
					</rim:Classification>

					<!-- HL7 Refrain Policy Code - NEW for DS4P as an element -->
					<rim:Classification classificationScheme="urn:uuid:292629b1-638d-40df-b802-2bbfdb94187a"
						classifiedObject="Document01" id="CLS0-4"
						objectType="urn:oasis:names:tc:ebxmlregrep:ObjectType:RegistryObject:Classification"
						nodeRepresentation="{$documentRefrainPolicy}">
						<rim:Slot name="codingScheme">
							<rim:ValueList>
								<rim:Value>2.16.840.1.113883.1.11.20446</rim:Value>
							</rim:ValueList>
						</rim:Slot>
						<rim:Name>
							<rim:LocalizedString
								value="{ds4p:mapRefrainPolicyFromChartoString($documentRefrainPolicy)}" />
						</rim:Name>
					</rim:Classification>

					<!-- The value= content must be generated and unique Unless some unique 
						value in the message is able to be used -->
					<rim:ExternalIdentifier id="ei02"
						registryObject="Document01" identificationScheme="urn:uuid:2e82c1f6-a085-4c72-9da3-8640a32e42ab"
						value="{$xdsDocumentEntryUniqueId}">
						<rim:Name>
							<rim:LocalizedString value="XDSDocumentEntry.uniqueId" />
						</rim:Name>
					</rim:ExternalIdentifier>

				</rim:ExtrinsicObject>

				<rim:RegistryPackage id="SubmissionSet01">

					<!-- submissionTime comes from the Date header. It must be converted 
						into YYYYMMDDHHMMSS format -->
					<rim:Slot name="submissionTime">
						<rim:ValueList>
							<rim:Value><xsl:value-of select="$currentEffectiveTime" /></rim:Value>
						</rim:ValueList>
					</rim:Slot>

					<!-- authorTelecommunication comes from the From header. It must be 
						prepended with ^^Internet^ -->
					<rim:Classification id="cl08"
						classificationScheme="urn:uuid:a7058bb9-b4e4-4307-ba5b-e3f0ab85e12d"
						classifiedObject="SubmissionSet01">
						<rim:Slot name="authorTelecommunication">
							<rim:ValueList>
								<rim:Value>									
									<xsl:value-of select="concat('^^Internet^', $authorTelecommunication)" />
								</rim:Value>
							</rim:ValueList>
						</rim:Slot>
					</rim:Classification>

					<!-- intendedRecipient. It must be prepended with ^^Internet^ -->
					<rim:Classification id="cl08"
						classificationScheme="urn:uuid:a7058bb9-b4e4-4307-ba5b-e3f0ab85e12d"
						classifiedObject="SubmissionSet01">
						<rim:Slot name="intendedRecipient">
							<rim:ValueList>
								<rim:Value>									
									<xsl:value-of select="concat('^^Internet^', $intendedRecipient)" />
								</rim:Value>
							</rim:ValueList>
						</rim:Slot>
					</rim:Classification>

					<!-- The value= content must be generated and unique Unless some unique 
						value in the message is able to be used -->
					<rim:ExternalIdentifier id="ei03"
						registryObject="SubmissionSet01" identificationScheme="urn:uuid:96fdda7c-d067-4183-912e-bf5ee74998a8"
						value="1.3.6.1.4.1.21367.2005.3.9999.33">
						<rim:Name>
							<rim:LocalizedString value="XDSSubmissionSet.uniqueId" />
						</rim:Name>
					</rim:ExternalIdentifier>

					<!-- The value= content must be determined through configuration It 
						represents the source of the content and is expected to be a mapping from 
						the From header -->
					<rim:ExternalIdentifier id="ei04"
						registryObject="SubmissionSet01" identificationScheme="urn:uuid:554ac39e-e3fe-47fe-b233-965d2a147832"
						value="3670984664">
						<rim:Name>
							<rim:LocalizedString value="XDSSubmissionSet.sourceId" />
						</rim:Name>
					</rim:ExternalIdentifier>
				</rim:RegistryPackage>

			</RegistryObjectList>
		</lcm:SubmitObjectsRequest>

	</xsl:template>
</xsl:stylesheet>