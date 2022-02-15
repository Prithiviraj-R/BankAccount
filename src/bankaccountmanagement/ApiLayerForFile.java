package bankaccountmanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import newexception.MistakeOccuredException;
import util.HelperUtil;

public class ApiLayerForFile
{
//	public static void main(String[] args)
//	{
//		ApiLayerForFile obj=new ApiLayerForFile();
//		Scanner input=new Scanner(System.in);
//		boolean condition=true;
//		while(condition)
//		{
//			int choice=Integer.parseInt(input.nextLine());
//		switch(choice)
//		{
//		case 0:
//			condition=false;
//			break;
//		case 1:
//		Customer cusObj=new Customer();
//		cusObj.setName("Prithivi");
//		cusObj.setDob("18101999");
//		cusObj.setAddress("Sungagte");
//		cusObj.setPhoneNumber(587964231l);
//		
//		cusObj.setCustomerId(obj.generateCusId());
//		try {
//			System.out.println(obj.addCustomerInfo("/home/inc4","CustomerDetails",cusObj));
//		} catch (MistakeOccuredException e) {
//			e.printStackTrace();
//		}
//		break;
//		case 2:
//			int id=1;
//			try
//			{
//				System.out.println(obj.getCustomerInfo("/home/inc4","CustomerDetails", id));
//			
//			}
//			catch(IOException e)
//			{
//				System.out.println(e.getMessage());
//			}
//			catch(MistakeOccuredException ex)
//			{
//				System.out.println(ex.getMessage());
//			}
//			break;
//		case 3:
//			int id1=1;
//			try
//			{
//				System.out.println(obj.getStatusOfCustomer("/home/inc4","CustomerDetails", id1));
//			}
//			catch(MistakeOccuredException ex)
//			{
//				System.out.println(ex.getMessage());
//			}
//			break;
//		}
//		}
//	}
	//Map used to store the Customer and Account details.
	  Properties customerMap=new Properties();
	  Properties accountInfo=new Properties();

	  
	  //Object for Customer PojoClass
	  Customer cusInfo=new Customer();
	  //Object for Account PojoClass
	  AccountDetails accInfo = new AccountDetails();
	  
	  
	  //Auto generated accNo and Customer Id are after this below numbers.
	  int cusId=0+cusInfo.getCustomerId();
	  long accNo=200018488;
	  
	  
	  //method to auto generate Customer ID
	  public int generateCusId()
	  {
		  return ++cusId;
	  }
	  
	  
	  //method to auto generate AccountNumber
	  public long generateAccNo()
	  {
		  return ++accNo;
	  }

	  
	  //method to give access to access active Account Only
	  public void accountAccess(AccountDetails obj) throws MistakeOccuredException
	  {
		  if(obj.isStatus()==false)
		  {
			  throw new MistakeOccuredException("This Account is deactivated please Contact branch.");
		  }
	  }
	  
	  
	  
	  //method to check customer id's value is exist or not Exist in customer map 
	  public void idCheck(int id) throws MistakeOccuredException
	  {
		  if(customerMap.get(id)==null)
		  {
			  throw new MistakeOccuredException("Entered Key is not exist.");
		  }
	  }
	  
