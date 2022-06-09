package models;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class Text {
    String message;

    @Override
    public String toString() {
        return message;
    }
}
