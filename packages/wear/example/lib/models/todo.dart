class Todo {
  final int id;
  final String title;
  final bool done;
  final DateTime? due;

  Todo({
    required this.id,
    required this.title,
    this.done = false,
    this.due,
  });

  bool get overdue => due != null && due!.isBefore(DateTime.now());

  Todo setDone(bool done) => Todo(id: id, title: title, done: done, due: due);

  factory Todo.fromJson(Map<String, dynamic> json) {
    return Todo(
      id: json['id'],
      title: json['title'],
      done: json['done'],
      due: json['due'] != null ? DateTime.fromMillisecondsSinceEpoch(json['due']) : null,
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'title': title,
      'done': done,
      'due': due?.millisecondsSinceEpoch,
    };
  }
}
