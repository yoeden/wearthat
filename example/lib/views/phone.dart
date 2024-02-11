import 'dart:convert';
import 'dart:math' as math;

import 'package:flutter/material.dart';
import 'package:wear/messaging.dart';
import 'package:wear_example/models/todo.dart';
import 'package:wear_example/repositories/todo.dart';
import 'package:wear_example/views/common/addtodo.dart';
import 'package:wear_example/views/common/todotiles.dart';

class PhoneView extends StatefulWidget {
  const PhoneView({
    super.key,
  });

  @override
  State<PhoneView> createState() => _PhoneViewState();
}

class _PhoneViewState extends State<PhoneView> {
  final repo = const TodoRepository();
  final todos = <Todo>[];

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);

    return Scaffold(
      floatingActionButton: FloatingActionButton(
        child: Icon(Icons.add),
        onPressed: () async {
          final title = await showModalBottomSheet(
            context: context,
            isScrollControlled: true,
            builder: (context) {
              return Padding(
                padding: EdgeInsets.only(bottom: MediaQuery.of(context).viewInsets.bottom),
                child: AddTodo(),
              );
            },
          );
          if (title == null) return;

          final todo = Todo(id: math.Random().nextInt(0x7FFFFFFFFFFFFFFF - 1), title: title);
          todos.add(todo);
          repo.add(todo);
          WearMessenger.send(Message.string("todos.sync", jsonEncode(await repo.getAll())));
          setState(() {});
        },
      ),
      body: FutureBuilder<List<Todo>>(
          future: repo.getAll(),
          builder: (context, snapshot) {
            if (snapshot.connectionState != ConnectionState.done) {
              return Center(child: CircularProgressIndicator());
            }

            todos.clear();
            todos.addAll(snapshot.data!);
            return Padding(
              padding: const EdgeInsets.all(24.0),
              child: Column(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Text("Todos:", style: theme.textTheme.headlineLarge),
                  Expanded(
                    child: ListView.separated(
                      itemBuilder: (context, i) {
                        return TodoTile(
                          todo: todos[i],
                          onTodoChanged: (t) async {
                            todos[i] = t;
                            await repo.update(t);
                            WearMessenger.send(Message.string("todos.sync", jsonEncode(await repo.getAll())));
                            setState(() {});
                          },
                        );
                      },
                      separatorBuilder: (BuildContext context, int index) => const Divider(),
                      itemCount: todos.length,
                    ),
                  ),
                ],
              ),
            );
          }),
    );
  }

  Future<void> _messageFromWear(Message message) {
    print("Message from wear ! ");
    return Future.value();
  }
}
