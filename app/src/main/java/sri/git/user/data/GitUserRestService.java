package sri.git.user.data;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;
import sri.git.user.model.GitRepo;
import sri.git.user.model.GitUser;

/**
 * Created by sridhar on 9/5/17.
 */

public interface GitUserRestService {

    @GET Observable<List<GitUser>> fetchUsers(@Url String url);

    @GET Observable<List<GitRepo>> fetchRepos(@Url String url);

}
