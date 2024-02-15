package yoeden.flutter.wear.base.channel;

public interface Transformer<T> {
    T transform(Object value);
}
