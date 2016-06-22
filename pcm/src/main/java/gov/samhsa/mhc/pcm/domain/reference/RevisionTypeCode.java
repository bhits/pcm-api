package gov.samhsa.mhc.pcm.domain.reference;

import javax.persistence.*;

/**
 * Created by tomson.ngassa on 6/10/2016.
 */
@Entity
@Table(name = "revision_type_code")
public class RevisionTypeCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private Byte code;

    private String displayName;

    public Byte getCode() {
        return code;
    }

    public void setCode(Byte code) {
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
