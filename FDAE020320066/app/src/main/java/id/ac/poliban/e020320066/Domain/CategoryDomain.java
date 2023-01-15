package id.ac.poliban.e020320066.Domain;

public class CategoryDomain {
    private String title;
    private String pic;

    //Add Constructor
    public CategoryDomain(String title, String pic) {
        this.title = title;
        this.pic = pic;
    }

    //Get and Setter

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
