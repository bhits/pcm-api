package gov.samhsa.c2s.pcm.infrastructure.pdfbox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.pdfbox.pdmodel.font.PDFont;

import java.awt.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TableAttribute {
    private float xCoordinate;
    private float yCoordinate;
    private float rowHeight;
    private float cellMargin;
    private PDFont contentFont;
    private float contentFontSize;
    private Color borderColor;
    /**
     * Define each column with in order to calculate table width
     */
    private List<Column> columns;
}
