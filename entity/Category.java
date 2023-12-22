package entity;

public class Category {
    private String gender;
    private String categoryId;
    private String name;

    public Category() {
    }

    public Category(String categoryId, String gender, String name) {
        this.gender = gender;
        this.categoryId = categoryId;
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return categoryId + "," + gender + "," + name;
    }
}
