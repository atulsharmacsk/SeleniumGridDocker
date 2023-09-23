package com.org.Docker;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalTime;

public class DockerManager {

    private static final String ROOT = System.getProperty("user.dir");
    private static final String LOG_FILE = "logs.txt";
    private static final Path PATH = Path.of(ROOT + "\\" + LOG_FILE);

    private static final String STARTED_MSG = " Node has been added";
    private static final String STOPPED_MSG = "selenium-hub exited with code 0";

    public DockerManager() {
        deleteLogFile();
    }

    @SneakyThrows
    private void deleteLogFile() {
        if (Files.exists(PATH)) {
            Files.delete(PATH);
            System.out.println("File deleted");
        }
    }

    @SneakyThrows
    public void actionDocker(String action) {
        Runtime runtime = Runtime.getRuntime();

        switch (action) {
            case "START":
                runtime.exec("cmd /c start startDocker.bat");
                waitTillLogFileCreated();
                waitTll(STARTED_MSG);
                System.out.println("Grid is up");
                break;
            case "STOP":
                runtime.exec("cmd /c start stopDocker.bat");
                waitTll(STOPPED_MSG);
                System.out.println("Grid is down");
                break;
            default:
                throw new RuntimeException("Please provide either START or STOP for running docker");
        }
    }

    private void waitTillLogFileCreated() {
        boolean flag = false;
        while (!flag) {
            flag = Files.exists(PATH);
        }
        System.out.println("Logs file is created");
    }

    @SneakyThrows
    private void waitTll(String signal) {
        LocalTime waitTill = LocalTime.now().plus(Duration.ofSeconds(40));
        String fileContent = "";
        boolean started = false;

        while (LocalTime.now().compareTo(waitTill) < 0) {
            fileContent = Files.readString(PATH);
            if (fileContent.contains(signal)) {
                started = true;
                break;
            }
        }

        if (!started)
            throw new RuntimeException("Grid not started/teminated");
    }
}
