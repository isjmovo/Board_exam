package com.kjm.exam08.Board;

import java.util.*;

public class Main {

  static void makeTestData(List<Article> articles) {
    for (int i = 0; i < 100; i++) {
      int num = i + 1;
      articles.add(new Article(num, "제목" + num, "내용" + num));
    }
  }

  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
    int articleLastNum = 0;

    List<Article> articles = new ArrayList<>();

    makeTestData(articles);

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
        actionUserArticleList(rq, articles);
      }

      else if (rq.getUrlPath().equals("/usr/article/write")) {
        actionUserArticleWrite(sc, articleLastNum, articles);
        articleLastNum++;
      }

      else if (rq.getUrlPath().equals("/usr/article/detail")) {
        actionUserArticleDetail(rq, articles);
      }

      else {
        System.out.println("입력된 명령: " + cmd);
      }
    }

    System.out.println("===== 프로그램 종료 =====");

    sc.close();
  }

  public static void actionUserArticleWrite(Scanner sc, int articleLastNum, List<Article> articles) {
    System.out.println("===== 게시물 등록 =====");

    System.out.printf("제목: ");
    String title = sc.nextLine();

    System.out.printf("내용: ");
    String content = sc.nextLine();
    int num = ++articleLastNum;

    Article article = new Article(num, title, content);

    articles.add(article);
    System.out.printf("%d번째 게시물이 입력되었습니다.\n", article.num);
    System.out.println("생성된 게시물 객체: " + article);
  }

  public static void actionUserArticleDetail(Rq rq,List<Article> articles) {

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

  public static void actionUserArticleList(Rq rq, List<Article> articles) {
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

  public static<T> List<T> reverseList(List<T> list) {
    List<T> reverse = new ArrayList<>(list.size());

    for (int i = list.size() - 1; i >= 0; i--) {
      reverse.add(list.get(i));
    }
    return reverse;
  }
}