package ru.javawebinar.topjava.service.mealServiceTests;

import org.springframework.test.context.ActiveProfiles;

import static org.slf4j.LoggerFactory.getLogger;


//@ActiveProfiles(resolver = ActiveDbProfileResolver.class)
@ActiveProfiles(profiles = {"jpa"})
public class MealServiceJpaTest extends AbstractMealServiceTest {
}