import 'dart:ui';

import 'package:flutter/material.dart' as m;
import 'package:wearthat/tiles.dart';
import 'package:wear_example/models/todo.dart';
import 'package:wear_example/repositories/todo.dart';

class ReminderTile extends Tile<Todo> {
  @override
  Future<Todo?> createState() async {
    const repo = TodoRepository();
    return (await repo.getAll()).firstWhere((t) => !t.done);
  }

  @override
  // ignore: avoid_renaming_method_parameters
  TileWidget build(context, Todo? todo) {
    return PrimaryLayout(
      chip: _CheckButtonWidget(),
      title: _HeaderText(due: todo!.due),
      body: _TodoText(title: todo.title),
    );
  }

  @override
  Map<String, TileResourceProvider> resources(
          TileContext context, Todo? state) =>
      {
        'progress': TileResources.asset("assets/double_check.png"),
        'check': TileResources.asset("assets/check.png"),
      };
}

class _CheckButtonWidget extends TileWidget {
  @override
  TileWidget build() {
    final appColor = m.Colors.green[300];

    return Button(
      child: const SizedBox(
        width: 32,
        height: 32,
        child: Image("check"),
      ),
      action: ClickableActions.pushNamed('/mark'),
      style: ButtonStyle(
        backgroundColor: appColor,
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
        Text(_format(due ?? now),
            style: TextStyle.title3().copyWith(color: appColor)),
        Text("Did you remember to", style: TextStyle.caption2()),
      ],
    );
  }

  String _format(DateTime date) => "${_toShortMonth(date.month)} ${date.day}";
  String _toShortMonth(int month) => [
        "Jan",
        "Feb",
        "Mar",
        "Apr",
        "May",
        "Jun",
        "Jul",
        "Aug",
        "Sep",
        "Oct",
        "Nov",
        "Dec"
      ][month - 1];
}

class _TodoText extends TileWidget {
  final String title;

  _TodoText({required this.title});

  @override
  TileWidget build() {
    return Text(
      title,
      textAlign: TextAlign.center,
      maxLines: 3,
      style: const TextStyle(
        fontSize: 18,
        fontWeight: FontWeight.bold,
      ),
    );
  }
}
