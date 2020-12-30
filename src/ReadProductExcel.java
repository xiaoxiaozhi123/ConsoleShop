import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

public class ReadProductExcel {
    public User[] readExcel(InputStream in) {
        User users[] = null;
        try {
            XSSFWorkbook xw = new XSSFWorkbook(in);
            XSSFSheet xs = xw.getSheetAt(0);
            users = new User[xs.getLastRowNum()];
            for (int j = 1; j <= xs.getLastRowNum(); j++) {
                XSSFRow row = xs.getRow(j);
                User user = new User();//每循环一次就把电子表格的一行的数据给对象赋值
                for (int k = 0; k <= row.getLastCellNum(); k++) {
                    XSSFCell cell = row.getCell(k);
                    if (cell == null)
                        continue;
                    if (k == 0) {
                        user.setUsername(this.getValue(cell));//给username属性赋值
                    } else if (k == 1) {
                        user.setPassword(this.getValue(cell));//给password属性赋值
                    } else if (k == 2) {
                        user.setAddress(this.getValue(cell));//给address属性赋值
                    } else if (k == 3) {
                        user.setPhone(this.getValue(cell));//给phone属性赋值
                    }
                }
                users[j-1] = user;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
    public Product getProductById(String id,InputStream in) {
        try {
            XSSFWorkbook xw = new XSSFWorkbook(in);
            XSSFSheet xs = xw.getSheetAt(0);
            for (int j = 1; j <= xs.getLastRowNum(); j++) {
                XSSFRow row = xs.getRow(j);
                Product product = new Product();
                for (int k = 0; k <= row.getLastCellNum(); k++) {
                    XSSFCell cell = row.getCell(k);
                    if (cell == null)
                        continue;
                    if (k == 0) {
                        product.setpId(this.getValue(cell));
                    } else if (k == 1) {
                        product.setpName(this.getValue(cell));
                    } else if (k == 2) {
                        product.setPrice(this.getValue(cell));//把字符串转Float
                    } else if (k == 3) {
                        product.setpDesc(this.getValue(cell));
                    }
                }
                if(id.equals(product.getpId())){
                    return product;
                }
                //如果id（手动输入的）和product的id（从电子表格里读出来的）一致，则表示找到了该商品，然后返回该商品即可
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;//如果找不到就返回空
    }

    private String getValue(XSSFCell cell) {
        String value;
        CellType type = cell.getCellTypeEnum();
        DecimalFormat df = new DecimalFormat("#");
        switch (type) {
            case STRING:
                value = cell.getStringCellValue();
                break;
            case BLANK:
                value = "";
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue() + "";
                break;
            case NUMERIC:
                value = df.format(cell.getNumericCellValue());//double和一个空字符串相连接，最终得到字符串
                System.out.println("转换后的："+value);
                break;
            case FORMULA:
                value = cell.getCellFormula();
                break;
            case ERROR:
                value = "非法字符";
                break;
            default:
                value = "";
                break;
        }
        return value;
    }
}