	//method to create file.
		public File createFile(String path,String fileName)throws MistakeOccuredException
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
	  //Method to create Customer
	  public Properties addCustomerInfo(String path,String name,Customer cusObj) throws MistakeOccuredException
	  {
		  File file=createFile(path,name);
		  try(FileWriter writer=new FileWriter(file,true))
		  {
		  HelperUtil.objectCheck(cusObj);
		  int cusId=cusObj.getCustomerId();
		  String cusId1=String.valueOf(cusId);
		  String obj=String.valueOf(cusObj);
		  customerMap.setProperty(cusId1,obj);
		  customerMap.store(writer,"");
		  return customerMap;
		  }
		  catch(IOException ex)
		  {
			  throw new MistakeOccuredException(ex);
		  }
	  }
	  
	  
	  public String getCustomerInfo(String path,String name,int id)throws MistakeOccuredException, IOException
	  {
		  HelperUtil.numberCheck(id);
		  String e=String.valueOf(id);
		  File file=createFile(path,name);
		  try(BufferedReader reader=new BufferedReader(new FileReader(file)))
		  {
		  customerMap.load(reader);
		  return customerMap.getProperty(e);
		  }
		  catch(IOException ex)
		  {
			  throw new MistakeOccuredException(ex);
		  }
	  }
	  
	  
	  public boolean getStatusOfCustomer(String path,String name,int id)throws MistakeOccuredException
	  {
		  String e=String.valueOf(id);
		  Customer cusDetails=stringToObject(path,name,id);
		  HelperUtil.objectCheck(cusDetails, "CustomerDetails");
		  return cusDetails.isStatus();
	  }
	  
	  //Method to add Account in customer Id.
	  @SuppressWarnings("unused")
	public Properties addAccountToCusId(String path,String name,int cusId,AccountDetails accObj) throws MistakeOccuredException
	  {
		  HelperUtil.numberCheck(cusId);
		  HelperUtil.objectCheck(accObj);
		  String id=String.valueOf(cusId);
	      String getExisting=accountInfo.getProperty(id);
	      Properties myProps=null;
	      File file=createFile(path,name);
		  try(FileWriter writer=new FileWriter(file,true))
		  {
	      if(getExisting==null)
	      {
	    	  myProps=new Properties();
	    	  myProps.put(String.valueOf(accObj.getAccountNumber()),String.valueOf(accObj));
	    	  accountInfo.put(id, String.valueOf(myProps));
	    	  accountInfo.store(writer, "");
	    	  return accountInfo;
	      }
	      else
	      {	
	      Properties loser=new Properties();
	      String[] arr=getExisting.split("=");
	      System.out.println(Arrays.toString(arr));
	      for(int i=0;i<arr.length;i++)
	      {
	    	  if(i%2!=0)
	    	  {
	    	  loser.put(arr[i-1], arr[i]);
	    	  }
	      }
	      myProps=loser;
	      myProps.put(accObj.getAccountNumber(),accObj);
	      accountInfo.put(id,String.valueOf(myProps));
	      System.out.println(accountInfo);
	      accountInfo.store(writer,"AccountDetails");
	      return accountInfo;
		  } 
      }
	      catch (IOException e) {
			throw new MistakeOccuredException(e);
		}
		  }
		
	      /*if(getExisting==null)
		  {
			  Map<Long, AccountDetails> mapOfAcc=new HashMap<>();
			  mapOfAcc.put(accObj.getAccountNumber(),accObj);
			  accountInfo.put(id,mapOfAcc);
			  return accountInfo;
		  }
		   accountInfo.get(id).put(accObj.getAccountNumber(),accObj);
		   */
//	      if(getExisting==null)
//		  {
//			  getExisting=new Hashtable<>();
//			  accountInfo.put(cusId, getExisting); 
//		  } 
//	       getExisting.put(accObj.getAccountNumber(),accObj);
//	      
//		   return accountInfo;
//	   }
	  
	  

	  public Customer stringToObject(String path,String name,int id) throws MistakeOccuredException
	  {
		  String[] customerArr=null;
		  String[] singleDetails=null;
		  String[] finalArr=null;
		  List detailsList=null;
		  try {
			String customerDetails=getCustomerInfo(path,name,id);
			 customerArr = customerDetails.split(",");
			 for(int i=0;i<customerArr.length;i++)
			 {
	                singleDetails=customerArr[i].split("\\:");
	                if(singleDetails[0].equals("Customer Name"))
	                {
	                	cusInfo.setName(singleDetails[1]);
	                }
	                else if(singleDetails[0].equals("Customer DOB"))
	                {
	                	cusInfo.setDob(singleDetails[1]);
	                }
	                else if(singleDetails[0].equals("Address"))
	                {
	                	cusInfo.setAddress(singleDetails[1]);
	                }
	                else if(singleDetails[0].equals("Phone Number"))
	                {
	                	cusInfo.setPhoneNumber(Long.parseLong(singleDetails[1]));
	                }
	                else if(singleDetails[0].equals("status"))
	                {
	                	cusInfo.setStatus(Boolean.parseBoolean(singleDetails[1]));
	                }
			 }
			 return cusInfo;
		}
		 catch (IOException e) {
			// TODO Auto-generated catch block
			throw new MistakeOccuredException(e);
		}
	
	  }
	  
