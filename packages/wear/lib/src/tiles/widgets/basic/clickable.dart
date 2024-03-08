import 'package:wearthat/tiles.dart';

class Clickable extends TileWidget {
  final TileWidget child;
  final ClickableAction action;

  Clickable({
    required this.action,
    required this.child,
  });

  @override
  Map<String, Object> serialize() => {
        "type": "__clickable",
        "child": child.serialize(),
        "action": action.serialize(),
      };

  @override
  TileWidget build() => this;
}

abstract class ClickableActions {
  static ClickableAction loadTile(String route) => _LoadTileAction(route);

  static ClickableAction pushNamed(String route, [Object? arguments]) => _PushNamedAction(route, arguments);

  static ClickableAction nothing() => _EmptyAction();

  static ClickableAction setState<TState>(TState Function() func) => _SetStateAction<TState>(func);
}

abstract class ClickableAction {
  Map<String, Object> serialize();
}

class _LoadTileAction extends ClickableAction {
  final String route;

  _LoadTileAction(this.route);

  @override
  Map<String, Object> serialize() => {
        "action": "__loadtile",
        "route": route,
      };
}

class _PushNamedAction extends ClickableAction {
  final String route;
  final Object? arguments;

  _PushNamedAction(this.route, this.arguments);

  @override
  Map<String, Object> serialize() => {
        "action": "__pushnamed",
        "route": route,
        if (arguments != null) "args": arguments!,
      };
}

class _EmptyAction extends ClickableAction {
  @override
  Map<String, Object> serialize() => {
        "action": "__nothing",
        "route": "",
      };
}

class _SetStateAction<T> extends ClickableAction {
  final T Function() func;

  _SetStateAction(this.func);

  @override
  Map<String, Object> serialize() => {
        "action": "__setstate",
        "route": "",
        "state": _serializeState(),
      };

  _serializeState() {
    final state = func();
    if (state == null) return null;

    final json = (state as dynamic).toJson();
    assert(json is Map<String, dynamic>);

    return json;
  }
}
