package com.pepop99.emaildispatcher.handlers;

import com.pepop99.emaildispatcher.metadata.GrantMeta;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class CSVData {
    final String[] headers;
    final ArrayList<GrantMeta> data = new ArrayList<>();

    public CSVData(String[] headers) {
        this.headers = headers;
    }

    public void addEntry(GrantMeta meta) {
        data.add(meta);
    }

    public JSONObject fetchAllData() {
        final JSONObject op = new JSONObject();
        final ArrayList<JSONObject> dataArray = new ArrayList<>();
        for (GrantMeta meta : data) {
            JSONObject jsonObject = new JSONObject();
            for (String header : headers) {
                String val;
                if (StringUtils.equalsIgnoreCase(header, "Grant Type")) {
                    val = String.valueOf(meta.getGrantType()).trim();
                } else if (StringUtils.equalsIgnoreCase(header, "tags")) {
                    val = Arrays.toString(meta.getTags()).trim().replace("[", "").replace("]", "");
                } else {
                    val = meta.getOtherData().get(header).trim();
                }
                jsonObject.put(header, val);
            }
            dataArray.add(jsonObject);
        }
        op.put("data", dataArray);
        op.put("headers", Arrays.toString(headers));
        return op;
    }
}
