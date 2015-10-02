/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the <organization> nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 * 
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
package gov.samhsa.consent2share.domain.educationmaterial;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * The Class EducationMaterial.
 */
@Entity
public class EducationMaterial {

    /** The name. */
    @NotNull
    @Size(max = 30)
    private String name;

    /** The description. */
    @Size(max = 500)
    private String description;

    /** The filename. */
    @NotNull
    private String filename;
    
    //TODO Type byte[] not supported by Roo. Replace content field with the following
    /*
        @NotNull
        @Lob
        @Basic(fetch = FetchType.LAZY)
        private byte[] content;
    */

    /** The content. */
    @NotNull
    @Size(max = 250)
    private String content;

    /** The content type. */
    @NotNull
    private String contentType;

    /** The document size. */
    @NotNull
    private Long documentSize;

    /** The document url. */
    @Size(max = 250)
    private String documentUrl;

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
        return this.name;
    }

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
        this.name = name;
    }

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
        return this.description;
    }

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
        this.description = description;
    }

	/**
	 * Gets the filename.
	 *
	 * @return the filename
	 */
	public String getFilename() {
        return this.filename;
    }

	/**
	 * Sets the filename.
	 *
	 * @param filename the new filename
	 */
	public void setFilename(String filename) {
        this.filename = filename;
    }

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent() {
        return this.content;
    }

	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent(String content) {
        this.content = content;
    }

	/**
	 * Gets the content type.
	 *
	 * @return the content type
	 */
	public String getContentType() {
        return this.contentType;
    }

	/**
	 * Sets the content type.
	 *
	 * @param contentType the new content type
	 */
	public void setContentType(String contentType) {
        this.contentType = contentType;
    }

	/**
	 * Gets the document size.
	 *
	 * @return the document size
	 */
	public Long getDocumentSize() {
        return this.documentSize;
    }

	/**
	 * Sets the document size.
	 *
	 * @param documentSize the new document size
	 */
	public void setDocumentSize(Long documentSize) {
        this.documentSize = documentSize;
    }

	/**
	 * Gets the document url.
	 *
	 * @return the document url
	 */
	public String getDocumentUrl() {
        return this.documentUrl;
    }

	/**
	 * Sets the document url.
	 *
	 * @param documentUrl the new document url
	 */
	public void setDocumentUrl(String documentUrl) {
        this.documentUrl = documentUrl;
    }

	/** The id. */
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

	/** The version. */
	@Version
    @Column(name = "version")
    private Integer version;

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
        return this.id;
    }

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(Long id) {
        this.id = id;
    }

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	public Integer getVersion() {
        return this.version;
    }

	/**
	 * Sets the version.
	 *
	 * @param version the new version
	 */
	public void setVersion(Integer version) {
        this.version = version;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	/**
	 * To json.
	 *
	 * @return the string
	 */
	public String toJson() {
        return new JSONSerializer().exclude("*.class").deepSerialize(this);
    }

	/**
	 * From json to education material.
	 *
	 * @param json the json
	 * @return the education material
	 */
	public static EducationMaterial fromJsonToEducationMaterial(String json) {
        return new JSONDeserializer<EducationMaterial>().use(null, EducationMaterial.class).deserialize(json);
    }

	/**
	 * To json array.
	 *
	 * @param collection the collection
	 * @return the string
	 */
	public static String toJsonArray(Collection<EducationMaterial> collection) {
        return new JSONSerializer().exclude("*.class").deepSerialize(collection);
    }

	/**
	 * From json array to education materials.
	 *
	 * @param json the json
	 * @return the collection
	 */
	public static Collection<EducationMaterial> fromJsonArrayToEducationMaterials(String json) {
        return new JSONDeserializer<List<EducationMaterial>>().use(null, ArrayList.class).use("values", EducationMaterial.class).deserialize(json);
    }
}
