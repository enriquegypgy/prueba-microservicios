package demo.moviecatalogservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

/*    @Autowired
    private WebClient.Builder webClientBuilder;*/

    @Autowired
    MovieInfoService movieInfoServiceo;

    @Autowired
    UserRatingInfoService userRatingInfoService;

    @RequestMapping("{userId}")
    private List<CatalogeItem> catalog(@PathVariable("userId") String userId) {
        System.out.println("catalog");
        UserRating ur = userRatingInfoService.getUserRating(userId);
        return ur.getUserRating().stream()
                            .map(rating -> {return movieInfoServiceo.getCatalogeItem(rating);})
                            .collect(Collectors.toList());
    }
}
