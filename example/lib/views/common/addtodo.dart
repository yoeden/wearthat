import 'package:flutter/material.dart';

class AddTodo extends StatefulWidget {
  const AddTodo({super.key});

  @override
  State<AddTodo> createState() => _AddTodoState();
}

class _AddTodoState extends State<AddTodo> {
  final TextEditingController _text = TextEditingController();

  @override
  Widget build(BuildContext context) {
    final theme = Theme.of(context);

    return Padding(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        mainAxisSize: MainAxisSize.min,
        children: [
          Text("Todo: ", style: theme.textTheme.headlineSmall),
          TextField(controller: _text),
          const SizedBox(height: 16),
          Center(
            child: FloatingActionButton(
              onPressed: () {
                Navigator.of(context).pop(_text.text);
              },
              child: const Icon(Icons.check),
            ),
          ),
        ],
      ),
    );
  }
}
