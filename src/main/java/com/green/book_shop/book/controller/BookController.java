package com.green.book_shop.book.controller;

import com.green.book_shop.book.dto.BookDTO;
import com.green.book_shop.book.dto.ImgDTO;
import com.green.book_shop.book.service.BookService;
import com.green.book_shop.utill.UploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
  private final BookService bookService;
  private final UploadUtil uploadUtil;

  //도서 등록 api
  //post ~/books
  @PostMapping("")
  public void regBook(
          BookDTO bookDTO,
          @RequestParam(name ="mainImg" , required = false) MultipartFile mainImg,
          @RequestParam(name ="subImg" , required = false) MultipartFile subImg
  ){

    //첨부파일(도서 이미지) 업로드
    //mainImg.getOriginalFilename(); // 원본파일명
    //첨부된파일명은 fileUpload()메서드에서 만들어짐
    String mainAttachedFileName = uploadUtil.fileUpload(mainImg);
    String subAttachedFileName = uploadUtil.fileUpload(subImg);


    //다음에 들어갈 BOOK_CODE 조회
    int nextBookCode = bookService.getNextBookCode();

    //BOOK 테이블에 데이터 INSERT
    bookDTO.setBookCode(nextBookCode);
    bookService.insertBook(bookDTO);

    //bookDTO에 이미지 데이터 저장
    List<ImgDTO> imgList = new ArrayList<>();
    ImgDTO mainImgDTO = new ImgDTO();
    mainImgDTO.setOriginFileName(mainImg.getOriginalFilename());
    mainImgDTO.setAttachedFileName(mainAttachedFileName);
    mainImgDTO.setIsMain("Y");
    mainImgDTO.setBookCode(nextBookCode);

    ImgDTO subImgDTO = new ImgDTO();
    subImgDTO.setOriginFileName(subImg.getOriginalFilename());
    subImgDTO.setAttachedFileName(subAttachedFileName);
    subImgDTO.setIsMain("N");
    subImgDTO.setBookCode(nextBookCode);

    imgList.add(mainImgDTO);
    imgList.add(subImgDTO);
    bookDTO.setImgList(imgList);


    //BOOK_IMG 테이블에 도서 이미지 정보 INSERT
    bookService.insertImgs(bookDTO); //bookDTO
  }

  //상품(도서) 목록 조회
  @GetMapping("")
  public ResponseEntity<?> getBookList(){
    //도서 목록 조회 쿼리 실행
    List<BookDTO> bookList = bookService.selectItemList();

    return ResponseEntity
            .status(HttpStatus.OK)
            .body(bookList);

  }

}















