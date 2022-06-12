package models;

import intarfaces.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Storage implements Entity {
    private final String id = UUID.randomUUID().toString();
    @Setter(value = AccessLevel.NONE)
    private Product product;
    private Integer quantity;
}
