package com.xws.xysz.thymeleaf.dialect;

import com.xws.xysz.thymeleaf.expression.FormatExpression;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.dialect.IExpressionObjectDialect;
import org.thymeleaf.expression.IExpressionObjectFactory;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * JokerYG
 * Date: 2018-11-28
 * Time: 16:09
 */
public class MyDialect extends AbstractProcessorDialect implements IExpressionObjectDialect {
    private static final String NAME = "MyDialect";
    private static final String PREFIX = "my";
    private static IExpressionObjectFactory expressionObjectFactory;

    public MyDialect() {
        super(NAME, PREFIX, StandardDialect.PROCESSOR_PRECEDENCE);
    }


    @Override
    public IExpressionObjectFactory getExpressionObjectFactory() {
        if (this.expressionObjectFactory == null) {
            this.expressionObjectFactory = new FormatExpression();
        }
        return this.expressionObjectFactory;

    }

    @Override
    public Set<IProcessor> getProcessors(String dialectPrefix) {
        LinkedHashSet<IProcessor> processors = new LinkedHashSet<IProcessor>();
//        processors.add(new SansitiveEncryptProcessor(dialectPrefix));
        return processors;
    }
}
