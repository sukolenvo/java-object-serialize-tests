A sample project to reproduce issue with object serialization with java.io.ObjectInputStream / java.io.ObjectOutputStream.

## About

When multiple Lists serialized, deserialization fails with `java.lang.ClassNotFoundException`. ObjectInputStream holds reference
to application classloader. When nested Lists deserialized this reference is lost - and classloading for subsequent objects is
not possible.

For snippet below, class loader reference is lost while deserializing ClassA, hence deserialization of ClassB fails

```java
ObjectOutputStream ois = new ObjectOutputStream(...);
ois.writeObject(List.of());
ois.writeObject(List.of(new ClassA(), new ClassB()));

public static class ClassA implements Serializable {
  Object a = List.of(0);
  Object c = new NestedClassA();
}

public static class ClassB implements Serializable {
}

public static class NestedClassA implements Serializable {
}
```

## References

Affected versions, IBM Semeru 17.0.5+, 21

Related issues: https://github.com/gwtproject/gwt/issues/9789
