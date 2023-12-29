package explore.springboot.data.transaction.utils;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;


public class ControllerResultGenerator<T extends Object, RS extends Collection, TYPE > {

    public JSONObject generateResultObject(RS resultSet) {
        try {
            if (!(resultSet instanceof java.util.List) && !(resultSet instanceof java.util.Map)) {
                return null;
            }

            JSONObject rs = new JSONObject();
            rs.put("code", 200);
            rs.put("msg", "success");

            if (resultSet instanceof java.util.List) {

            }



            try {
                if (clazz instanceof com.alibaba.fastjson2.JSONArray) {
                    JSONArray tmp = new JSONArray();
                    tmp.add(new JSONObject().put("code", 200));
                    resultObject = tmp;
                } else if (resultObject instanceof com.alibaba.fastjson2.JSONObject) {
                    resultObject = new JSONObject().put("code", 200);
                } else {
                    return null;
                }
                return resultObject;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }
}
