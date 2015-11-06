package gov.samhsa.pcm.infrastructure.security;

import javax.servlet.http.HttpSession;

import org.owasp.esapi.AccessReferenceMap;
import org.owasp.esapi.errors.AccessControlException;
import org.owasp.esapi.reference.RandomAccessReferenceMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * The Class AccessReferenceMapperImpl.
 */
public class AccessReferenceMapperImpl implements AccessReferenceMapper {

	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The Constant MAP_NAME. */
	final static String MAP_NAME = "AccessReferenceMap";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.infrastructure.security.AccessReferenceMapper
	 * #getDirectReference(java.lang.String)
	 */
	@Override
	public long getDirectReference(String indirectReference) {
		HttpSession session = getSession();
		AccessReferenceMap<String> map = getMap();
		Long directReference = null;
		try {
			directReference = Long.parseLong((String) map
					.getDirectReference(indirectReference));
		} catch (AccessControlException e1) {
			logger.error(e1.getLogMessage());
		}
		return directReference;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.infrastructure.security.AccessReferenceMapper
	 * #getIndirectReference(long)
	 */
	@Override
	public String getIndirectReference(String directReference) {
		HttpSession session = getSession();
		AccessReferenceMap<String> map = getMap();
		String indirectReference = map.addDirectReference(directReference);
		session.setAttribute("AccessReferenceMap", map);
		return indirectReference;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * gov.samhsa.consent2share.infrastructure.security.AccessReferenceMapper
	 * #setupAccessReferenceMap(java.lang.Iterable)
	 */
	@Override
	public void setupAccessReferenceMap(Iterable<? extends HasId> objects) {
		HttpSession session = getSession();
		AccessReferenceMap<String> map = getMap();
		for (HasId o : objects) {
			String indirectReference = map.addDirectReference(o.getId());
			o.setId(indirectReference);
		}
		session.setAttribute("AccessReferenceMap", map);
	}

	/**
	 * Gets the session.
	 *
	 * @return the session
	 */
	HttpSession getSession() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession();
		return session;
	}

	/**
	 * Gets the map.
	 *
	 * @return the map
	 */
	AccessReferenceMap<String> getMap() {
		AccessReferenceMap<String> map = (AccessReferenceMap<String>) getSession()
				.getAttribute(MAP_NAME);
		if (map == null)
			map = new RandomAccessReferenceMap();
		return map;
	}
}
