package com.javateam.HanXacThucNguoiDung;

public class User {
    private int userid;
    private String hoten;
    private String ngaySinh;
    private String phone;
    private String email;
    private String diachi;
    private String hashedPassword;

    public User(int userid, String hoten, String ngaySinh, String phone, String email, String diachi, String hashedPassword) {
        this.userid = userid;
        this.hoten = hoten;
        this.ngaySinh = ngaySinh;
        this.phone = phone;
        this.email = email;
        this.diachi = diachi;
        this.hashedPassword = hashedPassword;
    }

    public int getUserid() { return userid; }
    public String getHoten() { return hoten; }
    public String getNgaySinh() { return ngaySinh; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getDiachi() { return diachi; }
    public String getHashedPassword() { return hashedPassword; }
}