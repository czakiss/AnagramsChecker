package com.czakiss.comarch.backend.service;

import com.czakiss.comarch.backend.ComarchExerciseBackendApplication;
import com.czakiss.comarch.backend.dto.AnagramsDto;
import com.czakiss.comarch.backend.dto.AnagramsResponseDto;
import com.czakiss.comarch.backend.dto.AnagramsStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class AnagramsService {
    private final static String FAILED_VALIDATION_NOT_SAME_LENGTH = "Words doesn't have the same length of characters";
    private final static String FAILED_VALIDATION_NOT_SAME_CHARACTERS = "Words doesn't have the same characters";
    private final static String FAILED_VALIDATION_THE_SAME_WORDS = "Words are the same";
    private final static String FAILED_VALIDATION_NOT_IN_DICTIONARY_A = "Word A doesn't exists in dictionary";
    private final static String FAILED_VALIDATION_NOT_IN_DICTIONARY_B = "Word B doesn't exists in dictionary";
    private final static String SUCCESSFUL_VALIDATION = "Successful validation";

    private final static List<String> dictionary = new ArrayList<>();

    Logger logger = LoggerFactory.getLogger(ComarchExerciseBackendApplication.class);


    public AnagramsService(){
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("dictionary.txt");
        if(inputStream != null){
            Scanner scanner = new Scanner(inputStream);
            logger.info("-- Loading dictionary. It will take a while... ");
            while(scanner.hasNextLine()) {
                String arrayWordString = scanner.nextLine();
                String[] arrayWord = arrayWordString.split(", ");
                List<String> stringList = Arrays.stream(arrayWord).map(String::toLowerCase).collect(Collectors.toList());
                dictionary.addAll(stringList);
            }
            logger.info("-- Loading dictionary completed. ");
        }

    }

    public AnagramsResponseDto validate(AnagramsDto anagramsDto){
        String wordA = anagramsDto.getWordA();
        String wordB = anagramsDto.getWordB();
        if( wordA.length() == wordB.length() ){
            if(wordA.equals(wordB)){
                return AnagramsResponseDto
                        .builder()
                        .anagramsDto(anagramsDto)
                        .anagramsStatus(AnagramsStatus.Validation.FAILED)
                        .message(FAILED_VALIDATION_THE_SAME_WORDS)
                        .build();
            }
            if(!checkInDictionary(wordA)){
                return AnagramsResponseDto
                        .builder()
                        .anagramsDto(anagramsDto)
                        .anagramsStatus(AnagramsStatus.Validation.FAILED)
                        .message(FAILED_VALIDATION_NOT_IN_DICTIONARY_A)
                        .build();
            }
            if(!checkInDictionary(wordB)){
                return AnagramsResponseDto
                        .builder()
                        .anagramsDto(anagramsDto)
                        .anagramsStatus(AnagramsStatus.Validation.FAILED)
                        .message(FAILED_VALIDATION_NOT_IN_DICTIONARY_B)
                        .build();
            }
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
    private boolean checkInDictionary(String word){
        return dictionary.contains(word);
    }
}
