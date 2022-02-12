import com.love.githubrepo.data.api_reponse.RepositryApiResponse
import com.love.githubrepo.data.bean.Repositry
import io.reactivex.Observable
import retrofit2.http.GET

/**
 *
 * This class contains all api methods with its url and parameters.
 */

interface Api {


    @GET("developers")
    fun getDevelopers(): Observable<ArrayList<Repositry>>

}
