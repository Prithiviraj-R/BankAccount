package bankaccountmanagement;

import java.util.*;

import newexception.MistakeOccuredException;
import util.HelperUtil;

public class ApiLayer 
{
  //Map used to store the Customer and Account details.
  Map<Integer,Customer> customerMap=new HashMap<>();
  Map<Integer,Map<Long,AccountDetails>> accountInfo=new HashMap<>();

  
  //Object for Customer PojoClass
  Customer cusInfo=new Customer();
  //Object for Account PojoClass
  AccountDetails accInfo = new AccountDetails();
  
  
  //Auto generated accNo and Customer Id are after this below numbers.
  int cusId=10;
  long accNo=10000178;
  
  
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
  
  
  //Method to create Customer
  public Map<Integer,Customer> addCustomerInfo(Customer cusObj) throws MistakeOccuredException
  {
	  HelperUtil.objectCheck(cusObj);
	  int cusId=cusObj.getCustomerId();
	  customerMap.put(cusId,cusObj);
	  return customerMap;
  }
  
  
  //Method to add Account in customer Id.
  public Map<Integer,Map<Long,AccountDetails>> addAccountToCusId(int cusId,AccountDetails accObj) throws MistakeOccuredException
  {
	  HelperUtil.numberCheck(cusId);
	  HelperUtil.objectCheck(accObj);
	  idCheck(cusId);
      Map<Long,AccountDetails> getExisting=accountInfo.get(cusId);
	
      /*if(getExisting==null)
	  {
		  Map<Long, AccountDetails> mapOfAcc=new HashMap<>();
		  mapOfAcc.put(accObj.getAccountNumber(),accObj);
		  accountInfo.put(id,mapOfAcc);
		  return accountInfo;
	  }
	   accountInfo.get(id).put(accObj.getAccountNumber(),accObj);
	   */
      if(getExisting==null)
	  {
		  getExisting=new HashMap<>();
		  accountInfo.put(cusId, getExisting); 
	  } 
       getExisting.put(accObj.getAccountNumber(),accObj);
      
	   return accountInfo;
   }
 
//  public void addAccToCusId(int id,AccountDetails accObj)
//  {
//	  if(newMap.get(id)!=null)
//	  {
//	  if(accountInfo.get(id)!=null)
//	  {
//		  ((List<Object>) accountInfo.get(id)).add(accObj);
//	  }
//	  else
//	  {
//		  List<Object> temp=new ArrayList<>();
//		  temp.add(accObj);
//		  accountInfo.put(id,temp);
//	  }
//	  System.out.println(accountInfo);
//	  }
//	  else
//	  {
//		System.out.println("DoesNotExist");  
//	  }
//  }


  // method to get customer details
  public Object getCustomerInfo(int id)throws MistakeOccuredException
  {
	  idCheck(id);
	  HelperUtil.numberCheck(id);
	  return customerMap.get(id);
  }
  

  //method to get account details belongs to customer id.
  public AccountDetails getAccountInfo(int cusId,long accNo)throws MistakeOccuredException
  {
	  HelperUtil.numberCheck(cusId);
	  Map<Long,AccountDetails> map2 = accountInfo.get(cusId);
	  HelperUtil.objectCheck(map2, "CustomerId");
	  AccountDetails accDetails=map2.get(accNo);
	  HelperUtil.objectCheck(accDetails, "AccountDetails");
	  return accDetails;
   }
  

  // method to get status of the Account.
  public boolean getStatusOfAccount(int cusId,long accNo)throws MistakeOccuredException
  {
	  HelperUtil.numberCheck(cusId);
      Map<Long,AccountDetails> map2 = accountInfo.get(cusId);
      HelperUtil.objectCheck(map2, "CustomerId");
	  AccountDetails accDetails=map2.get(accNo);
	  HelperUtil.objectCheck(accDetails, "AccountDetails");
	  return accDetails.isStatus();
  }

  
  //method to set status of the Account.
  public void setStatusOfAccount(int cusId,long accNo,int status) throws MistakeOccuredException
  {
	  HelperUtil.numberCheck(cusId);
	  HelperUtil.numberCheck(status);
	  Map<Long,AccountDetails> map2 = accountInfo.get(cusId);
	  HelperUtil.objectCheck(map2, "CustomerId");
	  AccountDetails accDetails=map2.get(accNo);
	  HelperUtil.objectCheck(accDetails, "AccountDetails");
	  if(status==1)
	  {
		  accDetails.setStatus(true);
		  System.out.println(map2);
	  }
	  else if(status==0)
	  {
		  accDetails.setStatus(false);
		  System.out.println(map2);
	  }
	  else
	  {
		  throw new MistakeOccuredException("Enter The Corresponding numbers only.");
	  }
  }
  
  
  //method to set status of Customer.
  public void setStatusOfCustomer(int cusId,int status)throws MistakeOccuredException
  {
	  Customer customerDetails=customerMap.get(cusId);
	  HelperUtil.objectCheck(customerDetails, "CustomerDetails");
	  if(status==1)
	  {
		  customerDetails.setStatus(true);
		  System.out.println(customerDetails);
	  }
	  else if(status==0)
	  {
		  customerDetails.setStatus(false);
		  System.out.println(customerDetails);
	  }
	  else
	  {
		  throw new MistakeOccuredException("Enter The Corresponding numbers only.");
	  }
  }
  
  
  //method to get status of customer.
  public boolean getStatusOfCustomer(int cusId)throws MistakeOccuredException
  {
	  Customer customerDetails=customerMap.get(cusId);
	  HelperUtil.objectCheck(customerDetails, "CustomerDetails");
	  return customerDetails.isStatus();
  }
  
  
  //method to calculate the balance after deposit
  public double balanceAfterDeposit(int cusId,long accNo,double depositAmount)throws MistakeOccuredException
  {
	  Map<Long,AccountDetails> accDetailsMap=accountInfo.get(cusId);
	  HelperUtil.objectCheck(accDetailsMap, "Customer account");
	  AccountDetails accInfo=accDetailsMap.get(accNo);
	  HelperUtil.objectCheck(accInfo, "AccountDetails");
	  accountAccess(accInfo);
	  double balance=accInfo.getBalance();
	  double newBalance=balance+depositAmount;
	  accInfo.setBalance(newBalance);
      return accInfo.getBalance();
  }
   
  public double balanceAfterWithDraw(int cusId,long accNo,double withDrawAmount)throws MistakeOccuredException
  {
	  Map<Long,AccountDetails> accDetailsMap=accountInfo.get(cusId);
	  HelperUtil.objectCheck(accDetailsMap, "CustomerId");
	  AccountDetails accInfo=accDetailsMap.get(accNo);
	  HelperUtil.objectCheck(accInfo, "AccountDetails");
	  accountAccess(accInfo);
	  double balance=accInfo.getBalance();
	  if(balance<withDrawAmount)
	  {
		  throw new MistakeOccuredException("Insufficient balance.");
	  }
	  double newBalance=balance-withDrawAmount;
	  accInfo.setBalance(newBalance);
      return accInfo.getBalance();
  }
  
}
