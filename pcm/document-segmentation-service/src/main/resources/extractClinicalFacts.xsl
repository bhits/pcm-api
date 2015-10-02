<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:xs="http://www.w3.org/2001/XMLSchema" xmlns:sdtc="urn:hl7-org:sdtc"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" exclude-result-prefixes="xs" version="2.0"
    xpath-default-namespace="urn:hl7-org:v3" xmlns:voc="urn:hl7-org:v3/voc">
    <xsl:output indent="yes" omit-xml-declaration="yes"/>
    <xsl:strip-space elements="*"/>

    <!-- Extracts clinical facts from the original document. A clinical fact contains a code, a displayName, a code system,
    the title and LOINC code of the section that contains the fact, and the observationId. The fact model also includes the XACML Result from the PDP.
    The fact model is inserted into the working memory of the Drools rules engine. The latter returns a set of directives for segmenting the clinical document.-->

    <xsl:variable name="xacmlResult" select="document('xacmlResult')"/>

    <xsl:template match="/">
        <FactModel xsl:exclude-result-prefixes="#all">
            <xsl:sequence select="$xacmlResult"/>

            <xsl:variable name="entry"
                select="/ClinicalDocument/component/structuredBody/component/section/entry"/>

            <xsl:variable name="documentationOfServiceEvent"
                select="/ClinicalDocument/documentationOf/serviceEvent"/>

            <ClinicalFacts>
                
                <xsl:for-each select="$documentationOfServiceEvent//.[@code and @codeSystem]">
                    <ClinicalFact>
                        <xsl:call-template name="serviceEvents"/>
                    </ClinicalFact>
                </xsl:for-each>

                <xsl:for-each select="$entry//.[@code and @codeSystem]">
                    <ClinicalFact>
                        <xsl:call-template name="clinicalFacts"/>
                    </ClinicalFact>
                </xsl:for-each>

            </ClinicalFacts>

            <EntryReferences>

                <!--<xsl:for-each select="$entry//reference[starts-with(@value, '#')]">-->
                <xsl:for-each select="$entry//reference">
                    <EntryReference>
                        <xsl:call-template name="entryReferences"/>
                    </EntryReference>
                </xsl:for-each>

            </EntryReferences>

            <!--<xsl:result-document href="c32-out.xml">-->
            <EmbeddedClinicalDocument xsl:exclude-result-prefixes="#all" xmlns="urn:hl7-org:v3">
                <xsl:call-template name="clinicalDocument"/>
                <xsl:call-template name="entry"/>
                <xsl:call-template name="serviceEvent"/>
            </EmbeddedClinicalDocument>
            <!--</xsl:result-document>-->
        </FactModel>

    </xsl:template>
    
    <xsl:template name="serviceEvents" exclude-result-prefixes="#all">
        <code>
            <xsl:value-of select="@code"/>
        </code>
        <displayName>
            <xsl:value-of select="@displayName"/>
        </displayName>
        <codeSystem>
            <xsl:value-of select="@codeSystem"/>
        </codeSystem>
        <codeSystemName>
            <xsl:value-of select="@codeSystemName"/>
        </codeSystemName>
        <entry>
            <xsl:value-of select="generate-id(ancestor::serviceEvent)"/>
        </entry>
    </xsl:template>

    <xsl:template name="clinicalFacts" exclude-result-prefixes="#all">
        <code>
            <xsl:value-of select="@code"/>
        </code>
        <displayName>
            <xsl:value-of select="@displayName"/>
        </displayName>
        <codeSystem>
            <xsl:value-of select="@codeSystem"/>
        </codeSystem>
        <codeSystemName>
            <xsl:value-of select="@codeSystemName"/>
        </codeSystemName>
        <c32SectionTitle>
            <xsl:value-of select="ancestor::section[1]/title"/>
        </c32SectionTitle>
        <c32SectionLoincCode>
            <xsl:value-of select="ancestor::section[1]/code/@code"/>
        </c32SectionLoincCode>
        <observationId>
            <xsl:value-of select="../id/@root"/>
        </observationId>
        <entry>
            <xsl:value-of select="generate-id(ancestor::entry)"/>
        </entry>
    </xsl:template>

    <xsl:template name="entryReferences" exclude-result-prefixes="#all">
        <entry>
            <xsl:value-of select="generate-id(ancestor::entry)"/>
        </entry>
        <reference>
            <!--<xsl:value-of select="substring(@value,2)"/>-->
            <xsl:value-of select="replace(@value, '#', '')"/>
        </reference>
    </xsl:template>

    <xsl:template name="clinicalDocument" match="@*|node()">
        <xsl:copy>
            <xsl:apply-templates select="@*|node()"/>
        </xsl:copy>
    </xsl:template>
    <xsl:template name="serviceEvent" match="serviceEvent[parent::documentationOf]">
        <xsl:copy>
            <xsl:apply-templates select="@*"/>
            <xsl:apply-templates select="realmCode"/>
            <xsl:apply-templates select="typeId"/>
            <xsl:apply-templates select="templateId"/>
            <xsl:apply-templates select="id"/>
            <xsl:apply-templates select="code"/>
            <xsl:apply-templates select="effectiveTime"/>
            <xsl:apply-templates select="performer"/>
            <generatedServiceEventId xsl:exclude-result-prefixes="#all" xmlns="urn:hl7-org:v3">
                <xsl:value-of select="generate-id(.)"/>
            </generatedServiceEventId>
        </xsl:copy>
    </xsl:template>
    <xsl:template name="entry" match="entry">
        <xsl:copy>
            <xsl:apply-templates select="@*"/>
            <xsl:apply-templates select="realmCode"/>
            <xsl:apply-templates select="typeId"/>
            <xsl:apply-templates select="templateId"/>
            <xsl:apply-templates select="act"/>
            <xsl:apply-templates select="encounter"/>
            <xsl:apply-templates select="observation"/>
            <xsl:apply-templates select="observationMedia"/>
            <xsl:apply-templates select="organizer"/>
            <xsl:apply-templates select="procedure"/>
            <xsl:apply-templates select="regionOfInterest"/>
            <xsl:apply-templates select="substanceAdministration"/>
            <xsl:apply-templates select="supply"/>
            <generatedEntryId xsl:exclude-result-prefixes="#all" xmlns="urn:hl7-org:v3">
                <xsl:value-of select="generate-id(.)"/>
            </generatedEntryId>
        </xsl:copy>
    </xsl:template>

</xsl:stylesheet>
