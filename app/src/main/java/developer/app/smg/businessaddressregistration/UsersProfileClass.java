package developer.app.smg.businessaddressregistration;

public class UsersProfileClass {
    private String fname, mname, lname, email, phone, lat, longt;

    public UsersProfileClass(){

    }

    public UsersProfileClass(String fname, String mname, String lname, String email, String phone, String lat, String longt) {
        this.fname = fname;
        this.mname = mname;
        this.lname = lname;
        this.email = email;
        this.phone = phone;
        this.lat = lat;
        this.longt = longt;
    }

    public String getFname() {
        return fname;
    }

    public String getMname() {
        return mname;
    }

    public String getLname() {
        return lname;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getLat() {
        return lat;
    }

    public String getLongt() {
        return longt;
    }
}
