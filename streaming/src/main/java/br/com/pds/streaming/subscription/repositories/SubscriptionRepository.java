package br.com.pds.streaming.subscription.repositories;

import br.com.pds.streaming.subscription.model.entities.Subscription;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SubscriptionRepository extends MongoRepository<Subscription, String> {
}
