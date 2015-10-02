<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" exclude-result-prefixes="xs"
	version="2.0">

	<xsl:output indent="yes" />

	<xsl:param name="enterpriseIdentifier" as="xs:string" select="'PleaseEnterValidMRNForEID'"/>
	<xsl:param name="medicalRecordNumber" as="xs:string" select="'PleaseEnterValidMRN'"/>
	
	<xsl:template match="/">
		<xsl:variable name="countDoNotShareClinicalDocumentSectionTypeCodes"
			select="count(//doNotShareClinicalDocumentSectionTypeCodesList/doNotShareClinicalDocumentSectionTypeCodes)+count(//doNotShareSensitivityPolicyCodesList/doNotShareSensitivityPolicyCodes)+count(//doNotShareClinicalConceptCodesList/doNotShareClinicalConceptCodes)+count(//doNotShareClinicalDocumentTypeCodesList/doNotShareClinicalDocumentTypeCodes)" />
		<!-- <xsl:variable name="countDoNotShareSensitivityPolicyCodes" select="count(//doNotShareSensitivityPolicyCodesList/doNotShareSensitivityPolicyCodes)" 
			/> -->
		<!-- <xsl:variable name="countDoNotShareClinicalConceptCodes" select="count(//doNotShareClinicalConceptCodesList/doNotShareClinicalConceptCodes)" 
			/> -->
		<xsl:variable name="consentId" select="ConsentExport/id" />
		<xsl:variable name="patientId" select="ConsentExport/Patient/email" />
		<xsl:variable name="resourceId">
			<xsl:choose>
				<xsl:when test="not(codepoint-equal($enterpriseIdentifier,'PleaseEnterValidMRNForEID'))">
					<xsl:value-of select="$enterpriseIdentifier"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$patientId"/>
				</xsl:otherwise>
			</xsl:choose>		
		</xsl:variable>
		<xsl:variable name="policyId"
			select="concat('urn:samhsa:names:tc:consent2share:1.0:policyid:', $patientId, ':', $consentId)" />

		<Policy xmlns="urn:oasis:names:tc:xacml:2.0:policy:schema:os"
			PolicyId="{$consentId}"
			RuleCombiningAlgId="urn:oasis:names:tc:xacml:1.0:rule-combining-algorithm:permit-overrides">
			<Description>
				This is a reference policy for
				<xsl:value-of select="$patientId" />
			</Description>
			<Target></Target>
			<Rule Effect="Permit" RuleId="primary-group-rule">
				<Target>
					<Resources>
						<Resource>
							<ResourceMatch
								MatchId="urn:oasis:names:tc:xacml:1.0:function:string-regexp-match">
								<AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">
									<!-- xsl:value-of select="ConsentExport/Patient/email" /-->
									<xsl:value-of select="$resourceId" />
								</AttributeValue>
								<ResourceAttributeDesignator
									AttributeId="urn:oasis:names:tc:xacml:1.0:resource:resource-id"
									DataType="http://www.w3.org/2001/XMLSchema#string"></ResourceAttributeDesignator>
							</ResourceMatch>
						</Resource>
					</Resources>
					<Actions>
						<Action>
							<ActionMatch MatchId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
								<AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">write</AttributeValue>
								<ActionAttributeDesignator
									AttributeId="urn:oasis:names:tc:xacml:1.0:action:action-id"
									DataType="http://www.w3.org/2001/XMLSchema#string"></ActionAttributeDesignator>
							</ActionMatch>
						</Action>
					</Actions>
				</Target>
				<Condition>
					<Apply FunctionId="urn:oasis:names:tc:xacml:1.0:function:and">

						<Apply FunctionId="urn:oasis:names:tc:xacml:1.0:function:or">

							<xsl:for-each
								select="//IndividualProvidersPermittedToDiscloseList/IndividualProvidersPermittedToDisclose">

								<Apply FunctionId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
									<Apply
										FunctionId="urn:oasis:names:tc:xacml:1.0:function:string-one-and-only">
										<SubjectAttributeDesignator
											MustBePresent="false"
											AttributeId="urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject"
											DataType="http://www.w3.org/2001/XMLSchema#string"></SubjectAttributeDesignator>
									</Apply>
									<AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">
										<xsl:value-of select="npi" />
									</AttributeValue>
								</Apply>

							</xsl:for-each>


							<xsl:for-each
								select="//OrganizationalProvidersPermittedToDiscloseList/OrganizationalProvidersPermittedToDisclose">

								<Apply FunctionId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
									<Apply
										FunctionId="urn:oasis:names:tc:xacml:1.0:function:string-one-and-only">
										<SubjectAttributeDesignator
											MustBePresent="false"
											AttributeId="urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject"
											DataType="http://www.w3.org/2001/XMLSchema#string"></SubjectAttributeDesignator>
									</Apply>
									<AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">
										<xsl:value-of select="npi" />
									</AttributeValue>
								</Apply>

							</xsl:for-each>

						</Apply>
						<Apply FunctionId="urn:oasis:names:tc:xacml:1.0:function:or">
							<xsl:for-each
								select="//IndividualProvidersDisclosureIsMadeToList/IndividualProvidersDisclosureIsMadeTo">
								<Apply FunctionId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
									<Apply
										FunctionId="urn:oasis:names:tc:xacml:1.0:function:string-one-and-only">
										<SubjectAttributeDesignator
											MustBePresent="false"
											AttributeId="urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject"
											DataType="http://www.w3.org/2001/XMLSchema#string"></SubjectAttributeDesignator>
									</Apply>
									<AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">
										<xsl:value-of select="npi" />
									</AttributeValue>
								</Apply>
							</xsl:for-each>

							<xsl:for-each
								select="//OrganizationalProvidersDisclosureIsMadeToList/OrganizationalProvidersDisclosureIsMadeTo">
								<Apply FunctionId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
									<Apply
										FunctionId="urn:oasis:names:tc:xacml:1.0:function:string-one-and-only">
										<SubjectAttributeDesignator
											MustBePresent="false"
											AttributeId="urn:oasis:names:tc:xacml:1.0:subject-category:recipient-subject"
											DataType="http://www.w3.org/2001/XMLSchema#string"></SubjectAttributeDesignator>
									</Apply>
									<AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">
										<xsl:value-of select="npi" />
									</AttributeValue>
								</Apply>
							</xsl:for-each>
						</Apply>
						<Apply FunctionId="urn:oasis:names:tc:xacml:1.0:function:or">

							<xsl:for-each
								select="//shareForPurposeOfUseCodesList/shareForPurposeOfUseCodes">

								<Apply FunctionId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
									<Apply
										FunctionId="urn:oasis:names:tc:xacml:1.0:function:string-one-and-only">
										<SubjectAttributeDesignator
											MustBePresent="false" AttributeId="gov.samhsa.consent2share.purpose-of-use-code"
											DataType="http://www.w3.org/2001/XMLSchema#string"></SubjectAttributeDesignator>
									</Apply>
									<AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">
										<xsl:value-of select="code" />
									</AttributeValue>
								</Apply>


							</xsl:for-each>
						</Apply>

						<xsl:for-each select="//consentStart">




							<Apply
								FunctionId="urn:oasis:names:tc:xacml:1.0:function:dateTime-greater-than-or-equal">
								<Apply
									FunctionId="urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only">
									<EnvironmentAttributeDesignator
										MustBePresent="false"
										AttributeId="urn:oasis:names:tc:xacml:1.0:environment:current-dateTime"
										DataType="http://www.w3.org/2001/XMLSchema#dateTime"></EnvironmentAttributeDesignator>
								</Apply>
								<AttributeValue DataType="http://www.w3.org/2001/XMLSchema#dateTime">
									<xsl:value-of select="." />
								</AttributeValue>
							</Apply>

						</xsl:for-each>



						<xsl:for-each select="//consentEnd">
							<Apply
								FunctionId="urn:oasis:names:tc:xacml:1.0:function:dateTime-less-than-or-equal">
								<Apply
									FunctionId="urn:oasis:names:tc:xacml:1.0:function:dateTime-one-and-only">
									<EnvironmentAttributeDesignator
										MustBePresent="false"
										AttributeId="urn:oasis:names:tc:xacml:1.0:environment:current-dateTime"
										DataType="http://www.w3.org/2001/XMLSchema#dateTime"></EnvironmentAttributeDesignator>
								</Apply>
								<AttributeValue DataType="http://www.w3.org/2001/XMLSchema#dateTime">
									<xsl:value-of select="." />
								</AttributeValue>
							</Apply>

						</xsl:for-each>


					</Apply>
				</Condition>
			</Rule>

			<Rule Effect="Deny" RuleId="deny-others"></Rule>

			<xsl:if test="$countDoNotShareClinicalDocumentSectionTypeCodes > 0">
				<Obligations>

					<xsl:for-each
						select="//doNotShareClinicalDocumentSectionTypeCodesList/doNotShareClinicalDocumentSectionTypeCodes">
						<Obligation
							ObligationId="urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code"
							FulfillOn="Permit">
							<AttributeAssignment
								AttributeId="urn:oasis:names:tc:xacml:3.0:example:attribute:text"
								DataType="http://www.w3.org/2001/XMLSchema#string">
								<xsl:value-of select="code" />
							</AttributeAssignment>
						</Obligation>
					</xsl:for-each>

					<xsl:for-each
						select="//doNotShareSensitivityPolicyCodesList/doNotShareSensitivityPolicyCodes">
						<Obligation
							ObligationId="urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code"
							FulfillOn="Permit">
							<AttributeAssignment
								AttributeId="urn:oasis:names:tc:xacml:3.0:example:attribute:text"
								DataType="http://www.w3.org/2001/XMLSchema#string">
								<xsl:value-of select="code" />
							</AttributeAssignment>
						</Obligation>
					</xsl:for-each>

					<xsl:for-each
						select="//doNotShareClinicalDocumentTypeCodesList/doNotShareClinicalDocumentTypeCodes">
						<Obligation
							ObligationId="urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code"
							FulfillOn="Permit">
							<AttributeAssignment
								AttributeId="urn:oasis:names:tc:xacml:3.0:example:attribute:text"
								DataType="http://www.w3.org/2001/XMLSchema#string">
								<xsl:value-of select="code" />
							</AttributeAssignment>
						</Obligation>
					</xsl:for-each>




					<xsl:for-each
						select="//doNotShareClinicalConceptCodesList/doNotShareClinicalConceptCodes">
						<Obligation
							ObligationId="urn:samhsa:names:tc:consent2share:1.0:obligation:redact-document-section-code"
							FulfillOn="Permit">
							<AttributeAssignment
								AttributeId="urn:oasis:names:tc:xacml:3.0:example:attribute:text"
								DataType="http://www.w3.org/2001/XMLSchema#string">
								<xsl:value-of select="code" />
							</AttributeAssignment>
						</Obligation>
					</xsl:for-each>



				</Obligations>
			</xsl:if>


		</Policy>

	</xsl:template>


</xsl:stylesheet>