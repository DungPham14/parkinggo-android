package parkinggo.com.constants;

public interface Constants {
    // Database
    long DATABASE_VERSION =1;

    //Net
    int FAIL_CONNECT_CODE = -1;
    int JSON_PARSER_CODE = -10;
    int OTHER_CODE = -20;
    int HTTP_AUTHENTICATION = 401;
    String HTTP_API_VERSION_HEADER_KEY = "HTTP-X-API-VERSION";
    String UER_AGENT_HEADER_KEY="User-Agent";
    String UER_AGENT_HEADER_VALUE= "Android";
    String HTTP_API_TOKEN_HEADER_KEY = "HTTP-X-API-TOKEN";
    String IC_NUMBER_HEADER_KEY = "ic-number";
    String EMAIL_HEADER_KEY = "email";

    // message default
    String FAIL_CONNECT_MESSAGE = "Fail to connect to server";

}
