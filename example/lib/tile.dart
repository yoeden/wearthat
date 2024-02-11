import 'dart:convert';
import 'dart:ui';

import 'package:wear/widgets.dart';
import 'package:flutter/material.dart' as m;
import 'package:wear_example/models/todo.dart';
import 'package:wear_example/repositories/todo.dart';

class Item {
  final int id;
  final int userId;
  final String title;
  final String body;

  factory Item.fromJson(Map<String, dynamic> json) {
    return Item(
      json['id'],
      json['userId'],
      json['title'],
      json['body'],
    );
  }

  Item(this.id, this.userId, this.title, this.body);
}

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
              SizedBox(
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
  TileFreshness get freshness => TileFreshness.interval(Duration(seconds: 5));
}

class DemoTileService extends Tile<Todo> {
  @override
  Future<Todo?> createState() async {
    const repo = TodoRepository();
    return (await repo.getAll()).firstWhere((t) => !t.done);
  }

  @override
  TileWidget build(Todo? state) {
    final appColor = m.Colors.green[300];

    return Scaffold(
      //action: ClickableActions.loadTile("/"),
      bottom: FlatButton(
        child: SizedBox(
          width: 42,
          height: 42,
          child: Image(image: AssetImageProvider("assets/check.png")),
        ),
        action: ClickableActions.pushNamed(
          '/mark',
          {"id": state!.id},
        ),
        style: ButtonStyle(
          color: appColor,
        ),
      ),
      top: SizedBox(
        height: 14,
        width: 14,
        child: Image(image: AssetImageProvider('assets/double_check.png')),
      ),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          SizedBox(height: 8),
          Column(
            mainAxisSize: MainAxisSize.min,
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text("Dec 28", style: TextStyle.title3().copyWith(color: appColor)),
              Text("Did you remember to", style: TextStyle.caption2()),
            ],
          ),
          Stack(
            children: [
              Text(
                //"Do something very long that doesn't fit here, but i have to make so that it will.",
                state!.title ?? "¯\_(ツ)_/¯",
                textAlign: TextAlign.center,
                maxLines: 3,
                style: TextStyle(
                  fontSize: 18,
                  fontWeight: FontWeight.bold,
                ),
              ),
            ],
          )
        ],
      ),
    );
  }
}
