package hozorghiyab.listCityACT;

import java.util.ArrayList;

public class RefreshDb {

    public static void refresh(Database db,Contacts contacts,ArrayList<Contacts> users){
        db.open();
        int save = db.shomaresh_field("tbl_citys", "schoolName");
        for (int i = 0; i < save; i++) {

            String schoolName = db.namayesh_fasl2("tbl_citys", i,1);
            String className = db.namayesh_fasl2("tbl_citys", i,2);
            contacts = new Contacts(schoolName,className);
            users.add(contacts);
        }

        db.close();
    }

}
