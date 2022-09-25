package com.kjm.exam08.Board;

import java.util.*;

public class Main {

  static void makeTestData(List<Article> articles) {
    articles.add(new Article(1, "제목1", "내용1"));
    articles.add(new Article(2, "제목2", "내용2"));
    articles.add(new Article(3, "제목3", "내용3"));
  }

  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
    int articleLastnum = 0;

    List<Article> articles = new ArrayList<>();

    makeTestData(articles);

    if (articles.size() > 0) {
      articleLastnum = articles.get(articles.size() - 1).num;

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

        System.out.println("===== 게시물 리스트 =====");
        System.out.println("-------------------------");
        System.out.println("  번호  |  제목  |  내용  ");
        System.out.println("-------------------------");

        boolean orderBynumDesc = true;

        if (params.containsKey("orderBy") && params.get("orderBy").equals("numAsc")) {
          orderBynumDesc = false;
        }

        if (orderBynumDesc) {
          for (int i = articles.size() - 1; i >= 0; i--) {
            Article article = articles.get(i);
            System.out.printf("    %d   |   %s   |   %s   \n", article.num, article.title, article.content);
          }
        }
        else {
          for (Article article : articles) {
            System.out.printf("    %d   |   %s   |   %s   \n", article.num, article.title, article.content);
          }
        }
      }

      else if (rq.getUrlPath().equals("/usr/article/write")) {

        System.out.println("===== 게시물 등록 =====");

        System.out.printf("제목: ");
        String title = sc.nextLine();

        System.out.printf("내용: ");
        String content = sc.nextLine();
        int num = articleLastnum + 1;
        articleLastnum = num;

        Article article = new Article(num, title, content);

        articles.add(article);
        System.out.printf("%d번째 게시물이 입력되었습니다.\n", article.num);
        System.out.println(article);
      }

      else if (rq.getUrlPath().equals("/usr/article/detail")) {

        if (params.containsKey("num") == false) {
          System.out.println("num을 입력해주세요.");
          continue;
        }

        int num = 0;

        try {
          num = Integer.parseInt(params.get("num"));
        }
        catch (NumberFormatException e) {
          System.out.println("num을 정수 형태로 입력해주세요.");
          continue;
        }

        Article article = articles.get(num - 1);

        if (num > articles.size()) {
          System.out.println("게시물이 존재하지 않습니다.");
          continue;
        }

        System.out.println("===== 게시물 상세 내용 =====");

        System.out.printf("번호: %d\n", article.num);
        System.out.printf("제목: %s\n", article.title);
        System.out.printf("내용: %s\n", article.content);
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

class Rq {
  private String url;
  private String urlPath;
  private Map<String, String> params;

  Rq(String url) {
    this.url = url;
    urlPath = Util.getUrlPathFromUrl(this.url);
    params = Util.getParamsFromUrl(this.url);
  }

  public Map<String, String> getParams() {
    return params;
  }

  public String getUrlPath() {
    return urlPath;
  }
}

class Util {
  static Map<String, String> getParamsFromUrl(String url) {
    Map<String, String> params = new HashMap<>();
    String[] urlBits = url.split("\\?", 2);

    if (urlBits.length == 1) {
      return params;
    }

    String queryStr = urlBits[1];
    for (String bit : queryStr.split("&")) {
      String[] bits = bit.split("=", 2);
      if (bits.length == 1) {
        continue;
      }

      params.put(bits[0], bits[1]);
    }

    return params;
  }

  static String getUrlPathFromUrl(String url) {
    return url.split("\\?", 2)[0];
  }
}