
package kesean.com.anotherweatherapp.data.model;

import com.google.gson.annotations.SerializedName;

public class Weather {

    @SerializedName("description")
    private String mDescription;
    @SerializedName("icon")
    private String mIcon;
    @SerializedName("main")
    private String mMain;
    @SerializedName("id")
    private Long mId;

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getMain() {
        return mMain;
    }

    public void setMain(String main) {
        mMain = main;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }


}
