import 'dart:ui';

import 'package:flutter/widgets.dart' as flutter;
import 'package:flutterwear/tiles.dart';

enum TextStyleType {
  custom,
  display1,
  display2,
  display3,
  title1,
  title2,
  title3,
  body1,
  body2,
  button,
  caption1,
  caption2,
}

class TextStyle {
  factory TextStyle.display1() => const TextStyle(type: TextStyleType.display1);
  factory TextStyle.display2() => const TextStyle(type: TextStyleType.display2);
  factory TextStyle.display3() => const TextStyle(type: TextStyleType.display3);
  factory TextStyle.title1() => const TextStyle(type: TextStyleType.title1);
  factory TextStyle.title2() => const TextStyle(type: TextStyleType.title2);
  factory TextStyle.title3() => const TextStyle(type: TextStyleType.title3);
  factory TextStyle.body1() => const TextStyle(type: TextStyleType.body1);
  factory TextStyle.body2() => const TextStyle(type: TextStyleType.body2);
  factory TextStyle.button() => const TextStyle(type: TextStyleType.button);
  factory TextStyle.caption1() => const TextStyle(type: TextStyleType.caption1);
  factory TextStyle.caption2() => const TextStyle(type: TextStyleType.caption2);

  final TextStyleType type;
  final double? fontSize;
  final Color? color;
  final flutter.FontStyle? fontStyle;
  final flutter.FontWeight? fontWeight;
  final double? letterSpacing;

  const TextStyle({
    this.type = TextStyleType.custom,
    this.fontSize,
    this.color,
    this.fontStyle,
    this.fontWeight,
    this.letterSpacing,
  });

  TextStyle copyWith({
    double? fontSize,
    Color? color,
    flutter.FontStyle? fontStyle,
    flutter.FontWeight? fontWeight,
    double? letterSpacing,
  }) {
    return TextStyle(
      type: type,
      color: color ?? this.color,
      fontSize: fontSize ?? this.fontSize,
      fontStyle: fontStyle ?? this.fontStyle,
      fontWeight: fontWeight ?? this.fontWeight,
      letterSpacing: letterSpacing ?? this.letterSpacing,
    );
  }

  Map<String, Object> toJson() => {
        "type": type.index,
        if (fontSize != null) "size": fontSize!,
        if (color != null) "color": color!.value,
        if (fontStyle != null) "italic": fontStyle == flutter.FontStyle.italic,
        if (fontWeight != null) "weight": fontWeight!.value,
        if (letterSpacing != null) "letterSpacing": letterSpacing!,
      };
}

class Text extends TileWidget {
  final String text;
  final TextStyle? style;
  final TextAlign textAlign;
  final TextOverflow overflow;
  final int maxLines;

//TODO: Add asserts
  const Text(
    this.text, {
    this.style,
    this.textAlign = TextAlign.start,
    this.overflow = TextOverflow.ellipsis,
    this.maxLines = 1,
  });

  @override
  Map<String, Object> serialize() => {
        "type": "__text",
        "text": text,
        "align": textAlign.index,
        "overflow": overflow.index,
        "maxLines": maxLines,
        if (style != null) "style": style!.toJson(),
      };

  @override
  TileWidget build() => this;
}

/// How overflowing text should be handled.
///
/// A [TextOverflow] can be passed to [Text] and [RichText] via their
/// [Text.overflow] and [RichText.overflow] properties respectively.
enum TextOverflow {
  /// Use an ellipsis to indicate that the text has overflowed.
  ellipsis,

  /// Render overflowing text outside of its container.
  truncate,
}

/// Whether and how to align text horizontally.
// The order of this enum must match the order of the values in RenderStyleConstants.h's ETextAlign.
enum TextAlign {
  /// Align the text in the center of the container.
  center,

  /// Align the text on the leading edge of the container.
  ///
  /// For left-to-right text ([TextDirection.ltr]), this is the left edge.
  ///
  /// For right-to-left text ([TextDirection.rtl]), this is the right edge.
  start,

  /// Align the text on the trailing edge of the container.
  ///
  /// For left-to-right text ([TextDirection.ltr]), this is the right edge.
  ///
  /// For right-to-left text ([TextDirection.rtl]), this is the left edge.
  end,
}
