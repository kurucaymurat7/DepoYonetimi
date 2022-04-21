package depopackage;

import jdk.swing.interop.SwingInterOpUtils;

import java.util.*;

public class Methods {

    static Map<Integer, List> urunlerMap = new TreeMap<Integer, List>();
    static Scanner scan = new Scanner(System.in);
    static Random rnd = new Random();

    public static void giris(){
        while (true) {
            System.out.println("DEPO YÖNETİMİ");
            System.out.println("Aşağıdaki menuden bir seçim yapınız.");
            System.out.printf("%-10s\n %-10s\n %-10s\n %-10s\n %-10s\n %-10s\n",
                    " Ürün Tanımlama (1)", "Ürün Girisi (2)", "Ürün Listele (3)", "Ürünü Rafa Koy (4)", "Ürün Cikisi (5)", "Bitir (0)");
            System.out.print("Secim : ");
            int secim = 0;
            try {
                secim = scan.nextInt();
            } catch (Exception e) {
                String str = scan.nextLine();
                System.out.println("Hatali giris yapildi.");
            }
            switch (secim) {
                case 1:
                    urunTanimla();
                    break;
                case 2:
                    urunGirisi();
                    break;
                case 3:
                    urunListele();
                    break;
                case 4:
                    urunuRafaKoy();
                    break;
                case 5:
                    urunCikisi();
                    break;
                case 0:
                    cikis();
                default:
                    System.out.println("Hatali giris yaptiniz.");
                    break;
            }
        }
    }

    public static void urunTanimla() {
        while (true) {
            System.out.println("Bitirmek için X'e basiniz. ");
            System.out.println("Urun bilgileri giriniz : ");
            int urunID = rnd.nextInt(1000);
            String urunIsmi = "";
            try {
                System.out.print("Urun ismi :");
                urunIsmi = scan.next();
                if (urunIsmi.equalsIgnoreCase("X")) {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            scan.nextLine();//dummy
            System.out.print("Uretici Firma : ");
            String ureticiFirma = scan.nextLine();
            Urun yeniUrun = new Urun(urunID, urunIsmi,ureticiFirma);
            List<Urun> urunTanimlaList = new ArrayList<>();
            urunTanimlaList.add(yeniUrun);
            urunlerMap.put(yeniUrun.getId(), urunTanimlaList);
        }
        urunListele();
    }


    public static void urunListele() {

        System.out.printf("%-9s %-10s %-10s %-10s %-10s %-10s\n", "ID", " Ürün", " Üretici", "Miktar", "Cuval", "Raf");
        System.out.println("====================================================================================");
        Set<Integer> keySets = urunlerMap.keySet();
        Collection<List> valueSet = urunlerMap.values();
        for (Integer each : keySets) {
            List<Urun> valuesList = new ArrayList<>();
            valuesList = urunlerMap.get(each);
            int index = 0;
            while (index < valuesList.size()) {
                System.out.printf("%-9s %-10s %-10s %-10d %-10s %-10s\n", each, valuesList.get(index).getUrunIsmi(), valuesList.get(index).getUretici(),
                        valuesList.get(index).getMiktar(), valuesList.get(index).getBirim(), valuesList.get(index).getRaf());
                index++;
            }
        }
    }

    public static void urunGirisi() {
        System.out.print("Ürün ID giriniz : ");
        int urunID = scan.nextInt();
        System.out.print("Ürün girisi miktar : " );
        int miktar = scan.nextInt();
        Set<Integer> keySets = urunlerMap.keySet(); // keyler
        Collection<List> valueSet = urunlerMap.values(); // value'lar List olarak
        for (Integer each : keySets)  {
            if (each == urunID) {
                List<Urun> valuesList = new ArrayList<>();
                valuesList = urunlerMap.get(each);
                valuesList.get(0).setMiktar(miktar);
                Urun UrunMiktarEklendi = new Urun(each,valuesList.get(0).getUrunIsmi(),valuesList.get(0).getUretici(),valuesList.get(0).getMiktar(),
                        valuesList.get(0).getBirim(), valuesList.get(0).getRaf());
                List<Urun> urunTanimlaList = new ArrayList<>();
                urunTanimlaList.add(UrunMiktarEklendi);
                urunlerMap.put(each, urunTanimlaList);
            }
        }
        urunListele();
    }

    public static void urunuRafaKoy() {
        System.out.print("Ürün ID giriniz : ");
        int urunID = scan.nextInt();
        System.out.print("Raf : " );
        String raf = scan.next();
        Set<Integer> keySets = urunlerMap.keySet(); // keyler
        Collection<List> valueSet = urunlerMap.values(); // value'lar List olarak
        for (Integer each : keySets)  {
            if (each == urunID) {
                List<Urun> valuesList = new ArrayList<>();
                valuesList = urunlerMap.get(each);
                valuesList.get(0).setRaf(raf);
                Urun UrunRafaEklendi = new Urun(each,valuesList.get(0).getUrunIsmi(),valuesList.get(0).getUretici(),valuesList.get(0).getMiktar(),
                        valuesList.get(0).getBirim(), valuesList.get(0).getRaf());
                List<Urun> urunTanimlaList = new ArrayList<>();
                urunTanimlaList.add(UrunRafaEklendi);
                urunlerMap.put(each, urunTanimlaList);
            }
        }
        urunListele();
    }

    public static void urunCikisi() {
        System.out.print("Ürün ID giriniz : ");
        int urunID = scan.nextInt();
        int miktar;
        Set<Integer> keySets = urunlerMap.keySet(); // keyler
        Collection<List> valueSet = urunlerMap.values(); // value'lar List olarak
        for (Integer each : keySets)  {
            if (each == urunID) {
                List<Urun> valuesList = new ArrayList<>();
                valuesList = urunlerMap.get(each);

                while (true) {
                    System.out.print("Ürün cikisi miktar : " );
                    miktar = scan.nextInt();
                    if (miktar > valuesList.get(0).getMiktar()) {
                        System.out.println("Depoda sadece " + valuesList.get(0).getMiktar() + " " + valuesList.get(0).getBirim() + " " +
                                valuesList.get(0).getUrunIsmi() + " bulunmaktadır.");

                    } else {
                        valuesList.get(0).setMiktar(miktar*(-1));
                        break;
                    }
                }

                Urun urunMiktarEksildi = new Urun(each,valuesList.get(0).getUrunIsmi(),valuesList.get(0).getUretici(),valuesList.get(0).getMiktar(),
                        valuesList.get(0).getBirim(), valuesList.get(0).getRaf());
                List<Urun> urunTanimlaList = new ArrayList<>();
                urunTanimlaList.add(urunMiktarEksildi);
                urunlerMap.put(each, urunTanimlaList);
            }
        }
        urunListele();
    }

    public static void cikis() {
        System.out.println("Çıkış yapılıyor. İyi günler...");
    }
}
