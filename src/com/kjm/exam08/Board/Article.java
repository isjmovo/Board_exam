package com.kjm.exam08.Board;

public class Article {
  int num;
  String title;
  String content;

  Article (int num, String title, String content) {
    this.num = num;
    this.title = title;
    this.content = content;
  }
  @Override
  public String toString() {
    return String.format("{num: %d, title: %s, content: %s}", num, title, content);
  }
}