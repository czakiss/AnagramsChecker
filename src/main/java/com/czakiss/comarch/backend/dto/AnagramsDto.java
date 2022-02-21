package com.czakiss.comarch.backend.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AnagramsDto {
    private final String wordA;
    private final String wordB;
}
