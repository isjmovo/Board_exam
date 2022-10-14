package com.kjm.exam08.Board;

import java.util.Scanner;

public class Container {
  static Scanner sc;
  static UsrArticleController usrArticleController;

  static {
    sc = new Scanner(System.in);
    usrArticleController = new UsrArticleController();
  }
}
