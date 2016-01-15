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
package gov.samhsa.bhits.common.tool;

import java.io.ByteArrayOutputStream;

/**
 * The Interface SimpleMarshaller.
 */
public interface SimpleMarshaller {

    /**
     * Marshal from object to xml string.
     *
     * @param obj the obj
     * @return the string
     * @throws SimpleMarshallerException the simple marshaller exception
     */
    public abstract String marshal(Object obj) throws SimpleMarshallerException;

    public abstract ByteArrayOutputStream marshalAsByteArrayOutputStream(
            Object obj) throws SimpleMarshallerException;

    /**
     * Marshal without root element.
     *
     * @param <T> the generic type
     * @param obj the obj
     * @return the string
     * @throws SimpleMarshallerException the simple marshaller exception
     */
    public abstract <T> String marshalWithoutRootElement(T obj)
            throws SimpleMarshallerException;

    /**
     * Unmarshal from xml string to generic type object.
     *
     * @param <T>   the generic type
     * @param clazz the clazz
     * @param xml   the xml
     * @return the t
     * @throws SimpleMarshallerException the simple marshaller exception
     */
    public abstract <T> T unmarshalFromXml(Class<T> clazz, String xml)
            throws SimpleMarshallerException;
}
