package gov.samhsa.c2s.pcm.infrastructure.pdfbox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Column {
    private String header;
    private float cellWidth;

    public Column(float cellWidth) {
        this.cellWidth = cellWidth;
    }
}
