package cloudladder.core.object;

import cloudladder.core.error.CLRuntimeError;
import cloudladder.core.error.CLRuntimeErrorType;

import java.util.ArrayList;

@CLObjectAnnotation(typeIdentifier = "number")
public class CLNumber extends CLObject {
    public double value;
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

    public boolean isInteger() {
        return this.value % 1 == 0;
    }

    @Override
    public CLObject add(CLObject other) throws CLRuntimeError {
        if (other instanceof CLNumber) {
            double result = this.value + ((CLNumber) other).value;
            return CLNumber.getNumberObject(result);
        }

        throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting number");
    }

    @Override
    public CLObject sub(CLObject other) throws CLRuntimeError {
        if (other instanceof CLNumber) {
            double result = this.value - ((CLNumber) other).value;
            return CLNumber.getNumberObject(result);
        }

        throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting number");
    }

    @Override
    public CLObject mul(CLObject other) throws CLRuntimeError {
        if (other instanceof CLNumber) {
            double result = this.value * ((CLNumber) other).value;
            return CLNumber.getNumberObject(result);
        }

        throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting number");
    }

    @Override
    public CLObject div(CLObject other) throws CLRuntimeError {
        if (other instanceof CLNumber) {
            double result = this.value / ((CLNumber) other).value;
            return CLNumber.getNumberObject(result);
        }

        throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting number");
    }

    @Override
    public CLObject mod(CLObject other) throws CLRuntimeError {
        if (other instanceof CLNumber) {
            double result = this.value % ((CLNumber) other).value;
            return CLNumber.getNumberObject(result);
        }

        throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting number");
    }

    @Override
    public CLObject ne(CLObject other) throws CLRuntimeError {
        if (other instanceof CLNumber) {
            return CLBoolean.get(this.value == ((CLNumber) other).value);
        }
        return CLBoolean.boolTrue;
    }

    @Override
    public CLObject eq(CLObject other) throws CLRuntimeError {
        if (other instanceof CLNumber) {
            boolean result = this.value == ((CLNumber) other).value;
            return CLBoolean.get(result);
        }
        return CLBoolean.boolFalse;
    }

    @Override
    public CLObject gt(CLObject other) throws CLRuntimeError {
        if (other instanceof CLNumber) {
            return CLBoolean.get(this.value > ((CLNumber) other).value);
        }

        throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting number");
    }

    @Override
    public CLObject ge(CLObject other) throws CLRuntimeError {
        if (other instanceof CLNumber) {
            return CLBoolean.get(this.value >= ((CLNumber) other).value);
        }

        throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting number");
    }

    @Override
    public CLObject lt(CLObject other) throws CLRuntimeError {
        if (other instanceof CLNumber) {
            return CLBoolean.get(this.value < ((CLNumber) other).value);
        }

        throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting number");
    }

    @Override
    public CLObject le(CLObject other) throws CLRuntimeError {
        if (other instanceof CLNumber) {
            return CLBoolean.get(this.value <= ((CLNumber) other).value);
        }

        throw new CLRuntimeError(CLRuntimeErrorType.TypeError, "expecting number");
    }

    @Override
    public CLObject neg() throws CLRuntimeError {
        return CLNumber.getNumberObject(-this.value);
    }

    @Override
    public String toString() {
        if (this.value % 1 == 0) {
            return Integer.toString(((int) this.value));
        } else {
            return Double.toString(this.value);
        }
    }
}
