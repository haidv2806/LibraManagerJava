package BackEnd_Hai.newpackage;

// CREATE TABLE books (
//     MaSach SERIAL PRIMARY KEY,
//     MaNXB INT,
//     TenSach VARCHAR(50),
//     NamXuatBan DATE,
//     Gia MONEY,
//     MoTa VARCHAR(1000),
//     FOREIGN KEY (MaNXB) REFERENCES publisher(MaNXB)
// );

public class Books {
    private int MaSach;
    private int MaNXB;
    private String TenSach;
    private String NamXuatBan;
    private double Gia;
    private String MoTa;
}
