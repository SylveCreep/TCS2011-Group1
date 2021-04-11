package com.example.server.constant;

public class Constant {
        public static final int SUCCESS = 1;
        public static final int FAILURE = 0;

        public static final int NOTDELETED = 0;
        public static final int DELETED = 1;

        // Contribution status
        public static final int APPROVED = 1;
        public static final int PENDING = 0;
        public static final int DENIED = -1;

        public static final int MALE = 0;
        public static final int FEMALE = 1;

        // Magazine status
        public static final int OPENNING = 0;
        public static final int PROCESSING = 1;
        public static final int PUBLISHING = 2;
        public static final int CLOSED = 3;

        //Online
        public static final int ONLINE = 1;
        public static final int OFFLINE = 0;

        //Reset password expire date
        public static final int minute = 15;

        //Reset password reset link
        public static final String urlLink = "http://localhost:8080/users/forgotpassword/";

        //Redirect to change password url
        public static final String urlReset = "http://localhost:8081/password/reset/";

        //Email notify contribution
        public static final int JUSTCREATED = 0;
        public static final int NEEDNOTIFY = 1;

        public static String CLIENTURL = "http://localhost:8081";

        //Kafka
        public static final String GROUP_ID = "kafka-sandbox";
        public static final String KAFKA_BROKER = "localhost:9092";
        public static final String KAFKA_TOPIC_COMMENT = "kafka-topic-comment";
        public static final String KAFKA_TOPIC_CHAT = "kafka-topic-chat";


}
