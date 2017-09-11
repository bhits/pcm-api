package gov.samhsa.c2s.pcm.infrastructure.pdfbox;

import gov.samhsa.c2s.pcm.infrastructure.pdfbox.util.PdfBoxHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Paragraph {

    private String text;

    private float width;

    private PDFont font;

    private float fontSize;

    public List<String> getLines() throws IOException {
        List<String> result = new ArrayList<>();

        String[] split = text.split("(?<=\\W)");
        int[] possibleWrapPoints = new int[split.length];
        possibleWrapPoints[0] = split[0].length();
        for (int i = 1; i < split.length; i++) {
            possibleWrapPoints[i] = possibleWrapPoints[i - 1] + split[i].length();
        }

        int start = 0;
        int end = 0;
        for (int i : possibleWrapPoints) {
            float width = PdfBoxHandler.targetedStringWidth(text.substring(start, i), font, fontSize);
            if (start < end && width > this.width) {
                result.add(text.substring(start, end));
                start = end;
            }
            end = i;
        }
        // Last piece of text
        result.add(text.substring(start));
        return result;
    }
}
