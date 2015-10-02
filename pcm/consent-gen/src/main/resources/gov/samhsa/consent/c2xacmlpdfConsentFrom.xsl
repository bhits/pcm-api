<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:xs="http://www.w3.org/2001/XMLSchema" exclude-result-prefixes="xs"
	version="2.0">

	<xsl:output indent="yes" />

	<xsl:param name="medicalRecordNumber" as="xs:string" select="'PleaseEnterValidMRNForEID'"/>
	<xsl:param name="policyId" as="xs:string" select="'PleaseEnterPolicyID'"/>
	
	<xsl:template match="/">
		<xsl:variable name="consentId" select="$policyId" />
		<xsl:variable name="patientId" select="ConsentExport/Patient/email" />
		<xsl:variable name="patientId" select="ConsentExport/Patient/email" />
		<xsl:variable name="resourceId">
			<xsl:choose>
				<xsl:when test="not(codepoint-equal($medicalRecordNumber,'PleaseEnterValidMRNForEID'))">
					<xsl:value-of select="$medicalRecordNumber"/>
				</xsl:when>
				<xsl:otherwise>
					<xsl:value-of select="$patientId"/>
				</xsl:otherwise>
			</xsl:choose>		
		</xsl:variable>
		
		<Policy xmlns="urn:oasis:names:tc:xacml:2.0:policy:schema:os"
			    PolicyId="{$policyId}"
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
							<ResourceMatch MatchId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
								<AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">28636-9</AttributeValue>
								<ResourceAttributeDesignator AttributeId="urn:oasis:names:tc:xacml:1.0:resource:typeCode" DataType="http://www.w3.org/2001/XMLSchema#string"/>
							</ResourceMatch>
						</Resource>
					</Resources>
					<Actions>
					
					<Action>
						<ActionMatch MatchId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
							<AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">xdsquery</AttributeValue>
							<ActionAttributeDesignator AttributeId="urn:oasis:names:tc:xacml:1.0:action:action-id" DataType="http://www.w3.org/2001/XMLSchema#string"/>
						</ActionMatch>
					</Action>
					<Action>
						<ActionMatch MatchId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
							<AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">xdsretrieve</AttributeValue>
							<ActionAttributeDesignator AttributeId="urn:oasis:names:tc:xacml:1.0:action:action-id" DataType="http://www.w3.org/2001/XMLSchema#string"/>
						</ActionMatch>
					</Action>
				</Actions>
				</Target>
				<Condition>
					<Apply FunctionId="urn:oasis:names:tc:xacml:1.0:function:and">
						<Apply FunctionId="urn:oasis:names:tc:xacml:1.0:function:or">
							<Apply FunctionId="urn:oasis:names:tc:xacml:1.0:function:string-equal">
								<Apply
									FunctionId="urn:oasis:names:tc:xacml:1.0:function:string-one-and-only">
									<SubjectAttributeDesignator
										MustBePresent="false"
										AttributeId="urn:oasis:names:tc:xacml:1.0:subject-category:intermediary-subject"
										DataType="http://www.w3.org/2001/XMLSchema#string"></SubjectAttributeDesignator>
								</Apply>
								<AttributeValue DataType="http://www.w3.org/2001/XMLSchema#string">
									C2S Health
								</AttributeValue>
							</Apply>
						</Apply>
				
						<Apply FunctionId="urn:oasis:names:tc:xacml:1.0:function:or">

							<xsl:for-each
								select="//IndividualProvidersPermittedToDiscloseList/IndividualProvidersPermittedToDisclose">

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
								select="//OrganizationalProvidersPermittedToDiscloseList/OrganizationalProvidersPermittedToDisclose">

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
											MustBePresent="false" AttributeId="urn:oasis:names:tc:xspa:1.0:subject:purposeofuse"
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

		</Policy>

	</xsl:template>


</xsl:stylesheet>