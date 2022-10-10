package com.kjm.exam08.Board;

import java.util.*;

public class Main {
  static int articleLastNum;
  static List<Article> articles;

  static void makeTestData() {
    for (int i = 0; i < 100; i++) {
      int num = i + 1;
      articles.add(new Article(num, "제목" + num, "내용" + num));
    }
  }

  static {
    articleLastNum = 0;
    articles = new ArrayList<>();
  }
  public static void main(String[] args) {

    Scanner sc = Container.sc;

    makeTestData();

    if (articles.size() > 0) {
      articleLastNum = articles.get(articles.size() - 1).num;

    }

    System.out.println("===== 프로그램 실행 =====");
    System.out.println("===== 게시판 v 0.1 =====");

    while (true) {
      System.out.printf("명령을 입력하세요: ");
      String cmd = sc.nextLine();

      Rq rq = new Rq(cmd);
      Map<String, String> params = rq.getParams();

      if (cmd.equals("exit")) {
        break;
      }
      else if (rq.getUrlPath().equals("/usr/article/list")) {
        actionUsrArticleList(rq);
      }
      else if (rq.getUrlPath().equals("/usr/article/write")) {
        actionUsrArticleWrite();
      }
      else if (rq.getUrlPath().equals("/usr/article/detail")) {
        actionUsrArticleDetail(rq);
      }
      else if (rq.getUrlPath().equals("/usr/article/modify")) {
        actionUsrArticleModify(rq);
      }
      else if (rq.getUrlPath().equals("/usr/article/delete")) {
        actionUsrArticleDelete(rq);
      }
      else {
        System.out.println("입력된 명령: " + cmd);
      }
    }

    System.out.println("===== 프로그램 종료 =====");

    sc.close();
  }

  private static void actionUsrArticleDelete(Rq rq) {
    Map<String, String> params = rq.getParams();

    if (params.containsKey("num") == false) {
      System.out.println("num을 입력해주세요.");
      return;
    }

    int num = 0;

    try {
      num = Integer.parseInt(params.get("num"));
    }
    catch (NumberFormatException e) {
      System.out.println("num을 정수 형태로 입력해주세요.");
      return;
    }

    Article foundArticle = null;

    for (Article article : articles) {
      if (article.num == num) {
        foundArticle = article;
        break;
      }
    }

    if (foundArticle == null) {
      System.out.printf("%d번 게시물이 존재하지 않습니다.\n", num);
      return;
    }

    articles.remove(foundArticle);
    System.out.printf("%d번 게시물을 삭제하였습니다.\n", num);
  }

  private static void actionUsrArticleModify(Rq rq) {
    Map<String, String> params = rq.getParams();

    if (params.containsKey("num") == false) {
      System.out.println("num을 입력해주세요.");
      return;
    }

    int num = 0;

    try {
      num = Integer.parseInt(params.get("num"));
    }
    catch (NumberFormatException e) {
      System.out.println("num을 정수 형태로 입력해주세요.");
      return;
    }

    Article foundArticle = null;

    for (Article article : articles) {
      if (article.num == num) {
        foundArticle = article;
        break;
      }
    }

    if (foundArticle == null) {
      System.out.printf("%d번 게시물이 존재하지 않습니다.\n", num);
      return;
    }

    System.out.printf("새 제목: ");
    foundArticle.title = Container.sc.nextLine();
    System.out.printf("새 내용: ");
    foundArticle.content = Container.sc.nextLine();

    System.out.printf("%d번 게시물을 수정하였습니다.\n", num);
  }

  public static void actionUsrArticleWrite() {
    System.out.println("===== 게시물 등록 =====");

    System.out.printf("제목: ");
    String title = Container.sc.nextLine();

    System.out.printf("내용: ");
    String content = Container.sc.nextLine();

    int num = ++articleLastNum;
    articleLastNum = num;

    Article article = new Article(num, title, content);

    articles.add(article);
    System.out.printf("%d번째 게시물이 입력되었습니다.\n", article.num);
    System.out.println("생성된 게시물 객체: " + article);
  }

  public static void actionUsrArticleDetail(Rq rq) {

    Map<String, String> params = rq.getParams();

    if (params.containsKey("num") == false) {
      System.out.println("num을 입력해주세요.");
      return;
    }

    int num = 0;

    try {
      num = Integer.parseInt(params.get("num"));
    }
    catch (NumberFormatException e) {
      System.out.println("num을 정수 형태로 입력해주세요.");
      return;
    }

    Article article = articles.get(num - 1);

    if (num > articles.size()) {
      System.out.println("게시물이 존재하지 않습니다.");
      return;
    }

    System.out.println("===== 게시물 상세 내용 =====");

    System.out.printf("번호: %d\n", article.num);
    System.out.printf("제목: %s\n", article.title);
    System.out.printf("내용: %s\n", article.content);
  }

  public static void actionUsrArticleList(Rq rq) {
    System.out.println("===== 게시물 리스트 =====");
    System.out.println("-------------------------");
    System.out.println("  번호  |  제목  |  내용  ");
    System.out.println("-------------------------");

    Map<String, String> params = rq.getParams();
    // 검색 시작
    List<Article> filteredArticles = articles;

    if (params.containsKey("searchKeyword")) {
      String searchKeyword = params.get("searchKeyword");

      filteredArticles = new ArrayList<>();

      for (Article article : articles) {
        boolean matched = article.title.contains(searchKeyword) || article.content.contains(searchKeyword);

        if (matched) {
          filteredArticles.add(article);
        }
      }
    }
    // 검색 끝

    List<Article> sortedArticles = filteredArticles;

    boolean orderBynumDesc = true;

    if (params.containsKey("orderBy") && params.get("orderBy").equals("numAsc")) {
      orderBynumDesc = false;
    }

    if (orderBynumDesc) {
      sortedArticles = Util.reverseList(sortedArticles);
    }

    for (Article article : sortedArticles) {
      System.out.printf("    %d   |   %s   |   %s   \n", article.num, article.title, article.content);
    }
  }
}