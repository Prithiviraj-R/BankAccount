package bankaccountmanagement;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


import newexception.MistakeOccuredException;
import util.HelperUtil;

public class ApiLayerSerialization {

	// Constructor which is used to read the Files.
	public ApiLayerSerialization() throws MistakeOccuredException 
	{
		CustomerDetailsFile("/home/inc4","CustomerDetails");
		
		AccountDetailsFile("/home/inc4","AccountDetails");
		
		AccountNumberFile("/home/inc4","AccountNumber");
		
		Map<Integer, Customer> map = Serialize.readCustomer("/home/inc4", "CustomerDetails");
		customerMap.putAll(map);
		
		Map<Integer, Map<Long, AccountDetails>> map2 = Serialize.readAccount("/home/inc4", "AccountDetails");
		accountInfo.putAll(map2);
		
		Map<String,Long> map3=Serialize.readAccountNumber("/home/inc4","AccountNumber");
    	accountNumber.putAll(map3);
    	
		setCusId();
		
		setAccNo();
	}

	
	// Map used to store the Customer and Account details.
	Map<Integer, Customer> customerMap = new HashMap<>();

	Map<Integer, Map<Long, AccountDetails>> accountInfo = new HashMap<>();

	Map<String,Long> accountNumber=new HashMap<>();
	
	
	// Object for Customer PojoClass
	Customer cusInfo = new Customer();

	
	// Object for Account PojoClass
	AccountDetails accInfo = new AccountDetails();

	
	// Auto generated accNo and Customer Id are after this below numbers.

	int cusId = 0;
	long accNo = 0;
    long number=0;

  
    //Method to get CustomerId from customer Map
	  public void setCusId()
	  {
		   Set keys=customerMap.keySet();
		   cusId=(int) Collections.max(keys);
	  }
	
	  
	  //Method to get AccountNumber from Account map
	  public void setAccNo()
	  {
		   long keys=accountNumber.get("AccountNumber");
		   accNo=keys;
	  }
	  
	
	  public void setAccountnumber()
	  {
		accNo=number;
	  }
	
	
	// method to auto generate Customer ID
	public int generateCusId()
	{
		return ++cusId;
	}

	
	// method to auto generate AccountNumber
	public long generateAccNo() 
	{
		return ++accNo;
	}

	
	
	public static void CustomerDetailsFile(String path,String name) throws MistakeOccuredException
    {
    	File file=new File(path+File.separator+name);
    	if(!file.exists())
    	{
    	try
    	{
			file.createNewFile();
    		Customer dummyObj=new Customer();
			dummyObj.setName("Lobe");
			dummyObj.setDob("25");
			dummyObj.setAddress("Sungagte");
			dummyObj.setCustomerId(0);
			dummyObj.setPhoneNumber(987643123l);
			Map dummyMap=new HashMap();
			dummyMap.put(dummyObj.getCustomerId(), dummyObj);
			Serialize.writeObject(path,name, dummyMap);
   		}
    	 catch (IOException e)
		{
			throw new MistakeOccuredException(e);
		}
    	}	
    }
    
    
	
	public static void AccountDetailsFile(String path,String name) throws MistakeOccuredException
    {
    	File file=new File(path+File.separator+name);
    	if(!file.exists())
    	{
    	try
    	{
			file.createNewFile();
			AccountDetails dummyObj2=new AccountDetails();
			dummyObj2.setBranch("France");
			dummyObj2.setBalance(0);
			dummyObj2.setAccountNumber(2010l);
			dummyObj2.setCustomerId(0);
			Map dummyMap1=new HashMap();
			dummyMap1.put(dummyObj2.getAccountNumber(), dummyObj2);
			Map dummyMap2=new HashMap();
			dummyMap2.put(dummyObj2.getCustomerId(), dummyMap1);
			Serialize.writeObject(path,name, dummyMap2);
   		}
    	 catch (IOException e)
		{
			throw new MistakeOccuredException(e);
		}
    	}	
    }
    
    
	
	public static void AccountNumberFile(String path,String name) throws MistakeOccuredException
    {
    	File file=new File(path+File.separator+name);
    	if(!file.exists())
    	{
    	try
    	{
			file.createNewFile();
			Map dummyMap=new HashMap();
			dummyMap.put("AccountNumber", 2010l);
			Serialize.writeObject(path,name,dummyMap );
   		}
    	 catch (IOException e)
		{
			throw new MistakeOccuredException(e);
		}
    	}	
    }
	
    
    
    // method to give access to access active Account Only
	public void accountAccess(AccountDetails obj) throws MistakeOccuredException 
	{
		if (obj.isStatus() == false) 
		{
			throw new MistakeOccuredException("This Account is deactivated please Contact branch.");
		}
	}

	
	// method to check customer id's value is exist or not Exist in customer map
	public void idCheck(int id) throws MistakeOccuredException
	{
		if (customerMap.get(id) == null) {
			throw new MistakeOccuredException("Entered Key is not exist.");
		}
	}

	
	// Method to create Customer
	public Map<Integer, Customer> addCustomerInfo(String path, String name, Customer cusObj)
			throws MistakeOccuredException {
		HelperUtil.objectCheck(cusObj);
		
		int cusId = cusObj.getCustomerId();

		customerMap.put(cusId, cusObj);
		
		Serialize.writeObject(path, name, customerMap);
		
		return customerMap;
	}

