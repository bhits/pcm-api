<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" exclude-result-prefixes="xs c2s"
	xmlns:hl7="urn:hl7-org:v3" xmlns:c2s="gov.samhsa.pcm.hl7.dto" version="2.0">

	<xsl:output method="xml" version="1.0" indent="no" encoding="UTF-8"/>

	<xsl:variable name="currentDateTimeUtc"
		select="string(adjust-dateTime-to-timezone(current-dateTime(), xs:dayTimeDuration('PT0H')))"/>
	<xsl:variable name="currentEffectiveTime">
		<xsl:value-of select="substring($currentDateTimeUtc, 1, 4)"/>
		<xsl:value-of select="substring($currentDateTimeUtc, 6, 2)"/>
		<xsl:value-of select="substring($currentDateTimeUtc, 9, 2)"/>
		<xsl:value-of select="substring($currentDateTimeUtc, 12, 2)"/>
		<xsl:value-of select="substring($currentDateTimeUtc, 15, 2)"/>
		<xsl:value-of select="substring($currentDateTimeUtc, 18, 2)"/>
	</xsl:variable>

	<xsl:template match="/">

		<PRPA_IN201302UV02 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xmlns="urn:hl7-org:v3" ITSVersion="XML_1.0">
			<id root="{/c2s:PixPatientDto/c2s:messageId[1]}"/>
			<creationTime value="{$currentEffectiveTime}"/>
			<interactionId root="2.16.840.1.113883.1.6" extension="PRPA_IN201302UV02"/>
			<processingCode code="P"/>
			<processingModeCode code="T"/>
			<acceptAckCode code="AL"/>
			<receiver typeCode="RCV">
				<device classCode="DEV" determinerCode="INSTANCE">
					<id root="1.2.840.114350.1.13.99999.4567"/>
				</device>
			</receiver>
			<sender typeCode="SND">
				<device classCode="DEV" determinerCode="INSTANCE">
					<id root="{/c2s:PixPatientDto/c2s:idRoot[1]}"/>
				</device>
			</sender>
			<controlActProcess classCode="CACT" moodCode="EVN">
				<code code="PRPA_TE201302UV02" codeSystem="2.16.840.1.113883.1.6"/>
				<subject typeCode="SUBJ">
					<registrationEvent classCode="REG" moodCode="EVN">
						<id nullFlavor="NA"/>
						<statusCode code="active"/>
						<subject1 typeCode="SBJ">
							<patient classCode="PAT">
								<id extension="{/c2s:PixPatientDto/c2s:idExtension[1]}"
									assigningAuthorityName="{/c2s:PixPatientDto/c2s:idAssigningAuthorityName[1]}"
									root="{/c2s:PixPatientDto/c2s:idRoot[1]}"/>
								<statusCode code="active"/>
								<patientPerson>
									<name>
										<given>
											<xsl:value-of
												select="/c2s:PixPatientDto/c2s:patientFirstName[1]"
											/>
										</given>
										<family>
											<xsl:value-of
												select="/c2s:PixPatientDto/c2s:patientLastName[1]"/>
										</family>
									</name>

									<!-- Telephone -->
									<xsl:choose>
										<xsl:when
											test="/c2s:PixPatientDto/c2s:telecomValue[1] != ' '">
											<xsl:variable name="tel">tel:<xsl:value-of
												select="/c2s:PixPatientDto/c2s:telecomValue[1]"
												/></xsl:variable>
											<telecom use="{/c2s:PixPatientDto/c2s:telecomUse[1]}"
												value="{$tel}"/>
										</xsl:when>
										<xsl:otherwise>
											<xsl:variable name="tel">
												<xsl:value-of
												select="/c2s:PixPatientDto/c2s:telecomValue[1]"/>
											</xsl:variable>
											<telecom use="{/c2s:PixPatientDto/c2s:telecomUse[1]}"
												value="{$tel}"/>
										</xsl:otherwise>
									</xsl:choose>

									<!-- Email -->
									<xsl:variable name="email">mailto:<xsl:value-of
											select="/c2s:PixPatientDto/c2s:patientEmailHome[1]"
										/></xsl:variable>
									<telecom use="H" value="{$email}"/>

									<administrativeGenderCode
										code="{/c2s:PixPatientDto/c2s:administrativeGenderCode[1]}"/>
									<birthTime value="{/c2s:PixPatientDto/c2s:birthTimeValue[1]}"/>

									<!-- Address -->
									<xsl:variable name="addrStreetAddressLine"
										select="/c2s:PixPatientDto/c2s:addrStreetAddressLine[1]"/>
									<xsl:variable name="addrCity"
										select="/c2s:PixPatientDto/c2s:addrCity[1]"/>
									<xsl:variable name="addrState"
										select="/c2s:PixPatientDto/c2s:addrState[1]"/>
									<xsl:variable name="addrPostalCode"
										select="/c2s:PixPatientDto/c2s:addrPostalCode[1]"/>
									<addr>
										<streetAddressLine>
											<xsl:value-of select="$addrStreetAddressLine"/>
										</streetAddressLine>
										<city>
											<xsl:value-of select="$addrCity"/>
										</city>
										<state>
											<xsl:value-of select="$addrState"/>
										</state>
										<postalCode>
											<xsl:value-of select="$addrPostalCode"/>
										</postalCode>
									</addr>

									<!-- Marital Status Code -->
									<maritalStatusCode
										code="{/c2s:PixPatientDto/c2s:maritalStatusCode[1]}"
										codeSystem="2.16.840.1.113883.5.2"/>

									<!-- SSN -->
									<asOtherIDs classCode="CIT">
										<id root="2.16.840.1.113883.4.1"
											extension="{/c2s:PixPatientDto/c2s:ssn[1]}"/>
										<scopingOrganization classCode="ORG"
											determinerCode="INSTANCE">
											<id root="2.16.840.1.113883.4.1"/>
										</scopingOrganization>
									</asOtherIDs>

								</patientPerson>
							</patient>
						</subject1>
					</registrationEvent>
				</subject>
			</controlActProcess>
		</PRPA_IN201302UV02>

	</xsl:template>

</xsl:stylesheet>
