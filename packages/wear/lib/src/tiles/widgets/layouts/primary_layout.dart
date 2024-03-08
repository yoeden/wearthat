import 'package:wearthat/tiles.dart';

class PrimaryLayout extends TileWidget {
  final TileWidget? body;
  final TileWidget? chip;
  final TileWidget? title;
  final TileWidget? subtitle;

  PrimaryLayout({
    this.body,
    this.chip,
    this.title,
    this.subtitle,
  });

  @override
  TileWidget build() => this;

  @override
  Map<String, Object> serialize() => {
        'type': '__primary_layout',
        if (body != null) 'body': body!.serialize(),
        if (chip != null) 'chip': chip!.serialize(),
        if (title != null) 'title': title!.serialize(),
        if (subtitle != null) 'subtitle': subtitle!.serialize(),
      };
}
