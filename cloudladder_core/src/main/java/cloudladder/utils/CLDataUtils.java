//package cloudladder.utils;
//
//import cloudladder.core.runtime.data.*;
//import cloudladder.core.runtime.env.CLRtEnvironment;
//import com.google.gson.*;
//import com.google.gson.reflect.TypeToken;
//
//import java.lang.reflect.Type;
//import java.util.HashMap;
//import java.util.Map;
//
//public class CLDataUtils {
//    private static class CLDataAdapter implements JsonSerializer<CLData>, JsonDeserializer<CLData> {
//        private CLRtEnvironment env = null;
//
//        private CLDataAdapter(CLRtEnvironment env) {
//            this.env = env;
//        }
//
//        private CLDataAdapter() {}
//
//        @Override
//        public CLData deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
//            if (jsonElement.isJsonPrimitive()) {
//                JsonPrimitive j = jsonElement.getAsJsonPrimitive();
//                if (j.isBoolean()) {
//                    return env.getBool(j.getAsBoolean());
//                } else if (j.isNumber()) {
//                    double number = j.getAsNumber().doubleValue();
//                    return env.newNumber(number);
//                } else if (j.isString()) {
//                    String value = j.getAsString();
//                    return env.newString(value);
//                }
//            } else if (jsonElement.isJsonArray()) {
//                CLArray arr = env.newArray();
//
//                JsonArray jsonArray = jsonElement.getAsJsonArray();
//                for (int i = 0; i < jsonArray.size(); i++) {
//                    JsonElement ele = jsonArray.get(i);
//                    CLData data = jsonDeserializationContext.deserialize(ele, type);
//                    arr.addNumberRefer(data.wrap());
//                }
//
//                return arr;
//            } else if (jsonElement.isJsonObject()) {
//                CLObject obj = env.newObject();
//
//                JsonObject jsonObject = jsonElement.getAsJsonObject();
//                for(Map.Entry<String, JsonElement> entry: jsonObject.entrySet()) {
//                    CLData data = jsonDeserializationContext.deserialize(entry.getValue(), type);
//                    obj.addStringRefer(entry.getKey(), data.wrap());
//                }
//
//                return obj;
//            }
//
//            return null;
//        }
//
//        @Override
//        public JsonElement serialize(CLData src, Type typeOfSrc, JsonSerializationContext context) {
//            if (src instanceof CLNumber) {
//                return new JsonPrimitive(((CLNumber) src).getValue());
//            } else if (src instanceof CLBoolean) {
//                return new JsonPrimitive(((CLBoolean) src).getValue());
//            } else if (src instanceof CLString) {
//                return new JsonPrimitive(((CLString) src).getValue());
//            } else if (src instanceof CLObject) {
//                JsonObject obj = new JsonObject();
//                HashMap<String, CLReference> items = src.getStringRefers();
//                for (String key : items.keySet()) {
//                    CLData item = items.get(key).getReferee();
//                    obj.add(key, context.serialize(item));
//                }
//                return obj;
//            } else if (src instanceof CLArray) {
//                JsonArray arr = new JsonArray();
//                for (CLReference item : src.getNumberRefers()) {
//                    arr.add(context.serialize(item.getReferee()));
//                }
//                return arr;
//            } else if (src instanceof CLFunction) {
//                return new JsonPrimitive("[[function]]");
//            }
//            return JsonNull.INSTANCE;
//        }
//    }
//
//    public static String toJson(CLData data) {
//        if (data == null) {
//            return "";
//        }
//        GsonBuilder builder = new GsonBuilder();
//        CLDataAdapter adapter = new CLDataAdapter();
//        builder.registerTypeHierarchyAdapter(CLData.class, adapter);
//
//        Gson gson = builder.create();
//        return gson.toJson(data);
//    }
//
//    public static CLData fromJson(String json, CLRtEnvironment env) {
//        GsonBuilder builder = new GsonBuilder();
//        CLDataAdapter adapter = new CLDataAdapter(env);
//        builder.registerTypeHierarchyAdapter(CLData.class, adapter);
//
//        Gson gson = builder.create();
////        Type type = new TypeToken<CLData>(){}.getType();
//        return gson.fromJson(json, CLData.class);
//    }
//}
