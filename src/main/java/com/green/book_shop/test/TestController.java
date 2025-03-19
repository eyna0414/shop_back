package com.green.book_shop.test;

import com.green.book_shop.book.dto.BookDTO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/test")
public class TestController {

  @GetMapping("/1")
  public int test1(){
    return 5;
  }

  //첨부파일 연습 1
  @PostMapping("/upload1")
  public void upload1(BookDTO bookDTO,
                      @RequestParam("firstFile") MultipartFile file){
    System.out.println(bookDTO);
    System.out.println("첨부된 원본 파일명 : " + file.getOriginalFilename());

    //업로드될 경로
    String uploadPath ="D:\\01-STUDY\\devel\\ShopProject\\backEnd\\book_shop\\src\\main\\resources\\upload\\";

    //첨부한 원본 파일명
    String attachedFileName = file.getOriginalFilename();

    //업로드 경로, 파일명을 연결
    File f = new File(uploadPath + attachedFileName);

    try {
      //파일 첨부
      file.transferTo(f);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }
}










