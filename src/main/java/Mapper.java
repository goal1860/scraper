import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Mapper {
    public static void main(String[] args) throws IOException {
        try (Writer writer = Files.newBufferedWriter(Paths.get("output/csv/import.csv"));
             Reader reader = Files.newBufferedReader(Paths.get("output/csv/list.csv"))) {
            CSVWriter csvWriter = new CSVWriter(writer);
            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();
            List<String[]> readlines = csvReader.readAll();

            List<String[]> writelines = new ArrayList<>();
            writelines.add(new String[] {"extId", "name", "series", "type", "rarity", "color", "weapon", "armor",
                            "cost", "health", "image", "sigCard", "sigOf", "skill", "effect"});
            for (String[] readline : readlines) {
                String[] writeline = new String[] {readline[1], readline[2], readline[4], readline[0], readline[5],
                                readline[6], readline[7], readline[8], readline[9], readline[3], readline[12],
                                readline[10], readline[11], readline[13], readline[14]};
                writelines.add(writeline);
            }
            csvWriter.writeAll(writelines);
        }

    }
}
