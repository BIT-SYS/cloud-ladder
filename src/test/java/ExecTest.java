import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class ExecTest {
    static Tester tester = new AstVisitorTest();

    public static void main(String[] args) throws IOException {
        tester.start("examples/2-control-3-while.cl");
//        tester.start("examples/0-basic-1-test-type-simple.cl");
    }

    @Test
    public void IterTest() throws IOException {
        File file = new File("examples");
        File[] fs = file.listFiles();
        assert fs != null;
        for (File f : fs) {
            if (f.isFile()) {
                tester.start(f.getAbsolutePath());
            }
        }
    }
}
