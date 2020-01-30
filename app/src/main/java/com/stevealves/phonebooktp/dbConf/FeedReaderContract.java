package com.stevealves.phonebooktp.dbConf;

import android.provider.BaseColumns;

public class FeedReaderContract {

    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private FeedReaderContract() {}

    /* Inner class that defines the table contents */
    public static class FeedEntry implements BaseColumns {

        public static final String TABLE_NAME = "contactos";

        public static final String COLUMN_NOME = "nome";
        public static final String COLUMN_PHONE = "phone_number";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_BIRTHDAY = "birthday";
        public static final String COLUMN_PHOTO = "photo";
        public static final String COLUMN_LATITUDE= "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_FAV= "favorite";

    }


}
