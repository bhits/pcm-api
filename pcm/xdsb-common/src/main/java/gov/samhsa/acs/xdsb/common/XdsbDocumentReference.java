/*******************************************************************************
 * Open Behavioral Health Information Technology Architecture (OBHITA.org)
 *
 *   Redistribution and use in source and binary forms, with or without
 *   modification, are permitted provided that the following conditions are met:
 *       * Redistributions of source code must retain the above copyright
 *         notice, this list of conditions and the following disclaimer.
 *       * Redistributions in binary form must reproduce the above copyright
 *         notice, this list of conditions and the following disclaimer in the
 *         documentation and/or other materials provided with the distribution.
 *       * Neither the name of the <organization> nor the
 *         names of its contributors may be used to endorse or promote products
 *         derived from this software without specific prior written permission.
 *
 *   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 *   ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 *   WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 *   DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> BE LIABLE FOR ANY
 *   DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 *   (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 *   LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 *   ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *   (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package gov.samhsa.acs.xdsb.common;

import ihe.iti.xds_b._2007.RetrieveDocumentSetRequest.DocumentRequest;

/**
 * The Class XdsbDocumentReference.
 */
public class XdsbDocumentReference extends DocumentRequest {

	/**
	 * Instantiates a new xdsb document reference.
	 */
	public XdsbDocumentReference() {
		super();
	}

	/**
	 * Instantiates a new xdsb document reference.
	 *
	 * @param documentUniqueId
	 *            the document unique id
	 * @param repositoryUniqueId
	 *            the repository unique id
	 */
	public XdsbDocumentReference(String documentUniqueId,
			String repositoryUniqueId) {
		super();
		super.setDocumentUniqueId(documentUniqueId);
		super.setRepositoryUniqueId(repositoryUniqueId);
	}

	/**
	 * Equals.
	 *
	 * @param anObject
	 *            the an object
	 * @return true if the passed XdsbDocumentReference's documentUniqueId and
	 *         repositoryId is same with this XdsbDocumentReference
	 */
	@Override
	public boolean equals(Object anObject) {
		boolean equals = false;
		if (anObject instanceof XdsbDocumentReference) {
			final XdsbDocumentReference xdsbDocumentReference = (XdsbDocumentReference) anObject;
			equals = documentUniqueId.equals(xdsbDocumentReference
					.getDocumentUniqueId())
					&& repositoryUniqueId.equals(xdsbDocumentReference
							.getRepositoryUniqueId());
		}
		return equals;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	/**
	 * To string.
	 *
	 * @return the String representation of XdsbDocumentReference in
	 *         'repositoryId:documentUniqueId' format
	 */
	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append(repositoryUniqueId);
		builder.append(":");
		builder.append(documentUniqueId);
		return builder.toString();
	}

	/**
	 * From.
	 *
	 * @param documentRequest
	 *            the document request
	 * @return the xdsb document reference
	 */
	public static final XdsbDocumentReference from(
			DocumentRequest documentRequest) {
		final XdsbDocumentReference xdsbDocumentReference = new XdsbDocumentReference();
		xdsbDocumentReference.setDocumentUniqueId(documentRequest
				.getDocumentUniqueId());
		xdsbDocumentReference.setHomeCommunityId(documentRequest
				.getHomeCommunityId());
		xdsbDocumentReference.setRepositoryUniqueId(documentRequest
				.getRepositoryUniqueId());
		return xdsbDocumentReference;
	}
}
