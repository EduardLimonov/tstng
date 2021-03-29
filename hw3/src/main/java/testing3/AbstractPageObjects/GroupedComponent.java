package testing3.AbstractPageObjects;

public interface GroupedComponent extends MyElement {
    int numberOfElements();
    SelectableElement getElement(int i);
}