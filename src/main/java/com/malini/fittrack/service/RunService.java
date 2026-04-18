package com.malini.fittrack.service;

import com.malini.fittrack.model.Run;
import com.malini.fittrack.model.User;
import com.malini.fittrack.repository.RunRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RunService {

    private final RunRepository runRepository;

    public RunService(RunRepository runRepository) {
        this.runRepository = runRepository;
    }

    public Run logRun(User user, String routeName, double distanceKm,
                      int durationMinutes, java.time.LocalDate runDate) {
        Run run = new Run();
        run.setUser(user);
        run.setRouteName(routeName);
        run.setDistanceKm(distanceKm);
        run.setDurationMinutes(durationMinutes);
        run.setRunDate(runDate);
        return runRepository.save(run);
    }

    public List<Run> getUserRuns(User user) {
        return runRepository.findByUserOrderByRunDateDesc(user);
    }

    public double getTotalDistance(User user) {
        Double total = runRepository.getTotalDistanceByUser(user);
        return total != null ? total : 0.0;
    }

    public double getAvgPace(User user) {
        Double pace = runRepository.getAvgPaceByUser(user);
        return pace != null ? pace : 0.0;
    }

    public String getStats(User user) {
        List<Run> runs = getUserRuns(user);
        double totalKm = getTotalDistance(user);
        double avgPace = getAvgPace(user);
        double bestRun = runs.stream()
                .mapToDouble(Run::getDistanceKm)
                .max()
                .orElse(0.0);

        return String.format(
                "Total runs: %d | Total distance: %.2f km | Avg pace: %.2f min/km | Best run: %.2f km",
                runs.size(), totalKm, avgPace, bestRun);
    }

    public void deleteRun(Long runId) {
        runRepository.deleteById(runId);
    }
}