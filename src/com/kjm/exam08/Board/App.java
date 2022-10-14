package com.kjm.exam08.Board;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {
  void main() {

    Scanner sc = Container.sc;

    System.out.println("===== 프로그램 실행 =====");
    System.out.println("===== 게시판 v 0.1 =====");

    while (true) {
      System.out.printf("명령을 입력하세요: ");
      String cmd = sc.nextLine();

      Rq rq = new Rq(cmd);
      Map<String, String> params = rq.getParams();

      if (cmd.equals("exit")) {
        break;
      } else if (rq.getUrlPath().equals("/usr/article/list")) {
        Container.usrArticleController.actionList(rq);
      } else if (rq.getUrlPath().equals("/usr/article/write")) {
        Container.usrArticleController.actionWrite();
      } else if (rq.getUrlPath().equals("/usr/article/detail")) {
        Container.usrArticleController.actionDetail(rq);
      } else if (rq.getUrlPath().equals("/usr/article/modify")) {
        Container.usrArticleController.actionModify(rq);
      } else if (rq.getUrlPath().equals("/usr/article/delete")) {
        Container.usrArticleController.actionDelete(rq);
      } else {
        System.out.println("입력된 명령: " + cmd);
      }
    }

    System.out.println("===== 프로그램 종료 =====");

    sc.close();
  }
}