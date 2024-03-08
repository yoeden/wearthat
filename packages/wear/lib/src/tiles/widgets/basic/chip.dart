import 'package:wearthat/tiles.dart';

class Chip extends TileWidget {
  final String? icon;
  final TileWidget? child;
  final ClickableAction action;

  Chip({
    required this.action,
    this.icon,
    this.child,
  });

  @override
  TileWidget build() => this;

  @override
  Map<String, Object> serialize() => {
        'type': '__chip',
        'action': action.serialize(),
        if (icon != null) 'icon': icon!,
        if (child != null) 'child': child!,
      };
}
