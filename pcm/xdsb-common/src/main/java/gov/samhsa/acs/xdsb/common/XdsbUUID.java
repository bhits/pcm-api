package gov.samhsa.acs.xdsb.common;

/**
 * The Interface XdsbUUID.
 */
public interface XdsbUUID {

	/**
	 * Gets the uuid.
	 *
	 * @return the uuid
	 */
	public default String getUUID() {
		return getXdsbUUIDValue().getUUID();
	}

	/**
	 * Gets the xdsb uuid value.
	 *
	 * @return the xdsb uuid value
	 */
	public abstract XdsbUUIDValue getXdsbUUIDValue();
}
