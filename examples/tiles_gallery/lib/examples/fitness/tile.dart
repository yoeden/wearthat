import 'package:wearthat/src/tiles/core/resource.dart';
import 'package:wearthat/tiles.dart';
import 'package:flutter/material.dart' as m;
import 'dart:math' as math;

class FitnessData {
  final int calories;
  final int steps;

  FitnessData({
    required this.calories,
    required this.steps,
  });
}

class FitnessTileState {
  final FitnessData goal;
  final FitnessData current;

  FitnessTileState({
    required this.goal,
    required this.current,
  });

  double get caloriesProgress => (current.calories / goal.calories);
  double get stepsProgress => (current.steps / goal.steps);

  factory FitnessTileState.fromJson(Map<String, dynamic> json) {
    return FitnessTileState(
      goal: FitnessData(
        calories: json["goal"]["calories"],
        steps: json["goal"]["steps"],
      ),
      current: FitnessData(
        calories: json["current"]["calories"],
        steps: json["current"]["steps"],
      ),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      "goal": {
        "calories": goal.calories,
        "steps": goal.steps,
      },
      "current": {
        "calories": current.calories,
        "steps": current.steps,
      }
    };
  }
}

class FitnessTile extends Tile<FitnessTileState> {
  @override
  Future<FitnessTileState?> createState() {
    final rnd = math.Random();
    final goal = FitnessData(
      calories: rnd.nextInt(2000),
      steps: rnd.nextInt(6000),
    );

    return Future.value(
      FitnessTileState(
        goal: goal,
        current: FitnessData(
          calories: rnd.nextInt(goal.calories),
          steps: rnd.nextInt(goal.steps),
        ),
      ),
    );
  }

  @override
  TileWidget build(TileContext context, FitnessTileState? state) {
    return Padding(
      padding: EdgeInsets.all(8.0),
      child: Stack(
        children: [
          _fitnessLevel(state!),
          ArcLayout(
            anchor: 225,
            children: [
              //Steps
              ArcProgressLine(
                length: 90,
                progress: state.stepsProgress,
                direction: ArcProgressDirection.clockwise,
                style: ArcProgressLineStyle(
                  thickness: 8,
                  color: m.Colors.blue.shade300,
                  backgroundColor: m.Colors.grey.shade700,
                ),
              ),
              ArcSpacer(length: 90),
              //Calories
              ArcProgressLine(
                length: 90,
                progress: state.caloriesProgress,
                direction: ArcProgressDirection.counterClockwise,
                style: ArcProgressLineStyle(
                  thickness: 8,
                  color: m.Colors.orange.shade300,
                  backgroundColor: m.Colors.grey.shade700,
                ),
              ),
              ArcSpacer(length: 6),
              ArcAdapter(
                child: SizedBox(
                  height: 16,
                  width: 16,
                  child: Image('kal', fit: BoxFit.fill),
                ),
              ),
              ArcSpacer(length: 50),
              ArcAdapter(
                child: SizedBox(
                  height: 16,
                  width: 16,
                  child: Image('steps', fit: BoxFit.fill),
                ),
              ),
            ],
          ),
        ],
      ),
    );
  }

  TileWidget _fitnessLevel(FitnessTileState state) {
    final levels = [
      MapEntry('😭', 'Stop being lazy !'),
      MapEntry('😒', 'You can do better !'),
      MapEntry('🙃', 'Almost there !'),
      MapEntry('🤩', 'Goal reacher !'),
    ];

    final progress = (state.caloriesProgress + state.stepsProgress) / 2;
    final level = progress ~/ (1 / levels.length);

    return Column(
      mainAxisSize: MainAxisSize.min,
      children: [
        Text(
          levels[level].key,
          maxLines: 1,
          textAlign: TextAlign.center,
          style: TextStyle.display3(),
        ),
        Text(
          levels[level].value,
          maxLines: 1,
          textAlign: TextAlign.center,
          style: TextStyle.caption1(),
        ),
      ],
    );
  }

  @override
  Map<String, TileResourceProvider> resources(context, state) => {
        'steps': TileResources.asset("assets/fitness/footsteps.png"),
        'kal': TileResources.asset("assets/fitness/calories.png"),
      };
}
