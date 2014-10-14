package edu.cmu.sv.ws.ssnoc.common.utils;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.nio.file.FileStore;
import java.nio.file.FileSystemException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;

import com.sun.management.OperatingSystemMXBean;

import edu.cmu.sv.ws.ssnoc.common.logging.Log;
import edu.cmu.sv.ws.ssnoc.data.dao.DAOFactory;
import edu.cmu.sv.ws.ssnoc.data.dao.IMemoryCrumbDAO;
import edu.cmu.sv.ws.ssnoc.data.po.MemoryCrumbPO;


/**
 * This is a utility class to perform memory and performance measurements in a different thread
 * 
 */
public class MeasurementUtils {
	public static long samplingFrequency;
	public static long reportingPeriod;
	
	public static void measure() 
	{
		NumberFormat nf = NumberFormat.getNumberInstance();
		
//		//Memory measurements - this is only JVM info
//		Runtime runtime = Runtime.getRuntime();
//
//	    long maxMemory = runtime.maxMemory();
//	    long allocatedMemory = runtime.totalMemory();
//	    long freeMemory = runtime.freeMemory();
//	    
//	    long usedVolatileMemory =  allocatedMemory - freeMemory;
//	    long remainingVolatileMemory = maxMemory - usedVolatileMemory;
	    
		//Memory Measurements - this is for whole system
		OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory
	            .getOperatingSystemMXBean();
		long maxMemory = bean.getTotalPhysicalMemorySize();
	    long freeMemory = bean.getFreePhysicalMemorySize();
	    
	    long usedVolatileMemory =  (maxMemory - freeMemory) / 1024;
	    long remainingVolatileMemory = freeMemory / 1024;
	    
	    //Disk space measurements
	    long usedPersistentMemory = 0;
	    long remainingPersistentMemory = 0;
	    for (Path root : FileSystems.getDefault().getRootDirectories())
	    {
	        System.out.print(root + ": ");

	        try
	        {
	            FileStore store = Files.getFileStore(root);
	            long totalSpace = store.getTotalSpace();
	            long freeSpace = store.getUnallocatedSpace();
	            		
	            usedPersistentMemory = (totalSpace - freeSpace) / 1024;
	            remainingPersistentMemory = freeSpace / 1024;
	        }
	        catch (FileSystemException e)
	        {
	            System.out.println("error querying space: " + e.toString());
	        } catch (IOException e) {
	        	System.out.println("error querying space: " + e.toString());
			}
	    }
	    
	    //Printouts
	    StringBuilder sb = new StringBuilder();
	    sb.append("timestamp: " + System.currentTimeMillis() + "\n");
	    sb.append("used volatile memory: " + nf.format(usedVolatileMemory) + "\n");
	    sb.append("remaining volatile memory: " + nf.format(remainingVolatileMemory) + "\n");
	    sb.append("used persistent memory: " + nf.format(usedPersistentMemory) + "\n");
	    sb.append("remaining persistent memory: " + nf.format(remainingPersistentMemory) + "\n");
	    System.out.println(sb.toString());
	    
	    //Save the memory measurements to database
	    try {
			IMemoryCrumbDAO dao = DAOFactory.getInstance().getMemoryCrumbDAO();
			MemoryCrumbPO po = new MemoryCrumbPO();
			po.setUsedVolatileMemory(usedVolatileMemory);
			po.setRemainingVolatileMemory(remainingVolatileMemory);
			po.setUsedPersistentMemory(usedPersistentMemory);
			po.setRemainingPersistentMemory(remainingPersistentMemory);
			po.setOnlineUsers(0);	
			dao.save(po);
		} catch (Exception e) {
			Log.error(e);
		} finally {
			Log.exit();
		}
	}
	
}