	  //Method to add Account in customer Id.
//	  public Map<Integer,Map<Long,AccountDetails>> addAccountToCusId(int cusId,AccountDetails accObj) throws MistakeOccuredException
//	  {
//		  HelperUtil.numberCheck(cusId);
//		  HelperUtil.objectCheck(accObj);
//		  idCheck(cusId);
//	      Map<Long,AccountDetails> getExisting=accountInfo.get(cusId);
//		
//	      /*if(getExisting==null)
//		  {
//			  Map<Long, AccountDetails> mapOfAcc=new HashMap<>();
//			  mapOfAcc.put(accObj.getAccountNumber(),accObj);
//			  accountInfo.put(id,mapOfAcc);
//			  return accountInfo;
//		  }
//		   accountInfo.get(id).put(accObj.getAccountNumber(),accObj);
//		   */
//	      if(getExisting==null)
//		  {
//			  getExisting=new Hashtable<>();
//			  accountInfo.put(cusId, getExisting); 
//		  } 
//	       getExisting.put(accObj.getAccountNumber(),accObj);
//	      
//		   return accountInfo;
//	   }
//	 
//	//  public void addAccToCusId(int id,AccountDetails accObj)
//	//  {
////		  if(newMap.get(id)!=null)
////		  {
////		  if(accountInfo.get(id)!=null)
////		  {
////			  ((List<Object>) accountInfo.get(id)).add(accObj);
////		  }
////		  else
////		  {
////			  List<Object> temp=new ArrayList<>();
////			  temp.add(accObj);
////			  accountInfo.put(id,temp);
////		  }
////		  System.out.println(accountInfo);
////		  }
////		  else
////		  {
////			System.out.println("DoesNotExist");  
////		  }
//	//  }
//
//
//	  // method to get customer details
//	  public Object getCustomerInfo(int id)throws MistakeOccuredException
//	  {
//		  idCheck(id);
//		  HelperUtil.numberCheck(id);
//		  return customerMap.get(id);
//	  }
//	  
//
//	  //method to get account details belongs to customer id.
//	  public AccountDetails getAccountInfo(int cusId,long accNo)throws MistakeOccuredException
//	  {
//		  HelperUtil.numberCheck(cusId);
//		  Map<Long,AccountDetails> map2 = accountInfo.get(cusId);
//		  HelperUtil.objectCheck(map2, "CustomerId");
//		  AccountDetails accDetails=map2.get(accNo);
//		  HelperUtil.objectCheck(accDetails, "AccountDetails");
//		  return accDetails;
//	   }
//	  
//
//	  // method to get status of the Account.
//	  public boolean getStatusOfAccount(int cusId,long accNo)throws MistakeOccuredException
//	  {
//		  HelperUtil.numberCheck(cusId);
//	      Map<Long,AccountDetails> map2 = accountInfo.get(cusId);
//	      HelperUtil.objectCheck(map2, "CustomerId");
//		  AccountDetails accDetails=map2.get(accNo);
//		  HelperUtil.objectCheck(accDetails, "AccountDetails");
//		  return accDetails.isStatus();
//	  }
//
//	  
//	  //method to set status of the Account.
//	  public void setStatusOfAccount(int cusId,long accNo,int status) throws MistakeOccuredException
//	  {
//		  HelperUtil.numberCheck(cusId);
//		  HelperUtil.numberCheck(status);
//		  Map<Long,AccountDetails> map2 = accountInfo.get(cusId);
//		  HelperUtil.objectCheck(map2, "CustomerId");
//		  AccountDetails accDetails=map2.get(accNo);
//		  HelperUtil.objectCheck(accDetails, "AccountDetails");
//		  if(status==1)
//		  {
//			  accDetails.setStatus(true);
//			  System.out.println(map2);
//		  }
//		  else if(status==0)
//		  {
//			  accDetails.setStatus(false);
//			  System.out.println(map2);
//		  }
//		  else
//		  {
//			  throw new MistakeOccuredException("Enter The Corresponding numbers only.");
//		  }
//	  }
//	  
//	  
//	  //method to set status of Customer.
//	  public void setStatusOfCustomer(int cusId,int status)throws MistakeOccuredException
//	  {
//		  Customer customerDetails=customerMap.get(cusId);
//		  HelperUtil.objectCheck(customerDetails, "CustomerDetails");
//		  if(status==1)
//		  {
//			  customerDetails.setStatus(true);
//			  System.out.println(customerDetails);
//		  }
//		  else if(status==0)
//		  {
//			  customerDetails.setStatus(false);
//			  System.out.println(customerDetails);
//		  }
//		  else
//		  {
//			  throw new MistakeOccuredException("Enter The Corresponding numbers only.");
//		  }
//	  }
//	  
//	  
//	  //method to get status of customer.
//	  public boolean getStatusOfCustomer(int cusId)throws MistakeOccuredException
//	  {
//		  Customer customerDetails=customerMap.get(cusId);
//		  HelperUtil.objectCheck(customerDetails, "CustomerDetails");
//		  return customerDetails.isStatus();
//	  }
//	  
//	  
//	  //method to calculate the balance after deposit
//	  public double balanceAfterDeposit(int cusId,long accNo,double depositAmount)throws MistakeOccuredException
//	  {
//		  Map<Long,AccountDetails> accDetailsMap=accountInfo.get(cusId);
//		  HelperUtil.objectCheck(accDetailsMap, "Customer account");
//		  AccountDetails accInfo=accDetailsMap.get(accNo);
//		  HelperUtil.objectCheck(accInfo, "AccountDetails");
//		  accountAccess(accInfo);
//		  double balance=accInfo.getBalance();
//		  double newBalance=balance+depositAmount;
//		  accInfo.setBalance(newBalance);
//	      return accInfo.getBalance();
//	  }
//	   
//	  public double balanceAfterWithDraw(int cusId,long accNo,double withDrawAmount)throws MistakeOccuredException
//	  {
//		  Map<Long,AccountDetails> accDetailsMap=accountInfo.get(cusId);
//		  HelperUtil.objectCheck(accDetailsMap, "CustomerId");
//		  AccountDetails accInfo=accDetailsMap.get(accNo);
//		  HelperUtil.objectCheck(accInfo, "AccountDetails");
//		  accountAccess(accInfo);
//		  double balance=accInfo.getBalance();
//		  if(balance<withDrawAmount)
//		  {
//			  throw new MistakeOccuredException("Insufficient balance.");
//		  }
//		  double newBalance=balance-withDrawAmount;
//		  accInfo.setBalance(newBalance);
//	      return accInfo.getBalance();
//	  }
	 
//	  public static String toString(Object o) throws IOException {
//		    try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		            ObjectOutputStream oos = new ObjectOutputStream(baos);) {
//		        oos.writeObject(o);
//		        return new String(Base64.getEncoder().encode(baos.toByteArray()));
//		    }
//		}
//	  public static Object fromString(String s) throws IOException, ClassNotFoundException {
//		    byte[] data = Base64.getDecoder().decode(s);
//		    Object o;
//		    try (ObjectInputStream ois = new ObjectInputStream(
//		            new ByteArrayInputStream(data))) {
//		        o = ois.readObject();
//		    }
//		    return o;
//		}
	  
}
