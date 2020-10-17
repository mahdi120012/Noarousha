package hozorghiyab.cityDetail;

import java.util.ArrayList;

public class SectionDataModelRecyclerModel {


        private String headerTitle;
        private ArrayList<RecyclerModel> allItemsInSection;


        public SectionDataModelRecyclerModel() {

        }
        public SectionDataModelRecyclerModel(String headerTitle, ArrayList<RecyclerModel> allItemsInSection) {
            this.headerTitle = headerTitle;
            this.allItemsInSection = allItemsInSection;
        }



        public String getHeaderTitle() {
            return headerTitle;
        }

        public void setHeaderTitle(String headerTitle) {
            this.headerTitle = headerTitle;
        }

        public ArrayList<RecyclerModel> getAllItemsInSection() {
            return allItemsInSection;
        }

        public void setAllItemsInSection(ArrayList<RecyclerModel> allItemsInSection) {
            this.allItemsInSection = allItemsInSection;
        }




}
