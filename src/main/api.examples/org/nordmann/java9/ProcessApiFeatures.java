package org.nordmann.java9;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;

/**
 * Created by yoavn on 5/9/17.
 */
public class ProcessApiFeatures {

    public static void main(String[] args) {


    }

    void cleanupApplication(String applicationName) {
        ProcessHandle
                .allProcesses()
                .filter(process -> isApplication(applicationName, process))
                .forEach(process -> process.info().command().ifPresent(command ->
                        closeAndLog(process, command)));
    }

    private boolean isApplication(String applicationName, ProcessHandle process) {
        return true;
    }

    void closeAndLog(ProcessHandle process, String command) {
        String status = process.destroyForcibly() ? " Success!" : " Failed";
        System.out.println("Killing ... " + command + status);
    }

    private void findLongRunningProcesses() {
        final String userName = System.getProperty("user.name");
        ProcessHandle
                .allProcesses()
                .map(ProcessHandle::info)
                .filter(info -> info.user().filter(name ->
                        name.contains(userName)).isPresent())
                .sorted(Comparator.comparing(info ->
                        info.totalCpuDuration().orElse(Duration.ZERO)))
                .forEach(info ->
                        info.command().ifPresent(command ->
                                info.totalCpuDuration().ifPresent(duration ->
                                        System.out.println(command + " has been running for " + duration))));
    }


    private void exampleLsThenGrep() throws IOException {
        ProcessBuilder ls = new ProcessBuilder()
                .command("ls")
                .directory(Paths.get("/home/nipa/tmp").toFile());
        ProcessBuilder grepPdf = new ProcessBuilder()
                .command("grep", "pdf")
                .redirectOutput(ProcessBuilder.Redirect.INHERIT);
        List<Process> lsThenGrep = ProcessBuilder
                .startPipeline(List.of(ls, grepPdf));
    }

}
