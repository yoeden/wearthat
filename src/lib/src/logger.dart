enum Logger {
  black("30"),
  red("31"),
  green("32"),
  yellow("33"),
  blue("34"),
  magenta("35"),
  cyan("36"),
  white("37");

  final String code;
  const Logger(this.code);

  void log(dynamic text) => print('\x1B[${code}m$text\x1B[0m');

  static void warning(Object text) => Logger.yellow.log(text);
  static void error(Object text) => Logger.red.log(text);
  static void info(Object text) => Logger.white.log(text);
}
