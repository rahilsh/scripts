package in.rsh.script.common;

import static java.lang.System.exit;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class TestScript {

  private final String value;

  @Inject
  public TestScript(@Named("testKey") String value) {
    this.value = value;
  }

  public static void main(String[] args) {
    TestScript acao = Main.getInjector().getInstance(TestScript.class);
    acao.process();
    exit(0);
  }

  private void process() {
    System.out.println(value);
  }
}
