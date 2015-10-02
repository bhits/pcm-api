<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" exclude-result-prefixes="xs c2s"
	xmlns:hl7="urn:hl7-org:v3" xmlns:c2s="gov.samhsa.consent2share.hl7.dto" version="2.0">

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

		<urn:PRPA_IN201301UV02 xmlns:urn="urn:hl7-org:v3"
			xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
			xsi:schemaLocation="urn:hl7-org:v3 ../schema/HL7V3/NE2008/multicacheschemas/PRPA_IN201301UV02.xsd"
			xmlns="urn:hl7-org:v3" ITSVersion="XML_1.0">
			<urn:id root="{/c2s:PixPatientDto/c2s:messageId[1]}"/>
			<urn:creationTime value="{$currentEffectiveTime}"/>
			<urn:interactionId extension="PRPA_IN201301UV02" root="2.16.840.1.113883.1.6"/>
			<urn:processingCode code="P"/>
			<urn:processingModeCode code="T"/>
			<urn:acceptAckCode code="AL"/>
			<urn:receiver typeCode="RCV">
				<urn:device classCode="DEV" determinerCode="INSTANCE">
					<urn:id root="1.2.840.114350.1.13.99999.4567"/>
				</urn:device>
			</urn:receiver>
			<urn:sender typeCode="SND">
				<urn:device classCode="DEV" determinerCode="INSTANCE">
					<urn:id root="{/c2s:PixPatientDto/c2s:idRoot[1]}"/>
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
								<urn:id extension="{/c2s:PixPatientDto/c2s:idExtension[1]}"
									assigningAuthorityName="{/c2s:PixPatientDto/c2s:idAssigningAuthorityName[1]}"
									root="{/c2s:PixPatientDto/c2s:idRoot[1]}"/>
								<urn:statusCode code="active"/>
								<urn:patientPerson>
									<urn:name>
										<urn:given>
											<xsl:value-of
												select="/c2s:PixPatientDto/c2s:patientFirstName[1]"
											/>
										</urn:given>
										<urn:family>
											<xsl:value-of
												select="/c2s:PixPatientDto/c2s:patientLastName[1]"/>
										</urn:family>
									</urn:name>
									<urn:administrativeGenderCode
										code="{/c2s:PixPatientDto/c2s:administrativeGenderCode[1]}"/>
									<urn:birthTime
										value="{/c2s:PixPatientDto/c2s:birthTimeValue[1]}"/>
								</urn:patientPerson>
							</urn:patient>
						</urn:subject1>
					</urn:registrationEvent>
				</urn:subject>
			</urn:controlActProcess>
		</urn:PRPA_IN201301UV02>

	</xsl:template>

</xsl:stylesheet>
