package gov.samhsa.mhc.pcm.infrastructure.pagination.sql;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;

public class SqlRebuilder {
    private static final String WHERE = " WHERE ";
    private static final String AND = " AND ";
    private static final String OR = " OR ";
    private static final String SELECT = "SELECT ";
    private static final String FROM = "FROM ";
    private static final String LIMIT = " LIMIT ";
    private static final String ORDERBY = " ORDER BY ";
    private static final String COMMA = ", ";
    private static final String PARAM = " = ?";

    private String columnNames;

    public SqlRebuilder() {
        this("*");
    }

    public SqlRebuilder(String columnNames) {
        this.columnNames = columnNames;
    }

    public String count(SqlFromClause fromClause) {
        return SELECT + "COUNT(*) " + FROM + fromClause.getFromClause();
    }

    //TODO: Refactor
    public String countByArgs(SqlFromClause fromClause, Object arg) {
        StringBuilder whereClause = new StringBuilder(WHERE);
        for (String s : fromClause.getIdColumns()) {
            whereClause.append(s).append(" = ");
        }
        return SELECT + "COUNT(*) " + FROM + fromClause.getFromClause() + whereClause + "'" + arg + "'";
    }

    public String selectAll(SqlFromClause fromClause) {
        return SELECT + columnNames + ' ' + FROM + fromClause.getFromClause();
    }

    public String selectAll(SqlFromClause fromClause, Pageable page) {
        return selectAll(fromClause, page.getSort()) + limitClause(page);
    }

    public String selectAll(SqlFromClause fromClause, Sort sort) {
        return selectAll(fromClause) + sortingClauseIfRequired(sort);
    }

    public String selectById(SqlFromClause fromClause) {
        return selectAll(fromClause) + whereByIdClause(fromClause);
    }

    public String selectByIdPageable(SqlFromClause fromClause, Pageable page) {
        return selectAll(fromClause) + whereByIdClause(fromClause) + sortingClauseIfRequired(page.getSort()) + limitClause(page);
    }

    public String selectByIds(SqlFromClause fromClause, int idsCount) {
        switch (idsCount) {
            case 0:
                return selectAll(fromClause);
            case 1:
                return selectById(fromClause);
            default:
                return selectAll(fromClause) + whereByIdsClause(fromClause, idsCount);
        }
    }

    private String limitClause(Pageable page) {
        int offset = page.getPageNumber() * page.getPageSize();
        return LIMIT + offset + COMMA + page.getPageSize();
    }

    private String sortingClauseIfRequired(Sort sort) {
        if (sort == null) {
            return "";
        }
        StringBuilder orderByClause = new StringBuilder();
        orderByClause.append(ORDERBY);

        for (Iterator<Sort.Order> iterator = sort.iterator(); iterator.hasNext(); ) {
            Sort.Order order = iterator.next();
            orderByClause
                    .append(order.getProperty())
                    .append(' ')
                    .append(order.getDirection().toString());

            if (iterator.hasNext()) {
                orderByClause.append(COMMA);
            }
        }
        return orderByClause.toString();
    }

    private String whereByIdClause(SqlFromClause fromClause) {
        StringBuilder whereClause = new StringBuilder(WHERE);

        for (Iterator<String> it = fromClause.getIdColumns().iterator(); it.hasNext(); ) {
            whereClause.append(it.next()).append(PARAM);
            if (it.hasNext()) {
                whereClause.append(AND);
            }
        }
        return whereClause.toString();
    }

    private String whereByIdsClause(SqlFromClause fromClause, int idsCount) {
        List<String> idColumnNames = fromClause.getIdColumns();

        if (idColumnNames.size() > 1) {
            return whereByIdsWithMultipleIdColumns(idsCount, idColumnNames);
        } else {
            return whereByIdsWithSingleIdColumn(idsCount, idColumnNames.get(0));
        }
    }

    private String whereByIdsWithMultipleIdColumns(int idsCount, List<String> idColumnNames) {

        int idColumnsCount = idColumnNames.size();
        int totalParams = idsCount * idColumnsCount;
        StringBuilder whereClause = new StringBuilder(WHERE);

        for (int idColumnIdx = 0; idColumnIdx < totalParams; idColumnIdx += idColumnsCount) {
            if (idColumnIdx > 0) {
                whereClause.append(OR);
            }
            whereClause.append('(');

            for (int i = 0; i < idColumnsCount; ++i) {
                if (i > 0) {
                    whereClause.append(AND);
                }
                whereClause.append(idColumnNames.get(i)).append(" = ?");
            }
            whereClause.append(')');
        }
        return whereClause.toString();
    }

    private String whereByIdsWithSingleIdColumn(int idsCount, String idColumn) {
        return WHERE + idColumn + " IN (" + repeat("?", COMMA, idsCount) + ')';
    }

    private static String repeat(String s, String separator, int count) {
        StringBuilder string = new StringBuilder((s.length() + separator.length()) * count);
        while (--count > 0) {
            string.append(s).append(separator);
        }
        return string.append(s).toString();
    }
}
