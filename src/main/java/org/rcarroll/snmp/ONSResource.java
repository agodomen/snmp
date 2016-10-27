package org.rcarroll.snmp;

/**
 * @author rcarroll
 * @version 1.0
 * @created 14-Feb-2013 15:41:52
 */
public class ONSResource {

	private int resourceID;
	private int resourceType;
	private int powerState;
	public Facility m_Facility;
	public PowerMonitorLog m_PowerMonitorLog;
	public ONSResource m_ONS_Resource;

	public ONSResource(){

	}

	public void finalize() throws Throwable {

	}

}