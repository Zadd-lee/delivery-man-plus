package com.deliveryManPlus.constant;
//todo : url 상수화 리팩터링
public class UrlConst {

    //로그인 필터 화이트 리스트
    public static final String[] WHITE_LIST = {"/api/", "/api/auth/signin", "/api/auth/login", "/api/shop", "/api/shop/*"
    ,"/auth/**"};

    //사장 인터셉터 리스트
    public static final String[] ADMIN_INTERCEPTOR_LIST = {"/api/admin", "/api/admin/**"};

    //사장 인터셉터 리스트
    public static final String[] OWNER_INTERCEPTOR_LIST = {"/api/owner", "/api/owner/**"};

    //손님 인터셉터 리스트
    public static final String[] CUSTOMER_INTERCEPTOR_LIST = {"/api/user", "/api/user/**"};

}
