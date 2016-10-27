package org.rcarroll.snmp.impl;

import org.rcarroll.snmp.Energy;
import org.rcarroll.snmp.ONSResource;
import org.rcarroll.snmp.PowerMonitorLog;
import org.rcarroll.snmp.energyClass;
import org.rcarroll.snmp.energyType;
import org.snmp4j.smi.OID;

import edu.gwd.snmp.SNMPManager;
import edu.gwd.snmp.base.APCDriverSNMP;

import java.io.IOException;
import java.util.Date;
/**
 * @author rcarroll
 * @version 1.0
 * @created 14-Feb-2013 15:41:56
 */
public class PowerDeliveryResource extends ONSResource {

	private PowerSupplyResource primaryPowerSupply;
	private DeliveryRatedLoad deliveryRatedLoad;
	
	private APCDriverSNMP driver;
	private String myIP;
	
	// PowerSupply data, statically set here but should be read dynamically in reality
	
	private energyClass energyclass = energyClass.Green;
	private energyType energytype = energyType.Wind;
	
	private double pricePerKW = 0.11;
	private Energy psEnergy = new Energy(energyclass, energytype, pricePerKW);
	
	 
	public PowerDeliveryResource(String SNMPIP){
		myIP = SNMPIP;
		primaryPowerSupply = new PowerSupplyResource(psEnergy, pricePerKW);
	
		
		try{
			driver = new APCDriverSNMP(myIP);
		}catch(IOException ioe){
			System.out.println("Error from APC Driver:"+ioe.getMessage());
		}
		
		 
	}

	public String getDeviceName(){
		String name;
		
		try{
			name=driver.getDeviceName();
			
		}catch(IOException ioe){
			System.out.println("Error from APC Driver:"+ioe.getMessage());
			return null;
		}
		
		return name;
	}
	
	public MeasuredLoad getCurrentPowerMetrics(int targetOutletIndex){
		
		Double outletCurrent;
		Double outletVoltage;
		Double outletPower;
		
		MeasuredLoad ml;
		Date currentTime;
		
		try{
			outletCurrent = driver.getCurrentCurrent(targetOutletIndex);
			outletVoltage = driver.getCurrentVoltage(targetOutletIndex);
			outletPower = driver.getCurrentPower(targetOutletIndex);
			
		}catch(IOException ioe){
			System.out.println("Error from APC Driver:"+ioe.getMessage());
			return null;
		}
		
		ml = new MeasuredLoad();
		ml.setCurrent(outletCurrent);
		ml.setPower(outletPower);
		ml.setVoltage(outletVoltage);
		
		currentTime = new Date();
		ml.setReadingTime(currentTime);
		
		return ml;
	}
	
	public boolean powerOnPort(int targetOutletIndex){
		try{
		
			return driver.powerOnPort(targetOutletIndex);
		
		}catch(IOException ioe){
			System.out.println("Error from APC Driver:"+ioe.getMessage());
			return false;
		}

			
	}
	
	public boolean powerOffPort(int targetOutletIndex){
		try{
			return driver.powerOffPort(targetOutletIndex);
		}catch(IOException ioe){
			System.out.println("Error from APC Driver:"+ioe.getMessage());
			return false;
		}
	}
	
	public boolean getPortPowerStatus(int targetOutletIndex){
		try{
			return driver.getOutletStatus(targetOutletIndex);
		}catch(IOException ioe){
			System.out.println("Error from APC Driver:"+ioe.getMessage());
			return false;
		}

		
	}
	
	public String getEnergyType(){
		return primaryPowerSupply.getEnergy().getEnergyType().toString();
	}
	
	public String getEnergyClass(){
		return primaryPowerSupply.getEnergy().getEnergyClass().toString();
	}
	
	
	public double getCO2perKW(){
		return primaryPowerSupply.getEnergy().getCO2perKw();
	}
	
	public void finalize() throws Throwable {
		super.finalize();
	}

}