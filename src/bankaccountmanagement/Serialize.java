package bankaccountmanagement;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import newexception.MistakeOccuredException;
import util.HelperUtil;
public class Serialize
{
	
	public static File createFile(String path,String fileName)throws MistakeOccuredException
	{
		HelperUtil.stringCheck(path);
		HelperUtil.stringCheck(fileName);
		File testFile=new File(path,fileName);
		try
		{
		if(!testFile.exists())
		{
			testFile.createNewFile();
		}
		return testFile;
		}
		catch(IOException e)
		{
			throw new MistakeOccuredException(e);
		}
	}
	
	public static void writeObject(String path,String name,Object obj)throws MistakeOccuredException
	{
		try(FileOutputStream fos=new FileOutputStream(path+File.separator+name);
				ObjectOutputStream oos=new ObjectOutputStream(fos))
		{
			oos.writeObject(obj);
		}
		catch(IOException ex)
		{
			throw new MistakeOccuredException(ex);
		}
	}
	
	public static void writeCustomer(String path,String name,Map<Integer,Customer> obj)throws MistakeOccuredException
	{
		try(FileOutputStream fos=new FileOutputStream(path+File.separator+name);
				ObjectOutputStream oos=new ObjectOutputStream(fos))
		{
			oos.writeObject(obj);
		}
		catch(IOException ex)
		{
			throw new MistakeOccuredException(ex);
		}
	}
	
	public static void writeAccount(String path,String name,Map<Integer,Map<Long,AccountDetails>> obj)throws MistakeOccuredException
	{
		try(FileOutputStream fos=new FileOutputStream(path+File.separator+name);
				ObjectOutputStream oos=new ObjectOutputStream(fos))
		{
			oos.writeObject(obj);
		}
		catch(IOException ex)
		{
			throw new MistakeOccuredException(ex);
		}
	}
	
	public static void writeAccountNumber(String path,String name,Map<String,Long> obj)throws MistakeOccuredException
	{
		try(FileOutputStream fos=new FileOutputStream(path+File.separator+name);
				ObjectOutputStream oos=new ObjectOutputStream(fos))
		{
			oos.writeObject(obj);
		}
		catch(IOException ex)
		{
			throw new MistakeOccuredException(ex);
		}
	}
	
	public static void writeObjects(String path,String name,Map<Integer,Customer> customerMap,
			Map<Integer,Map<Long,AccountDetails>> accountsMap,Map<String,Long> accountNumber) throws MistakeOccuredException
	{
		try(FileOutputStream fos=new FileOutputStream(path+File.separator+name);
				ObjectOutputStream oos=new ObjectOutputStream(fos))
		{
			oos.writeObject(customerMap);
			oos.writeObject(accountsMap);
			oos.writeObject(accountNumber);
		}
		catch(IOException ex)
		{
			throw new MistakeOccuredException(ex);
		}
	}
	

	public static Map<Integer,Customer> readCustomer(String path,String name) throws MistakeOccuredException
	{
		try(FileInputStream fis=new FileInputStream(path+File.separator+name);
		ObjectInputStream ois=new ObjectInputStream(fis))
		{
			
			Map<Integer,Customer> resultant=(Map) ois.readObject();
			return resultant;
		}
		catch(IOException ex)
		{
			throw new MistakeOccuredException(ex);
		} catch (ClassNotFoundException e) {
			
			throw new MistakeOccuredException(e);
		}
	}
	
	public static Map<Integer,Map<Long,AccountDetails>> readAccount(String path,String name) throws MistakeOccuredException
	{
		try(FileInputStream fis=new FileInputStream(path+File.separator+name);
		ObjectInputStream ois=new ObjectInputStream(fis))
		{
	        Map<Integer, Map<Long,AccountDetails>> resultant=(Map)ois.readObject();
	        return resultant;
		}
		catch(IOException ex)
		{
			throw new MistakeOccuredException(ex);
		} catch (ClassNotFoundException e) {
			
			throw new MistakeOccuredException(e);
		}
	}
	
	public static Map<String,Long> readAccountNumber(String path,String name) throws MistakeOccuredException
	{
		try(FileInputStream fis=new FileInputStream(path+File.separator+name);
		ObjectInputStream ois=new ObjectInputStream(fis))
		{
	        Map<String, Long> resultant=(Map)ois.readObject();
	        return resultant;
		}
		catch(IOException ex)
		{
			throw new MistakeOccuredException(ex);
		} catch (ClassNotFoundException e) {
			
			throw new MistakeOccuredException(e);
		}
	}
	
}
