import 'package:wearthat/tiles.dart';

class ArcAdapter extends ArcWidget {
  final TileWidget child;

  const ArcAdapter({required this.child}) : assert(child is! ArcWidget, "ArcAdapter can't wrap an ArcWidget");

  @override
  TileWidget build() => this;

  @override
  Map<String, Object> serialize() => {
        "type": "__arc_adapter",
        "child": child.serialize(),
      };
}
