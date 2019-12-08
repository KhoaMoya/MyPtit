package com.khoa.myptit.xemdiem.util;

/*
 * Created at 10/21/19 10:29 PM by Khoa
 */

import android.content.Context;
import android.util.Log;

import androidx.databinding.ObservableInt;

import com.github.mikephil.charting.data.PieEntry;
import com.khoa.myptit.login.net.Downloader;
import com.khoa.myptit.login.repository.BaseRepository;
import com.khoa.myptit.xemdiem.model.DiemHocKy;
import com.khoa.myptit.xemdiem.model.DiemMonHoc;
import com.khoa.myptit.xemdiem.viewmodel.XemDiemViewModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.TreeMap;

public class ParseDiem {

    public static ArrayList<DiemHocKy> convertToListHocKy(Context context, Downloader downloader){

        Document document = Jsoup.parse(downloader.getResponse().body());
        Element table = document.select("table[class=view-table]").first();

        ArrayList<DiemHocKy> listDiemHocKy = new ArrayList<DiemHocKy>();

        Elements allElement = table.select("tr");
        DiemHocKy diemHocKy=null;
        for(Element element : allElement){
            if(element.toString().contains("class=\"title-hk-diem\"")) {
                Log.e("Loi", element.text());
                if(diemHocKy!=null) listDiemHocKy.add(diemHocKy);
                diemHocKy = new DiemHocKy(element.text());
            }

            if(element.toString().contains("class=\"row-diem\"")){
                Elements columns = element.select("td");
                DiemMonHoc diemMonHoc = new DiemMonHoc(columns.get(1).text().replace("\u00a0",""),
                                        columns.get(2).text().replace("\u00a0",""),
                                        columns.get(3).text().replace("\u00a0",""),
                                        columns.get(13).text().replace("\u00a0",""),
                                        columns.get(15).text().replace("\u00a0",""),
                                        columns.get(16).text().replace("\u00a0",""));
                diemHocKy.getListMonHoc().add(diemMonHoc);
            }

            if(element.toString().contains("class=\"row-diemTK\"")){
                Elements columns = element.select("span");
//                if(columns.get(0).text().equals("Điểm trung bình học kỳ hệ 10/100:")) diemHocKy.setDiemTB10(columns.get(1).text());
                if(columns.get(0).text().equals("Điểm trung bình học kỳ hệ 4:")) diemHocKy.setDiemTB4(columns.get(1).text());
//                if(columns.get(0).text().equals("Điểm trung bình tích lũy:")) diemHocKy.setDiemTBTichLuy10(columns.get(1).text());
                if(columns.get(0).text().equals("Điểm trung bình tích lũy (hệ 4):")) diemHocKy.setDiemTBTichLuy4(columns.get(1).text());
                if(columns.get(0).text().equals("Số tín chỉ đạt:")) diemHocKy.setSoTinChiDat(columns.get(1).text());
                if(columns.get(0).text().equals("Số tín chỉ tích lũy:")) diemHocKy.setSoTinChiTichLuy(columns.get(1).text());
            }
        }

        if(!listDiemHocKy.contains(diemHocKy)) listDiemHocKy.add(diemHocKy);

        if(!listDiemHocKy.isEmpty()) {
            new BaseRepository<ArrayList<DiemHocKy>>().write(context, DiemMonHoc.mFileName, listDiemHocKy);
            return listDiemHocKy;
        }

        return null;
    }

    public static void convertToTreeMapMonHoc(XemDiemViewModel viewModel, ArrayList<DiemHocKy> hocKyArrayList){
        viewModel.mListHocLai.clear();
        viewModel.mListHocCaiThien.clear();
        viewModel.mTreeMapMonHoc = new TreeMap<>();
        for (DiemHocKy diemHocKy : hocKyArrayList) {
            for (DiemMonHoc diemMonHoc : diemHocKy.getListMonHoc()) {
                String tenMonHoc = diemMonHoc.getTenMonHoc();
                if(viewModel.mTreeMapMonHoc.containsKey(tenMonHoc)){
                    DiemMonHoc monHoc = viewModel.mTreeMapMonHoc.get(tenMonHoc);
                    if(monHoc.getTK4().equals("F")) viewModel.mListHocLai.add(diemMonHoc);
                    else viewModel.mListHocCaiThien.add(diemMonHoc);
                }
                viewModel.mTreeMapMonHoc.put(tenMonHoc, diemMonHoc);
            }
        }
    }

    public static TreeMap<String, ArrayList> convertToTreeMapDiem(TreeMap<String, DiemMonHoc> treeMapMonHoc){
        TreeMap<String, ArrayList> treeMapDiem4 = new TreeMap<>();
        for (DiemMonHoc diemMonHoc : treeMapMonHoc.values()) {
            String diemChu = diemMonHoc.getTK4();
            if (diemChu.isEmpty()) continue;
            if (treeMapDiem4.containsKey(diemChu)) {
                ArrayList<DiemMonHoc> list = treeMapDiem4.get(diemChu);
                list.add(diemMonHoc);
            } else {
                ArrayList<DiemMonHoc> list = new ArrayList<>();
                list.add(diemMonHoc);
                treeMapDiem4.put(diemChu, list);
            }
        }
        return treeMapDiem4;
    }

    public static ArrayList<PieEntry> convertToPieEntries(TreeMap<String, ArrayList> treeMapDiem){
        ArrayList<PieEntry> entries = new ArrayList<>();
        for (String key : treeMapDiem.keySet()) {
            entries.add((new PieEntry(treeMapDiem.get(key).size(), key)));
        }
        return entries;
    }
}
