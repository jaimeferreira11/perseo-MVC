package py.com.perseo.sys;

import java.util.ArrayList;
import java.util.List;

public class ActiveUserStore {
 
	
	/**
	 * active user
	 */
    public List<String> users;
 
    public ActiveUserStore() {
        users = new ArrayList<String>();
    }

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}
    
}