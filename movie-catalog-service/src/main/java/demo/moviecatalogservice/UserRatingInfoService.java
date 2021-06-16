package demo.moviecatalogservice;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class UserRatingInfoService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallBackUserRating",
                    commandProperties = {
                            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "50"),
                            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
                            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "1000"),
                            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "10"),
                            @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"),
                            @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "20000"),
                            @HystrixProperty(name = "metrics.rollingPercentile.timeInMilliseconds", value = "20000"),
                            @HystrixProperty(name = "metrics.healthSnapshot.intervalInMilliseconds", value = "5000"),
                            @HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests", value = "100")
                    })
    public UserRating getUserRating(String userId) {
        return restTemplate.getForObject("http://rating-data-service/ratingData/users/" + userId, UserRating.class);
    }

    private UserRating getFallBackUserRating(String userId) {
        System.out.println("getFallBackUserRating");
        UserRating userRating = new UserRating();
        userRating.setUserRating(Arrays.asList(new Rating("No Rating",0)));
        return userRating;
    }
}
