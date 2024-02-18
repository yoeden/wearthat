import 'package:wear/tile_widgets.dart';
import 'package:wear/tiles.dart';
import 'package:flutter/material.dart' as m;

class HelloWorldTile extends Tile {
  @override
  TileWidget build(_) {
    return Stack(
      children: [
        Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(
              "Hello World",
              textAlign: TextAlign.center,
              maxLines: 3,
              style: TextStyle.display2(),
            ),
            FlatButton(
              child: Text("Yes !"),
              action: ClickableActions.nothing(),
              style: ButtonStyle(
                color: m.Colors.purple.shade300,
              ),
            ),
          ],
        )
      ],
    );
  }
}
