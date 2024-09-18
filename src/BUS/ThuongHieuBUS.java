/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package BUS;

import java.util.ArrayList;

import DAO.ThuongHieuDAO;
import DTO.ThuocTinhSanPham.ThuongHieuDTO;

/**
 *
 * @author 84907
 */
public class ThuongHieuBUS {

    private final ThuongHieuDAO lsDAO = new ThuongHieuDAO();
    private ArrayList<ThuongHieuDTO> listTH = new ArrayList<>();

    public ThuongHieuBUS() {
        listTH = lsDAO.selectAll();
    }

    // tổng số lượng của thương hiệu
    public ArrayList<ThuongHieuDTO> getAll() {
        return this.listTH;
    }

    public ThuongHieuDTO getByIndex(int index) {
        return this.listTH.get(index);
    }

    // khi update xog nó sẽ vô đây(tìm chỉ số theo mã)
    public int getIndexByMaLH(int maloaihang) {
        int i = 0;
        int vitri = -1;
        while (i < this.listTH.size() && vitri == -1) {
            if (listTH.get(i).getMathuonghieu() == maloaihang) {
                vitri = i;
            } else {
                i++;
            }
        }
        return vitri;
    }

    public Boolean add(String name) {
        // dữ liệu thêm vào
        ThuongHieuDTO lh = new ThuongHieuDTO(lsDAO.getAutoIncrement(), name);
        boolean check = lsDAO.insert(lh) != 0;
        if (check) {
            // nếu true sẽ add vào
            this.listTH.add(lh);
        }
        return check;
    }

    public Boolean delete(ThuongHieuDTO lh) {
        boolean check = lsDAO.delete(Integer.toString(lh.getMathuonghieu())) != 0;
        if (check) {
            this.listTH.remove(lh);
        }
        return check;
    }

    // lh như generic của java
    public Boolean update(ThuongHieuDTO lh) {
        boolean check = lsDAO.update(lh) != 0;
        if (check) {
            this.listTH.set(getIndexByMaLH(lh.getMathuonghieu()), lh);

        }
        return check;
    }

    public ArrayList<ThuongHieuDTO> search(String text) {
        text = text.toLowerCase();
        ArrayList<ThuongHieuDTO> result = new ArrayList<>();
        for (ThuongHieuDTO i : this.listTH) {
            if (Integer.toString(i.getMathuonghieu()).toLowerCase().contains(text)
                    || i.getTenthuonghieu().toLowerCase().contains(text)) {
                result.add(i);
            }
        }
        return result;
    }

    public String[] getArrTenThuongHieu() {
        int size = listTH.size();
        String[] result = new String[size];
        for (int i = 0; i < size; i++) {
            result[i] = listTH.get(i).getTenthuonghieu();
        }
        return result;
    }

    // lấy tên thương hiệu dựa vào id
    public String getTenThuongHieu(int mathuonghieu) {
        return this.listTH.get(this.getIndexByMaLH(mathuonghieu)).getTenthuonghieu();
    }

    // kiểm tra tên thương hiệu có bị trùng lặp hay không
    public boolean checkDup(String name) {
        boolean check = true;
        int i = 0;
        while (i < this.listTH.size() && check == true) {
            if (this.listTH.get(i).getTenthuonghieu().toLowerCase().contains(name.toLowerCase())) {
                check = false;
            } else {
                i++;
            }
        }
        return check;
    }
}
