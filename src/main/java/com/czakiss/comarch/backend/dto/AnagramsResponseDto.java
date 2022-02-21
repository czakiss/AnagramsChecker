package com.czakiss.comarch.backend.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AnagramsResponseDto {
    private AnagramsDto anagramsDto;
    private AnagramsStatus.Validation anagramsStatus;
    private String message;
}
