package gov.samhsa.consent2share.infrastructure.security;

/**
 * The Interface AccessReferenceMapper.
 */
public interface AccessReferenceMapper {

	/**
	 * Gets the direct reference.
	 *
	 * @param indirectReference
	 *            the indirect reference
	 * @return the direct reference
	 */
	public long getDirectReference(String indirectReference);

	/**
	 * Sets the up access reference map.
	 *
	 * @param objects
	 *            the new up access reference map
	 */
	public void setupAccessReferenceMap(Iterable<? extends HasId> objects);

	/**
	 * Gets the indirect reference.
	 *
	 * @param directReference
	 *            the direct reference
	 * @return the indirect reference
	 */
	String getIndirectReference(String directReference);

}
