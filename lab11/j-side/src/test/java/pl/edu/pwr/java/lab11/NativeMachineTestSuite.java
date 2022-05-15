package pl.edu.pwr.java.lab11;

import org.junit.platform.suite.api.IncludeTags;
import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectPackages("pl.edu.pwr.java.lab11")
@IncludeTags("native-sorting")
public class NativeMachineTestSuite {
}
