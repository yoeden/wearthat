enum LoggerColor {
  black("30"),
  red("31"),
  green("32"),
  yellow("33"),
  blue("34"),
  magenta("35"),
  cyan("36"),
  white("37");

  final String code;
  const LoggerColor(this.code);

  void _log(dynamic text) => print('\x1B[${code}m$text\x1B[0m');
}

enum LoggerLevel {
  debug,
  info,
  warning,
  error,
}

mixin class Log {
  static void d(Object message, {bool ignoreIndent = false}) => Logger.def.d(message, ignoreIndent: ignoreIndent);
  static void i(Object message, {bool ignoreIndent = false}) => Logger.def.i(message, ignoreIndent: ignoreIndent);
  static void w(Object message, {bool ignoreIndent = false}) => Logger.def.w(message, ignoreIndent: ignoreIndent);
  static void e(Object message, {bool ignoreIndent = true}) => Logger.def.e(message, ignoreIndent: ignoreIndent);
}

class Logger {
  static const Logger def = Logger();

  final int indentation;
  final Logger? parent;
  final LoggerLevel max;
  final LoggerLevel min;
  final Map<LoggerLevel, LoggerColor> colors;

  const Logger({
    this.indentation = 0,
    this.max = LoggerLevel.error,
    this.min = LoggerLevel.debug,
    this.colors = const {
      LoggerLevel.debug: LoggerColor.blue,
      LoggerLevel.warning: LoggerColor.yellow,
      LoggerLevel.info: LoggerColor.white,
      LoggerLevel.error: LoggerColor.magenta,
    },
    this.parent,
  });

  void log(LoggerLevel level, Object message, {bool ignoreIndent = false}) {
    if (!willLog(level)) return;
    if (colors[level] == null) throw Exception("Color not set for level '$level'");

    return colors[level]!._log("${ignoreIndent ? "" : "  " * indentation}$message");
  }

  void d(Object message, {bool ignoreIndent = false}) => log(LoggerLevel.debug, message, ignoreIndent: ignoreIndent);
  void i(Object message, {bool ignoreIndent = false}) => log(LoggerLevel.info, message, ignoreIndent: ignoreIndent);
  void w(Object message, {bool ignoreIndent = false}) => log(LoggerLevel.warning, message, ignoreIndent: ignoreIndent);
  void e(Object message, {bool ignoreIndent = true}) => log(LoggerLevel.error, message, ignoreIndent: ignoreIndent);

  Logger wrap() => Logger(
        indentation: indentation + 1,
        max: max,
        min: min,
        colors: colors,
        parent: this,
      );

  Logger unwrap() => parent ?? this;

  Logger copyWith({
    int? indentation,
    LoggerLevel? max,
    LoggerLevel? min,
    Map<LoggerLevel, LoggerColor>? colors,
  }) =>
      Logger(
        indentation: indentation ?? this.indentation,
        max: max ?? this.max,
        min: min ?? this.min,
        colors: colors ?? this.colors,
      );

  bool willLog(LoggerLevel level) => level.index >= min.index && level.index <= max.index;
}

extension LoggerExt on Logger {
  T nest<T>(T Function(Logger logger) fn) => fn(wrap());
}
