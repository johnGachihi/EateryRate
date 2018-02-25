package eatery_database;

public class EateryDB {

    //Table `regularuser`
    public static final String TABLE_REGULARUSER = "regularuser";
    public static class RegularUserTable{
        //columns
        public static final String USERNAME = "username";
        public static final String USER_EMAIL = "email";
        public static final String USER_PASSWORD = "passcode";
    }

    //Table `eatery`
    public static final String TABLE_EATERY = "eatery";
    public static class EateryTable{
        //columns
        public static final String EATERY_NAME = TABLE_EATERY + ".eatery_name";
        public static final String EATERY_PASSCODE = TABLE_EATERY + ".eatery_passcode";
        public static final String EATERY_RATING = TABLE_EATERY + ".rating_ave";
        public static final String EATERY_LOCATION_ID = TABLE_EATERY + ".location_id";
        public static final String EATERY_FOOD_DESCRIPTION = TABLE_EATERY + ".food_description";
    }

    public static final String TABLE_LOCATION = "location";
    public static class LocationTable{
        //columns
        public static final String LOCATION_ID = TABLE_LOCATION + ".location_id";
        public static final String LATITUDE = TABLE_LOCATION + ".latitude";
        public static final String LONGITUDE = TABLE_LOCATION + ".longitude";
        public static final String EATERY_NAME = TABLE_LOCATION + ".eatery_name"; //Not required!!
    }
}
