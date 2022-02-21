package com.czakiss.comarch.backend.rest;

import com.czakiss.comarch.backend.dto.AnagramsDto;
import com.czakiss.comarch.backend.dto.AnagramsResponseDto;
import com.czakiss.comarch.backend.dto.AnagramsStatus;
import com.czakiss.comarch.backend.service.AnagramsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AnagramsController {

    @Autowired
    private AnagramsService anagramsService;

    @CrossOrigin
    @GetMapping(value = "/check-anagrams")
    public ResponseEntity<AnagramsResponseDto> checkAnagrams(@RequestBody AnagramsDto anagramsDto){
        return new ResponseEntity<AnagramsResponseDto>(anagramsService.validate(anagramsDto), HttpStatus.OK);
    }

}
