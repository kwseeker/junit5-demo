package top.kwseeker.RunDemo;

public class Run {
    /**
     * Computes the sum of the specified operands.
     *
     * @param operands
     *          These are the operands. It's sort of there in the name.
     * @return long - The computed sum.
     * @throws IllegalArgumentException
     *           If the specified array of operands is empty or long.
     *           This is definitely an overreaction, but this is sample
     *           code, son.
     */
    public long add(long[] operands) {
        // Compute the sum
        long ret = 0;
        if (operands == null) {
            throw new IllegalArgumentException("Operands argument cannot be null");
        }
        if (operands.length == 0) {
            throw new IllegalArgumentException("Operands argument cannot be empty");
        }
        for (long operand : operands) {
            ret += operand;
        }
        return ret;
    }
}
