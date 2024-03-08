import 'package:flutterwear/tiles.dart';
import 'package:flutterwear/tiles.dart';

class TimerTile extends Tile {
  @override
  TileWidget build(context, state) {
    return MultiButtonLayout(
      children: [
        Button(action: ClickableActions.pushNamed(''), child: Text("30s")),
        Button(action: ClickableActions.nothing(), child: Text("1m")),
        Button(action: ClickableActions.nothing(), child: Text("5m")),
        Button(action: ClickableActions.nothing(), child: Text("15m")),
        Button(action: ClickableActions.nothing(), child: Text("30m")),
      ],
    );
  }
}
