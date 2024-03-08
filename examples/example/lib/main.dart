import 'package:flutter/material.dart';
import 'package:wear/tiles.dart';
import 'package:wear_example/models/todo.dart';
import 'package:wear_example/repositories/todo.dart';
import 'package:wear_example/tiles/progress.dart';
import 'package:wear_example/tiles/reminder.dart';
import 'package:wear_example/views/mark_as_done.dart';
import 'package:wear_example/views/watch.dart';

@pragma('vm:entry-point')
void maintile(List<String> args) {
  runTiles(
    [
      TileService(
        name: "ExampleTile",
        routes: {
          '/': () => ProgressTile(),
          '/reminder': () => ReminderTile(),
        },
      ),
    ],
  );
}

Future<void> main(List<String> args) async {
  WidgetsFlutterBinding.ensureInitialized();

  const repo = TodoRepository();
  if (await repo.count() == 0) {
    final todos = [
      Todo(id: 1, title: "Buy some milk"),
      Todo(id: 2, title: "Read a book", done: true),
      Todo(id: 3, title: "Call mom", due: DateTime.now().add(const Duration(days: 3))),
      Todo(id: 4, title: "Call dad", due: DateTime.now().add(const Duration(days: -2))),
    ];
    repo.setAll(todos);
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
              //return constraints.maxWidth >= 350 ? const PhoneView() : const WearView();
              return WearView();
            },
          ),
        ),
      ),
      routes: {
        '/mark': (context) {
          final arguments = ModalRoute.of(context)!.settings.arguments;
          return const Scaffold(
            body: MarkTodoAsDone(),
          );
        },
      },
    );
  }
}
