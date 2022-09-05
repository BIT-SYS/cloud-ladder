package cloudladder.core.object;

import java.util.ArrayList;

public class CLNumber extends CLObject {
    private double value;
    private static CLNumberPool pool = new CLNumberPool();

    public static class CLNumberPool {
        private ArrayList<CLNumber> pool;
        private static final int LOWER = -100;
        private static final int UPPER = 100;

        CLNumberPool() {
            this.pool = new ArrayList<>();

            for (int i = CLNumberPool.LOWER; i <= CLNumberPool.UPPER; i++) {
                CLNumber obj = new CLNumber((double) i);
                this.pool.add(obj);
            }
        }
    }

    public CLNumber(double value) {
        this.value = value;
    }

    public static CLNumber getNumberObject(double value) {
        if ((value % 1) == 0) {
            // an integer
            int value2 = (int) value;
            if (value2 >= CLNumberPool.LOWER && value2 <= CLNumberPool.UPPER) {
                return CLNumber.pool.pool.get(value2 - CLNumberPool.LOWER);
            } else {
                return new CLNumber(value);
            }
        } else {
            // a float
            return new CLNumber(value);
        }
    }

    @Override
    public CLObject add(CLObject other) {
        if (other instanceof CLNumber) {
            double result = this.value + ((CLNumber) other).value;
            return CLNumber.getNumberObject(result);
        }

        // todo
        return null;
    }
}
