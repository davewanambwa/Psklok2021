package ke.co.corellia.psklok;

import com.google.android.gms.maps.model.LatLng;

public class LazyInitializedSingletonExample {
    private static LazyInitializedSingletonExample instance;
    private String lo;
    private String json;

    private String Email;
    private String Password;
    private String Telephone;
    private String Fullname ="None";
    private String Result;
    private String Distance;
    private Double fromlat;
    private Double tolat;
    private Double fromlon;
    private Double tolon;
    private LatLng lfrom;
    private int weight;
    private String phoneofdriver;
    private Double wallet;
    private boolean ride=true;
    private LatLng lto;
    private String riderequesteresponse;
    private String customerid,fcode;
    private Double topamount;

    private String rReportid;

    public String getPhoneofdriver() {
        return phoneofdriver;
    }

    public Double getTopamount() {
        return topamount;
    }

    public void setTopamount(Double topamount) {
        this.topamount = topamount;
    }

    public void setPhoneofdriver(String phoneofdriver) {
        this.phoneofdriver = phoneofdriver;
    }

    public String getrReportid() {
        return rReportid;
    }

    public void setrReportid(String rReportid) {
        this.rReportid = rReportid;
    }

    private int insurancevalue,insurancecost;



    private String Driver ="All riders are busy.. Searching.." ;


    private String Source,pdestination,pname;
    private String Des;
    private boolean insured;
    private String boxlock;
    private String tripid;


    public boolean isInsured() {
        return insured;
    }

    public String getTripid() {
        return tripid;
    }

    public void setTripid(String tripid) {
        this.tripid = tripid;
    }

    public String getPdestination() {
        return pdestination;
    }

    public String getBoxlock() {
        return boxlock;
    }

    public void setBoxlock(String boxlock) {
        this.boxlock = boxlock;
    }

    public void setPdestination(String pdestination) {
        this.pdestination = pdestination;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public void setInsured(boolean insured) {
        this.insured = insured;
    }

    public int getWeight() {
        return weight;
    }

    public String getFcode() {
        return fcode;
    }

    public void setFcode(String fcode) {
        this.fcode = fcode;
    }

    public int getInsurancevalue() {
        return insurancevalue;
    }

    public void setInsurancevalue(int insurancevalue) {
        this.insurancevalue = insurancevalue;
    }

    public int getInsurancecost() {
        return insurancecost;
    }

    public void setInsurancecost(int insurancecost) {
        this.insurancecost = insurancecost;
    }

    public boolean isRide() {
        return ride;
    }

    public void setRide(boolean ride) {
        this.ride = ride;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getFare() {
        return fare;
    }

    public String getDriver() {
        return Driver;
    }

    public void setDriver(String driver) {
        Driver = driver;
    }

    public Double getWallet() {
        return wallet;
    }

    public void setWallet(Double wallet) {
        this.wallet = wallet;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    private String fare;
    private String job="Ride";

    public String getJob() {
        return job;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getDes() {
        return Des;
    }

    public void setDes(String des) {
        Des = des;
    }

    public static void setInstance(LazyInitializedSingletonExample instance) {
        LazyInitializedSingletonExample.instance = instance;
    }


    public String getRiderequesteresponse() {
        return riderequesteresponse;
    }

    public void setRiderequesteresponse(String riderequesteresponse) {
        this.riderequesteresponse = riderequesteresponse;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public LatLng getLfrom() {
        return lfrom;
    }

    public void setLfrom(LatLng lfrom) {
        this.lfrom = lfrom;
    }

    public LatLng getLto() {
        return lto;
    }

    public void setLto(LatLng lto) {
        this.lto = lto;
    }

    public String getDistance() {
        return Distance;
    }

    public void setDistance(String distance) {
        Distance = distance;
    }

    public String getResult() {
        return Result;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public Double getFromlat() {
        return fromlat;
    }

    public void setFromlat(Double fromlat) {
        this.fromlat = fromlat;
    }

    public Double getTolat() {
        return tolat;
    }

    public void setTolat(Double tolat) {
        this.tolat = tolat;
    }

    public Double getFromlon() {
        return fromlon;
    }

    public void setFromlon(Double fromlon) {
        this.fromlon = fromlon;
    }

    public Double getTolon() {
        return tolon;
    }

    public void setTolon(Double tolon) {
        this.tolon = tolon;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public String getFullname() {
        return Fullname;
    }

    public void setFullname(String fullname) {
        Fullname = fullname;
    }

    private LazyInitializedSingletonExample() {
    }  //private constructor.

    public static LazyInitializedSingletonExample getInstance() {
        if (instance == null) {
            //if there is no instance available... create new one;

            instance = new LazyInitializedSingletonExample();
        }

        return instance;
    }

    public String getLo() {
        return lo;
    }

    public void setLo(String lo) {
        this.lo = lo;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
