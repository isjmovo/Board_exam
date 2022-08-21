package com.kjm.exam08.Board;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
    int num = 0;
    Article lastAc = null;

    System.out.println("===== 프로그램 실행 =====");
    System.out.println("===== 게시판 v 0.1 =====");

    while (true) {
      System.out.printf("명령을 입력하세요: ");
      String cmd = sc.nextLine();

      if (cmd.equals("/usr/article/write")) {

        System.out.println("===== 게시물 등록 =====");

        System.out.printf("제목: ");
        String title = sc.nextLine();

        System.out.printf("내용: ");
        String content = sc.nextLine();

        num++;

        Article ac = new Article(num, title, content);
        lastAc = ac;

        System.out.printf("%d번째 게시물이 입력되었습니다.\n", ac.num);
        System.out.println(ac);
      }

      else if (cmd.equals("exit")) {
        break;
      }

      else if (cmd.equals("/usr/article/detail")) {
        if (lastAc == null) {
          System.out.println("게시물이 존재하지 않습니다.");
          continue;
        }

        Article ac = lastAc;

        System.out.println("===== 게시물 상세 내용 =====");

        System.out.printf("번호: %d\n", lastAc.num);
        System.out.printf("제목: %s\n", lastAc.title);
        System.out.printf("내용: %s\n", lastAc.content);
      }

      else {
        System.out.println("입력된 명령: " + cmd);
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