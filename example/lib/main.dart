import 'dart:convert';

import 'package:flutter/material.dart';
import 'package:wear/messaging.dart';
import 'package:wear/widgets/app.dart';
import 'package:wear_example/models/todo.dart';
import 'package:wear_example/repositories/todo.dart';
import 'package:wear_example/tile.dart';
import 'package:wear_example/views/mark_as_done.dart';
import 'package:wear_example/views/phone.dart';
import 'package:wear_example/views/watch.dart';

@pragma('vm:entry-point')
Future<void> maintile() {
  WidgetsFlutterBinding.ensureInitialized();
  final host = TileHost(
    tiles: [
      TileService(
        name: "main",
        resources: [
          "assets/item.png",
          "assets/check.png",
          "assets/face.png",
          "assets/double_check.png",
          "assets/spongebob-dance.gif",
        ],
        routes: {
          '/': () => ProgressTile(),
          '/reminder': () => DemoTileService(),
        },
      ),
    ],
  );

  // print("Start main tile");
  // MessageClient().setOnMessageReceived((message) {
  //   print(message.path);
  //   return Future.value();
  // });

  return host.run();
}

Future<void> main(List<String> args) async {
  WidgetsFlutterBinding.ensureInitialized();

  const repo = TodoRepository();
  if (await repo.count() == 0) {
    final todos = [
      Todo(id: 1, title: "Buy some milk"),
      Todo(id: 2, title: "Read a book", done: true),
      Todo(id: 3, title: "Call mom", due: DateTime.now().add(Duration(days: 3))),
      Todo(id: 4, title: "Call dad", due: DateTime.now().add(Duration(days: -2))),
    ];
    repo.setAll(todos);
    WearMessenger.send(Message.string("set_todos", jsonEncode(todos)));
  }

  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData.light(),
      home: SafeArea(
        child: Scaffold(
          body: LayoutBuilder(
            builder: (context, constraints) {
              return constraints.maxWidth >= 350 ? PhoneView() : WearView();
            },
          ),
        ),
      ),
      routes: {
        '/mark': (context) {
          final arguments = ModalRoute.of(context)!.settings.arguments;
          return Scaffold(
            body: MarkTodoAsDone(),
          );
        },
      },
    );
  }
}
