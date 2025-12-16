package model;

public class HocSinh {
    private int maHocSinh;
    private String hoTen;
    private String lop;

    public HocSinh() {}

    public HocSinh(int maHocSinh, String hoTen, String lop) {
        this.maHocSinh = maHocSinh;
        this.hoTen = hoTen;
        this.lop = lop;
    }

    public int getMaHocSinh() { return maHocSinh; }
    public void setMaHocSinh(int maHocSinh) { this.maHocSinh = maHocSinh; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getLop() { return lop; }
    public void setLop(String lop) { this.lop = lop; }
}