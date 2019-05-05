package excel;

import annotation.excel.*;
import annotation.excel.Column;
import annotation.excel.Sheet;
import annotation.excel.Title;
import org.apache.poi.ss.usermodel.CellType;

import java.util.Date;

/**
 * @author Hearts
 * @date 2019/5/5
 * @desc
 */
@annotation.excel.Excel()
public class User {

    @Column(name = "编号",colIndex = 0,cellType = CellType.NUMERIC)
    private Integer no;

    @Column(name = "姓名",colIndex = 1,cellType = CellType.STRING)
    private String name;

    @Column(name = "创建时间",colIndex = 2,cellType = CellType.STRING,typeHandler = "excel.DataFormatTypeHandler")
    private Date createTime;

    public User(Integer no, String name, Date createTime) {
        this.no = no;
        this.name = name;
        this.createTime = createTime;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
