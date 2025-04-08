package com.green.book_shop.book.controller;

import com.green.book_shop.book.dto.BookCategoryDTO;
import com.green.book_shop.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class BookCategoryController {
  private final BookService bookService;

  //카테고리 목록 조회 api
  //get  ~/categories
  @GetMapping("")
  public ResponseEntity<?> getCateList(){
    try {
      //조회가 안되면 list는 null 아님, list.size() == 0
      List<BookCategoryDTO> list = bookService.selectCategoryList();

      return ResponseEntity
              .status(HttpStatus.OK)
              .body(list);
    }catch (Exception e){
      e.printStackTrace();  //오류가 발샌한 위치 및 이유를 알려줌
//      return ResponseEntity
//              .status(HttpStatus.INTERNAL_SERVER_ERROR)
//              .body("카테고리 목록 조회 중 서버 오류 발생");

      return ResponseEntity
              //body에 전달할 데이터가 없을 때 build() 메서드 호출로 마무리
              .status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

  }

  //카테고리 등록 api
  //post ~/categories
  @PostMapping("")
  public ResponseEntity<?> insertCategory(@RequestBody BookCategoryDTO bookCategoryDTO){
    //등록 성공 -> 1 리턴
    //등록 안했으면 -> 0 리턴
    int result = bookService.insertCategory(bookCategoryDTO.getCateName());

    return ResponseEntity
            .status(result == 1 ? HttpStatus.CREATED : HttpStatus.INTERNAL_SERVER_ERROR) //.CREATED -> 201번 리소스 생성 성공
            .body(result == 1 ? result : "알 수 없는 이유로 등록이 되지 않았습니다");
  }

  @GetMapping("/test1")
  public ResponseEntity<String> test1(){
    return ResponseEntity.status(HttpStatus.OK).body("java");
  }

  @GetMapping("/test2")
  public ResponseEntity<Integer> test2(){
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(10);
  }

  @GetMapping("/test3")
  public ResponseEntity<List<BookCategoryDTO>> test3(){
    List<BookCategoryDTO> list = bookService.selectCategoryList();

    return ResponseEntity
            .status(HttpStatus.OK)
            .body(list);
  }

  @GetMapping("/test4")
  public ResponseEntity<String> test4(){
    HttpHeaders headers = new HttpHeaders();
    headers.add("myName", "hong");
    //문자만 가능! list dto 불가
    headers.add("myAge", "20");

    return ResponseEntity
            .status(HttpStatus.OK)
            .headers(headers)
            .body("java");
  }

  @GetMapping("/test5/{cateCode}")
  //? -> 제네릭의 와일드카드 :어떤 문법이라도 다 받겠다
  public ResponseEntity<?> test5(@PathVariable("cateCode") int cateCode){


    try{
      //예외가 날 수 있는 코드 작성
      BookCategoryDTO resultDTO = bookService.getCategoryByCateCode(cateCode);

      return ResponseEntity
              .status(resultDTO == null ? HttpStatus.NOT_FOUND : HttpStatus.OK)
              .body(resultDTO == null ? "정보가 없습니다." : resultDTO);
    }catch (Exception e){
      //예외가 발생했을 때 실행할 코드
      return ResponseEntity
              .status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body("서버에서 오류 발생");
    }





//    //조회가 되지 않았을 때
//    if(resultDTO == null){
//      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("정보가 없습니다.");
//    }
//    //조회가 잘 됐을 때
//    else {
//      return ResponseEntity
//              .status(HttpStatus.OK)
//              .body(resultDTO);
//    }
  }

}





