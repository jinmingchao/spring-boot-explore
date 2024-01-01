package explore.springboot.data.transaction.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;


public class ControllerResultGenerator<T extends Object, RS extends Collection> {

    public JSONObject generateResultObject(RS resultSet) {
        try {
            JSONObject rs = null;
            if (!(resultSet instanceof java.util.List) && !(resultSet instanceof java.util.Map)) {
                return null;
            }

            rs = new JSONObject();
            rs.put("code", 200);
            rs.put("msg", "success");

            if (resultSet instanceof java.util.List) {
                JSONArray arr = new JSONArray();
                for (int i = 0; i < arr.size(); ++i) {
                    arr.add(JSONObject.from(((List<?>) resultSet).get(i)));
                }
                rs.put("result", arr);
            }
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