	// Method to add Account in customer Id.
	public Map<Integer, Map<Long, AccountDetails>> addAccountToCusId(String path, String name, int cusId,
			AccountDetails accObj) throws MistakeOccuredException {
		
		HelperUtil.numberCheck(cusId);
		
		HelperUtil.objectCheck(accObj);
		
		idCheck(cusId);
		
		Map<Long, AccountDetails> getExisting = accountInfo.get(cusId);
		
		if (getExisting == null)
		{
			getExisting = new HashMap<>();
			
			accountInfo.put(cusId, getExisting);
		}
		
		getExisting.put(accObj.getAccountNumber(), accObj);
		
		accountNumber.put("AccountNumber", accObj.getAccountNumber());
		
		Serialize.writeObject("/home/inc4","AccountNumber",accountNumber);
		
		Serialize.writeObject(path,name, accountInfo);
		
		return accountInfo;
	}


	// method to get customer details
	public Object getCustomerInfo(int id) throws MistakeOccuredException 
	{
		
		HelperUtil.numberCheck(id);
		
		return customerMap.get(id);
	}

	// method to get account details belongs to customer id.
	public AccountDetails getAccountInfo(int cusId, long accNo)
			throws MistakeOccuredException 
	{
		HelperUtil.numberCheck(cusId);
		
		Map<Long, AccountDetails> map2 = accountInfo.get(cusId);
		
		HelperUtil.objectCheck(map2, "CustomerId");
		
		AccountDetails accDetails = map2.get(accNo);
		
		HelperUtil.objectCheck(accDetails, "AccountDetails");
		
		return accDetails;
	}

	// method to get status of the Account.
	public boolean getStatusOfAccount(int cusId, long accNo) 
			throws MistakeOccuredException 
	{
		HelperUtil.numberCheck(cusId);
		
		Map<Long, AccountDetails> map2 = accountInfo.get(cusId);
		
		HelperUtil.objectCheck(map2, "CustomerId");
		
		AccountDetails accDetails = map2.get(accNo);
		
		HelperUtil.objectCheck(accDetails, "AccountDetails");
		
		return accDetails.isStatus();
	}

	// method to set status of the Account.
	public void setStatusOfAccount(String path,String name,int cusId, long accNo, int status) 
			throws MistakeOccuredException {
		
		HelperUtil.numberCheck(cusId);
		
		HelperUtil.numberCheck(status);
		
		Map<Long, AccountDetails> map2 = accountInfo.get(cusId);
		
		HelperUtil.objectCheck(map2, "CustomerId");
		
		AccountDetails accDetails = map2.get(accNo);
		
		HelperUtil.objectCheck(accDetails, "AccountDetails");
		
		if (status == 1)
		{
			accDetails.setStatus(true);
			
			Serialize.writeObject("/home/inc4", "AccountDetails", accountInfo);
			
			System.out.println(accDetails);
		}
		else if (status == 0) 
		{
			accDetails.setStatus(false);
			
			Serialize.writeObject("/home/inc4", "AccountDetails", accountInfo);
			
			System.out.println(accDetails);
		}
		else
		{
			throw new MistakeOccuredException("Enter The Corresponding numbers only.");
		}
	}

	// method to set status of Customer.
	public void setStatusOfCustomer(String path,String name,int cusId, int status) 
			throws MistakeOccuredException 
	{
		
		Customer customerDetails = customerMap.get(cusId);
		
		HelperUtil.objectCheck(customerDetails, "CustomerDetails");
		
		if (status == 1) {
			customerDetails.setStatus(true);
			Serialize.writeObject(path,name, customerMap);
			System.out.println(customerDetails);
		}
		else if (status == 0) {
			customerDetails.setStatus(false);
			Serialize.writeObject(path,name, customerMap);
			System.out.println(customerDetails);
		} 
		else {
			throw new MistakeOccuredException("Enter The Corresponding numbers only.");
		}
	}

	// method to get status of customer.
	public boolean getStatusOfCustomer(int cusId) throws MistakeOccuredException 
	{
		Customer customerDetails = customerMap.get(cusId);
		
		HelperUtil.objectCheck(customerDetails, "CustomerDetails");
		
		return customerDetails.isStatus();
	}

	// method to calculate the balance after deposit
	public double balanceAfterDeposit(String path,String name,int cusId, long accNo, double depositAmount) 
			throws MistakeOccuredException 
	{
		Map<Long, AccountDetails> accDetailsMap = accountInfo.get(cusId);
		
		HelperUtil.objectCheck(accDetailsMap, "Customer account");
		
		AccountDetails accInfo = accDetailsMap.get(accNo);
		
		HelperUtil.objectCheck(accInfo, "AccountDetails");
		
		accountAccess(accInfo);
		
		double balance = accInfo.getBalance();
		
		double newBalance = balance + depositAmount;
		
		accInfo.setBalance(newBalance);
		
		Serialize.writeObject(path, name, accountInfo);
		
		return accInfo.getBalance();
	}

	public double balanceAfterWithDraw(String path,String name,int cusId, long accNo, double withDrawAmount)
			throws MistakeOccuredException 
	{
		
		Map<Long, AccountDetails> accDetailsMap = accountInfo.get(cusId);
		
		HelperUtil.objectCheck(accDetailsMap, "CustomerId");
		
		AccountDetails accInfo = accDetailsMap.get(accNo);
		
		HelperUtil.objectCheck(accInfo, "AccountDetails");
		
		accountAccess(accInfo);
		
		double balance = accInfo.getBalance();
		
		if (balance < withDrawAmount) {
		
			throw new MistakeOccuredException("Insufficient balance.");
		}
		
		double newBalance = balance - withDrawAmount;
		
		accInfo.setBalance(newBalance);
		
		Serialize.writeObject(path, name, accountInfo);
		
		return accInfo.getBalance();
	}

}
