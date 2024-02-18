import 'dart:ui';

import 'package:flutter/material.dart' as m;
import 'package:wear/tile_widgets.dart';
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
  TileWidget build(Todo? todo) {
    return Scaffold(
      bottom: _CheckButtonWidget(),
      top: const SizedBox(
        height: 14,
        width: 14,
        child: Image(image: AssetImageProvider('assets/double_check.png')),
      ),
      body: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          const SizedBox(height: 8),
          _HeaderText(due: todo!.due),
          _TodoText(title: todo.title),
        ],
      ),
    );
  }
}

class _CheckButtonWidget extends TileWidget {
  @override
  TileWidget build() {
    final appColor = m.Colors.green[300];

    return FlatButton(
      child: const SizedBox(
        width: 42,
        height: 42,
        child: Image(image: AssetImageProvider("assets/check.png")),
      ),
      action: ClickableActions.pushNamed('/mark'),
      style: ButtonStyle(
        color: appColor,
      ),
    );
  }
}

class _HeaderText extends TileWidget {
  final DateTime? due;

  _HeaderText({
    this.due,
  });

  @override
  TileWidget build() {
    final appColor = m.Colors.green[300];
    final now = DateTime.now();

    return Column(
      mainAxisSize: MainAxisSize.min,
      mainAxisAlignment: MainAxisAlignment.center,
      children: [
        Text(_format(due ?? now), style: TextStyle.title3().copyWith(color: appColor)),
        Text("Did you remember to", style: TextStyle.caption2()),
      ],
    );
  }

  String _format(DateTime date) => "${_toShortMonth(date.month)} ${date.day}";
  String _toShortMonth(int month) => ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"][month - 1];
}

class _TodoText extends TileWidget {
  final String title;

  _TodoText({required this.title});

  @override
  TileWidget build() {
    //TODO: Expose this as a center widget
    return Stack(
      children: [
        Text(
          title,
          textAlign: TextAlign.center,
          maxLines: 3,
          style: const TextStyle(
            fontSize: 18,
            fontWeight: FontWeight.bold,
          ),
        ),
      ],
    );
  }
}
