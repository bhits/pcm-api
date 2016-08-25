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
package gov.samhsa.c2s.pcm.domain.audit;

import org.hibernate.envers.EntityTrackingRevisionListener;
import org.hibernate.envers.RevisionListener;
import org.hibernate.envers.RevisionType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.Serializable;
import java.util.Optional;

/**
 * The Class RevisionListenerImpl.
 */
public class RevisionListenerImpl implements RevisionListener,
		EntityTrackingRevisionListener {

	/** The modified entity type entity repository. */
	ModifiedEntityTypeEntityRepository modifiedEntityTypeEntityRepository;

	/**
	 * Instantiates a new revision listener impl.
	 */
	public RevisionListenerImpl() {
		super();
	}

	/**
	 * Instantiates a new revision listener impl.
	 *
	 * @param modifiedEntityTypeEntityRepository
	 *            the modified entity type entity repository
	 */
	public RevisionListenerImpl(
			ModifiedEntityTypeEntityRepository modifiedEntityTypeEntityRepository) {
		super();
		this.modifiedEntityTypeEntityRepository = modifiedEntityTypeEntityRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.hibernate.envers.RevisionListener#newRevision(java.lang.Object)
	 */
	@Override
	public void newRevision(Object revisionEntity) {
		Optional.ofNullable(SecurityContextHolder.getContext())
				.map(SecurityContext::getAuthentication)
				.map(Authentication::getName)
				.ifPresent(name -> ((RevisionInfoEntity) revisionEntity).setUsername(name));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.hibernate.envers.EntityTrackingRevisionListener#entityChanged(java
	 * .lang.Class, java.lang.String, java.io.Serializable,
	 * org.hibernate.envers.RevisionType, java.lang.Object)
	 */
	@Override
	public void entityChanged(@SuppressWarnings("rawtypes") Class entityClass,
			String entityName, Serializable entityId,
			RevisionType revisionType, Object revisionEntity) {
		String type = entityClass.getName();
		Byte rt = revisionType.getRepresentation();
		((RevisionInfoEntity) revisionEntity).addModifiedEntityType(type, rt,
				(RevisionInfoEntity) revisionEntity);

	}

}
