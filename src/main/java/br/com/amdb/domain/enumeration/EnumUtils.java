package br.com.amdb.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
enum EnumUtils {
    SPACE(" "),
    ZERO("0"),
    NONE("");
    private final String value;
}
