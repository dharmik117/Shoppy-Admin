package Model;

public class customer {

    public String username,fullname,emailid,phoneno,gender;

    public customer(String name, String phone){


    }

    public customer(String username, String fullname, String emailid, String phoneno, String gender) {
        this.username = username;
        this.fullname = fullname;
        this.emailid = emailid;
        this.phoneno = phoneno;
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }
}
