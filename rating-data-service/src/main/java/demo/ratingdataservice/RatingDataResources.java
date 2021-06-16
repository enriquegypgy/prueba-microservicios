package demo.ratingdataservice;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingData")
public class RatingDataResources {

    @RequestMapping("{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId) {
        return new Rating(movieId, 5);
    }

    @RequestMapping("/users/{userId}")
    public UserRating getUserRating(@PathVariable("userId") String userId) {
        List<Rating> ratings = Arrays.asList(new Rating("20", 3),
                                             new Rating("21", 5));
        UserRating ur = new UserRating();
        ur.setUserRating(ratings);
        return ur;
    }

}
