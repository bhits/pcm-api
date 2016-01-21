package gov.samhsa.bhits.common.document.transformer;

import gov.samhsa.bhits.common.marshaller.SimpleMarshaller;
import gov.samhsa.bhits.common.marshaller.SimpleMarshallerException;
import gov.samhsa.bhits.common.param.Params;
import gov.samhsa.bhits.common.log.Logger;
import gov.samhsa.bhits.common.log.LoggerFactory;
import org.w3c.dom.Document;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

/**
 * The Class XmlTransformerImpl.
 */
public class XmlTransformerImpl implements XmlTransformer {

    /**
     * The logger.
     */
    private final Logger logger = LoggerFactory
            .getLogger(this.getClass());

    /**
     * The marshaller.
     */
    private final SimpleMarshaller marshaller;

    /**
     * Instantiates a new xml transformer impl.
     *
     * @param marshaller the marshaller
     */
    public XmlTransformerImpl(SimpleMarshaller marshaller) {
        super();
        this.marshaller = marshaller;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.acs.common.tool.XmlTransformer#transform(org.w3c.dom.Document,
     * javax.xml.transform.Source, java.util.Optional, java.util.Optional)
     */
    @Override
    public String transform(Document xmlDocument, Source xslSource,
                            Optional<Params> params, Optional<URIResolver> uriResolver) {
        return transformToStreamResult(new DOMSource(xmlDocument), xslSource,
                params, uriResolver).getOutputStream().toString();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.acs.common.tool.XmlTransformer#transform(org.w3c.dom.Document,
     * java.lang.String, java.util.Optional, java.util.Optional)
     */
    @Override
    public String transform(Document xmlDocument, String xslFileName,
                            Optional<Params> params, Optional<URIResolver> uriResolver) {
        return transformToString(new DOMSource(xmlDocument), xslFileName,
                params, uriResolver);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.acs.common.tool.XmlTransformer#transform(java.lang.Object,
     * java.lang.String, java.util.Optional, java.util.Optional)
     */
    @Override
    public String transform(Object obj, String xslFileName,
                            Optional<Params> params, Optional<URIResolver> uriResolver) {
        ByteArrayOutputStream sr1;
        Source bais;
        try {
            sr1 = marshaller.marshalAsByteArrayOutputStream(obj);
            bais = new StreamSource(new ByteArrayInputStream(sr1.toByteArray()));
        } catch (final SimpleMarshallerException e) {
            logger.error("Error in JAXB Transfroming", e);
            throw new XmlTransformerException(e.getMessage(), e);
        }
        return transformToString(bais, xslFileName, params, uriResolver);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.acs.common.tool.XmlTransformer#transform(javax.xml.transform
     * .Source, javax.xml.transform.Source, java.util.Optional,
     * java.util.Optional)
     */
    @Override
    public String transform(Source xmlSource, Source xslSource,
                            Optional<Params> params, Optional<URIResolver> uriResolver) {
        return transformToStreamResult(xmlSource, xslSource, params,
                uriResolver).getOutputStream().toString();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.acs.common.tool.XmlTransformer#transform(java.lang.String,
     * java.lang.String, java.util.Optional, java.util.Optional)
     */
    @Override
    public String transform(String xml, String xslFileName,
                            Optional<Params> params, Optional<URIResolver> uriResolver) {
        try {
            return transformToString(new StreamSource(new ByteArrayInputStream(
                    xml.getBytes("UTF-8"))), xslFileName, params, uriResolver);
        } catch (final UnsupportedEncodingException e) {
            logger.error(
                    "Error in converting XML String to byte array using UTF-8",
                    e);
            throw new XmlTransformerException(e.getMessage(), e);
        }
    }

    /**
     * Transform to stream result.
     *
     * @param xmlSource   the xml source
     * @param xslSource   the xsl source
     * @param params      the params
     * @param uriResolver the uri resolver
     * @return the stream result
     */
    private StreamResult transformToStreamResult(Source xmlSource,
                                                 final Source xslSource, Optional<Params> params,
                                                 Optional<URIResolver> uriResolver) {
        try {
            final StreamResult result = new StreamResult(
                    new ByteArrayOutputStream());
            final TransformerFactory tfactory = TransformerFactory
                    .newInstance();
            final Transformer transformer = tfactory.newTransformer(xslSource);
            // Set transformer output properties
            transformer.setOutputProperty(OutputKeys.INDENT, "no");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            // Set URI resolver
            uriResolver.ifPresent(transformer::setURIResolver);
            // Set parameters
            params.ifPresent(param -> param.toMap().forEach(
                    transformer::setParameter));
            // Transform
            transformer.transform(xmlSource, result);
            return result;
        } catch (final Exception e) {
            throw new XmlTransformerException(e.getMessage(), e);
        }
    }

    /**
     * Transform to stream result.
     *
     * @param xslID       the xsl id
     * @param xmlSource   the xml source
     * @param params      the params
     * @param uriResolver the uri resolver
     * @return the stream result
     */
    private StreamResult transformToStreamResult(String xslID,
                                                 Source xmlSource, Optional<Params> params,
                                                 Optional<URIResolver> uriResolver) {
        final Source xslSource = new StreamSource(xslID);

        return transformToStreamResult(xmlSource, xslSource, params,
                uriResolver);
    }

    /**
     * Transform to string.
     *
     * @param source      the source
     * @param xslFileName the xsl file name
     * @param params      the params
     * @param uriResolver the uri resolver
     * @return the string
     */
    private String transformToString(Source source, String xslFileName,
                                     Optional<Params> params, Optional<URIResolver> uriResolver) {
        final StreamResult srcdar = transformToStreamResult(xslFileName,
                source, params, uriResolver);
        return srcdar.getOutputStream().toString();
    }
}
