package models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AgeLimit {
    _3(3),
    _7(7),
    _12(12),
    _16(16),
    _18(18);

    private final Integer value;
}
