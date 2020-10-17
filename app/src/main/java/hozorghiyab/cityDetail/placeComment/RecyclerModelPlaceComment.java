package hozorghiyab.cityDetail.placeComment;

public class RecyclerModelPlaceComment {
    private String username;
    private String name;
    private String picture;
    private String comment;
    public RecyclerModelPlaceComment(String username,String name ,String picture, String comment) {
        this.username = username;
        this.name = name;
        this.picture = picture;
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public String getComment() {
        return comment;
    }


}