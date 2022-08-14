package com.kjm.exam08.Board;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);

    Article article = new Article();
    int num = 0;

    System.out.println("===== 프로그램 실행 =====");
    System.out.println("===== 게시판 v 0.1 =====");

    while (true) {
      System.out.printf("명령을 입력하세요: ");
      String cmd = sc.nextLine();

      System.out.println("입력된 명령: " + cmd);

      if (cmd.equals("/usr/article/write")) {

        System.out.println("===== 게시물 등록 =====");

        System.out.printf("제목: ");
        String title = sc.nextLine();
        article.title = title;

        System.out.printf("내용: ");
        String content = sc.nextLine();
        article.content = content;

        num++;
        article.num = num;
        System.out.println(num + "번째 게시물이 등록되었습니다.");
      }

      else if (cmd.equals("exit")) {
        break;
      }

      else {

      }
    }
    System.out.println("===== 프로그램 종료 =====");

    sc.close();
  }
}

class Article {
  int num;
  String title;
  String content;

  @Override
  public String toString() {
    return String.format("{num: %d, title: %s, content: %s}", num, title, content);
  }
}