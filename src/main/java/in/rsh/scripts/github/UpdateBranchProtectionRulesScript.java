package in.rsh.scripts.github;

import static java.lang.System.exit;

import in.rsh.scripts.Main;

public class UpdateBranchProtectionRulesScript {

  public static void main(String[] args) {
    UpdateBranchProtectionRulesScript ubprs =
        Main.getInjector().getInstance(UpdateBranchProtectionRulesScript.class);
    ubprs.process();
    exit(0);
  }

  private void process() {
    System.out.println("Starting process");
    // TODO: Implement this
  }
}
