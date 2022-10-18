package io.cloudladder.cloudladderserver.dto;

import com.google.gson.Gson;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;

@Data
public class CreateImplementationBody {
    private String scope;
    private String name;
    private String from;
    private String type;
    private ArrayList<String> paramNames;
    private boolean catchAll;
    private HashMap<String, Object> data;

    public String getDataAsJson() {
        Gson gson = new Gson();
        return gson.toJson(this.data);
    }
}
