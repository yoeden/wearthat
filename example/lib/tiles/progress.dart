import 'package:wear/tile_widgets.dart';
import 'package:wear/tiles.dart';
import 'package:wear_example/models/todo.dart';
import 'package:wear_example/repositories/todo.dart';
import 'package:flutter/material.dart' as m;

class ProgressTile extends Tile<List<Todo>> {
  @override
  Future<List<Todo>> createState() {
    return const TodoRepository().getAll();
  }

  @override
  TileWidget build(state) {
    final doneCount = state!.where((e) => e.done).length;
    final count = state.length;
    final appColor = m.Colors.green[300];

    return Stack(
      children: [
        ArcLayout(
          anchor: -360 * 1 / 3,
          children: [
            ArcProgressLine(
              length: 360 * 2 / 3,
              progress: doneCount / count,
              style: ArcProgressLineStyle(
                color: appColor,
                thickness: 8,
              ),
            ),
          ],
          edgeAlignment: ArcEdgesFitAlignment.safe,
        ),
        Clickable(
          action: ClickableActions.loadTile("/reminder"),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            mainAxisSize: MainAxisSize.min,
            children: [
              const SizedBox(
                height: 24,
                width: 24,
                child: Image(
                  image: AssetImageProvider("assets/double_check.png"),
                ),
              ),
              Text(
                "${(doneCount / count * 100).toInt()} %",
                style: TextStyle.display2().copyWith(color: appColor),
              ),
              Text(
                "${count - doneCount} left",
                style: TextStyle.title3(),
              ),
            ],
          ),
        ),
      ],
    );
  }

  @override
  TileFreshness get freshness => const TileFreshness.interval(Duration(seconds: 5));
}
