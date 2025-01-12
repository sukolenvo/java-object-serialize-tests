import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author vadym
 */
public class ObjectSerializeTest {

  @ParameterizedTest
  @MethodSource("testCases")
  void test(Object param1, Object param2) throws Exception {
    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    ObjectOutputStream ois = new ObjectOutputStream(buffer);
    ois.writeObject(param1);
    ois.writeObject(param2);
    byte[] bytes2 = buffer.toByteArray();
    ObjectInputStream ois2 = new ObjectInputStream(new ByteArrayInputStream(bytes2));
    Assertions.assertArrayEquals(new Object[]{param1, param2}, new Object[]{ois2.readObject(), ois2.readObject()});
  }

  static Stream<Arguments> testCases() {
    return Stream.of(
        Arguments.of(new ClassA(), new ClassB()),
        Arguments.of(List.of(), List.of(new ClassA(), new ClassB())),
        Arguments.of(List.of(new ClassA(), new ClassB()), List.of()),
        Arguments.of(List.of(), List.of(new ClassB(), new ClassA()))
    );
  }

  public static class ClassA implements Serializable {

    Object a = List.of(0);
    Object c = new NestedClassA();

    @Override
    public String toString() {
      return "ClassA";
    }

    @Override
    public boolean equals(Object o) {
      return o instanceof ClassA;
    }

    @Override
    public int hashCode() {
      return 7;
    }
  }

  public static class ClassB implements Serializable {

    @Override
    public String toString() {
      return "ClassB";
    }

    @Override
    public boolean equals(Object o) {
      return o instanceof ClassB;
    }

    @Override
    public int hashCode() {
      return 13;
    }
  }

  public static class NestedClassA implements Serializable {
  }
}
