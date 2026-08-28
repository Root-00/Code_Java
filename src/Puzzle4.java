public class Puzzle4 {
    public static void main(String[] args) {
        Value[] values = new Value[6];
        int number = 1;
        int i = 0;
        while (i < 6){
            values[i] = new Value();  //创建对象，实例化
//            new Value() 是在内存里新建一个 Value 对象
//            把这个对象的引用存入数组 values[i]
            values[i].intValue = number; //给实例变量复制/设置字段
//            通过 . 操作符访问对象的实例变量 intValue
//            把 number 的值赋给它
            number= number * 10;
            i = i + 1;
        }

        int result = 0;
        i = 6;
        while (i > 0){
            i = i - 1;
            result = result + values[i].doStuff(i);
        }
        System.out.println("result "+result);
    }
}
class Value{
    int intValue;

    public int doStuff(int factor){
        if (intValue > 100){
            return intValue*factor;
        }
        else {
            return intValue*(5 - factor);
        }
}
        }
