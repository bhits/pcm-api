<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema" exclude-result-prefixes="xs" version="2.0">

    <xsl:output indent="yes"/>

    <xsl:variable name="currentDateTimeUtc" select="string(current-dateTime())"/>

    <xsl:variable name="patient" select="//ConsentExport/Patient"/>

    <xsl:variable name="providersDisclosureIsMadeTo" select="//providersDisclosureIsMadeToList"/>
    
    <xsl:variable name="consentId" select="ConsentExport/id"/>
    <xsl:variable name="patientId" select="ConsentExport/Patient/email"/>
    <xsl:variable name="policyId" select="concat('urn:samhsa:names:tc:consent2share:1.0:policyid:', $patientId, ':', $consentId)"/>
    
    <xsl:variable name="xacml">
        
        <Policy xmlns="urn:oasis:names:tc:xacml:3.0:core:schema:wd-17" PolicyId="{$consentId}"
            RuleCombiningAlgId="urn:oasis:names:tc:xacml:3.0:rule-combining-algorithm:deny-overrides"
            Version="1.0">
            <Description>This is a reference policy for <xsl:value-of select="$patientId"
            /></Description>
            
            <Target/>
            
            <Rule Effect="Permit">
                <xsl:attribute name="RuleId">
                    <xsl:value-of select="$consentId"/>
                </xsl:attribute>
                <Target>
                    <AnyOf>
                        <xsl:for-each
                            select="//IndividualProvidersPermittedToDiscloseList/IndividualProvidersPermittedToDisclose">
                            
                            <AllOf>
                                <Match MatchId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
                                    <AttributeValue
                                        DataType="http://www.w3.org/2001/XMLSchema#string">
                                        <xsl:value-of select="npi"/>
                                    </AttributeValue>
                                    <AttributeDesignator MustBePresent="false"
                                        Category="urn:oasis:names:tc:xacml:1.0:subject-category:access-subject"
                                        AttributeId="urn:samhsa:names:tc:consent2share:1.0:subject:provider-npi"
                                        DataType="http://www.w3.org/2001/XMLSchema#string"/>
                                </Match>
                            </AllOf>
                            
                        </xsl:for-each>
                        <xsl:for-each
                            select="//OrganizationalProvidersPermittedToDiscloseList/OrganizationalProvidersPermittedToDisclose">
                            
                            <AllOf>
                                <Match MatchId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
                                    <AttributeValue
                                        DataType="http://www.w3.org/2001/XMLSchema#string">
                                        <xsl:value-of select="npi"/>
                                    </AttributeValue>
                                    <AttributeDesignator MustBePresent="false"
                                        Category="urn:oasis:names:tc:xacml:1.0:subject-category:access-subject"
                                        AttributeId="urn:samhsa:names:tc:consent2share:1.0:subject:provider-npi"
                                        DataType="http://www.w3.org/2001/XMLSchema#string"/>
                                </Match>
                            </AllOf>
                            
                        </xsl:for-each>
                        <xsl:for-each
                            select="//IndividualProvidersDisclosureIsMadeToList/IndividualProvidersDisclosureIsMadeTo">
                            
                            <AllOf>
                                <Match MatchId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
                                    <AttributeValue
                                        DataType="http://www.w3.org/2001/XMLSchema#string">
                                        <xsl:value-of select="npi"/>
                                    </AttributeValue>
                                    <AttributeDesignator MustBePresent="false"
                                        Category="urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject"
                                        AttributeId="urn:samhsa:names:tc:consent2share:1.0:subject:provider-npi"
                                        DataType="http://www.w3.org/2001/XMLSchema#string"/>
                                </Match>
                            </AllOf>
                            
                        </xsl:for-each>
                        <xsl:for-each
                            select="//OrganizationalProvidersDisclosureIsMadeToList/OrganizationalProvidersDisclosureIsMadeTo">
                            
                            <AllOf>
                                <Match MatchId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
                                    <AttributeValue
                                        DataType="http://www.w3.org/2001/XMLSchema#string">
                                        <xsl:value-of select="npi"/>
                                    </AttributeValue>
                                    <AttributeDesignator MustBePresent="false"
                                        Category="urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject"
                                        AttributeId="urn:samhsa:names:tc:consent2share:1.0:subject:provider-npi"
                                        DataType="http://www.w3.org/2001/XMLSchema#string"/>
                                </Match>
                            </AllOf>
                            
                        </xsl:for-each>
                    </AnyOf>
                    <AnyOf>
                        <xsl:for-each
                            select="//shareForPurposeOfUseCodesList/shareForPurposeOfUseCodes">
                            
                            <AllOf>
                                <Match MatchId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
                                    <AttributeValue
                                        DataType="http://www.w3.org/2001/XMLSchema#string">
                                        <xsl:value-of select="code"/>
                                    </AttributeValue>
                                    <AttributeDesignator MustBePresent="false"
                                        Category="urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject"
                                        AttributeId="urn:samhsa:names:tc:consent2share:1.0:purpose-of-use-code"
                                        DataType="http://www.w3.org/2001/XMLSchema#string"/>
                                </Match>
                            </AllOf>
                            
                        </xsl:for-each>
                    </AnyOf>
                    <AnyOf>
                        <AllOf>
                            <Match MatchId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
                                <AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">
                                    <!--This could also be a patient unique identifer. Using email for now.-->
                                    <xsl:value-of select="ConsentExport/Patient/email"/>
                                </AttributeValue>
                                <AttributeDesignator MustBePresent="false"
                                    Category="urn:oasis:names:tc:xacml:3.0:attribute-category:resource"
                                    AttributeId="urn:oasis:names:tc:xacml:1.0:resource:resource-id"
                                    DataType="http://www.w3.org/2001/XMLSchema#string"/>
                            </Match>
                        </AllOf>
                    </AnyOf>
                    
                    
                    
                    <AnyOf>
                        <AllOf>
                            <Match MatchId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
                                <AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string"
                                    >write</AttributeValue>
                                <AttributeDesignator MustBePresent="false"
                                    Category="urn:oasis:names:tc:xacml:3.0:attribute-category:action"
                                    AttributeId="urn:oasis:names:tc:xacml:1.0:action:action-id"
                                    DataType="http://www.w3.org/2001/XMLSchema#string"/>
                            </Match>
                        </AllOf>
                    </AnyOf>
                    <AnyOf>
                        <xsl:for-each select="//consentStart">
                            
                            <AllOf>
                                <Match
                                    MatchId="urn:oasis:names:tc:xacml:1.0:function:dateTime-greater-than-or-equal">
                                    <AttributeValue
                                        DataType="http://www.w3.org/2001/XMLSchema#dateTime">
                                        <xsl:value-of select="."/>
                                    </AttributeValue>
                                    <AttributeDesignator MustBePresent="false"
                                        Category="urn:oasis:names:tc:xacml:3.0:attribute-category:environment"
                                        AttributeId="urn:oasis:names:tc:xacml:1.0:environment:current-dateTime"
                                        DataType="http://www.w3.org/2001/XMLSchema#dateTime"/>
                                </Match>
                            </AllOf>
                            
                        </xsl:for-each>
                    </AnyOf>
                    <AnyOf>
                        
                        <xsl:for-each select="//consentEnd">
                            
                            <AllOf>
                                <Match
                                    MatchId="urn:oasis:names:tc:xacml:1.0:function:dateTime-less-than-or-equal">
                                    <AttributeValue
                                        DataType="http://www.w3.org/2001/XMLSchema#dateTime">
                                        <xsl:value-of select="."/>
                                    </AttributeValue>
                                    <AttributeDesignator MustBePresent="false"
                                        Category="urn:oasis:names:tc:xacml:3.0:attribute-category:environment"
                                        AttributeId="urn:oasis:names:tc:xacml:1.0:environment:current-dateTime"
                                        DataType="http://www.w3.org/2001/XMLSchema#dateTime"/>
                                </Match>
                            </AllOf>
                            
                        </xsl:for-each>
                    </AnyOf>
                </Target>
                
                
                
                
                
            </Rule>
            <ObligationExpressions>
                
                <xsl:for-each
                    select="//doNotShareClinicalDocumentSectionTypeCodesList/doNotShareClinicalDocumentSectionTypeCodes">
                    <ObligationExpression
                        ObligationId="urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code"
                        FulfillOn="Permit">
                        <AttributeAssignmentExpression
                            AttributeId="urn:oasis:names:tc:xacml:3.0:example:attribute:text">
                            <AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">
                                <xsl:value-of select="code"/>
                            </AttributeValue>
                        </AttributeAssignmentExpression>
                    </ObligationExpression>
                </xsl:for-each>
                
                <xsl:for-each
                    select="//doNotShareSensitivityPolicyCodesList/doNotShareSensitivityPolicyCodes">
                    <ObligationExpression
                        ObligationId="urn:samhsa:names:tc:consent2share:1.0:obligation:redact-sensitivity-code"
                        FulfillOn="Permit">
                        <AttributeAssignmentExpression
                            AttributeId="urn:oasis:names:tc:xacml:3.0:example:attribute:text">
                            <AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">
                                <xsl:value-of select="code"/>
                            </AttributeValue>
                        </AttributeAssignmentExpression>
                    </ObligationExpression>
                </xsl:for-each>
                
                
                <xsl:for-each
                    select="//doNotShareClinicalConceptCodesList/doNotShareClinicalConceptCodes">
                    <ObligationExpression
                        ObligationId="urn:samhsa:names:tc:consent2share:1.0:obligation:redact-clinical-concept-code"
                        FulfillOn="Permit">
                        <AttributeAssignmentExpression
                            AttributeId="urn:oasis:names:tc:xacml:3.0:example:attribute:text">
                            <AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">
                                <xsl:value-of select="code"/>
                            </AttributeValue>
                        </AttributeAssignmentExpression>
                    </ObligationExpression>
                </xsl:for-each>
                
            </ObligationExpressions>
        </Policy>
    </xsl:variable>

    <xsl:variable name="currentEffectiveTime">
        <xsl:value-of select="substring($currentDateTimeUtc, 0, 4)"/>
        <xsl:value-of select="substring($currentDateTimeUtc, 6, 1)"/>
        <xsl:value-of select="substring($currentDateTimeUtc, 9, 2)"/>
        <xsl:value-of select="substring($currentDateTimeUtc, 12, 2)"/>
        <xsl:value-of select="substring($currentDateTimeUtc, 15, 2)"/>
        <xsl:value-of select="substring($currentDateTimeUtc, 18, 2)"/>
        <xsl:value-of select="substring($currentDateTimeUtc, 21, 2)"/>
        <xsl:text>+0000</xsl:text>
    </xsl:variable>

    <xsl:template match="/">


        <ClinicalDocument xmlns="urn:hl7-org:v3"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" classCode="DOCCLIN" moodCode="EVN">
            <!-- Consent Directive DSTU Header-->
            <realmCode code="US"/>
            <typeId root="2.16.840.1.113883.1.3" extension="09230"/>
            <!-- General Header Constraints -->
            <templateId root="2.16.840.1.113883.10.20.3"/>
            <!--  Consent Directive Header Constraints -->
            <templateId root="2.16.840.1.113883.3.445.1"/>
            <!-- Document instance id-->
            <id root="1.3.6.4.1.4.1.2835.888888" extension="221"/>
            <code code="57016-8" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC"
                displayName="Privacy Policy Acknowledgement Document"/>
            <title representation="TXT" mediaType="text/plain">Consent Authorization</title>
            <effectiveTime value="{$currentEffectiveTime}"/>
            <confidentialityCode code="N"/>
            <!-- Client/Record Target  Reference-->
            <recordTarget>
                <patientRole>
                    <id extension="266666666" root="2.16.840.1.113883.3.933"/>
                    <addr>
                        <streetAddressLine>
                            <xsl:value-of select="$patient/addressTypeStreetAddressLine"/>
                        </streetAddressLine>
                        <city>
                            <xsl:value-of select="$patient/addressTypeCity"/>
                        </city>
                        <state>
                            <xsl:value-of select="$patient/addressTypeStateCode"/>
                        </state>
                        <postalCode>
                            <xsl:value-of select="$patient/addressTypePostalCode"/>
                        </postalCode>
                        <country>
                            <xsl:value-of select="$patient/addressTypeCountryCode"/>
                        </country>
                    </addr>
                    <patient>
                        <name>
                            <given>
                                <xsl:value-of select="$patient/firstName"/>
                            </given>
                            <family>
                                <xsl:value-of select="$patient/lastName"/>
                            </family>
                        </name>
                        <administrativeGenderCode code="{$patient/administrativeGenderCode}"
                            codeSystem="2.16.840.1.113883.5.1"/>
                        <birthTime value="{$patient/birthDate}"/>
                    </patient>
                </patientRole>
            </recordTarget>
            <!-- Person and/or organization issuing the consent directive form-->
            <author>
                <templateId root="2.16.840.1.113883.3.445.2"/>
                <functionCode code="POACON"
                    displayName="healthcare power of attorney consent author"
                    codeSystem="2.16.840.1.113883.1.11.19930"
                    codeSystemName="ConsenterParticipationFunction Decision Maker"/>
                <time value="201003122244"/>
                <assignedAuthor>
                    <id extension="11111111" root="1.3.5.35.1.4436.7"/>
                    <assignedPerson classCode="PSN">
                        <name>
                            <given>Default</given>
                            <family>Authority</family>
                        </name>
                    </assignedPerson>
                    <representedOrganization>
                        <id root="1.3.6.4.1.4.1.2835.2" extension="980983"/>
                        <name>Level Seven Healthcare, Inc.</name>
                        <telecom value="409-444-2353"/>
                        <addr>
                            <streetAddressLine>4444 Healthcare Drive</streetAddressLine>
                            <city>Ann Arbor</city>
                            <state>MI</state>
                            <postalCode>99999</postalCode>
                            <country>US</country>
                        </addr>
                    </representedOrganization>
                </assignedAuthor>
            </author>
            <!-- Clerk who entered or scanned the consent (optional)-->
            <!--dataEnterer typeCode="ENT" contextControlCode="OP">
                <time value="200910262244+050"/>
                <assignedEntity>
                    <id extension="22222222" root="1.3.6.4.1.4.1.2835.2"/>
                    <assignedPerson>
                        <name>
                            <prefix>Ms.</prefix>
                            <given>Joan</given>
                            <family>Clerk</family>
                        </name>
                    </assignedPerson>
                </assignedEntity>
            </dataEnterer-->
            <!-- Information Custodian -->
            <custodian>
                <assignedCustodian>
                    <representedCustodianOrganization>
                        <id root="1.3.6.4.1.4.1.2835.2" extension="980983"/>
                        <name>Level Seven Healthcare, Inc.</name>
                        <telecom value="409-444-2353"/>
                        <addr>
                            <streetAddressLine>4444 Healthcare Drive</streetAddressLine>
                            <city>Ann Arbor</city>
                            <state>MI</state>
                            <postalCode>99999</postalCode>
                            <country>US</country>
                        </addr>
                    </representedCustodianOrganization>
                </assignedCustodian>
            </custodian>
            <!-- Information Recipient - may be both Consent Directive receiver and intended information receiver  -->
            <xsl:for-each select="//providersDisclosureIsMadeToList/providersDisclosureIsMadeTo">
                <informationRecipient typeCode="PRCP">
                    <intendedRecipient classCode="ASSIGNED">
                        <id root="1.3.6.4.1.4.1.2835.2" extension="{npi}"/>
                        <addr>
                            <streetAddressLine>
                                <xsl:value-of select="firstLinePracticeLocationAddress"/>
                            </streetAddressLine>
                            <city>
                                <xsl:value-of select="practiceLocationAddressCityName"/>
                            </city>
                            <state>
                                <xsl:value-of select="practiceLocationAddressStateName"/>
                            </state>
                            <postalCode>
                                <xsl:value-of select="practiceLocationAddressPostalCode"/>
                            </postalCode>
                        </addr>
                        <informationRecipient classCode="PSN" determinerCode="INSTANCE">
                            <name>
                                <prefix>
                                    <xsl:value-of select="namePrefix"/>
                                </prefix>
                                <family>
                                    <xsl:value-of select="lastName"/>
                                </family>
                                <given>
                                    <xsl:value-of select="firstName"/>
                                </given>
                                <suffix>
                                    <xsl:value-of select="nameSuffix"/>
                                </suffix>
                            </name>
                        </informationRecipient>
                    </intendedRecipient>
                </informationRecipient>
            </xsl:for-each>

            <xsl:for-each
                select="//organizationalProvidersDisclosureIsMadeToList/organizationalProvidersDisclosureIsMadeTo">
                <informationRecipient typeCode="PRCP">
                    <intendedRecipient classCode="ASSIGNED">
                        <id root="1.3.6.4.1.4.1.2835.2" extension="{npi}"/>
                        <addr>
                            <streetAddressLine>
                                <xsl:value-of select="firstLinePracticeLocationAddress"/>
                            </streetAddressLine>
                            <city>
                                <xsl:value-of select="practiceLocationAddressCityName"/>
                            </city>
                            <state>
                                <xsl:value-of select="practiceLocationAddressStateName"/>
                            </state>
                            <postalCode>
                                <xsl:value-of select="practiceLocationAddressPostalCode"/>
                            </postalCode>
                        </addr>
                        <receivedOrganization classCode="ORG" determinerCode="INSTANCE">
                            <id root="1.3.6.4.1.4.1.2835.2" extension="09809"
                                assigningAuthorityName="NPI"/>
                            <name>
                                <xsl:value-of select="orgName"/>
                            </name>
                        </receivedOrganization>
                    </intendedRecipient>
                </informationRecipient>
            </xsl:for-each>



            <!-- Substitute Decision Maker or Patient that signs the Consent Directive -->
            <!--legalAuthenticator contextControlCode="OP" typeCode="LA">
                <time value="20091025"/>
               
                <signatureCode code="S"/>
                <assignedEntity classCode="ASSIGNED">
                    <id extension="11111111" root="1.3.5.35.1.4436.7"/>
                    <assignedPerson classCode="PSN">
                        <name>
                            <given>Bernard</given>
                            <family>Everyperson</family>
                            <suffix>Sr.</suffix>
                        </name>
                    </assignedPerson>
                </assignedEntity>
            </legalAuthenticator-->
            <!-- Witness -->
            <authenticator>
                <time value="20091025"/>
                <signatureCode code="S"/>
                <assignedEntity>
                    <id extension="112" root="1.3.5.35.1.4436.7"/>
                    <representedOrganization>
                        <name>Adobe Echo Sign</name>
                    </representedOrganization>

                </assignedEntity>
            </authenticator>
            <!-- Effective time for the Consent Directive -->
            <documentationOf typeCode="DOC">
                <serviceEvent moodCode="EVN">
                    <templateId root="2.16.840.1.113883.3.445.3"/>
                    <id root="2.16.840.1.113883.3.445.3"/>
                    <code code="57016-8" codeSystem="2.16.840.1.113883.6.1" codeSystemName="LOINC"
                        displayName="Privacy Policy Acknowledgement Document"/>
                    <effectiveTime>
                        <low value="{/ConsentExport/consentStart}"/>
                        <high value="{/ConsentExport/consentEnd}"/>
                    </effectiveTime>
                </serviceEvent>
            </documentationOf>
            <!-- Previous Consent Directive Reference -->
            <relatedDocument typeCode="RPLC">
                <parentDocument classCode="DOCCLIN" moodCode="EVN">
                    <id root="1.3.6.1.4.1.19376.1.5.3.1.2.6" extension="2345"/>
                </parentDocument>
            </relatedDocument>
            <component typeCode="COMP" contextConductionInd="true">
                <structuredBody>
                    <component typeCode="COMP" contextConductionInd="true">
                        <section classCode="DOCSECT" moodCode="EVN">
                            <!-- Consent Directive Details section -->
                            <templateId root="2.16.840.1.113883.3.445.17"/>
                            <title>Consent Directive Details</title>
                            <!-- Narrative consent  directive-->
                            <text mediaType="text/x-hl7-text+xml">
                                <paragraph>CONSENT FOR RELEASE OF <content
                                        ID="Clinical_Condition_SNOMED-CT_371422002">SUBSTANCE
                                        ABUSE</content> INFORMATION</paragraph>
                                <paragraph> I, <content ID="Patient_Name">
                                        <xsl:value-of
                                            select="concat($patient/firstName,' ', $patient/lastName)"
                                        /></content>
                                    <content ID="permit">authorize</content>
                                    <content ID="CustodianOrganization">
                                        <xsl:for-each
                                            select="//providersPermittedToDiscloseList/providersPermittedToDisclose">
                                            <xsl:value-of select="concat(firstName,' ', lastName)"
                                            />, </xsl:for-each>
                                        <xsl:for-each
                                            select="//organizationalProvidersPermittedToDiscloseList/organizationalProvidersPermittedToDisclose">
                                            <xsl:value-of select="orgName"/>, </xsl:for-each>
                                    </content> to <content ID="Operation">disclose</content> to
                                        <content ID="ReceiverOrganization_1">
                                        <xsl:for-each
                                            select="//providersDisclosureIsMadeToList/providersDisclosureIsMadeTo">
                                            <xsl:value-of select="concat(firstName,' ', lastName)"
                                            />, </xsl:for-each>
                                        <xsl:for-each
                                            select="//organizationalProvidersDisclosureIsMadeToList/organizationalProvidersDisclosureIsMadeTo">
                                            <xsl:value-of select="orgName"/>, </xsl:for-each>
                                    </content> all my medical information except the following
                                    information: </paragraph>

                                <xsl:for-each
                                    select="//doNotShareClinicalDocumentTypeCodesList/doNotShareClinicalDocumentTypeCodes">
                                    <paragraph> * <content><xsl:value-of select="displayName"
                                            /></content></paragraph>
                                </xsl:for-each>

                                <xsl:for-each
                                    select="//doNotShareClinicalDocumentSectionTypeCodesList/doNotShareClinicalDocumentSectionTypeCodes">
                                    <paragraph> * <content><xsl:value-of select="displayName"
                                            /></content></paragraph>
                                </xsl:for-each>

                                <xsl:for-each
                                    select="//doNotShareSensitivityPolicyCodesList/doNotShareSensitivityPolicyCodes">
                                    <paragraph> * <content><xsl:value-of select="displayName"
                                            /></content></paragraph>
                                </xsl:for-each>



                                <paragraph> The purpose of this disclosure is all purpose except:
                                        <content ID="purpose">
                                        <xsl:for-each
                                            select="//doNotShareForPurposeOfUseCodesList/doNotShareForPurposeOfUseCodes">
                                            <xsl:value-of select="displayName"/>, </xsl:for-each>
                                    </content></paragraph>
                            </text>
                            <!-- Consent Directive Entry  -->

                            <entry contextConductionInd="true" typeCode="COMP">
                                <act classCode="CONS" moodCode="DEF" negationInd="false">
                                    <code code="DIS" codeSystem="2.16.840.1.113883.1.11.19897"
                                        displayName="Disclose" codeSystemName="ActConsentType"/>
                                    <entryRelationship typeCode="COMP">
                                        <templateId extension="Platform-specific Consent Directive"/>
                                        <observationMedia classCode="OBS" moodCode="EVN">
                                            <value mediaType="text/xml" representation="TXT">
                                                
                                                
                                                <xsl:sequence select="$xacml"/>
                                                
                                                
                                            </value>
                                        </observationMedia>
                                    </entryRelationship>
                                </act>
                            </entry>




                        </section>
                    </component>



                </structuredBody>
            </component>
        </ClinicalDocument>





    </xsl:template>



</xsl:stylesheet>
