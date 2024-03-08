import 'package:flutterwear/tiles.dart';

class TitleChip extends TileWidget {
  final String title;
  final ClickableAction action;
  final String? icon;

  TitleChip({
    required this.title,
    required this.action,
    this.icon,
  });

  @override
  TileWidget build() => this;

  @override
  Map<String, Object> serialize() => {
        "type": "__titlechip",
        "title": title,
        "action": action.serialize(),
        if (icon != null) "icon": icon!,
      };
}
