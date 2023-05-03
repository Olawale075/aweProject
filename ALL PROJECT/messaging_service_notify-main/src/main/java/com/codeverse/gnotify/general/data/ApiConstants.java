package com.codeverse.gnotify.general.data;

import org.springframework.http.MediaType;

public class ApiConstants {

    public static final String MEDIA_TYPE_OCTET_STREAM = MediaType.APPLICATION_OCTET_STREAM_VALUE;
    public static final String MEDIA_TYPE_JSON = MediaType.APPLICATION_JSON_VALUE;
    public static final String BASE_URL_V1 = "/v1";
    public static final String SMS_URL = BASE_URL_V1 + "/sms";
    public static final String SMS_MESSAGE_BASE_URL = SMS_URL + "/message";
    public static final String SMS_TEMPLATE_BASE_URL = SMS_URL + "/template";
    public static final String VARIABLE_TEMPLATE_BASE_URL = BASE_URL_V1 + "/variable";
    public static final String INFOBIP_BASE_URL = "/infobip";
    public static final String INFOBIP_SMS_TEMPLATE_BASE_URL = SMS_URL + INFOBIP_BASE_URL;
    public static final String NOTIFICATION_TYPE_BASE_URL = BASE_URL_V1 + "/notification";

    public static final String AUTH_BASE_URL = "/auth";
    public static final String USER_BASE_URL = "/user";
    public static final String ROLE_BASE_URL = "/role";

}
