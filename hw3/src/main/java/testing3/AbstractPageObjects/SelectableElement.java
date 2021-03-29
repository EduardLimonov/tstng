package testing3.AbstractPageObjects;

public interface SelectableElement extends MyElement {
    void select(int what);

    boolean isSelected();
}
