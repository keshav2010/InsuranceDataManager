package UserDetail;
import java.io.Serializable;

import Misc.CustomDate;
//Add + Delete + Update
//filter by name (character)
//filter by mobile number
//search by policyNumber
//filter by nextDue
//filter by doc
public class User implements Serializable, Comparable<User> {
	public String name;
	public String policyNumber; 
	public String mobileNumber;
	public CustomDate dateOfBirth;
	public CustomDate doc; //date of commencement 
	public double sum;//sum assured 
	//plan and term (int and int)
	public String mode; //monthy/ yearly etc etc
	public double primium;
	public String nextDue; //month names , this should be array or suitable 
	public String address;
	public String planAndTerm;
	
	@Override
	public int compareTo(User other) {
		return 0;//same
	}
	
};
