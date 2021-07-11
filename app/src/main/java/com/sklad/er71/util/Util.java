package com.sklad.er71.util;

import android.text.TextUtils;
import android.util.Patterns;

import com.sklad.er71.Enum.TabBarItem;
import com.sklad.er71.R;

import java.util.ArrayList;
import java.util.List;

public class Util {

    public static final String NAMESPACE = "http://localhost/wsdlo";
    public static final String METHODNAME = "GetRecipe_SNILS";
    public static final String METHODNAME_NEXT = "ResiduesPharm";
    public static final String WSDL = "http://87.244.7.142:9000/WebServicesDLO/ws/GetRecipeSNILS.1cws";
    public static final String SOAP_ACTION = NAMESPACE + "#kltro:" + METHODNAME;
    public static final String SOAP_ACTION_NEXT = NAMESPACE + "#kltro:" + METHODNAME_NEXT;
    public static final String USER_PASSWORD = "webtest:777111";

    public static String TAG = "soap";

    public static boolean isValidEmail(String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static List<TabBarItem> generateTabBar() {
        List<TabBarItem> TAB_BAR_LIST = new ArrayList<>();
        TAB_BAR_LIST.add(
                new TabBarItem(R.id.image_item_1, R.id.text_item_1,
                        R.drawable.ic_home_no_active,
                        R.drawable.ic_home));
        TAB_BAR_LIST.add(
                new TabBarItem(R.id.image_item_2, R.id.text_item_2,
                        R.drawable.ic_prescription_no_active,
                        R.drawable.ic_prescription));
        TAB_BAR_LIST.add(
                new TabBarItem(R.id.image_item_3, R.id.text_item_3,
                        R.drawable.ic_user_no_active,
                        R.drawable.ic_user));
        return TAB_BAR_LIST;
    }
}
