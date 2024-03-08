import 'package:confetti/confetti.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:wear_example/repositories/todo.dart';

class MarkTodoAsDone extends StatefulWidget {
  const MarkTodoAsDone({super.key});

  @override
  State<MarkTodoAsDone> createState() => _MarkTodoAsDoneState();
}

class _MarkTodoAsDoneState extends State<MarkTodoAsDone> {
  late ConfettiController _controllerTopCenter;

  @override
  void initState() {
    super.initState();
    _controllerTopCenter = ConfettiController(duration: const Duration(seconds: 3));
  }

  @override
  Widget build(BuildContext context) {
    return FutureBuilder(
      future: () async {
        const repo = TodoRepository();
        final todo = (await repo.getAll()).firstWhere((t) => !t.done);
        repo.update(todo.setDone(true));

        //
        Future.delayed(
          const Duration(seconds: 5),
          () {
            SystemNavigator.pop();
          },
        );
      }(),
      builder: (context, snapshot) {
        if (snapshot.connectionState != ConnectionState.done) {
          return const Center(child: CircularProgressIndicator());
        }

        _controllerTopCenter.play();
        return Stack(
          children: [
            Center(
              child: Column(
                mainAxisSize: MainAxisSize.min,
                children: [
                  const Icon(
                    Icons.check,
                    size: 48,
                  ),
                  Text(
                    "Well done !",
                    style: Theme.of(context).textTheme.headlineSmall,
                  ),
                ],
              ),
            ),
            Align(
              alignment: Alignment.topCenter,
              child: ConfettiWidget(
                confettiController: _controllerTopCenter,
                blastDirection: 3.14 / 2,
                maxBlastForce: 5, // set a lower max blast force
                minBlastForce: 2, // set a lower min blast force
                emissionFrequency: 0.2,
                numberOfParticles: 10, // a lot of particles at once
                gravity: 0.2,
              ),
            ),
          ],
        );
      },
    );
  }

  @override
  void dispose() {
    super.dispose();
    _controllerTopCenter.dispose();
  }
}
