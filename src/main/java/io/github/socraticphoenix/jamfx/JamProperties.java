package io.github.socraticphoenix.jamfx;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Represents a set of properties, i.e. a {@code Map<String, ?>}.
 *
 * <p>This class offers additional methods for getting properties of a certain type through {@link
 * JamProperties#get(String, Class)} and {@link JamProperties#require(String, Class)}.
 */
public class JamProperties {
  private Map<String, Object> properties = new LinkedHashMap<>();

  public Map<String, Object> getProperties() {
    return properties;
  }

  public JamProperties copy() {
    JamProperties copy = new JamProperties();
    copy.properties.putAll(this.properties);
    return copy;
  }

  /**
   * @param key The key to check
   * @return true if there exists a value for the given {@code key}
   */
  public boolean contains(String key) {
    return this.properties.containsKey(key);
  }

  /**
   * @param key The key to check
   * @return true if there exists a value for the given {@code key}, and that value ins nonnull
   */
  public boolean nonnull(String key) {
    return this.properties.get(key) != null;
  }

  /**
   * Puts the given {@code val} into the map under the given {@code key}.
   *
   * @param key The key
   * @param val The value
   * @return This, for method chaining.
   */
  public JamProperties put(String key, Object val) {
    this.properties.put(key, val);
    return this;
  }

  /**
   * Puts the given {@code val} into the map under the given {@code key}, as long as {@code key} is
   * not already present in the map.
   *
   * @param key The key
   * @param val The value
   * @return This, for method chaining.
   */
  public JamProperties putIfAbsent(String key, Object val) {
    this.properties.putIfAbsent(key, val);
    return this;
  }

  /**
   * Gets a property of the specified type. If the property does not exist, or is not of the
   * specified type, an empty Optional is returned.
   *
   * @param key The property key
   * @param type The property type
   * @param <T> The property type
   * @return The property at the key, or an empty Optional if no property of the given type exists.
   */
  public <T> Optional<T> get(String key, Class<T> type) {
    Object value = this.properties.get(key);

    if (type.isInstance(value)) {
      return Optional.of((T) value);
    } else {
      return Optional.empty();
    }
  }

  /**
   * Gets a property of the specified type. If the property does not exist, or is not of the
   * specified type, a {@link JamPropertyRequiredException} is thrown.
   *
   * @param key The property key
   * @param type The property type
   * @param <T> The property type
   * @throws JamPropertyRequiredException if there is not a property of the given type mapped to the
   *     given key
   * @return The property at the key.
   */
  public <T> T require(String key, Class<T> type) {
    return get(key, type)
        .orElseThrow(
            () ->
                new JamPropertyRequiredException(
                    "Required "
                        + type.getName()
                        + " at key \""
                        + key
                        + "\", got "
                        + (contains(key) ? this.properties.get(key) : "<no mapping>")));
  }

  /**
   * Equivalent to {@code Properties::get(key, Object.class)}
   *
   * @see JamProperties#get(String, Class)
   */
  public Optional<Object> get(String key) {
    return get(key, Object.class);
  }

  /**
   * Equivalent to {@code Properties::require(key, Object.class)}
   *
   * @see JamProperties#require(String, Class)
   */
  public Object require(String key) {
    return require(key, Object.class);
  }

  /**
   * Equivalent to {@code Properties::get(key, String.class)}
   *
   * @see JamProperties#get(String, Class)
   */
  public Optional<String> getString(String key) {
    return this.get(key, String.class);
  }

  /**
   * Equivalent to {@code Properties::require(key, String.class)}
   *
   * @see JamProperties#require(String, Class)
   */
  public String requireString(String key) {
    return this.require(key, String.class);
  }

  /**
   * Equivalent to {@code Properties::get(key, Boolean.class)}
   *
   * @see JamProperties#get(String, Class)
   */
  public Optional<Boolean> getBoolean(String key) {
    return this.get(key, Boolean.class);
  }

  /**
   * Equivalent to {@code Properties::require(key, Boolean.class)}
   *
   * @see JamProperties#require(String, Class)
   */
  public boolean requireBoolean(String key) {
    return this.require(key, Boolean.class);
  }

  /**
   * Equivalent to {@code Properties::get(key, Byte.class)}
   *
   * @see JamProperties#get(String, Class)
   */
  public Optional<Byte> getByte(String key) {
    return this.get(key, Byte.class);
  }

  /**
   * Equivalent to {@code Properties::require(key, Byte.class)}
   *
   * @see JamProperties#require(String, Class)
   */
  public byte requireByte(String key) {
    return this.require(key, Byte.class);
  }

  /**
   * Equivalent to {@code Properties::get(key, Short.class)}
   *
   * @see JamProperties#get(String, Class)
   */
  public Optional<Short> getShort(String key) {
    return this.get(key, Short.class);
  }

  /**
   * Equivalent to {@code Properties::require(key, Short.class)}
   *
   * @see JamProperties#require(String, Class)
   */
  public short requireShort(String key) {
    return this.require(key, Short.class);
  }

  /**
   * Equivalent to {@code Properties::get(key, Integer.class)}
   *
   * @see JamProperties#get(String, Class)
   */
  public Optional<Integer> getInt(String key) {
    return this.get(key, Integer.class);
  }

  /**
   * Equivalent to {@code Properties::require(key, Long.class)}
   *
   * @see JamProperties#require(String, Class)
   */
  public int requireInt(String key) {
    return this.require(key, Integer.class);
  }

  /**
   * Equivalent to {@code Properties::get(key, Long.class)}
   *
   * @see JamProperties#get(String, Class)
   */
  public Optional<Long> getLong(String key) {
    return this.get(key, Long.class);
  }

  /**
   * Equivalent to {@code Properties::require(key, Long.class)}
   *
   * @see JamProperties#require(String, Class)
   */
  public long requireLong(String key) {
    return this.require(key, Long.class);
  }

  /**
   * Equivalent to {@code Properties::get(key, Float.class)}
   *
   * @see JamProperties#get(String, Class)
   */
  public Optional<Float> getFloat(String key) {
    return this.get(key, Float.class);
  }

  /**
   * Equivalent to {@code Properties::require(key, Float.class)}
   *
   * @see JamProperties#require(String, Class)
   */
  public float requireFloat(String key) {
    return this.require(key, Float.class);
  }

  /**
   * Equivalent to {@code Properties::get(key, Double.class)}
   *
   * @see JamProperties#get(String, Class)
   */
  public Optional<Double> getDouble(String key) {
    return this.get(key, Double.class);
  }

  /**
   * Equivalent to {@code Properties::require(key, Double.class)}
   *
   * @see JamProperties#require(String, Class)
   */
  public double requireDouble(String key) {
    return this.require(key, Double.class);
  }
}
