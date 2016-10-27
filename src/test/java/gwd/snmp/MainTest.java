/**
 * 
 */
package gwd.snmp;

import java.io.IOException;

import org.junit.Test;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import edu.gwd.snmp.SnmpMenu;

/**
 * @author gwd
 *
 */
public class MainTest {
	

	 public  void testSnmp4jMenu() {
	        //Snmp的三个版本号
	        //int ver3 = SnmpConstants.version3;
	        int ver2c = SnmpConstants.version2c;
	        //int ver1 = SnmpConstants.version1;
	        SnmpMenu manager = new SnmpMenu(ver2c);
	        // 构造报文
	        PDU pdu = new PDU();
	         //PDU pdu = new ScopedPDU();
	        // 设置要获取的对象ID，这个OID代表远程计算机的名称
	        OID oids = new OID("1.3.6.1.2.1.1.5.0");
	        pdu.add(new VariableBinding(oids));
	        // 设置报文类型
	        pdu.setType(PDU.GET);
	        //((ScopedPDU) pdu).setContextName(new OctetString("priv"));
	        try {
	            // 发送消息 其中最后一个是想要发送的目标地址
	            //manager.sendMessage(false, true, pdu, "udp:192.168.1.229/161");//192.168.1.229 Linux服务器
	            manager.sendMessage(false, true, pdu, "udp:127.0.0.1/161");//192.168.1.233 WinServer2008服务器
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	
	@Test
	public void testGetMib(){
		try{  
	        //设定CommunityTarget   
	        CommunityTarget myTarget = new CommunityTarget();  
	        Address deviceAdd = GenericAddress.parse("udp:localhost/161"); //定义远程主机的地址   
	        //Address deviceAdd=new IpAddress("udp:127.0.0.1/161");   
	        myTarget.setAddress(deviceAdd);  //设定远程主机的地址   
	        myTarget.setCommunity(new OctetString("public"));   //设置snmp共同体   
	        myTarget.setRetries(2);  //设置超时重试次数   
	        myTarget.setTimeout(5*60);   //设置超时的时间   
	        myTarget.setVersion(SnmpConstants.version2c);//设置snmp版本   
	         
	        //设定采取的协议   
	        TransportMapping transport = new DefaultUdpTransportMapping();  //设定传输协议为UDP   
	        transport.listen();  
	        Snmp protocol = new Snmp(transport);  
	         
	        //获取mib   
	        PDU request = new PDU();  
	         
	        request.add(new VariableBinding(new OID("1.3.6.1.2.1.1.1")));  
	        request.add(new VariableBinding(new OID(new int[] {1,3,6,1,2,1,1,2})));  
	        
	        request.setType(PDU.GETNEXT);  
	        ResponseEvent responseEvent = protocol.send(request, myTarget);  
	        PDU response=responseEvent.getResponse();  
	        //输出   
	        if(response != null){  
	            System.out.println("request.size()="+request.size());  
	            System.out.println("response.size()="+response.size());  
	            VariableBinding vb1 = response.get(0);  
	            VariableBinding vb2 = response.get(1);  
	            System.out.println(vb1);  
	            System.out.println(response.toString());  
	            System.out.println("##:"+getIpOfGateway());
	            transport.close();  
	        }  
	        
	      }catch(IOException e){  
	          e.printStackTrace();  
	          
	      }  
	}
	
	public String getIpOfGateway(){
        String gatewayIpString=null; //网关ip地址是这个字符串的子串
        String gatewayIp=null;  // 这是代表网关ip
        try {    
            CommunityTarget localhost = new CommunityTarget();
            Address address = GenericAddress.parse("udp:127.0.0.1/161");
            localhost.setAddress(address);
            localhost.setCommunity(new OctetString("public")); 
            localhost.setRetries(2);
            localhost.setTimeout(5*60);
            localhost.setVersion(SnmpConstants.version2c);
           
            TransportMapping transport = new DefaultUdpTransportMapping();
            transport.listen();
            Snmp protocol = new Snmp(transport);
           
            PDU requestPDU = new PDU();
            requestPDU.add(new VariableBinding(new OID("1.3.6.1.2.1.4.21.1.7")));//ipRouteNextHop
            requestPDU.setType(PDU.GETNEXT);
           
            ResponseEvent responseEvent = protocol.send(requestPDU, localhost);
            PDU responsePDU=responseEvent.getResponse();
            if(responsePDU!=null){
                VariableBinding getIp=responsePDU.get(0);
                gatewayIpString=getIp.toString();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        gatewayIp=gatewayIpString.substring(31);
        return gatewayIp;
      
    }
}
