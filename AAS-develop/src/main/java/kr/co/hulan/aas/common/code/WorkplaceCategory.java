package kr.co.hulan.aas.common.code;

public enum WorkplaceCategory {
  APARTMENT(1, "아파트"),
  RESIDENTIAL_COMMERCIAL(2, "주상복합"),
  OFFICETEL(3, "오피스텔"),
  TOWN_HOUSE(4, "다세대,연립주택"),
  BUSINESS_FACILITIES(5, "업무시설(관공서,사옥등)"),
  COMMERICAL_FACILITIES(6, "상업시설(상가,백화점,쇼핑몰등)"),
  ACCOMMODATION(7, "숙박시설(호텔,리조트,콘도등)"),
  MEDICAL_FACILITIES(8, "의료시설(병원,요양원등)"),
  WAREHOUSE_FACTORY(9, "창고 및 공장"),
  RELIGIOUS_FACILITIES(10, "종교시설"),
  SPORT_FACILITIES(11, "운동시설(체육관,운동장등)"),
  EDUCATIONAL_RESEARCH_FACILITIES(12, "교육연구시설(학교,연구원등)"),
  MILITARY_INSTALLATION(13, "군사시설"),
  TRANSPORTATION_FACILITIES(14, "운수시설(공항,철도,항만,터미널)"),
  OTHER_FACILITIES(15, "기타시설")
  ;

  private int code;
  private String name;
  WorkplaceCategory(int code, String name){
    this.code = code;
    this.name = name;
  }

  public int getCode() {
    return code;
  }
  public String getName(){
    return name;
  }
  public static WorkplaceCategory get(int code){
    for(WorkplaceCategory item : values()){
      if(code == item.getCode()){
        return item;
      }
    }
    return null;
  }
}
