package org.example.service.extensions;

import org.example.service.OrdersService;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

public class ExtensionForOrders implements ParameterResolver {

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().equals(OrdersService.class);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        return new OrdersService("C:\\IntelijProjects\\Z10_T03_MultimoduleApp\\parentModule\\service\\src\\test\\resources\\clients.json",
                "C:\\IntelijProjects\\Z10_T03_MultimoduleApp\\parentModule\\service\\src\\test\\resources\\products.json",
                "C:\\IntelijProjects\\Z10_T03_MultimoduleApp\\parentModule\\service\\src\\test\\resources\\preferences.json");
    }
}
