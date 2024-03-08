import 'package:flutterwear/tiles.dart';
import 'package:flutter/material.dart' as m;

class Button extends TileWidget {
  final TileWidget child;
  final ClickableAction action;
  final ButtonStyle? style;

  Button({
    required this.child,
    required this.action,
    this.style,
  });

  @override
  TileWidget build() => this;

  @override
  Map<String, Object> serialize() => {
        'type': '__button',
        'child': child.serialize(),
        'action': action.serialize(),
        if (style != null) 'style': style!.serialize(),
      };
}

class ButtonStyle {
  final m.Color? backgroundColor;
  final m.Color? foregroundColor;

  ButtonStyle({
    this.backgroundColor,
    this.foregroundColor,
  });

  Map<String, Object> serialize() => {
        if (backgroundColor != null) "backgroundColor": backgroundColor!.value,
        if (foregroundColor != null) "foregroundColor": foregroundColor!.value,
      };
}
