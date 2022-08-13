package com.kjm.exam08.Board;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);

    System.out.println("===== 프로그램 실행 =====");
    System.out.println("===== 게시판 v 0.1 =====");

    while (true) {
      System.out.printf("명령을 입력하세요: ");
      String cmd = sc.nextLine();

      System.out.println("입력된 명령: " + cmd);

      if (cmd.equals("/usr/article/write")) {

        int num = 0;

        System.out.println("===== 게시물 등록 =====");

        System.out.printf("제목: ");
        String title = sc.nextLine();

        System.out.printf("내용: ");
        String content = sc.nextLine();

        num++;
        System.out.println(num + "번째 게시물이 등록되었습니다.");
      }

      else if (cmd.equals("exit")) {
        break;
      }

      else {

      }
      sc.close();

      System.out.println("===== 프로그램 종료 =====");
    }
  }
}