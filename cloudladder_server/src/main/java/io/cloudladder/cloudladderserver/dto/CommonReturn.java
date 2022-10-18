package io.cloudladder.cloudladderserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonReturn {
    private boolean ok;
    private String message;

    public static CommonReturn ok() {
        return new CommonReturn(true, null);
    }

    public static CommonReturn error(String message) {
        return new CommonReturn(false, message);
    }
}
