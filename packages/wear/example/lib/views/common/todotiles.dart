import 'package:flutter/material.dart';
import 'package:wear_example/models/todo.dart';

class TodoTile extends StatelessWidget {
  final Todo todo;
  final ValueSetter<Todo> onTodoChanged;

  const TodoTile({super.key, required this.todo, required this.onTodoChanged});

  @override
  Widget build(BuildContext context) {
    return ListTile(
      leading: Checkbox(
        value: todo.done,
        shape: const CircleBorder(),
        onChanged: (value) async {
          onTodoChanged(todo.setDone(value!));
        },
      ),
      title: Text(
        todo.title,
        style: TextStyle(
          fontSize: 16,
          decoration: todo.done ? TextDecoration.lineThrough : null,
        ),
      ),
      subtitle: Row(
        mainAxisSize: MainAxisSize.min,
        children: [
          const Icon(Icons.calendar_month, size: 12),
          const SizedBox(width: 8),
          Text(
            todo.due == null
                ? "Today"
                : "${todo.due!.day}/${todo.due!.month}/${todo.due!.year}",
            style: TextStyle(
                fontSize: 12, color: todo.overdue ? Colors.red : null),
          ),
        ],
      ),
    );
  }
}
