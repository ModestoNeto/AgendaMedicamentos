class AppSession {
  static int? userId;
  static String? userName;
  static String? userEmail;

  static bool get isLogged => userId != null;

  static void clear() {
    userId = null;
    userName = null;
    userEmail = null;
  }
}
