package zad1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TravelData {
    private List<Travel> list;

    public TravelData(File dataDir) {

        try {
            Stream<Path> paths = Files.walk(dataDir.toPath());
            list = paths
                    .filter(Files::isRegularFile)
                    .flatMap(path -> {
                        try {
                            return Files.lines(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .map(line -> {
                        String[] offer = line.split("\t");
                        return new Travel(offer[0], offer[1], offer[2], offer[3], offer[4], offer[5], offer[6]);
                    })
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    List<String> getOffersDescriptionsList(String locale, String dateFormat) {
        Locale loc = Locale.forLanguageTag(locale.replace("_", "-"));
        return list
                .stream()
                .map(line -> line.formatString(loc))
                .collect(Collectors.toList());
    }

    List<Travel> getList() {
        return this.list;
    }

}
