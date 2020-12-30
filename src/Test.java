import java.io.InputStream;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException {
        boolean bo=true;
        while (bo) {
            System.out.println("请输入用户名：");
            Scanner sc = new Scanner(System.in);
            String username = sc.next();//阻塞方法
            System.out.println("请输入密码：");
            String password = sc.next();

            //File file = new File("C:\\Users\\Administrator\\IdeaProjects\\ConsoleShop\\src\\users.xlsx");
            InputStream in = Class.forName("Test").getResourceAsStream("/users.xlsx");
            InputStream inProduct = Class.forName("Test").getResourceAsStream("/product.xlsx");
            ReadUserExcel readExcel = new ReadUserExcel();//创建对象
            User users[] = readExcel.readExcel(in);
            for (int i = 0; i < users.length; i++) {
                if (username.equals(users[i].getUsername()) && password.equals(users[i].getPassword())) {
                    System.out.println("登录成功");
                    bo = false;

                    ReadProductExcel readProductExcel = new ReadProductExcel();
                    Product products[] = readProductExcel.getAllProduct(inProduct);
                    for (Product product : products) {
                        System.out.println(product.getpId());
                        System.out.println("\t" + product.getpName());
                        System.out.println("\t" + product.getPrice());
                        System.out.println("\t" + product.getpDesc());
                    }
                    System.out.println("请输人商品ID把该商品加入购物车");
                    String pId = sc.next();
                    int count = 0;
                    Product carts[] = new Product[3];

                    inProduct = null;
                    inProduct = Class.forName("Test").getResourceAsStream("/product . xlsx");
                    Product product = readProductExcel.getProductById(pId, inProduct);
                    System.out.println("要购买的商品价格：" + product.getPrice());
                      /*
                      数组模拟的购物车
                      */
                    if (product != null) {
                        carts[count++] = product;
                    }
                    System.out.println("继续购买商品请按1");
                    System.out.println("查看购物车请按2");
                    int choose = sc.nextInt();
                    if (choose == 1) {/*
                      查看购物车
                      */
                        inProduct = null;
                        inProduct = Class.forName("Test").getResourceAsStream("/product . xlsx");
                        ReadProductExcel readProductExcel = new ReadProductExcel();
                        Product products[] = readProductExcel.getAllProduct(inProduct);

                        for (Product p : products) {
                            System.out.println(p.getpId());
                            System.out.println("\t" + p.getpName());
                            System.out.println("\t" + p.getPrice());
                            System.out.println("\t" + p.getpDesc());
                            System.out.println("\t" + p.getpDesc());

                        }
                        System.out.println("请输入商品ID把该商品加入购物车");
                        pId = sc.next();
                        inProduct = null;
                        inProduct = Class.forName("Test").getResourceAsStream("/product . xlsx");
                        Product product = readProductExcel.getproductById(pId, inProduct);
                        System.out.println("要购买的商品价格：" + product.getPrice());
                        if (product != null) {
                            carts[count++] = product;
                        }
                    } else if (choose == 2) {
                        System.out.println("当前购物车商品如下：");
                        for (Product p : carts) {
                            if (p != null) {
                                System.out.println(p.getpId());
                                System.out.println("\t" + p.getpName());
                                System.out.println("\t" + p.getPrice());
                                System.out.println("\t" + p.getpDesc());
                            }

                        }
                    }
                }
            }
        }
    }
}
