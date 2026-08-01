package in.rsh.scripts;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.google.inject.Injector;
import org.junit.jupiter.api.Test;

class MainTest {

  @Test
  void injectorIsCreated() {
    Injector injector = Main.getInjector();
    assertNotNull(injector, "Guice injector should be created");
  }

  @Test
  void injectorIsSingleton() {
    assertSame(
        Main.getInjector(),
        Main.getInjector(),
        "getInjector() should return the same cached instance");
  }
}
