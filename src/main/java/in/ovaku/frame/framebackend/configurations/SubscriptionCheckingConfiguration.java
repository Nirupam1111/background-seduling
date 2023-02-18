package in.ovaku.frame.framebackend.configurations;

import in.ovaku.frame.framebackend.dtos.commons.BusinessDto;
import in.ovaku.frame.framebackend.dtos.responses.SubscriptionResponseDto;
import in.ovaku.frame.framebackend.services.BusinessServices;
import in.ovaku.frame.framebackend.services.SubscriptionService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;
import java.util.List;

@Configuration
public class SubscriptionCheckingConfiguration {
    private static final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
    private final BusinessServices businessServices;
    private final SubscriptionService subscriptionService;

    public SubscriptionCheckingConfiguration(SubscriptionService subscriptionService, BusinessServices businessServices) {
        this.subscriptionService = subscriptionService;
        this.businessServices = businessServices;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void checkSubscription() {
        List<BusinessDto> businesses = businessServices.getAll(true);
        List<SubscriptionResponseDto> subscriptions = subscriptionService.getAll(true);

        for (SubscriptionResponseDto subscription : subscriptions) {
            long endDate = subscription.getEndDate().getTime();
            long now = new Date().getTime();

            if (now + MILLIS_IN_A_DAY > endDate) {
                subscriptionService.delete(subscription.getId());
            }
        }
    }

}
