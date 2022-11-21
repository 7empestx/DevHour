package dev.hour.model;

public class DietPreferenceItem {
    private String name = "";
    private boolean checked = false;
    public DietPreferenceItem () { }
    public DietPreferenceItem (String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isChecked() {
        return checked;
    }
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    public String toString() {
        return name ;
    }
    public void toggleChecked() {
        checked = !checked ;
    }
}
