package sql;

/**
 * @author Hearts
 * @date 2019/3/13
 * @desc limit 参数
 */
public class Limit {

    private int startIndex;

    private int searchCount;

    public Limit(int startIndex, int searchCount) {
        if (startIndex >=0 && searchCount >=0){
            this.startIndex = startIndex;
            this.searchCount = searchCount;
        }
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getSearchCount() {
        return searchCount;
    }
}
