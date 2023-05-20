package br.com.amdb.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Filler {
    SPACES_RIGTH(EnumUtils.SPACE.getValue()),
    SPACES_LEFT(EnumUtils.SPACE.getValue()),
    NONE(EnumUtils.NONE.getValue()),
    ZEROS_RIGTH(EnumUtils.ZERO.getValue()),
    ZEROS_LEFT(EnumUtils.ZERO.getValue());

    private final String value;
}
