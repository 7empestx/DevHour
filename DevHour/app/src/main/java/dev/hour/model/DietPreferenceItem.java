package dev.hour.model;

public class DietPreferenceItem {
    private String name = "";
    private boolean checked = false;
    private boolean isDiet = false;
    private boolean isAllergen = true;
    public DietPreferenceItem () { }
    public DietPreferenceItem (String name, boolean isItemADiet) {
        this.name = name;
        if (isItemADiet) {
            this.setAsDiet();
        } else {
            this.setAsAllergen();
        }

    }
    public String getName() {
        return name;
    }
    public boolean isDiet() {
        return isDiet;
    }
    public boolean isAllergen() {
        return isAllergen;
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
    public void setAsDiet() {
        this.isDiet = true;
        this.isAllergen = false;
    }
    public void setAsAllergen() {
        this.isDiet = false;
        this.isAllergen = true;
    }
}
