package demo.moviecatalogservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfoService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallBackCatalogeItems")
    public CatalogeItem getCatalogeItem(Rating rating) {
        Movie m = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
        return new CatalogeItem(m.getName(), "", rating.getRating());
    }

    private CatalogeItem getFallBackCatalogeItems(Rating rating) {
        System.out.println("getFallBackCatalogeItems");
        return new CatalogeItem("Movie name not found","", rating.getRating());
    }
}
