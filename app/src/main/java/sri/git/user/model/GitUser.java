package sri.git.user.model;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by sridhar on 9/5/17.
 */

@DatabaseTable(tableName = "git_user")
public class GitUser implements Serializable {

    @DatabaseField(generatedId = true)
    @SerializedName("id")
    private int id;

    @DatabaseField
    @SerializedName("login")
    private String login;

    @DatabaseField
    @SerializedName("avatar_url")
    private String avatar_url;

    @DatabaseField
    @SerializedName("repos_url")
    private String repos_url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatarUrl() {
        return this.avatar_url;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatar_url = avatarUrl;
    }

    public String getReposUrl() {
        return this.repos_url;
    }

    public void setReposUrl(String reposUrl) {
        this.repos_url = reposUrl;
    }

}
