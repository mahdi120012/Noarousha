package hozorghiyab.listCityACT;

public class Contacts {

    private String schoolName,className;

    public Contacts(String schoolName,String className){
        this.setSchoolName(schoolName);
        this.setClassName(className);
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
