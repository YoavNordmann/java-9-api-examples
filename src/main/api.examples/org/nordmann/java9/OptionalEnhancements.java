package org.nordmann.java9;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

/**
 * Created by yoavn on 5/9/17.
 */
public class OptionalEnhancements {

    public static void main(String [] args) {
    }


    private static void example1(){
        lookupBooking("").ifPresentOrElse(
                OptionalEnhancements::displayCheckIn,
                OptionalEnhancements::displayMissingBookingPage);
    }

    private static void example2(){
        int companyId = 1;
        Optional<Object> client = findClient(companyId)
                .or(() -> lookupCompanyDetails(companyId));
    }


    private static void example3(){
        List<String> SETTING_NAMES = List.of("1", "2", "3", "4");

        List<Object> settings =
                SETTING_NAMES.stream()
                        .map(OptionalEnhancements::lookupSettingByName)
                        .flatMap(Optional::stream)
                        .collect(toList());
    }

    private static Optional<String> lookupSettingByName(String s) {
        return Optional.of("hello");
    }

    private static Object lookupCompanyDetails(int companyId) {
      return new Object();
    }

    private static <T> Optional findClient(int companyId) {
      return Optional.empty();
    }

    private static void displayMissingBookingPage() {
    }

    private static void displayCheckIn(String s) {
    }

    private static Optional<String> lookupBooking(String s) {
      return Optional.empty();
    }
}
