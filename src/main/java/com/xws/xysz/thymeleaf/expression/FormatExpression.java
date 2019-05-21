package com.xws.xysz.thymeleaf.expression;

import com.xws.xysz.thymeleaf.util.FormatUtil;
import com.xws.xysz.thymeleaf.util.MathUtil;
import org.thymeleaf.context.IExpressionContext;
import org.thymeleaf.expression.IExpressionObjectFactory;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * JokerYG
 * Date: 2018-11-28
 * Time: 15:50
 */
public class FormatExpression implements IExpressionObjectFactory {
    public static final String FORMAT_UTILS_EXPRESSION_NAME = "format";
    public static final String MATH_UTILS_EXPRESSION_NAME = "math";
    public static final Set<String> ALL_EXPRESSION_OBJECT_NAMES;
    static {
        final Set<String> allExpressionObjectNames = new LinkedHashSet<String>();
        allExpressionObjectNames.add(FORMAT_UTILS_EXPRESSION_NAME);
        allExpressionObjectNames.add(MATH_UTILS_EXPRESSION_NAME);
        ALL_EXPRESSION_OBJECT_NAMES = Collections.unmodifiableSet(allExpressionObjectNames);
    }
    @Override
    public Set<String> getAllExpressionObjectNames() {
        return ALL_EXPRESSION_OBJECT_NAMES;
    }

    @Override
    public Object buildObject(IExpressionContext iExpressionContext, String expressionObjectName) {
        if(null == expressionObjectName)
            return null;
        Object obj = null;
        switch (expressionObjectName) {
            case FORMAT_UTILS_EXPRESSION_NAME:
                obj = new FormatUtil();
                break;
            case MATH_UTILS_EXPRESSION_NAME:
                obj = new MathUtil();
                break;
            default:
                break;
        }
        return obj;

    }

    @Override
    public boolean isCacheable(String expressionObjectName) {
        if(FORMAT_UTILS_EXPRESSION_NAME.equals(expressionObjectName)) {
            return true;
        }else if(MATH_UTILS_EXPRESSION_NAME.equals(expressionObjectName)){
            return true;
        }else {
            return false;
        }

    }
}
