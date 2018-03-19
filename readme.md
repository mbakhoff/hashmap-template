# How does a hashmap work?

A map contains key-value pairs. 
Pairs can be added and values can be looked up by the key.
Adding a key-value pair when the key already exists in the map will replace the existing value.

A naive map holds a list of key-value pairs: `List<Pair<KeyType, ValueType>>`.
To look up a value, each key-value pair in the list must be inspected.
When the number of pairs gets large, then the lookups will get slow.

Hashmap is faster and smarter than that.
A hashmap doesn't have a single list for all pairs.
It instead contains a lot of small lists, called **buckets**.

The key-value pair is placed in only one of the buckets.
The Object class in Java defines the method `int hashCode()`.
The bucket is selected based on the key: the hashmap calls `key.hashCode()` and calculates the bucket number.
When looking up a pair from the hashmap, only the elements of a single small list must be inspected.

What does the hashCode method do?
For the Integer class: returns the integer value.
For the String class:
```java
@Override
public int hashCode() {
  int hash = 0;
  for (char c : characters)
    hash = 31 * hash + c;
  return hash;
}
``` 
  
If your class needs to be useful as a hashmap key, then you must override the hashCode method.
The result of hashCode should return: 
* same value when called multiple times
* same value for identical objects
* different value for different objects

```java
class SimpleHashMap<KeyType, ValueType> {
  
  List<Pair<KeyType, ValueType>>[] buckets = new List[1000];
  
  void put(KeyType key, ValueType value) {
    List<Pair<KeyType, ValueType>> bucket = findBucket(key);
    Pair<KeyType, ValueType> pair = findPairByKey(bucket, key);
    if (pair != null)
      pair.setValue(value);
    else
      bucket.add(new Pair(key, value));
  }
  
  ValueType get(KeyType key) {
    List<Pair<KeyType, ValueType>> bucket = findBucket(key);
    Pair<KeyType, ValueType> pair = findPairByKey(bucket, key);
    if (pair == null)
      return null;
    return pair.getValue();
  }
  
  List<Pair<KeyType, ValueType>> findBucket(KeyType key) {
    // use remainder of division. result is [0; buckets.length[
    int bucketToUse = key.hashCode() % buckets.length;
    return buckets[bucketToUse];
  }
  
  Pair<KeyType, ValueType> findPairByKey(List<Pair<KeyType, ValueType>>bucket, KeyType key) {
    for (Pair<KeyType, ValueType> pair : bucket) {
      if (pair.getKey().equals(key)) // uses equals, not ==
        return pair;
    }
    return null;
  }
}
```

The equals method in the Object class uses == to compare the two objects:
```java
public boolean equals(Object other) {
  return this == other;
}
```

If your class needs to be useful as a hashmap key, then you must also override the equals method.
A good hashmap key must compare the actual object contents.
For example, the String class compares its characters with the other String's characters.

Here's an example of a correct key:

```java
class Person {

  // fields can never change, otherwise the hashCode could change as well
  final String firstName, lastName;
  
  Person(String firstName, String lastName) {
    if (firstName == null || lastName == null)
      throw new IllegalArgumentException();
    this.firstName = firstName;
    this.lastName = lastName;
  }
  
  @Override
  public int hashCode() {
    return 31 * firstName.hashCode() + lastName.hashCode();
  }
  
  @Override
  public boolean equals(Object other) {
    if (other == null || getClass() != other.getClass())
      return false;
    Person otherPerson = (Person) other;
    return firstName.equals(otherPerson.firstName) &&
           lastName.equals(otherPerson.lastName);
  }
}
```
