package vip.xiaonuo.biz.modular.New.subtemplate.strategy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public enum CalculateStrategy implements Strategy {
    addMatrixSTRATEGY {
        @Override
        public List<List<BigDecimal>> calculateMethod(List<List<BigDecimal>> a, List<List<BigDecimal>> b,
                                                         Map<Long, List<List<BigDecimal>>> subtemplateIdToStartYearData, Long subtemplateId) {
            int rows = a.size();
            int cols = a.get(0).size();
            List<List<BigDecimal>> result = new ArrayList<>(rows);

            if(b.get(0).get(0).equals(new BigDecimal("-1E+102"))){
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
    },
    subtractMatrixSTRATEGY {
        @Override
        public List<List<BigDecimal>> calculateMethod(List<List<BigDecimal>> a, List<List<BigDecimal>> b,
                                       Map<Long, List<List<BigDecimal>>> subtemplateIdToStartYearData, Long subtemplateId) {
            int rows = a.size();
            int cols = a.get(0).size();
            List<List<BigDecimal>> result = new ArrayList<>(rows);

            if(b.get(0).get(0).equals(new BigDecimal("-1E+102"))){
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
    },
    multiplyMatrixSTRATEGY {
        @Override
        public List<List<BigDecimal>> calculateMethod(List<List<BigDecimal>> a, List<List<BigDecimal>> b,
                                                         Map<Long, List<List<BigDecimal>>> subtemplateIdToStartYearData, Long subtemplateId) {
            int rows = a.size();
            int cols = a.get(0).size();
            List<List<BigDecimal>> result = new ArrayList<>(rows);

            if(b.get(0).get(0).equals(new BigDecimal("-1E+102"))){
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
    },
    divideMatrixSTRATEGY {
        public List<List<BigDecimal>> calculateMethod(List<List<BigDecimal>> a, List<List<BigDecimal>> b,
                                                         Map<Long, List<List<BigDecimal>>> subtemplateIdToStartYearData, Long subtemplateId) {
            int rows = a.size();
            int cols = a.get(0).size();
            List<List<BigDecimal>> result = new ArrayList<>(rows);

            if(b.get(0).get(0).equals(new BigDecimal("-1E+102"))){
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
    },
    addScalarSTRATEGY {
        public List<List<BigDecimal>> calculateMethod(List<List<BigDecimal>> a, List<List<BigDecimal>> b,
                                                         Map<Long, List<List<BigDecimal>>> subtemplateIdToStartYearData, Long subtemplateId) {
            int rows = a.size();
            int cols = a.get(0).size();
            BigDecimal scalar = b.get(0).get(1);
            List<List<BigDecimal>> result = new ArrayList<>(rows);
            for (int i = 0; i < rows; i++) {
                List<BigDecimal> row = new ArrayList<>(cols);
                for (int j = 0; j < cols; j++) {
                    row.add(a.get(i).get(j).add(scalar));
                }
                result.add(row);
            }
            return result;
        }
    },
    subtractScalarSTRATEGY {
        public List<List<BigDecimal>> calculateMethod(List<List<BigDecimal>> a, List<List<BigDecimal>> b,
                                                         Map<Long, List<List<BigDecimal>>> subtemplateIdToStartYearData, Long subtemplateId) {
            int rows = a.size();
            int cols = a.get(0).size();
            BigDecimal scalar = b.get(0).get(1);
            List<List<BigDecimal>> result = new ArrayList<>(rows);
            for (int i = 0; i < rows; i++) {
                List<BigDecimal> row = new ArrayList<>(cols);
                for (int j = 0; j < cols; j++) {
                    row.add(a.get(i).get(j).subtract(scalar));
                }
                result.add(row);
            }
            return result;
        }
    },
    multiplyScalarSTRATEGY {
        public List<List<BigDecimal>> calculateMethod(List<List<BigDecimal>> a, List<List<BigDecimal>> b,
                                                         Map<Long, List<List<BigDecimal>>> subtemplateIdToStartYearData, Long subtemplateId) {
            int rows = a.size();
            int cols = a.get(0).size();
            BigDecimal scalar = b.get(0).get(1);
            List<List<BigDecimal>> result = new ArrayList<>(rows);
            for (int i = 0; i < rows; i++) {
                List<BigDecimal> row = new ArrayList<>(cols);
                for (int j = 0; j < cols; j++) {
                    row.add(a.get(i).get(j).multiply(scalar));
                }
                result.add(row);
            }
            return result;
        }
    },
    divideScalarSTRATEGY {
        public List<List<BigDecimal>> calculateMethod(List<List<BigDecimal>> a, List<List<BigDecimal>> b,
                                                         Map<Long, List<List<BigDecimal>>> subtemplateIdToStartYearData, Long subtemplateId) {
            int rows = a.size();
            int cols = a.get(0).size();
            BigDecimal scalar = b.get(0).get(1);
            List<List<BigDecimal>> result = new ArrayList<>(rows);
            for (int i = 0; i < rows; i++) {
                List<BigDecimal> row = new ArrayList<>(cols);
                for (int j = 0; j < cols; j++) {
                    row.add(a.get(i).get(j).divide(scalar, BigDecimal.ROUND_HALF_UP));
                }
                result.add(row);
            }
            return result;
        }
    };

//    public static calculateStrategy fromType(int type) {
//        switch (type) {
//            case 1:
//                return addMatrixSTRATEGY;
//            case 2:
//                return subtractMatrixSTRATEGY;
//            default:
//                throw new IllegalArgumentException("无效选择，请输入1或2");
//        }
//    }
    public static CalculateStrategy fromType(char op, List<List<BigDecimal>> a, List<List<BigDecimal>> b) {
        switch (op) {
            case '+':
                if(b.get(0).get(0).equals(new BigDecimal("-1E+101"))) return addScalarSTRATEGY;
                else return addMatrixSTRATEGY;
            case '-':
                if(b.get(0).get(0).equals(new BigDecimal("-1E+101"))) return subtractScalarSTRATEGY;
                else return subtractMatrixSTRATEGY;
            case '*':
                if(b.get(0).get(0).equals(new BigDecimal("-1E+101"))) return multiplyScalarSTRATEGY;
                else return multiplyMatrixSTRATEGY;
            case '/':
                if(b.get(0).get(0).equals(new BigDecimal("-1E+101"))){
                    if (b.get(0).get(1).compareTo(BigDecimal.ZERO) == 0) throw new ArithmeticException("Division by scalar zero");
                    return divideScalarSTRATEGY;
                }
                else return divideMatrixSTRATEGY;
            default:
                throw new IllegalArgumentException("无效操作符!");
        }
    }
}
