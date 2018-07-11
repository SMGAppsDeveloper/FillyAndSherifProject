package developer.app.smg.businessaddressregistration;

public class AddisAbabaBusinessLocationsClass {
    private String OwnerName, BusinessName, BusinessPhone, BuildName, FloorNum, Lat, Long;

    public AddisAbabaBusinessLocationsClass(){

    }

    public AddisAbabaBusinessLocationsClass(String ownerName, String businessName, String businessPhone, String buildName, String floorNum, String lat, String aLong) {
        OwnerName = ownerName;
        BusinessName = businessName;
        BusinessPhone = businessPhone;
        BuildName = buildName;
        FloorNum = floorNum;
        Lat = lat;
        Long = aLong;
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public String getBusinessName() {
        return BusinessName;
    }

    public String getBusinessPhone() {
        return BusinessPhone;
    }

    public String getBuildName() {
        return BuildName;
    }

    public String getFloorNum() {
        return FloorNum;
    }

    public String getLat() {
        return Lat;
    }

    public String getLong() {
        return Long;
    }
}
