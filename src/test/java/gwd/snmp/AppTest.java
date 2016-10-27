package gwd.snmp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import edu.gwd.snmp.SnmpData;

/**
 * Unit test for simple App.
 */
public class AppTest {
    @Test
	public void testGet() throws IOException {
		String ip = "127.0.0.1";
		String community = "public";
		String oidval = "1.3.6.1.2.1.1.6.0";
		SnmpData.snmpGet(ip, community, oidval);
		SnmpData.createDefault(ip, community);
		SnmpData.setPDU(ip, community, oidval);
		SnmpData.snmpAsynWalk(ip, community, oidval);
		SnmpData.snmpWalk(ip, community, oidval);		
		SnmpData.snmpAsynWalk(ip, community, "1.3.6.1.2.1.25.4.2.1.2");
	}


	public void testGetList() {
		String ip = "127.0.0.1";
		String community = "public";
		List<String> oidList = new ArrayList<String>();
		oidList.add("1.3.6.1.2.1.1.5.0");
		oidList.add("1.3.6.1.2.1.1.7.0");
		SnmpData.snmpGetList(ip, community, oidList);
	}

	public void testGetAsyList() {
		String ip = "127.0.0.1";
		String community = "public";
		List<String> oidList = new ArrayList<String>();
		oidList.add("1.3.6.1.2.1.1.5.0");
		oidList.add("1.3.6.1.2.1.1.7.0");
		SnmpData.snmpAsynGetList(ip, community, oidList);
	}

	public void testWalk() {
		String ip = "127.0.0.1";
		String community = "public";
		String targetOid = "1.3.6.1.2.1.25.4.2.1.2";
		SnmpData.snmpWalk(ip, community, targetOid);
	}

	public void testAsyWalk() {
		String ip = "127.0.0.1";
		String community = "public";
		// 异步采集数据
		SnmpData.snmpAsynWalk(ip, community, "1.3.6.1.2.1.25.4.2.1.2");
	}

	public void testSetPDU() throws Exception {
		String ip = "127.0.0.1";
		String community = "public";
		SnmpData.setPDU(ip, community, "1.3.6.1.2.1.1.6.0");
	}
}
