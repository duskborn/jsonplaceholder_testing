package model;

public class BaseModel implements Cloneable{
    @Override
    public Object clone() {
        try {
            Object o = super.clone();
            return o;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
