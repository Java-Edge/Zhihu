package com.nowcoder.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nowcoder on 17/8/4.
 *
 * 简化开发的工具类,传递到velocity时,可以传入任何数据库字段类型的数据,到时直接for循环即可
 */
public class ViewObject {
    private Map<String, Object> objs = new HashMap<String, Object>();
    public void set(String key, Object value) {
        objs.put(key, value);
    }

    public Object get(String key) {
        return objs.get(key);
    }
}
