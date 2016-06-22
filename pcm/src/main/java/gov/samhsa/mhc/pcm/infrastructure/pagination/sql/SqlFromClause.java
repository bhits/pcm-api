package gov.samhsa.mhc.pcm.infrastructure.pagination.sql;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SqlFromClause {

    private final String tableName;
    private final String fromClause;
    private final List<String> idColumns;

    public SqlFromClause(String tableName, String fromClause, String... idColumns) {
        Assert.notNull(tableName);
        Assert.notNull(idColumns);
        Assert.isTrue(idColumns.length > 0, "At least one primary key column must be provided");

        this.tableName = tableName;
        this.fromClause = StringUtils.hasText(fromClause) ? fromClause : tableName;
        this.idColumns = Collections.unmodifiableList(Arrays.asList(idColumns));
    }

    public SqlFromClause(String tableName, String idColumn) {
        this(tableName, null, idColumn);
    }

    public String getTableName() {
        return tableName;
    }

    public String getFromClause() {
        return fromClause;
    }

    public List<String> getIdColumns() {
        return idColumns;
    }
}
