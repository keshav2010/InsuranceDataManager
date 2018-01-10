package UserDetail;
import java.io.Serializable;
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
	public String dateOfBirth;
	public String doc; //date of commencement 
	public double sum;//sum assured 
	//plan and term (int and int)
	public String mode; //monthy/ yearly etc etc
	public double primium;
	public String nextDue; //month names , trhis should be array or suitable 
	public String address;
	
	
	@Override
	public int compareTo(User other) {
		return 0;//same
	}
	
};
