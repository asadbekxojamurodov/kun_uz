package com.example.controller;

import com.example.dto.AttachDTO;
import com.example.service.AttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/attach")
public class AttachController {

    @Autowired
    private AttachService attachService;

//    @PostMapping("/upload")
//    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
//        String fileName = attachService.saveToSystem(file);
//        return ResponseEntity.ok().body(fileName);
//    }

    @PostMapping("/upload")
    public ResponseEntity<AttachDTO> upload(@RequestParam("file") MultipartFile file) {
        AttachDTO dto = attachService.save(file);
        return ResponseEntity.ok().body(dto);
    }

    @GetMapping(value = "/open/{fileName}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] open(@PathVariable("fileName") String fileName) {
        if (fileName != null && fileName.length() > 0) {
            try {
                return this.attachService.loadImage(fileName);
            } catch (Exception e) {
                e.printStackTrace();
                return new byte[0];
            }
        }
        return null;
    }

    @GetMapping(value = "/open_general/{fileName}", produces = MediaType.ALL_VALUE)
    public byte[] open_general(@PathVariable("fileName") String fileName) {
        return attachService.open_general(fileName);
    }

//    @GetMapping("/download/{fineName:.+}")
//    public ResponseEntity<Resource> download(@PathVariable("fineName") String fileName) {
//        Resource file = attachService.download(fileName);
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
//    }

//    @GetMapping("/download/{attachId}")
//    public ResponseEntity<byte[]> download(@PathVariable("attachId") String attachId) {
//        byte[] fileData = attachService.loadImage(attachId);
//
//        if (fileData != null) {
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//            headers.setContentDispositionFormData("attachment", attachId);
//
//            return new ResponseEntity<>(fileData, headers, HttpStatus.OK);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @GetMapping("/download/{fineName}")
    public ResponseEntity<Resource> download(@PathVariable("fineName") String fileName) {
        return attachService.download(fileName);
    }


}
