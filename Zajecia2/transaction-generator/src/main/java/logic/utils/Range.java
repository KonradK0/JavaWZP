package logic.utils;

public class Range<T> {
    private T lowBound;
    private T upBound;

    public Range(T lowBound, T upBound) {
        this.lowBound = lowBound;
        this.upBound = upBound;
    }

    public T getLowBound() {
        return lowBound;
    }

    public T getUpBound() {
        return upBound;
    }

    public void setLowBound(T lowBound) {
        this.lowBound = lowBound;
    }

    public void setUpBound(T upBound) {
        this.upBound = upBound;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Range<?> range = (Range<?>) o;

        if (!lowBound.equals(range.lowBound)) return false;
        return upBound.equals(range.upBound);
    }

    @Override
    public int hashCode() {
        int result = lowBound.hashCode();
        result = 31 * result + upBound.hashCode();
        return result;
    }
}
