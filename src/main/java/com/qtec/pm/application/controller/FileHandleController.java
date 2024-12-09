package com.qtec.pm.application.controller;


import com.qtec.pm.application.service.FileHandlerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileHandleController {

    @Autowired
    private FileHandlerService fileHandlerService;

    @PostMapping("/upload-file")
    public String fileUpload(@RequestParam("file") MultipartFile file){
        return fileHandlerService.fileHandler(file);
    }

}
