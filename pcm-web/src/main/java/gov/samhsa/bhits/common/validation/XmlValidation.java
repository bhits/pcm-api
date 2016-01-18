/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * <p>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of the <organization> nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.bhits.common.validation;

import gov.samhsa.bhits.common.log.Logger;
import gov.samhsa.bhits.common.log.LoggerFactory;
import gov.samhsa.bhits.common.validation.exception.InvalidXmlDocumentException;
import gov.samhsa.bhits.common.validation.exception.XmlDocumentReadFailureException;
import gov.samhsa.bhits.common.validation.exception.XmlSchemaFailureException;
import org.springframework.util.Assert;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * The Class XmlValidation.
 */
public class XmlValidation {

    /**
     * The logger.
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * The schema base path.
     */
    private final String schemaBasePath;

    /**
     * The schema.
     */
    private Schema schema;

    /**
     * The validator.
     */
    private Validator validator;

    /**
     * Instantiates a new xml validation.
     *
     * @param xsdInputStream the xsd input stream
     * @param schemaBasePath the schema base path
     */
    public XmlValidation(InputStream xsdInputStream, String schemaBasePath) {
        LSResourceResolver resourceResolver = null;
        Assert.hasText(schemaBasePath, "'schemaBasePath' must not be empty.");
        Assert.notNull(xsdInputStream, "'xsdInputStream' must not be null.");
        this.schemaBasePath = schemaBasePath;
        resourceResolver = new ResourceResolver(this.schemaBasePath);
        try {
            this.schema = createSchema(xsdInputStream, resourceResolver);
            this.validator = createValidator(this.schema);
        } catch (final XmlSchemaFailureException e) {
            logger.error("XmlValidation initialization is failed: the schema cannot be loaded.");
        }
    }

    /**
     * Validate.
     *
     * @param xmlInputStream the xml input stream
     * @return true, if successful
     * @throws InvalidXmlDocumentException     the invalid xml document exception
     * @throws XmlDocumentReadFailureException the xml document read failure exception
     */
    public boolean validate(InputStream xmlInputStream)
            throws InvalidXmlDocumentException, XmlDocumentReadFailureException {
        Assert.notNull(
                this.validator,
                "There has been an error during XmlValidation initialization, the validator cannot be null.");

        try {
            validator.validate(new StreamSource(xmlInputStream));
        } catch (final SAXException e) {
            logger.error(e.getMessage());
            throw new InvalidXmlDocumentException(e);
        } catch (final IOException e) {
            logger.error(e.getMessage());
            throw new XmlDocumentReadFailureException(e);
        }
        return true;
    }

    /**
     * Validate.
     *
     * @param xml the xml
     * @return true, if successful
     * @throws InvalidXmlDocumentException     the invalid xml document exception
     * @throws XmlDocumentReadFailureException the xml document read failure exception
     */
    public boolean validate(String xml) throws InvalidXmlDocumentException,
            XmlDocumentReadFailureException {
        return validate(new ByteArrayInputStream(xml.getBytes(Charset
                .forName("UTF-8"))));
    }

    /**
     * Validate with all errors.
     *
     * @param xmlInputStream the xml input stream
     * @return the xml validation result
     * @throws XmlDocumentReadFailureException the xml document read failure exception
     */
    public XmlValidationResult validateWithAllErrors(InputStream xmlInputStream)
            throws XmlDocumentReadFailureException {
        Validator validatorWithAllErrors = null;
        XmlValidationResult result = null;
        try {
            validatorWithAllErrors = createValidator(this.schema);
            Assert.notNull(
                    validatorWithAllErrors,
                    "There has been an error during XmlValidation initialization, the validatorWithAllErrors cannot be null.");
            result = new XmlValidationResult();
            validatorWithAllErrors
                    .setErrorHandler(new ErrorHandlerImpl(result));
            validatorWithAllErrors.validate(new StreamSource(xmlInputStream));
        } catch (final XmlSchemaFailureException e) {
            logger.error("XmlValidation initialization is failed: the schema cannot be loaded.");
        } catch (final SAXException e) {
            // Do nothing
        } catch (final IOException e) {
            logger.error(e.getMessage());
            throw new XmlDocumentReadFailureException(e);
        }
        return result;
    }

    /**
     * Validate with all errors.
     *
     * @param xml the xml
     * @return the xml validation result
     * @throws XmlDocumentReadFailureException the xml document read failure exception
     */
    public XmlValidationResult validateWithAllErrors(String xml)
            throws XmlDocumentReadFailureException {
        return validateWithAllErrors(new ByteArrayInputStream(
                xml.getBytes(Charset.forName("UTF-8"))));
    }

    /**
     * Creates the schema.
     *
     * @param xsdInputStream   the xsd input stream
     * @param resourceResolver the resource resolver
     * @return the schema
     * @throws XmlSchemaFailureException the xml schema failure exception
     */
    private Schema createSchema(InputStream xsdInputStream,
                                LSResourceResolver resourceResolver)
            throws XmlSchemaFailureException {
        final SchemaFactory factory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        final Source schemaSource = new StreamSource(xsdInputStream);
        if (resourceResolver != null) {
            factory.setResourceResolver(resourceResolver);
        }
        Schema schema;
        try {
            schema = factory.newSchema(schemaSource);
        } catch (final SAXException e) {
            logger.error(e.getMessage());
            throw new XmlSchemaFailureException(e);
        }
        return schema;
    }

    /**
     * Creates the validator.
     *
     * @param schema the schema
     * @return the validator
     * @throws XmlSchemaFailureException the xml schema failure exception
     */
    private Validator createValidator(Schema schema)
            throws XmlSchemaFailureException {
        return schema.newValidator();
    }
}
