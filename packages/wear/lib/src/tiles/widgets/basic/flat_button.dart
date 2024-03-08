import 'dart:ui';

import 'package:wear/tiles.dart';

class FlatButton extends TileWidget {
  final TileWidget child;
  final ClickableAction action;
  final FlatButtonStyle style;

  FlatButton({
    required this.child,
    required this.action,
    this.style = const FlatButtonStyle(),
  });

  @override
  TileWidget build() {
    return Clickable(
      action: action,
      child: DecoratedBox(
        decoration: BoxDecoration(color: style.color, borderRadius: style.borderRadius, border: style.border),
        child: Padding(
          padding: style.padding,
          child: child is Text ? _textChild(child as Text) : child,
        ),
      ),
    );
  }

  TileWidget _textChild(Text child) {
    if (child.style != null) return child;

    return Text(
      child.text,
      style: TextStyle.button(),
      maxLines: child.maxLines,
      overflow: child.overflow,
      textAlign: child.textAlign,
    );
  }
}

class FlatButtonStyle {
  final Color? color;
  final Border? border;
  final double borderRadius;
  final EdgeInsets padding;

  const FlatButtonStyle({
    this.color,
    this.border,
    this.borderRadius = 32,
    //TODO: This should be for text button
    this.padding = const EdgeInsets.symmetric(horizontal: 12, vertical: 6),
  });

  FlatButtonStyle copyWith({
    Color? color,
    Border? border,
    double? borderRadius,
    EdgeInsets? padding,
  }) {
    return FlatButtonStyle(
      color: color ?? this.color,
      border: border ?? this.border,
      borderRadius: borderRadius ?? this.borderRadius,
      padding: padding ?? this.padding,
    );
  }
}
