import 'dart:ui';

import 'package:wear/tile_widgets.dart';
import 'package:flutter/material.dart' as m;
import 'package:wear/tiles.dart';
import 'package:wear_example/models/todo.dart';
import 'package:wear_example/repositories/todo.dart';

class ReminderTile extends Tile<Todo> {
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
        child: const SizedBox(
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
      top: const SizedBox(
        height: 14,
        width: 14,
        child: Image(image: AssetImageProvider('assets/double_check.png')),
      ),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          const SizedBox(height: 8),
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
                state.title,
                textAlign: TextAlign.center,
                maxLines: 3,
                style: const TextStyle(
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
