/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BUS;

import DAO.KhuVucKhoDAO;
import DTO.KhuVucKhoDTO;
import java.util.ArrayList;

public class KhuVucKhoBUS {

    private final KhuVucKhoDAO kvkDAO = new KhuVucKhoDAO();
    private ArrayList<KhuVucKhoDTO> listKVKDAO = new ArrayList<>();

    public KhuVucKhoBUS getInstance() {
        return new KhuVucKhoBUS();
    }

    public KhuVucKhoBUS() {
        listKVKDAO = kvkDAO.selectAll();
    }

    public ArrayList<KhuVucKhoDTO> getAll() {
        return this.listKVKDAO;
    }

    public KhuVucKhoDTO getByIndex(int index) {
        return this.listKVKDAO.get(index);
    }

    public int getIndexByMaLH(int makhuvuc) {
        int i = 0;
        int vitri = -1;
        while (i < this.listKVKDAO.size() && vitri == -1) {
            if (listKVKDAO.get(i).getMakhuvuc() == makhuvuc) {
                vitri = i;
            } else {
                i++;
            }
        }
        return vitri;
    }

    public boolean add(KhuVucKhoDTO kvk) {
        boolean check = kvkDAO.insert(kvk) != 0;
        if (check) {
            this.listKVKDAO.add(kvk);
        }
        return check;
    }

    public boolean delete(KhuVucKhoDTO kvk, int index) {
        boolean check = kvkDAO.delete(Integer.toString(kvk.getMakhuvuc())) != 0;
        if (check) {
            this.listKVKDAO.remove(index);
        }
        return check;
    }

    public boolean update(KhuVucKhoDTO kvk) {
        boolean check = kvkDAO.update(kvk) != 0;
        if (check) {
            this.listKVKDAO.set(getIndexByMaKVK(kvk.getMakhuvuc()), kvk);
        }
        return check;
    }

    public int getIndexByMaKVK(int makvk) {
        int i = 0;
        int vitri = -1;
        while (i < this.listKVKDAO.size() && vitri == -1) {
            if (listKVKDAO.get(i).getMakhuvuc() == makvk) {
                vitri = i;
                break;
            } else {
                i++;
            }
        }
        return vitri;
    }

    public ArrayList<KhuVucKhoDTO> search(String txt, String type) {
        ArrayList<KhuVucKhoDTO> result = new ArrayList<>();
        txt = txt.toLowerCase();
        switch (type) {
            case "Tất cả" -> {
                for (KhuVucKhoDTO i : listKVKDAO) {
                    if (Integer.toString(i.getMakhuvuc()).contains(txt)
                            || i.getTenkhuvuc().toLowerCase().contains(txt)) {
                        result.add(i);
                    }
                }
            }
            case "Mã khu vực kho" -> {
                for (KhuVucKhoDTO i : listKVKDAO) {
                    if (Integer.toString(i.getMakhuvuc()).contains(txt)) {
                        result.add(i);
                    }
                }
            }
            case "Tên khu vực kho" -> {
                for (KhuVucKhoDTO i : listKVKDAO) {
                    if (i.getTenkhuvuc().toLowerCase().contains(txt)) {
                        result.add(i);
                    }
                }
            }
        }
        return result;
    }

    public String[] getArrTenKhuVuc() {
        int size = listKVKDAO.size();
        String[] result = new String[size];
        for (int i = 0; i < size; i++) {
            result[i] = listKVKDAO.get(i).getTenkhuvuc();
        }
        return result;
    }

    public String getTenKhuVuc(int makhuvuc) {
        return this.listKVKDAO.get(this.getIndexByMaKVK(makhuvuc)).getTenkhuvuc();
    }
}
