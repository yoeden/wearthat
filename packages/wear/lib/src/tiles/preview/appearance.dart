/// Appearance of a watch device
///
class WatchDeviceAppearance {
  /// The width of the device (divide by 2 of original width)
  final double width;

  /// The height of the device (divide by 2 of original height)
  final double height;

  /// Indicates whether the device is circular
  final bool isCircular;

  const WatchDeviceAppearance({
    required this.width,
    required this.height,
    required this.isCircular,
  });
}
