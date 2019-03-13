package sql;

/**
 * @author Hearts
 * @date 2019/3/13
 * @desc
 */
public class QueryCondition {

    private String tableName;

    private String[] searchField;

    private String[] screenCondition;

    private Limit limit;

    private String orderBy;

    public class Builder{

        private Limit limit0;

        private String orderBy0;

        private String tableName0;

        private String[] screenCondition0;

        private String[] searchField0;

        public Builder setLimit(int startIndex,int searchCount) {
            this.limit0 = new Limit(startIndex,searchCount);
            return this;
        }

        public Builder setOrderBy(String orderBy) {
            this.orderBy0 = orderBy;
            return this;
        }

        public Builder setTableName(String tableName) {
            this.tableName0 = tableName;
            return this;
        }

        public Builder setScreenCondition(String[] screenCondition) {
            this.screenCondition0 = screenCondition;
            return this;
        }


        public Builder setSearchField(String[] searchField) {
            this.searchField0 = searchField;
            return this;
        }

        public QueryCondition build(){
            limit = limit0;
            orderBy = orderBy0;
            tableName = tableName0;
            screenCondition = screenCondition0;
            searchField = searchField0;
            return QueryCondition.this;
        }
    }

    public Builder getBuilder(){
        return new Builder();
    }

    public String createSQL(){

        StringBuffer sql = new StringBuffer();

        if (tableName != null && tableName.trim().length()!=0){

            sql.append("SELECT ");

            if (searchField == null || searchField.length == 0){
                sql.append("* ");
            }else {
                for (String s : searchField) {
                    sql.append(s+",");
                }

                sql.replace(sql.length()-1,sql.length()," ");
            }

            sql.append("FROM "+tableName+" ");

            if (screenCondition != null && screenCondition.length >0){

                sql.append("WHERE ");

                for (String s : screenCondition) {
                    sql.append(s+" AND");
                }

                sql.delete(sql.length()-3,sql.length());

            }

            if (orderBy != null && orderBy.length() > 0){
                sql.append("ORDER BY "+orderBy+" ");
            }

            if (limit != null){
                sql.append(String.format("LIMIT %d,%d",limit.getStartIndex(),limit.getSearchCount()));
            }
        }

        return sql.toString();

    }
}
