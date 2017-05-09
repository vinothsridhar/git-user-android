package sri.git.user.data;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;
import sri.git.user.model.GitRepo;
import sri.git.user.model.GitUser;

/**
 * Created by sridhar on 9/5/17.
 */

public interface GitUserRestService {

    @GET Observable<GitUser> fetchUsers(@Url String url);

    @GET Observable<GitRepo> fetchRepos(@Url String url);

}
