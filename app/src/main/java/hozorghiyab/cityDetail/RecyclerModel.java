package hozorghiyab.cityDetail;

public class RecyclerModel {
    private String id;
    private String onvan;
    private String matn;
    private String picture;
    private String city;
    private String position;
    private String rate;
    private String countRateAndComment;
    private int idTimeTakhir;
    private String vaziyat;
    private String workNumber;
    private String saatVorodEnglish;
    private String saatKhorojEnglish;
    public RecyclerModel(String id,String onvan,String matn ,String picture, String city,
                         String position,String rate,String countRateAndComment,int idTimeTakhir,String vaziyat,String workNumber
            ,String saatVorodEnglish, String saatKhorojEnglish) {
        this.id = id;
        this.onvan = onvan;
        this.matn = matn;
        this.picture = picture;
        this.city = city;
        this.position = position;
        this.rate = rate;
        this.countRateAndComment = countRateAndComment;
        this.idTimeTakhir = idTimeTakhir;
        this.vaziyat = vaziyat;
        this.workNumber = workNumber;
        this.saatVorodEnglish = saatVorodEnglish;
        this.saatKhorojEnglish = saatKhorojEnglish;
    }




    public String getId() {
        return id;
    }



    public String getCountRateAndComment() {
        return countRateAndComment;
    }

    public String getOnvan() {
        return onvan;
    }
    public String getMatn() {
        return matn;
    }

    public void setMatn(String matn) {
        this.matn = matn;
    }

    public String getPicture() {
        return picture;
    }

    public String getCity() {
        return city;
    }

    public String getPosition() {
        return position;
    }

    public int getIdTimeTakhir() {
        return idTimeTakhir;
    }

    public String getRate() {
        return rate;
    }

    public String getVaziyat() {
        return vaziyat;
    }

    public String getWorkNumber() {
        return workNumber;
    }

    public String getSaatVorodEnglish() {
        return saatVorodEnglish;
    }

    public String getSaatKhorojEnglish() {
        return saatKhorojEnglish;
    }
}