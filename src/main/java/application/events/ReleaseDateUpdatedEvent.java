package application.events;

import java.time.LocalDate;
import java.util.UUID;

public record ReleaseDateUpdatedEvent( UUID prisonerId, LocalDate newReleaseDate, ActivityType activityType, int daysReduced) { }
