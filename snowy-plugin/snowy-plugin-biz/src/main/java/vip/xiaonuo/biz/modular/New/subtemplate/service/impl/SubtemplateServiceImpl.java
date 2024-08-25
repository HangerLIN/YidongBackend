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

    private Map<Long, List<List<BigDecimal>>> subtemplateIdToStartYearData = new HashMap<>();
    private Map<Long, List<List<BigDecimal>>> subtemplateIdToOtherYearData = new HashMap<>();
    private Map<List<List<BigDecimal>>, Long> dataTosubtemplateId = new HashMap<>();
    private Long subtemplateId;
    private BigDecimal f1 = new BigDecimal("-1E+101");
    private BigDecimal f2 = new BigDecimal("-1E+102");

    @Override
    public SubtemplateOutput calculate(List<Subtemplate> subtemplateList, SubtemplateInputParam subtemplateInputParam) {

        Map<Pair<Long,Integer>, Long> idAndSerialToSubtemplateId = new HashMap<>();

        List<SubtemplateInputParam.SubmouleDV> submouleDVList = subtemplateInputParam.getSubmouleDVList();

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
        System.out.println(subtemplateIdToStartYearData);
        System.out.println(subtemplateIdToOtherYearData);

        for (Subtemplate subtemplate : subtemplateList) {

            String originalStartYearEq = subtemplate.getStartyearEq();
            String originalOtherYearEq = subtemplate.getEndyearEq();
            subtemplateId = subtemplate.getSubtemplateId();

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


            resultStartYear = calculateExpression(startYearEq, subtemplateIdToStartYearData);
            subtemplateIdToStartYearData.put(subtemplateId, resultStartYear);

            System.out.println(otherYearEq);
            resultOtherYear = calculateExpression(otherYearEq, subtemplateIdToOtherYearData);
            subtemplateIdToOtherYearData.put(subtemplateId, resultOtherYear);

            List<List<BigDecimal>> result = merge(resultStartYear, resultOtherYear);
            System.out.println(result);
            System.out.println("--------------------------------------------");
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

    private List<List<BigDecimal>> calculateSum(List<List<BigDecimal>> data){
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

//    public void splitList(List<List<BigDecimal>> originalList, List<List<BigDecimal>> firstColumn, List<List<BigDecimal>> remainingColumns) {
//        for (List<BigDecimal> row : originalList) {
//            // 提取每一行的第一个元素并存入firstColumn
//            List<BigDecimal> firstElementList = new ArrayList<>();
//            firstElementList.add(row.get(0));
//            firstColumn.add(firstElementList);
////            System.out.println(firstElementList);
//
//            // 提取每一行剩余的元素并存入remainingColumns
//            List<BigDecimal> remainingList = new ArrayList<>(row.subList(1, row.size()));
////            System.out.println(remainingList);
//            remainingColumns.add(remainingList);
//        }
//    }

    private List<List<BigDecimal>> addvertically(List<List<BigDecimal>> data){
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

    private List<List<BigDecimal>> addHorizontally(List<List<BigDecimal>> data){
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

    private String convertExpression(String expression, Map<Pair<Long, Integer>, Long> idAndSerialToSubtemplateId) {
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

    // 计算表达式
    public List<List<BigDecimal>> calculateExpression(
            String expression, Map<Long, List<List<BigDecimal>>> matrixMap) {

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

                if(matrixId.equals(subtemplateId)){
                    values.push(selfMatrix());
                }
                else values.push(matrixMap.get(matrixId));

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
                values.push(calculateSum(values.pop()));
                i += 2;
            }
        }

        while (!operators.isEmpty()) {
            values.push(applyOp(operators.pop(), values.pop(), values.pop()));
        }

        return values.pop();
    }

    //处理标量，用来处理 1#5*1.06的情况，将1.06存入二维LIst，并用-1E+100标识
    private List<List<BigDecimal>> scalarToMatrix(BigDecimal scalar) {
        List<List<BigDecimal>> matrix = new ArrayList<>();
        List<BigDecimal> flag = new ArrayList<>();
        flag.add(new BigDecimal("-1E+101"));
        flag.add(scalar);
        matrix.add(flag);
        return matrix;
    }

    //处理计算过程中需要用到上一年数据的情况
    private List<List<BigDecimal>> selfMatrix() {
        List<List<BigDecimal>> matrix = new ArrayList<>();
        List<BigDecimal> flag = new ArrayList<>();
        flag.add(new BigDecimal("-1E+102"));
        matrix.add(flag);
        return matrix;
    }


    private List<List<List<BigDecimal>>> preprocess(List<List<BigDecimal>> b, List<List<BigDecimal>> a){
        if(a.get(0).get(0).equals(f1) && !b.get(0).get(0).equals(f1)) return List.of(b, a);
        if(a.get(0).get(0).equals(f2) && !b.get(0).get(0).equals(f2)) return List.of(b, a);
        return List.of(a, b);
    }

    private List<List<BigDecimal>> applyOp(char op, List<List<BigDecimal>> y, List<List<BigDecimal>> x) {
        List<List<List<BigDecimal>>> swappedLists = preprocess(y,x);
        List<List<BigDecimal>> a = swappedLists.get(0);
        List<List<BigDecimal>> b = swappedLists.get(1);
        System.out.println(a);
        System.out.println(b);

        switch (op) {
            case '+':
                if(b.get(0).get(0).equals(f1)) return addScalar(a, b.get(0).get(1));
                else return addMatrices(a,b);
            case '-':
                if(b.get(0).get(0).equals(f1)) return subtractScalar(a, b.get(0).get(1));
                else return subtractMatrices(a,b);
            case '*':
                if(b.get(0).get(0).equals(f1)) return multiplyScalar(a, b.get(0).get(1));
                else return multiplyMatrices(a,b);
            case '/':
                if(b.get(0).get(0).equals(f1)){
                    if (b.get(0).get(1).compareTo(BigDecimal.ZERO) == 0) throw new ArithmeticException("Division by scalar zero");
                    return divideScalar(a, b.get(0).get(1));
                }
                else return divideMatrices(a,b);
        }
        return new ArrayList<>();
    }

    // 判断是否是操作符
    private boolean isOperator(char ch) {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/';
    }

    // 判断操作符优先级
    private boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) return false;
        return true;
    }

    public List<List<BigDecimal>> deepCopy(List<List<BigDecimal>> original) {
        List<List<BigDecimal>> copy = new ArrayList<>();
        for (List<BigDecimal> row : original) {
            // 创建每一行的深拷贝
            List<BigDecimal> newRow = new ArrayList<>();
            for (BigDecimal value : row) {
                // 对每个元素进行拷贝
                newRow.add(new BigDecimal(value.toString()));
            }
            copy.add(newRow);
        }
        return copy;
    }

    // 矩阵相加
    private List<List<BigDecimal>> addMatrices(List<List<BigDecimal>> a, List<List<BigDecimal>> b) {
        int rows = a.size();
        int cols = a.get(0).size();
        List<List<BigDecimal>> result = new ArrayList<>(rows);

        List<List<BigDecimal>> aCopy = deepCopy(a);

        if(b.get(0).get(0).equals(f2)){
            List<List<BigDecimal>> startYearData = subtemplateIdToStartYearData.get(subtemplateId);
            for (int i = 0; i < rows; i++) {
                List<BigDecimal> tmp = new ArrayList<>();
                for (int j = 0; j < cols; j++) {
                    if(j == 0) tmp.add(a.get(i).get(j).add(startYearData.get(i).get(j)));
                    else tmp.add(a.get(i).get(j).add(tmp.get(j - 1)));
                }
                result.add(tmp);
            }
        }
        else{
            for (int i = 0; i < rows; i++) {
                List<BigDecimal> tmp = new ArrayList<>();
                for (int j = 0; j < cols; j++) {
                    tmp.add(a.get(i).get(j).add(b.get(i).get(j)));
                }
                result.add(tmp);
            }
        }
        return result;
    }

    // 矩阵相减
    private List<List<BigDecimal>> subtractMatrices(List<List<BigDecimal>> a, List<List<BigDecimal>> b) {
        int rows = a.size();
        int cols = a.get(0).size();
        List<List<BigDecimal>> result = new ArrayList<>(rows);

        if(b.get(0).get(0).equals(f2)){
            List<List<BigDecimal>> startYearData = subtemplateIdToStartYearData.get(subtemplateId);
            for (int i = 0; i < rows; i++) {
                List<BigDecimal> tmp = new ArrayList<>();
                for (int j = 0; j < cols; j++) {
                    if(j == 0) tmp.add(a.get(i).get(j).subtract(startYearData.get(i).get(j)));
                    else tmp.add(a.get(i).get(j).subtract(tmp.get(j - 1)));
                }
                result.add(tmp);
            }
        }
        else{
            for (int i = 0; i < rows; i++) {
                List<BigDecimal> tmp = new ArrayList<>();
                for (int j = 0; j < cols; j++) {
                    tmp.add(a.get(i).get(j).subtract(b.get(i).get(j)));
                }
                result.add(tmp);
            }
        }
        return result;
    }

    // 矩阵逐元素相乘
    private List<List<BigDecimal>> multiplyMatrices(List<List<BigDecimal>> a, List<List<BigDecimal>> b) {
        int rows = a.size();
        int cols = a.get(0).size();
        List<List<BigDecimal>> result = new ArrayList<>(rows);

        if(b.get(0).get(0).equals(f2)){
            List<List<BigDecimal>> startYearData = subtemplateIdToStartYearData.get(subtemplateId);
            for (int i = 0; i < rows; i++) {
                List<BigDecimal> tmp = new ArrayList<>();
                for (int j = 0; j < cols; j++) {
                    if(j == 0) tmp.add(a.get(i).get(j).multiply(startYearData.get(i).get(j)));
                    else tmp.add(a.get(i).get(j).multiply(tmp.get(j - 1)));
                }
                result.add(tmp);
            }
        }
        else{
            for (int i = 0; i < rows; i++) {
                List<BigDecimal> tmp = new ArrayList<>();
                for (int j = 0; j < cols; j++) {
                    tmp.add(a.get(i).get(j).multiply(b.get(i).get(j)));
                }
                result.add(tmp);
            }
        }
        return result;
    }

    // 矩阵逐元素相除
    private List<List<BigDecimal>> divideMatrices(List<List<BigDecimal>> a, List<List<BigDecimal>> b) {
        int rows = a.size();
        int cols = a.get(0).size();
        List<List<BigDecimal>> result = new ArrayList<>(rows);

        if(b.get(0).get(0).equals(f2)){
            List<List<BigDecimal>> startYearData = subtemplateIdToStartYearData.get(subtemplateId);
            for (int i = 0; i < rows; i++) {
                List<BigDecimal> tmp = new ArrayList<>();
                for (int j = 0; j < cols; j++) {
                    if(j == 0) tmp.add(a.get(i).get(j).divide(startYearData.get(i).get(j), BigDecimal.ROUND_HALF_UP));
                    else tmp.add(a.get(i).get(j).divide(tmp.get(j - 1), BigDecimal.ROUND_HALF_UP));
                }
                result.add(tmp);
            }
        }
        else{
            for (int i = 0; i < rows; i++) {
                List<BigDecimal> tmp = new ArrayList<>();
                for (int j = 0; j < cols; j++) {
                    tmp.add(a.get(i).get(j).divide(b.get(i).get(j), BigDecimal.ROUND_HALF_UP));
                }
                result.add(tmp);
            }
        }
        return result;
    }

    // 矩阵逐元素乘以标量
    private List<List<BigDecimal>> addScalar(List<List<BigDecimal>> matrix, BigDecimal scalar) {
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
    private List<List<BigDecimal>> subtractScalar(List<List<BigDecimal>> matrix, BigDecimal scalar) {
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
    private List<List<BigDecimal>> multiplyScalar(List<List<BigDecimal>> matrix, BigDecimal scalar) {
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
    private List<List<BigDecimal>> divideScalar(List<List<BigDecimal>> matrix, BigDecimal scalar) {
        int rows = matrix.size();
        int cols = matrix.get(0).size();
        List<List<BigDecimal>> result = new ArrayList<>(rows);
        for (int i = 0; i < rows; i++) {
            List<BigDecimal> row = new ArrayList<>(cols);
            for (int j = 0; j < cols; j++) {
                row.add(matrix.get(i).get(j).divide(scalar, BigDecimal.ROUND_HALF_UP));
            }
            result.add(row);
        }
        return result;
    }
}





