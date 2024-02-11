import 'dart:convert';

import 'package:shared_preferences/shared_preferences.dart';
import 'package:wear_example/models/todo.dart';

class TodoRepository {
  static const String _kTodos = "todos";

  const TodoRepository();

  Future add(Todo todo) async {
    final todos = await getAll();
    if (todos.any((element) => element.id == todo.id)) throw Exception('Todo already exists');

    todos.add(todo);
    setAll(todos);
  }

  Future update(Todo todo) async {
    final todos = await getAll();
    if (!todos.any((element) => element.id == todo.id)) throw Exception('Todo does not exist');

    todos[todos.indexWhere((element) => element.id == todo.id)] = todo;
    setAll(todos);
  }

  Future<Todo?> findById(int id) async {
    final todos = (await getAll()).cast<Todo?>();
    return todos.firstWhere((t) => t!.id == id, orElse: () => null);
  }

  Future delete(Todo todo) async {
    final todos = await getAll();
    todos.removeWhere((element) => element.id == todo.id);
  }

  Future<int> count() async => (await getAll()).length;

  Future<List<Todo>> getAll() async {
    final storage = await SharedPreferences.getInstance();
    final raw = storage.getString(_kTodos);
    if (raw == null) return <Todo>[];
    final todos = (jsonDecode(raw) as List).map((e) => Todo.fromJson(e)).toList();
    return todos;
  }

  Future setAll(List<Todo> todos) async {
    final storage = await SharedPreferences.getInstance();
    return storage.setString(_kTodos, jsonEncode(todos));
  }

  Future clear() async {
    final storage = await SharedPreferences.getInstance();
    return storage.remove(_kTodos);
  }
}
