package bankaccountmanagement;

import java.util.Map;
import java.util.Scanner;

import newexception.MistakeOccuredException;

public class InputLayerForFile 
{
	public static void main(String[] args) throws MistakeOccuredException {
		Scanner input=new Scanner(System.in);
		ApiLayerSerialization layerObj = new ApiLayerSerialization();
		
		System.out.println("1.Generate Customer Id"+"\n"+"2.Create Account in customer Id"+"\n"+"3.Get Customer info"+"\n"+"4.get Account info"+"\n"+"5.get The status of Account"+"\n"+"6.Set The status of Account"+"\n"+"7.Get the Status of Customer"
		+"\n"+"8.Set the status of customer"+"\n"+"9.Deposit"+"\n"+"10.Withdraw");
		System.out.println("Enter the choice:");
		int choice=Integer.parseInt(input.nextLine());
		switch(choice)
		{
		case 1:
			try
			{
	    System.out.println("Generate Customer ID");
		System.out.println("Enter the count");
		int count=Integer.parseInt(input.nextLine());
		Customer cusObj=null;
	    AccountDetails accObj = null;
		Map newOne=null;
	    for(int i=0;i<count;i++)
	    {
	    	cusObj=new Customer();
	    	System.out.println("Enter the name:");
	    	String name=input.nextLine();
	    	System.out.println("Enter the date of birth:");
	    	String DOB=input.nextLine();
	    	System.out.println("Enter the address:");
	    	String address=input.nextLine();
	    	System.out.println("Enter the phone number:");
	    	long phNo=input.nextLong();
	    	System.out.println("Enter the Customer Id:");
			int id=input.nextInt();input.nextLine();
	    	input.nextLine();
	    	cusObj.setName(name);
	    	cusObj.setDob(DOB);
	    	cusObj.setAddress(address);
	    	cusObj.setPhoneNumber(phNo);
	    	cusObj.setCustomerId(id);
	    	newOne=layerObj.addCustomerInfo("D:\\Zoho workSpace","CustomerDetails",cusObj);
	    }
	    System.out.println(newOne);
			}
			catch(MistakeOccuredException ex)
			{
				System.out.println(ex.getMessage());
			}
	    break;
		case 2:
			try
			{
			System.out.println("Adding Account to Customer Id:");
			AccountDetails accNo1=null;
			Map accMap=null;
			System.out.println("Enter the count of Account:");
			int count1=input.nextInt();
			input.nextLine();
			for(int i=0;i<count1;i++)
			{
			accNo1=new AccountDetails();
			System.out.println("Enter the Customer Id:");
			int id=input.nextInt();input.nextLine();
			System.out.println("Enter the branch:");
			String branch=input.nextLine();
			System.out.println("Enter the AccountBalance:");
			double bal=input.nextFloat();
			System.out.println("Enter the Account number:");
	    	long accNo=input.nextLong();
			input.nextLine();
			accNo1.setBranch(branch);
			accNo1.setCustomerId(id);
			accNo1.setBalance(bal);
			accNo1.setAccountNumber(accNo);
			System.out.println(layerObj.addAccountToCusId("D:\\Zoho workSpace","AccountDetails",id,accNo1));
			}
			}
			catch(MistakeOccuredException ex)
			{
				System.out.println(ex.getMessage());
				ex.printStackTrace();
			}
			catch(Exception ex)
			{
				System.out.println(ex.getMessage());
			}
			break;
		case 3:
			try
			{
			System.out.println("Get the Customer Info:");
			System.out.println("Enter the id");
			int cusId=input.nextInt();input.nextLine();
			System.out.println(layerObj.getCustomerInfo(cusId));
			}
			catch(MistakeOccuredException ex)
			{
				System.out.println(ex.getMessage());
			}
			catch(Exception ex)
			{
				System.out.println(ex.getMessage());
			}
			break;
		case 4:
			try
			{
			System.out.println("Get the Account Info:");
			System.out.println("Enter the id");
			int cusId1=input.nextInt();input.nextLine();
			System.out.println("Enter the Account number");
			long accNo=input.nextInt();input.nextLine();
			System.out.println(layerObj.getAccountInfo("D:\\Zoho workSpace","AccountDetails",cusId1,accNo));
			}
			catch(MistakeOccuredException ex)
			{
				System.out.println(ex.getMessage());
			}
			catch(Exception ex)
			{
				System.out.println(ex.getMessage());
			}
			break;
		case 5:
			try
			{
			    System.out.println("Get the Status of Account.");;
				System.out.println("Enter the id");
				int cusId1=input.nextInt();input.nextLine();
				System.out.println("Enter the Account number");
				int accNo=input.nextInt();input.nextLine();
				System.out.println(layerObj.getStatusOfAccount(cusId1,accNo));
			}
			catch(MistakeOccuredException ex)
				{
					System.out.println(ex.getMessage());
				}
			catch(Exception ex)
				{
					System.out.println(ex.getMessage());
				}
			break;
		case 6:
			try
			{
			    System.out.println("Set the Status of Account.");;
				System.out.println("Enter the id");
				int cusId1=input.nextInt();input.nextLine();
				System.out.println("Enter the Account number");
				long accNo=input.nextInt();input.nextLine();
				System.out.println("Enter the Status:Active-->1,Inactive-->0");
				int status=input.nextInt();input.nextLine(); 
				layerObj.setStatusOfAccount(cusId1, accNo, status);
			}
			catch(MistakeOccuredException ex)
				{
					System.out.println(ex.getMessage());
				}
			catch(Exception ex)
				{
					System.out.println(ex.getMessage());
				}
			break;
		case 7:
			try
			{
			    System.out.println("Get the Status of Customer.");;
				System.out.println("Enter the id");
				int cusId1=input.nextInt();input.nextLine();
				System.out.println(layerObj.getStatusOfCustomer(cusId1));
			}
			catch(MistakeOccuredException ex)
				{
					System.out.println(ex.getMessage());
				}
			catch(Exception ex)
				{
					System.out.println(ex.getMessage());
				}
			break;
		case 8:
			try
			{
			    System.out.println("set the Status of Customer.");;
				System.out.println("Enter the id");
				int cusId1=input.nextInt();input.nextLine();
				System.out.println("Enter the Status:Active-->1,Inactive-->0");
				int status=input.nextInt();input.nextLine(); 
				layerObj.setStatusOfCustomer(cusId1, status);
			}
			catch(MistakeOccuredException ex)
				{
					System.out.println(ex.getMessage());
				}
			catch(Exception ex)
				{
					System.out.println(ex.getMessage());
				}
			break;
		
		case 9:
			try
			{
				System.out.println("Deposit");
				System.out.println("Enter the id");
				int cusId1=input.nextInt();input.nextLine();
				System.out.println("Enter the Account number");
				long accNo=input.nextLong();input.nextLine();
				System.out.println("Enter the amount to be deposit:");
				double deposit=input.nextDouble();input.nextLine();
				System.out.println(layerObj.balanceAfterDeposit(cusId1, accNo, deposit));
			}
			catch(MistakeOccuredException ex)
			{
				System.out.println(ex.getMessage());
			}
			catch(Exception ex)
			{
	            ex.printStackTrace();			
			}
			break;
			
		case 10:
			try
			{
				System.out.println("Withdraw");
				System.out.println("Enter the id");
				int cusId1=input.nextInt();input.nextLine();
				System.out.println("Enter the Account number");
				long accNo=input.nextLong();input.nextLine();
				System.out.println("Enter the amount to be deposit:");
				double withDraw=input.nextDouble();input.nextLine();
				System.out.println(layerObj.balanceAfterWithDraw(cusId1, accNo, withDraw));
			}
			catch(MistakeOccuredException ex)
			{
				System.out.println(ex.getMessage());
			}
			catch(Exception ex)
			{
	            ex.printStackTrace();			
			}
			break;
		default:
			System.out.println("Enter the correct one.");
			break;
		}
	}
}
