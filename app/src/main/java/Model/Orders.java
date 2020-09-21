package Model;

public class Orders
{
    private String Product_Ready,UserId,address,city,dateandtime,name,phone,state,totalamount;

    public Orders()
    {

    }

    public Orders(String product_Ready, String userId, String address, String city, String dateandtime, String name, String phone, String state, String totalamount) {
        Product_Ready = product_Ready;
        UserId = userId;
        this.address = address;
        this.city = city;
        this.dateandtime = dateandtime;
        this.name = name;
        this.phone = phone;
        this.state = state;
        this.totalamount = totalamount;
    }

    public Orders(String id) {
    }

    public String getProduct_Ready() {
        return Product_Ready;
    }

    public void setProduct_Ready(String product_Ready) {
        Product_Ready = product_Ready;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDateandtime() {
        return dateandtime;
    }

    public void setDateandtime(String dateandtime) {
        this.dateandtime = dateandtime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTotalamount() {
        return totalamount;
    }

    public void setTotalamount(String totalamount) {
        this.totalamount = totalamount;
    }
}
