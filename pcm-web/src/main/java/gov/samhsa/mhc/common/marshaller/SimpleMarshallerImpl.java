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
package gov.samhsa.mhc.common.marshaller;

import javax.xml.bind.*;
import javax.xml.namespace.QName;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;

/**
 * The Class MarshallerImpl.
 */
public class SimpleMarshallerImpl implements SimpleMarshaller {

    /*
     * (non-Javadoc)
     *
     * @see gov.samhsa.acs.common.tool.Marshaller# marshall(java.lang.Object)
     */
    @Override
    public String marshal(Object obj) throws SimpleMarshallerException {
        return marshal(obj, obj.getClass());
    }

    @Override
    public ByteArrayOutputStream marshalAsByteArrayOutputStream(Object obj)
            throws SimpleMarshallerException {
        try {
            final ByteArrayOutputStream marshalresult = new ByteArrayOutputStream();

            JAXBContext jaxbContext;
            jaxbContext = JAXBContext.newInstance(obj.getClass());
            final Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
                    Boolean.TRUE);
            marshaller.marshal(obj, marshalresult);

            return marshalresult;
        } catch (final Exception e) {
            throw new SimpleMarshallerException(e);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.acs.common.tool.SimpleMarshaller#marshalWithoutRootElement
     * (java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> String marshalWithoutRootElement(T obj)
            throws SimpleMarshallerException {
        final JAXBElement<T> wrappedJaxbElement = new JAXBElement<T>(new QName(
                obj.getClass().getSimpleName()), (Class<T>) obj.getClass(), obj);
        return marshal(wrappedJaxbElement, obj.getClass());
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * gov.samhsa.acs.common.tool.SimpleMarshaller#unmarshalFromXml(java.lang
     * .Class, java.lang.String)
     */
    @Override
    public <T> T unmarshalFromXml(Class<T> clazz, String xml)
            throws SimpleMarshallerException {
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(clazz);
        } catch (final JAXBException e) {
            throw new SimpleMarshallerException(e);
        }
        return unmarshalFromXml(context, clazz, xml);
    }

    /**
     * Marshal.
     *
     * @param <T>
     *            the generic type
     * @param obj
     *            the obj
     * @param contextClass
     *            the context class
     * @return the string
     * @throws SimpleMarshallerException
     *             the simple marshaller exception
     */
    private <T> String marshal(Object obj, Class<T> contextClass)
            throws SimpleMarshallerException {
        JAXBContext context;
        String output;
        try {
            context = JAXBContext.newInstance(contextClass);

            // Create the marshaller, this is the nifty little thing that will
            // actually transform the object into XML
            final Marshaller marshaller = context.createMarshaller();

            // Create a stringWriter to hold the XML
            final StringWriter stringWriter = new StringWriter();

            // Marshal the javaObject and write the XML to the stringWriter
            marshaller.marshal(obj, stringWriter);
            output = stringWriter.toString();
        } catch (final JAXBException e) {
            throw new SimpleMarshallerException(e);
        }
        return output;
    }

    /**
     * Unmarshal from xml.
     *
     * @param <T>
     *            the generic type
     * @param context
     *            the context
     * @param clazz
     *            the clazz
     * @param xml
     *            the xml
     * @return the t
     * @throws SimpleMarshallerException
     *             the simple marshaller exception
     */
    @SuppressWarnings("unchecked")
    private <T> T unmarshalFromXml(JAXBContext context, Class<T> clazz,
                                   String xml) throws SimpleMarshallerException {
        Unmarshaller um;
        try {
            um = context.createUnmarshaller();
            final ByteArrayInputStream input = new ByteArrayInputStream(
                    xml.getBytes("UTF-8"));
            return (T) um.unmarshal(input);
        } catch (JAXBException | UnsupportedEncodingException e) {
            throw new SimpleMarshallerException(e);
        }
    }
}
