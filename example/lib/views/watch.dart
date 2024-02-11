import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:wear/messaging.dart';
import 'package:wear_example/models/todo.dart';
import 'package:wear_example/repositories/todo.dart';
import 'package:wear_example/views/common/todotiles.dart';

class WearView extends StatefulWidget {
  const WearView({super.key});

  @override
  State<WearView> createState() => _WearViewState();
}

class _WearViewState extends State<WearView> {
  final repo = const TodoRepository();
  final todos = <Todo>[];

  @override
  void initState() {
    super.initState();
    WearMessenger.listen("todos.sync", _wearMessageReceived);
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder<List<Todo>>(
      future: repo.getAll(),
      builder: (context, snapshot) {
        if (snapshot.connectionState != ConnectionState.done) {
          return Center(child: CircularProgressIndicator());
        }

        todos.clear();
        todos.addAll(snapshot.data!);

        if (todos.isEmpty) {
          return Center(
            child: Text("Sync todos with phone !"),
          );
        }

        return PageView.builder(
          scrollDirection: Axis.vertical,
          itemCount: todos.length,
          itemBuilder: (context, i) {
            return Center(
              child: TodoTile(
                todo: todos[i],
                onTodoChanged: (value) {
                  todos[i] = value;
                  setState(() {});
                },
              ),
            );
          },
        );
      },
    );
  }

  Future _wearMessageReceived(Message message) async {
    await repo.clear();
    await repo.setAll((jsonDecode(message.dataAsString()) as List).map((e) => Todo.fromJson(e)).toList());
    setState(() {});
  }
}
