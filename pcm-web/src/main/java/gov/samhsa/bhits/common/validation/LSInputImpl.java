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


import gov.samhsa.bhits.common.filereader.FileReader;
import org.w3c.dom.ls.LSInput;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.Reader;

/**
 * The Class LSInputImpl.
 */
public class LSInputImpl implements LSInput {

    /** The public id. */
    private String publicId;

    /** The system id. */
    private String systemId;

    /** The input stream. */
    private BufferedInputStream inputStream;

    /** The file reader. */
    private FileReader fileReader;

    /**
     * Instantiates a new lS input impl.
     *
     * @param publicId
     *            the public id
     * @param sysId
     *            the sys id
     * @param input
     *            the input
     * @param fileReader
     *            the file reader
     */
    public LSInputImpl(String publicId, String sysId, InputStream input,
                       FileReader fileReader) {
        this.publicId = publicId;
        this.systemId = sysId;
        this.inputStream = new BufferedInputStream(input);
        this.fileReader = fileReader;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.ls.LSInput#getPublicId()
     */
    @Override
    public String getPublicId() {
        return publicId;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.ls.LSInput#setPublicId(java.lang.String)
     */
    @Override
    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.ls.LSInput#getBaseURI()
     */
    @Override
    public String getBaseURI() {
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.ls.LSInput#setBaseURI(java.lang.String)
     */
    @Override
    public void setBaseURI(String baseURI) {
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.ls.LSInput#getByteStream()
     */
    @Override
    public InputStream getByteStream() {
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.ls.LSInput#setByteStream(java.io.InputStream)
     */
    @Override
    public void setByteStream(InputStream byteStream) {
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.ls.LSInput#getCertifiedText()
     */
    @Override
    public boolean getCertifiedText() {
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.ls.LSInput#setCertifiedText(boolean)
     */
    @Override
    public void setCertifiedText(boolean certifiedText) {
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.ls.LSInput#getCharacterStream()
     */
    @Override
    public Reader getCharacterStream() {
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.ls.LSInput#setCharacterStream(java.io.Reader)
     */
    @Override
    public void setCharacterStream(Reader characterStream) {
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.ls.LSInput#getEncoding()
     */
    @Override
    public String getEncoding() {
        return null;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.ls.LSInput#setEncoding(java.lang.String)
     */
    @Override
    public void setEncoding(String encoding) {
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.ls.LSInput#getStringData()
     */
    @Override
    public String getStringData() {
        synchronized (inputStream) {
            return fileReader.readInputStreamAsString(inputStream);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.ls.LSInput#setStringData(java.lang.String)
     */
    @Override
    public void setStringData(String stringData) {
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.ls.LSInput#getSystemId()
     */
    @Override
    public String getSystemId() {
        return systemId;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.w3c.dom.ls.LSInput#setSystemId(java.lang.String)
     */
    @Override
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    /**
     * Gets the input stream.
     *
     * @return the input stream
     */
    public BufferedInputStream getInputStream() {
        return inputStream;
    }

    /**
     * Sets the input stream.
     *
     * @param inputStream
     *            the new input stream
     */
    public void setInputStream(BufferedInputStream inputStream) {
        this.inputStream = inputStream;
    }
}
