import 'package:flutter/widgets.dart' as flutter;
import 'package:wear/tiles/widgets.dart';

abstract class TileWidget {
  const TileWidget();

  Map<String, Object> serialize();
}

abstract class TileWidgetBuilder extends TileWidget {
  TileWidget build();

  @override
  Map<String, Object> serialize() => build().serialize();
}

class TileWidgetTreeSerializer {
  static final Map<Type, Map<String, Object> Function(TileWidgetTreeSerializer, TileWidget)> _mappers = {
    Text: (v, w) => _mapText(v, w as Text),
    Column: (v, w) => _mapColumn(v, w as Column),
  };

  final Map<String, Object> result = {};

  Map<String, Object> serialize(TileWidget widget) {
    return _mappers[widget.runtimeType]!(this, widget);
  }

  static Map<String, Object> _mapText(TileWidgetTreeSerializer s, Text widget) {
    if (widget.text == "") return {};

    return {
      "type": "__text",
      "text": widget.text,
      if (widget.style != null)
        "style": {
          "type": widget.style!.type.index,
          if (widget.style!.fontSize != null) "size": widget.style!.fontSize,
          if (widget.style!.color != null) "color": widget.style!.color!.value,
          if (widget.style!.fontStyle != null) "italic": widget.style!.fontStyle == flutter.FontStyle.italic,
          if (widget.style!.fontWeight != null) "weight": widget.style!.fontWeight!.index,
          if (widget.style!.letterSpacing != null) "letterSpacing": widget.style!.letterSpacing,
        },
    };
  }

  static Map<String, Object> _mapColumn(TileWidgetTreeSerializer s, Column widget) {
    final children = [];

    for (var child in widget.children) {
      children.add(s.serialize(child));
    }

    if (children.isEmpty) return {};

    return {
      "type": "__column",
      "children": children,
    };
  }

  static Map<String, Object> _mapStack(TileWidgetTreeSerializer s, Stack widget) {
    final children = [];

    for (var child in widget.children) {
      children.add(s.serialize(child));
    }

    if (children.isEmpty) return {};

    return {
      "type": "__stack",
      "children": children,
    };
  }
}
