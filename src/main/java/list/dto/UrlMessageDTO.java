package list.dto;

/**
 * Created by Administrator on 2018/4/1.
 */
public class UrlMessageDTO extends TestMessageDTO{
    private     String Title;
    private     String Description;
    private     String Url;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
