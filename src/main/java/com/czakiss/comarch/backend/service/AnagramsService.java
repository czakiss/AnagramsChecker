package com.czakiss.comarch.backend.service;

import com.czakiss.comarch.backend.dto.AnagramsDto;
import com.czakiss.comarch.backend.dto.AnagramsResponseDto;
import com.czakiss.comarch.backend.dto.AnagramsStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class AnagramsService {
    private final static String FAILED_VALIDATION_NOT_SAME_LENGTH = "Words doesn't have the same length of characters";
    private final static String FAILED_VALIDATION_NOT_SAME_CHARACTERS = "Words doesn't have the same characters";
    private final static String SUCCESSFUL_VALIDATION = "Successful validation";


    public AnagramsResponseDto validate(AnagramsDto anagramsDto){
        String wordA = anagramsDto.getWordA();
        String wordB = anagramsDto.getWordB();
        if( wordA.length() == wordB.length() ){
            char[] charsWordA = anagramsDto.getWordA().toLowerCase().toCharArray();
            char[] charsWordB = anagramsDto.getWordB().toLowerCase().toCharArray();
            Arrays.sort(charsWordA);
            Arrays.sort(charsWordB);
            if(Arrays.equals(charsWordA,charsWordB)){
                return AnagramsResponseDto
                        .builder()
                        .anagramsDto(anagramsDto)
                        .anagramsStatus(AnagramsStatus.Validation.SUCCESSFUL)
                        .message(SUCCESSFUL_VALIDATION)
                        .build();
            } else {
                return AnagramsResponseDto
                        .builder()
                        .anagramsDto(anagramsDto)
                        .anagramsStatus(AnagramsStatus.Validation.FAILED)
                        .message(FAILED_VALIDATION_NOT_SAME_CHARACTERS)
                        .build();
            }
        } else {
            return AnagramsResponseDto
                    .builder()
                    .anagramsDto(anagramsDto)
                    .anagramsStatus(AnagramsStatus.Validation.FAILED)
                    .message(FAILED_VALIDATION_NOT_SAME_LENGTH)
                    .build();
        }


    }
}
