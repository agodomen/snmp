/**
 * 
 */
package edu.gwd.snmp.base;
import java.io.BufferedReader;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import java.io.FileReader;  
import java.io.IOException;  
import java.util.HashMap;  
import java.util.Hashtable;  
import java.util.Map;  
import java.util.Properties;  
  
public class Config {  
    Properties properties;  
    Map map;  
  
    public Config() {  
        properties = new Properties();  
  
        try {  
            properties.load(new FileInputStream("mib.properties"));  
        } catch (IOException e) {  
            System.out.println("读取properties文件错误");  
            e.printStackTrace();  
        }  
    }  
  
    /** 
     * 根据oid获取value 
     *  
     * @param oid 
     * @return 
     */  
    public String getValueByOID(String oid) {  
  
        return properties.getProperty(oid);  
  
    }  
  
    public void setValueByOID(String oid, String value) {  
          
        properties.setProperty(oid, value);  
        try {  
            properties.store(new FileOutputStream("mib.properties"),"mib.properties");  
        } catch (FileNotFoundException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }   
      
  
    }  
  
    //测试主函数  
       public static void main(String[] args) {  
           Config cfg=new Config();  
           String oid="1.3.6.1.2.1.1.8.0";  
           System.out.println("---------"+cfg.getValueByOID(oid)); 
           cfg.setValueByOID(oid, "test"); 
           System.out.println("---------"+cfg.getValueByOID(oid));  
       }  
}  