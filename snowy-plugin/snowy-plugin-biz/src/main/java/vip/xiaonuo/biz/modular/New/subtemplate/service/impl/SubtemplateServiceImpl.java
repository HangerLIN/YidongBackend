package vip.xiaonuo.biz.modular.New.subtemplate.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import vip.xiaonuo.biz.modular.New.subtemplate.dto.SubtemplateInputParam;
import vip.xiaonuo.biz.modular.New.subtemplate.entity.Subtemplate;
import vip.xiaonuo.biz.modular.New.subtemplate.service.SubtemplateService;
import vip.xiaonuo.biz.modular.New.subtemplate.mapper.SubtemplateMapper;
import org.springframework.stereotype.Service;
import vip.xiaonuo.biz.modular.New.subtemplate.vo.SubtemplateOutput;
import vip.xiaonuo.biz.modular.strategy.utils.Pair;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author admin
* @description 针对表【SubTemplate】的数据库操作Service实现
* @createDate 2024-08-21 19:49:44
*/
@Service
public class SubtemplateServiceImpl extends ServiceImpl<SubtemplateMapper, Subtemplate>
    implements SubtemplateService{

    private static Map<Long, List<List<BigDecimal>>> subtemplateIdToStartYearData = new HashMap<>();
    private static Map<Long, List<List<BigDecimal>>> subtemplateIdToOtherYearData = new HashMap<>();
    private static Map<List<List<BigDecimal>>, Long> dataTosubtemplateId = new HashMap<>();

    @Override
    public SubtemplateOutput calculate(List<Subtemplate> subtemplateList, SubtemplateInputParam subtemplateInputParam) {

        Map<Pair<Long,Integer>, Long> idAndSerialToSubtemplateId = new HashMap<>();

        List<SubtemplateInputParam.SubmouleDV> submouleDVList = subtemplateInputParam.getSubmouleDVList();
//        int numberOfSubtemplates = subtemplateList.size();
//        int numberofSubProject = submouleDVList.get(0).getData().size();

        for (Subtemplate subtemplate : subtemplateList) {
            idAndSerialToSubtemplateId.put(new Pair<>(subtemplate.getTemplateId(), subtemplate.getSubtemplateSerial()), subtemplate.getSubtemplateId());
        }

        for(SubtemplateInputParam.SubmouleDV submouleDV : submouleDVList){

            if(submouleDV.getData().get(0).getStartYearData().equals(BigDecimal.valueOf(-1))) continue;

            List<List<BigDecimal>> startYearData = new ArrayList<>();
            List<List<BigDecimal>> otherYearData = new ArrayList<>();
            for(SubtemplateInputParam.SubmouleDV.Data data : submouleDV.getData()) {
                List<BigDecimal> startYear = new ArrayList<>();

                startYear.add(data.getStartYearData());
                startYearData.add(startYear);
                otherYearData.add(data.getEndYearData());
            }
            subtemplateIdToStartYearData.put(submouleDV.getSubtemplateId(), startYearData);
            subtemplateIdToOtherYearData.put(submouleDV.getSubtemplateId(), otherYearData);
        }
//        System.out.println(subtemplateIdToStartYearData);
//        System.out.println(subtemplateIdToOtherYearData);

        for (Subtemplate subtemplate : subtemplateList) {

            String originalStartYearEq = subtemplate.getStartyearEq();
            String originalOtherYearEq = subtemplate.getEndyearEq();
            Long subtemplateId = subtemplate.getSubtemplateId();

            // 替换 id#serial 为对应的 subtemplateId：1#1*1#2 -> 34*35
            String startYearEq = convertExpression(originalStartYearEq, idAndSerialToSubtemplateId);
            String otherYearEq = convertExpression(originalOtherYearEq, idAndSerialToSubtemplateId);

            List<List<BigDecimal>> resultStartYear = new ArrayList<>();
            List<List<BigDecimal>> resultOtherYear = new ArrayList<>();

            //不需要计算（Template_type: 1 or 3），跳过
            if(subtemplate.getTemplateType() != 0) continue;

            //Template_type: 0
            System.out.println("--------------------------------------------");
            System.out.println(startYearEq);
            System.out.println(otherYearEq);

            resultStartYear = calculateExpression(startYearEq, subtemplateIdToStartYearData, 0);
            resultOtherYear = calculateExpression(otherYearEq, subtemplateIdToOtherYearData, 1);

            subtemplateIdToStartYearData.put(subtemplateId, resultStartYear);
            subtemplateIdToOtherYearData.put(subtemplateId, resultOtherYear);

            System.out.println(subtemplateIdToStartYearData);
            System.out.println(subtemplateIdToOtherYearData);
            System.out.println("--------------------------------------------");


//            if(!calculateSum(startYearEq)){
//
//                resultStartYear = calculateExpression(startYearEq, subtemplateIdToStartYearData, 0);
//                resultOtherYear = calculateExpression(otherYearEq, subtemplateIdToOtherYearData, 1);
//                subtemplateIdToStartYearData.put(subtemplateId, resultStartYear);
//                subtemplateIdToOtherYearData.put(subtemplateId, resultOtherYear);
////                result = merge(resultStartYear, resultOtherYear);
//
////                System.out.println(subtemplate.getSubtemplateName());
////                System.out.println(resultStartYear);
////                System.out.println(resultOtherYear);
////                System.out.println(result);
////                System.out.println(subtemplateIdToStartYearData);
////                System.out.println(subtemplateIdToOtherYearData);
////                System.out.println("--------------------------------------------");
//
//
//
//            }
//            else{
//                List<List<BigDecimal>> addResult = new ArrayList<>();
//
//                Long sumId = Long.parseLong(startYearEq.substring(1,startYearEq.length() - 3));
////                System.out.println(sumId);
//                List<List<BigDecimal>> data1 = subtemplateIdToStartYearData.get(sumId);
//                List<List<BigDecimal>> data2 = subtemplateIdToOtherYearData.get(sumId);
//                List<List<BigDecimal>> data = merge(data1, data2);
//
////                System.out.println(subtemplate.getSubtemplateName());
////                System.out.println(data1);
////                System.out.println(data2);
////                System.out.println(data);
//
//                int size = data.size();
//                if(size == 1) addResult = addHorizontally(data);
//                else addResult = addvertically(data);
////                System.out.println(addResult);
//
//                List<List<BigDecimal>> firstColumn = new ArrayList<>();
//                List<List<BigDecimal>> remainingColumns = new ArrayList<>();
//
//                splitList(addResult, firstColumn, remainingColumns);
//
//                subtemplateIdToStartYearData.put(subtemplateId, firstColumn);
//                subtemplateIdToOtherYearData.put(subtemplateId, remainingColumns);
//
////                System.out.println(subtemplateIdToStartYearData);
////                System.out.println(subtemplateIdToOtherYearData);
////                System.out.println("--------------------------------------------");
//            }

        }

        SubtemplateOutput subtemplateOutput = new SubtemplateOutput();
        List<SubtemplateOutput.SubmouleOutputDV> subtemplateOutputList = new ArrayList<>();

        for(SubtemplateInputParam.SubmouleDV submouleDV : submouleDVList){
            SubtemplateOutput.SubmouleOutputDV submouleOutputDV = new SubtemplateOutput.SubmouleOutputDV();
            submouleOutputDV.setSubtemplateId(submouleDV.getSubtemplateId());
            submouleOutputDV.setProjectId(submouleDV.getProjectId());

            int n = submouleDV.getData().size();
            List<SubtemplateOutput.SubmouleOutputDV.Data> dataList = new ArrayList<>(n);
            for(int i = 0; i < n; i++){
                SubtemplateOutput.SubmouleOutputDV.Data data = new SubtemplateOutput.SubmouleOutputDV.Data();
                data.setBasicInformation(submouleDV.getData().get(i).getBasicInformation());
                data.setStartYearData(subtemplateIdToStartYearData.get(submouleDV.getSubtemplateId()).get(i).get(0));
                data.setEndYearData(subtemplateIdToOtherYearData.get(submouleDV.getSubtemplateId()).get(i));
                dataList.add(data);
            }
            submouleOutputDV.setData(dataList);
            subtemplateOutputList.add(submouleOutputDV);
        }
        subtemplateOutput.setSubmouleDVList(subtemplateOutputList);

        return subtemplateOutput;
    }

    private static List<List<BigDecimal>> calculateSum(Long subtemplateId, List<List<BigDecimal>> data, int flag){
        int size = data.size();
        if(size == 1) return addHorizontally(data);
        else return addvertically(data);
    }

    private List<List<BigDecimal>> merge(List<List<BigDecimal>> resultStartYear, List<List<BigDecimal>> resultOtherYear){
        int n = resultStartYear.size();
        List<List<BigDecimal>> result = new ArrayList<>();
        for(int i = 0; i < n; i++){
            List<BigDecimal> tmp = new ArrayList<>();
            tmp.addAll(resultStartYear.get(i));
            tmp.addAll(resultOtherYear.get(i));
            result.add(tmp);
        }
        return result;
    }

    public static void splitList(List<List<BigDecimal>> originalList, List<List<BigDecimal>> firstColumn, List<List<BigDecimal>> remainingColumns) {
        for (List<BigDecimal> row : originalList) {
            // 提取每一行的第一个元素并存入firstColumn
            List<BigDecimal> firstElementList = new ArrayList<>();
            firstElementList.add(row.get(0));
            firstColumn.add(firstElementList);
//            System.out.println(firstElementList);

            // 提取每一行剩余的元素并存入remainingColumns
            List<BigDecimal> remainingList = new ArrayList<>(row.subList(1, row.size()));
//            System.out.println(remainingList);
            remainingColumns.add(remainingList);
        }
    }

    private static List<List<BigDecimal>> addvertically(List<List<BigDecimal>> data){
        int m = data.size();
        int n = data.get(0).size();
        List<List<BigDecimal>> ans = new ArrayList<>();
        List<BigDecimal> tmp = new ArrayList<>();
        for(int j = 0; j < n; j++){
            BigDecimal sum = BigDecimal.ZERO;
            for(int i = 0; i < m; i++){
                sum = sum.add(data.get(i).get(j));
            }
            tmp.add(sum);
        }
        ans.add(tmp);
        return ans;
    }

    private static List<List<BigDecimal>> addHorizontally(List<List<BigDecimal>> data){
        int n = data.get(0).size();
        List<List<BigDecimal>> ans = new ArrayList<>(n);
        List<BigDecimal> tmp = new ArrayList<>();
        BigDecimal sum = BigDecimal.ZERO;
        for(int j = 0; j < n; j++){
            sum = sum.add(data.get(0).get(j));
        }
        tmp.add(sum);
        ans.add(tmp);
        return ans;
    }

    private static String convertExpression(String expression, Map<Pair<Long, Integer>, Long> idAndSerialToSubtemplateId) {
        // 定义正则表达式，匹配 a#b 格式的部分
        Pattern pattern = Pattern.compile("(\\d+)#(\\d+)");
        Matcher matcher = pattern.matcher(expression);
        StringBuffer sb = new StringBuffer();

        while (matcher.find()) {
            // 提取 a 和 b
            Long a = Long.parseLong(matcher.group(1));
            Integer b = Integer.parseInt(matcher.group(2));
            // 查找对应的 c
            Long c = idAndSerialToSubtemplateId.get(new Pair<>(a, b));
            if (c != null) {
                // 将 a#b 替换为 #c
                matcher.appendReplacement(sb, "#" + c);
            } else {
                // 如果没有找到对应的 c，保持原样
                matcher.appendReplacement(sb, matcher.group());
            }
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

//    public static boolean calculateSum(String str) {
//        if (str.length() < 3) {
//            return false; // 字符串长度小于3，肯定不是"SUM"
//        }
//
//        // 获取字符串的后三位
//        String lastThree = str.substring(str.length() - 3);
//
//        // 判断后三位是否为"SUM"
//        return lastThree.equals("SUM");
//    }

    // 计算表达式
    public static List<List<BigDecimal>> calculateExpression(
            String expression, Map<Long, List<List<BigDecimal>>> matrixMap, int flag) {

        Stack<List<List<BigDecimal>>> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);

            // 跳过空白字符
            if (ch == ' ') continue;

            // 如果是数字，读取完整的数字并压入值栈
            if (ch == '#') {
                int j = i + 1;
                while (j < expression.length() && Character.isDigit(expression.charAt(j))) {
                    j++;
                }
                Long matrixId = Long.parseLong(expression.substring(i + 1, j));
                values.push(matrixMap.get(matrixId));
                dataTosubtemplateId.put(matrixMap.get(matrixId), matrixId);
                i = j - 1;
            } else if (ch == '$') {
                int j = i + 1;
                while (j < expression.length() && (Character.isDigit(expression.charAt(j)) || expression.charAt(j) == '.')) {
                    j++;
                }
                //标量，用来处理 1#5*1.06的情况，将1.06存入二维LIst，并用-1E+100标识
                BigDecimal scalar = new BigDecimal(expression.substring(i + 1, j));
                values.push(scalarToMatrix(scalar));
                i = j - 1;
            } else if (ch == '(') {
                operators.push(ch);
            } else if (ch == ')') {
                while (operators.peek() != '(') {
                    values.push(applyOp(operators.pop(), values.pop(), values.pop()));
                }
                operators.pop(); // 去掉 '('
            } else if (isOperator(ch)) {
                while (!operators.isEmpty() && hasPrecedence(ch, operators.peek())) {
                    values.push(applyOp(operators.pop(), values.pop(), values.pop()));
                }
                operators.push(ch);
            } else if(i + 2 < expression.length() && expression.substring(i, i + 3).equals("SUM")){
                List<List<BigDecimal>> data = values.pop();
                values.push(calculateSum(dataTosubtemplateId.get(data),data,flag));
                i += 2;
            }
        }

        while (!operators.isEmpty()) {
            values.push(applyOp(operators.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    //处理标量，用来处理 1#5*1.06的情况，将1.06存入二维LIst，并用-1E+100标识
    private static List<List<BigDecimal>> scalarToMatrix(BigDecimal scalar) {
        List<List<BigDecimal>> matrix = new ArrayList<>();
        List<BigDecimal> flag = new ArrayList<>();
        flag.add(new BigDecimal("-1E+100"));
        flag.add(scalar);
        matrix.add(flag);
        return matrix;
    }

    // 应用操作符
    private static void swap(List<List<BigDecimal>> a, List<List<BigDecimal>> b){
        BigDecimal flag = new BigDecimal("-1E+100");
        if(!a.get(0).get(0).equals(flag) && !b.get(0).get(0).equals(flag)) return;
        if(a.get(0).get(0).equals(flag)){
            List<List<BigDecimal>> tmp = a;
            a = b;
            b = tmp;
        }
    }
    private static List<List<BigDecimal>> applyOp(char op, List<List<BigDecimal>> b, List<List<BigDecimal>> a) {
        swap(a,b);
        BigDecimal flag = new BigDecimal("-1E+100");
        switch (op) {
            case '+':
                if(b.get(0).get(0).equals(flag)) return addScalar(a, b.get(0).get(1));
                else return addMatrices(a,b);
            case '-':
                if(b.get(0).get(0).equals(flag)) return subtractScalar(a, b.get(0).get(1));
                else return subtractMatrices(a,b);
            case '*':
                if(b.get(0).get(0).equals(flag)) return multiplyScalar(a, b.get(0).get(1));
                else return multiplyMatrices(a,b);
            case '/':
                if(b.get(0).get(0).equals(flag)){
                    if (b.get(0).get(1).compareTo(BigDecimal.ZERO) == 0) throw new ArithmeticException("Division by scalar zero");
                    return divideScalar(a, b.get(0).get(1));
                }
                else return divideMatrices(a,b);
        }
        return new ArrayList<>();
    }

    // 判断是否是操作符
    private static boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    // 判断操作符优先级
    private static boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) return false;
        return true;
    }

    // 处理一个操作符
    private static void processOperation(Stack<List<List<BigDecimal>>> matrixStack, char operator) {
        List<List<BigDecimal>> b = matrixStack.pop();
        List<List<BigDecimal>> a = matrixStack.pop();
        switch (operator) {
            case '+':
                matrixStack.push(addMatrices(a, b));
                break;
            case '-':
                matrixStack.push(subtractMatrices(a, b));
                break;
            case '*':
                matrixStack.push(multiplyMatrices(a, b));
                break;
            case '/':
                matrixStack.push(divideMatrices(a, b));
                break;
        }
    }


    // 返回操作符的优先级
    private static int precedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 0;
        }
    }

    // 矩阵相加
    private static List<List<BigDecimal>> addMatrices(List<List<BigDecimal>> a, List<List<BigDecimal>> b) {
        int rows = a.size();
        int cols = a.get(0).size();
        List<List<BigDecimal>> result = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            List<BigDecimal> row = new ArrayList<>(cols);
            for (int j = 0; j < cols; j++) {
                row.add(a.get(i).get(j).add(b.get(i).get(j)));
            }
            result.add(row);
        }
        return result;
    }

    // 矩阵相减
    private static List<List<BigDecimal>> subtractMatrices(List<List<BigDecimal>> a, List<List<BigDecimal>> b) {
        int rows = a.size();
        int cols = a.get(0).size();
        List<List<BigDecimal>> result = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            List<BigDecimal> row = new ArrayList<>(cols);
            for (int j = 0; j < cols; j++) {
                row.add(a.get(i).get(j).subtract(b.get(i).get(j)));
            }
            result.add(row);
        }
        return result;
    }

    // 矩阵逐元素相乘
    private static List<List<BigDecimal>> multiplyMatrices(List<List<BigDecimal>> a, List<List<BigDecimal>> b) {
        int rows = a.size();
        int cols = a.get(0).size();
        List<List<BigDecimal>> result = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            List<BigDecimal> row = new ArrayList<>(cols);
            for (int j = 0; j < cols; j++) {
                row.add(a.get(i).get(j).multiply(b.get(i).get(j)));
            }
            result.add(row);
        }
        return result;
    }

    // 矩阵逐元素相除
    private static List<List<BigDecimal>> divideMatrices(List<List<BigDecimal>> a, List<List<BigDecimal>> b) {
        int rows = a.size();
        int cols = a.get(0).size();
        List<List<BigDecimal>> result = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            List<BigDecimal> row = new ArrayList<>(cols);
            for (int j = 0; j < cols; j++) {
                row.add(a.get(i).get(j).divide(b.get(i).get(j), BigDecimal.ROUND_HALF_UP));
            }
            result.add(row);
        }
        return result;
    }

    // 矩阵逐元素乘以标量
    private static List<List<BigDecimal>> addScalar(List<List<BigDecimal>> matrix, BigDecimal scalar) {
        int rows = matrix.size();
        int cols = matrix.get(0).size();
        List<List<BigDecimal>> result = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            List<BigDecimal> row = new ArrayList<>(cols);
            for (int j = 0; j < cols; j++) {
                row.add(matrix.get(i).get(j).add(scalar));
            }
            result.add(row);
        }
        return result;
    }

    // 矩阵逐元素乘以标量
    private static List<List<BigDecimal>> subtractScalar(List<List<BigDecimal>> matrix, BigDecimal scalar) {
        int rows = matrix.size();
        int cols = matrix.get(0).size();
        List<List<BigDecimal>> result = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            List<BigDecimal> row = new ArrayList<>(cols);
            for (int j = 0; j < cols; j++) {
                row.add(matrix.get(i).get(j).subtract(scalar));
            }
            result.add(row);
        }
        return result;
    }
    // 矩阵逐元素乘以标量
    private static List<List<BigDecimal>> multiplyScalar(List<List<BigDecimal>> matrix, BigDecimal scalar) {
        int rows = matrix.size();
        int cols = matrix.get(0).size();
        List<List<BigDecimal>> result = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            List<BigDecimal> row = new ArrayList<>(cols);
            for (int j = 0; j < cols; j++) {
                row.add(matrix.get(i).get(j).multiply(scalar));
            }
            result.add(row);
        }
        return result;
    }

    // 矩阵逐元素除以标量
    private static List<List<BigDecimal>> divideScalar(List<List<BigDecimal>> matrix, BigDecimal scalar) {
        int rows = matrix.size();
        int cols = matrix.get(0).size();
        List<List<BigDecimal>> result = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            List<BigDecimal> row = new ArrayList<>(cols);
            for (int j = 0; j < cols; j++) {
                row.add(matrix.get(i).get(j).divide(scalar));
            }
            result.add(row);
        }
        return result;
    }
}





