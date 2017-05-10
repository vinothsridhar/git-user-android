package sri.git.user.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sridhar on 9/5/17.
 */

public class GitRepo implements Serializable {

    @SerializedName("name")
    private String name;

    @SerializedName("owner")
    private GitUser owner;

    public String getName() {
        return name;
    }

    public GitUser getOwner() {
        return owner;
    }

}
