package rmi_phone;

import java.io.Serializable;

public class Phone implements Serializable {
    private static final long serialVersionUID = 1L; // đảm bảo phiên bản của lớp tương thích giữa các hệ thống

    private String id;
    private String name;
    private String brand;
    private String basicInfo;
    private double price;

    public Phone(String id, String name, String brand, String basicInfo, double price) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.basicInfo = basicInfo;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(String basicInfo) {
        this.basicInfo = basicInfo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
