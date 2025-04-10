package com.green.book_shop.book.service;

import com.green.book_shop.book.dto.BookCategoryDTO;
import com.green.book_shop.book.dto.BookDTO;

import java.util.List;

public interface BookService {
  //카테고리 목록 조회
  public List<BookCategoryDTO> selectCategoryList();

  //도서 등록
  public void insertBook(BookDTO bookDTO);

  //카테고리 등록 기능
  public int insertCategory(String cateName);

  //도서 이미지 등록
  public void insertImgs(BookDTO bookDTO);

  //다음에 들어갈 BOOK_CODE 조회
  public int getNextBookCode();

  //상품 목록 조회
  public List<BookDTO> selectItemList();

  public BookCategoryDTO getCategoryByCateCode(int cateCode);




}








